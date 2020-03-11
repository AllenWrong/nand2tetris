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

import Hack.Gates.*;

/**
 * A SubNode that receives a PartPins and an index and updates the PartPins at the given
 * index when the value of the node changes.
 */
public class SubNodePartPinsAdapter extends SubNode {

    // The PartPins that contain the node.
    private PartPins partPins;

    // The index of the node in the PartPins.
    private int index;

    /**
     * Constructs a new SubNodePartPinsAdapter with the sub bus specification,
     * the PartPins and the index.
     */
    public SubNodePartPinsAdapter(byte low, byte high, PartPins partPins, int index) {
        super(low, high);
        this.partPins = partPins;
        this.index = index;
    }

    public void set(short value) {
        this.value = value;
        partPins.quietUpdateGUI(index, get());
    }
}
