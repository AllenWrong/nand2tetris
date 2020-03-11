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
 * An interface for a computer memory GUI, representing a specific memory segment.
 * A memory segment holds the main memory gui.
 * Changes in the main memory gui should be reflected in this gui.
 * As with the memory gui, every memory cell contains an address and the contents
 * can be displayed in decimal, hexadecimal or binary format.
 * The displayed addresses should start from 0 and should be filled in consecutive order.
 */
public interface MemorySegmentGUI extends InteractiveValueComputerPartGUI {

    /**
     * Registers the given ComputerPartEventListener as a listener to this GUI.
     */
    public void addListener(ComputerPartEventListener listener);

    /**
     * Un-registers the given ComputerPartEventListener from being a listener to this GUI.
     */
    public void removeListener(ComputerPartEventListener listener);

    /**
     * Notifies all the ComputerPartEventListeners on a change in the memory by creating
     * a ComputerPartEvent (with the changed address and value) and sending it using the
     * memoryChanged method to all the listeners.
     */
    public void notifyListeners(int address, short value);

    /**
     * Sets the start address with the given one.
     */
    public void setStartAddress(int startAddress);

    /**
     * Hides all selections.
     */
    public void hideSelect();

    /**
     * Scrolls the memory such that the given address will be on top.
     */
    public void scrollTo(int address);
}
