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

package Hack.Gates;

import java.util.*;
import java.io.*;

/**
 * A factory and information source for gates.
 */
public abstract class GateClass {

    /**
     * The input pin type
     */
    public static final byte UNKNOWN_PIN_TYPE = 0;

    /**
     * The input pin type
     */
    public static final byte INPUT_PIN_TYPE = 1;

    /**
     * The output pin type
     */
    public static final byte OUTPUT_PIN_TYPE = 2;

    // input and output pin names
    protected PinInfo[] inputPinsInfo;
    protected PinInfo[] outputPinsInfo;

    // The name of the gate
    protected String name;

    // true if this gate is clocked
    protected boolean isClocked;

    // true if the corresponding input is clocked
    protected boolean[] isInputClocked;

    // true if the corresponding output is clocked
    protected boolean[] isOutputClocked;

    // Mapping from pin names to their types (INPUT_PIN_TYPE, OUTPUT_PIN_TYPE)
    protected Hashtable namesToTypes;

    // Mapping from pin names to their numbers (Integer objects)
    protected Hashtable namesToNumbers;

    // a table that maps a gate name with its GateClass
    protected static Hashtable GateClasses = new Hashtable();


    // Constructs a new GateCLass (public access through the getGateClass method)
    protected GateClass(String gateName, PinInfo[] inputPinsInfo, PinInfo[] outputPinsInfo) {
        namesToTypes = new Hashtable();
        namesToNumbers = new Hashtable();

        this.name = gateName;

        this.inputPinsInfo = inputPinsInfo;
        registerPins(inputPinsInfo, INPUT_PIN_TYPE);
        this.outputPinsInfo = outputPinsInfo;
        registerPins(outputPinsInfo, OUTPUT_PIN_TYPE);
    }

    /**
     * Returns the GateClass associated with the given gate name.
     * If containsPath is true, the gate name is assumed to contain the full
     * path of the hdl file. If doesn't contain path, looks for the hdl file
     * according to the directory hierarchy.
     * If the GateClass doesn't exist yet, creates the GateClass by parsing the hdl file.
     */
    public static GateClass getGateClass(String gateName, boolean containsPath) throws HDLException {
        String fileName = null;

        // find hdl file name according to the gate name.
        if (!containsPath) {
            fileName = GatesManager.getInstance().getHDLFileName(gateName);
            if (fileName == null)
                throw new HDLException("Chip " + gateName +
                                       " is not found in the working and built in folders");
        }
        else {
            fileName = gateName;
            File file = new File(fileName);
            if (!file.exists())
                throw new HDLException("Chip " + fileName + " doesn't exist");

            gateName = file.getName().substring(0, file.getName().lastIndexOf("."));
        }

        // Try to find the gate in the "cache"
        GateClass result = (GateClass)GateClasses.get(fileName);

        // gate wasn't found in cache
        if (result == null) {
            HDLTokenizer input = new HDLTokenizer(fileName);
            result = readHDL(input, gateName);
            GateClasses.put(fileName, result);
        }

        return result;
    }

    /**
     * Clears the gate Cache
     */
    public static void clearGateCache() {
        GateClasses.clear();
    }

    /**
     * Returns true if a GateClass exists for the given gate name.
     */
    public static boolean gateClassExists(String gateName) {
        String fileName = GatesManager.getInstance().getHDLFileName(gateName);
        return (GateClasses.get(fileName) != null);
    }

    // Loads the HDL from the given input, creates the appropriate GateClass and returns it.
    private static GateClass readHDL(HDLTokenizer input, String gateName)
     throws HDLException {

        // read CHIP keyword
        input.advance();
        if (!(input.getTokenType() == HDLTokenizer.TYPE_KEYWORD
              && input.getKeywordType() == HDLTokenizer.KW_CHIP))
            input.HDLError("Missing 'CHIP' keyword");

        // read gate name
        input.advance();
        if (input.getTokenType() != HDLTokenizer.TYPE_IDENTIFIER)
            input.HDLError("Missing chip name");
        String foundGateName = input.getIdentifier();
        if (!gateName.equals(foundGateName))
            input.HDLError("Chip name doesn't match the HDL name");

        // read '{' symbol
        input.advance();
        if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL
              && input.getSymbol() == '{'))
            input.HDLError("Missing '{'");

        // read IN keyword
        PinInfo[] inputPinsInfo, outputPinsInfo;
        input.advance();
        if (input.getTokenType() == HDLTokenizer.TYPE_KEYWORD
              && input.getKeywordType() == HDLTokenizer.KW_IN) {
            // read input pins list
            inputPinsInfo = getPinsInfo(input, readPinNames(input));
            input.advance();
        }
        else
            // no input pins
            inputPinsInfo = new PinInfo[0];

        // read OUT keyword
        if (input.getTokenType() == HDLTokenizer.TYPE_KEYWORD
              && input.getKeywordType() == HDLTokenizer.KW_OUT){
            // read output pins list
            outputPinsInfo = getPinsInfo(input, readPinNames(input));
            input.advance();
        }
        else
            // no output pins
            outputPinsInfo = new PinInfo[0];

        GateClass result = null;

        // read BuiltIn/Parts keyword
        if (input.getTokenType() == HDLTokenizer.TYPE_KEYWORD
             && input.getKeywordType() == HDLTokenizer.KW_BUILTIN)
            result = new BuiltInGateClass(gateName, input, inputPinsInfo, outputPinsInfo);
        else if (input.getTokenType() == HDLTokenizer.TYPE_KEYWORD
                 && input.getKeywordType() == HDLTokenizer.KW_PARTS) {
            result = new CompositeGateClass(gateName, input, inputPinsInfo, outputPinsInfo);
        }
        else
            input.HDLError("Keyword expected");

        return result;
    }

    // Returns an array of pin names read from the input (names may contain width specification).
    protected static String[] readPinNames(HDLTokenizer input)
     throws HDLException {
        Vector list = new Vector();
        boolean exit = false;
        input.advance();

        while (!exit) {
            // check ';' symbol
            if (input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == ';')
                exit = true;
            else {
                // read pin name
                if (input.getTokenType() != HDLTokenizer.TYPE_IDENTIFIER)
                    input.HDLError("Pin name expected");

                String pinName = input.getIdentifier();
                list.addElement(pinName);

                // check seperator
                input.advance();
                if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL
                      && (input.getSymbol() == ',' || input.getSymbol() == ';')))
                    input.HDLError("',' or ';' expected");
                if (input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == ',')
                    input.advance();
            }
        }

        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    // Returns a PinInfo array according to the given pin names
    // (which may contain width specification).
    private static PinInfo[] getPinsInfo(HDLTokenizer input, String[] names)
     throws HDLException {
        PinInfo[] result = new PinInfo[names.length];

        for (int i = 0; i < names.length; i++) {
            result[i] = new PinInfo();
            int bracketsPos = names[i].indexOf("[");
            if (bracketsPos >= 0) {
                try {
                    String width = names[i].substring(bracketsPos + 1, names[i].indexOf("]"));
                    result[i].width = (byte)Integer.parseInt(width);
                    result[i].name = names[i].substring(0, bracketsPos);
                } catch (Exception e) {
                    input.HDLError(names[i] + " has an invalid bus width");
                }
            }
            else {
                result[i].width = 1;
                result[i].name = names[i];
            }
        }

        return result;
    }

    /**
     * Returns the PinInfo according to the given pin type and number.
     * If doesn't exist, return null.
     */
    public PinInfo getPinInfo(byte type, int number) {
        PinInfo result = null;

        switch (type) {
            case INPUT_PIN_TYPE:
                if (number < inputPinsInfo.length)
                    result = inputPinsInfo[number];
                break;
            case OUTPUT_PIN_TYPE:
                if (number < outputPinsInfo.length)
                    result = outputPinsInfo[number];
                break;
        }

        return result;
    }

    /**
     * Returns the PinInfo according to the given pin name.
     * If doesn't exist, return null.
     */
    public PinInfo getPinInfo(String name) {
        byte type = getPinType(name);
        int index = getPinNumber(name);
        return getPinInfo(type, index);
    }

    /**
     * Registers the given pins with their given type and numbers.
     */
    protected void registerPins(PinInfo[] pins, byte type) {
        for (int i = 0; i < pins.length; i++) {
            namesToTypes.put(pins[i].name, new Byte(type));
            namesToNumbers.put(pins[i].name, new Integer(i));
        }
    }

    /**
     * Registers the given pin with its given type and number.
     */
    protected void registerPin(PinInfo pin, byte type, int number) {
        namesToTypes.put(pin.name, new Byte(type));
        namesToNumbers.put(pin.name, new Integer(number));
    }

    /**
     * Returns the type of the given pinName.
     * If not found, returns UNKNOWN_PIN_TYPE.
     */
    public byte getPinType(String pinName) {
        Byte result = (Byte)namesToTypes.get(pinName);
        return (result != null ? result.byteValue() : UNKNOWN_PIN_TYPE);
    }

    /**
     * Returns the number of the given pinName.
     * If not found, returns -1.
     */
    public int getPinNumber(String pinName) {
        Integer result = (Integer)namesToNumbers.get(pinName);
        return (result != null ? result.intValue() : -1);
    }

    /**
     * Returns the name of the gate.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if this gate is clocked.
     */
    public boolean isClocked() {
        return isClocked;
    }

    /**
     * Creates and returns a new Gate instance of this GateClass type.
     */
    public abstract Gate newInstance() throws InstantiationException;
}
