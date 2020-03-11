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

package Hack.VMEmulator;

import Hack.ComputerParts.*;

/**
 * A simple calculator, with two inputs, one output, and a set of operators.
 */
public class Calculator extends ValueComputerPart {

    /**
     * The Add operator
     */
    public static final int ADD = 0;

    /**
     * The Subtract operator
     */
    public static final int SUBTRACT = 1;

    /**
     * The Negate operator
     */
    public static final int NEGATE = 2;

    /**
     * The And operator
     */
    public static final int AND = 3;

    /**
     * The Or operator
     */
    public static final int OR = 4;

    /**
     * The Not operator
     */
    public static final int NOT = 5;

    /**
     * The Equal operator
     */
    public static final int EQUAL = 6;

    /**
     * The Greater-Than operator
     */
    public static final int GREATER_THAN = 7;

    /**
     * The Less-Than operator
     */
    public static final int LESS_THAN = 8;

    /**
     * The symbol of the Add operator
     */
    public static final char ADD_SYMBOL = '+';

    /**
     * The symbol of the Subtract operator
     */
    public static final char SUBTRACT_SYMBOL = '-';

    /**
     * The symbol of the Negate operator
     */
    public static final char NEGATE_SYMBOL = '-';

    /**
     * The symbol of the And operator
     */
    public static final char AND_SYMBOL = '&';

    /**
     * The symbol of the Or operator
     */
    public static final char OR_SYMBOL = '|';

    /**
     * The symbol of the Not operator
     */
    public static final char NOT_SYMBOL = '!';

    /**
     * The symbol of the Equal operator
     */
    public static final char EQUAL_SYMBOL = '=';

    /**
     * The symbol of the Greater-Than operator
     */
    public static final char GREATER_THAN_SYMBOL = '>';

    /**
     * The symbol of the Less-Than operator
     */
    public static final char LESS_THAN_SYMBOL = '<';

    // The gui of the calculator
    private CalculatorGUI gui;

    // The operators array
    private char[] operators;

    // The inputs
    private short input0, input1;

    // The output
    private short output;

    /**
     * Constructs a new calculator with the given GUI.
     */
    public Calculator(CalculatorGUI gui) {
        super (gui != null);
        this.gui = gui;

        operators = new char[9];

        operators[ADD] = ADD_SYMBOL;
        operators[SUBTRACT] = SUBTRACT_SYMBOL;
        operators[NEGATE] = NEGATE_SYMBOL;
        operators[AND] = AND_SYMBOL;
        operators[OR] = OR_SYMBOL;
        operators[NOT] = NOT_SYMBOL;
        operators[EQUAL] = EQUAL_SYMBOL;
        operators[GREATER_THAN] = GREATER_THAN_SYMBOL;
        operators[LESS_THAN] = LESS_THAN_SYMBOL;
    }

    /**
     * Computes the value of the output according to the input and the given operator.
     * Assumes a legal operator.
     */
    public void compute(int operator) {
        short result = 0;

        switch (operator) {
            case ADD:
                result = (short)(input0 + input1); break;
            case SUBTRACT:
                result = (short)(input0 - input1); break;
            case NEGATE:
                result = (short)(-input1); break;
            case AND:
                result = (short)(input0 & input1); break;
            case OR:
                result = (short)(input0 | input1); break;
            case NOT:
                result = (short)(~input1); break;
            case EQUAL:
                result = (short)(input0 == input1 ? -1 : 0); break;
            case GREATER_THAN:
                result = (short)(input0 > input1 ? -1 : 0); break;
            case LESS_THAN:
                result = (short)(input0 < input1 ? -1 : 0); break;
        }

        setValueAt(2, result, true);
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    public short getValueAt(int index) {
        short result = 0;

        switch (index) {
            case 0: result = input0; break;
            case 1: result = input1; break;
            case 2: result = output; break;
        }

        return result;
   }

    public void doSetValueAt(int index, short value) {
        switch (index) {
            case 0: input0 = value; break;
            case 1: input1 = value; break;
            case 2: output = value; break;
        }
    }

    public void reset() {
        super.reset();
        input0 = nullValue;
        input1 = nullValue;
        output = nullValue;
    }

    public void refreshGUI() {
        quietUpdateGUI(0, input0);
        quietUpdateGUI(1, input1);
        quietUpdateGUI(2, output);
    }

    /**
     * Displays the calculator GUI with the given amount of inputs (1 or 2).
     */
    public void showCalculator(int operator, int numOfInputs) {
        if (animate) {
            if (numOfInputs == 2)
                gui.showLeftInput();
            else
                gui.hideLeftInput();

            gui.reset();
            gui.setOperator(operators[operator]);
            gui.showCalculator();
        }
    }

    /**
     * Hides the calculator GUI.
     */
    public void hideCalculator() {
        if (animate)
            gui.hideCalculator();
    }
}
