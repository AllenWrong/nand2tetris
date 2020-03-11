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

import Hack.Events.*;

/**
 * An interface for a computer memory GUI.
 * Every memory row contains an address and a value.
 * The Memory GUI can display its contents in decimal, hexadecimal or binary format.
 */
public interface MemoryGUI extends InteractiveValueComputerPartGUI {

    /**
     * Registers the given ClearEventListener as a listener to this GUI.
     */
    public void addClearListener(ClearEventListener listener);

    /**
     * Un-registers the given ClearEventListener from being a listener to this GUI.
     */
    public void removeClearListener(ClearEventListener listener);

    /**
     * Notifies all the ClearEventListeners on a a request for clear by creating
     * a ClearEvent and sending it using the clearRequested method to all the listeners.
     */
    public void notifyClearListeners();

    /**
     * Sets the memory contents with the given values array.
     */
    public void setContents(short[] values);

    /**
     * Selects the commands in the range fromIndex..toIndex
     */
    public void select(int fromIndex, int toIndex);

    /**
     * Hides all selections.
     */
    public void hideSelect();

    /**
     * Scrolls the memory such that the given address will be on top.
     */
    public void scrollTo(int address);
}
