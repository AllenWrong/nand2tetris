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

import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

/**
 * A FileChooserComponent that enables viewing files during selection.
 */
public class ViewableFileChooserComponent extends FileChooserComponent {

    // A check box used for showing the file's content.
    private JCheckBox viewCheckBox = new JCheckBox();

    // The content window
    private FileContentWindow window;

    // The file to be showed in the window.
    private File contentFile;

    /**
     * Constructs a new ViewableFileChooserComponent.
     */
    public ViewableFileChooserComponent() {
        jbInit();
        window = new FileContentWindow();
    }

    /**
     * Sets the content window's location.
     */
    public void setWindowLocation(int x, int y) {
        window.setLocation(x,y);
    }

    /**
     * Sets the file shown in the content window.
     */
    public void setFileContent() {
        contentFile = new File(getFileName());
        window.setContent(contentFile);
        window.setTitle("File: " + contentFile);
    }

    /**
     * Delete the information currently shown in the content window.
     */
    public void deleteContentFile() {
        window.deleteContent();
        window.setTitle("");
    }

    /**
     * Refreshes the contents of the file.
     */
    public void refresh() {
        window.loadAnyway();
        setFileContent();
    }

    /**
     * Disables the checkbox which shows the content of the file.
     */
    public void disableCheckBox() {
        viewCheckBox.setEnabled(false);
    }

    public void showCurrentFileName() {
        fileName.setText(currentFileName);
        if (viewCheckBox.isSelected()) {
            if (!currentFileName.equals("")) {
                setFileContent();
            }
            else {
                viewCheckBox.setSelected(false);
                window.setVisible(false);
                deleteContentFile();
           }
        }
    }

    // Initializations
    private void jbInit() {
        viewCheckBox.setText("View File");
        viewCheckBox.setFont(Utilities.thinLabelsFont);
        viewCheckBox.setBounds(new Rectangle(407, 12, 76, 23));
        viewCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                viewCheckBox_itemStateChanged(e);
            }
        });

        this.add(viewCheckBox, null);
    }

    /**
     * Implementing the action of pressing the check box.
     */
    public void viewCheckBox_itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            window.setTitle("Loading...");
            window.setVisible(true);
            setFileContent();
        }
        else if (e.getStateChange() == ItemEvent.DESELECTED)
            window.setVisible(false);
    }
}
