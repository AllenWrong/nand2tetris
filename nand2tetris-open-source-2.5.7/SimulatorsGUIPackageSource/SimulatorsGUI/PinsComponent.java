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
import Hack.Gates.*;
import Hack.ComputerParts.*;
import Hack.Events.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class represents the gui of a pins list. Every pin row contains the pin's name
 * and value (which may be in a specified width). The GUI can display its values in
 * decimal, hexadecimal or binary format.
 */
public class PinsComponent extends JPanel implements PinsGUI, MouseListener, PinValueListener {

    // A component which represents the binary value of the pin.
    protected BinaryComponent binary;

    // The table of the input component.
    protected JTable pinsTable;

    // An array containing the info of the pins.
    private PinInfo[] pins;

    // The values of the pins in a String representation.
    private String[] valueStr;

    // The current format.
    protected int dataFormat;

    // A vector containing the listeners to this object.
    private Vector listeners;

    // A vector containing the error listeners to this object.
    private Vector errorEventListeners;

    // The scroll pane on which the table is placed.
    protected JScrollPane scrollPane;

    // The index of the flashed row in the table.
    protected int flashIndex = -1;

    // A vector containing the index of the rows that should be highlighted.
    protected Vector highlightIndex;

    // The location of this component relative to its top level ancestor.
    protected Point topLevelLocation;

    // The renderer of the table containing the pins list.
    private PinsTableCellRenderer renderer = new PinsTableCellRenderer();

    // The name of this pins component.
    private JLabel nameLbl = new JLabel();

    // a boolean field specifying if the user can enter values into the table.
    private boolean isEnabled = true;

    // The null value of this component
    protected short nullValue;

    // A boolean field specifying if the null value should be activated or not.
    protected boolean hideNullValue;

    // The start and end enabled indices.
    protected int startEnabling,endEnabling;

    // The index of the last selected row.
    private int lastSelectedRow;


    /**
     * Constructs a new PinsComponent.
     */
    public PinsComponent() {
        dataFormat = Format.DEC_FORMAT;
        JTextField tf = new JTextField();
        tf.setFont(Utilities.bigBoldValueFont);
        tf.setBorder(null);
        DefaultCellEditor editor = new DefaultCellEditor(tf);
        startEnabling = -1;
        endEnabling = -1;

        pins = new PinInfo[0];
        valueStr = new String[0];
        listeners = new Vector();
        errorEventListeners = new Vector();
        highlightIndex = new Vector();
        binary = new BinaryComponent();
        pinsTable = new JTable(getTableModel());
        pinsTable.setDefaultRenderer(pinsTable.getColumnClass(0), renderer);

        pinsTable.getColumnModel().getColumn(getValueColumn()).setCellEditor(editor);

        jbInit();

    }

    /**
     * Sets the null value of this component.
     */
    public void setNullValue (short value, boolean hideNullValue) {
        nullValue = value;
        this.hideNullValue = hideNullValue;
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

    public void setDimmed(boolean cond) {
        pinsTable.setEnabled(!cond);
    }

    /**
     * Returns the index of the values column.
     */
    protected int getValueColumn() {
        return 1;
    }

    /**
     * Returns the appropriate table model.
     */
    protected TableModel getTableModel() {
        return new PinsTableModel();
    }

    /**
     * Sets the name of this pins component.
     */
    public void setPinsName(String name) {
        nameLbl.setText(name);
    }

    /**
     * Registers the given ErrorEventListener as a listener to this GUI.
     */
    public void addErrorListener(ErrorEventListener listener) {
        errorEventListeners.addElement(listener);
    }

    /**
     * Un-registers the given ErrorEventListener from being a listener to this GUI.
     */
    public void removeErrorListener(ErrorEventListener listener) {
        errorEventListeners.removeElement(listener);
    }

   /**
     * Notifies all the ErrorEventListener on an error in this gui by
     * creating an ErrorEvent (with the error message) and sending it
     * using the errorOccured method to all the listeners.
     */
    public void notifyErrorListeners(String errorMessage) {
        ErrorEvent event = new ErrorEvent(this, errorMessage);
        for (int i=0; i<errorEventListeners.size(); i++)
            ((ErrorEventListener)errorEventListeners.elementAt(i)).errorOccured(event);
    }

    public void addListener(ComputerPartEventListener listener) {
        listeners.addElement(listener);
    }

    public void removeListener(ComputerPartEventListener listener) {
        listeners.removeElement(listener);
    }

    public void notifyListeners(int index, short value) {
        ComputerPartEvent event = new ComputerPartEvent(this, index, value);
        for (int i=0; i<listeners.size(); i++)
            ((ComputerPartEventListener)listeners.elementAt(i)).valueChanged(event);
    }

    public void notifyListeners() {
        for (int i=0; i<listeners.size(); i++)
            ((ComputerPartEventListener)listeners.elementAt(i)).guiGainedFocus();
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
            pins[lastSelectedRow].value = translateValueToShort(e.getValueStr());
            valueStr[lastSelectedRow] = translateValueToString(pins[lastSelectedRow].value,
                                                               pins[lastSelectedRow].width);
        }
        notifyListeners(lastSelectedRow, pins[lastSelectedRow].value);
    }

    /**
     * flashes the value at the given index.
     */
    public void flash(int index) {
        flashIndex = index;
        Utilities.tableCenterScroll(this, pinsTable, index);
    }

    /**
     * Hides the existing flash.
     */
    public void hideFlash() {
        flashIndex = -1;
        repaint();
    }

    /**
     * Highlights the value at the given index.
     */
    public void highlight(int index) {
        highlightIndex.addElement(new Integer(index));
        repaint();
    }

    /**
     * Hides all highlightes.
     */
    public void hideHighlight() {
        highlightIndex.removeAllElements();
        repaint();
    }

    /**
     * Sets the enabled range of this segment.
     * Any address outside this range will be disabled for user input.
     * If gray is true, addresses outside the range will be gray colored.
     */
    public void setEnabledRange(int start, int end, boolean gray) {
        startEnabling = start;
        endEnabling = end;
    }

    /**
     * Returns the value at the given index in its string representation.
     */
    public String getValueAsString (int index) {
        return valueStr[index];
    }

    /**
     * Sets the element at the given index with the given value.
     */
    public void setValueAt(int index, short value) {
        pins[index].value = value;
        valueStr[index] = translateValueToString(value, pins[index].width);
    }

    /**
     * Returns the coordinates of the top left corner of the value at the given index.
     */
    public Point getCoordinates (int index) {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        Rectangle r = pinsTable.getCellRect(index, 1, true);
        pinsTable.scrollRectToVisible(r);
        return new Point((int)(r.getX() + topLevelLocation.getX()),
                         (int)(r.getY() + topLevelLocation.getY() - bar.getValue()));
    }

    /**
     * Returns the location of the given index relative to the panel.
     */
    public Point getLocation (int index) {
        Rectangle r = pinsTable.getCellRect(index, 0, true);
        Point p = Utilities.getTopLevelLocation(this, pinsTable);
        return new Point ((int)(r.getX() + p.getX()) , (int)(r.getY() + p.getY()));
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

    /**
     * Sets the pins list's contents with the given array of PinInfo objects.
     */
    public void setContents(PinInfo[] newPins) {
        pins = new PinInfo[newPins.length];
        valueStr = new String[newPins.length];
        System.arraycopy(newPins, 0, pins, 0, newPins.length);
        for (int i=0; i<newPins.length;i++)
            valueStr[i] = translateValueToString(newPins[i].value, newPins[i].width);
        pinsTable.clearSelection();
        pinsTable.revalidate();
        repaint();
    }

    /**
     * Implementing the action of double-clicking the mouse on the text field
	 * to popup the binary input component and of clicking the mouse on the
	 * text field to cancel the binary input component.
	 * This generates the same feel as the (hexa)decimal input method
     */
    public void mouseClicked(MouseEvent e) {
        if(isEnabled && (e.getModifiers()&InputEvent.BUTTON1_MASK) != 0) {
			if (binary.isVisible()) {
				binary.hideBinary();
				// The pinsTable didn't get the selection message since it
				// was disabled. Enable it and select the correct row.
				pinsTable.setEnabled(true);
				pinsTable.changeSelection(pinsTable.rowAtPoint(e.getPoint()),
										  pinsTable.columnAtPoint(e.getPoint()),
										  false, false);
				pinsTable.grabFocus();
			}
            if (e.getClickCount() == 2) {
                if(dataFormat == Format.BIN_FORMAT) {
                    pinsTable.setEnabled(false);
                    binary.setLocation((int)getLocation(pinsTable.getSelectedRow()+1).getX(), (int)getLocation(pinsTable.getSelectedRow()+1).getY() );
                    binary.setValue(pins[pinsTable.getSelectedRow()].value);
                    binary.setNumOfBits(pins[pinsTable.getSelectedRow()].width);
                    binary.showBinary();
                }
            }
        }
    }

    // Empty implementations.
    public void mouseExited (MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseReleased (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}

    /**
     * Sets the top level location.
     */
    public void setTopLevelLocation(Component top) {
        topLevelLocation = Utilities.getTopLevelLocation(top, pinsTable);
    }

    // Determines the width of each column in the table.
    protected void determineColumnWidth() {
        TableColumn column = null;
        for (int i = 0; i < 2; i++) {
            column = pinsTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(116);
            } else {
                column.setPreferredWidth(124);
            }
        }
    }

    /**
     * Sets the numeric format with the given code (out of the format constants
     * in HackController).
     */
    public void setNumericFormat(int formatCode) {
        dataFormat = formatCode;
        for(int i=0;i<pins.length; i++)
            valueStr[i] = translateValueToString(pins[i].value, pins[i].width);
        repaint();

    }

    /**
     * Translates a given string to a short according to the current format.
     */
    protected short translateValueToShort(String data) {
        return Format.translateValueToShort(data,dataFormat);
    }

    /**
     * Translates a given short to a string according to the current format.
     */
    protected String translateValueToString(short value, int width) {
        String result = null;

        if(value == nullValue && hideNullValue)
            result = "";
        else {
            result = Format.translateValueToString(value, dataFormat);
            if(dataFormat == Format.BIN_FORMAT)
                result = result.substring(result.length()-width,result.length());
        }

        return result;
    }

    /**
     * Sets the number of visible rows.
     */
    public void setVisibleRows(int num) {
        int tableHeight = num * pinsTable.getRowHeight();
        scrollPane.setSize(getTableWidth(), tableHeight + 3);
        setPreferredSize(new Dimension(getTableWidth(), tableHeight + 30));
        setSize(getTableWidth(), tableHeight + 30);
    }

    /**
     * Returns the width of the table.
     */
    public int getTableWidth() {
        return 241;
    }

    // Initialization of this component.
    private void jbInit() {
        pinsTable.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                pinsTable_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                pinsTable_focusLost(e);
            }
        });
        pinsTable.addMouseListener(this);
        pinsTable.getTableHeader().setReorderingAllowed(false);
        pinsTable.getTableHeader().setResizingAllowed(false);
        this.setLayout(null);
        scrollPane = new JScrollPane(pinsTable);
        scrollPane.setLocation(0,27);
        setBorder(BorderFactory.createEtchedBorder());

        binary.setSize(new Dimension(240,52));
        binary.setLayout(null);
        binary.setVisible(false);
        binary.addListener(this);
        determineColumnWidth();
        nameLbl.setText("Name :");
        nameLbl.setBounds(new Rectangle(3, 3, 102, 21));
        nameLbl.setFont(Utilities.labelsFont);
        pinsTable.setFont(Utilities.valueFont);
        this.add(binary, null);
        this.add(scrollPane, null);
        this.add(nameLbl, null);

    }
    // The action of the table gaining focus
    public void pinsTable_focusGained(FocusEvent e) {
        notifyListeners();
    }

    // The action of the table loosing focus
    public void pinsTable_focusLost(FocusEvent e) {
        lastSelectedRow = pinsTable.getSelectedRow();
        pinsTable.clearSelection();
    }

     // An inner class representing the model of the breakpoint table.
    class PinsTableModel extends AbstractTableModel {
        String[] columnNames = {"Name", "Value"};

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
            return pins.length;
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

            if(col==0)
                return pins[row].name + (pins[row].width > 1 ? "[" + pins[row].width + "]" : "");
            else
                return valueStr[row];
        }

        /**
         * Returns true of this table cells are editable, false -
         * otherwise.
         */
        public boolean isCellEditable(int row, int col){
            if (isEnabled && col == 1 && dataFormat != Format.BIN_FORMAT &&
                (endEnabling == -1 || (row>= startEnabling && row <= endEnabling)))
                return true;
            else
                return false;
        }

        /**
         * Sets the value at a specific row and column.
         */
        public void setValueAt(Object value, int row, int col) {

            String data = (String)value;
            if (!valueStr[row].equals(data)) {
                try {
                    valueStr[row] = data;

                    if(data.equals("") && hideNullValue)
                        pins[row].value = nullValue;
                    else
                        pins[row].value = Format.translateValueToShort(data, dataFormat);

                    notifyListeners((short)row,pins[row].value);
                }
                catch(NumberFormatException nfe) {
                    notifyErrorListeners("Illegal value");
                    valueStr[row] = Format.translateValueToString(pins[row].value, dataFormat);
                }
                repaint();
            }
        }
    }

    // An inner class which implemets the cell renderer of the pins table, giving
    // the feature of alignment, flashing and highlighting.
    class PinsTableCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent
            (JTable table, Object value, boolean selected, boolean focused, int row, int column)
        {
            setEnabled(table == null || table.isEnabled());

            if(column==0) {
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
}
