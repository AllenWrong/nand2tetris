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

package Hack.Assembler;

/**
 * An Exception for errors that occur in the Assembler
 */
public class AssemblerException extends Exception {

    /**
     * Constructs a new AssemblerException with the given message.
     */
    public AssemblerException(String message) {
        super(message);
    }

    /**
     * Constructs a new AssemblerException with the given message and Line number.
     */
    public AssemblerException(String message, int lineNumber) {
        super("In line " + lineNumber + ", " + message);
    }
}
