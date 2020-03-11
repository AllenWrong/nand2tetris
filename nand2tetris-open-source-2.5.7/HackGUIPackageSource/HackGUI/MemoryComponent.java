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
import Hack.Events.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

/**
 * This class represents the GUI of a memory.
 */
public class MemoryComponent extends JPanel implements MemoryGUI {

    // The default number of visible rows.
    protected static final int DEFAULT_VISIBLE_ROWS = 10;

    /**
     * The current format.
     */
    public int dataFormat;

    // A vector containing the listeners to this object.
    private Vector listeners;

    // A vector containing the clear listeners to this object.
    private Vector clearListeners;

    // A vector containing the error listeners to this object.
    private Vector errorEventListeners;

    // A vector containing the repaint listeners to this object.
    private Vector changeListeners;

    // The table representing the memory.
    protected JTable memoryTable;

    // The model of the table.
    private MemoryTableModel tableModel = new MemoryTableModel();

    // The values of this memory in a string representation.
    protected String[] valuesStr;

    // The values of this memory in a short representation.
    protected short[] values;

    // The addresses of this memory.
    protected String[] addresses;

    // Creating buttons and icons.
    protected MouseOverJButton  searchButton = new MouseOverJButton();
    protected MouseOverJButton clearButton = new MouseOverJButton();
    private ImageIcon searchIcon = new ImageIcon(Utilities.imagesDir + "find.gif");
    private ImageIcon clearIcon = new ImageIcon(Utilities.imagesDir + "smallnew.gif");

    // The window of searching a specific location in memory.
    private SearchMemoryWindow searchWindow;

    // The scrollpane on which the table is placed.
    protected JScrollPane scrollPane;

    // A vector containing the highlighted rows.
    protected Vector highlightIndex;

    // The index of the flashed row.
    protected int flashIndex = -1;

    // The location of this component relative to its top level ancestor.
    protected Point topLevelLocation;

    // The name of this component.
    protected JLabel nameLbl = new JLabel();

    // A boolean field specifying if the user can enter values into the table.
    protected boolean isEnabled = true;

    // The null value of this component
    protected short nullValue;

    // A boolean field specifying if the null value should be activated or not.
    protected boolean hideNullValue;

    // The start and end row indices of the enabled region.
    protected int startEnabling, endEnabling;

    // If true, the disbaled region is shaded.
    protected boolean grayDisabledRange;

    /**
     * Constructs a new MemoryComponent.
     */
    public MemoryComponent() {
        dataFormat = Format.DEC_FORMAT;
        startEnabling = -1;
        endEnabling = -1;

        JTextField tf = new JTextField();
        tf.setFont(Utilities.bigBoldValueFont);
        tf.setBorder(null);
        DefaultCellEditor editor = new DefaultCellEditor(tf);

        listeners = new Vector();
        clearListeners = new Vector();
        errorEventListeners = new Vector();
        changeListeners = new Vector();
        highlightIndex = new Vector();
        memoryTable = new JTable(getTableModel());
        memoryTable.setDefaultRenderer(memoryTable.getColumnClass(0), getCellRenderer());
        memoryTable.getColumnModel().getColumn(getValueColumnIndex()).setCellEditor(editor);
        memoryTable.setTableHeader(null);

        values = new short[0];
        addresses = new String[0];
        valuesStr = new String[0];
        searchWindow = new SearchMemoryWindow(this, memoryTable);

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

    /**
     * Returns the index of the values column.
     */
    protected int getValueColumnIndex() {
        return 1;
    }

    /**
     * Returns the table model of this component.
     */
    protected TableModel getTableModel() {
        return new MemoryTableModel();
    }

    /**
     * Returns the cell renderer of this component.
     */
    protected DefaultTableCellRenderer getCellRenderer() {
        return new MemoryTableCellRenderer();
    }

    /**
     * Sets the name of this component.
     */
     public void setName(String name) {
        nameLbl.setText(name);
     }

    /**
     * Sets the location of this component relative to its top level ancestor.
     */
    public void setTopLevelLocation(Component top) {
        topLevelLocation = Utilities.getTopLevelLocation(top, memoryTable);
    }

    public void addListener(ComputerPartEventListener listener) {
        listeners.addElement(listener);
    }

    public void removeListener(ComputerPartEventListener listener) {
        listeners.removeElement(listener);
    }

    public void notifyListeners(int address, short value) {
        ComputerPartEvent event = new ComputerPartEvent(this,address,value);
        for (int i=0;i<listeners.size();i++) {
           ((ComputerPartEventListener)listeners.elementAt(i)).valueChanged(event);
        }
    }

    public void notifyListeners() {
        ComputerPartEvent event = new ComputerPartEvent(this);
        for (int i=0;i<listeners.size();i++) {
           ((ComputerPartEventListener)listeners.elementAt(i)).guiGainedFocus();
        }
    }

    public void addClearListener (ClearEventListener listener) {
        clearListeners.addElement(listener);
    }
    public void removeClearListener (ClearEventListener listener) {
        clearListeners.removeElement(listener);
    }

    public void notifyClearListeners() {
        ClearEvent clearEvent = new ClearEvent(this);
        for(int i=0; i<clearListeners.size();i++)
            ((ClearEventListener)clearListeners.elementAt(i)).clearRequested(clearEvent);
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

    /**
     * Registers the given MemoryChangeListener as a listener to this GUI.
     */
    public void addChangeListener(MemoryChangeListener listener) {
        changeListeners.addElement(listener);
    }

    /**
     * Un-registers the given MemoryChangeListener from being a listener to this GUI.
     */
    public void removeChangeListener(MemoryChangeListener listener) {
        changeListeners.removeElement(listener);
    }

    /**
     * Notifies all the changeListeners on a need to repaint themselves.
     */
    public void notifyRepaintListeners() {
        for (int i=0;i<changeListeners.size();i++) {
           ((MemoryChangeListener)changeListeners.elementAt(i)).repaintChange();
        }
    }

    /**
     * Notifies all the changeListeners on a need to revalidate themselves.
     */
    public void notifyRevalidateListeners() {
        for (int i=0;i<changeListeners.size();i++) {
           ((MemoryChangeListener)changeListeners.elementAt(i)).revalidateChange();
        }
    }

    /**
     * Sets the memory contents with the given values array. (assumes that the
     * length of the given array equals to the gui's size)
     */
    public void setContents(short[] newValues) {
        values = new short[newValues.length];
        addresses = new String[newValues.length];
        valuesStr = new String[newValues.length];

        System.arraycopy(newValues, 0, values, 0, newValues.length);
        for(int i=0;i<values.length;i++) {
            addresses[i] = Format.translateValueToString((short)i, Format.DEC_FORMAT);
            valuesStr[i] = translateValueToString(values[i]);
        }
        memoryTable.revalidate();
        repaint();
        notifyRevalidateListeners();
    }

    /**
     * Updates the values of the table memory.
     */
    protected void updateTable(short value, int row) {
        values[row] = value;
        valuesStr[row] = translateValueToString(value);
    }

    /**
     * Sets the contents of the memory in the given index with the given value.
     * (Assumes legal index - between 0 and getSize()-1).
     */
    public void setValueAt(int index, short value) {
        updateTable(value,index);
        repaint();
        notifyRepaintListeners();
    }

    /**
     * Resets the contents of this MemoryComponent.
     */
    public void reset() {
        for(int i= 0; i<values.length;i++){
            updateTable(nullValue, i);
        }
        repaint();
        notifyRepaintListeners();
        memoryTable.clearSelection();

        hideFlash();
        hideHighlight();
    }

    /**
     * Hides all highlightes.
     */
    public void hideHighlight() {
        highlightIndex.removeAllElements();
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
     * hides the existing flash.
     */
    public void hideFlash () {
        flashIndex = -1;
        repaint();
    }

    /**
     * flashes the value at the given index.
     */
    public void flash (int index) {
        flashIndex = index;
        Utilities.tableCenterScroll(this, memoryTable, index);
    }

    /**
     * Sets the enabled range of this segment.
     * Any address outside this range will be disabled for user input.
     * If gray is true, addresses outside the range will be gray colored.
     */
    public void setEnabledRange(int start, int end, boolean gray) {
        startEnabling = start;
        endEnabling = end;
        grayDisabledRange = gray;
        repaint();
    }

    /**
     * Returns the size (number of elements) of the memory.
     */
    public int getMemorySize() {
        return values != null ? values.length : 0;
    }

    /**
     * Returns the value at the given index in its string representation.
     */
    public String getValueAsString(int index) {
        return Format.translateValueToString(values[index], dataFormat);
    }

    /**
     * Selects the commands in the range fromIndex..toIndex
     */
    public void select(int fromIndex,int toIndex) {
        memoryTable.setRowSelectionInterval(fromIndex,toIndex);
        Utilities.tableCenterScroll(this, memoryTable, fromIndex);
    }

    /**
     * Hides all selections.
     */
    public void hideSelect() {
        memoryTable.clearSelection();
        repaint();
    }

    /**
     * Returns the coordinates of the top left corner of the value at the given index.
     */
    public Point getCoordinates (int index) {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        Rectangle r = memoryTable.getCellRect(index, getValueColumnIndex(), true);
        memoryTable.scrollRectToVisible(r);
        return new Point((int)(r.getX() + topLevelLocation.getX()),
                         (int)(r.getY() + topLevelLocation.getY() - bar.getValue()));
    }

    /**
     * Returns the address string at a specific address.
     */
    public String getAddressStr (short address) {
        return addresses[address];
    }

    /**
     * Returns the value (in a string representation) at a specific address.
     */
    public String getValueStr (short address) {
        return valuesStr[address];
    }

    /**
     * Returns the value (in a short representation) at a specific address.
     */
    public short getValueAsShort (short address) {
        return values[address];
    }

    /**
     * Translates a given string to a short according to the current format.
     * Throws a TranslationException if can't be translated.
     */
    protected short translateValueToShort(String data) throws TranslationException {
        short result = 0;
        try {
            result = Format.translateValueToShort(data,dataFormat);
        } catch (NumberFormatException nfe) {
            throw new TranslationException("Illegal value: " + data);
        }

        return result;
    }

    /**
     * Translates a given short to a string according to the current format.
     */
    protected String translateValueToString(short value) {
        if(hideNullValue) {
            if(value == nullValue)
                return "";
            else
                return Format.translateValueToString(value, dataFormat);
        }
        else
            return Format.translateValueToString(value, dataFormat);
    }

    /**
     * Sets the font of the table.
     */
    public void setTableFont (Font font) {
        memoryTable.setFont(font);
    }

    // Initializes this memory.
    private void jbInit(){

        memoryTable.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                memoryTable_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                memoryTable_focusLost(e);
            }
        });

        scrollPane = new JScrollPane(memoryTable);
        this.setLayout(null);
        searchButton.setToolTipText("Search");
        searchButton.setIcon(searchIcon);
        searchButton.setBounds(new Rectangle(159, 2, 31, 25));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchButton_actionPerformed(e);
            }
        });
        memoryTable.setFont(Utilities.valueFont);
        nameLbl.setBounds(new Rectangle(3, 5, 70, 23));
        nameLbl.setFont(Utilities.labelsFont);
        determineColumnWidth();
        setBorder(BorderFactory.createEtchedBorder());
        scrollPane.setLocation(0,27);

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearButton_actionPerformed(e);
            }
        });
        clearButton.setIcon(clearIcon);
        clearButton.setBounds(new Rectangle(128, 2, 31, 25));
        clearButton.setToolTipText("Clear");
        this.add(scrollPane, null);
        this.add(searchButton, null);
        this.add(nameLbl, null);
        this.add(clearButton, null);
    }

    /**
     * Returns the width of the table.
     */
    public int getTableWidth() {
        return 193;
    }

    /**
     * Sets the number of visible rows.
     */
    public void setVisibleRows(int num) {
        int tableHeight = num * memoryTable.getRowHeight();
        scrollPane.setSize(getTableWidth(), tableHeight + 3);
        setPreferredSize(new Dimension(getTableWidth(), tableHeight + 30));
        setSize(getTableWidth(), tableHeight + 30);
    }

    // Determines the width of each column in the table.
    protected void determineColumnWidth() {
        TableColumn column = null;
        for (int i = 0; i < 2; i++) {
            column = memoryTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(30);
            } else {
                column.setPreferredWidth(100);
            }
        }
    }

    public void scrollTo(int index) {
        Utilities.tableCenterScroll(this, memoryTable, index);
    }

    /**
     * Implementing the action of the table gaining the focus.
     */
    public void memoryTable_focusGained(FocusEvent e) {
        memoryTable.clearSelection();
        notifyListeners();
    }

    /**
     * Implementing the action of the table loosing the focus.
     */
    public void memoryTable_focusLost(FocusEvent e) {
        memoryTable.clearSelection();
    }

    /**
     * Implementing the action of pressing the search button.
     */
    public void searchButton_actionPerformed(ActionEvent e) {
        searchWindow.showWindow();
    }

    /**
     * Implementing the action of pressing the clear button.
     */
    public void clearButton_actionPerformed(ActionEvent e) {

        Object[] options = {"Yes", "No","Cancel"};
        int pressedButtonValue = JOptionPane.showOptionDialog(this.getParent(),
            "Are you sure you want to clear ?",
            "Warning Message",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[2]);

        if(pressedButtonValue==JOptionPane.YES_OPTION)
            notifyClearListeners();
    }

    // An inner class representing the model of this table.
    class MemoryTableModel extends AbstractTableModel {

        /**
         * Returns the number of columns.
         */
        public int getColumnCount() {
            return 2;
        }

        /**
         * Returns the number of rows.
         */
        public int getRowCount() {
            return getMemorySize();
        }

        /**
         * Returns the names of the columns.
         */
        public String getColumnName(int col) {
            return null;
        }

        /**
         * Returns the value at a specific row and column.
         */
        public Object getValueAt(int row, int col) {
            if(col==0)
                return addresses[row];
            else
                return valuesStr[row];
        }

        /**
         * Returns true of this table cells are editable, false -
         * otherwise.
         */
        public boolean isCellEditable(int row, int col) {
            boolean result = false;
            if(isEnabled && col == 1 &&
                (endEnabling == -1 || (row>= startEnabling && row <= endEnabling)))
                result = true;

            return result;
        }

        /**
         * Sets the value at a specific row and column.
         */
        public void setValueAt(Object value, int row, int col) {
            String data = ((String)value).trim();
            if (!valuesStr[row].equals(data)) {
                try {
                    valuesStr[row] = data;
                    if(data.equals("") && hideNullValue)
                        values[row] = nullValue;
                    else
                        values[row] = translateValueToShort(data);
                    notifyListeners((short)row,values[row]);
                } catch(TranslationException te) {
                    notifyErrorListeners(te.getMessage());
                    valuesStr[row] = translateValueToString(values[row]);
                }
                repaint();
                notifyRepaintListeners();
            }
        }
    }

    /**
     * Sets the numeric format with the given code (out of the format constants
     * in HackController).
     */
    public void setNumericFormat(int formatCode) {
        dataFormat = formatCode;
        for(int i=0;i<values.length; i++)
            valuesStr[i] = translateValueToString(values[i]);
        repaint();
        notifyRepaintListeners();

    }

    /**
     * Sets the size of the name label according to the size constants.
     */
    public void setNameLabelSize() {
        nameLbl.setBounds(new Rectangle(3, 7, 150, 23));
    }

    // An inner class which implemets the cell renderer of the memory table, giving
    // the feature of aligning the text in the cells.
    class MemoryTableCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent
            (JTable table, Object value, boolean selected, boolean focused, int row, int column)
        {
            setForeground(null);
            setBackground(null);

            setRenderer(row, column);
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            return this;
        }

        public void setRenderer(int row, int column) {

            if(column == 0)
                setHorizontalAlignment(SwingConstants.CENTER);
            else if (column == 1) {
                setHorizontalAlignment(SwingConstants.RIGHT);

                for (int i=0;i<highlightIndex.size(); i++) {
                    if(row == ((Integer)highlightIndex.elementAt(i)).intValue()) {
                        setForeground(Color.blue);
                        break;
                    }
                }

                if (row == flashIndex)
                    setBackground(Color.orange);
            }
            if (row < startEnabling || row > endEnabling && grayDisabledRange)
                    setForeground(Color.lightGray);
        }
    }
}
