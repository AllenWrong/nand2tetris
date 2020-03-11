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

import Hack.ComputerParts.*;
import Hack.Gates.*;

/**
 * Represents a collection of pins, using the Nodes implementation.
 * Enables changing pins values.
 */
public class Pins extends InteractiveValueComputerPart {

    // The gui of the Pins
    private PinsGUI gui;

    // The type of this pin (out of the type constants in GateClass)
    private byte type;

    // The nodes array
    private Node[] nodes;

    // The array of pins
    private PinInfo[] pins;

    /**
     * Constructs a new Pins with the given pin type and Pins GUI.
     */
    public Pins(byte type, PinsGUI gui) {
        super (gui != null);
        this.gui = gui;
        this.type = type;

        pins = new PinInfo[0];
        nodes = new Node[0];

        if (hasGUI) {
            gui.addListener(this);
            gui.addErrorListener(this);
            gui.setContents(pins);
        }
    }

    /**
     * Sets the nodes with the given nodes array according to the given GateClass.
     */
    public void setNodes(Node[] nodes, GateClass gateClass) {
        this.nodes = nodes;
        pins = new PinInfo[nodes.length];
        for (int i = 0; i < pins.length; i++) {
            pins[i] = gateClass.getPinInfo(type, i);
            pins[i].value = (short)nodes[i].get();

            nodes[i].addListener(new NodePinsAdapter(this, i));
        }

        if (hasGUI)
            gui.setContents(pins);
    }

    /**
     * Returns the Info for the pin at the given index.
     */
    public PinInfo getPinInfo(int index) {
        return pins[index];
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    public void doSetValueAt(int index, short value) {
        nodes[index].set(value);
    }

    public short getValueAt(int index) {
        return (short)nodes[index].get();
    }

    public void refreshGUI() {
        if (displayChanges) {
            for (int i = 0; i < pins.length; i++)
                pins[i].value = (short)nodes[i].get();
            gui.setContents(pins);
        }
    }

    public void reset() {
        gui.reset();
        for (int i = 0; i < nodes.length; i++)
            nodes[i].set((short)0);
        refreshGUI();
    }

    /**
     * Returns the number of pins.
     */
    public int getCount() {
        return nodes.length;
    }

    /**
     * Returns true if the width of the given value is less or equal to the width
     * of the pin at the given index.
     */
    public boolean isLegalWidth(int pinIndex, short value) {
        int maxWidth = pins[pinIndex].width;
        int width = value > 0 ? (int)(Math.log(value) / Math.log(2)) + 1 : 1;
        return (width <= maxWidth);
    }

    /**
     * Called when a value of a pin was changed.
     */
    public void valueChanged(ComputerPartEvent event) {
        clearErrorListeners();
        int index = event.getIndex();
        short value = event.getValue();
        if (isLegalWidth(index, value))
            setValueAt(index, value, true);
        else {
            notifyErrorListeners("Value doesn't match the pin's width");
            quietUpdateGUI(index, (short)nodes[event.getIndex()].get());
        }
    }
}
