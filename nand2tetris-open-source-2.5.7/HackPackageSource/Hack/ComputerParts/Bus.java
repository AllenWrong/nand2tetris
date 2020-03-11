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
 * A computer bus. Allows sending values between computer parts.
 */
public class Bus extends ComputerPart {

    // The gui of the bus.
    private BusGUI gui;

    /**
     * Constructs a new bus with its given (optional) GUI.
     */
    public Bus(BusGUI gui) {
        super(gui != null);
        this.gui = gui;
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    /**
     * Sets the animation speed with the given speed.
     * (in the range 1..HackController.NUMBER_OF_SPEED_UNITS)
     */
    public void setAnimationSpeed(int speed) {
        if (hasGUI)
            gui.setSpeed(speed);
    }

    /**
     * Sends a value from the the source computer part at location sourceIndex to the
     * target computer part at location targetIndex.
     */
    public synchronized void send(ValueComputerPart sourcePart, int sourceIndex,
                                  ValueComputerPart targetPart, int targetIndex) {

        if (animate && sourcePart.animate && hasGUI) {
            try {
                wait(100);
            } catch (InterruptedException ie) {}

            gui.move(((ValueComputerPartGUI)sourcePart.getGUI()).getCoordinates(sourceIndex),
                     ((ValueComputerPartGUI)targetPart.getGUI()).getCoordinates(targetIndex),
                     ((ValueComputerPartGUI)sourcePart.getGUI()).getValueAsString(sourceIndex));
        }

        targetPart.setValueAt(targetIndex, sourcePart.getValueAt(sourceIndex), false);
    }

    public void refreshGUI() {}
}
