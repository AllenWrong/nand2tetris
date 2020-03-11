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

import java.awt.*;
import javax.swing.table.*;

/**
 * A PointedMemorySegmentComponent with an absolute address referencing.
 */
public class AbsolutePointedMemorySegmentComponent extends PointedMemorySegmentComponent {

    /**
     * Returns the appropriate table model.
     */
    protected TableModel getTableModel() {
        return new AbsoluteTableModel();
    }

    /**
     * Returns the value at the given index in its string representation.
     */
    public String getValueAsString(int index) {
        return super.getValueAsString(index - startAddress);
    }

    /**
     * Returns the coordinates of the top left corner of the value at the given index.
     */
    public Point getCoordinates(int index) {
        return super.getCoordinates(index-startAddress);
    }

    /**
     * Scrolls the table to the pointer location.
     */
    protected void scrollToPointer() {
        Utilities.tableCenterScroll(this, segmentTable, pointerAddress - startAddress);
    }

    // An inner class representing the model of this table.
    public class AbsoluteTableModel extends MemorySegmentTableModel {

        /**
         * Returns the value at a specific row and column.
         */
        public Object getValueAt(int row, int col) {
            if(col==0)
                return String.valueOf(row + startAddress);
            else
                return super.getValueAt(row, col);
        }
    }
}
