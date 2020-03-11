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

package Hack.HardwareSimulator;

import Hack.Controller.*;

/**
 * An event for notifying the hardware simulator on actions taken in the controller.
 */
public class HardwareSimulatorControllerEvent extends ControllerEvent {

    /**
     * Action code for clicking on the TickTock button.
     * supplied data = null.
     */
    public static final byte TICKTOCK_CLICKED = 100;

    /**
     * Action code for clicking on the Eval button.
     * supplied data = null.
     */
    public static final byte EVAL_CLICKED = 101;

    /**
     * Action code for selecting a new chip name.
     * supplied data = The new chip file (File)
     */
    public static final byte CHIP_CHANGED = 102;

    /**
     * Action code for disabling the TickTock button.
     * supplied data = null
     */
    public static final byte DISABLE_TICKTOCK = 105;

    /**
     * Action code for enabling the TickTock button.
     * supplied data = null
     */
    public static final byte ENABLE_TICKTOCK = 106;

    /**
     * Action code for disabling the Eval button.
     * supplied data = null
     */
    public static final byte DISABLE_EVAL = 107;

    /**
     * Action code for enabling the Eval button.
     * supplied data = null
     */
    public static final byte ENABLE_EVAL = 108;


    /**
     * Constructs a new HardwareSimulatorControllerEvent with given source, the action code and
     * the supplied data.
     */
    public HardwareSimulatorControllerEvent(Object source, byte action, Object data) {
        super(source, action, data);
    }
}
