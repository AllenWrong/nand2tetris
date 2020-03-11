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
 * Format conversion utilities
 */
public class Conversions {

    // A helper string of zeros
    private static final String ZEROS = "0000000000000000000000000000000000000000";

    // A helper array of powers of two
    private static final int[] powersOf2 = {1,2,4,8,16,32,64,128,256,512,1024,2048,4096,
                                            8192,16384,32768,65536,131072,262144,524288,
                                            1048576,2097152,4194304,8388608,16777216,
                                            33554432,67108864,134217728,268435456,536870912,
                                            1073741824,-2147483648};

    // A helper array of powers of 16
    private static final int[] powersOf16 = {1,16,256,4096,65536,1048576,16777216,268435456};

    /**
     * If the given string starts with %X, %B or %D translates it to a normal decimal form.
     * If the given string is a decimal number, translates it into a normal decimal form.
     * Otherwise, return the given string as is.
     */
    public static String toDecimalForm(String value) {
        if (value.startsWith("%B"))
            value = String.valueOf(binaryToInt(value.substring(2)));
        else if (value.startsWith("%X")) {
            if (value.length() == 6)
                value = String.valueOf(hex4ToInt(value.substring(2)));
            else
                value = String.valueOf(hexToInt(value.substring(2)));
        }
        else if (value.startsWith("%D"))
            value = value.substring(2);
        else {
            try {
                int intValue = Integer.parseInt(value);
                value = String.valueOf(intValue);
            } catch (NumberFormatException nfe) {
            }
        }

        return value;
    }

    /**
     * Returns the decimal int representation of the given binary value.
     * The binary value is given as a string of 0's and 1's. If any other character
     * appears in the given string, a NumberFormatException is thrown.
     */
    public static int binaryToInt(String value) throws NumberFormatException {
        int result = 0;

        for (int i = value.length() - 1, mask = 1; i >= 0; i--, mask = mask << 1) {
            char bit = value.charAt(i);
            if (bit == '1')
                result = (short)(result | mask);
            else if (bit != '0')
                throw new NumberFormatException();
        }

        return result;
    }

    /**
     * Returns the decimal int representation of the given hexadecimal value.
     * The hexadecimal value is given as a string of 0-9, a-f. If any other character
     * appears in the given string, a NumberFormatException is thrown.
     */
    public static int hexToInt(String value) throws NumberFormatException {
        int result = 0;
        int multiplier = 1;

        for (int i = value.length() - 1; i >= 0; i--, multiplier *= 16) {
            char digit = value.charAt(i);
            if (digit >= '0' && digit <= '9')
                result += (digit - '0') * multiplier;
            else if (digit >= 'a' && digit <= 'f')
                result += (digit - 'a' + 10) * multiplier;
            else if (digit >= 'A' && digit <= 'F')
                result += (digit - 'A' + 10) * multiplier;
            else
                throw new NumberFormatException();
        }

        return result;
    }

    /**
     * Returns the decimal int representation of the given 4-digit hexadecimal value.
     * The hexadecimal value is given as a string of 0-9, a-f. If any other character
     * appears in the given string, a NumberFormatException is thrown.
     * The given value is assumed to have exactly 4 digits.
     */
    public static int hex4ToInt(String value) throws NumberFormatException {
        int result = hexToInt(value);

        if (result > 32767)
            result -= 65536;

        return result;

    }

    /**
     * Returns the binary string representation of the given int value, adding
     * preceeding zeros if the result contains less digits than the given amount of digits.
     */
    public static String decimalToBinary(int value, int numOfDigits) {
        value = value & (powersOf2[numOfDigits] - 1);
        String result = Integer.toBinaryString(value);
        if (result.length() < numOfDigits)
            result = ZEROS.substring(0, numOfDigits - result.length()) + result;
        return result;
    }

    /**
     * Returns the hexadeimal string representation of the given int value, adding
     * preceeding zeros if the result contains less digits than the given amount of digits.
     */
    public static String decimalToHex(int value, int numOfDigits) {
        value = value & (powersOf16[numOfDigits] - 1);
        String result = Integer.toHexString(value);
        if (result.length() < numOfDigits)
            result = ZEROS.substring(0, numOfDigits - result.length()) + result;
        return result;
    }
}
