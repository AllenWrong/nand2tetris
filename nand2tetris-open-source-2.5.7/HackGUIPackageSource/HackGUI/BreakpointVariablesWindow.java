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

import Hack.Controller.Breakpoint;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This class represents the window of adding or editing a breakpoint.
 */
public class BreakpointVariablesWindow extends JFrame {

    // Creating labels.
    private JLabel nameLbl = new JLabel();
    private JLabel valueLbl = new JLabel();

    // Creating text fields.
    private JTextField nameTxt = new JTextField();
    private JTextField valueTxt = new JTextField();

    // Creating the combo box of variables.
    private JComboBox nameCombo = new JComboBox();

    // Creating the ok and cancel buttons.
    private JButton okButton = new JButton();
    private JButton cancelButton = new JButton();

    // Creating ok and cancel icons.
    private ImageIcon okIcon = new ImageIcon(Utilities.imagesDir + "ok.gif");
    private ImageIcon cancelIcon = new ImageIcon(Utilities.imagesDir + "cancel.gif");

    // A vector conatining the listeners to this component.
    private Vector listeners;

    // The breakpoint which is being added or changed.
    private Breakpoint breakpoint;

    /**
     * Constructing a new BreakpointVariablesWindow.
     */
    public BreakpointVariablesWindow() {
        super("Breakpoint Variables");
        listeners = new Vector();
        jbInit();
    }

    /**
     * Registers the given BreakpointChangedListener as a listener to this component.
     */
    public void addListener (BreakpointChangedListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Un-registers the given BreakpointChangedListener from being a listener to this component.
     */
    public void removeListener (BreakpointChangedListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Notify all the BreakpointChangedListeners on actions taken in it, by creating a
     * BreakpointChangedEvent and sending it using the breakpointChanged method to all
     * of the listeners.
     */
    public void notifyListeners () {
        BreakpointChangedEvent event = new BreakpointChangedEvent(this,breakpoint);
        for(int i=0;i<listeners.size();i++) {
            ((BreakpointChangedListener)listeners.elementAt(i)).breakpointChanged(event);
        }
    }

    /**
     * Sets the list of recognized variables with the given one.
     */
    public void setVariables(String[] newVars) {
        for (int i=0;i<newVars.length;i++) {
            nameCombo.addItem(newVars[i]);
        }
    }

    /**
     * Sets the name of the breakpoint.
     */
    public void setBreakpointName (String name) {
        nameTxt.setText(name);
    }

    /**
     * Sets the value of the breakpoint.
     */
    public void setBreakpointValue (String value) {
        valueTxt.setText(value);
    }

    /**
     * Sets the selected value in the combobox to the given index.
     */
    public void setNameCombo (int index) {
        nameCombo.setSelectedIndex(index);
    }

    /**
     * Shows the breakpoint variables window.
     */
    public void showWindow() {
        nameTxt.requestFocus();
        setVisible(true);
    }

    // Initializes this component.
    private void jbInit() {
        nameLbl.setFont(Utilities.thinLabelsFont);
        nameLbl.setText("Name :");
        nameLbl.setBounds(new Rectangle(9, 10, 61, 19));
        this.getContentPane().setLayout(null);
        valueLbl.setBounds(new Rectangle(9, 42, 61, 19));
        valueLbl.setFont(Utilities.thinLabelsFont);
        valueLbl.setText("Value :");
        nameTxt.setBounds(new Rectangle(53, 10, 115, 19));
        valueTxt.setBounds(new Rectangle(53, 42, 115, 19));
        nameCombo.setBounds(new Rectangle(180, 10, 124, 19));
        nameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameCombo_actionPerformed(e);
            }
        });
        okButton.setToolTipText("Ok");
        okButton.setIcon(okIcon);
        okButton.setBounds(new Rectangle(61, 74, 63, 44));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        cancelButton.setBounds(new Rectangle(180, 74, 63, 44));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        cancelButton.setToolTipText("Cancel");
        cancelButton.setIcon(cancelIcon);
        this.getContentPane().add(nameLbl, null);
        this.getContentPane().add(valueLbl, null);
        this.getContentPane().add(nameTxt, null);
        this.getContentPane().add(valueTxt, null);
        this.getContentPane().add(okButton, null);
        this.getContentPane().add(cancelButton, null);
        this.getContentPane().add(nameCombo, null);

        setSize(320,160);
        setLocation(500,250);
    }

    /**
     * Implementing the action of pressing the ok button.
     */
    public void okButton_actionPerformed(ActionEvent e) {
        breakpoint = new Breakpoint(nameTxt.getText(),valueTxt.getText());
        setVisible(false);
        notifyListeners();
    }

    /**
     * Implementing the action of pressing the cancel button.
     */
    public void cancelButton_actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    /**
     * Implementing the action of changing the selected item in the combo box.
     */
    public void nameCombo_actionPerformed(ActionEvent e) {
        String name = (String)nameCombo.getSelectedItem();
        nameTxt.setText(name);
    }
}
