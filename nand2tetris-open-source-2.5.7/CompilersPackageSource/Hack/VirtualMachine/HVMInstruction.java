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

package Hack.VirtualMachine;

/**
 * This class represents a single hack Virtual machine instruction (with its arguments).
 * It holds the operation code that can be one of the VM operations
 * and up to 2 arguments.
 */
public class HVMInstruction {

    // the operation code
    private byte opCode;

    // the operation arguments
    private short arg0;
    private short arg1;

    // A string argument
    private String stringArg;

    // The number of arguments
    private short numberOfArgs;

    /**
     * Constructs a new instruction with two arguments.
     */
    public HVMInstruction(byte opCode, short arg0, short arg1) {
        this.opCode = opCode;
        this.arg0 = arg0;
        this.arg1 = arg1;
        numberOfArgs = 2;
    }

    /**
     * Constructs a new instruction with one argument.
     */
    public HVMInstruction(byte opCode, short arg0) {
        this.opCode = opCode;
        this.arg0 = arg0;
        numberOfArgs = 1;
    }

    /**
     * Constructs a new instruction with no arguments.
     */
    public HVMInstruction(byte opCode) {
        this.opCode = opCode;
        numberOfArgs = 0;
    }

    /**
     * Returns the operation code
     */
    public short getOpCode() {
        return opCode;
    }

    /**
     * Returns arg0
     */
    public short getArg0() {
        return arg0;
    }

    /**
     * Returns arg1
     */
    public short getArg1() {
        return arg1;
    }

    /**
     * Sets the string argument with the given string.
     */
    public void setStringArg(String arg) {
        stringArg = arg;
    }

    /**
     * Returns the string argument
     */
    public String getStringArg() {
        return stringArg;
    }

    /**
     * Returns the number of arguments.
     */
    public short getNumberOfArgs() {
        return numberOfArgs;
    }

    /**
     * Returns an array of 3 Strings. The first is the operation name, the second is
     * the first argument and the third is the second argument.
     */
    public String[] getFormattedStrings() {
        String[] result = new String[3];
        HVMInstructionSet instructionSet = HVMInstructionSet.getInstance();
        result[1] = "";
        result[2] = "";

        result[0] = instructionSet.instructionCodeToString(opCode);
        if (result[0] == null)
            result[0] = "";

        switch (opCode) {
            case HVMInstructionSet.PUSH_CODE:
                result[1] = instructionSet.segmentCodeToVMString((byte)arg0);
                result[2] = String.valueOf(arg1);
                break;
            case HVMInstructionSet.POP_CODE:
                if (numberOfArgs == 2) {
                    result[1] = instructionSet.segmentCodeToVMString((byte)arg0);
                    result[2] = String.valueOf(arg1);
                }
                break;
            case HVMInstructionSet.LABEL_CODE:
                result[1] = stringArg;
                break;
            case HVMInstructionSet.GOTO_CODE:
                result[1] = stringArg;
                break;
            case HVMInstructionSet.IF_GOTO_CODE:
                result[1] = stringArg;
                break;
            case HVMInstructionSet.FUNCTION_CODE:
                result[1] = stringArg;
                result[2] = String.valueOf(arg0);
                break;
            case HVMInstructionSet.CALL_CODE:
                result[1] = stringArg;
                result[2] = String.valueOf(arg1);
                break;
        }

        return result;
    }


    public String toString() {
        String[] formatted = getFormattedStrings();
        StringBuffer result = new StringBuffer();
        if (!formatted[0].equals("")) {
            result.append(formatted[0]);

            if (!formatted[1].equals("")) {
                result.append(" ");
                result.append(formatted[1]);

                if (!formatted[2].equals("")) {
                    result.append(" ");
                    result.append(formatted[2]);
                }
            }
        }

        return result.toString();
    }
}
