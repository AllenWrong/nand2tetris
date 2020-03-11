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

import java.util.EventObject;

/**
 * An event for notifying a ControllerEventListener on an action that should be taken,
 * together with a data object which is supplied with the action code.
 */
public class ControllerEvent extends EventObject {

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
     * Action code for changing the speed.
     * supplied data = speed (Integer, 1..ControllerGUI.NUMBER_OF_SPEED_UNITS)
     */
    public static final byte SPEED_CHANGE = 3;

    /**
     * Action code for performing the stop operation.
     * supplied data = null
     */
    public static final byte STOP = 4;

    /**
     * Action code for changing the breakpoints.
     * supplied data = Vector of Breakpoint objects
     */
    public static final byte BREAKPOINTS_CHANGE = 5;

    /**
     * Action code for changing the script file.
     * supplied data = script file (File)
     */
    public static final byte SCRIPT_CHANGE = 6;

    /**
     * Action code for performing the rewind operation.
     * supplied data = null
     */
    public static final byte REWIND = 9;

    /**
     * Action code for changing the animation mode.
     * supplied data = animation code (Integer, out of the animation constants in HackController)
     */
    public static final byte ANIMATION_MODE_CHANGE = 10;

    /**
     * Action code for changing the numeric format.
     * supplied data = format code (Integer, out of the format constants in HackController)
     */
    public static final byte NUMERIC_FORMAT_CHANGE = 11;

    /**
     * Action code for changing the additional display.
     * supplied data = additional display code (Integer, out of the additional display
     * constants in HackController)
     */
    public static final byte ADDITIONAL_DISPLAY_CHANGE = 12;

    /**
     * Action code for showing the controller.
     * supplied data = null
     */
    public static final byte SHOW_CONTROLLER = 13;

    /**
     * Action code for hiding the controller.
     * supplied data = null
     */
    public static final byte HIDE_CONTROLLER = 14;

    /**
     * Action code for disabling change of animation modes in the controller.
     * supplied data = null
     */
    public static final byte DISABLE_ANIMATION_MODE_CHANGE = 15;

    /**
     * Action code for enabling change of animation modes in the controller.
     * supplied data = null
     */
    public static final byte ENABLE_ANIMATION_MODE_CHANGE = 16;

    /**
     * Action code for disabling the SingleStep button in the controller.
     * supplied data = null
     */
    public static final byte DISABLE_SINGLE_STEP = 17;

    /**
     * Action code for enabling the SingleStep button in the controller.
     * supplied data = null
     */
    public static final byte ENABLE_SINGLE_STEP = 18;

    /**
     * Action code for disabling the FastForward button in the controller.
     * supplied data = null
     */
    public static final byte DISABLE_FAST_FORWARD = 19;

    /**
     * Action code for enabling the FastForward button in the controller.
     * supplied data = null
     */
    public static final byte ENABLE_FAST_FORWARD = 20;

    /**
     * Action code for halting the simulator program.
     * supplied data = null
     */
    public static final byte HALT_PROGRAM = 21;

    /**
     * Action code for continuing the simulator program.
     * supplied data = null
     */
    public static final byte CONTINUE_PROGRAM = 22;

    /**
     * Action code for disabling movement (single step, fast forward, rewind).
     * supplied data = null
     */
    public static final byte DISABLE_MOVEMENT = 23;

    /**
     * Action code for enabling movement (single step, fast forward, rewind).
     * supplied data = null
     */
    public static final byte ENABLE_MOVEMENT = 24;

    /**
     * Action code for displaying a message in the controller status line.
     * supplied data = message (String)
     */
    public static final byte DISPLAY_MESSAGE = 25;

    /**
     * Action code for displaying an error message in the controller status line.
     * supplied data = error message (String)
     */
    public static final byte DISPLAY_ERROR_MESSAGE = 26;

    /**
     * Action code for requesting to load a new program.
     * supplied data = null
     */
    public static final byte LOAD_PROGRAM = 27;

    // the action code
    private byte action;

    // the supplied data
    private Object data;

    /**
     * Constructs a new Controller event with given source, the action code and the supplied data.
     */
    public ControllerEvent(Object source, byte action, Object data) {
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
