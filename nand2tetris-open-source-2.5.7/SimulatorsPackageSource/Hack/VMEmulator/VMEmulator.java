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

import java.io.*;
import Hack.ComputerParts.*;
import Hack.Utilities.*;
import Hack.Controller.*;
import Hack.Events.*;
import Hack.Utilities.*;
import Hack.CPUEmulator.*;
import Hack.VirtualMachine.*;

/**
 * A virtual machine emulator. Emulates virtual machine code (in VM format).
 *
 * Recognizes the following variables:
 * RAM[i] - the contents of the RAM at location i (short)
 * sp - the value of the stack pointer (short)
 * local - the address of the Local segment (short)
 * local[i] - the contents of the i'th element in the Local segment (short)
 * argument - the address of the Argument segment (short)
 * argument[i] - the contents of the i'th element in the Argument segment (short)
 * this - the address of the This segment (short)
 * this[i] - the contents of the i'th element in the This segment (short)
 * that - the address of the That segment (short)
 * that[i] - the contents of the i'th element in the That segment (short 0..7)
 * temp[i] - the contents of the i'th element in the Temp segment (short)
 * currentFunction - the name of the current function (String) - READ ONLY
 * line - <function name>.<index in function> (String) - READ ONLY
 */
public class VMEmulator extends HackSimulator
 implements ComputerPartErrorEventListener {

	private static final File INITIAL_BUILTIN_DIR = new File("builtInVMCode");
    // variables
    private static final String VAR_SP = "sp";
    private static final String VAR_RAM = "RAM";
    private static final String VAR_LOCAL = "local";
    private static final String VAR_ARGUMENT = "argument";
    private static final String VAR_THIS = "this";
    private static final String VAR_THAT = "that";
    private static final String VAR_TEMP = "temp";
    private static final String VAR_LINE = "line";
    private static final String VAR_CURRENT_FUNCTION = "currentFunction";

    // Commands
    private static final String COMMAND_VMSTEP = "vmstep";
    private static final String COMMAND_ROMLOAD = "load";
    private static final String COMMAND_SETVAR = "set";

    // The CPU
    private CPU cpu;

    // The gui of the emulator
    private VMEmulatorGUI gui;

    // The list of recognized variables.
    private String[] vars;

    // The keyboard
    private Keyboard keyboard;

    // The current animation mode
    private int animationMode;

    /**
     * Constructs a new VM Emulator with no GUI component.
     */
    public VMEmulator() {
        VMProgram program = new VMProgram(null);

        MemorySegment[][] segments = new MemorySegment[Definitions.RAM_SIZE][];

        RAM ram = new RAM(null, segments, null);
        ram.addErrorListener(this);
        ram.reset();

        // assignes the memory segments
        AbsolutePointedMemorySegment stackSegment =
            new AbsolutePointedMemorySegment(ram, null);
        TrimmedAbsoluteMemorySegment workingStackSegment =
            new TrimmedAbsoluteMemorySegment(ram, null);
        MemorySegment staticSegment =
            new MemorySegment(ram, null);
        MemorySegment localSegment =
            new MemorySegment(ram, null);
        MemorySegment argSegment =
            new MemorySegment(ram, null);
        MemorySegment thisSegment =
            new MemorySegment(ram, null);
        MemorySegment thatSegment =
            new MemorySegment(ram, null);
        MemorySegment tempSegment =
            new MemorySegment(ram, null);

        stackSegment.reset();
        stackSegment.addErrorListener(this);
        stackSegment.setEnabledRange(Definitions.STACK_START_ADDRESS,
                                     Definitions.STACK_END_ADDRESS, true);

        workingStackSegment.reset();
        workingStackSegment.addErrorListener(this);
        workingStackSegment.setEnabledRange(Definitions.STACK_START_ADDRESS,
                                            Definitions.STACK_END_ADDRESS, true);

        staticSegment.reset();
        staticSegment.addErrorListener(this);

        localSegment.reset();
        localSegment.addErrorListener(this);

        argSegment.reset();
        argSegment.addErrorListener(this);

        thisSegment.reset();
        thisSegment.addErrorListener(this);

        thatSegment.reset();
        thatSegment.addErrorListener(this);

        tempSegment.reset();
        tempSegment.addErrorListener(this);
        tempSegment.setStartAddress(Definitions.TEMP_START_ADDRESS);
        tempSegment.setEnabledRange(5, 12, true);

        segments[Definitions.SP_ADDRESS] = new MemorySegment[]{stackSegment, workingStackSegment};
        segments[Definitions.LOCAL_POINTER_ADDRESS] = new MemorySegment[]{localSegment};
        segments[Definitions.ARG_POINTER_ADDRESS] = new MemorySegment[]{argSegment};
        segments[Definitions.THIS_POINTER_ADDRESS] = new MemorySegment[]{thisSegment};
        segments[Definitions.THAT_POINTER_ADDRESS] = new MemorySegment[]{thatSegment};

        keyboard = new Keyboard(ram, null);
        keyboard.reset();

        CallStack callStack = new CallStack(null);
        callStack.reset();

        Calculator calculator = new Calculator(null);
        calculator.reset();

        Bus bus = new Bus(null);
        bus.reset();

        cpu = new CPU(program, ram, callStack, calculator, bus, stackSegment,
					  workingStackSegment, staticSegment, localSegment,
					  argSegment, thisSegment, thatSegment, tempSegment,
					  INITIAL_BUILTIN_DIR);

        cpu.boot();

        init();
    }

    /**
     * Constructs a new VM Emulator with the given GUI component.
     */
    public VMEmulator(VMEmulatorGUI gui) {
        this.gui = gui;

        VMProgram program = new VMProgram(gui.getProgram());
        program.addErrorListener(this);
        program.addProgramListener(this);

        MemorySegment[][] segments = new MemorySegment[Definitions.RAM_SIZE][];

        RAM ram = new RAM(gui.getRAM(), segments, gui.getScreen());
        ram.addErrorListener(this);
        ram.reset();

        // sets ram labels
        LabeledPointedMemoryGUI ramGUI = gui.getRAM();
        ramGUI.setLabel(Definitions.SP_ADDRESS, Definitions.SP_NAME);
        ramGUI.setLabel(Definitions.LOCAL_POINTER_ADDRESS, Definitions.LOCAL_POINTER_NAME);
        ramGUI.setLabel(Definitions.ARG_POINTER_ADDRESS, Definitions.ARG_POINTER_NAME);
        ramGUI.setLabel(Definitions.THIS_POINTER_ADDRESS, Definitions.THIS_POINTER_NAME);
        ramGUI.setLabel(Definitions.THAT_POINTER_ADDRESS, Definitions.THAT_POINTER_NAME);
        ramGUI.setLabel(Definitions.R5_ADDRESS, "Temp0");
        ramGUI.setLabel(Definitions.R6_ADDRESS, "Temp1");
        ramGUI.setLabel(Definitions.R7_ADDRESS, "Temp2");
        ramGUI.setLabel(Definitions.R8_ADDRESS, "Temp3");
        ramGUI.setLabel(Definitions.R9_ADDRESS, "Temp4");
        ramGUI.setLabel(Definitions.R10_ADDRESS, "Temp5");
        ramGUI.setLabel(Definitions.R11_ADDRESS, "Temp6");
        ramGUI.setLabel(Definitions.R12_ADDRESS, "Temp7");
        ramGUI.setLabel(Definitions.R13_ADDRESS, Definitions.R13_NAME);
        ramGUI.setLabel(Definitions.R14_ADDRESS, Definitions.R14_NAME);
        ramGUI.setLabel(Definitions.R15_ADDRESS, Definitions.R15_NAME);

        // assignes the memory segments
        AbsolutePointedMemorySegment stackSegment =
            new AbsolutePointedMemorySegment(ram, gui.getStack());
        TrimmedAbsoluteMemorySegment workingStackSegment =
            new TrimmedAbsoluteMemorySegment(ram, gui.getWorkingStack());
        MemorySegment staticSegment =
            new MemorySegment(ram, gui.getStaticSegment());
        MemorySegment localSegment =
            new MemorySegment(ram, gui.getLocalSegment());
        MemorySegment argSegment =
            new MemorySegment(ram, gui.getArgSegment());
        MemorySegment thisSegment =
            new MemorySegment(ram, gui.getThisSegment());
        MemorySegment thatSegment =
            new MemorySegment(ram, gui.getThatSegment());
        MemorySegment tempSegment =
            new MemorySegment(ram, gui.getTempSegment());

        stackSegment.reset();
        stackSegment.setEnabledRange(Definitions.STACK_START_ADDRESS,
                                     Definitions.STACK_END_ADDRESS, true);
        stackSegment.addErrorListener(this);

        workingStackSegment.reset();
        workingStackSegment.setEnabledRange(Definitions.STACK_START_ADDRESS,
                                            Definitions.STACK_END_ADDRESS, true);
        workingStackSegment.addErrorListener(this);

        staticSegment.reset();
        staticSegment.addErrorListener(this);

        localSegment.reset();
        localSegment.addErrorListener(this);

        argSegment.reset();
        argSegment.addErrorListener(this);

        thisSegment.reset();
        thisSegment.addErrorListener(this);

        thatSegment.reset();
        thatSegment.addErrorListener(this);

        tempSegment.reset();
        tempSegment.setStartAddress(Definitions.TEMP_START_ADDRESS);
        tempSegment.setEnabledRange(5, 12, true);
        tempSegment.addErrorListener(this);

        segments[Definitions.SP_ADDRESS] = new MemorySegment[]{stackSegment, workingStackSegment};
        segments[Definitions.LOCAL_POINTER_ADDRESS] = new MemorySegment[]{localSegment};
        segments[Definitions.ARG_POINTER_ADDRESS] = new MemorySegment[]{argSegment};
        segments[Definitions.THIS_POINTER_ADDRESS] = new MemorySegment[]{thisSegment};
        segments[Definitions.THAT_POINTER_ADDRESS] = new MemorySegment[]{thatSegment};

        keyboard = new Keyboard(ram, gui.getKeyboard());
        keyboard.reset();

        CallStack callStack = new CallStack(gui.getCallStack());
        callStack.reset();

        Calculator calculator = new Calculator(gui.getCalculator());
        calculator.hideCalculator();
        calculator.reset();

        Bus bus = new Bus(gui.getBus());
        bus.reset();

        cpu = new CPU(program, ram, callStack, calculator, bus, stackSegment,
					  workingStackSegment, staticSegment, localSegment,
					  argSegment, thisSegment, thatSegment, tempSegment,
					  INITIAL_BUILTIN_DIR);

        cpu.boot();

        init();
    }

    // Initializes the emulator.
    private void init() {
        vars = new String[]{VAR_SP, VAR_CURRENT_FUNCTION, VAR_LINE, VAR_RAM + "[]",
                            VAR_LOCAL, VAR_LOCAL + "[]", VAR_ARGUMENT, VAR_ARGUMENT + "[]",
                            VAR_THIS, VAR_THIS + "[]", VAR_THAT, VAR_THAT + "[]",
                            VAR_TEMP + "[]", VAR_RAM + "[]"};
    }

    public String getName() {
        return "Virtual Machine Emulator";
    }

    /**
     * Returns the value of the given variable.
     * Throws VariableException if the variable is not legal.
     */
    public String getValue(String varName) throws VariableException {
        if (varName.equals(VAR_LOCAL))
            return String.valueOf(cpu.getRAM().getValueAt(Definitions.LOCAL_POINTER_ADDRESS));
        else if (varName.equals(VAR_ARGUMENT))
            return String.valueOf(cpu.getRAM().getValueAt(Definitions.ARG_POINTER_ADDRESS));
        else if (varName.equals(VAR_THIS))
            return String.valueOf(cpu.getRAM().getValueAt(Definitions.THIS_POINTER_ADDRESS));
        else if (varName.equals(VAR_THAT))
            return String.valueOf(cpu.getRAM().getValueAt(Definitions.THAT_POINTER_ADDRESS));
        else if (varName.equals(VAR_SP))
            return String.valueOf(cpu.getSP());
        else if (varName.equals(VAR_CURRENT_FUNCTION))
            return cpu.getCallStack().getTopFunction();
        else if (varName.equals(VAR_LINE))
            return String.valueOf(cpu.getCallStack().getTopFunction() + "." +
                                  cpu.getCurrentInstruction().getIndexInFunction());
        else if (varName.startsWith(VAR_LOCAL + "[")) {
            short index = getRamIndex(varName);
            return String.valueOf(cpu.getSegmentAt(HVMInstructionSet.LOCAL_SEGMENT_CODE, index));
        }
        else if (varName.startsWith(VAR_ARGUMENT + "[")) {
            short index = getRamIndex(varName);
            return String.valueOf(cpu.getSegmentAt(HVMInstructionSet.ARG_SEGMENT_CODE, index));
        }
        else if (varName.startsWith(VAR_THIS + "[")) {
            short index = getRamIndex(varName);
            return String.valueOf(cpu.getSegmentAt(HVMInstructionSet.THIS_SEGMENT_CODE, index));
        }
        else if (varName.startsWith(VAR_THAT + "[")) {
            short index = getRamIndex(varName);
            return String.valueOf(cpu.getSegmentAt(HVMInstructionSet.THAT_SEGMENT_CODE, index));
        }
        else if (varName.startsWith(VAR_TEMP + "[")) {
            short index = getRamIndex(varName);
            return String.valueOf(cpu.getSegmentAt(HVMInstructionSet.TEMP_SEGMENT_CODE, index));
        }
        else if (varName.startsWith(VAR_RAM + "[")) {
            short index = getRamIndex(varName);
            return String.valueOf(cpu.getRAM().getValueAt(index));
        }
        else
            throw new VariableException("Unknown variable", varName);
    }

    /**
     * Sets the given variable with the given value.
     * Throws VariableException if the variable name or value are not legal.
     */
    public void setValue(String varName, String value) throws VariableException {
        int numValue;

        try {
            value = Conversions.toDecimalForm(value);
            if (varName.equals(VAR_LOCAL)) {
                numValue = Integer.parseInt(value);
                check_address(varName, numValue);
                cpu.getRAM().setValueAt(Definitions.LOCAL_POINTER_ADDRESS, (short)numValue, false);
                if (gui != null)
                    gui.getLocalSegment().setEnabledRange(numValue, Definitions.STACK_END_ADDRESS, true);
            }
            else if (varName.equals(VAR_ARGUMENT)) {
                numValue = Integer.parseInt(value);
                check_address(varName, numValue);
                cpu.getRAM().setValueAt(Definitions.ARG_POINTER_ADDRESS, (short)numValue, false);
                if (gui != null)
                    gui.getArgSegment().setEnabledRange(numValue, Definitions.STACK_END_ADDRESS, true);
            }
            else if (varName.equals(VAR_THIS)) {
                numValue = Integer.parseInt(value);
                check_address(varName, numValue);
                cpu.getRAM().setValueAt(Definitions.THIS_POINTER_ADDRESS, (short)numValue, false);
                if (gui != null)
                    gui.getThisSegment().setEnabledRange(numValue, Definitions.HEAP_END_ADDRESS, true);
            }
            else if (varName.equals(VAR_THAT)) {
                numValue = Integer.parseInt(value);
                check_address(varName, numValue);
                cpu.getRAM().setValueAt(Definitions.THAT_POINTER_ADDRESS, (short)numValue, false);
                if (gui != null)
                    gui.getThatSegment().setEnabledRange(numValue, Definitions.SCREEN_END_ADDRESS, true);
            }
            else if (varName.equals(VAR_SP)) {
                numValue = Integer.parseInt(value);
                check_address(varName, numValue);
                cpu.setSP((short)numValue);
            }
            else if (varName.equals(VAR_CURRENT_FUNCTION))
                throw new VariableException("Read Only variable", varName);
            else if (varName.equals(VAR_LINE)) {
                numValue = Integer.parseInt(value);
                if (numValue >= cpu.getProgram().getSize())
                    throw new VariableException("Line " + value + "is not within the program range",
                                                varName);
                cpu.getProgram().setPC((short)numValue);
            }
            else if (varName.startsWith(VAR_LOCAL + "[")) {
                short index = getRamIndex(varName);
                numValue = Integer.parseInt(value);
                check_value(varName, numValue);
                cpu.setSegmentAt(HVMInstructionSet.LOCAL_SEGMENT_CODE, index, (short)numValue);
            }
            else if (varName.startsWith(VAR_ARGUMENT + "[")) {
                short index = getRamIndex(varName);
                numValue = Integer.parseInt(value);
                check_value(varName, numValue);
                cpu.setSegmentAt(HVMInstructionSet.ARG_SEGMENT_CODE, index, (short)numValue);
            }
            else if (varName.startsWith(VAR_THIS + "[")) {
                short index = getRamIndex(varName);
                numValue = Integer.parseInt(value);
                check_value(varName, numValue);
                cpu.setSegmentAt(HVMInstructionSet.THIS_SEGMENT_CODE, index, (short)numValue);
            }
            else if (varName.startsWith(VAR_THAT + "[")) {
                short index = getRamIndex(varName);
                numValue = Integer.parseInt(value);
                check_value(varName, numValue);
                cpu.setSegmentAt(HVMInstructionSet.THAT_SEGMENT_CODE, index, (short)numValue);
            }
            else if (varName.startsWith(VAR_TEMP + "[")) {
                short index = getRamIndex(varName);
                numValue = Integer.parseInt(value);
                check_value(varName, numValue);
                cpu.setSegmentAt(HVMInstructionSet.TEMP_SEGMENT_CODE, index, (short)numValue);
            }
            else if (varName.startsWith(VAR_RAM + "[")) {
                short index = getRamIndex(varName);
                numValue = Integer.parseInt(value);
                check_address(varName, index);
                cpu.getRAM().setValueAt(index, (short)numValue, false);
            }
            else
                throw new VariableException("Unknown variable", varName);
        } catch (NumberFormatException nfe) {
            throw new VariableException("'" + value + "' is not a legal value for variable",
                                        varName);
        }
    }

    /**
     * Executes the given simulator command (given in args[] style).
     * Throws CommandException if the command is not legal.
     * Throws ProgramException if an error occurs in the program.
     */
    public void doCommand(String[] command)
     throws CommandException, ProgramException, VariableException {
        if (command.length == 0)
            throw new CommandException("Empty command", command);

        // hide gui highlights
        if (animationMode != HackController.NO_DISPLAY_CHANGES)
            hideHighlightes();

        // execute the appropriate command
        if (command[0].equals(COMMAND_VMSTEP)) {
            if (command.length != 1)
                throw new CommandException("Illegal number of arguments to command", command);

            cpu.executeInstruction();
        }
        else if (command[0].equals(COMMAND_SETVAR)) {
            if (command.length != 3)
                throw new CommandException("Illegal number of arguments to command", command);
            setValue(command[1], command[2]);
        }
        else if (command[0].equals(COMMAND_ROMLOAD)) {
            if (command.length != 1 && command.length != 2)
                throw new CommandException("Illegal number of arguments to command", command);

            String fileName = workingDir + (command.length == 1 ? "" : "/" + command[1]);

            cpu.getProgram().loadProgram(fileName);
            cpu.boot();
        }
        else
            throw new CommandException("Unknown simulator command", command);
    }

    // Hides all highlights in GUIs.
    private void hideHighlightes() {
        cpu.getRAM().hideHighlight();
        cpu.getStack().hideHighlight();
        cpu.getWorkingStack().hideHighlight();
        cpu.getCalculator().hideHighlight();
        cpu.getStaticSegment().hideHighlight();

        MemorySegment[] segments = cpu.getMemorySegments();
        for (int i = 0; i < segments.length; i++)
            segments[i].hideHighlight();
    }

    /**
     * Restarts the VMEmulator - program will be restarted.
     */
    public void restart() {
        cpu.getRAM().reset();
        cpu.getCallStack().reset();
        cpu.getProgram().restartProgram();
        cpu.getStack().reset();
        cpu.getWorkingStack().reset();
        cpu.getCalculator().hideCalculator();
        cpu.getCalculator().reset();
        cpu.getStaticSegment().reset();

        MemorySegment[] segments = cpu.getMemorySegments();
        for (int i = 0; i < segments.length; i++)
            segments[i].reset();

        cpu.boot();
    }

    public void setAnimationMode(int newAnimationMode) {

        if (gui != null) {
            // enter NO_DISPLAY_CHANGES
            if (newAnimationMode == HackController.NO_DISPLAY_CHANGES &&
                    animationMode != HackController.NO_DISPLAY_CHANGES) {
                cpu.getRAM().disableUserInput();
                cpu.getStack().disableUserInput();
                cpu.getWorkingStack().disableUserInput();
                cpu.getStaticSegment().disableUserInput();

                MemorySegment[] segments = cpu.getMemorySegments();
                for (int i = 0; i < segments.length; i++)
                    segments[i].disableUserInput();

                ScreenGUI screen = gui.getScreen();
                if (screen != null)
                    screen.startAnimation();
            }

            // exit NO_DISPLAY_CHANGES
            if (newAnimationMode != HackController.NO_DISPLAY_CHANGES &&
                    animationMode == HackController.NO_DISPLAY_CHANGES) {
                cpu.getRAM().enableUserInput();
                cpu.getStack().enableUserInput();
                cpu.getWorkingStack().enableUserInput();
                cpu.getStaticSegment().enableUserInput();

                MemorySegment[] segments = cpu.getMemorySegments();
                for (int i = 0; i < segments.length; i++)
                    segments[i].enableUserInput();

                ScreenGUI screen = gui.getScreen();
                if (screen != null)
                    screen.stopAnimation();
            }
        }

        animationMode = newAnimationMode;

        boolean animate = (animationMode == HackController.ANIMATION);
        cpu.getBus().setAnimate(animate);
        cpu.getRAM().setAnimate(animate);
        cpu.getCallStack().setAnimate(animate);
        cpu.getProgram().setAnimate(animate);
        cpu.getStack().setAnimate(animate);
        cpu.getWorkingStack().setAnimate(animate);
        cpu.getCalculator().setAnimate(animate);
        cpu.getStaticSegment().setAnimate(animate);

        boolean displayChanges = (animationMode != HackController.NO_DISPLAY_CHANGES);
        cpu.getRAM().setDisplayChanges(displayChanges);
        cpu.getCallStack().setDisplayChanges(displayChanges);
        cpu.getProgram().setDisplayChanges(displayChanges);
        cpu.getStack().setDisplayChanges(displayChanges);
        cpu.getWorkingStack().setDisplayChanges(displayChanges);
        cpu.getCalculator().setDisplayChanges(displayChanges);
        cpu.getStaticSegment().setDisplayChanges(displayChanges);

        MemorySegment[] segments = cpu.getMemorySegments();
        for (int i = 0; i < segments.length; i++) {
            segments[i].setDisplayChanges(displayChanges);
            segments[i].setAnimate(animate);
        }
    }

    public int getInitialAnimationMode() {
        return HackController.DISPLAY_CHANGES;
    }

    public int getInitialNumericFormat() {
        return HackController.DECIMAL_FORMAT;
    }

    public void setNumericFormat(int formatCode) {
        cpu.getRAM().setNumericFormat(formatCode);
        cpu.getStack().setNumericFormat(formatCode);
        cpu.getWorkingStack().setNumericFormat(formatCode);
        cpu.getCalculator().setNumericFormat(formatCode);
    }

    public void setAnimationSpeed(int speedUnit) {
        cpu.getBus().setAnimationSpeed(speedUnit);
    }

    public void refresh() {
        cpu.getRAM().refreshGUI();
        cpu.getCallStack().refreshGUI();
        cpu.getProgram().refreshGUI();
        cpu.getStack().refreshGUI();
        cpu.getWorkingStack().refreshGUI();
        cpu.getCalculator().refreshGUI();
        cpu.getStaticSegment().refreshGUI();

        MemorySegment[] segments = cpu.getMemorySegments();
        for (int i = 0; i < segments.length; i++)
            segments[i].refreshGUI();
    }

    public void prepareFastForward() {
        gui.requestFocus();
        keyboard.requestFocus();
    }

    public void prepareGUI() {
    }

    public String[] getVariables() {
        return vars;
    }

    protected HackSimulatorGUI getGUI() {
        return gui;
    }

    /**
     * Called when an error occured in a computer part.
     * The event contains the source object and the error message.
     */
    public void computerPartErrorOccured(ComputerPartErrorEvent event) {
        displayMessage(event.getErrorMessage(), true);
    }

    public void programChanged(ProgramEvent event) {
        super.programChanged(event);

        if (event.getType() == ProgramEvent.LOAD) {
            int oldAnimationMode = animationMode;
            setAnimationMode(HackController.DISPLAY_CHANGES);

            refresh();
            notifyListeners(ControllerEvent.ENABLE_MOVEMENT, null);
            restart();

            setAnimationMode(oldAnimationMode);
        }
    }

    // receives a variable name of the form xxx[i] and returns the numeric
    // value of i, which is an address in the RAM.
    // Throws VariableException if i is not a legal address in the RAM.
    private static short getRamIndex(String varName) throws VariableException {
        if (varName.indexOf("]") == -1)
            throw new VariableException("Missing ']'", varName);

        String indexStr = varName.substring(varName.indexOf("[") + 1, varName.indexOf("]"));
        int index = Integer.parseInt(indexStr);
        if (index < 0 || index >= Definitions.RAM_SIZE)
            throw new VariableException("Illegal variable index", varName);

        return (short)index;
    }

    // Checks that the given value is a legal 16-bit value
    private void check_value(String varName, int value) throws VariableException {
        if (value < -32768 || value >= 32768)
            throw new VariableException(value +
                " is an illegal value for variable", varName);
    }

    // Checks that the given value is a legal 16-bit address
    private void check_address(String varName, int value) throws VariableException {
        if (value < 0 || value >= Definitions.RAM_SIZE)
            throw new VariableException(value +
                " is an illegal value for", varName);
    }
}
