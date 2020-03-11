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

import java.util.*;
import Hack.Utilities.*;

/**
 * The instruction set of the hack virtual machine.
 * This is a singleton class.
 */
public class HVMInstructionSet {

    /**
     * Add instruction code
     */
    public static final byte ADD_CODE = 1;

    /**
     * Substract instruction code
     */
    public static final byte SUBSTRACT_CODE = 2;

    /**
     * Negate instruction code
     */
    public static final byte NEGATE_CODE = 3;

    /**
     * Equal instruction code
     */
    public static final byte EQUAL_CODE = 4;

    /**
     * Greater-Than instruction code
     */
    public static final byte GREATER_THAN_CODE = 5;

    /**
     * Less-Than instruction code
     */
    public static final byte LESS_THAN_CODE = 6;

    /**
     * And instruction code
     */
    public static final byte AND_CODE = 7;

    /**
     * Or instruction code
     */
    public static final byte OR_CODE = 8;

    /**
     * Not instruction code
     */
    public static final byte NOT_CODE = 9;

    /**
     * Push instruction code
     */
    public static final byte PUSH_CODE = 10;

    /**
     * Pop instruction code
     */
    public static final byte POP_CODE = 11;

    /**
     * Label instruction code
     */
    public static final byte LABEL_CODE = 12;

    /**
     * Goto instruction code
     */
    public static final byte GOTO_CODE = 13;

    /**
     * If-Goto instruction code
     */
    public static final byte IF_GOTO_CODE = 14;

    /**
     * Function instruction code
     */
    public static final byte FUNCTION_CODE = 15;

    /**
     * Return instruction code
     */
    public static final byte RETURN_CODE = 16;

    /**
     * Call instruction code
     */
    public static final byte CALL_CODE = 17;

    /**
     * Unknown instruction code
     */
    public static final byte UNKNOWN_INSTRUCTION = -99;

    /**
     * Add instruction string
     */
    public static final String ADD_STRING = "add";

    /**
     * Substract instruction string
     */
    public static final String SUBSTRACT_STRING = "sub";

    /**
     * Negate instruction string
     */
    public static final String NEGATE_STRING = "neg";

    /**
     * Equal instruction string
     */
    public static final String EQUAL_STRING = "eq";

    /**
     * Greater-Than instruction string
     */
    public static final String GREATER_THAN_STRING = "gt";

    /**
     * Less-Than instruction string
     */
    public static final String LESS_THAN_STRING = "lt";

    /**
     * And instruction string
     */
    public static final String AND_STRING = "and";

    /**
     * Or instruction string
     */
    public static final String OR_STRING = "or";

    /**
     * Not instruction string
     */
    public static final String NOT_STRING = "not";

    /**
     * Push instruction string
     */
    public static final String PUSH_STRING = "push";

    /**
     * Pop instruction string
     */
    public static final String POP_STRING = "pop";

    /**
     * Label instruction string
     */
    public static final String LABEL_STRING = "label";

    /**
     * Goto instruction string
     */
    public static final String GOTO_STRING = "goto";

    /**
     * If-Goto instruction string
     */
    public static final String IF_GOTO_STRING = "if-goto";

    /**
     * Function instruction string
     */
    public static final String FUNCTION_STRING = "function";

    /**
     * Return instruction string
     */
    public static final String RETURN_STRING = "return";

    /**
     * Call instruction string
     */
    public static final String CALL_STRING = "call";


    // Memory segments

    /**
     * The number of actual memory segments
     */
    public final static int NUMBER_OF_ACTUAL_SEGMENTS = 5;

    // The actual segments should be numbered from 0 onwards, so they can be used as array indice.

    /**
     * Local segment code
     */
    public final static byte LOCAL_SEGMENT_CODE = 0;

    /**
     * Arg segment code
     */
    public final static byte ARG_SEGMENT_CODE = 1;

    /**
     * This segment code
     */
    public final static byte THIS_SEGMENT_CODE = 2;

    /**
     * That segment code
     */
    public final static byte THAT_SEGMENT_CODE = 3;

    /**
     * Temp segment code
     */
    public final static byte TEMP_SEGMENT_CODE = 4;

    /**
     * Static virtual segment code
     */
    public final static byte STATIC_SEGMENT_CODE = 100;

    /**
     * Const virtual segment code
     */
    public final static byte CONST_SEGMENT_CODE = 101;

    /**
     * Pointer virtual segment code
     */
    public final static byte POINTER_SEGMENT_CODE = 102;

    /**
     * Unknown segment code
     */
    public static final byte UNKNOWN_SEGMENT = -1;

    /**
     * Static virtual segment string in VM
     */
    public final static String STATIC_SEGMENT_VM_STRING = "static";

    /**
     * Local segment string in VM
     */
    public final static String LOCAL_SEGMENT_VM_STRING = "local";

    /**
     * Arg segment string in VM
     */
    public final static String ARG_SEGMENT_VM_STRING = "argument";

    /**
     * This segment string in VM
     */
    public final static String THIS_SEGMENT_VM_STRING = "this";

    /**
     * That segment string in VM
     */
    public final static String THAT_SEGMENT_VM_STRING = "that";

    /**
     * Temp segment string in VM
     */
    public final static String TEMP_SEGMENT_VM_STRING = "temp";

    /**
     * Const virtual segment string in VM
     */
    public final static String CONST_SEGMENT_VM_STRING = "constant";

    /**
     * Pointer virtual segment string in VM
     */
    public final static String POINTER_SEGMENT_VM_STRING = "pointer";

    // the single instance
    private static HVMInstructionSet instance;

    // the translation table from instruction strings to codes.
    private Hashtable instructionToCode;

    // the translation table from instruction codes to strings.
    private Hashtable instructionToString;

    // the translation table from segment VM strings to codes.
    private Hashtable segmentCodes;

    // the translation table from segment codes to segment VM strings.
    private Hashtable segmentStrings;

    // the translation table from segment VM strings to hardware pointer names.
    private Hashtable segmentPointerStrings;

    // Constructs the singlton HVMInstructionSet
    private HVMInstructionSet() {
        instance = this;
        initInstructions();
        initSegmentStrings();
        initSegmentCodes();
    }

    /**
     * Returns the single instance of the instruction set.
     */
    public static HVMInstructionSet getInstance() {
        if (instance == null)
            new HVMInstructionSet();
        return instance;
    }

    // initializes the instructions table
    private void initInstructions() {
        instructionToCode = new Hashtable();
        instructionToCode.put(ADD_STRING,new Byte(ADD_CODE));
        instructionToCode.put(SUBSTRACT_STRING,new Byte(SUBSTRACT_CODE));
        instructionToCode.put(NEGATE_STRING,new Byte(NEGATE_CODE));
        instructionToCode.put(EQUAL_STRING,new Byte(EQUAL_CODE));
        instructionToCode.put(GREATER_THAN_STRING,new Byte(GREATER_THAN_CODE));
        instructionToCode.put(LESS_THAN_STRING,new Byte(LESS_THAN_CODE));
        instructionToCode.put(AND_STRING,new Byte(AND_CODE));
        instructionToCode.put(OR_STRING,new Byte(OR_CODE));
        instructionToCode.put(NOT_STRING,new Byte(NOT_CODE));
        instructionToCode.put(PUSH_STRING,new Byte(PUSH_CODE));
        instructionToCode.put(POP_STRING,new Byte(POP_CODE));
        instructionToCode.put(LABEL_STRING,new Byte(LABEL_CODE));
        instructionToCode.put(GOTO_STRING,new Byte(GOTO_CODE));
        instructionToCode.put(IF_GOTO_STRING,new Byte(IF_GOTO_CODE));
        instructionToCode.put(FUNCTION_STRING,new Byte(FUNCTION_CODE));
        instructionToCode.put(RETURN_STRING,new Byte(RETURN_CODE));
        instructionToCode.put(CALL_STRING,new Byte(CALL_CODE));

        instructionToString = new Hashtable();
        instructionToString.put(new Byte(ADD_CODE), ADD_STRING);
        instructionToString.put(new Byte(SUBSTRACT_CODE), SUBSTRACT_STRING);
        instructionToString.put(new Byte(NEGATE_CODE), NEGATE_STRING);
        instructionToString.put(new Byte(EQUAL_CODE), EQUAL_STRING);
        instructionToString.put(new Byte(GREATER_THAN_CODE), GREATER_THAN_STRING);
        instructionToString.put(new Byte(LESS_THAN_CODE), LESS_THAN_STRING);
        instructionToString.put(new Byte(AND_CODE), AND_STRING);
        instructionToString.put(new Byte(OR_CODE), OR_STRING);
        instructionToString.put(new Byte(NOT_CODE), NOT_STRING);
        instructionToString.put(new Byte(PUSH_CODE), PUSH_STRING);
        instructionToString.put(new Byte(POP_CODE), POP_STRING);
        instructionToString.put(new Byte(LABEL_CODE), LABEL_STRING);
        instructionToString.put(new Byte(GOTO_CODE), GOTO_STRING);
        instructionToString.put(new Byte(IF_GOTO_CODE), IF_GOTO_STRING);
        instructionToString.put(new Byte(FUNCTION_CODE), FUNCTION_STRING);
        instructionToString.put(new Byte(RETURN_CODE), RETURN_STRING);
        instructionToString.put(new Byte(CALL_CODE), CALL_STRING);
    }

    // initializes the segment strings table
    private void initSegmentStrings() {
        segmentPointerStrings = new Hashtable();
        segmentPointerStrings.put(LOCAL_SEGMENT_VM_STRING, Definitions.LOCAL_POINTER_NAME);
        segmentPointerStrings.put(ARG_SEGMENT_VM_STRING, Definitions.ARG_POINTER_NAME);
        segmentPointerStrings.put(THIS_SEGMENT_VM_STRING, Definitions.THIS_POINTER_NAME);
        segmentPointerStrings.put(THAT_SEGMENT_VM_STRING, Definitions.THAT_POINTER_NAME);

        segmentStrings = new Hashtable();
        segmentStrings.put(new Byte(STATIC_SEGMENT_CODE), STATIC_SEGMENT_VM_STRING);
        segmentStrings.put(new Byte(LOCAL_SEGMENT_CODE), LOCAL_SEGMENT_VM_STRING);
        segmentStrings.put(new Byte(ARG_SEGMENT_CODE), ARG_SEGMENT_VM_STRING);
        segmentStrings.put(new Byte(THIS_SEGMENT_CODE), THIS_SEGMENT_VM_STRING);
        segmentStrings.put(new Byte(THAT_SEGMENT_CODE), THAT_SEGMENT_VM_STRING);
        segmentStrings.put(new Byte(TEMP_SEGMENT_CODE), TEMP_SEGMENT_VM_STRING);
        segmentStrings.put(new Byte(CONST_SEGMENT_CODE), CONST_SEGMENT_VM_STRING);
        segmentStrings.put(new Byte(POINTER_SEGMENT_CODE), POINTER_SEGMENT_VM_STRING);
    }

    // initializes the segment codes table
    private void initSegmentCodes() {
        segmentCodes = new Hashtable();
        segmentCodes.put(STATIC_SEGMENT_VM_STRING, new Byte(STATIC_SEGMENT_CODE));
        segmentCodes.put(LOCAL_SEGMENT_VM_STRING, new Byte(LOCAL_SEGMENT_CODE));
        segmentCodes.put(ARG_SEGMENT_VM_STRING, new Byte(ARG_SEGMENT_CODE));
        segmentCodes.put(THIS_SEGMENT_VM_STRING, new Byte(THIS_SEGMENT_CODE));
        segmentCodes.put(THAT_SEGMENT_VM_STRING, new Byte(THAT_SEGMENT_CODE));
        segmentCodes.put(TEMP_SEGMENT_VM_STRING, new Byte(TEMP_SEGMENT_CODE));
        segmentCodes.put(CONST_SEGMENT_VM_STRING, new Byte(CONST_SEGMENT_CODE));
        segmentCodes.put(POINTER_SEGMENT_VM_STRING, new Byte(POINTER_SEGMENT_CODE));
    }

    /**
     * Returns the code of the given instruction string.
     * If not exists, returns UNKNOWN_INSTRUCTION.
     */
    public byte instructionStringToCode(String instruction) {
        Byte result = (Byte)instructionToCode.get(instruction);
        return (result != null ? result.byteValue() : UNKNOWN_INSTRUCTION);
    }

    /**
     * Returns the string of the given instruction code.
     * If not exists, returns null.
     */
    public String instructionCodeToString(byte code) {
        return (String)instructionToString.get(new Byte(code));
    }

    /**
     * Returns true if the given segment VM string is a legal segment string.
     */
    public boolean isLegalVMSegment(String segment) {
        return (segmentCodes.get(segment) != null);
    }

    /**
     * Returns the code of the given segment VM string.
     * If not exists, returns UNKNOWN_SEGMENT.
     */
    public byte segmentVMStringToCode(String segment) {
        Byte result = (Byte)segmentCodes.get(segment);
        return (result != null ? result.byteValue() : UNKNOWN_SEGMENT);
    }

    /**
     * Returns the hardware pointer name of the given segment VM string.
     * If not exists, returns null.
     */
    public String segmentStringVMToPointer(String segment) {
        return (String)segmentPointerStrings.get(segment);
    }

    /**
     * Returns the code of the given segment VM string.
     * If not exists, returns null.
     */
    public String segmentCodeToVMString(byte code) {
        return (String)segmentStrings.get(new Byte(code));
    }
}
