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
 * Holds information on a pin of an internal part.
 */
public class PartPinInfo extends PinInfo {

    /**
     * The name of the part pin.
     */
    public String partPinName;

    /**
     * The sub bus of the part pin, represented as an array of two elements:
     * the low bit and the high bit. If no sub bus, contains null.
     */
    public byte[] partPinSubBus;

    /**
     * The name of the gate pin.
     */
    public String gatePinName;

    /**
     * The sub bus of the gate pin, represented as an array of two elements:
     * the low bit and the high bit. If no sub bus, contains null.
     */
    public byte[] gatePinSubBus;

    /**
     * The value of the pin.
     */
    public short value;

    /**
     * Constructs a new empty PartPinInfo.
     */
    public PartPinInfo() {
    }

}
