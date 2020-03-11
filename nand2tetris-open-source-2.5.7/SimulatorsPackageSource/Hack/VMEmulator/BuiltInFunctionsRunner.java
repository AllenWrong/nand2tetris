/********************************************************************************
 * The contents of this file are subject to the GNU General Public License      *
 * (GPL) Version 2 or later (the "License"); you may not use this file except   *
 * in compliance with the License. You may obtain a copy of the License at      *
 * http://www.gnu.org/copyleft/gpl.html                                         *
 *                                                                              *
 * Software distributed under the License is distributed on an "AS IS" basis,   *
 * without warranty of any kind, either expressed or implied. See the License   *
 * for the specific language governing rights and limitations under the         *
 * License.                                                                     *
 *                                                                              *
 * This file was originally developed as part of the software suite that        *
 * supports the book "The Elements of Computing Systems" by Nisan and Schocken, *
 * MIT Press 2005. If you modify the contents of this file, please document and *
 * mark your changes clearly, for the benefit of others.                        *
 ********************************************************************************/

package Hack.VMEmulator;

import Hack.Controller.ProgramException;
import Hack.Utilities.Definitions;
import java.io.File;
import java.lang.reflect.*;

/**
 * A class that runs built-in VM code as a coroutine so that
 * built-in code may pause and call VM code that the user may debug and step
 * through.
 */
public class BuiltInFunctionsRunner implements Runnable {

	// Message types between threads
	private static final int CALL_REQUEST = 0;
	private static final int RETURN_REQUEST = 1;
	private static final int END_PROGRAM_REQUEST = 2; // program => built-in
	private static final int THROW_PROGRAM_EXCEPTION_REQUEST = 3; // b.i.=>prog
	private static final int INFINITE_LOOP_REQUEST = 4; // b.i.=>prog

    // objects for communication between the threads
	private class BuiltInToProgramRequest {
		int request;
		String details;
		short[] params;
		short returnValue;
	}
    private class ProgramToBuiltInRequest {
		int request;
		Method functionObject;
		Object[] params;
		short returnValue;
	};
	private BuiltInToProgramRequest builtInToProgram;
	private ProgramToBuiltInRequest programToBuiltIn;

	// The thread that runs the built-in code
	private Thread thread;

	// The CPU that communicates with this class
	private CPU cpu;

	// The built-in dir
	private File builtInDir;
	
	/********************** Code common to both threads *****/

	/**
	 * Relinquises control to the other thread until it relinquishes back.
	 * Invariant: at any given time one of the threads is waiting here.
	 */
	private synchronized void continueOtherThread() {
		notify();
		while (true) {
			try {
				wait();
			} catch (InterruptedException e) {
				continue;
			}
			return;
		}
	}

	/********************** Code run by the VM Emulator	thread *****/

    /**
     * Constructs a new BuiltInFunctionsRunner, which will read memory
	 * and issue call-backs and returns to the given CPU and load built-in
	 * vm code from the given directory.
     */
    public BuiltInFunctionsRunner(CPU cpu, File builtInDir) {
		this.cpu = cpu;
		this.builtInDir = builtInDir;
		builtInToProgram = new BuiltInToProgramRequest();
		programToBuiltIn = new ProgramToBuiltInRequest();
		thread = new Thread(this);
		synchronized (this) {
			thread.start();
			continueOtherThread(); // Let the built-in code runner init itself
								   // The notify part of this call does nothing
		}
    }

	/**
	 * Called by the VM emulator. Tells the built-in code runner thread
	 * to exit all currently running built-in functions. Returns after
	 * this was completed.
	 */
	public void killAllRunningBuiltInFunctions() {
		programToBuiltIn.request = END_PROGRAM_REQUEST;
		continueOtherThread();
	}

	/**
	 * Called by the VM emulator. Tells	the built-in code runner thread
	 * to resume an already-running built-in function which was waiting for
	 * a return value from another function.
	 */
	public void returnToBuiltInFunction(short returnValue) throws ProgramException {
		programToBuiltIn.request = RETURN_REQUEST;
		programToBuiltIn.returnValue = returnValue;
		sendBuiltInRequestAndWaitForAnswer();
	}

	/**
	 * Called by the VM emulator. Searches for a built-in vm function by its
	 * name and number of parameters (the length of the params array).
	 * If found - tells the built-in code runner thread
	 * to call the named built-in function with the given params.
	 * Throws a ProgramException if no built-in implementation was found.
	 */
	public void callBuiltInFunction(String functionName, short[] params) throws ProgramException {
        int dotLocation = functionName.indexOf(".");
        if (dotLocation == -1) {
            throw new ProgramException("Illegal function name: " + functionName);
		}
        String className = functionName.substring(0, dotLocation);
		String methodName = functionName.substring(dotLocation+1, functionName.length());
		if (methodName.equals("new")) {
			// new is a reserved Java word - therefore Jack functions named
			// new are implemented by Java functions named NEW.
			methodName = "NEW";
		}
		
		// Find the implementing class
		Class implementingClass;
		try {
			implementingClass = Class.forName(builtInDir+"."+className);
		} catch (ClassNotFoundException cnfe) {
			throw new ProgramException("Can't find "+className+".vm or a built-in implementation for class "+className);
		}

		// Check that the class is a subclass of BuiltInVMClass
		// (right now not that important if the class doesn't want to access
		// the computer - like math - but good practice anyway).
		Class currentClass = implementingClass;
		boolean found;
		do {
			currentClass = currentClass.getSuperclass();
			found = currentClass.getName().equals("Hack.VMEmulator.BuiltInVMClass");
		} while (!found && !currentClass.getName().equals("java.lang.Object"));
		if (!found) {
			throw new ProgramException("Built-in implementation for "+className+" is not a subclass of BuiltInVMClass");
		}

		// Find the implementing method & fill in the request
		Class[] paramsClasses = new Class[params.length];
		Object[] requestParams = new Object[params.length];
		for (int i=0; i<params.length; ++i) {
			requestParams[i] = new Short(params[i]);
			paramsClasses[i] = short.class;
		}

		Method functionObject;
		try {
			functionObject =
				implementingClass.getDeclaredMethod(methodName, paramsClasses);
		} catch (NoSuchMethodException nsme) {
			throw new ProgramException("Can't find "+className+".vm or a built-in implementation for function "+methodName+" in class "+className+" taking "+params.length+" argument"+(params.length==1?"":"s")+".");
		}
		Class returnType = functionObject.getReturnType();
		if (returnType != short.class && returnType != void.class &&
			returnType != char.class && returnType != boolean.class) {
			throw new ProgramException("Can't find "+className+".vm and the built-in implementation for "+functionName+" taking "+params.length+" arguments doesn't return short/char/void/boolean.");
		}
		programToBuiltIn.request = CALL_REQUEST;
		programToBuiltIn.params = requestParams;
		programToBuiltIn.functionObject = functionObject;
			
		sendBuiltInRequestAndWaitForAnswer();
	}

	/**
	 * Sends a request to the built-in thread (the request is a data-member)
	 * and waits for an answer from the built-in thread and passes it to the
	 * VM Emulator.
	 * If a built-in function finished, calls
	 * cpu.returnFromBuiltInFunction with the return value.
	 * If the function requested another function call,
	 * calls cpu.callFunctionFromBuiltIn with the function name and parameters.
	 * If an exception was thrown by a built-in function, throws a
	 * ProgramException.
	 */
	private void sendBuiltInRequestAndWaitForAnswer() throws ProgramException {
		continueOtherThread();
		switch(builtInToProgram.request) {
		case CALL_REQUEST:
			cpu.callFunctionFromBuiltIn(builtInToProgram.details,
										builtInToProgram.params);
			break;
		case RETURN_REQUEST:
			cpu.returnFromBuiltInFunction(builtInToProgram.returnValue);
			break;
		case INFINITE_LOOP_REQUEST:
			cpu.infiniteLoopFromBuiltIn(builtInToProgram.details);
			break;
		case THROW_PROGRAM_EXCEPTION_REQUEST:
			throw new ProgramException(builtInToProgram.details);
			//break - unreachable
		}
	}

	/********************** Code run by the Built In Code Runner thread *****/

	/**
	 * Runs the built-in code runner thread
	 */
	public void run() {
		// Nothing on this thread should work while
		// the main program isn't waiting
		synchronized (this) {
			// Make sure that callbacks / memory access for built-in code
			// run on this thread is done by this instance of
			// BuiltInFunctionsRunner.
			BuiltInVMClass.associateForThread(this);
			while (true) {
				try {
					// Tell the VM Emulator that we finished init
					// by issueing a request for calling a "null function" -
					// The emulator expects us to tell it we finished init
					// and will ignore the actual function call request and
					// continue as normal.
					builtInFunctionRequestsCall(null, null);
				} catch (TerminateVMProgramThrowable e) {
					continue;
				}
				return;
			}
		}
	}

    /**
     * Called by a built-in function through the BuiltInVMClass class.
	 * Requests that the VM Emulator run a function (either built-in or not).
	 * Once the function completes, the function's return value is returned.
	 * If due to a user's request or due to an error the VM program is
	 * required to terminate, a TerminateVMProgramThrowable object is thrown.
	 * The calling built-in function may catch this object, perform any
	 * necessary cleanups, and rethrow it.
     */
	public short builtInFunctionRequestsCall(String functionName, short[] params) throws TerminateVMProgramThrowable {
		builtInToProgram.request = CALL_REQUEST;
		builtInToProgram.details = functionName;
		builtInToProgram.params = params;
		// Wait for a command and loop while we're getting call commands
		for(continueOtherThread(); programToBuiltIn.request == CALL_REQUEST;
			continueOtherThread()) {
			try { // Try to run the built-in implementation
				// programToBuiltIn might be overwritten until the return
				// from the call. Save what's needed.
				Class returnType = programToBuiltIn.functionObject.getReturnType();
				functionName =
					programToBuiltIn.functionObject.getName();
				// Execute
				Object returnValue =
					programToBuiltIn.functionObject.invoke(null,
														   programToBuiltIn.params);
				builtInToProgram.request = RETURN_REQUEST;
				if (returnType == short.class) {
					builtInToProgram.returnValue = ((Short)returnValue).shortValue();
				} else if (returnType == char.class) {
					builtInToProgram.returnValue = (short)((Character)returnValue).charValue();
				} else if (returnType == boolean.class) {
					if (((Boolean)returnValue).booleanValue()) {
						builtInToProgram.returnValue = (short)-1;
					} else {
						builtInToProgram.returnValue = 0;
					}
				} else { // returnType == void.class
					builtInToProgram.returnValue = 0;
				}
			} catch (IllegalAccessException iae) {
				// Error running - abort VM program
				builtInToProgram.request = THROW_PROGRAM_EXCEPTION_REQUEST;
				builtInToProgram.details = "Error trying to run the built-in implementation of "+functionName;
			} catch (InvocationTargetException ita) {
				// Rethrow a TerminateVMProgramThrowable object that was thrown
				try {
					throw (TerminateVMProgramThrowable)ita.getTargetException();
				} catch (ClassCastException cce) {
					// Error in the built-in function - abort VM program
					builtInToProgram.request = THROW_PROGRAM_EXCEPTION_REQUEST;
					builtInToProgram.details = "The built-in implementation of "+functionName+" caused an exception: "+ita.getTargetException().toString();
				}
			}
		}
		if (programToBuiltIn.request == RETURN_REQUEST) {
			return programToBuiltIn.returnValue;
		} else { // END_PROGRAM_REQUEST
			throw new TerminateVMProgramThrowable();
		}
	}

	/**
	 * Makes sure an address that a built-in function requested
	 * to write/read from is legal. If not - notifies the vm emulator
	 * Thread that an exception occured, waits for a signal from it and
	 * throws a TerminateVMProgramThrowable.
	 */
	private void checkMemoryAddress(short address) throws TerminateVMProgramThrowable {
        if (!((address >= Definitions.HEAP_START_ADDRESS && address <= Definitions.HEAP_END_ADDRESS) ||
              (address >= Definitions.SCREEN_START_ADDRESS && address <= Definitions.SCREEN_END_ADDRESS) ||
              address == 0)) {
			builtInToProgram.request = THROW_PROGRAM_EXCEPTION_REQUEST;
			builtInToProgram.details = "A built-in function tried to access memory outside the Heap or Screen range";
			continueOtherThread();
			// now programToBuiltIn.request == END_PROGRAM_REQUEST
			throw new TerminateVMProgramThrowable();
		}
	}

	/**
	 * Called by a built-in functio through the BuiltInVMClass class.
	 * Enters an infinite loop, de-facto halting the program.
	 * Important so that tests and other scripts finish counting
	 * (since a built-in infinite loop doesn't count as steps).
	 * Also needed because there is no good way to use the stop button to
	 * stop an infinite loop in a built-in Jack class.
	 * A message containing information may be provided (can be null).
	 */
	public void builtInFunctionRequestsInfiniteLoop(String message)
			throws TerminateVMProgramThrowable {
		builtInToProgram.request = INFINITE_LOOP_REQUEST;
		builtInToProgram.details = message;
		continueOtherThread();
		// now programToBuiltIn.request == END_PROGRAM_REQUEST
		throw new TerminateVMProgramThrowable();
	}

	/**
     * Called by a built-in function through the BuiltInVMClass class.
	 * Writes the given value top the given address in the VM memory.
	 */
	public void builtInFunctionRequestsMemoryWrite(short address, short value) throws TerminateVMProgramThrowable {
		checkMemoryAddress(address);
		cpu.getRAM().setValueAt(address, value, false);
	}

	/**
     * Called by a built-in function through the BuiltInVMClass class.
	 * Returns the contents of the given address in the VM memory.
	 */
	public short builtInFunctionRequestsMemoryRead(short address) throws TerminateVMProgramThrowable {
		checkMemoryAddress(address);
		return cpu.getRAM().getValueAt(address);
	}
}
