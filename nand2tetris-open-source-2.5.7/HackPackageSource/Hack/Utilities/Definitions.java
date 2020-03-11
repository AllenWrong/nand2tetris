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

import java.util.*;
import java.awt.event.*;

/**
 * Hack definitions and services. Designed as a singleton.
 */
public class Definitions {

    /**
     * Current software version
     */
    public static final String version = "2.5";

    /**
     * Size of RAM
     */
    public static final int RAM_SIZE = 24577;

    /**
     * Size of ROM
     */
    public static final int ROM_SIZE = 32768;

    /**
     * Number of bits per memory word
     */
    public static final int BITS_PER_WORD = 16;

    // screen size
    private static final int SCREEN_WIDTH_IN_WORDS = 32;
    private static final int SCREEN_HEIGHT_IN_WORDS = 256;

    /**
     * Number of words in the screen
     */
    public static final short SCREEN_SIZE_IN_WORDS = SCREEN_WIDTH_IN_WORDS * SCREEN_HEIGHT_IN_WORDS;

    /**
     * Screen width in pixels
     */
    public static final int SCREEN_WIDTH = SCREEN_WIDTH_IN_WORDS * BITS_PER_WORD;

    /**
     * Screen width in pixels (=words)
     */
    public static final int SCREEN_HEIGHT = SCREEN_HEIGHT_IN_WORDS;

    /**
     * Total number of pixels in the screen
     */
    public static final int SCREEN_SIZE = SCREEN_WIDTH * SCREEN_HEIGHT;


    /**
     * Address of the beginning of variable storage area
     */
    public static final short VAR_START_ADDRESS = 16;

    /**
     * Address of the end of variable storage area
     */
    public static final short VAR_END_ADDRESS = 255;


    // Memory segments

    /**
     * The start address of the global stack
     */
    public static final short STACK_START_ADDRESS = 256;

    /**
     * The end address of the global stack
     */
    public static final short STACK_END_ADDRESS = 2047;

    /**
     * The start address of the heap
     */
    public static final short HEAP_START_ADDRESS = 2048;

    /**
     * The end address of the heap
     */
    public static final short HEAP_END_ADDRESS = 16383;

    /**
     * The start address of the screen
     */
    public static final short SCREEN_START_ADDRESS = 16384;

    /**
     * The end address of the screen
     */
    public static final short SCREEN_END_ADDRESS = SCREEN_START_ADDRESS + SCREEN_SIZE_IN_WORDS;

    /**
     * The address of the memory-mapped keyboard
     */
    public static final short KEYBOARD_ADDRESS = 24576;

    /**
     * The start address of the temp memory segment
     */
    public static final short TEMP_START_ADDRESS = 5;

    /**
     * The end address of the temp memory segment
     */
    public static final short TEMP_END_ADDRESS = 12;


    // pointers addresses

    /**
     * The address of the SP regsiter
     */
    public static final short SP_ADDRESS = 0;

    /**
     * The address of the LOCAL regsiter
     */
    public static final short LOCAL_POINTER_ADDRESS = 1;

    /**
     * The address of the ARG regsiter
     */
    public static final short ARG_POINTER_ADDRESS = 2;

    /**
     * The address of the THIS regsiter
     */
    public static final short THIS_POINTER_ADDRESS = 3;

    /**
     * The address of the THAT regsiter
     */
    public static final short THAT_POINTER_ADDRESS = 4;


    // Register addresses

    /**
     * The address of the R0 register
     */
    public static final short R0_ADDRESS = 0;

    /**
     * The address of the R1 register
     */
    public static final short R1_ADDRESS = 1;

    /**
     * The address of the R2 register
     */
    public static final short R2_ADDRESS = 2;

    /**
     * The address of the R3 register
     */
    public static final short R3_ADDRESS = 3;

    /**
     * The address of the R4 register
     */
    public static final short R4_ADDRESS = 4;

    /**
     * The address of the R5 register
     */
    public static final short R5_ADDRESS = 5;

    /**
     * The address of the R6 register
     */
    public static final short R6_ADDRESS = 6;

    /**
     * The address of the R7 register
     */
    public static final short R7_ADDRESS = 7;

    /**
     * The address of the R8 register
     */
    public static final short R8_ADDRESS = 8;

    /**
     * The address of the R9 register
     */
    public static final short R9_ADDRESS = 9;

    /**
     * The address of the R10 register
     */
    public static final short R10_ADDRESS = 10;

    /**
     * The address of the R11 register
     */
    public static final short R11_ADDRESS = 11;

    /**
     * The address of the R12 register
     */
    public static final short R12_ADDRESS = 12;

    /**
     * The address of the R13 register
     */
    public static final short R13_ADDRESS = 13;

    /**
     * The address of the R14 register
     */
    public static final short R14_ADDRESS = 14;

    /**
     * The address of the R15 register
     */
    public static final short R15_ADDRESS = 15;

    /**
     * Symbolizes an unknown address
     */
    public static final short UNKNOWN_ADDRESS = -1;


    // Assembly symbols

    /**
     * The name of the screen assembly symbol
     */
    public final static String SCREEN_NAME = "SCREEN";

    /**
     * The name of the keybaord assembly symbol
     */
    public final static String KEYBOARD_NAME = "KBD";

    /**
     * The name of the stack pointer assembly symbol
     */
    public final static String SP_NAME = "SP";

    /**
     * The name of the local register assembly symbol
     */
    public final static String LOCAL_POINTER_NAME = "LCL";

    /**
     * The name of the argumet register assembly symbol
     */
    public final static String ARG_POINTER_NAME = "ARG";

    /**
     * The name of the "this" register assembly symbol
     */
    public final static String THIS_POINTER_NAME = "THIS";

    /**
     * The name of the "that" register assembly symbol
     */
    public final static String THAT_POINTER_NAME = "THAT";

    /**
     * The name of the R0 register assembly symbol
     */
    public final static String R0_NAME = "R0";

    /**
     * The name of the R1 register assembly symbol
     */
    public final static String R1_NAME = "R1";

    /**
     * The name of the R2 register assembly symbol
     */
    public final static String R2_NAME = "R2";

    /**
     * The name of the R3 register assembly symbol
     */
    public final static String R3_NAME = "R3";

    /**
     * The name of the R4 register assembly symbol
     */
    public final static String R4_NAME = "R4";

    /**
     * The name of the R5 register assembly symbol
     */
    public final static String R5_NAME = "R5";

    /**
     * The name of the R6 register assembly symbol
     */
    public final static String R6_NAME = "R6";

    /**
     * The name of the R7 register assembly symbol
     */
    public final static String R7_NAME = "R7";

    /**
     * The name of the R8 register assembly symbol
     */
    public final static String R8_NAME = "R8";

    /**
     * The name of the R9 register assembly symbol
     */
    public final static String R9_NAME = "R9";

    /**
     * The name of the R10 register assembly symbol
     */
    public final static String R10_NAME = "R10";

    /**
     * The name of the R11 register assembly symbol
     */
    public final static String R11_NAME = "R11";

    /**
     * The name of the R12 register assembly symbol
     */
    public final static String R12_NAME = "R12";

    /**
     * The name of the R13 register assembly symbol
     */
    public final static String R13_NAME = "R13";

    /**
     * The name of the R14 register assembly symbol
     */
    public final static String R14_NAME = "R14";

    /**
     * The name of the R15 register assembly symbol
     */
    public final static String R15_NAME = "R15";

    // Key codes
    public static final short NEWLINE_KEY = 128;
    public static final short BACKSPACE_KEY = 129;
    public static final short LEFT_KEY = 130;
    public static final short UP_KEY = 131;
    public static final short RIGHT_KEY = 132;
    public static final short DOWN_KEY = 133;
    public static final short HOME_KEY = 134;
    public static final short END_KEY = 135;
    public static final short PAGE_UP_KEY = 136;
    public static final short PAGE_DOWN_KEY = 137;
    public static final short INSERT_KEY = 138;
    public static final short DELETE_KEY = 139;
    public static final short ESC_KEY = 140;
    public static final short F1_KEY = 141;
    public static final short F2_KEY = 142;
    public static final short F3_KEY = 143;
    public static final short F4_KEY = 144;
    public static final short F5_KEY = 145;
    public static final short F6_KEY = 146;
    public static final short F7_KEY = 147;
    public static final short F8_KEY = 148;
    public static final short F9_KEY = 149;
    public static final short F10_KEY = 150;
    public static final short F11_KEY = 151;
    public static final short F12_KEY = 152;

    // the single instance
    private static Definitions instance;

    // the translation table from pointer names to addresses
    private Hashtable addresses;

    // translation table for action key codes
    private short[] actionKeyCodes;

    // Constructor: initializes addresses and key codes.
    private Definitions() {
        initAddresses();
        initKeyCodes();
    }

    /**
     * Returns the single instance of the definitions object.
     */
    public static Definitions getInstance() {
        if (instance == null)
            instance = new Definitions();
        return instance;
    }

    /**
     * Computes an ALU's command with the given information and returns the result.
     * input0, input1 - the two ALU inputs.
     * zero0 - if true, zeros input0 before operation
     * zero1 - if true, zeros input1 before operation
     * negate0 - if true, negates input0 before operation
     * negate1 - if true, negates input1 before operation
     * ADDorAND - if true, ADDs the inputs. Otherwise, ANDs the inputs (logical AND)
     * negateOutput - if true, negates the output after the operation.
     */
    public static short computeALU(short input0, short input1, boolean zero0, boolean negate0,
                                   boolean zero1, boolean negate1, boolean ADDorAND,
                                   boolean negateOutput) {
        short result;

        if (zero0)
            input0 = 0;
        if (zero1)
            input1 = 0;
        if (negate0)
            input0 = (short)(~input0);
        if (negate1)
            input1 = (short)(~input1);
        if (ADDorAND)
            result = (short)(input0 + input1);
        else
            result = (short)(input0 & input1);
        if (negateOutput)
            result = (short)(~result);

        return result;
    }

    /**
     * Returns the translation table from pointer names to addresses.
     */
    public Hashtable getAddressesTable() {
        return (Hashtable)addresses.clone();
    }

    /**
     * Returns the hack key code from the given key event.
     */
    public short getKeyCode(KeyEvent e) {
        short key = 0;
        int letter = (int)e.getKeyChar();
        short code = (short)e.getKeyCode();

        if (letter == KeyEvent.CHAR_UNDEFINED)
            key = actionKeyCodes[code];
        else {
            if (code >= 65 && code <= 90)
                key = code;
            else if (letter == 8)
                key = BACKSPACE_KEY;
            else if (letter == 10)
                key = NEWLINE_KEY;
            else if (letter == 27)
                key = ESC_KEY;
            else if (letter == 127)
                key = DELETE_KEY;
            else
                key = (short)letter;
        }

        return key;
    }

    /**
     * Returns the key name from the given key event.
     */
    public String getKeyName(KeyEvent e) {
        String modifiers = e.getKeyModifiersText(e.getModifiers());
        String result = modifiers + (modifiers.length() > 0 ? "+" : "")
                        + e.getKeyText(e.getKeyCode());

        return result;
    }

    // initializes address translation table
    private void initAddresses() {
        addresses = new Hashtable();
        addresses.put(SP_NAME,new Short(SP_ADDRESS));
        addresses.put(LOCAL_POINTER_NAME,new Short(LOCAL_POINTER_ADDRESS));
        addresses.put(ARG_POINTER_NAME,new Short(ARG_POINTER_ADDRESS));
        addresses.put(THIS_POINTER_NAME,new Short(THIS_POINTER_ADDRESS));
        addresses.put(THAT_POINTER_NAME,new Short(THAT_POINTER_ADDRESS));
        addresses.put(R0_NAME,new Short(R0_ADDRESS));
        addresses.put(R1_NAME,new Short(R1_ADDRESS));
        addresses.put(R2_NAME,new Short(R2_ADDRESS));
        addresses.put(R3_NAME,new Short(R3_ADDRESS));
        addresses.put(R4_NAME,new Short(R4_ADDRESS));
        addresses.put(R5_NAME,new Short(R5_ADDRESS));
        addresses.put(R6_NAME,new Short(R6_ADDRESS));
        addresses.put(R7_NAME,new Short(R7_ADDRESS));
        addresses.put(R8_NAME,new Short(R8_ADDRESS));
        addresses.put(R9_NAME,new Short(R9_ADDRESS));
        addresses.put(R10_NAME,new Short(R10_ADDRESS));
        addresses.put(R11_NAME,new Short(R11_ADDRESS));
        addresses.put(R12_NAME,new Short(R12_ADDRESS));
        addresses.put(R13_NAME,new Short(R13_ADDRESS));
        addresses.put(R14_NAME,new Short(R14_ADDRESS));
        addresses.put(R15_NAME,new Short(R15_ADDRESS));
        addresses.put(SCREEN_NAME,new Short(SCREEN_START_ADDRESS));
        addresses.put(KEYBOARD_NAME,new Short(KEYBOARD_ADDRESS));
    }

    // prepare map of action keys from java codes to jack codes
    private void initKeyCodes() {
        actionKeyCodes = new short[255];
        actionKeyCodes[KeyEvent.VK_PAGE_UP] = PAGE_UP_KEY;
        actionKeyCodes[KeyEvent.VK_PAGE_DOWN] = PAGE_DOWN_KEY;
        actionKeyCodes[KeyEvent.VK_END] = END_KEY;
        actionKeyCodes[KeyEvent.VK_HOME] = HOME_KEY;
        actionKeyCodes[KeyEvent.VK_LEFT] = LEFT_KEY;
        actionKeyCodes[KeyEvent.VK_UP] = UP_KEY;
        actionKeyCodes[KeyEvent.VK_RIGHT] = RIGHT_KEY;
        actionKeyCodes[KeyEvent.VK_DOWN] = DOWN_KEY;
        actionKeyCodes[KeyEvent.VK_F1] = F1_KEY;
        actionKeyCodes[KeyEvent.VK_F2] = F2_KEY;
        actionKeyCodes[KeyEvent.VK_F3] = F3_KEY;
        actionKeyCodes[KeyEvent.VK_F4] = F4_KEY;
        actionKeyCodes[KeyEvent.VK_F5] = F5_KEY;
        actionKeyCodes[KeyEvent.VK_F6] = F6_KEY;
        actionKeyCodes[KeyEvent.VK_F7] = F7_KEY;
        actionKeyCodes[KeyEvent.VK_F8] = F8_KEY;
        actionKeyCodes[KeyEvent.VK_F9] = F9_KEY;
        actionKeyCodes[KeyEvent.VK_F10] = F10_KEY;
        actionKeyCodes[KeyEvent.VK_F11] = F11_KEY;
        actionKeyCodes[KeyEvent.VK_F12] = F12_KEY;
        actionKeyCodes[KeyEvent.VK_INSERT] = INSERT_KEY;
    }
}
