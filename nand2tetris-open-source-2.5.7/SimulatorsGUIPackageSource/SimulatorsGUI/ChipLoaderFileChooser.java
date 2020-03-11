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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * This class represents the gui of the chip loader file chooser.
 */
public class ChipLoaderFileChooser extends JFrame {

    // creating the file chooser components.
    private FileChooserComponent workingDir = new FileChooserComponent();
    private FileChooserComponent builtInDir = new FileChooserComponent();

    // creating the ok and cancel icons.
    private ImageIcon okIcon = new ImageIcon(Utilities.imagesDir + "ok.gif");
    private ImageIcon cancelIcon = new ImageIcon(Utilities.imagesDir + "cancel.gif");

    // creating the ok and cancel buttons.
    private JButton okButton = new JButton();
    private JButton cancelButton = new JButton();

    // the vector containing the listeners to this component.
    private Vector listeners;

    /**
     * Constructs a new ChipLoaderFileChooser.
     */
    public ChipLoaderFileChooser() {
        super ("Directories Selection");
        listeners = new Vector();
        setSelectionToDirectory();
        setNames();
        jbInit();
    }

    /**
     * Shows the file chooser.
     */
    public void showWindow() {
        setVisible(true);
        workingDir.getTextField().requestFocus();
    }

    /**
     * Registers the given FilesTypeListener as a listener to this component.
     */
    public void addListener (FilesTypeListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Un-registers the given FilesTypeListener from being a listener to this component.
     */
    public void removeListener (FilesTypeListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Notify all the FilesTypeListeners on actions taken in it, by creating a
     * FilesTypeEvent and sending it using the filesNamesChanged method to all
     * of the listeners.
     */
    public void notifyListeners (String working, String builtIn/*, String composite*/) {
        FilesTypeEvent event = new FilesTypeEvent(this,working, builtIn, null/*, composite*/);

        for(int i=0;i<listeners.size();i++) {
            ((FilesTypeListener)listeners.elementAt(i)).filesNamesChanged(event);
        }
    }

    /**
     * Sets the current HDL directory.
     */
    public void setWorkingDir(File file) {
        workingDir.setCurrentFileName(file.getName());
        workingDir.showCurrentFileName();
    }

    /**
     * Sets the BuiltIn HDL directory.
     */
    public void setBuiltInDir (File file) {
        builtInDir.setCurrentFileName(file.getName());
        builtInDir.showCurrentFileName();
    }

    // Sets the selection mode to directories only.
    private void setSelectionToDirectory() {
        workingDir.setSelectionToDirectories();
        builtInDir.setSelectionToDirectories();
    }

    // sets the names of the file choosers.
    private void setNames() {
        workingDir.setName("Working Dir :");
        builtInDir.setName("BuiltIn Dir :");
    }

    // Initializes this component.
    private void jbInit() {
        this.getContentPane().setLayout(null);
        workingDir.setBounds(new Rectangle(5, 2, 485, 48));
        builtInDir.setBounds(new Rectangle(5, 38, 485, 48));
        okButton.setToolTipText("OK");
        okButton.setIcon(okIcon);
        okButton.setBounds(new Rectangle(90, 95, 63, 44));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        cancelButton.setBounds(new Rectangle(265, 95, 63, 44));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        cancelButton.setToolTipText("CANCEL");
        cancelButton.setIcon(cancelIcon);
        this.getContentPane().add(workingDir, null);
        this.getContentPane().add(builtInDir, null);
        this.getContentPane().add(okButton, null);
        this.getContentPane().add(cancelButton, null);

        setSize(470,210);
        setLocation(250,250);
    }

    /**
     * Implementing the action of pressing the ok button.
     */
    public void okButton_actionPerformed(ActionEvent e) {

        String working = null;
        String builtIn = null;

        if(workingDir.isFileNameChanged()) {
            working = workingDir.getFileName();
            workingDir.setCurrentFileName(working);
            workingDir.showCurrentFileName();
        }

        if(builtInDir.isFileNameChanged()) {
            builtIn = builtInDir.getFileName();
            builtInDir.setCurrentFileName(builtIn);
            builtInDir.showCurrentFileName();
        }

        if(!(working == null && builtIn == null)) {
            notifyListeners(working,builtIn);
        }
        setVisible(false);
    }

    /**
     * Implementing the action of pressing the cancel button.
     */
    public void cancelButton_actionPerformed(ActionEvent e) {
        workingDir.showCurrentFileName();
        builtInDir.showCurrentFileName();
        setVisible(false);
    }
}
