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

import java.util.*;

/**
 * A chip instance.
 */
public abstract class Gate {

    /**
     * The special "true" node.
     */
    public static final Node TRUE_NODE = new Node((short)-1);

    /**
     * The special "false" node.
     */
    public static final Node FALSE_NODE = new Node((short)0);

    /**
     * The special "clock" node.
     */
    public static final Node CLOCK_NODE = new Node();

    // the input pins
    protected Node[] inputPins;

    // the output pins
    protected Node[] outputPins;

    // the class of this gate
    protected GateClass gateClass;

    // true if the inputs to this gate changed since the last re-computation.
    protected boolean isDirty;

    // A list of listeners to the isDirty property.
    private Vector dirtyGateListeners;

    /**
     * Adds the given listener as a listener to the isDirty property.
     */
    public void addDirtyGateListener(DirtyGateListener listener) {
        if (dirtyGateListeners == null)
            dirtyGateListeners = new Vector(1, 1);

        dirtyGateListeners.add(listener);
    }

    /**
     * Removes the given listener from being a listener to the isDirty property.
     */
    public void removeDirtyGateListener(DirtyGateListener listener) {
        if (dirtyGateListeners != null)
            dirtyGateListeners.remove(listener);
    }

    /**
     * Re-computes the values of all output pins according to the gate's functionality.
     */
    protected abstract void reCompute();

    /**
     * Updates the internal state of a clocked gate according to the gate's functionality.
     * (outputs are not updated).
     */
    protected abstract void clockUp();

    /**
     * Updates the outputs of the gate according to its internal state.
     */
    protected abstract void clockDown();

    /**
     * Marks the gate as "dirty" - needs to be recomputed.
     */
    public void setDirty() {
        isDirty = true;

        // notify listeners
        if (dirtyGateListeners != null)
            for (int i = 0; i < dirtyGateListeners.size(); i++)
                ((DirtyGateListener)dirtyGateListeners.elementAt(i)).gotDirty();
    }

    /**
     * Returns the GateClass of this gate.
     */
    public GateClass getGateClass() {
        return gateClass;
    }

    /**
     * Returns the node according to the given node name (may be an input or an output).
     * If doesn't exist, returns null.
     */
    public Node getNode(String name) {
        Node result = null;

        byte type = gateClass.getPinType(name);
        int index = gateClass.getPinNumber(name);
        switch (type) {
            case GateClass.INPUT_PIN_TYPE:
                result = inputPins[index];
                break;
            case GateClass.OUTPUT_PIN_TYPE:
                result = outputPins[index];
                break;
        }

        return result;
    }

    /**
     * Returns the input pins.
     */
    public Node[] getInputNodes() {
        return inputPins;
    }

    /**
     * Returns the output pins.
     */
    public Node[] getOutputNodes() {
        return outputPins;
    }

    /**
     * Recomputes the gate's outputs if inputs changed since the last computation.
     */
    public void eval() {
        if (isDirty)
            doEval();
    }

    /**
     * Recomputes the gate's outputs.
     */
    private void doEval() {
        if (isDirty) {
            isDirty = false;

            // notify listeners
            if (dirtyGateListeners != null)
                for (int i = 0; i < dirtyGateListeners.size(); i++)
                    ((DirtyGateListener)dirtyGateListeners.elementAt(i)).gotClean();
        }

        reCompute();
    }

    /**
     * First computes the gate's output (from non-clocked information) and then updates
     * the internal state of the gate (which doesn't affect the outputs)
     */
    public void tick() {
        doEval();
        clockUp();
    }

    /**
     * First updates the gate's outputs according to the internal state of the gate, and
     * then computes the outputs from non-clocked information.
     */
    public void tock() {
        clockDown();
        doEval();
    }

}
