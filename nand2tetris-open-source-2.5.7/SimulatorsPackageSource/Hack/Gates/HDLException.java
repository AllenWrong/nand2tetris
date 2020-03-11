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

package Hack.Gates;

/**
 * An exception for errors in the HDL file.
 */
public class HDLException extends Exception {

    /**
     * Constructs a new HDLException with the given message, HDL file name and Line number.
     */
    public HDLException(String message, String HDLName, int lineNumber) {
        super("In HDL file " + HDLName + ", Line " + lineNumber + ", " + message);
    }

    /**
     * Constructs a new HDLException with the given message and HDL file name.
     */
    public HDLException(String message, String HDLName) {
        super("In HDL file " + HDLName + ", " + message);
    }

    /**
     * Constructs a new HDLException with the given message.
     */
    public HDLException(String message) {
        super(message);
    }
}
