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

package Hack.CPUEmulator;

import Hack.Utilities.*;
import Hack.ComputerParts.*;

/**
 * A computer ALU. Has two inputs, one output, and a set of commands.
 */
public class ALU extends ValueComputerPart {

    // The amount of miliseconds that the ALU will flash when computing its value.
    private static final int BODY_FLASH_TIME = 500;
    private static final int COMMAND_FLASH_TIME = 500;

    // The inputs of the ALU
    private short input0, input1;

    // The output of the ALU
    private short output;

    // The dscription of the command
    private String commandDescription;

    // The gui of the ALU
    private ALUGUI gui;

    // if true, zeros input0 before operation
    private boolean zero0;

    // if true, zeros input1 before operation
    private boolean zero1;

    // if true, negates input0 before operation
    private boolean negate0;

    // if true, negates input1 before operation
    private boolean negate1;

    // if true, ADDs the inputs. Otherwise, ANDs the inputs (logical AND)
    private boolean ADDorAND;

    // if true, negates the output after the operation.
    private boolean negateOutput;


    /**
     * Constructs a new ALU with the given ALU GUI.
     */
    public ALU(ALUGUI gui) {
        super(gui != null);
        this.gui = gui;
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    /**
     * Sets the ALU's command with the given information.
     * zero0 - if true, zeros input0 before operation
     * zero1 - if true, zeros input1 before operation
     * negate0 - if true, negates input0 before operation
     * negate1 - if true, negates input1 before operation
     * ADDorAND - if true, ADDs the inputs. Otherwise, ANDs the inputs (logical AND)
     * negateOutput - if true, negates the output after the operation.
     */
    public synchronized void setCommand(String description,  boolean zero0, boolean negate0,
                                        boolean zero1, boolean negate1, boolean ADDorAND,
                                        boolean negateOutput) {
        commandDescription = description;
        this.zero0 = zero0;
        this.negate0 = negate0;
        this.zero1 = zero1;
        this.negate1 = negate1;
        this.ADDorAND = ADDorAND;
        this.negateOutput = negateOutput;

        if (displayChanges)
            gui.setCommand(description);

        if (animate) {
            gui.commandFlash();
            try {
                wait(COMMAND_FLASH_TIME);
            } catch (InterruptedException ie) {}
            gui.hideCommandFlash();
        }
    }


    /**
     * Computes the value of the ALU's output according to the inputs and the
     * current command.
     */
    public synchronized void compute() {

        if (animate) {
            gui.bodyFlash();
            try {
                wait(BODY_FLASH_TIME);
            } catch (InterruptedException ie) {}
            gui.hideBodyFlash();
        }

        short result = Definitions.computeALU(input0, input1, zero0, negate0, zero1,
                                              negate1, ADDorAND, negateOutput);

        setValueAt(2, result, false);
    }

    /**
     * Sets the first input of the ALU with the given value.
     */
    public void setInput0(short value) {
        setValueAt(0, value, false);
    }

    /**
     * Sets the second input of the ALU with the given value.
     */
    public void setInput1(short value) {
        setValueAt(1, value, false);
    }

    /**
     * Returns the output of the ALU.
     */
    public short getOutput() {
        return getValueAt(2);
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
}
