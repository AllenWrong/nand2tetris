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

import java.util.Hashtable;
import Hack.Utilities.Definitions;

/**
 * A BuiltIn VM Class.
 * The base class for all classes which are implemented in java.
 * All methods in decendents of this class represent functions and therefore
 * should be static.
 */
public abstract class BuiltInVMClass {
	private static Hashtable builtInFunctionsRunnerByThread = new Hashtable();

	/* Some definitions regarding the memory. */
    public static final short SCREEN_START_ADDRESS = Definitions.SCREEN_START_ADDRESS;
    public static final short SCREEN_END_ADDRESS = Definitions.SCREEN_END_ADDRESS-1; // Definitions.SCREEN_END_ADDRESS is actually one past...
    public static final int SCREEN_WIDTH = Definitions.SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT = Definitions.SCREEN_HEIGHT;
    public static final short HEAP_START_ADDRESS=Definitions.HEAP_START_ADDRESS;
    public static final short HEAP_END_ADDRESS = Definitions.HEAP_END_ADDRESS;
	public static final short KEYBOARD_ADDRESS = Definitions.KEYBOARD_ADDRESS;
	public static final short NEWLINE_KEY = Definitions.NEWLINE_KEY;
	public static final short BACKSPACE_KEY = Definitions.BACKSPACE_KEY;

	/* Methods */

	/**
	 * Writes a value to the VM memory.
	 * The arguments are ints so that literals and chars (for the value)
	 * may be passed to them without conversion for convenience however
	 * they are truncated to shorts.
	 */
    protected static void writeMemory(int address, int value)
			throws TerminateVMProgramThrowable {
		((BuiltInFunctionsRunner)builtInFunctionsRunnerByThread.get(Thread.currentThread())).builtInFunctionRequestsMemoryWrite((short)address, (short)value);
	}

	/**
	 * Reads a value from the VM memory.
	 * The argument is an int so that literals may be passed to it
	 * without conversion for convenience however it is truncated to a short.
	 */
    protected static short readMemory(int address)
			throws TerminateVMProgramThrowable {
		return ((BuiltInFunctionsRunner)builtInFunctionsRunnerByThread.get(Thread.currentThread())).builtInFunctionRequestsMemoryRead((short)address);
	}

	/**
	 * The following functions call a VM function.
	 * The first version is the general version for all numbers of parameters.
	 * After it, specialized 0-4 params syntactic-sugar versions are provided.
	 * These accept ints for convenience - so that integer literals and
	 * characters may be passed to them. Note however that all their
	 * arguments are truncated to shorts.
	 */
	protected static short callFunction(String functionName,
		   								short[] params)
			throws TerminateVMProgramThrowable {
		return ((BuiltInFunctionsRunner)builtInFunctionsRunnerByThread.get(Thread.currentThread())).builtInFunctionRequestsCall(functionName, params);
	}

	protected static short callFunction(String functionName)
			throws TerminateVMProgramThrowable {
		return callFunction(functionName, new short[0]);
	}

	protected static short callFunction(String functionName,
										int param)
			throws TerminateVMProgramThrowable {
		return callFunction(functionName, new short[]{(short)param});
	}

	protected static short callFunction(String functionName,
										int param1, int param2)
			throws TerminateVMProgramThrowable {
		return callFunction(functionName, new short[]{(short)param1,
													  (short)param2});
	}

	protected static short callFunction(String functionName,
										int param1, int param2,
										int param3)
			throws TerminateVMProgramThrowable {
		return callFunction(functionName, new short[]{(short)param1,
													  (short)param2,
													  (short)param3});
	}

	protected static short callFunction(String functionName,
										int param1, int param2,
										int param3, int param4)
			throws TerminateVMProgramThrowable {
		return callFunction(functionName, new short[]{(short)param1,
													  (short)param2,
													  (short)param3,
													  (short)param4});
	}

	/**
	 * Should be called only by Sys.halt (or an equivalent routine in an
	 * alternative hack operating system).
	 * Enters an infinite loop, de-facto halting the program.
	 * Important so that tests and other scripts finish counting
	 * (since a built-in infinite loop doesn't count as steps).
	 * Also needed because there is no good way to use the stop button to
	 * stop an infinite loop in a built-in Jack class.
	 * A message containing information may be provided (can be null).
	 */
	protected static void infiniteLoop(String message)
			throws TerminateVMProgramThrowable {
		((BuiltInFunctionsRunner)builtInFunctionsRunnerByThread.get(Thread.currentThread())).builtInFunctionRequestsInfiniteLoop(message);
	}


	/* Methods for internal use: */

	/**
	 * The following function should not be called by an implementing class:
	 *
	 * Called by a BuiltInFunctionsRunner to request that all calls from
	 * built-in functions executed from this thread be forwarded to it.
	 * This is used instead of instatiating each implementing class as
	 * needed with a data member of the BuiltInFunctionsRunner because
	 * logically all implementing classes should implement only static
	 * methods.
	 */
	static final void associateForThread(BuiltInFunctionsRunner bifr) {
		builtInFunctionsRunnerByThread.put(Thread.currentThread(), bifr);
	}

}
