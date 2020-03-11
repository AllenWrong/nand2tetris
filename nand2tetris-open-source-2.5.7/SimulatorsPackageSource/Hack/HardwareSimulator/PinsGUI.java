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

package Hack.HardwareSimulator;

import Hack.ComputerParts.*;
import Hack.Gates.*;

/**
 * An interface for the GUI of a pins list.
 * Every pin row contains the pin's name and value (which may be in a specified width).
 * The GUI can display its values in decimal, hexadecimal or binary format.
 */
public interface PinsGUI extends InteractiveValueComputerPartGUI {
    /**
     * Sets the pins list's contents with the given array of PinInfo objects.
     */
    public void setContents(PinInfo[] pins);

    /**
     * Set the pins to be dimmed or not dimmed.
     */
    public void setDimmed(boolean cond);

}
