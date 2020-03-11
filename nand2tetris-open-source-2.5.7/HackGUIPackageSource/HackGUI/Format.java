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

package HackGUI;

import Hack.Utilities.Conversions;
import Hack.Controller.*;

/**
 * A utility class for handling format convsersions.
 */
public class Format {

    /**
     * The decimal format.
     */
    public static final int DEC_FORMAT = HackController.DECIMAL_FORMAT;

    /**
     * The hexadecimal format.
     */
    public static final int HEX_FORMAT = HackController.HEXA_FORMAT;

    /**
     * The binary format.
     */
    public static final int BIN_FORMAT = HackController.BINARY_FORMAT;

    /**
     * Translates a given string to a short according to the current format.
     * The format can be decimal, hexadecimal or binary.
     */
    public static short translateValueToShort(String data, int dataFormat) throws NumberFormatException {
        short result = 0;
        switch (dataFormat) {
            case DEC_FORMAT:
                result = Short.parseShort(data);
                break;

            case HEX_FORMAT:
                result = (short)Conversions.hexToInt(data);
                break;

            case BIN_FORMAT:
                result = (short)Conversions.binaryToInt(data);
                break;
        }

        return result;
    }

    /**
     * Translates a given short to a string according to the current format.
     * The format can be decimal, hexadecimal or binary.
     */
    public static String translateValueToString(short value, int dataFormat) {
        String result = null;

        switch (dataFormat) {
            case DEC_FORMAT:
                result = String.valueOf(value);
                break;

            case HEX_FORMAT:
                result = Conversions.decimalToHex(value,4);
                break;

            case BIN_FORMAT:
                result = Conversions.decimalToBinary(value,16);
                break;
        }

        return result;
    }
}
