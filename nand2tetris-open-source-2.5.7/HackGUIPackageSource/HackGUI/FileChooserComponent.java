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
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.*;

/**
 * This class represents a window from which the users can select different types
 * of files, in order to be loaded to the simulator.
 */
public class FileChooserComponent extends JPanel {

    // The label of this component.
    private JLabel fileTypeName = new JLabel();

    // The text field.
    protected JTextField fileName = new JTextField();

    // The browse button.
    private JButton browseButton = new JButton();

    // The file chooser component.
    private JFileChooser fc = new JFileChooser();
    private JFrame fileChooserFrame = new JFrame();

    // The name of the current file.
    protected String currentFileName = "";

    // Creating the browse icon.
    private ImageIcon load = new ImageIcon(Utilities.imagesDir + "open.gif");

    // The vector of listeners the this component.
    private Vector listeners;

    /**
     * Constructs a new FileChooserComponent.
     */
    public FileChooserComponent() {
        listeners = new Vector();
        jbInit();
        fileChooserFrame.setSize(440,250);
        fileChooserFrame.setLocation(250,250);
        fileChooserFrame.setTitle("Choose a file :");
        fileChooserFrame.getContentPane().add(fc);
    }

    /**
     * Registers the given EnterPressedListener as a listener to this GUI.
     */
    public void addListener(EnterPressedListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Un-registers the given EnterPressedListener as a listener to this GUI.
     */
    public void removeListener(EnterPressedListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Notifies all the EnterPressedListeners on an event of pressing the enter
     * button by calling the enterPressed method to all the listeners.
     */
    public void notifyListeners() {
        for (int i=0;i<listeners.size();i++) {
           ((EnterPressedListener)listeners.elementAt(i)).enterPressed();
        }
    }

    /**
     * Sets the name of the file chooser.
     */
    public void setName(String name) {
        fileTypeName.setText(name);
    }

    /**
     * Returns the current file name.
     */
    public String getCurrentFileName() {
        return currentFileName;
    }

    /**
     * Sets the selection of the file chooser to directories only.
     */
     public void setSelectionToDirectories() {
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
     }

    /**
     * Sets the current file name.
     */
    public void setCurrentFileName(String currentName) {
        currentFileName = currentName;
    }

    /**
     * Sets the textfield with the current file name.
     */
    public void showCurrentFileName() {
        fileName.setText(currentFileName);
    }

    /**
     * Sets the filter of the FileChooser component.
     */
    public void setFilter(FileFilter filter) {
        fc.setFileFilter(filter);
    }

    /**
     * Returns true if the file name was changed by the user, false - otherwise.
     */
    public boolean isFileNameChanged() {
         return !currentFileName.equals(fileName.getText());
    }

    /**
     * Returns the file name.
     */
    public String getFileName() {
        return fileName.getText();
    }

    /**
     * Returns the textfield.
     */
    public JTextField getTextField() {
        return fileName;
    }

    /**
     * Sets the directory of the script files.
     */
    public void setScriptDir(String dir) {
        fc.setCurrentDirectory(new File(dir));
    }

    // Initialization of this component.
    private void jbInit() {
        fileTypeName.setFont(Utilities.thinLabelsFont);
        fileTypeName.setText("File Type: ");
        fileTypeName.setBounds(new Rectangle(10, 10, 104, 26));
        this.setLayout(null);
        fileName.setDisabledTextColor(Color.black);
        fileName.setBounds(new Rectangle(118, 13, 221, 22));
        fileName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileName_actionPerformed(e);
            }
        });
        browseButton.setToolTipText("Load File");
        browseButton.setIcon(load);
        browseButton.setBounds(new Rectangle(351, 12, 46, 24));
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseButton_actionPerformed(e);
            }
        });
        currentFileName = "";

        this.add(fileTypeName, null);
        this.add(fileName, null);
        this.add(browseButton, null);
    }

    /**
     * Implementing the action of pressing the browse button.
     */
    public void browseButton_actionPerformed(ActionEvent e) {
        int returnVal = fc.showDialog(FileChooserComponent.this, "Select file");
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            fileName.setText(file.getAbsolutePath());
        }
    }

    /**
     * Implementing the action of pressing 'enter' on the file name text field.
     */
    public void fileName_actionPerformed(ActionEvent e) {
        notifyListeners();
    }
}
