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
 * An event for notifying an TextFileEventListener on a change in the selected row.
 */
public class TextFileEvent extends EventObject {

    // the changed row index;
    private int rowIndex;

    // the changed row string
    private String rowString;

    /**
     * Constructs a new TextFileEvent with the given source and the selected row
     * string and index.
     */
    public TextFileEvent(Object source, String rowString, int rowIndex) {
        super(source);
        this.rowString = rowString;
        this.rowIndex = rowIndex;
    }

    /**
     * Returns the selected row String.
     */
    public String getRowString() {
        return rowString;
    }

    /**
     * Returns the selected row index.
     */
    public int getRowIndex() {
        return rowIndex;
    }
}
