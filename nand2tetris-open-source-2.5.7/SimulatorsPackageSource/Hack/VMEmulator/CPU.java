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

import Hack.ComputerParts.*;
import java.util.*;
import java.io.*;
import Hack.Utilities.*;
import Hack.CPUEmulator.RAM;
import Hack.Controller.*;
import Hack.VirtualMachine.*;

/**
 * A CPU of a computer. Runs the program on the virtual machine emulator.
 */
public class CPU {

    // stack identification
    private static final int MAIN_STACK = 1;
    private static final int METHOD_STACK = 2;

    // The program that will be executed
    private VMProgram program;

    // The memory used by the CPU
    private RAM ram;

    // The call stack
    private CallStack callStack;

    // The calculator
    private Calculator calculator;

    // The bus.
    private Bus bus;

    // The memory segments
    private AbsolutePointedMemorySegment stackSegment;
    private TrimmedAbsoluteMemorySegment workingStackSegment;
    private MemorySegment staticSegment;
    private MemorySegment localSegment;
    private MemorySegment argSegment;
    private MemorySegment thisSegment;
    private MemorySegment thatSegment;
    private MemorySegment tempSegment;

    // A mapping from memory segment codes to the MemorySegment objects (not including stack).
    private MemorySegment[] segments;

    // A stack of method frame addresses
    private Vector stackFrames;

    // The last instruction that was executed.
    private VMEmulatorInstruction currentInstruction;

	// Runner for built-in vm code
	private BuiltInFunctionsRunner builtInFunctionsRunner;

    /**
     * Constructs the CPU with given program, RAM, call stack, bus, stack and other
     * memory segments.
     */
    public CPU(VMProgram program, RAM ram, CallStack callStack,
			   Calculator calculator, Bus bus,
               AbsolutePointedMemorySegment stackSegment,
               TrimmedAbsoluteMemorySegment workingStackSegment,
			   MemorySegment staticSegment, MemorySegment localSegment,
			   MemorySegment argSegment, MemorySegment thisSegment,
               MemorySegment thatSegment, MemorySegment tempSegment,
			   File builtInDir) {
        this.program = program;
        this.ram = ram;
        this.callStack = callStack;
        this.calculator = calculator;
        this.bus = bus;

        this.stackSegment = stackSegment;
        this.workingStackSegment = workingStackSegment;
        this.staticSegment = staticSegment;
        this.localSegment = localSegment;
        this.argSegment = argSegment;
        this.thisSegment = thisSegment;
        this.thatSegment = thatSegment;
        this.tempSegment = tempSegment;

        segments = new MemorySegment[HVMInstructionSet.NUMBER_OF_ACTUAL_SEGMENTS];
        segments[HVMInstructionSet.LOCAL_SEGMENT_CODE] = localSegment;
        segments[HVMInstructionSet.ARG_SEGMENT_CODE] = argSegment;
        segments[HVMInstructionSet.THIS_SEGMENT_CODE] = thisSegment;
        segments[HVMInstructionSet.THAT_SEGMENT_CODE] = thatSegment;
        segments[HVMInstructionSet.TEMP_SEGMENT_CODE] = tempSegment;

        stackFrames = new Vector();

        if (program.getGUI() != null) {
            builtInFunctionsRunner =
                new BuiltInFunctionsRunner(this, builtInDir);
        }
    }

    /**
     * Initializes the cpu.
     */
    public void boot() {
        stackSegment.setStartAddress(Definitions.STACK_START_ADDRESS);
        workingStackSegment.setStartAddress(Definitions.STACK_START_ADDRESS);
        localSegment.setEnabledRange(Definitions.STACK_START_ADDRESS,
                                     Definitions.STACK_END_ADDRESS, true);
        argSegment.setEnabledRange(Definitions.STACK_START_ADDRESS,
                                   Definitions.STACK_END_ADDRESS, true);
        thisSegment.setEnabledRange(Definitions.HEAP_START_ADDRESS,
									Definitions.HEAP_END_ADDRESS, true);
        thatSegment.setEnabledRange(Definitions.HEAP_START_ADDRESS,
			   						Definitions.SCREEN_END_ADDRESS, true);
        staticSegment.setStartAddress(Definitions.VAR_START_ADDRESS);
        staticSegment.setEnabledRange(Definitions.VAR_START_ADDRESS,
			   						  Definitions.VAR_END_ADDRESS - 1, true);
        setSP(Definitions.STACK_START_ADDRESS);
		stackFrames.clear();
        if (builtInFunctionsRunner != null) {
            builtInFunctionsRunner.killAllRunningBuiltInFunctions();
        }
    }

    /**
     * Returns the bus.
     */
    public Bus getBus() {
        return bus;
    }

    /**
     * Returns the RAM (random access memory).
     */
    public RAM getRAM() {
        return ram;
    }

    /**
     * Returns the program.
     */
    public VMProgram getProgram() {
        return program;
    }

    /**
     * Returns the call stack.
     */
    public CallStack getCallStack() {
        return callStack;
    }

    /**
     * Returns the calculator.
     */
    public Calculator getCalculator() {
        return calculator;
    }

    /**
     * Returns an array of the memory segments.
     */
    public MemorySegment[] getMemorySegments() {
        return segments;
    }

    /**
     * Returns the stack PointedMemorySegment.
     */
    public PointedMemorySegment getStack() {
        return stackSegment;
    }

    /**
     * Returns the working stack PointedMemorySegment.
     */
    public PointedMemorySegment getWorkingStack() {
        return workingStackSegment;
    }

    /**
     * Returns the last instruction that was executed.
     */
    public VMEmulatorInstruction getCurrentInstruction() {
        return currentInstruction;
    }

    /**
     * Executes the current instruction (Program at pc).
     * Returns false if END command, true otherwise.
     */
    public void executeInstruction() throws ProgramException {
        currentInstruction = program.getNextInstruction();

        if (currentInstruction == null)
            throw new ProgramException("No more instructions to execute");

        switch (currentInstruction.getOpCode()) {
            case HVMInstructionSet.ADD_CODE:
                add();
                break;
            case HVMInstructionSet.SUBSTRACT_CODE:
                substract();
                break;
            case HVMInstructionSet.NEGATE_CODE:
                negate();
                break;
            case HVMInstructionSet.EQUAL_CODE:
                equal();
                break;
            case HVMInstructionSet.GREATER_THAN_CODE:
                greaterThan();
                break;
            case HVMInstructionSet.LESS_THAN_CODE:
                lessThan();
                break;
            case HVMInstructionSet.AND_CODE:
                and();
                break;
            case HVMInstructionSet.OR_CODE:
                or();
                break;
            case HVMInstructionSet.NOT_CODE:
                not();
                break;

            case HVMInstructionSet.PUSH_CODE:
                push(currentInstruction.getArg0(), currentInstruction.getArg1());
                break;
            case HVMInstructionSet.POP_CODE:
                pop(currentInstruction.getArg0(), currentInstruction.getArg1());
                break;

            case HVMInstructionSet.GOTO_CODE:
                goTo(currentInstruction.getArg0());
                break;
            case HVMInstructionSet.IF_GOTO_CODE:
                ifGoTo(currentInstruction.getArg0());
                break;

            case HVMInstructionSet.FUNCTION_CODE:
                if (program.getCurrentPC() == program.getPreviousPC() + 1)
                    throw new ProgramException("Missing return in " + callStack.getTopFunction());

                function(currentInstruction.getArg0());
                break;
            case HVMInstructionSet.RETURN_CODE:
                returnFromFunction();
                break;
            case HVMInstructionSet.CALL_CODE:
                callFunction(currentInstruction.getArg0(), currentInstruction.getArg1(),
                           currentInstruction.getStringArg(), false);
                break;
        }
    }

    /**
     * integer addition (binary operation).
     */
    public void add() throws ProgramException {
        calculate(2, Calculator.ADD);
    }

    /**
     * 2's complement integer substraction (binary operation)
     */
    public void substract() throws ProgramException {
        calculate(2, Calculator.SUBTRACT);
    }

    /**
     * 2's complement negation (unary operation)
     */
    public void negate() throws ProgramException {
        calculate(1, Calculator.NEGATE);
    }

    /**
     * Equalaty operation (binary operation). Returns(to the stack)
     * 0xFFFF as true,0x0000 as false
     */
    public void equal() throws ProgramException {
        calculate(2, Calculator.EQUAL);
    }

    /**
     * Greater than operation (binary operation). Returns(to the stack)
     * 0xFFFF as true,0x0000 as false
     */
    public void greaterThan() throws ProgramException {
        calculate(2, Calculator.GREATER_THAN);
    }

    /**
     * Less than operation (binary operation). Returns(to the stack)
     * 0xFFFF as true,0x0000 as false
     */
    public void lessThan() throws ProgramException {
        calculate(2, Calculator.LESS_THAN);
    }

    /**
     * Bit wise "AND" (binary operation).
     */
    public void and() throws ProgramException {
        calculate(2, Calculator.AND);
    }

    /**
     * Bit wise "OR" (binary operation).
     */
    public void or() throws ProgramException {
        calculate(2, Calculator.OR);
    }

    /**
     * Bit wise "NOT" (unary operation).
     */
    public void not() throws ProgramException {
        calculate(1, Calculator.NOT);
    }


    //----  Memory access instructions ---//

    /**
     * Pushes the n'th entry of the given segment onto the stack
     */
    public void push(short segment, short n) throws ProgramException {
        if (segment == HVMInstructionSet.CONST_SEGMENT_CODE)
            pushValue(METHOD_STACK ,n);
        else if (segment == HVMInstructionSet.POINTER_SEGMENT_CODE)
            switch (n) {
                case 0: pushFromRAM(METHOD_STACK, Definitions.THIS_POINTER_ADDRESS);
                        break;
                case 1: pushFromRAM(METHOD_STACK, Definitions.THAT_POINTER_ADDRESS);
                        break;
            }
        else
            pushFromSegment(METHOD_STACK, segment, n);
    }

    /**
     * Pops an item from the stack and stores it in the n'th entry of the given segment
     */
    public void pop(short segment, short n) throws ProgramException {
        if (segment == HVMInstructionSet.POINTER_SEGMENT_CODE) {
            switch (n) {
                case 0: popToThisPointer(METHOD_STACK);
                        break;
                case 1: popToThatPointer(METHOD_STACK);
                        break;
            }
        }
        else
            popToSegment(METHOD_STACK, segment, n);
    }


    //----  Program flow instructions ---//

    /**
     * Goes to the label at the given address
     */
    public void goTo(short address) {
        program.setPC(address);
    }

    /**
     * Pops a value from the stack and goes to the given address if the value
     * is not zero.
     */
    public void ifGoTo(short address) throws ProgramException {
        if (popAndReturn() != 0) {
            program.setPC(address);
        }
    }


    //----  Function calls instructions ---//

    /**
     * Here Starts the code of a function according to the given function name
     * that has the given number of local variables.
     * @param numberOfLocals The number of local variables
     */
    public void function(short numberOfLocals) throws ProgramException {

        short newSP = (short)(getSP() + numberOfLocals);
        checkSP(newSP);
        workingStackSegment.setStartAddress(newSP);

        // disable non relevant range of the local segment - enable only the number
        // of locals of this function.
        localSegment.setEnabledRange(getSP(), newSP - 1, true);

        for (int i = 0; i < numberOfLocals; i++) {
            pushValue(MAIN_STACK, (short)0);
        }

        String functionName = currentInstruction.getStringArg();

        // adds the new function to the top of the call stack.
        callStack.pushFunction(functionName);

        // sets the static segment range
        setStaticRange(functionName);
    }

	/**
	 * Enters an infinite loop requested by a built-in function,
	 * de-facto halting the program.
	 * important so that tests and other scripts finish counting
	 * (since a built-in infinite loop doesn't count as steps).
	 * also needed because there is no good way to use the stop button to
	 * stop an infinite loop in a built-in jack class.
	 * A message containing information may be provided (can be null).
	 */
	public void infiniteLoopFromBuiltIn(String message) {
		program.setPCToInfiniteLoopForBuiltIns(message);
	}

    /**
     * Returns from a built-in function
     */
	public void returnFromBuiltInFunction(short returnValue) throws ProgramException {
		// Push the return value
		pushValue(METHOD_STACK ,returnValue);
		// Return as we normally would
		returnFromFunction();
	}

    /**
     * Returns the value of the function to the top of the stack.
     */
    public void returnFromFunction() throws ProgramException {

        // make sure that there's somewhere to return to (old local <> 0)
        if (stackSegment.getValueAt(Definitions.LOCAL_POINTER_ADDRESS) == 0)
            throw new ProgramException("Nowhere to return to in " +
                                       getCallStack().getTopFunction() + "." +
                                       getCurrentInstruction().getIndexInFunction());

        // done in order to clear the method stack's contents
        workingStackSegment.setStartAddress(getSP());

        bus.send(ram, Definitions.LOCAL_POINTER_ADDRESS, ram, Definitions.R13_ADDRESS); // R13 = lcl
        bus.send(stackSegment, stackSegment.getValueAt(Definitions.LOCAL_POINTER_ADDRESS) - 5, ram, Definitions.R14_ADDRESS); // R14 = return address
        bus.send(stackSegment, getSP() - 1, stackSegment, ram.getValueAt(Definitions.ARG_POINTER_ADDRESS)); // *arg = return value
        setSP((short)(ram.getValueAt(Definitions.ARG_POINTER_ADDRESS) + 1)); // SP = arg + 1
        bus.send(stackSegment, ram.getValueAt(Definitions.R13_ADDRESS) - 1, ram, Definitions.THAT_POINTER_ADDRESS); // that = *(R13 - 1)
        bus.send(stackSegment, ram.getValueAt(Definitions.R13_ADDRESS) - 2, ram, Definitions.THIS_POINTER_ADDRESS); // this = *(R13 - 2)
        bus.send(stackSegment, ram.getValueAt(Definitions.R13_ADDRESS) - 3, ram, Definitions.ARG_POINTER_ADDRESS); // arg = *(R13 - 3)
        bus.send(stackSegment, ram.getValueAt(Definitions.R13_ADDRESS) - 4, ram, Definitions.LOCAL_POINTER_ADDRESS); // lcl = *(R13 - 4)

        // removes the top function from the call stack
        callStack.popFunction();

        // check whether there is a "calling frame"
        if (stackFrames.size() > 0) {
            // retrieve stack frame address of old function
            int frameAddress = ((Integer)stackFrames.lastElement()).intValue();
            stackFrames.removeElementAt(stackFrames.size() - 1);
            workingStackSegment.setStartAddress(frameAddress);

            // disable non relevant range of the local segment - enable only the locals
            // of the function that we returned to.
            localSegment.setEnabledRange(Math.max(localSegment.getStartAddress(), Definitions.STACK_START_ADDRESS), frameAddress - 1, true);

            // enable in the arg segment only the number of args that were sent to the function
            // that we returned to.
            argSegment.setEnabledRange(argSegment.getStartAddress(),
                                       localSegment.getStartAddress() - 6, true);

            // enable this, that according to their retrieved pointers
            thisSegment.setEnabledRange(Math.max(thisSegment.getStartAddress(), Definitions.HEAP_START_ADDRESS), Definitions.HEAP_END_ADDRESS, true);
            thatSegment.setEnabledRange(Math.max(thatSegment.getStartAddress(), Definitions.HEAP_START_ADDRESS), Definitions.SCREEN_END_ADDRESS, true);
        }/* else {
			error("Nowhere to return to");
		} */ // Allow return if we previously had "function" even with no call -
			 // For the SimpleFunction test

        short returnAddress = ram.getValueAt(Definitions.R14_ADDRESS);
		if (returnAddress == VMProgram.BUILTIN_FUNCTION_ADDRESS) {
			staticSegment.setEnabledRange(0, -1, true); // empty static segment
			builtInFunctionsRunner.returnToBuiltInFunction(popValue(METHOD_STACK));
		} else if (returnAddress >= 0 && returnAddress < program.getSize()) {
            // sets the static segment range
			if (stackFrames.size() > 0) {
				setStaticRange(callStack.getTopFunction());
			} else {
				staticSegment.setStartAddress(Definitions.VAR_START_ADDRESS);
				staticSegment.setEnabledRange(Definitions.VAR_START_ADDRESS,
											  Definitions.VAR_END_ADDRESS - 1,
											  true);
			}
			program.setPC((short)(returnAddress-1)); // set previousPC currectly
			program.setPC(returnAddress); // pc = *sp
		} else {
            error("Illegal return address");
		}
    }

	/**
	 * Calls a function according to the given function name
	 * with the given parameters from a built-in function
	 */
	public void callFunctionFromBuiltIn(String functionName, short[] params)
			throws ProgramException {
		// Push the arguments onto the stack
		for (int i=0; i<params.length; ++i) {
			pushValue(METHOD_STACK, params[i]);
		}
		callFunction(program.getAddress(functionName), (short)params.length,
					 functionName, true);
	}
	
    /**
     * Calls a function according to the given function number stating
     * that the given number of arguments have been pushed onto the stack
	 *
	 * If callerIsBuiltIn then the caller is a builtIn function that called
	 * this function through callFunctionFromBuiltIn.
	 * If address is -1 then a native function should be looked up and called.
     */
    public void callFunction(short address, short numberOfArguments, String functionName, boolean callerIsBuiltIn)
     throws ProgramException {
        stackFrames.addElement(new Integer(workingStackSegment.getStartAddress()));
        workingStackSegment.setStartAddress(getSP() + 5);

		if (callerIsBuiltIn) {
			pushValue(MAIN_STACK, VMProgram.BUILTIN_FUNCTION_ADDRESS);
		} else {
			pushValue(MAIN_STACK, program.getPC());
		}
        pushFromRAM(MAIN_STACK, Definitions.LOCAL_POINTER_ADDRESS);
        pushFromRAM(MAIN_STACK, Definitions.ARG_POINTER_ADDRESS);
        pushFromRAM(MAIN_STACK, Definitions.THIS_POINTER_ADDRESS);
        pushFromRAM(MAIN_STACK, Definitions.THAT_POINTER_ADDRESS);
        ram.setValueAt(Definitions.ARG_POINTER_ADDRESS, (short)(getSP() - numberOfArguments - 5), false);
        ram.setValueAt(Definitions.LOCAL_POINTER_ADDRESS, getSP(), false);

        // enable in the arg segment only the number of args that were sent to the called function.
        argSegment.setEnabledRange(argSegment.getStartAddress(),
                                   argSegment.getStartAddress() + numberOfArguments - 1, true);

		if (address == VMProgram.BUILTIN_FUNCTION_ADDRESS) {
			// Perform some actions normally done in the function() method
			localSegment.setEnabledRange(localSegment.getStartAddress(),
										 localSegment.getStartAddress()-1,
										 true); // no local variables
			callStack.pushFunction(functionName + " (built-in)");
			staticSegment.setEnabledRange(0, -1, true); // empty static segment
			// Read parameters from the stack
			short[] params = new short[numberOfArguments];
			for (int i=0; i<numberOfArguments; ++i) {
				params[i] = argSegment.getValueAt(i);
			}
			// Call the built-in implementation
			builtInFunctionsRunner.callBuiltInFunction(functionName, params);
		} else if (address >= 0 || address < program.getSize()) {
			program.setPC(address);
			program.setPC(address); // make sure previouspc isn't pc-1
									// which might happen if the calling
									// function called this function in the
									// last line before the "return" and
									// was declared just before this function.
									// In this case encountering the "function"
									// command will issue an error about
									// "missing return"...
		} else {
            error("Illegal call address");
		}
    }

    /**
     * Sets the static segment range according to the the given function (file) name.
     */
    protected void setStaticRange(String functionName) throws ProgramException {
        int dotLocation = functionName.indexOf(".");
        if (dotLocation == -1)
            throw new ProgramException("Illegal function name: " + functionName);

        String className = functionName.substring(0, dotLocation);
        int[] range = program.getStaticRange(className);
        if (range == null)
            throw new ProgramException("Function name doesn't match class name: " + functionName);

        staticSegment.setStartAddress(range[0]);
        staticSegment.setEnabledRange(range[0], range[1], true);
    }

    // Pops the given number of arguments from the method stack to the calculator,
    // computes the result according to the given operator and pushes the result
    // back into the method stack.
    private void calculate(int numberOfArgs, int operator) throws ProgramException {
        calculator.showCalculator(operator, numberOfArgs);
        popToCalculator(METHOD_STACK, 1);
        if (numberOfArgs > 1)
            popToCalculator(METHOD_STACK, 0);

        calculator.compute(operator);
        pushFromCalculator(METHOD_STACK, 2);
        calculator.hideCalculator();
    }

    // Pushes the given value at the top of the stack and increments sp by 1.
    private void pushValue(int stackID, short value) throws ProgramException {
        short sp = getSP();

        if (stackID == MAIN_STACK)
            stackSegment.setValueAt(sp, value, false);
        else
            workingStackSegment.setValueAt(sp, value, false);

        checkSP((short)(sp + 1));
        setSP((short)(sp + 1));
    }

    // Pushes a value from the RAM at the given index into the appropriate stack.
    private void pushFromRAM(int stackID, int index) throws ProgramException {
        short sp = getSP();
        bus.send(ram, index,
                 (stackID == MAIN_STACK ? stackSegment : workingStackSegment), sp);

        checkSP((short)(sp + 1));
        setSP((short)(sp + 1));
    }

    // Push a value from the calculator at the given index into the appropriate stack.
    private void pushFromCalculator(int stackID, int index) throws ProgramException {
        short sp = getSP();
        bus.send(calculator, index,
                 (stackID == MAIN_STACK ? stackSegment : workingStackSegment), sp);

        checkSP((short)(sp + 1));
        setSP((short)(sp + 1));
    }

    // sends a value from the given segment at the the given index to the appropriate stack (at sp)
    // and increments sp.
    private void pushFromSegment(int stackID, short segmentCode, int index)
     throws ProgramException {
        short sp = getSP();
        MemorySegment segment = (segmentCode == HVMInstructionSet.STATIC_SEGMENT_CODE) ?
                                staticSegment : segments[segmentCode];

        checkSegmentIndex(segment, segmentCode, index);
        bus.send(segment, index, (stackID == MAIN_STACK ? stackSegment : workingStackSegment), sp);

        checkSP((short)(sp + 1));
        setSP((short)(sp + 1));
    }

    // sends a value from the appropriate stack (at sp-1) to the given segment at the given index
    // and increments sp.
    private void popToSegment(int stackID, short segmentCode, int index) throws ProgramException {
        short newSP = (short)(getSP() - 1);
        MemorySegment segment = (segmentCode == HVMInstructionSet.STATIC_SEGMENT_CODE) ?
                                staticSegment : segments[segmentCode];

        checkSegmentIndex(segment, segmentCode, index);
        bus.send((stackID == MAIN_STACK ? stackSegment : workingStackSegment), newSP,
                 segment, index);

        checkSP(newSP);
        setSP(newSP);
    }

    // Pops the a value from the appropriate stack, decrements sp, and returns
	// the popped value.
    private short popValue(int stackID) throws ProgramException {
        short newSP = (short)(getSP() - 1);
		short value;

        if (stackID == MAIN_STACK)
            value = stackSegment.getValueAt(newSP);
        else
            value = workingStackSegment.getValueAt(newSP);

        checkSP(newSP);
        setSP(newSP);

		return value;
    }

    // sends a value from the appropriate stack (at sp-1) to the ram at the given index
    // and increments sp.
    private void popToRAM(int stackID, int index) throws ProgramException {
        short newSP = (short)(getSP() - 1);
        bus.send((stackID == MAIN_STACK ? stackSegment : workingStackSegment), newSP,
                 ram, index);

        checkSP(newSP);
        setSP(newSP);
    }

    // sends a value from the appropriate stack (at sp-1) to the this pointer
    // and increments sp.
    private void popToThisPointer(int stackID) throws ProgramException {
        short value = ram.getValueAt(getSP() - 1);
        if ((value < Definitions.HEAP_START_ADDRESS || value > Definitions.HEAP_END_ADDRESS)
            && value > 0)
            error("'This' segment must be in the Heap range");

        popToRAM(stackID, Definitions.THIS_POINTER_ADDRESS);
        thisSegment.setEnabledRange(value, Definitions.HEAP_END_ADDRESS, true);
    }

    // sends a value from the appropriate stack (at sp-1) to the that pointer
    // and increments sp.
    private void popToThatPointer(int stackID) throws ProgramException {
        short value = ram.getValueAt(getSP() - 1);
        if (!((value >= Definitions.HEAP_START_ADDRESS && value <= Definitions.HEAP_END_ADDRESS) ||
              (value >= Definitions.SCREEN_START_ADDRESS && value <= Definitions.SCREEN_END_ADDRESS) ||
              value == 0))
            error("'That' segment must be in the Heap or Screen range");

        popToRAM(stackID, Definitions.THAT_POINTER_ADDRESS);
        thatSegment.setEnabledRange(value, Definitions.SCREEN_END_ADDRESS, true);
    }

    // sends a value from the appropriate stack (at sp-1) to the calculator at the given index
    // and increments sp.
    private void popToCalculator(int stackID, int index) throws ProgramException {
        short newSP = (short)(getSP() - 1);
        bus.send((stackID == MAIN_STACK ? stackSegment : workingStackSegment), newSP,
                 calculator, index);

        checkSP(newSP);
        setSP(newSP);
    }

    // Returns the element at the top of the stack and decrements sp by 1.
    private short popAndReturn() throws ProgramException {
        short newSP = (short)(getSP() - 1);
        checkSP(newSP);
        setSP(newSP);
        return stackSegment.getValueAt(newSP);
    }

    /**
     * Returns the static segment.
     */
    public MemorySegment getStaticSegment() {
        return staticSegment;
    }

    /**
     * Returns the value at the i'th position in the given segment
     */
    public short getSegmentAt(short segmentCode, short i) {
        return segments[segmentCode].getValueAt(i);
    }

    /**
     * Sets the value at the i'th position in the given segment with the given value.
     */
    public void setSegmentAt(short segmentCode, short i, short value) {
        segments[segmentCode].setValueAt(i, value, false);
    }

    /**
     * Returns the stack pointer
     */
    public short getSP() {
        return ram.getValueAt(Definitions.SP_ADDRESS);
    }

    /**
     * Sets the stack pointer with the given value.
     */
    public void setSP(short value) {
        ram.setValueAt(Definitions.SP_ADDRESS, value, true);
    }

    // Checks the given sp value. If not legal, throws an exception.
    private void checkSP(short sp) throws ProgramException {
        if (sp < Definitions.STACK_START_ADDRESS || sp > Definitions.STACK_END_ADDRESS)
            error("Stack overflow");
    }

    // Verifies that the given index of the given segment is valid.
    private void checkSegmentIndex(MemorySegment segment, int segmentCode, int index)
     throws ProgramException {
        short loc = (short)(index + segment.getStartAddress());

        if (segmentCode == HVMInstructionSet.THIS_SEGMENT_CODE) {
            if (loc < Definitions.HEAP_START_ADDRESS || loc > Definitions.HEAP_END_ADDRESS)
                error("Out of segment space");
        }
        else {
            int[] range = segment.getEnabledRange();
            if (loc < range[0] || loc > range[1]) {
                error("Out of segment space");
            }
        }
    }

    // Throws a program exception with the given message.
    private void error(String message) throws ProgramException {
        throw new ProgramException(message + " in " + callStack.getTopFunction() + "." +
                                   currentInstruction.getIndexInFunction());
    }
}
