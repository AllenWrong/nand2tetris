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

package Hack.Translators;

import java.util.EventObject;

/**
 * An event for notifying a HackTranslatorEventListener on an action that should be taken,
 * together with a data object which is supplied with the action code.
 */
public class HackTranslatorEvent extends EventObject {

    /**
     * Action code for performing the single step operation.
     * supplied data = null
     */
    public static final byte SINGLE_STEP = 1;

    /**
     * Action code for performing the fast forward operation.
     * supplied data = null
     */
    public static final byte FAST_FORWARD = 2;

    /**
     * Action code for performing the stop operation.
     * supplied data = null
     */
    public static final byte STOP = 3;

    /**
     * Action code for performing the rewind operation.
     * supplied data = null
     */
    public static final byte REWIND = 4;

    /**
     * Action code for performing the Full Compilation operation.
     * supplied data = null
     */
    public static final byte FULL_COMPILATION = 5;

    /**
     * Action code for saving the dest file.
     * supplied data = dest file name (String)
     */
    public static final byte SAVE_DEST = 6;

    /**
     * Action code for loading a source file.
     * supplied data = source file name (String)
     */
    public static final byte SOURCE_LOAD = 7;

    // the action code
    private byte action;

    // the supplied data
    private Object data;

    /**
     * Constructs a new HackTranslatorEvent with given source, the action code and the supplied data.
     */
    public HackTranslatorEvent(Object source, byte action, Object data) {
        super(source);
        this.action = action;
        this.data = data;
    }

    /**
     * Returns the event's action code.
     */
    public byte getAction() {
        return action;
    }

    /**
     * Returns the event's supplied data.
     */
     public Object getData() {
        return data;
     }
}
