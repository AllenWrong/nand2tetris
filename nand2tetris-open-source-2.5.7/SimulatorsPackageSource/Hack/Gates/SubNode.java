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

package Hack.Gates;

import Hack.Utilities.*;

/**
 * A node that represents a sub-bus.
 */
public class SubNode extends Node {

    // The mask which filters out the non-relevant part of the sub-node
    private short mask;

    // The amount of bits to shift right the masked value
    private byte shiftRight;

    /**
     * Constructs a new SubNode with the given low & high sub-bus indice.
     */
    public SubNode(byte low, byte high) {
        mask = getMask(low, high);
        shiftRight = low;
    }

    /**
     * Returns the value of this sub-node.
     */
    public short get() {
        return Shifter.unsignedShiftRight((short)(value & mask), shiftRight);
    }

    /**
     * Returns a mask according to the given low & high bit indice.
     */
    public static short getMask(byte low, byte high) {
        short mask = 0;

        short bitHolder = Shifter.powersOf2[low];
        for (byte i = low; i <= high; i++) {
            mask |= bitHolder;
            bitHolder = (short)(bitHolder << 1);
        }

        return mask;
    }
}
