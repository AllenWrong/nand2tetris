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

package builtInChips;

import Hack.Gates.*;
import Hack.ComputerParts.*;
import HackGUI.*;
import java.awt.*;

/**
 * RAM chip of a variable size, each memory location is 16 bit-wide.
 * The output is the value stored at the memory location specified by address.
 * If load=1, loads the input into the memory location specified by address (this
 * loaded value will be available starting from the next time step.)
 * The chip contains a gui that displays the memory contents and enables the user
 * to change them.
 * Used as a base class for all RAM chips.
 */
public abstract class RAM extends BuiltInGateWithGUI implements ComputerPartEventListener {

    // The memory array.
    protected short[] values;

    // The gui of the memory.
    protected PointedMemoryComponent memoryGUI;

    /**
     * Constructs a new RAM of the given size.
     */
    public RAM(int size) {
        values = new short[size];

        if (GatesManager.getInstance().isChipsGUIEnabled()) {
            memoryGUI = new PointedMemoryComponent();
            memoryGUI.setContents(values);
            memoryGUI.setVisibleRows(8);
            memoryGUI.setLocation(166,10);
            memoryGUI.addListener(this);
            memoryGUI.addErrorListener(this);
        }
    }

    protected void clockUp() {
        short in = inputPins[0].get();
        short load = inputPins[1].get();
        short address = inputPins[2].get();
        if (load == 1) {
            values[address] = in;
            if (memoryGUI != null)
                memoryGUI.setValueAt(address, in);
        }
    }

    protected void reCompute() {
        short address = inputPins[2].get();
        outputPins[0].set(values[address]);
        if (memoryGUI != null)
           memoryGUI.setPointer(address);
    }

    protected void clockDown() {
        reCompute();
    }

    public Component getGUIComponent() {
        return memoryGUI;
    }

    /**
     * Called when the contents of the memory are changed through the memory gui.
     */
    public void valueChanged(ComputerPartEvent event) {
        short newValue = event.getValue();
        int newAddress = event.getIndex();
        clearErrorListeners();
        updateValue(newAddress, newValue);
    }

    // updates the given value
    private void updateValue(int address, short value) {
        values[address] = value;
        if (memoryGUI != null)
            memoryGUI.setValueAt(address, value);
        reCompute();
        evalParent();
    }

    public void guiGainedFocus() {
    }

    public short getValueAt(int index) throws GateException {
        checkIndex(index);
        return values[index];
    }

    // checks the given index. If illegal throws GateException.
    private void checkIndex(int index) throws GateException {
        if (index < 0 || index >= values.length)
            throw new GateException("Illegal index");
    }

    public void setValueAt(int index, short value) throws GateException {
        checkIndex(index);
        updateValue(index, value);
    }
}
