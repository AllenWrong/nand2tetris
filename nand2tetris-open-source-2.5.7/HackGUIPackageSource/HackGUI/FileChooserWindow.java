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
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.filechooser.FileFilter;

/**
 * This class repersents the GUI of the component which allows the user to load
 * a certain file.
 */
public class FileChooserWindow extends JFrame implements EnterPressedListener {

    // Creating the file chooser component.
    private ViewableFileChooserComponent fileChooser;

    // Creating the ok and cancel buttons.
    private JButton okButton = new JButton();
    private JButton cancelButton = new JButton();

    // Creating the icons.
    private ImageIcon okIcon = new ImageIcon(Utilities.imagesDir + "ok.gif");
    private ImageIcon cancelIcon = new ImageIcon(Utilities.imagesDir + "cancel.gif");

    // the listeners to this component.
    private Vector listeners;

    /**
     * Constructs a new FilesChooserWindow.
     */
    public FileChooserWindow(FileFilter filter) {
        listeners = new Vector();
        fileChooser = new ViewableFileChooserComponent();
        fileChooser.setFilter(filter);

        jbInit();
    }

    /**
     * Shows the file chooser window.
     */
    public void showWindow() {
        setVisible(true);
        fileChooser.getTextField().requestFocus();
    }

    /**
     * Called when the user pressed the enter button.
     */
    public void enterPressed() {
        String file = null;
        file = fileChooser.getFileName();
        fileChooser.setCurrentFileName(file);
        if(!(file == null))
            notifyListeners(file);
        setVisible(false);
    }

    /**
     * Sets the file name (which is written inside the text field).
     */
    public void setFileName(String name) {
        fileChooser.setCurrentFileName(name);
        fileChooser.showCurrentFileName();
    }

    /**
     * Sets the name of the file chooser.
     */
    public void setName(String name) {
        fileChooser.setName(name);
    }

    /**
     * Returns the textfield.
     */
    public JTextField getTextField() {
        return fileChooser.getTextField();
    }

    // Initialization.
    private void jbInit() {
        fileChooser.addListener(this);
        fileChooser.setWindowLocation(647,3);
        this.getContentPane().setLayout(null);
        setTitle("Files selection");
        fileChooser.setBounds(new Rectangle(5, 2, 482, 48));
        okButton.setToolTipText("OK");
        okButton.setIcon(okIcon);
        okButton.setBounds(new Rectangle(124, 64, 63, 44));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        cancelButton.setBounds(new Rectangle(282, 63, 63, 44));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        cancelButton.setToolTipText("CANCEL");
        cancelButton.setIcon(cancelIcon);
        this.getContentPane().add(fileChooser, null);
        this.getContentPane().add(cancelButton, null);
        this.getContentPane().add(okButton, null);
        setSize(496,150);
        setLocation(145,250);
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
    public void notifyListeners (String fileName) {
        FilesTypeEvent event = new FilesTypeEvent(this,fileName, null, null);

        for(int i=0;i<listeners.size();i++) {
            ((FilesTypeListener)listeners.elementAt(i)).filesNamesChanged(event);
        }
    }

    /**
     * Implementing the action of pressing the cancel button.
     */
    public void cancelButton_actionPerformed(ActionEvent e) {
        fileChooser.showCurrentFileName();
        setVisible(false);
    }

    /**
     * Implementing the action of pressing the ok button.
     */
    public void okButton_actionPerformed(ActionEvent e) {

        String file = null;
        file = fileChooser.getFileName();
        fileChooser.setCurrentFileName(file);
        setVisible(false);
        if(!(file == null)) {
            notifyListeners(file);
        }
    }
}
