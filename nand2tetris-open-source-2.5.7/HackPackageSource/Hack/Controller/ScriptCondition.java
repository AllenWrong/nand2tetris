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

import Hack.Utilities.*;

/**
 * Represents a boolean condition. Has two arguments, which may be variable names or
 * constants, and a comparison operator, which may be =, >, <, <>, >=, <=.
 */
public class ScriptCondition {

    /**
     * The Equal comparison operator.
     */
    public static final byte EQUAL = 1;

    /**
     * The Greater comparison operator.
     */
    public static final byte GREATER = 2;

    /**
     * The Less comparison operator.
     */
    public static final byte LESS = 3;

    /**
     * The Greater or Equal comparison operator.
     */
    public static final byte GREATER_EQUAL = 4;

    /**
     * The Less or Equal comparison operator.
     */
    public static final byte LESS_EQUAL = 5;

    /**
     * The Not Equal comparison operator.
     */
    public static final byte NOT_EQUAL = 6;


    // The condition arguments (may be variable names or constants).
    private String arg0, arg1;

    // The comparison operator code.
    private byte comparisonOperator;

    /**
     * Constructs a new ScriptCondition with the given ScriptTokenizer, placed on the
     * beginning of the condition clause.
     * Returns the tokenizer after the condition clause.
     */
    public ScriptCondition(ScriptTokenizer input) throws ScriptException, ControllerException {
        // check arg0
        if (input.getTokenType() != ScriptTokenizer.TYPE_IDENTIFIER &&
            input.getTokenType() != ScriptTokenizer.TYPE_INT_CONST)
            throw new ScriptException("A condition expected");

        arg0 = input.getToken();

        // check operator
        input.advance();
        String op;
        if (input.getTokenType() == ScriptTokenizer.TYPE_SYMBOL) {
            op = input.getToken();
            input.advance();
            if (input.getTokenType() == ScriptTokenizer.TYPE_SYMBOL) {
                op += input.getToken();
                input.advance();
            }

            if (op.equals("="))
                comparisonOperator = EQUAL;
            else if (op.equals(">"))
                comparisonOperator = GREATER;
            else if (op.equals(">="))
                comparisonOperator = GREATER_EQUAL;
            else if (op.equals("<"))
                comparisonOperator = LESS;
            else if (op.equals("<="))
                comparisonOperator = LESS_EQUAL;
            else if (op.equals("<>"))
                comparisonOperator = NOT_EQUAL;
            else
                throw new ScriptException("Illegal comparison operator: " + op);
        }
        else
            throw new ScriptException("Comparison operator expected");

        // check arg1
        if (input.getTokenType() != ScriptTokenizer.TYPE_IDENTIFIER &&
            input.getTokenType() != ScriptTokenizer.TYPE_INT_CONST)
            throw new ScriptException("A variable name or constant expected");

        arg1 = input.getToken();
        input.advance();
    }

    /**
     * Returns the result of the condition for the given simulator.
     */
    public boolean compare(HackSimulator simulator) throws ControllerException {
        boolean result = false;
        String val0, val1;
        int num0 = 0, num1 = 0;
        boolean isNum0, isNum1;

        // find if arg0 and arg1 are variables. If so, retrieve their values.
        // Otherwise, treat them as constants.
        try {
            val0 = simulator.getValue(arg0);
        } catch (VariableException ve) {
            val0 = arg0;
        }

        try {
            val1 = simulator.getValue(arg1);
        } catch (VariableException ve) {
            val1 = arg1;
        }

        // Find if val0 and val1 are integers.
        try {
            num0 = Integer.parseInt(Conversions.toDecimalForm(val0));
            isNum0 = true;
        } catch (NumberFormatException nfe) {
            isNum0 = false;
        }

        try {
            num1 = Integer.parseInt(Conversions.toDecimalForm(val1));
            isNum1 = true;
        } catch (NumberFormatException nfe) {
            isNum1 = false;
        }

        // if both values are integers, compare them using integer comparison.
        if (isNum0 && isNum1) {
            switch (comparisonOperator) {
                case EQUAL: result = (num0 == num1); break;
                case GREATER: result = (num0 > num1); break;
                case LESS: result = (num0 < num1); break;
                case GREATER_EQUAL: result = (num0 >= num1); break;
                case LESS_EQUAL: result = (num0 <= num1); break;
                case NOT_EQUAL: result = (num0 != num1); break;
            }
        }
        else if (!isNum0 && !isNum1) { // if = or <>, compare using string comparison
            switch (comparisonOperator) {
                case EQUAL: result = val0.equals(val1); break;
                case NOT_EQUAL: result = !val0.equals(val1); break;
                default: throw new ControllerException("Only = and <> can be used to compare strings");
            }
        }
        else
            throw new ControllerException("Cannot compare an integer with a string");
        return result;
    }
}
