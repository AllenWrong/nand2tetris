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
 * An event for notifying a ProgramEventListener on a request for a new program.
 */
public class ProgramEvent extends EventObject {

    /**
     * event type for notifying on a new loaded program.
     * supplied data = program file name (String)
     */
    public static final byte LOAD = 1;

    /**
     * event type for notifying on a new saved program.
     * supplied data = program file name (String)
     */
    public static final byte SAVE = 2;

    /**
     * event type for notifying that the program was cleared.
     * supplied data = null
     */
    public static final byte CLEAR = 3;

    // The program's file name.
    private String programFileName;

    // The type of the event.
    private byte eventType;

    /**
     * Constructs a new ProgramEvent with the given source, event type and the new program
     * file name (or null if not applicable).
     */
    public ProgramEvent(Object source, byte eventType, String programFileName) {
        super(source);
        this.programFileName = programFileName;
        this.eventType = eventType;
    }

    /**
     * Returns the new program's file name.
     */
    public String getProgramFileName() {
        return programFileName;
    }

    /**
     * Returns the event type.
     */
    public byte getType() {
        return eventType;
    }
}
