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
import Hack.VMEmulator.*;
import Hack.Events.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import Hack.VirtualMachine.*;

/**
 * This class represents the gui of a Program.
 */
public class ProgramComponent extends JPanel implements VMProgramGUI {

    // A vector containing the listeners to this object.
    private Vector listeners;

    // A vector containing the error listeners to this object.
    private Vector errorEventListeners;

    // The table representing this program
    protected JTable programTable;

    // The model of the table;
    private ProgramTableModel model;

    // The HVMInstructions of this program.
    protected VMEmulatorInstruction[] instructions;

    // Creating the browse button.
    protected MouseOverJButton browseButton = new MouseOverJButton();

    // Creating the icon of the button.
    private ImageIcon browseIcon = new ImageIcon(Utilities.imagesDir + "open2.gif");

    // The file chooser window.
    //private FileChooserWindow fileChooser = new FileChooserWindow(null);
    private JFileChooser fileChooser = new JFileChooser();

    // The current instruction index (yellow background).
    private int instructionIndex;

    // The text field with the message (for example "Loading...").
    private JTextField messageTxt = new JTextField();

    // The cell renderer of this table.
    private ColoredTableCellRenderer coloredRenderer = new ColoredTableCellRenderer();

    // Creating the search button.
    private MouseOverJButton searchButton = new MouseOverJButton();

    // Creating the icon for the search button.
    private ImageIcon searchIcon = new ImageIcon(Utilities.imagesDir + "find.gif");

    // The window of searching a specific location in memory.
    private SearchProgramWindow searchWindow;

    // The scroll pane on which the table is placed.
    private JScrollPane scrollPane;

    // The name of this component ("Program :").
    private JLabel nameLbl = new JLabel();

    // Creating the clear button.
    protected MouseOverJButton clearButton = new MouseOverJButton();

    // Creating the icon for the clear button.
    private ImageIcon clearIcon = new ImageIcon(Utilities.imagesDir + "smallnew.gif");

    /**
     * Constructs a new ProgramComponent.
     */
    public ProgramComponent() {
        listeners = new Vector();
        errorEventListeners = new Vector();
        instructions = new VMEmulatorInstruction[0];
        model = new ProgramTableModel();
        programTable = new JTable(model);
        programTable.setDefaultRenderer(programTable.getColumnClass(0), coloredRenderer);
        searchWindow = new SearchProgramWindow(programTable, instructions);

        jbInit();

    }

    /**
     * Registers the given ProgramEventListener as a listener to this GUI.
     */
    public void addProgramListener(ProgramEventListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Un-registers the given ProgramEventListener from being a listener to this GUI.
     */
    public void removeProgramListener(ProgramEventListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Notifies all the ProgramEventListeners on a change in the program by creating a
     * ProgramEvent (with the new event type and program's directory name) and sending it
     * using the programChanged method to all the listeners.
     */
    public void notifyProgramListeners(byte eventType, String programFileName) {
        ProgramEvent event = new ProgramEvent(this, eventType, programFileName);
        for(int i=0;i<listeners.size();i++) {
            ((ProgramEventListener)listeners.elementAt(i)).programChanged(event);
        }
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
     * Sets the working directory with the given directory File.
     */
    public void setWorkingDir(File file) {
        fileChooser.setCurrentDirectory(file);
    }

    /**
     * Sets the contents of the gui with the first instructionsLength
	 * instructions from the given array of instructions.
     */
    public synchronized void setContents(VMEmulatorInstruction[] newInstructions,
										 int newInstructionsLength) {
        instructions = new VMEmulatorInstruction[newInstructionsLength];
        System.arraycopy(newInstructions,0,instructions,0,newInstructionsLength);
        programTable.revalidate();
        try {
            wait(100);
        } catch (InterruptedException ie) {}
        searchWindow.setInstructions(instructions);
    }

    /**
     * Sets the current instruction with the given instruction index.
     */
    public void setCurrentInstruction(int instructionIndex) {
        this.instructionIndex = instructionIndex;
        Utilities.tableCenterScroll(this, programTable, instructionIndex);
    }

    /**
     * Resets the contents of this ProgramComponent.
     */
    public void reset() {
        instructions = new VMEmulatorInstruction[0];
        programTable.clearSelection();
        repaint();
    }

    /**
     * Opens the program file chooser for loading a program.
     */
    public void loadProgram() {
        int returnVal = fileChooser.showDialog(this, "Load Program");
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            notifyProgramListeners(ProgramEvent.LOAD,
                                   fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Implementing the action of pressing the browse button.
     */
    public void browseButton_actionPerformed(ActionEvent e) {
        loadProgram();
    }

    /**
     * Hides the displayed message.
     */
    public void hideMessage() {
        messageTxt.setText("");
        messageTxt.setVisible(false);
        searchButton.setVisible(true);
        clearButton.setVisible(true);
        browseButton.setVisible(true);
    }

    /**
     * Displays the given message.
     */
    public void showMessage(String message) {
        messageTxt.setText(message);
        messageTxt.setVisible(true);
        searchButton.setVisible(false);
        clearButton.setVisible(false);
        browseButton.setVisible(false);
    }

    // Determines the width of each column in the table.
    private void determineColumnWidth() {
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = programTable.getColumnModel().getColumn(i);
            if (i == 0)
                column.setPreferredWidth(30);
            else if (i==1)
                column.setPreferredWidth(40);
            else if(i==2)
                column.setPreferredWidth(100);
        }
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
            "Are you sure you want to clear the program?",
            "Warning Message",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[2]);

        if(pressedButtonValue==JOptionPane.YES_OPTION)
            notifyProgramListeners(ProgramEvent.CLEAR, null);
    }

    /**
     * Sets the number of visible rows.
     */
    public void setVisibleRows(int num) {
        int tableHeight = num * programTable.getRowHeight();
        scrollPane.setSize(getTableWidth(), tableHeight + 3);
        setPreferredSize(new Dimension(getTableWidth(), tableHeight + 30));
        setSize(getTableWidth(), tableHeight + 30);
    }

    /**
     * Sets the name label.
     */
    public void setNameLabel (String name) {
        nameLbl.setText(name);
    }

    /**
     * Returns the width of the table.
     */
    public int getTableWidth() {
        return 225;
    }

    // Initialization of this component.
    private void jbInit() {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new VMFileFilter());
        programTable.getTableHeader().setReorderingAllowed(false);
        programTable.getTableHeader().setResizingAllowed(false);
        scrollPane = new JScrollPane(programTable);
        scrollPane.setLocation(0,27);
        browseButton.setToolTipText("Load Program");
        browseButton.setIcon(browseIcon);
        browseButton.setBounds(new Rectangle(119, 2, 31, 24));
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseButton_actionPerformed(e);
            }
        });
        messageTxt.setBackground(SystemColor.info);
        messageTxt.setEnabled(false);
        messageTxt.setFont(Utilities.labelsFont);
        messageTxt.setPreferredSize(new Dimension(70, 20));
        messageTxt.setDisabledTextColor(Color.red);
        messageTxt.setEditable(false);
        messageTxt.setBounds(new Rectangle(91, 2, 132, 23));
        messageTxt.setVisible(false);

        searchButton.setToolTipText("Search");
        searchButton.setIcon(searchIcon);
        searchButton.setBounds(new Rectangle(188, 2, 31, 24));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchButton_actionPerformed(e);
            }
        });
        this.setForeground(Color.lightGray);
        this.setLayout(null);
        nameLbl.setText("Program");
        nameLbl.setBounds(new Rectangle(5, 5, 73, 20));
        nameLbl.setFont(Utilities.labelsFont);

        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearButton_actionPerformed(e);
            }
        });
        clearButton.setBounds(new Rectangle(154, 2, 31, 24));
        clearButton.setIcon(clearIcon);
        clearButton.setToolTipText("Clear");
        this.add(scrollPane, null);
        this.add(nameLbl, null);
        this.add(searchButton, null);
        this.add(clearButton, null);
        this.add(messageTxt, null);
        this.add(browseButton, null);
        determineColumnWidth();
        programTable.setTableHeader(null);
        setBorder(BorderFactory.createEtchedBorder());
    }


    // An inner class representing the model of the CallStack table.
    class ProgramTableModel extends AbstractTableModel {

        /**
         * Returns the number of columns.
         */
        public int getColumnCount() {
            return 3;
        }

        /**
         * Returns the number of rows.
         */
        public int getRowCount() {
            return instructions.length;
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

            String[] formattedString = instructions[row].getFormattedStrings();

            switch(col) {
                case 0:
                    short index = instructions[row].getIndexInFunction();
                    if (index >= 0)
                        return new Short(index);
                    else
                        return "";
                case 1:
                    return formattedString[0];
                case 2:
                    return formattedString[1] + " " + formattedString[2];
                default:
                    return null;

            }
        }

        /**
         * Returns true of this table cells are editable, false -
         * otherwise.
         */
        public boolean isCellEditable(int row, int col){
            return false;
        }
    }

    // An inner class which implemets the cell renderer of the program table, giving
    // the feature of coloring the background of a specific cell.
    class ColoredTableCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent
            (JTable table, Object value, boolean selected, boolean focused, int row, int column)
        {
            setEnabled(table == null || table.isEnabled());
            setBackground(null);
            setForeground(null);

            if(column==0) {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
            else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            if(row==instructionIndex)
                setBackground(Color.yellow);
            else {
                HVMInstruction currentInstruction = instructions[row];
                String op = (currentInstruction.getFormattedStrings())[0];
                if (op.equals("function") && (column == 1 || column == 2))
                    setBackground(new Color(190,171,210));
            }

            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            return this;
        }
    }

	/**
	 * Displays a confirmation window asking the user permission to
	 * use built-in vm functions
	 */
	public boolean confirmBuiltInAccess() {
		String message =
			"No implementation was found for some functions which are called in the VM code.\n" +
			"The VM Emulator provides built-in implementations for the OS functions.\n" +
			"If available, should this built-in implementation be used for functions which were not implemented in the VM code?";
		return (JOptionPane.showConfirmDialog(this.getParent(),
											  message,
											  "Confirmation Message",
											  JOptionPane.YES_NO_OPTION,
											  JOptionPane.QUESTION_MESSAGE) ==
				JOptionPane.YES_OPTION);
	}

	/**
	 * Displays a notification window with the given message.
	 */
	public void notify(String message) {
		JOptionPane.showMessageDialog(this.getParent(),
									  message,
									  "Information Message",
									  JOptionPane.INFORMATION_MESSAGE);
	}
}
