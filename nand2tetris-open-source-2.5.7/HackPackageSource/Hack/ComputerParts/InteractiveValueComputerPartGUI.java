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

package Hack.ComputerParts;

/**
 * An interface for the gui of an InteractiveValueComputerPart.
 */
public interface InteractiveValueComputerPartGUI
 extends ValueComputerPartGUI, InteractiveComputerPartGUI {

    /**
     * Registers the given ComputerPartEventListener as a listener to this GUI.
     */
    public void addListener(ComputerPartEventListener listener);

    /**
     * Un-registers the given ComputerPartEventListener from being a listener to this GUI.
     */
    public void removeListener(ComputerPartEventListener listener);

    /**
     * Notifies all the ComputerPartEventListeners on a change in the computer part by creating
     * a ComputerPartEvent (with the changed index and value) and sending it using the
     * valueChanged method to all the listeners.
     */
    public void notifyListeners(int index, short value);

    /**
     * Notifies all the ComputerPartEventListeners that the gui gained focus by creating
     * a ComputerPartEvent and sending it using the guiGainedFocus method to all the listeners.
     */
    public void notifyListeners();

    /**
     * Enables user input into the computer part.
     */
    public void enableUserInput();

    /**
     * Disables user input into the computer part.
     */
    public void disableUserInput();

    /**
     * Sets the enabled range of this segment.
     * Any address outside this range will be disabled for user input.
     * If gray is true, addresses outside the range will be gray colored.
     */
    public void setEnabledRange(int start, int end, boolean gray);
}
