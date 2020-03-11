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

/**
 * Represents a segment of the main memory - from a start address till the end.
 * A MemorySegment doesn't hold its own values. instead, it uses the appropriate values
 * of its main memory.
 * The start address can be accessed using the getStartAddress & setStartAddress methods.
 */
public class MemorySegment extends InteractiveValueComputerPart {

    // The gui of the memory segment
    protected MemorySegmentGUI gui;

    // The main memory
    protected Memory mainMemory;

    // The start address of this segment in the main memory
    protected int startAddress;

    /**
     * Constructs a new Memory Segment with the given main memory and GUI.
     */
    public MemorySegment(Memory mainMemory, MemorySegmentGUI gui) {
        super(gui != null);
        init(mainMemory, gui);
    }

    /**
     * Constructs a new Memory Segment with the given main memory, GUI and the legal
     * values range.
     */
    public MemorySegment(Memory mainMemory, MemorySegmentGUI gui, short minValue,
                         short maxValue) {
        super(gui != null, minValue, maxValue);
        init(mainMemory, gui);
    }

    // Initializes the memory segment
    private void init(Memory mainMemory, MemorySegmentGUI gui) {
        this.mainMemory = mainMemory;
        this.gui = gui;

        if (hasGUI) {
            gui.addListener(this);
            gui.addErrorListener(this);
        }
    }

    /**
     * Sets the start address with the given one.
     */
    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
        if (displayChanges)
            gui.setStartAddress(startAddress);
    }

    /**
     * Returns the start address.
     */
    public int getStartAddress() {
        return startAddress;
    }

    public void doSetValueAt(int index, short value) {
        if (mainMemory.getValueAt(startAddress + index) != value)
            mainMemory.setValueAt(startAddress + index, value, true);
    }

    public short getValueAt(int index) {
        return mainMemory.getValueAt(startAddress + index);
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    public void refreshGUI() {
        super.refreshGUI();

        if (displayChanges)
            gui.setStartAddress(startAddress);
    }

    /**
     * Scrolls the memory such that the given address will be on top.
     * (assumes legal address).
     */
    public void scrollTo(int address) {
        if (displayChanges)
            gui.scrollTo(startAddress + address);
    }

    /**
     * Hides all selections.
     */
    public void hideSelect() {
        if (displayChanges)
            gui.hideSelect();
    }
}
