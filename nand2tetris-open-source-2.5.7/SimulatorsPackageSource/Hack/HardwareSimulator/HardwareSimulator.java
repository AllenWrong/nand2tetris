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

package Hack.HardwareSimulator;

import Hack.Controller.*;
import Hack.ComputerParts.*;
import Hack.Utilities.*;
import java.io.*;
import Hack.Gates.*;
import Hack.Events.*;

/**
 * A simulator for the Hack Hardware. Simulates chips (in .hdl format).
 *
 * Recognizes the following variables:
 * time - the number of tick-tocks that passed since the program started running.
 *        if clock is up, adds a '+' (String) - READ ONLY
 * Input/Output/Internal pin name
 *
 * Recognizes the following commands:
 * load <gate name> - loads the given gate (from an HDL file) into the simulator
 *					  (the file name should not contain the path and the .HDL extension).
 * tick - Clock goes up (internal state of clocked gates changes).
 * tock - Clock goes down (outputs of clocked gates are modified).
 * eval - propagate all the input values of the gate and re-compute all outputs of the gate.
 */
public class HardwareSimulator extends HackSimulator
 implements TextFileEventListener, GateErrorEventListener,
            DirtyGateListener {

    // Variables
    private static final String VAR_TIME = "time";

    // Commands
    private static final String COMMAND_TICK = "tick";
    private static final String COMMAND_TOCK = "tock";
    private static final String COMMAND_LOAD = "load";
    private static final String COMMAND_EVAL = "eval";
    private static final String COMMAND_SETVAR = "set";

    private static final File INITIAL_BUILTIN_DIR = new File("builtInChips");

    // null value
    private static final short NULL_VALUE = 0;

    // The gui of the simulator.
    private HardwareSimulatorGUI gui;

    // The simulated gate.
    private Gate gate;

    // The Pins lists
    private Pins inputPins, outputPins, internalPins;

    // The Part pins list
    private PartPins partPins;

    // The parts list
    private Parts parts;

    // True if the clock state is up.
    private boolean clockUp;

    // The amount of times the clock went up and down.
    private int time;

    // The current animation mode
    private int animationMode;

    // The list of recognized variables.
    private String[] vars;

    /**
     * Constructs a new Hardware Simulator with no gui.
     */
    public HardwareSimulator() {
        init();
        GatesManager.getInstance().enableChipsGUI(false);
    }

    /**
     * Constructs a new Hardware Simulator with the given gui.
     */
    public HardwareSimulator(HardwareSimulatorGUI gui) {
        this.gui = gui;
        init();

        if (gui.getGatesPanel() != null)
            GatesManager.getInstance().setGatesPanel(gui.getGatesPanel());

        inputPins = new Pins(GateClass.INPUT_PIN_TYPE, gui.getInputPins());
        outputPins = new Pins(GateClass.OUTPUT_PIN_TYPE, gui.getOutputPins());
        internalPins = new Pins(CompositeGateClass.INTERNAL_PIN_TYPE, gui.getInternalPins());

        partPins = new PartPins(gui.getPartPins());
        parts = new Parts(gui.getParts());

        inputPins.enableUserInput();
        inputPins.setNullValue(NULL_VALUE, false);
        inputPins.addErrorListener(this);
        outputPins.disableUserInput();
        outputPins.setNullValue(NULL_VALUE, false);
        internalPins.disableUserInput();
        internalPins.setNullValue(NULL_VALUE, false);
        partPins.setNullValue(NULL_VALUE, false);

        if (gui.getHDLView() != null)
            gui.getHDLView().addTextFileListener(this);

        if (gui.getGateInfo() != null)
            gui.getGateInfo().reset();

        gui.hideInternalPins();
        gui.hidePartPins();
        gui.hideParts();
    }

    // Initializes the hardware simulator
    private void init() {
        Gate.CLOCK_NODE.set((short)1);
        clockUp = false;
        time = 0;
        GatesManager.getInstance().setErrorHandler(this);
        GatesManager.getInstance().setBuiltInDir(INITIAL_BUILTIN_DIR);

        vars = new String[]{VAR_TIME};
    }

    public String getName() {
        return "Hardware Simulator";
    }

    /**
     * Returns the value of the given variable.
     * Throws VariableException if the variable is not legal.
     */
    public String getValue(String varName) throws VariableException {
        String result = null;

        if (gate == null)
            throw new VariableException("cannot get var's value since no gate is currently loaded", varName);

        if (varName.equals(VAR_TIME))
            result = String.valueOf(time) + (clockUp ? "+" : " ");
        else {
            Node node = gate.getNode(varName);
            if (node != null)
                result = String.valueOf(node.get());
            else {
                String gateName = getVarChipName(varName);
                if (gateName != null) {
                    int index = getVarIndex(varName);

                    // try to find if gateName is an internal part with GUI
                    BuiltInGateWithGUI guiChip = getGUIChip(gateName);
                    if (guiChip != null) {
                        try {
                            result = String.valueOf(guiChip.getValueAt(index));
                        } catch (GateException ge) {
                            throw new VariableException(ge.getMessage(), varName);
                        }
                    } else {
						throw new VariableException("No such built-in chip used",
													gateName);
					}
                }

                if (result == null)
                    throw new VariableException("Unknown variable", varName);
            }
        }

        return result;
    }

    // Returns the BuiltInGateWithGUI that matches the given chip name, or
    // null if doesn't exist.
    private BuiltInGateWithGUI getGUIChip(String chipName) {
        BuiltInGateWithGUI chip = null;

        BuiltInGateWithGUI[] gates = GatesManager.getInstance().getChips();
        for (int i = 0; i < gates.length && chip == null; i++)
            if (gates[i].getGateClass().getName().equals(chipName))
                chip = gates[i];

        return chip;
    }

    // Returns the chip name of the given var name.
    // If not legal varName, returns null.
    private String getVarChipName(String varName) {
        String result = null;
        int loc = varName.indexOf("[");
        if (loc >= 0)
            result = varName.substring(0, loc);

        return result;
    }

    // Returns the index of the given var name.
    // throws VariableException if illegal index format.
    private int getVarIndex(String varName) throws VariableException {
        int index;

        if (varName.endsWith("[]"))
            index = 0;
        else {
            try {
                index = Integer.parseInt(varName.substring(varName.indexOf("[") + 1,
                                                           varName.indexOf("]")));
            } catch (Exception nfe) {
                throw new VariableException("Illegal component index", varName);
            }
        }

        return index;
    }

    /**
     * Sets the given variable with the given value.
     * Throws VariableException if the variable name or value are not legal.
     */
    public void setValue(String varName, String value) throws VariableException {
        if (gate == null)
            throw new VariableException("cannot get var's value since no gate is currently loaded", varName);

        short numValue;

        try {
            value = Conversions.toDecimalForm(value);
            numValue = Short.parseShort(value);
        } catch (NumberFormatException nfe) {
            throw new VariableException("'" + value + "' is not a legal value for variable",
                                        varName);
        }

        boolean readOnly = false;

        if (varName.equals(VAR_TIME))
            readOnly = true;
        else {
            Node node = gate.getNode(varName);
            if (node != null) {
                byte type = gate.getGateClass().getPinType(varName);
                if (type != GateClass.INPUT_PIN_TYPE)
                    readOnly = true;
                else {
                    if (!isLegalWidth(varName, numValue))
                        throw new VariableException(value + " doesn't fit in the pin's width",
                                                    varName);
                    else
                        node.set(numValue);
                }
            }
            else {
                boolean found = false;
                String gateName = getVarChipName(varName);
                if (gateName != null) {
                    int index = getVarIndex(varName);

                    // try to find if gateName is an internal part with GUI
                    BuiltInGateWithGUI guiChip = getGUIChip(gateName);
                    if (guiChip != null) {
                        try {
                            guiChip.setValueAt(index, numValue);
                            found = true;
                        } catch (GateException ge) {
                            throw new VariableException(ge.getMessage(), varName);
                        }
                    } else {
						throw new VariableException("No such built-in chip used",
													gateName);
                    }
                }

                if (!found)
                    throw new VariableException("Unknown variable", varName);
            }
        }

        if (readOnly)
            throw new VariableException("Read Only variable", varName);

    }

    /*
     * Returns true if the width of the given value is less or equal to the width
     * of the given pin (name).
     */
    private boolean isLegalWidth(String pinName, short value) {
        byte maxWidth = gate.getGateClass().getPinInfo(pinName).width;
        byte width = (byte)(value > 0 ? (int)(Math.log(value) / Math.log(2)) + 1 : 1);
        return (width <= maxWidth);
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

        // execute the appropriate command
        if (command[0].equals(COMMAND_TICK)) {
            if (command.length != 1)
                throw new CommandException("Illegal number of arguments to command", command);
            else if (gate == null)
                throw new CommandException("Illegal command since no gate is currently loaded", command);
            else if (clockUp)
                throw new CommandException("Illegal command since clock is already up", command);

            performTick();
        }
        else if (command[0].equals(COMMAND_TOCK)) {
            if (command.length != 1)
                throw new CommandException("Illegal number of arguments to command", command);
            else if (gate == null)
                throw new CommandException("Illegal command since no gate is currently loaded", command);
            else if (!clockUp)
                throw new CommandException("Illegal command since clock is already down", command);

            performTock();
        }
        else if (command[0].equals(COMMAND_EVAL)) {
            if (command.length != 1)
                throw new CommandException("Illegal number of arguments to command", command);
            else if (gate == null)
                throw new CommandException("Illegal command since no gate is currently loaded", command);

            performEval();
        }
        else if (command[0].equals(COMMAND_SETVAR)) {
            if (command.length != 3)
                throw new CommandException("Illegal number of arguments to command", command);
            setValue(command[1], command[2]);
        }
        else if (command[0].equals(COMMAND_LOAD)) {
            if (command.length != 2)
                throw new CommandException("Illegal number of arguments to command", command);

            if (gui != null && gui.getGateInfo() != null)
                gui.getGateInfo().setChip(command[1]);

            try {
                if (!command[1].endsWith(".hdl"))
                    throw new CommandException("A .hdl file is expected", command);

                if (command[1].indexOf("/") >= 0)
                    throw new CommandException("The gate name must not contain path specification",
                                               command);

                // use gate name without the .hdl extension
                String gateName = command[1].substring(0, command[1].length() - 4);
                loadGate(gateName, false);
                notifyProgramListeners(ProgramEvent.LOAD, GatesManager.getInstance().getHDLFileName(gateName));
            } catch (GateException ge) {
                throw new CommandException(ge.getMessage(), command);
            }
        }
        else {
            boolean found = false;

            // try to re-direct command to a part with gui
            BuiltInGateWithGUI guiChip = getGUIChip(command[0]);
            if (guiChip != null) {
                found = true;
                String[] newCommand = new String[command.length - 1];
                System.arraycopy(command, 1, newCommand, 0, newCommand.length);
                try {
                    guiChip.doCommand(newCommand);
                } catch (GateException ge) {
                    throw new CommandException(ge.getMessage(), command);
                }
            }

            if (!found)
                throw new CommandException("Unknown command or component name", command);
        }
    }

    public void setWorkingDir(File file) {
        super.setWorkingDir(file);
        GatesManager.getInstance().setWorkingDir(file.getParentFile());
    }

    // Hides all highlights in GUIs.
    private void hideHighlightes() {
        inputPins.hideHighlight();
        outputPins.hideHighlight();
        internalPins.hideHighlight();
        partPins.hideHighlight();
    }

    /**
     * Animation is not valid in the hardware simulator
     */
    public void setAnimationSpeed(int speedUnit) {
    }

    public void refresh() {
        inputPins.refreshGUI();
        outputPins.refreshGUI();
        internalPins.refreshGUI();
        partPins.refreshGUI();
        parts.refreshGUI();
    }

    public void restart() {
        if (gui != null) {
            inputPins.reset();
            outputPins.reset();
            internalPins.reset();
            partPins.reset();
            parts.reset();

            if (gui.getHDLView() != null) {
                gui.getHDLView().hideSelect();
                gui.getHDLView().clearHighlights();
            }

            if (gui.getGateInfo() != null)
                gui.getGateInfo().reset();

            hideHighlightes();
        }

        if (gate != null)
            gate.eval();

        time = 0;
        Gate.CLOCK_NODE.set((short)1);
        clockUp = false;
    }

    public String[] getVariables() {
        return vars;
    }

    public void setAnimationMode(int newAnimationMode) {
        if (gui != null) {
            // enter NO_DISPLAY_CHANGES
            if (newAnimationMode == HackController.NO_DISPLAY_CHANGES &&
                    animationMode != HackController.NO_DISPLAY_CHANGES) {
                inputPins.disableUserInput();
            }

            // exit NO_DISPLAY_CHANGES
            if (newAnimationMode != HackController.NO_DISPLAY_CHANGES &&
                    animationMode == HackController.NO_DISPLAY_CHANGES) {
                inputPins.enableUserInput();
            }

            animationMode = newAnimationMode;

            boolean animate = (animationMode == HackController.ANIMATION);
            inputPins.setAnimate(animate);
            outputPins.setAnimate(animate);
            internalPins.setAnimate(animate);
            partPins.setAnimate(animate);

            boolean displayChanges = (animationMode != HackController.NO_DISPLAY_CHANGES);
            inputPins.setDisplayChanges(displayChanges);
            outputPins.setDisplayChanges(displayChanges);
            internalPins.setDisplayChanges(displayChanges);
            partPins.setDisplayChanges(displayChanges);
        }
    }

    public int getInitialAnimationMode() {
        return HackController.DISPLAY_CHANGES;
    }

    public int getInitialNumericFormat() {
        return HackController.DECIMAL_FORMAT;
    }

    public void setNumericFormat(int formatCode) {
        inputPins.setNumericFormat(formatCode);
        outputPins.setNumericFormat(formatCode);
        internalPins.setNumericFormat(formatCode);
        partPins.setNumericFormat(formatCode);
    }

    public void prepareFastForward() {
    }

    public void prepareGUI() {
    }

    protected HackSimulatorGUI getGUI() {
        return gui;
    }

    // Loads a gate with the given name
    // If containsPath is true, the gateName should contain the full path.
    protected synchronized void loadGate(String gateName, boolean containsPath) throws GateException {

        GateClass gateClass = null;

        if (gui != null)
            displayMessage("Loading chip...", false);

        try {
            // clears the gate cache, so all gates will be reloaded
            GateClass.clearGateCache();

            // find gate class and create gate
            gateClass = GateClass.getGateClass(gateName, containsPath);

            GatesManager.getInstance().removeAllChips();
            Gate oldGate = gate; // save old gate
            gate = gateClass.newInstance(); // create new gate instance

            // register as dirty gate listener (and remove the old one)
            gate.addDirtyGateListener(this);
            if (oldGate != null)
                oldGate.removeDirtyGateListener(this);

            // assign gate's pins to the Pins computer parts
            if (gui != null) {
                inputPins.setNodes(gate.getInputNodes(), gateClass);
                outputPins.setNodes(gate.getOutputNodes(), gateClass);
                if (gateClass instanceof CompositeGateClass) {
                    internalPins.setNodes(((CompositeGate)gate).getInternalNodes(), gateClass);
                    partPins.setGate(gate);
                    parts.setParts(((CompositeGate)gate).getParts());
                }
            }

            restart();

            if (gui != null) {
                if (gui.getGateInfo() != null)
                    gui.getGateInfo().setChip(gateClass.getName());

                notifyListeners(HardwareSimulatorControllerEvent.DISABLE_EVAL, null);
                gui.getOutputPins().setDimmed(false);
                gui.getInternalPins().setDimmed(false);
                if (gateClass.isClocked()) {
                    notifyListeners(HardwareSimulatorControllerEvent.ENABLE_TICKTOCK, null);
                    if (gui.getGateInfo() != null) {
                        gui.getGateInfo().setClocked(gateClass.isClocked());
                        gui.getGateInfo().enableTime();
                    }
                }
                else {
                    notifyListeners(HardwareSimulatorControllerEvent.DISABLE_TICKTOCK, null);
                    if (gui.getGateInfo() != null)
                        gui.getGateInfo().disableTime();
                }

                if (gui.getHDLView() != null) {
                    if (containsPath)
                        gui.getHDLView().setContents(gateName);
                    else
                        gui.getHDLView().setContents(GatesManager.getInstance().getHDLFileName(gateName));
                }

                if (gateClass instanceof BuiltInGateClass)
                    gui.hideInternalPins();
                else
                    gui.showInternalPins();

                gui.hidePartPins();
                gui.hideParts();
            }
        } catch (HDLException he) {
            throw new GateException(he.getMessage());
        } catch (InstantiationException ie) {
            throw new GateException(ie.getMessage());
        }

        if (gui != null)
            clearMessage();
    }

    /**
     * Called when a line was selected in the HDL View.
     */
    public void rowSelected(TextFileEvent event) {
        if (gate instanceof CompositeGate) {

            String line = event.getRowString();
            boolean partPinsLineFound = false;
            boolean partsLineFound = false;

            try {
                HDLTokenizer input = new HDLLineTokenizer(line);
                if (input.hasMoreTokens()) {
                    input.advance();
                    if (input.getTokenType() == HDLTokenizer.TYPE_KEYWORD &&
                         input.getKeywordType() == HDLTokenizer.KW_PARTS)
                        partsLineFound = true;
                    else if (input.getTokenType() == HDLTokenizer.TYPE_IDENTIFIER) {
                        String name = input.getIdentifier();

                        // verify the a '(' follows
                        input.advance();
                        if (input.getTokenType() == HDLTokenizer.TYPE_SYMBOL
                             && input.getSymbol() == '(') {

                            // check if name is a recognized gate
                            if (GateClass.gateClassExists(name)) {
                                GateClass partGateClass = GateClass.getGateClass(name, false);
                                partPins.setPart(partGateClass, name);
                                partPinsLineFound = true;
                                boolean endOfPins = false;

                                // read pin names
                                while (!endOfPins) {
                                    input.advance(); // read left pin name
                                    String leftName = input.getIdentifier();

                                    input.advance(); // read '='

                                    input.advance(); // read right pin name
                                    String rightName = input.getIdentifier();

                                    partPins.addPin(leftName, rightName);

                                    input.advance(); // read ',' or ')'
                                    if (input.getTokenType() == HDLTokenizer.TYPE_SYMBOL &&
                                         input.getSymbol() == ')')
                                        endOfPins = true;
                                }
                            }
                        }
                    }
                }
            } catch (HDLException he) {
                displayMessage(he.getMessage(), true);
            }

            if (partPinsLineFound) {
                gui.hideInternalPins();
                gui.hideParts();
                gui.showPartPins();
            }
            else if (partsLineFound) {
                gui.hideInternalPins();
                gui.hidePartPins();
                gui.showParts();
            }
            else {
                gui.hidePartPins();
                gui.hideParts();
                gui.showInternalPins();
            }
        }
    }

    /**
     * Called when an error occured in a computer part.
     * The event contains the source object and the error message.
     */
    public void computerPartErrorOccured(ComputerPartErrorEvent event) {
        displayMessage(event.getErrorMessage(), true);
    }

    /**
     * Called when an error occured in a gate's component.
     * The event contains the source object and the error message.
     */
    public void gateErrorOccured(GateErrorEvent event) {
        displayMessage(event.getErrorMessage(), true);
    }

     /**
     * Executed when a gate becomes dirty.
     */
    public void gotDirty() {
        notifyListeners(HardwareSimulatorControllerEvent.ENABLE_EVAL, null);
        if (gui != null) {
            gui.getOutputPins().setDimmed(true);
            gui.getInternalPins().setDimmed(true);
        }
    }

    /**
     * Executed when a gate becomes clean.
     */
    public void gotClean() {
        notifyListeners(HardwareSimulatorControllerEvent.DISABLE_EVAL, null);
        if (gui != null) {
            gui.getOutputPins().setDimmed(false);
            gui.getInternalPins().setDimmed(false);
        }
    }

    // Performs eval on the current gate
    private void performEval() {
        gate.eval();
    }

    /**
     * Runs the Eval task.
     */
    protected void runEvalTask() {
        Thread t = new Thread(new EvalTask());
        t.start();
    }

    /**
     * Runs the Tick or Tock tasks, according to the current clock state.
     */
    protected void runTickTockTask() {
        if (clockUp) {
            Thread t = new Thread(new TockTask());
            t.start();
        }
        else {
            Thread t = new Thread(new TickTask());
            t.start();
        }
    }

    // Performs tick on the current gate
    private void performTick() {
        Gate.CLOCK_NODE.set((short)0);
        gate.tick();
        clockUp = true;

        if (gui != null) {
            // hide gui highlights
            if (animationMode != HackController.NO_DISPLAY_CHANGES)
                hideHighlightes();

            updateTime();
        }
    }

    // Performs tick on the current gate
    private void performTock() {
        Gate.CLOCK_NODE.set((short)1);
        gate.tock();
        clockUp = false;
        time++;

        if (gui != null)
            updateTime();
    }

    /**
     * Updates the clock & time in the gui.
     */
    private void updateTime() {
        if (gui != null && gui.getGateInfo() != null) {
            gui.getGateInfo().setClock(clockUp);
            if (!clockUp)
                gui.getGateInfo().setTime(time);
        }
    }

    // receives a variable name of the form xxx[i] and returns the numeric
    // value of i.
    // Throws VariableException if i is negative.
    private static short getIndex(String varName) throws VariableException {
        if (varName.indexOf("]") == -1)
            throw new VariableException("Missing ']'", varName);

        String indexStr = varName.substring(varName.indexOf("[") + 1, varName.indexOf("]"));
        int index = Integer.parseInt(indexStr);
        if (index < 0)
            throw new VariableException("Illegal variable index", varName);

        return (short)index;
    }

    // Returns the given pin name including its sub bus specification.
    public static String getFullPinName(String name, byte[] subBus) {
        StringBuffer result = new StringBuffer(name);

        if (subBus != null
            && !name.equals(CompositeGateClass.TRUE_NODE_INFO.name)
            && !name.equals(CompositeGateClass.FALSE_NODE_INFO.name)) {

            result.append("[");
            result.append(subBus[0]);
            if (subBus[0] != subBus[1])
                result.append(".." + subBus[1]);
            result.append("]");
        }

        return result.toString();
    }

    class EvalTask implements Runnable {
        public void run() {
            performEval();
        }
    }

    class TickTask implements Runnable {
        public void run() {
            performTick();
        }
    }

    class TockTask implements Runnable {
        public void run() {
            performTock();
        }
    }
}
