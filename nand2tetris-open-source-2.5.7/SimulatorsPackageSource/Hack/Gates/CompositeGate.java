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

public class CompositeGate extends Gate {

    // the internal pins
    protected Node[] internalPins;

    // The contained parts (Gates), sorted in topological order.
    protected Gate[] parts;

    protected void clockUp() {
        if (gateClass.isClocked)
            for (int i = 0; i < parts.length; i++)
                parts[i].tick();
    }

    protected void clockDown() {
        if (gateClass.isClocked)
            for (int i = 0; i < parts.length; i++)
                parts[i].tock();
    }

    protected void reCompute() {
        for (int i = 0; i < parts.length; i++)
            parts[i].eval();
    }

    /**
     * Returns the node according to the given node name (may be input, output or internal).
     * If doesn't exist, returns null.
     */
    public Node getNode(String name) {
        Node result = super.getNode(name);

        if (result == null) {
            byte type = gateClass.getPinType(name);
            int index = gateClass.getPinNumber(name);
            if (type == CompositeGateClass.INTERNAL_PIN_TYPE)
                result = internalPins[index];
        }

        return result;
    }

    /**
     * Returns the internal pins.
     */
    public Node[] getInternalNodes() {
        return internalPins;
    }

    /**
     * Returns the parts (internal gates) of this gate, sorted in topological order.
     */
    public Gate[] getParts() {
        return parts;
    }

    /**
     * Initializes the gate
     */
    public void init(Node[] inputPins, Node[] outputPins, Node[] internalPins, Gate[] parts,
                     GateClass gateClass) {
        this.inputPins = inputPins;
        this.outputPins = outputPins;
        this.internalPins = internalPins;
        this.parts = parts;
        this.gateClass = gateClass;
        setDirty();
    }

}
