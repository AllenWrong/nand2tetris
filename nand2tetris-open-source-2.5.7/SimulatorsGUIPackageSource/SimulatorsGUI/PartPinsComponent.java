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

package SimulatorsGUI;

import HackGUI.*;
import Hack.HardwareSimulator.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * This class represents the gui of a part pin.
 */
public class PartPinsComponent extends PinsComponent implements PartPinsGUI {

    // An array containing the info of the part pins.
    private PartPinInfo[] partPins;

    // The values of the pins in a String representation.
    private String[] valuesStr;

    // The renderer of the pins table.
    private PartPinsTableCellRenderer renderer = new PartPinsTableCellRenderer();

    // a boolean field specifying if the user can enter values into the table.
    private boolean isEnabled = true;

    // The part name
    private JLabel partNameLbl = new JLabel();

    /**
     * Constructs a new PartPinsComponent.
     */
    public PartPinsComponent() {
        super();
        partPins = new PartPinInfo[0];
        valuesStr = new String[0];
        pinsTable.setDefaultRenderer(pinsTable.getColumnClass(0), renderer);

        jbInit();

    }

    /**
     * Sets the name of the part with the given name.
     */
    public void setPartName(String partName) {
        partNameLbl.setText(partName);
    }

    /**
     * Enables user input into the source.
     */
    public void enableUserInput() {
        isEnabled = true;
    }

    /**
     * Disables user input into the source.
     */
    public void disableUserInput() {
        isEnabled = false;
    }


    /**
     * Returns the index of the values column.
     */
    protected int getValueColumn() {
        return 2;
    }

    /**
     * Returns the appropriate table model.
     */
    protected TableModel getTableModel() {
        return new PartPinsTableModel();
    }

    /**
     * Called when there was a change in one of the pin values.
     * The event contains the changed value in a string representation and
     * a boolean which is true if the user pressed the 'ok' button and false
     * if the user pressed the 'cancel' button.
     */
    public void pinValueChanged (PinValueEvent e) {
        pinsTable.setEnabled(true);
        if(e.getIsOk()) {
            valuesStr[pinsTable.getSelectedRow()] = e.getValueStr();
            partPins[pinsTable.getSelectedRow()].value = Format.translateValueToShort(e.getValueStr(), dataFormat);
        }
        notifyListeners(pinsTable.getSelectedRow(), Format.translateValueToShort(e.getValueStr(), dataFormat));
    }

    /**
     * Returns the coordinates of the top left corner of the value at the given index.
     */
    public Point getCoordinates (int index) {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        Rectangle r = pinsTable.getCellRect(index, 2, true);
        pinsTable.scrollRectToVisible(r);
        return new Point((int)(r.getX() + topLevelLocation.getX()),
                         (int)(r.getY() + topLevelLocation.getY() - bar.getValue()));
    }

    /**
     * Sets the pins list's contents with the given vector of PartPinInfo objects.
     */
    public void setContents(Vector newPins) {
        partPins = new PartPinInfo[newPins.size()];
        valuesStr = new String[newPins.size()];
        newPins.toArray(partPins);
        for (int i=0; i<partPins.length;i++)
            valuesStr[i] = Format.translateValueToString(partPins[i].value, dataFormat);
        pinsTable.clearSelection();
        pinsTable.revalidate();
        repaint();
    }

    /**
     * Returns the value at the given index in its string representation.
     */
    public String getValueAsString (int index) {
        return valuesStr[index];
    }

    /**
     * Sets the element at the given index with the given value.
     */
    public void setValueAt(int index, short value) {
        partPins[index].value = value;
        valuesStr[index] = Format.translateValueToString(value, dataFormat);
        repaint();
    }

    /**
     * Resets the contents of the Pins component.
     */
    public void reset() {
        pinsTable.clearSelection();
        repaint();
        // resetting the flash and highlight
        hideFlash();
        hideHighlight();
    }

    // Determines the width of each column in the table.
    protected void determineColumnWidth() {
        TableColumn column = null;
        for (int i = 0; i < 2; i++) {
            column = pinsTable.getColumnModel().getColumn(i);
            if (i == 0)
                column.setPreferredWidth(20);
            else if(i==1)
                column.setPreferredWidth(20);
            else if(i==2)
                column.setPreferredWidth(180);
        }
    }


    // An inner class representing the model of the breakpoint table.
    class PartPinsTableModel extends AbstractTableModel {

        String[] columnNames = {"Part pin", "Gate pin", "Value"};

        /**
         * Returns the number of columns.
         */
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * Returns the number of rows.
         */
        public int getRowCount() {
            if(partPins == null)
                return 0;
            else
                return partPins.length;
        }

        /**
         * Returns the names of the columns.
         */
        public String getColumnName(int col) {
            return columnNames[col];
        }

        /**
         * Returns the value at a specific row and column.
         */
        public Object getValueAt(int row, int col) {
            String result ="";
            if(col==0)
                result = HardwareSimulator.getFullPinName(partPins[row].partPinName, partPins[row].partPinSubBus);
            else if (col==1)
                result = HardwareSimulator.getFullPinName(partPins[row].gatePinName, partPins[row].gatePinSubBus);
            else if (col==2)
                result = valuesStr[row];
            return result;
        }

        /**
         * Returns true of this table cells are editable, false -
         * otherwise.
         */
        public boolean isCellEditable(int row, int col){
            return false;
        }

        /**
         * Sets the value at a specific row and column.
         */
        public void setValueAt(Object value, int row, int col) {

            String data = ((String)value).trim();
            try {
                valuesStr[row] = data;
                partPins[row].value = Format.translateValueToShort(data, dataFormat);
                notifyListeners((short)row,partPins[row].value);
            }
            catch(NumberFormatException nfe) {
                notifyErrorListeners("Illegal value");
                valuesStr[row] = Format.translateValueToString(partPins[row].value, dataFormat);
            }
            repaint();
        }
    }

    // An inner class which implemets the cell renderer of the pins table, giving
    // the feature of alignment, flashing and highlighting.
    class PartPinsTableCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent
            (JTable table, Object value, boolean selected, boolean focused, int row, int column)
        {
            setEnabled(table == null || table.isEnabled());

            if(column==0 || column==1) {
                setHorizontalAlignment(SwingConstants.CENTER);
                setForeground(null);
                setBackground(null);
            }
            else {
                setHorizontalAlignment(SwingConstants.RIGHT);
                for (int i=0;i<highlightIndex.size(); i++) {
                    if(row == ((Integer)highlightIndex.elementAt(i)).intValue()) {
                        setForeground(Color.blue);
                        break;
                    }
                    else
                        setForeground(null);
                }
                if (row == flashIndex) {
                    setBackground(Color.orange);
                }
                else
                    setBackground(null);

            }
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            return this;
        }
    }

    // Initializes this component
    private void jbInit()  {
        partNameLbl.setFont(Utilities.bigLabelsFont);
        partNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
        partNameLbl.setText("keyboard");
        partNameLbl.setForeground(Color.black);
        partNameLbl.setBounds(new Rectangle(62, 10, 102, 21));
        this.add(partNameLbl, null);
    }
}
