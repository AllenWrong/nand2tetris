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

import java.awt.*;

/**
 * An interface for the GUI of a computer part that holds values.
 */
public interface ValueComputerPartGUI extends ComputerPartGUI {

    /**
     * Returns the coordinates of the top left corner of the value at the given index.
     */
    public Point getCoordinates(int index);

    /**
     * Sets the element at the given index with the given value.
     */
    public void setValueAt(int index, short value);

    /**
     * Returns the value at the given index in its string representation.
     */
    public String getValueAsString(int index);

    /**
     * Highlights the value at the given index.
     */
    public void highlight(int index);

    /**
     * Hides all highlightes.
     */
    public void hideHighlight();

    /**
     * flashes the value at the given index.
     */
    public void flash(int index);

    /**
     * hides the existing flash.
     */
    public void hideFlash();

    /**
     * Sets the numeric format with the given code (out of the format constants in HackController).
     */
    public void setNumericFormat(int formatCode);

    /**
     * Sets the null value (default value) of this computer part with the given value.
     * If hideNullValue is true, values which are equal to the null value will be
     * hidden.
     */
    public void setNullValue(short value, boolean hideNullValue);
}
