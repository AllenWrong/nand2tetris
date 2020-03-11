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

import Hack.ComputerParts.LabeledPointedMemoryGUI;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

/**
 * This class represents a memomy component with additional feature of another
 * column representing the labels of this memory component.
 */
public class LabeledMemoryComponent extends PointedMemoryComponent implements LabeledPointedMemoryGUI{

    // The array of labels of this memory.
    protected String[] labels;

    // The flash index of the labels.
    private int labelFlashIndex = -1;

    /**
     * Constructs a new LabeledMemoryComponent.
     */
    public LabeledMemoryComponent() {
        searchButton.setLocation(199,2);
        clearButton.setLocation(168,2);
        memoryTable.setGridColor(Color.lightGray);
        labels = new String[0];
    }

    /**
     * Returns the width of the table.
     */
    public int getTableWidth() {
        return 233;
    }

    /**
     * Returns the index of the values column.
     */
    protected int getValueColumnIndex() {
        return 2;
    }

    /**
     * Sets a name for the memory cell that matches the given address
     */
    public void setLabel(int address, String name) {
        labels[address] = name + ":";
        repaint();
    }

    /**
     * Clears all cell names.
     */
    public void clearLabels() {
        for (int i = 0; i < labels.length; i++)
            if (labels[i] != null)
                labels[i] = null;
        repaint();
    }

    /**
     * flashes the label at the given index.
     */
    public void labelFlash(int index) {
        labelFlashIndex = index;
        repaint();
    }

    /**
     * hides all existing falsh label.
     */
    public void hideLabelFlash() {
        labelFlashIndex = -1;
        repaint();
    }

    /**
     * Sets the memory contents with the given values array. (assumes that the
     * length of the given array equals to the gui's size)
     */
    public void setContents(short[] newValues) {
        String[] oldLabels = labels;
        labels = new String[newValues.length];
        System.arraycopy(oldLabels, 0, labels, 0, Math.min(oldLabels.length, labels.length));
        super.setContents(newValues);
    }

    /**
     * Returns the table model of this component.
     */
    protected TableModel getTableModel() {
        return new LabeledMemoryTableModel();
    }

    /**
     * Returns the cell renderer for this component.
     */
    protected DefaultTableCellRenderer getCellRenderer() {
        return new LabeledPointedMemoryTableCellRenderer();
    }

    /**
     * Determines the width of each column in the table.
     */
    protected void determineColumnWidth() {
        TableColumn column = null;
        for (int i = 0; i < 2; i++) {
            column = memoryTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(1);
                column.setPreferredWidth(1);
            }
            else if (i==1) {
                column.setMinWidth(1);
                column.setPreferredWidth(1);
            }
        }
    }

    // An inner class representing the model of this table.
    class LabeledMemoryTableModel extends MemoryTableModel {

        /**
         * Returns the number of columns.
         */
        public int getColumnCount() {
            return 3;
        }

        /**
         * Returns the value at a specific row and column.
         */
        public Object getValueAt(int row, int col) {
            String result ="";
            if(col==0)
                result = labels[row];
            else
                result = (String)super.getValueAt(row, col - 1);
            return result;
        }

        /**
         * Returns true of this table cells are editable, false -
         * otherwise.
         */
        public boolean isCellEditable(int row, int col) {
            if (col == 0)
                return false;
            else
                return super.isCellEditable(row, col - 1);
        }

    }

    // An inner class which implemets the cell renderer of the VMMemoryComponent.
    public class LabeledPointedMemoryTableCellRenderer extends PointedMemoryTableCellRenderer {

        public void setRenderer(int row, int column) {
            super.setRenderer(row, column - 1);

            if (column == 0) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                setFont(Utilities.boldValueFont);
                setBackground(Color.lightGray);
                if (row==labelFlashIndex)
                    setBackground(Color.orange);
            }
        }
    }
}
