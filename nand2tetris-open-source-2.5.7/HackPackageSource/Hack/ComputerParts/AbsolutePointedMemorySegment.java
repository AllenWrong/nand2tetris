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
 * A PointedMemorySegment with absolute address referencing.
 * When referencing an address (for getValueAt & setValueAt), the referenced address
 * should be relative to the beginning of the main memory (and not to the start address).
 */
public class AbsolutePointedMemorySegment extends PointedMemorySegment {

    /**
     * Constructs a new AbsolutePointedMemorySegment with the given main memory and GUI.
     */
    public AbsolutePointedMemorySegment(Memory mainMemory, PointedMemorySegmentGUI gui) {
        super(mainMemory, gui);
    }

    /**
     * Constructs a new AbsolutePointedMemorySegment with the given main memory, GUI and
     * the legal values range.
     */
    public AbsolutePointedMemorySegment(Memory mainMemory, PointedMemorySegmentGUI gui,
                                 short minValue, short maxValue) {
        super(mainMemory, gui, minValue, maxValue);
    }

    public void setValueAt(int index, short value, boolean quiet) {
        super.setValueAt(index - startAddress, value, quiet);
    }

    public short getValueAt(int index) {
        return mainMemory.getValueAt(index);
    }

    public void valueChanged(ComputerPartEvent event) {
        ComputerPartEvent newEvent = new ComputerPartEvent((ComputerPartGUI)event.getSource(),
                                                           event.getIndex() + startAddress,
                                                           event.getValue());
        super.valueChanged(newEvent);
    }
}
