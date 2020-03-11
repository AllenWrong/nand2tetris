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

package Hack.Events;

import java.util.EventObject;

/**
 * An event for notifying on an error.
 */
public class ErrorEvent extends EventObject {

    // the error message
    private String errorMessage;

    /**
     * Constructs a new ErrorEvent with the given source and error message.
     */
    public ErrorEvent(Object source, String errorMessage) {
        super(source);
        this.errorMessage = errorMessage;
    }

    /**
     * Returns the error message.
     * If null, the error display should be cleared.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
