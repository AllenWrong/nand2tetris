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

package HackGUI;

import Hack.ComputerParts.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class PointedMemorySegmentComponent extends MemorySegmentComponent implements PointedMemorySegmentGUI {

    // The pointer address
    protected short pointerAddress = -1;

    // Indicates whether this component has the focus.
    protected boolean hasFocus = false;

    /**
     * Constructs a new PointedMemorySegmentComponent.
     */
    public PointedMemorySegmentComponent() {
        super();
    }

    /**
     * Sets the start address.
     */
    public void setStartAddress(int address) {
        super.setStartAddress(address);
        scrollToPointer();
    }

    /**
     * Sets the pointer with the given pointer address (absolute address).
     */
    public void setPointer(int pointerAddress) {
        this.pointerAddress = (short)pointerAddress;
        scrollToPointer();
    }

    protected DefaultTableCellRenderer getCellRenderer() {
        return new PointedMemorySegmentTableCellRenderer();
    }

    /**
     * Scrolls the table to the pointer location.
     */
    protected void scrollToPointer() {
        if(pointerAddress >= 0)
            Utilities.tableCenterScroll(this, segmentTable, pointerAddress);
    }

    /**
     * Implementing the action of the table gaining the focus.
     */
    public void segmentTable_focusGained(FocusEvent e) {
        super.segmentTable_focusGained(e);
        hasFocus = true;

    }

    /**
     * Implementing the action of the table loosing the focus.
     */
    public void segmentTable_focusLost(FocusEvent e) {
        super.segmentTable_focusLost(e);
        hasFocus = false;
    }

    // An inner class which implemets the cell renderer of the program table, giving
    // the feature of coloring the background of a specific cell.
    public class PointedMemorySegmentTableCellRenderer extends MemorySegmentTableCellRenderer {

        public void setRenderer(int row, int column) {
            if (row == pointerAddress - startAddress)
                setBackground(Color.yellow);
            else
                setBackground(null);

            super.setRenderer(row, column);
        }
   }
}
