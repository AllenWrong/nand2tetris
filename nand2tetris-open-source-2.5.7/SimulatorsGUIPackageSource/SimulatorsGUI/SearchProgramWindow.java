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

import java.util.StringTokenizer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import HackGUI.*;
import Hack.VirtualMachine.*;

/**
 * This class represents a search window for the use of ProgramComponent.
 */
public class SearchProgramWindow extends JFrame {

    // creating the label of this window
    private JLabel instructionLbl = new JLabel();

    // creating the text field of this window.
    private JTextField instruction = new JTextField();

    // creating ok and cancel buttons.
    private JButton okButton = new JButton();
    private JButton cancelButton = new JButton();

    // creating ok and cancel icons.
    private ImageIcon okIcon = new ImageIcon(Utilities.imagesDir + "ok.gif");
    private ImageIcon cancelIcon = new ImageIcon(Utilities.imagesDir + "cancel.gif");

    // The table of this component.
    private JTable table;

    // an array containing the HVMInstructions.
    private HVMInstruction[] instructions;

    /**
     * Constructs a new SearchWindow.
     */
    public SearchProgramWindow(JTable table, HVMInstruction[] instructions) {
        super("Search");
        this.table = table;

        jbInit();

    }

    /**
     * Sets the array of instructions of this search window.
     */
    public void setInstructions(HVMInstruction[] instructions) {
        this.instructions = instructions;
    }

    /**
     * Returns the index of row which the user searched for.
     */
    private int getSearchedRowIndex() {
        int rowIndex = -1;
        String searchedStr = instruction.getText();
        StringTokenizer tokenizer = new StringTokenizer(searchedStr);
        String firstToken= "", secondToken="", thirdToken="";
        int numOfTokens = tokenizer.countTokens();
        switch(numOfTokens) {
            case 0:
                break;
            case 1:
                firstToken = tokenizer.nextToken();
                for(int i=0; i<instructions.length; i++) {
                    String[] formattedStr = instructions[i].getFormattedStrings();
                    if(formattedStr[0].equalsIgnoreCase(firstToken)) {
                        rowIndex = i;
                        break;
                    }
                }
                break;
            case 2:
                firstToken = tokenizer.nextToken();
                secondToken = tokenizer.nextToken();
                for(int i=0; i<instructions.length; i++) {
                    String[] formattedStr = instructions[i].getFormattedStrings();
                    if(formattedStr[0].equalsIgnoreCase(firstToken) &&
                       formattedStr[1].equalsIgnoreCase(secondToken)) {
                        rowIndex = i;
                        break;
                    }
                }
                break;
            case 3:
                firstToken = tokenizer.nextToken();
                secondToken = tokenizer.nextToken();
                thirdToken = tokenizer.nextToken();

                for(int i=0; i<instructions.length; i++) {
                    String[] formattedStr = instructions[i].getFormattedStrings();
                    if(formattedStr[0].equalsIgnoreCase(firstToken) &&
                       formattedStr[1].equalsIgnoreCase(secondToken) &&
                       formattedStr[2].equalsIgnoreCase(thirdToken)) {
                        rowIndex = i;
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return rowIndex;
    }

    /**
     * Shows the search window.
     */
    public void showWindow() {
        setVisible(true);
        instruction.requestFocus();
    }

    // Initialization of this component.
    private void jbInit() {
        instructionLbl.setFont(Utilities.thinLabelsFont);
        instructionLbl.setText("Text to find :");
        instructionLbl.setBounds(new Rectangle(9, 22, 79, 23));
        this.getContentPane().setLayout(null);
        instruction.setBounds(new Rectangle(82, 25, 220, 18));
        instruction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instruction_actionPerformed(e);
            }
        });
        okButton.setToolTipText("OK");
        okButton.setIcon(okIcon);
        okButton.setBounds(new Rectangle(66, 68, 63, 44));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        cancelButton.setBounds(new Rectangle(190, 68, 63, 44));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        cancelButton.setToolTipText("CANCEL");
        cancelButton.setIcon(cancelIcon);
        this.getContentPane().add(instruction, null);
        this.getContentPane().add(okButton, null);
        this.getContentPane().add(cancelButton, null);
        this.getContentPane().add(instructionLbl, null);

        setSize(320,150);
        setLocation(250,250);
    }

    /**
     * Implementing the action of pressing the OK button.
     */
    public void okButton_actionPerformed(ActionEvent e) {
        try {
            int row = getSearchedRowIndex();
            if(row != -1) {
                Rectangle r = table.getCellRect(row, 0, true);
                table.scrollRectToVisible(r);
                table.setRowSelectionInterval(row,row);
                setVisible(false);
            }
        } catch (NumberFormatException nfe) {}
          catch (IllegalArgumentException iae) {}
    }

    /**
     * Implementing the action of pressing the cancel button.
     */
    public void cancelButton_actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    /**
     * Implementing the action of pressing 'enter' on the text field.
     */
    public void instruction_actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
    }
}
