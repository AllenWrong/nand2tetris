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

package Hack.VMEmulator;

import Hack.ComputerParts.*;
import Hack.Events.*;

/**
 * An interface for the GUI of the VM program. displays a list of instructions and
 * marks the current instruction.
 * Also displays the current program file/directory name and enables the selection of
 * a new file/directory. If a new file/directory is selected, the gui will notify its listeners
 * on the change.
 * This GUI should disable external inputs to the program itself.
 * The instruction codes constants can be found in Hack.VirtualMachine.HVMInstructionSet .
 */
public interface VMProgramGUI extends InteractiveComputerPartGUI {

    /**
     * Registers the given ProgramEventListener as a listener to this GUI.
     */
    public void addProgramListener(ProgramEventListener listener);

    /**
     * Un-registers the given ProgramEventListener from being a listener to this GUI.
     */
    public void removeProgramListener(ProgramEventListener listener);

    /**
     * Notifies all the ProgramEventListeners on a change in the program by creating
     * a ProgramEvent (with the new event type and program's file/directory name) and sending it
     * using the programChanged method to all the listeners.
     */
    public void notifyProgramListeners(byte eventType, String programFileName);

    /**
     * Sets the contents of the gui with the first instructionsLength
	 * instructions from the given array of instructions.
     */
    public void setContents(VMEmulatorInstruction[] instructions,
							int instructionsLength);

    /**
     * Sets the current instruction with the given instruction index.
     */
    public void setCurrentInstruction(int instructionIndex);

    /**
     * Displays the given message.
     */
    public void showMessage(String message);

    /**
     * Hides the displayed message.
     */
    public void hideMessage();
	
	/**
	 * Displays a confirmation window asking the user permission to
	 * use built-in vm functions
	 */
	public boolean confirmBuiltInAccess();

	/**
	 * Displays a notification window with the given message.
	 */
	public void notify(String message);
}
