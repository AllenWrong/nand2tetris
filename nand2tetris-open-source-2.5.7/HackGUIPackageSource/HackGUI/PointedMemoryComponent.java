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
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class represents the GUI of a pointed memory.
 */
public class PointedMemoryComponent extends MemoryComponent implements PointedMemoryGUI {

    // The pointer address
    protected int pointerAddress = -1;

    // Indicates whether this component has the focus
    protected boolean hasFocus = false;

    protected DefaultTableCellRenderer getCellRenderer() {
        return new PointedMemoryTableCellRenderer();
    }

     /**
     * Sets the pointer with the given pointer address.
     */
    public void setPointer (int pointerAddress) {
        this.pointerAddress = pointerAddress;

        if (pointerAddress >= 0)
            Utilities.tableCenterScroll(this, memoryTable, pointerAddress);
    }

    /**
     * Implementing the action of the table gaining the focus.
     */
    public void memoryTable_focusGained(FocusEvent e) {
        super.memoryTable_focusGained(e);
        hasFocus = true;
    }

    /**
     * Implementing the action of the table loosing the focus.
     */
    public void memoryTable_focusLost(FocusEvent e) {
        super.memoryTable_focusLost(e);
        hasFocus = false;
    }

    // An inner class which implemets the cell renderer of the program table, giving
    // the feature of coloring the background of a specific cell.
    public class PointedMemoryTableCellRenderer extends MemoryTableCellRenderer {

        public void setRenderer(int row, int column) {
            if (row == pointerAddress)
                setBackground(Color.yellow);
            else
                setBackground(null);

            super.setRenderer(row, column);
        }
    }
}
