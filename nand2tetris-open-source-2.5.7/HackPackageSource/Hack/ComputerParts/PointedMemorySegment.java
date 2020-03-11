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
 * A Memory Segment that has an address pointer.
 */
public class PointedMemorySegment extends MemorySegment {

    /**
     * Constructs a new PointedMemorySegment with the given main memory and GUI.
     */
    public PointedMemorySegment(Memory mainMemory, PointedMemorySegmentGUI gui) {
        super(mainMemory, gui);
    }

    /**
     * Constructs a new PointedMemorySegment with the given main memory, GUI
     * and the legal values range.
     */
    public PointedMemorySegment(Memory mainMemory, PointedMemorySegmentGUI gui,
                                short minValue, short maxValue) {
        super(mainMemory, gui, minValue, maxValue);
    }

    /**
     * Sets the pointer to point at the given address.
     */
    public void setPointerAddress(int address) {
        if (displayChanges)
            ((PointedMemorySegmentGUI)gui).setPointer(address);
    }

    public void reset() {
        super.reset();
    }
}
