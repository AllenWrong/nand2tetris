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

import java.util.EventObject;

/**
 * An event for notifying a ComputerPartEventListener on a change in the ComputerPart's contents,
 * which was done through the ComputerPart's GUI.
 */
public class ComputerPartEvent extends EventObject {

    // the index of the ComputerPart location that was changed
    private int index;

    // the changed value
    private short value;

    /**
     * Constructs a new ComputerPartEvent with the given source.
     */
    public ComputerPartEvent(ComputerPartGUI source) {
        super(source);
    }

    /**
     * Constructs a new ComputerPartEvent with the given source, the index (in which the value
     * was changed) and the new value.
     */
    public ComputerPartEvent(ComputerPartGUI source, int index, short value) {
        super(source);
        this.index = index;
        this.value = value;
    }

    /**
     * Returns the index of the ComputerPart location that was changed.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the new value.
     */
    public short getValue() {
        return value;
    }
}
