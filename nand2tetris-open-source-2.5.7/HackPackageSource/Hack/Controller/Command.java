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

package Hack.Controller;

/**
 * This class represents a single controller command (with its arguments).
 * It holds the command code, an array of string arguments, one integer argument
 * and the terminator type.
 */
public class Command {

    // commands

    /**
     * A Simulator script command
     */
    public static final byte SIMULATOR_COMMAND = 1;

    /**
     * An output-file script command
     */
    public static final byte OUTPUT_FILE_COMMAND = 2;

    /**
     * A compare-to script command
     */
    public static final byte COMPARE_TO_COMMAND = 3;

    /**
     * An output-list script command
     */
    public static final byte OUTPUT_LIST_COMMAND = 4;

    /**
     * An output script command
     */
    public static final byte OUTPUT_COMMAND = 5;

    /**
     * A breakpoint script command
     */
    public static final byte BREAKPOINT_COMMAND = 6;

    /**
     * A clear-breakpoints script command
     */
    public static final byte CLEAR_BREAKPOINTS_COMMAND = 7;

    /**
     * A repeat script command
     */
    public static final byte REPEAT_COMMAND = 8;

    /**
     * An end-repeat script command
     */
    public static final byte END_REPEAT_COMMAND = 9;

    /**
     * A while script command
     */
    public static final byte WHILE_COMMAND = 10;

    /**
     * An end-while script command
     */
    public static final byte END_WHILE_COMMAND = 11;

    /**
     * An end-script script command
     */
    public static final byte END_SCRIPT_COMMAND = 12;

    /**
     * An echo script command
     */
    public static final byte ECHO_COMMAND = 13;

    /**
     * A clear-echo script command
     */
    public static final byte CLEAR_ECHO_COMMAND = 14;

    // terminators

    /**
     * No script command terminator
     */
    public static final byte NO_TERMINATOR = 0;

    /**
     * A mini step script command terminator
     */
    public static final byte MINI_STEP_TERMINATOR = 1;

    /**
     * A single step script command terminator
     */
    public static final byte SINGLE_STEP_TERMINATOR = 2;

    /**
     * A stop script command terminator
     */
    public static final byte STOP_TERMINATOR = 3;


    // the command code
    private byte code;

    // the command argument (may be an array)
    private Object arg;

    // the type of the terminator of this command
    private byte terminatorType;

    /**
     * Constructs a new command with the given command code and an argument.
     */
    public Command(byte code, Object arg) {
        this.code = code;
        this.arg = arg;
        this.terminatorType = NO_TERMINATOR;
    }

    /**
     * Constructs a new command with the given command code and no argument.
     */
    public Command(byte code) {
        this.code = code;
        this.terminatorType = NO_TERMINATOR;
    }

    /**
     * Returns the command code
     */
    public byte getCode() {
        return code;
    }

    /**
     * Returns the argument of the command.
     */
    public Object getArg() {
        return arg;
    }

    /**
     * Sets the terminator type with the given type.
     * (Assumes legal type).
     */
    public void setTerminator(byte type) {
        terminatorType = type;
    }

    /**
     * Returns the terminator type of the command.
     */
    public byte getTerminator() {
        return terminatorType;
    }
}
