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

import Hack.ComputerParts.*;
import Hack.Gates.*;

/**
 * A composite gate's parts list, where each part is a gate.
 */
public class Parts extends ComputerPart {

    // The gui
    private PartsGUI gui;

    // The parts (gates) array
    private Gate[] parts;

    /**
     * Constructs a new Parts with the given gui (optional).
     */
    public Parts(PartsGUI gui) {
        super(gui != null);
        this.gui = gui;
        parts = new Gate[0];
        refreshGUI();
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    public void refreshGUI() {
        if (displayChanges)
            gui.setContents(parts);
    }

    /**
     * Sets the internal parts (gates) with the given array of gates.
     */
    public void setParts(Gate[] parts) {
        this.parts = parts;
        refreshGUI();
    }
}
