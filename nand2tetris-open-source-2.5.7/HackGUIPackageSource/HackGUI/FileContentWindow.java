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

import java.io.*;
import java.awt.*;
import javax.swing.*;

/**
 * This class represents a window which shows the contents of a specified file.
 */
public class FileContentWindow extends JFrame {

    // The text area on which the content of the file is shown.
    private JTextArea fileContent = new JTextArea();

    // A reader used for reading the file.
    private BufferedReader reader;

    // The name of the file which is currently displayed.
    private String displayedFileName;

    // If true, loads the file's contents even if the filename wasn't changed
    private boolean loadAnyway;

    // The scroll pane of this component.
    private JScrollPane scrollPane;

    /**
     * Constructs a new FileContentWindow.
     */
    public FileContentWindow() {

        jbInit();
    }

    /**
     * Calling this method causes the next call to setContent to load the file
     * even if its name wasn't changed.
     */
    public void loadAnyway() {
    	loadAnyway = true;
    }

    /**
     * Sets the content of this window.
     */
    public void setContent(File fileName) {
	if (loadAnyway || !fileName.equals(displayedFileName)) {
            displayedFileName = fileName.getAbsolutePath();
            fileContent.setText("");
            try {
		reader = new BufferedReader(new FileReader(fileName));
		String line;
		while((line = reader.readLine()) != null) {
                    fileContent.append(line);
                    fileContent.append("\n");
		}
		reader.close();
            } catch (IOException ioe) {}
        }
        fileContent.select(0,0);
    }

    /**
     * Deletes the content of the window.
     */
    public void deleteContent() {
        fileContent.setText("");
    }

    // Initializes this component.
    private void jbInit() {
        fileContent.setEditable(false);
        fileContent.setFont(Utilities.valueFont);
        fileContent.setEnabled(false);
        fileContent.setDisabledTextColor(Color.black);
        scrollPane = new JScrollPane(fileContent);
        scrollPane.setPreferredSize(new Dimension(190, 330));
        setSize(375,372);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
