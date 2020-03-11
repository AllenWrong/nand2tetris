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

package Hack.ComputerParts;

import Hack.Events.*;

/**
 * A computer memory.
 */
public class Memory extends InteractiveValueComputerPart implements ClearEventListener {

    // The size of the memory
    protected int size;

    // the memory array
    protected short[] mem;

    // The main gui of the memory.
    protected MemoryGUI gui;

    /**
     * Constructs a new Memory with the given size and (optional) memory GUI.
     */
    public Memory(int size, MemoryGUI gui) {
        super(gui != null);
        init(size, gui);
    }

    /**
     * Constructs a new Memory with the given size, a memory GUI and the
     * legal values range.
     */
    public Memory(int size, MemoryGUI gui, short minValue, short maxValue) {
        super(gui != null, minValue, maxValue);
        init(size, gui);
    }

    // Initializes the memory
    private void init(int size, MemoryGUI gui) {
        this.size = size;
        this.gui = gui;
        mem = new short[size];

        if (hasGUI) {
            gui.setContents(mem);
            gui.addListener(this);
            gui.addClearListener(this);
            gui.addErrorListener(this);
        }
    }

    public short getValueAt(int address) {
        return mem[address];
    }

    public void doSetValueAt(int address, short value) {
        mem[address] = value;
    }

    /**
     * Returns the contents of the memory as a an array.
     */
    public short[] getContents() {
        return mem;
    }

    /**
     * Puts the given contents array in the memory, starting from the given address.
     * (Assumes that the contents fits)
     */
    public void setContents(short[] contents, int startAddress) {
        System.arraycopy(contents, 0, mem, startAddress, contents.length);
        refreshGUI();
    }

    /**
     * Returns the size of the memory.
     */
    public int getSize() {
        return size;
    }

    /**
     * Resets the contents of the computer part.
     */
    public void reset() {
        super.reset();
        for (int i = 0; i < size; i++)
            mem[i] = nullValue;
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    public void refreshGUI() {
        super.refreshGUI();

        if (displayChanges)
            gui.setContents(mem);
    }

    /**
     * Scrolls the memory such that the given address will be on top.
     * (assumes legal address).
     */
    public void scrollTo(int address) {
        if (displayChanges)
            gui.scrollTo(address);
    }

    public void clearRequested(ClearEvent event) {
        reset();
    }

    /**
     * Selects the commands in the range fromIndex..toIndex
     */
    public void select(int fromIndex, int toIndex) {
        if (displayChanges)
            gui.select(fromIndex, toIndex);
    }

    /**
     * Hides all selections.
     */
    public void hideSelect() {
        if (displayChanges)
            gui.hideSelect();
    }
}
