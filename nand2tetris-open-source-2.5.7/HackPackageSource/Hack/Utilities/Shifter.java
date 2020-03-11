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

package Hack.Utilities;

/**
 * A static service of bit shifting.
 */
public class Shifter {

    // A helper array of powers of two
    public static final short[] powersOf2 = {1,2,4,8,16,32,64,128,256,512,1024,2048,4096,
                                             8192,16384,-32768};
    /**
     * Returns the given value shifted right (zero filled) by the given amount of bits.
     * (Shift bits is assumed to be <= 15).
     */
    public static short unsignedShiftRight(short value, byte shiftBits) {
        short result;

        if (value >= 0)
            result = (short)(value >> shiftBits);
        else {
            value &= 0x7fff;
            result = (short)(value >> shiftBits);
            result |= powersOf2[15 - shiftBits];
        }

        return result;
    }

}
