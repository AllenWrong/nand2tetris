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

/**
 * Represents a part of a computer.
 */
public abstract class ComputerPart {

    // If true, changes to the computer part's values will be displayed in its gui.
    protected boolean displayChanges = true;

    // If true, changes to the computer part's values will be animated.
    protected boolean animate;

    // when true, the ComputerPart should display its contents.
    protected boolean hasGUI;

    /**
     * Constructs a new ComputerPart.
     * If hasGUI is true, the ComputerPart will display its contents.
     */
    public ComputerPart(boolean hasGUI) {
        this.hasGUI = hasGUI;
        displayChanges = hasGUI;
        animate = false;
    }

    /**
     * Sets the display changes property of the computer part. If set to true, changes
     * that are made to the values of the computer part will be displayed in its GUI.
     * Otherwise, changes will not be displayed.
     */
    public void setDisplayChanges(boolean trueOrFalse) {
        displayChanges = trueOrFalse && hasGUI;
    }

    /**
     * Sets the animate property of the computer part. If set to true, changes
     * that are made to the values of the computer part will be animated.
     */
    public void setAnimate(boolean trueOrFalse) {
        animate = trueOrFalse && hasGUI;
    }

    /**
     * Resets the contents of the computer part.
     */
    public void reset() {
        if (hasGUI)
            getGUI().reset();
    }

    /**
     * Returns the GUI of the computer part.
     */
    public abstract ComputerPartGUI getGUI();

    /**
     * Refreshes the GUI of this computer part.
     */
    public abstract void refreshGUI();
}
