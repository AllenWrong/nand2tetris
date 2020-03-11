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

import Hack.ComputerParts.*;

/**
 * An AbsolutePointedMemorySegment in which the pointer is always set to the address just
 * after the last updated address.
 * Can be useful together with a gui that doesn't display addresses beyond the pointer address.
 */
public class TrimmedAbsoluteMemorySegment extends AbsolutePointedMemorySegment {

    /**
     * Constructs a new TrimmedAbsoluteMemorySegment with the given main memory and GUI.
     */
    public TrimmedAbsoluteMemorySegment(Memory mainMemory, PointedMemorySegmentGUI gui) {
        super(mainMemory, gui);
    }

    /**
     * Constructs a new TrimmedAbsoluteMemorySegment with the given main memory, GUI and the legal
     * values range.
     */
    public TrimmedAbsoluteMemorySegment(Memory mainMemory, PointedMemorySegmentGUI gui,
                                        short minValue, short maxValue) {
        super(mainMemory, gui, minValue, maxValue);
    }

    public void setValueAt(int index, short value, boolean quiet) {
        if (displayChanges)
            ((PointedMemorySegmentGUI)gui).setPointer(index + 1);

        super.setValueAt(index, value, quiet);
    }
}
