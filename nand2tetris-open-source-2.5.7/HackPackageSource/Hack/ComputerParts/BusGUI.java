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

import Hack.ComputerParts.*;
import java.awt.*;

/**
 * An interface for the GUI of the bus.
 */
public interface BusGUI extends ComputerPartGUI {

    /**
     * Moves the given value from the source coordinates to the traget coordinates.
     */
    public void move(Point sourceCoordinates, Point targetCoordinates, String value);

    /**
     * Sets the sending speed (in the range 1..HackController.NUMBER_OF_SPEED_UNITS).
     */
    public void setSpeed(int speedUnit);
}
