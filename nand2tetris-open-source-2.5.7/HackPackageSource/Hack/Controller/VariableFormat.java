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

package Hack.Controller;

/**
 * A simulator variable with a printing format
 */
public class VariableFormat {

    // the valid formats

    /**
     * Binary numeric format
     */
    public static final char BINARY_FORMAT = 'B';

    /**
     * Decimal numeric format
     */
    public static final char DECIMAL_FORMAT = 'D';

    /**
     * Hexa decimal numeric format
     */
    public static final char HEX_FORMAT = 'X';

    /**
     * String format
     */
    public static final char STRING_FORMAT = 'S';

    /**
     * The variable's name.
     */
    public String varName;

    /**
     * The number of padding spaces.
     */
    public int padL, padR;

    /**
     * The printing length.
     */
    public int len;

    /**
     * The printing format.
     */
    public char format;

    /**
     * Constructs a new VariableFormat.
     */
    public VariableFormat(String varName, char format, int padL, int padR, int len) {
        this.varName = varName;
        this.format = format;
        this.padL = padL;
        this.padR = padR;
        this.len = len;
    }
}
