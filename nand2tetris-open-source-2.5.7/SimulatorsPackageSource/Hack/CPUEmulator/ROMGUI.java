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

package Hack.CPUEmulator;

import Hack.ComputerParts.*;
import Hack.Events.*;

/**
 * An interface for a computer ROM GUI, which is a Pointed Memory GUI.
 * Apart from having the usual pointed memory GUI functionallity, the ROM GUI displayes
 * the current program (a .hack file name) and enables the selection of a new program.
 * If a new program is selected, the GUI will notify its listeners on this change.
 * The ROM GUI can also display its contents as an Assembly language instructions.
 */
public interface ROMGUI extends PointedMemoryGUI {

    /**
     * Registers the given ProgramEventListener as a listener to this GUI.
     */
    public void addProgramListener(ProgramEventListener listener);

    /**
     * Un-registers the given ProgramEventListener from being a listener to this GUI.
     */
    public void removeProgramListener(ProgramEventListener listener);

    /**
     * Notifies all the ProgramEventListeners on a change in the ROM's program by creating
     * a ProgramEvent (with the new event type and program's file name) and sending it using the
     * programChanged method to all the listeners.
     */
    public void notifyProgramListeners(byte eventType, String programFileName);

    /**
     * Sets the current program file name with the given name.
     */
    public void setProgram(String programFileName);

    /**
     * Displays the given message.
     */
    public void showMessage(String message);

    /**
     * Hides the displayed message.
     */
    public void hideMessage();
}
