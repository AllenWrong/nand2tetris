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

package Hack.Gates;

import java.awt.*;
import Hack.Events.*;
import java.util.*;

/**
 * A BuiltInGate with a GUI component.
 * Notifies its listeners on errors using the GateErrorEvent.
 * Also listens to ErrorEvents from the GUI (and therefore should register
 * as an ErrorEventListener to it). When such an event occures, the error is sent to
 * the error listeners of the computer part itself.
 */
public abstract class BuiltInGateWithGUI extends BuiltInGate
 implements ErrorEventListener {

    private Vector errorListeners;

    // The gate parent of this gate. re-evaluates when a change is done through the gui.
    protected Gate parent;

    /**
     * Constructs a new BuiltInGateWithGUI.
     */
    public BuiltInGateWithGUI() {
        errorListeners = new Vector();
    }

    /**
     * Sets the gate parent of this gate.
     * The given gate will be re-evaluated when the output of this gate changes.
     */
    protected void setParent(Gate gate) {
        parent = gate;
    }

    /**
     * Evaluates the parent of this gate.
     * Should be executed whenever a change is done to the gate through its gui (after
     * the gate's outputs were set).
     */
    protected void evalParent() {
        parent.setDirty();
        parent.eval();
    }

    /**
     * Registers the given GateErrorEventListener as a listener to this simulator.
     */
    public void addErrorListener(GateErrorEventListener listener) {
        errorListeners.addElement(listener);
    }

    /**
     * Un-registers the given GateErrorEventListener from being a listener to this GUI.
     */
    public void removeErrorListener(GateErrorEventListener listener) {
        errorListeners.removeElement(listener);
    }

    /**
     * Notifies all the GateErrorEventListeners on an error that occured in the
     * computer part by creating a GateErrorEvent (with the error message) and sending
     * it using the gateErrorOccured method to all the listeners.
     */
    public void notifyErrorListeners(String errorMessage) {
        GateErrorEvent event = new GateErrorEvent(this, errorMessage);

        for (int i = 0; i < errorListeners.size(); i++)
            ((GateErrorEventListener)errorListeners.elementAt(i)).gateErrorOccured(event);
    }

    /**
     * Clears all the GateErrorEventListeners from errors.
     */
    public void clearErrorListeners() {
        GateErrorEvent event = new GateErrorEvent(this, null);

        for (int i = 0; i < errorListeners.size(); i++)
            ((GateErrorEventListener)errorListeners.elementAt(i)).gateErrorOccured(event);
    }

    /**
     * Called when an error occured in the GUI.
     * The event contains the source object and the error message.
     */
    public void errorOccured(ErrorEvent event) {
        notifyErrorListeners(event.getErrorMessage());
    }

    /**
     * Returns the GUI component of the chip.
     */
    public abstract Component getGUIComponent();

    /**
     * Returns the value of the chip at the given index.
     * Throws GateException if index is not legal.
     */
    public abstract short getValueAt(int index) throws GateException;

    /**
     * Sets the value at the given index with the value.
     */
    public abstract void setValueAt(int index, short value) throws GateException;

    /**
     * Executes the given command, given in args[] style.
     * Subclasses may override this method to implement commands.
     */
    public void doCommand(String[] command) throws GateException {
        throw new GateException("This chip supports no commands");
    }
}
