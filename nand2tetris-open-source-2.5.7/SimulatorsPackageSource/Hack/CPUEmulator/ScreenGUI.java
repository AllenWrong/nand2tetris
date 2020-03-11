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

/**
 * A interface for a computer screen GUI.
 */
public interface ScreenGUI extends ComputerPartGUI {

    /**
     * Updates the screen at the given index with the given value
     * (Assumes legal index)
     */
    public void setValueAt(int index, short value);

    /**
     * Updates the screen's contents with the given values array.
     */
    public void setContents(short[] values);

    /**
     * Refreshes the screen
     */
    public void refresh();

    /**
     * Starts animating the screen display
     */
    public void startAnimation();

    /**
     * Stops animating the screen display
     */
    public void stopAnimation();
}
