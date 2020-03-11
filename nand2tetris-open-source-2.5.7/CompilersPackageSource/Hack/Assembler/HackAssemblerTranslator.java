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

package Hack.Assembler;

import java.util.*;
import java.io.*;
import Hack.Utilities.*;
import Hack.Translators.*;

/**
 * A translation service between the Assembly text and the numeric instruction values.
 * The translation is bidirectional.
 * This is a singlton class.
 */
public class HackAssemblerTranslator {

    /**
     * Indicates an assembly line with no operation
     */
    public static final short NOP = (short)0x8000;

    // exp constants
    private static final Short ZERO         = new Short((short)0xea80);
    private static final Short ONE          = new Short((short)0xefc0);
    private static final Short MINUS_ONE    = new Short((short)0xee80);
    private static final Short EXP_D        = new Short((short)0xe300);
    private static final Short NOT_D        = new Short((short)0xe340);
    private static final Short EXP_M        = new Short((short)0xfc00);
    private static final Short EXP_A        = new Short((short)0xec00);
    private static final Short NOT_M        = new Short((short)0xfc40);
    private static final Short NOT_A        = new Short((short)0xec40);
    private static final Short MINUS_D      = new Short((short)0xe3c0);
    private static final Short MINUS_M      = new Short((short)0xfcc0);
    private static final Short MINUS_A      = new Short((short)0xecc0);
    private static final Short D_PLUS_ONE   = new Short((short)0xe7c0);
    private static final Short M_PLUS_ONE   = new Short((short)0xfdc0);
    private static final Short A_PLUS_ONE   = new Short((short)0xedc0);
    private static final Short D_MINUS_ONE  = new Short((short)0xe380);
    private static final Short M_MINUS_ONE  = new Short((short)0xfc80);
    private static final Short A_MINUS_ONE  = new Short((short)0xec80);
    private static final Short D_PLUS_M     = new Short((short)0xf080);
    private static final Short D_PLUS_A     = new Short((short)0xe080);
    private static final Short D_MINUS_M    = new Short((short)0xf4c0);
    private static final Short D_MINUS_A    = new Short((short)0xe4c0);
    private static final Short M_MINUS_D    = new Short((short)0xf1c0);
    private static final Short A_MINUS_D    = new Short((short)0xe1c0);
    private static final Short D_AND_M      = new Short((short)0xf000);
    private static final Short D_AND_A      = new Short((short)0xe000);
    private static final Short D_OR_M       = new Short((short)0xf540);
    private static final Short D_OR_A       = new Short((short)0xe540);

    // dest constants
    private static final Short A   = new Short((short)0x20);
    private static final Short M   = new Short((short)0x8);
    private static final Short D   = new Short((short)0x10);
    private static final Short AM  = new Short((short)0x28);
    private static final Short AD  = new Short((short)0x30);
    private static final Short MD  = new Short((short)0x18);
    private static final Short AMD = new Short((short)0x38);

    // jmp constants
    private static final Short JMP              = new Short((short)0x7);
    private static final Short JMP_LESS_THEN    = new Short((short)0x4);
    private static final Short JMP_EQUAL        = new Short((short)0x2);
    private static final Short JMP_GREATER_THEN  = new Short((short)0x1);
    private static final Short JMP_NOT_EQUAL    = new Short((short)0x5);
    private static final Short JMP_LESS_EQUAL   = new Short((short)0x6);
    private static final Short JMP_GREATER_EQUAL = new Short((short)0x3);

    // the single instance
    private static HackAssemblerTranslator instance;

    // The translation tables from text to codes
    private Hashtable expToCode, destToCode, jmpToCode;

    // The translation table from code to text.
    private Hashtable expToText, destToText, jmpToText;


    /**
     * Creates a new translator.
     */
    private HackAssemblerTranslator() {
        instance = this;
        initExp();
        initDest();
        initJmp();
    }

    /**
     * Returns the single instance of the translator.
     */
    public static HackAssemblerTranslator getInstance() {
        if (instance == null)
            new HackAssemblerTranslator();
        return instance;
    }

    /**
     * Returns the code which represents the given exp text.
     * If doesn't exist, throws AssemblerException.
     */
    public short getExpByText(String text) throws AssemblerException {
        Short code = (Short)expToCode.get(text);
        if (code == null)
            throw new AssemblerException("Illegal exp: " + text);
        return code.shortValue();
    }

    /**
     * Returns the text which represents the given exp code.
     * If doesn't exist, throws AssemblerException.
     */
    public String getExpByCode(short code) throws AssemblerException {
        String result = (String)expToText.get(new Short(code));
        if (result == null)
            throw new AssemblerException("Illegal exp: " + code);
        return result;
    }

    /**
     * Returns the code which represents the given dest text.
     * If doesn't exist, throws AssemblerException.
     */
    public short getDestByText(String text) throws AssemblerException {
        Short code = (Short)destToCode.get(text);
        if (code == null)
            throw new AssemblerException("Illegal dest: " + text);
        return code.shortValue();
    }

    /**
     * Returns the text which represents the given dest code.
     * If doesn't exist, throws AssemblerException.
     */
    public String getDestByCode(short code) throws AssemblerException {
        String result = (String)destToText.get(new Short(code));
        if (result == null)
            throw new AssemblerException("Illegal dest: " + code);
        return result;
    }

    /**
     * Returns the code which represents the given jmp text.
     * If doesn't exist, throws AssemblerException.
     */
    public short getJmpByText(String text) throws AssemblerException {
        Short code = (Short)jmpToCode.get(text);
        if (code == null)
            throw new AssemblerException("Illegal jmp: " + text);
        return code.shortValue();
    }

    /**
     * Returns the text which represents the given jmp code.
     * If doesn't exist, throws AssemblerException.
     */
    public String getJmpByCode(short code) throws AssemblerException {
        String result = (String)jmpToText.get(new Short(code));
        if (result == null)
            throw new AssemblerException("Illegal jmp: " + code);
        return result;
    }

    /**
     * Translates the given assembly language command and returns the corresponding
     * machine language code.
     * If the command is not legal, throws AssemblerException.
     */
    public short textToCode(String command) throws AssemblerException {
        short code = 0;
        short expCode = 0, jmpCode = 0, destCode = 0;

        try {
            AssemblyLineTokenizer input = new AssemblyLineTokenizer(command);

            if (input.isToken("@")) {
                input.advance(true);
                try {
                    code = Short.parseShort(input.token());
                } catch (NumberFormatException nfe) {
                    throw new AssemblerException("A numeric value is expected");
                }
            }
            else { // compute-store-jump command

                String firstToken = input.token();
                input.advance(false);

                // find dest (if any)
                if (input.isToken("=")) {
                    Short dest = (Short)destToCode.get(firstToken);
                    if (dest == null)
                        throw new AssemblerException("Destination expected");

                    destCode = dest.shortValue();
                    input.advance(true);
                }

                // find exp
                Short exp;
                if (!firstToken.equals("=") && destCode == 0)
                    exp = (Short)expToCode.get(firstToken);
                else
                    exp = (Short)expToCode.get(input.token());

                if (exp == null)
                    throw new AssemblerException("Expression expected");

                expCode = exp.shortValue();
                input.advance(false);

                if (input.isToken(";"))
                    input.advance(false);

                // find jmp (if any)
                if (!input.isEnd()) {
                    Short jmp = (Short)jmpToCode.get(input.token());
                    if (jmp == null)
                        throw new AssemblerException("Jump directive expected");

                    jmpCode = jmp.shortValue();
                    input.ensureEnd();
                }

                code = (short)(destCode + expCode + jmpCode);
            }

        } catch (IOException ioe) {
            throw new AssemblerException("Error while parsing assembly line");
        } catch (HackTranslatorException hte) {
            throw new AssemblerException(hte.getMessage());
        }

        return code;
    }

    /**
     * Translates the given machine language code and returns the corresponding Assembly
     * language command (String).
     * If illegal, throws AssemblerException.
     */
    public String codeToText(short code) throws AssemblerException {
        StringBuffer command = new StringBuffer();

        if (code != HackAssemblerTranslator.NOP) {
            if ((code & 0x8000) == 0) {
                command.append('@');
                command.append(code);
            }
            else {
                short exp = (short)(code & 0xffc0);
                short dest = (short)(code & 0x0038);
                short jmp = (short)(code & 0x0007);

                String expText = getExpByCode(exp);
                if (!expText.equals("")) {

                    if (dest != 0) {
                        command.append(getDestByCode(dest));
                        command.append('=');
                    }

                    command.append(expText);

                    if (jmp != 0) {
                        command.append(';');
                        command.append(getJmpByCode(jmp));
                    }
                }
            }
        }

        return command.toString();
    }

    /**
     * Loads the given program file (HACK or ASM) and returns a memory array of
     * the given size that contains the program. The given null value will be used
     * to fill the memory array initially.
     */
    public static short[] loadProgram(String fileName, int size, short nullValue)
     throws AssemblerException {
        short[] memory = null;

        File file = new File(fileName);
        if (!file.exists())
            throw new AssemblerException(fileName + " doesn't exist");

        if (fileName.endsWith(".hack")) {
            memory = new short[size];
            for (int i = 0; i < size; i++)
                memory[i] = nullValue;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                int pc = 0;

                while ((line = reader.readLine()) != null) {

                    short value = 0;

                    if (pc >= size)
                        throw new AssemblerException("Program too large");

                    try {
                        value = (short)Conversions.binaryToInt(line);
                    } catch (NumberFormatException nfe) {
                        throw new AssemblerException("Illegal character");
                    }

                    memory[pc++] = value;
                }

                reader.close();
            } catch (IOException ioe) {
                throw new AssemblerException("IO error while reading " + fileName);
            }
        }
        else if (fileName.endsWith(".asm")) {
            try {
                HackAssembler assembler = new HackAssembler(fileName, size, nullValue, false);
                memory = assembler.getProgram();
            } catch (HackTranslatorException ae) {
                throw new AssemblerException(ae.getMessage());
            }
        }
        else
            throw new AssemblerException(fileName + " is not a .hack or .asm file");

        return memory;
    }

    // initializes the exp table
    private void initExp() {
        expToCode = new Hashtable();
        expToText = new Hashtable();

        expToCode.put("0",ZERO);
        expToCode.put("1",ONE);
        expToCode.put("-1",MINUS_ONE);
        expToCode.put("D",EXP_D);
        expToCode.put("!D",NOT_D);
        expToCode.put("NOTD",NOT_D);
        expToCode.put("M",EXP_M);
        expToCode.put("A",EXP_A);
        expToCode.put("!M",NOT_M);
        expToCode.put("NOTM",NOT_M);
        expToCode.put("!A",NOT_A);
        expToCode.put("NOTA",NOT_A);
        expToCode.put("-D",MINUS_D);
        expToCode.put("-M",MINUS_M);
        expToCode.put("-A",MINUS_A);
        expToCode.put("D+1",D_PLUS_ONE);
        expToCode.put("M+1",M_PLUS_ONE);
        expToCode.put("A+1",A_PLUS_ONE);
        expToCode.put("D-1",D_MINUS_ONE);
        expToCode.put("M-1",M_MINUS_ONE);
        expToCode.put("A-1",A_MINUS_ONE);
        expToCode.put("D+M",D_PLUS_M);
        expToCode.put("M+D",D_PLUS_M);
        expToCode.put("D+A",D_PLUS_A);
        expToCode.put("A+D",D_PLUS_A);
        expToCode.put("D-M",D_MINUS_M);
        expToCode.put("D-A",D_MINUS_A);
        expToCode.put("M-D",M_MINUS_D);
        expToCode.put("A-D",A_MINUS_D);
        expToCode.put("D&M",D_AND_M);
        expToCode.put("M&D",D_AND_M);
        expToCode.put("D&A",D_AND_A);
        expToCode.put("A&D",D_AND_A);
        expToCode.put("D|M",D_OR_M);
        expToCode.put("M|D",D_OR_M);
        expToCode.put("D|A",D_OR_A);
        expToCode.put("A|D",D_OR_A);

        expToText.put(ZERO,"0");
        expToText.put(ONE,"1");
        expToText.put(MINUS_ONE,"-1");
        expToText.put(EXP_D,"D");
        expToText.put(NOT_D,"!D");
        expToText.put(EXP_M,"M");
        expToText.put(EXP_A,"A");
        expToText.put(NOT_M,"!M");
        expToText.put(NOT_A,"!A");
        expToText.put(MINUS_D,"-D");
        expToText.put(MINUS_M,"-M");
        expToText.put(MINUS_A,"-A");
        expToText.put(D_PLUS_ONE,"D+1");
        expToText.put(M_PLUS_ONE,"M+1");
        expToText.put(A_PLUS_ONE,"A+1");
        expToText.put(D_MINUS_ONE,"D-1");
        expToText.put(M_MINUS_ONE,"M-1");
        expToText.put(A_MINUS_ONE,"A-1");
        expToText.put(D_PLUS_M,"D+M");
        expToText.put(D_PLUS_A,"D+A");
        expToText.put(D_MINUS_M,"D-M");
        expToText.put(D_MINUS_A,"D-A");
        expToText.put(M_MINUS_D,"M-D");
        expToText.put(A_MINUS_D,"A-D");
        expToText.put(D_AND_M,"D&M");
        expToText.put(D_AND_A,"D&A");
        expToText.put(D_OR_M,"D|M");
        expToText.put(D_OR_A,"D|A");
    }

    // initializes the dest table
    private void initDest() {
        destToCode = new Hashtable();
        destToText = new Hashtable();

        destToCode.put("A",A);
        destToCode.put("M",M);
        destToCode.put("D",D);
        destToCode.put("AM",AM);
        destToCode.put("AD",AD);
        destToCode.put("MD",MD);
        destToCode.put("AMD",AMD);

        destToText.put(A,"A");
        destToText.put(M,"M");
        destToText.put(D,"D");
        destToText.put(AM,"AM");
        destToText.put(AD,"AD");
        destToText.put(MD,"MD");
        destToText.put(AMD,"AMD");
    }

    // initializes the jmp table
    private void initJmp() {
        jmpToCode = new Hashtable();
        jmpToText = new Hashtable();

        jmpToCode.put("JMP",JMP);
        jmpToCode.put("JLT",JMP_LESS_THEN);
        jmpToCode.put("JEQ",JMP_EQUAL);
        jmpToCode.put("JGT",JMP_GREATER_THEN);
        jmpToCode.put("JNE",JMP_NOT_EQUAL);
        jmpToCode.put("JLE",JMP_LESS_EQUAL);
        jmpToCode.put("JGE",JMP_GREATER_EQUAL);

        jmpToText.put(JMP,"JMP");
        jmpToText.put(JMP_LESS_THEN,"JLT");
        jmpToText.put(JMP_EQUAL,"JEQ");
        jmpToText.put(JMP_GREATER_THEN,"JGT");
        jmpToText.put(JMP_NOT_EQUAL,"JNE");
        jmpToText.put(JMP_LESS_EQUAL,"JLE");
        jmpToText.put(JMP_GREATER_EQUAL,"JGE");
    }
}
