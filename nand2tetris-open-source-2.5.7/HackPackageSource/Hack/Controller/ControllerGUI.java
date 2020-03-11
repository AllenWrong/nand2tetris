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

import java.util.*;
import javax.swing.*;
import java.io.*;

/**
 * An interface for a GUI of the hack controller.
 */
public interface ControllerGUI {

    /**
     * Registers the given ControllerEventListener as a listener to this GUI.
     */
    public void addControllerListener(ControllerEventListener listener);

    /**
     * Un-registers the given ControllerEventListener from being a listener to this GUI.
     */
    public void removeControllerListener(ControllerEventListener listener);

    /**
     * Notify all the ControllerEventListeners on actions taken in it, by creating
     * a ControllerEvent (with the action and supplied data) and sending it using
     * the actionPerformed method to all the listeners.
     */
    public void notifyControllerListeners(byte action, Object data);

    /**
     * Sets the simulator component
     */
    public void setSimulator(HackSimulatorGUI simulator);

    /**
     * Sets the title of the translator with the given title.
     */
    public void setTitle(String title);

    /**
     * Displays the given message, according to the given type.
     */
    public void displayMessage(String message, boolean error);

    /**
     * Sets the working dir name with the given one.
     */
    public void setWorkingDir(File file);

    /**
     * Sets the script file name with the given one.
     */
    public void setScriptFile(String fileName);

    /**
     * Sets the current script line.
     */
    public void setCurrentScriptLine(int line);

    /**
     * Returns the script file component.
     */
    public JComponent getScriptComponent();

    /**
     * Sets the output file name with the given one.
     */
    public void setOutputFile(String fileName);

    /**
     * Sets the current output line.
     */
    public void setCurrentOutputLine(int line);

    /**
     * Returns the output file component.
     */
    public JComponent getOutputComponent();

    /**
     * Sets the comparison file name with the given one.
     */
    public void setComparisonFile(String fileName);

    /**
     * Sets the current comparison line.
     */
    public void setCurrentComparisonLine(int line);

    /**
     * Returns the comparison file component.
     */
    public JComponent getComparisonComponent();

    /**
     * Sets the additional display (int code, out of the possible additional display
     * constants in HackController)
     */
    public void setAdditionalDisplay(int additionalDisplayCode);

    /**
     * Sets the breakpoints list with the given one.
     */
    public void setBreakpoints(Vector breakpoints);

    /**
     * Sets the list of recognized variables with the given one.
     */
    public void setVariables(String[] vars);

    /**
     * Sets the speed (int code, between 1 and HackController.NUMBER_OF_SPEED_UNTIS)
     */
    public void setSpeed(int speed);

    /**
     * Sets the animation mode (int code, out of the possible animation constants in HackController)
     */
    public void setAnimationMode(int animationMode);

    /**
     * Sets the numeric format (int code, out of the possible format constants in HackController)
     */
    public void setNumericFormat(int formatCode);

    /**
     * Opens the breakpoints panel.
     */
    public void showBreakpoints();

    /**
     * Called when the output file is updated.
     */
    public void outputFileUpdated();

    /**
     * Enables the single step action.
     */
    public void enableSingleStep();

    /**
     * Disables the single step action.
     */
    public void disableSingleStep();

    /**
     * Enables the fast forward action.
     */
    public void enableFastForward();

    /**
     * Disables the fast forward action.
     */
    public void disableFastForward();

    /**
     * Enables the stop action.
     */
    public void enableStop();

    /**
     * Disables the stop action.
     */
    public void disableStop();

    /**
     * Enables the open script action.
     */
    public void enableScript();

    /**
     * Disables the open script action.
     */
    public void disableScript();

    /**
     * Enables the rewind action.
     */
    public void enableRewind();

    /**
     * Disables the rewind action.
     */
    public void disableRewind();

    /**
     * Enables the load program action.
     */
    public void enableLoadProgram();

    /**
     * Disables the load program action.
     */
    public void disableLoadProgram();

    /**
     * Enables the speed slider.
     */
    public void enableSpeedSlider();

    /**
     * Disables the speed slider.
     */
    public void disableSpeedSlider();

    /**
     * Enables the animation mode buttons.
     */
    public void enableAnimationModes();

    /**
     * Disables the animation mode buttons.
     */
    public void disableAnimationModes();
}
