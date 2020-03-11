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

package Hack.Controller;

import java.util.*;
import Hack.Events.*;
import Hack.ComputerParts.*;
import java.io.*;

/**
 * An abstract base class for a simulator that can be controlled by the Hack Controller.
 */
public abstract class HackSimulator implements ProgramEventListener, ComputerPartErrorEventListener
{
    // The vector of listeners
    private Vector listeners;

    // The vector of program listeners
    private Vector programListeners;

    // The current working dir
    protected File workingDir;

    /**
     * Constructs a new hack simulator.
     */
    public HackSimulator() {
        listeners = new Vector();
        programListeners = new Vector();
    }

    /**
     * Returns the name of the translator.
     */
    public abstract String getName();

    /**
     * Returns the value of the given variable.
     * Throws VariableException if the variable name is not legal.
     */
    public abstract String getValue(String varName) throws VariableException;

    /**
     * Sets the given variable with the given value.
     * Throws VariableException if the variable name or value are not legal.
     */
    public abstract void setValue(String varName, String value) throws VariableException;

    /**
     * Executes the given simulator command (given in args[] style).
     * Throws CommandException if the command is not legal.
     * Throws ProgramException if an error occurs in the program.
     */
    public abstract void doCommand(String[] command)
     throws CommandException, ProgramException, VariableException;

    /**
     * Restarts the simulator.
     */
    public abstract void restart();

    /**
     * Sets the animation mode of the simulator with the given animation mode
     * (out of the possible animation constants in HackController).
     */
    public abstract void setAnimationMode(int animationMode);

    /**
     * Sets the numeric format of the simulator with the given format code
     * (out of the possible format constants in HackController).
     */
    public abstract void setNumericFormat(int formatCode);

    /**
     * Sets the animation speed (in the range 1..HackController.NUMBER_OF_SPEED_UNITS).
     */
    public abstract void setAnimationSpeed(int speedUnit);

    /**
     * Refreshes the contents of the simulator.
     */
    public abstract void refresh();

    /**
     * Prepares the simulator for FastForward.
     */
    public abstract void prepareFastForward();

    /**
     * Prepares the GUI of the simulator.
     * Called after the simulator is added (and displayed) to the controller's frame.
     */
    public abstract void prepareGUI();

    /**
     * Returns the list of the simulator's recognized variables.
     */
    public abstract String[] getVariables();

    /**
     * Returns the initial animation mode of the simulator.
     */
    public int getInitialAnimationMode() {
        return HackController.DISPLAY_CHANGES;
    }

    /**
     * Returns the initial numeric format of the simulator.
     */
    public int getInitialNumericFormat() {
        return HackController.DECIMAL_FORMAT;
    }

    /**
     * Returns the initial additional display of the simulator.
     */
    public int getInitialAdditionalDisplay() {
        return HackController.NO_ADDITIONAL_DISPLAY;
    }

    /**
     * Returns the gui of the hack simulator.
     */
    protected abstract HackSimulatorGUI getGUI();

    /**
     * Opens the load Program window.
     */
    protected void loadProgram() {
        getGUI().loadProgram();
    }

    /**
     * Sets the working dir.
     */
    public void setWorkingDir(File file) {
        File parent = file.getParentFile();
        workingDir = file.isDirectory() ? file : parent;

        HackSimulatorGUI gui = getGUI();
        if (gui != null)
            getGUI().setWorkingDir(parent);
    }

    /**
     * Displays the given message, according to the given type.
     */
    protected void displayMessage(String message, boolean error) {
        if (error)
            notifyListeners(ControllerEvent.DISPLAY_ERROR_MESSAGE, message);
        else
            notifyListeners(ControllerEvent.DISPLAY_MESSAGE, message);
    }

    /**
     * Clears the message display.
     */
    protected void clearMessage() {
        notifyListeners(ControllerEvent.DISPLAY_MESSAGE, "");
    }

    /**
     * Registers the given ControllerrEventListener as a listener to this simulator.
     */
    public void addListener(ControllerEventListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Un-registers the given ControllerEventListener from being a listener to this GUI.
     */
    public void removeListener(ControllerEventListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Notifies all the ControllerEventListeners on an action that was taken in the
     * simulator by creating a ControllerEvent (with the action code and supplied data)
     * and sending it using the actionPerformed method to all the listeners.
     */
    public void notifyListeners(byte action, Object data) {
        ControllerEvent event = new ControllerEvent(this, action, data);

        for (int i = 0; i < listeners.size(); i++)
            ((ControllerEventListener)listeners.elementAt(i)).actionPerformed(event);
    }

    /**
     * Registers the given ProgramEventListener as a listener to this GUI.
     */
    public void addProgramListener(ProgramEventListener listener) {
        programListeners.add(listener);
    }

    /**
     * Un-registers the given ProgramEventListener from being a listener to this GUI.
     */
    public void removeProgramListener(ProgramEventListener listener) {
        programListeners.remove(listener);
    }

    /**
     * Notifies all the ProgramEventListeners on a change in the current program by creating
     * a ProgramEvent (with the new event type and program's file name) and sends it using the
     * programChanged method to all the listeners.
     */
    protected void notifyProgramListeners(byte eventType, String programFileName) {
        ProgramEvent event = new ProgramEvent(this, eventType, programFileName);

        for (int i = 0; i < programListeners.size(); i++) {
            ((ProgramEventListener)programListeners.elementAt(i)).programChanged(event);
        }
    }

    public void programChanged(ProgramEvent event) {
        notifyProgramListeners(event.getType(), event.getProgramFileName());
    }
}
