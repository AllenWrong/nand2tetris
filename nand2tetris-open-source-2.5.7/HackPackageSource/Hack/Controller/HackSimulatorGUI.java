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

package Hack.Controller;

import javax.swing.*;
import java.io.*;

/**
 * An interface for the gui of the hack simulator.
 */
public interface HackSimulatorGUI {

    /**
     * Displays the given component in the simulator.
     * If another component is already displayed, it will be removed.
     * If the given component is null, removes the currently displayed component.
     */
    public void setAdditionalDisplay(JComponent component);

    /**
     * Opens the program file dialog for choosing a new program.
     */
    public void loadProgram();

    /**
     * Sets the name of the html file that contains the help usage.
     */
    public void setUsageFileName(String fileName);

    /**
     * Sets the name of the html file that contains the "about" information.
     */
    public void setAboutFileName(String fileName);

    /**
     * Returns the name of the help usage file.
     */
    public String getUsageFileName();

    /**
     * Returns the name of the about information file.
     */
    public String getAboutFileName();

    /**
     * Sets the working dir name with the given one.
     */
    public void setWorkingDir(File file);
}
