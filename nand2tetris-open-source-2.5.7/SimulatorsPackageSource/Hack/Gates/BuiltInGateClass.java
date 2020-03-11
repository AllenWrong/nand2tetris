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

/**
 * A GateClass for Built In gates.
 */
public class BuiltInGateClass extends GateClass {

    // the java class that holds the basic gate functionality
    private Class javaGateClass;

    /**
     * Constructs a new BuiltInGateClass with the given gate name and the HDLTokenizer
     * input which is positioned just after the BUILTIN declaration.
     * The HDL's input and output pin names are also given.
     */
    public BuiltInGateClass(String gateName, HDLTokenizer input, PinInfo[] inputPinsInfo, PinInfo[] outputPinsInfo)
     throws HDLException {
        super(gateName, inputPinsInfo, outputPinsInfo);

        // read java class name
        input.advance();
        if (input.getTokenType() != HDLTokenizer.TYPE_IDENTIFIER)
            input.HDLError("Missing java class name");

        String classFileName = input.getIdentifier();
        String fullName = GatesManager.getInstance().getBuiltInDir() + "." + classFileName;

        try {
            javaGateClass = Class.forName(fullName);
        } catch (ClassNotFoundException cnfe) {
            input.HDLError("Can't find " + classFileName + " java class");
        }

        // check that the class is a subclass of BuiltInGate
        Class currentClass = javaGateClass;
        boolean found;
        do {
            currentClass = currentClass.getSuperclass();
            found = currentClass.getName().equals("Hack.Gates.BuiltInGate");
        } while (!found && !currentClass.getName().equals("java.lang.Object"));

        if (!found)
            input.HDLError(classFileName + " is not a subclass of BuiltInGate");

        // read ';' symbol
        input.advance();
        if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == ';'))
            input.HDLError("Missing ';'");

        isInputClocked = new boolean[inputPinsInfo.length];
        isOutputClocked = new boolean[outputPinsInfo.length];

        input.advance();

        // check if clocked keyword exists
        if (input.getTokenType() == HDLTokenizer.TYPE_KEYWORD) {
            if (input.getKeywordType() != HDLTokenizer.KW_CLOCKED)
                input.HDLError("Unexpected keyword");

            isClocked = true;

            // read clocked input pins list
            String[] clockedNames = readPinNames(input);

            for (int i = 0; i < clockedNames.length; i++) {
                boolean inputFound = false;
                boolean outputFound = false;
                // check if clocked name is an input pin
                for (int j = 0; j < isInputClocked.length && !inputFound; j++) {
                    if (!isInputClocked[j]) {
                        inputFound = inputPinsInfo[j].name.equals(clockedNames[i]);
                        isInputClocked[j] = inputFound;
                    }
                }
                if (!inputFound) {
                    // check if clocked name is an output pin
                    for (int j = 0; j < isOutputClocked.length && !outputFound; j++) {
                        if (!isOutputClocked[j]) {
                            outputFound = outputPinsInfo[j].name.equals(clockedNames[i]);
                            isOutputClocked[j] = outputFound;
                        }
                    }
                }
            }

            input.advance();
        }

        if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL &&
              input.getSymbol() == '}'))
                input.HDLError("Missing '}'");
    }

    /**
     * Creates and returns a new instance of BuiltInGate.
     */
    public Gate newInstance() throws InstantiationException {
        BuiltInGate result;

        Node[] inputNodes = new Node[inputPinsInfo.length];
        Node[] outputNodes = new Node[outputPinsInfo.length];

        for (int i = 0; i < inputNodes.length; i++)
            inputNodes[i] = new Node();

        for (int i = 0; i < outputNodes.length; i++)
            outputNodes[i] = new Node();

        try {
            result = (BuiltInGate)javaGateClass.newInstance();
        } catch (IllegalAccessException iae) {
            throw new InstantiationException(iae.getMessage());
        }

        result.init(inputNodes, outputNodes, this);

        // if the gate has a gui component, add the gate to the gate manager
        // and set it to be its own parent for eval notifications
        if (result instanceof BuiltInGateWithGUI)
            GatesManager.getInstance().addChip((BuiltInGateWithGUI)result);

        // Add a DirtyGateAdapter as a listener to all the non-clocked inputs,
        // so the gate will become dirty when one of its non-clocked input changes.
        Node adapter = new DirtyGateAdapter(result);
        for (int i = 0; i < isInputClocked.length; i++)
            if (!isInputClocked[i])
                inputNodes[i].addListener(adapter);

        return result;
    }
}
