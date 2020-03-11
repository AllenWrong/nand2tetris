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
 * A value computer part - a computer part that has values which can be get & set.
 */
public abstract class ValueComputerPart extends ComputerPart {

    // The amount of miliseconds that a changed value will flash.
    private static final int FLASH_TIME = 500;

    // used as default value (in reset)
    protected short nullValue;

    /**
     * Constructs a new ValueComputerPart
     * If hasGUI is true, the ComputerPart should display its contents.
     */
    public ValueComputerPart(boolean hasGUI) {
        super(hasGUI);
    }

    /**
     * Sets the element at the given index with the given value and updates the gui.
     */
    public void setValueAt(int index, short value, boolean quiet) {
        doSetValueAt(index, value);
        if (displayChanges) {
            if (quiet)
                quietUpdateGUI(index, value);
            else
                updateGUI(index, value);
        }
    }

    /**
     * Sets the element at the given index with the given value.
     */
    public abstract void doSetValueAt(int index, short value);

    /**
     * Returns the element at the given index.
     */
    public abstract short getValueAt(int index);

    /**
     * Updates the GUI of this computer part at the given location with the given value
     */
    public synchronized void updateGUI(int index, short value) {
        if (displayChanges) {
            ValueComputerPartGUI gui = (ValueComputerPartGUI)getGUI();
            gui.setValueAt(index, value);

            if (animate) {
                gui.flash(index);
                try {
                    wait(FLASH_TIME);
                } catch (InterruptedException ie) {}
                gui.hideFlash();
            }

            gui.highlight(index);
        }
    }

    /**
     * Updates the GUI of this computer part at the given location with the given value
     * quietly - no flashing will be done
     */
    public void quietUpdateGUI(int index, short value) {
        if (displayChanges)
            ((ValueComputerPartGUI)getGUI()).setValueAt(index, value);
    }

    /**
     * Hides all highlightes.
     */
    public void hideHighlight() {
        if (displayChanges)
            ((ValueComputerPartGUI)getGUI()).hideHighlight();
    }

    /**
     * Sets the numeric format with the given code (out of the format constants in HackController).
     */
    public void setNumericFormat(int formatCode) {
        if (displayChanges)
            ((ValueComputerPartGUI)getGUI()).setNumericFormat(formatCode);
    }

    /**
     * Sets the null value (default value) of this computer part with the given value.
     * If hideNullValue is true, values which are equal to the null value will be
     * hidden.
     */
    public void setNullValue(short value, boolean hideNullValue) {
        nullValue = value;

        if (hasGUI) {
            ValueComputerPartGUI gui = (ValueComputerPartGUI)getGUI();
            gui.setNullValue(value, hideNullValue);
        }
    }
}
