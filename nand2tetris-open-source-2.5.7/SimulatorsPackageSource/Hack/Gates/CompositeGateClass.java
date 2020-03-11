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
import Hack.Utilities.*;

/**
 * A GateClass for composite gates.
 */
public class CompositeGateClass extends GateClass {

    /**
     * The internal pin type
     */
    public static final byte INTERNAL_PIN_TYPE = 3;

    /**
     * The info of the "true" special node
     */
    public static final PinInfo TRUE_NODE_INFO = new PinInfo("true", (byte)16);

    /**
     * The info of the "false" special node
     */
    public static final PinInfo FALSE_NODE_INFO = new PinInfo("false", (byte)16);

    /**
     * The info of the "clock" special node
     */
    public static final PinInfo CLOCK_NODE_INFO = new PinInfo("clk", (byte)1);


    // internal pins info
    protected Vector internalPinsInfo;

    // The list of contained GateClasses (parts)
    private Vector partsList;

    // Array of indice of parts (taken from the parts vector), in a topological order.
    private int[] partsOrder;

    // The set of connections between the gate and its parts
    private ConnectionSet connections;

    /**
     * Constructs a new CompositeGateClass with the given gate name and the HDLTokenizer input
     * which is positioned just after the PARTS: declaration.
     * The HDL's input and output pin names are also given.
     */
    public CompositeGateClass(String gateName, HDLTokenizer input, PinInfo[] inputPinsInfo, PinInfo[] outputPinsInfo)
     throws HDLException {
        super(gateName, inputPinsInfo, outputPinsInfo);

        partsList = new Vector();
        internalPinsInfo = new Vector();
        connections = new ConnectionSet();
        isInputClocked = new boolean[inputPinsInfo.length];
        isOutputClocked = new boolean[outputPinsInfo.length];

        readParts(input);

        Graph graph = createConnectionsGraph();

        // runs the topological sort, starting from the "master parts" node,
        // which connects to all the parts. This will also check for circles.
        Object[] topologicalOrder = graph.topologicalSort(partsList);

        if (graph.hasCircle())
            throw new HDLException("This chip has a circle in its parts connections");

        // create the partsOrder array, by taking from the topologicalOrder
        // only the Integer objects, which represent the parts.
        partsOrder = new int[partsList.size()];
        int counter = 0;
        for (int i = 0; i < topologicalOrder.length; i++) {
            if (topologicalOrder[i] instanceof Integer)
                partsOrder[counter++] = ((Integer)topologicalOrder[i]).intValue();
        }

        // for each input pin, check if there is a path in the graph to an output pin
        // (actually to the "master output", which all outputs connect to).
        // If there is, the input is not clocked. Otherwise, it is.
        for (int i = 0; i < inputPinsInfo.length; i++)
            isInputClocked[i] = !graph.pathExists(inputPinsInfo[i], outputPinsInfo);

        // for each output pin, check if there is a path in the graph from any input pin
        // (actually from the "master input", which connects to all inputs) to this output pin.
        // If there is, the output is not clocked. Otherwise, it is.
        for (int i = 0; i < outputPinsInfo.length; i++)
            isOutputClocked[i] = !graph.pathExists(inputPinsInfo, outputPinsInfo[i]);
    }

    // Reads the parts list from the given HDL input
    private void readParts(HDLTokenizer input)
     throws HDLException {
        boolean endOfParts = false;

        while (input.hasMoreTokens() && !endOfParts) {

            input.advance();

            // check if end of hdl
            if (input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == '}')
                endOfParts = true;
            else {
                // check if part name
                if (!(input.getTokenType() == HDLTokenizer.TYPE_IDENTIFIER))
                    input.HDLError("A GateClass name is expected");

                String partName = input.getIdentifier();
                GateClass gateClass = getGateClass(partName, false);
                partsList.addElement(gateClass);
                isClocked = isClocked || gateClass.isClocked;
                int partNumber = partsList.size() - 1;

                // check '('
                input.advance();
                if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == '('))
                    input.HDLError("Missing '('");

                readPinNames(input, partNumber, partName);

                // check ';'
                input.advance();
                if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == ';'))
                    input.HDLError("Missing ';'");

            }
        }
		if (!endOfParts) {
			input.HDLError("Missing '}'");
		}
		if (input.hasMoreTokens()) {
			input.HDLError("Expected end-of-file after '}'");
		}

        // check if internal pins have no source
        boolean[] hasSource = new boolean[internalPinsInfo.size()];
        Iterator connectionIter = connections.iterator();
        while (connectionIter.hasNext()) {
            Connection connection = (Connection)connectionIter.next();
            if (connection.getType() == Connection.TO_INTERNAL)
                hasSource[connection.getGatePinNumber()] = true;
        }
        for (int i = 0; i < hasSource.length; i++)
            if (!hasSource[i])
                input.HDLError(((PinInfo)internalPinsInfo.elementAt(i)).name +
                               " has no source pin");
    }

    // Reads the pin names list from the HDL input. Returns the input after the ')' .
    private void readPinNames(HDLTokenizer input, int partNumber, String partName)
     throws HDLException {
        boolean endOfPins = false;

        // read pin names
        while (!endOfPins) {
            // read left pin name
            input.advance();
            if (!(input.getTokenType() == HDLTokenizer.TYPE_IDENTIFIER))
                input.HDLError("A pin name is expected");

            String leftName = input.getIdentifier();

            // check '='
            input.advance();
            if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == '='))
                input.HDLError("Missing '='");

            // read right pin name
            input.advance();
            if (!(input.getTokenType() == HDLTokenizer.TYPE_IDENTIFIER))
                input.HDLError("A pin name is expected");

            String rightName = input.getIdentifier();
            addConnection(input, partNumber, partName, leftName, rightName);

            // check ',' or ')'
            input.advance();
            if (input.getTokenType() == HDLTokenizer.TYPE_SYMBOL && input.getSymbol() == ')')
                endOfPins = true;
            else if (!(input.getTokenType() == HDLTokenizer.TYPE_SYMBOL
                       && input.getSymbol() == ','))
                input.HDLError("',' or ')' are expected");
        }
    }

    // Returns an array of two integers: the low and high bits of the given pin name.
    // If no sub bus specified, returns null.
    // This ensures that if result != null, than result[0] <= result[1] and result[0] > 0
    private static byte[] getSubBusAndCheck(HDLTokenizer input, String pinName, int busWidth)
     throws HDLException {
        byte[] result = null;

        try {
            result = getSubBus(pinName);
        } catch (Exception e) {
            input.HDLError(pinName + " has an invalid sub bus specification");
        }

        if (result != null) {
            if (result[0] < 0 || result[1] < 0)
                input.HDLError(pinName + ": negative bit numbers are illegal");
            else if (result[0] > result[1])
                input.HDLError(pinName + ": left bit number should be lower than the right one");
            else if (result[1] >= busWidth)
                input.HDLError(pinName + ": the specified sub bus is not in the bus range");
        }

        return result;
    }

    /**
     * Returns an array of two integers: the low and high bits of the given pin name.
     * If no sub bus specified, returns null.
     * If illegal name, throws a HDLException.
     */
    public static byte[] getSubBus(String pinName)
     throws Exception {
        byte[] result = null;

        int bracketsPos = pinName.indexOf("[");
        if (bracketsPos >= 0) {
            result = new byte[2];
            String num = null;
            int dotsPos = pinName.indexOf("..");
            if (dotsPos >= 0) {
                num = pinName.substring(bracketsPos + 1, dotsPos);
                result[0] = Byte.parseByte(num);
                num = pinName.substring(dotsPos + 2, pinName.indexOf("]"));
                result[1] = Byte.parseByte(num);
            }
            else {
                num = pinName.substring(bracketsPos + 1, pinName.indexOf("]"));
                result[0] = Byte.parseByte(num);
                result[1] = result[0];
            }
        }

        return result;
    }

    // Adds a connection between the two given pin names, where fullLeftName is a pin
    // of the part, and fullRightName is a pin of this CompositeGateClass.
    // Both pin names may include sub bus specification.
    private void addConnection(HDLTokenizer input, int partNumber, String partName,
                               String fullLeftName, String fullRightName) throws HDLException {

        GateClass partGateClass = (GateClass)partsList.elementAt(partNumber);
        String leftName, rightName;
        byte connectionType = 0;

        // find left pin name (without sub bus specification)
        int bracketsPos = fullLeftName.indexOf("[");
        leftName = (bracketsPos >= 0 ? fullLeftName.substring(0, bracketsPos) : fullLeftName);

        // find left pin info. If doesn't exist - error.
        byte leftType = partGateClass.getPinType(leftName);
        if (leftType == UNKNOWN_PIN_TYPE)
            input.HDLError(leftName + " is not a pin in " + partName);
        int leftNumber = partGateClass.getPinNumber(leftName);
        PinInfo leftPinInfo = partGateClass.getPinInfo(leftType, leftNumber);
        byte[] leftSubBus = getSubBusAndCheck(input, fullLeftName, leftPinInfo.width);
        byte leftWidth = (leftSubBus == null ? leftPinInfo.width :
                                               (byte)(leftSubBus[1] - leftSubBus[0] + 1));

        // find right pin name (without sub bus specification)
        bracketsPos = fullRightName.indexOf("[");
        rightName = (bracketsPos >= 0 ? fullRightName.substring(0, bracketsPos) : fullRightName);
        PinInfo rightPinInfo;
        int rightNumber = 0;
        byte rightType = GateClass.UNKNOWN_PIN_TYPE;
        boolean selfFittingWidth = false;

        // check if special nodes
        if (rightName.equals(CompositeGateClass.TRUE_NODE_INFO.name)) {
            rightPinInfo = TRUE_NODE_INFO;
            connectionType = Connection.FROM_TRUE;
            selfFittingWidth = true;
        }
        else if (rightName.equals(CompositeGateClass.FALSE_NODE_INFO.name)) {
            rightPinInfo = FALSE_NODE_INFO;
            connectionType = Connection.FROM_FALSE;
            selfFittingWidth = true;
        }
        else if (rightName.equals(CompositeGateClass.CLOCK_NODE_INFO.name)) {
            rightPinInfo = CLOCK_NODE_INFO;
            connectionType = Connection.FROM_CLOCK;
        }
        else {
            rightType = getPinType(rightName);

            // check that not sub bus of intenral
            if ((rightType == UNKNOWN_PIN_TYPE || rightType == INTERNAL_PIN_TYPE) &&
                !fullRightName.equals(rightName))
                    input.HDLError(fullRightName + ": sub bus of an internal node may not be used");

            // find right pin's info. If doesn't exist, create it as an internal pin.
            if (rightType == UNKNOWN_PIN_TYPE) {
                rightType = INTERNAL_PIN_TYPE;
                rightPinInfo = new PinInfo();
                rightPinInfo.name = rightName;
                rightPinInfo.width = leftWidth;
                internalPinsInfo.addElement(rightPinInfo);
                rightNumber = internalPinsInfo.size() - 1;
                registerPin(rightPinInfo, INTERNAL_PIN_TYPE, rightNumber);
            }
            else {
                rightNumber = getPinNumber(rightName);
                rightPinInfo = getPinInfo(rightType, rightNumber);
            }
        }

        byte[] rightSubBus;
        int rightWidth;

        if (selfFittingWidth) {
            if(!rightName.equals(fullRightName))
                input.HDLError(rightName + " may not be subscripted");

            rightWidth = leftWidth;
            rightSubBus = new byte[]{0, (byte)(rightWidth - 1)};
        }
        else {
            rightSubBus = getSubBusAndCheck(input, fullRightName, rightPinInfo.width);
            rightWidth = (rightSubBus == null ? rightPinInfo.width :
                                                    rightSubBus[1] - rightSubBus[0] + 1);
        }

        // check that right & left has the same width
        if (leftWidth != rightWidth)
            input.HDLError(leftName + "(" + leftWidth + ") and " + rightName + "(" + rightWidth +
                           ") have different bus widths");

        // make sure that an internal pin is only fed once by a part's output pin
        if ((rightType == INTERNAL_PIN_TYPE) && (leftType == OUTPUT_PIN_TYPE)) {
            if (rightPinInfo.isInitialized(rightSubBus))
                input.HDLError("An internal pin may only be fed once by a part's output pin");
            else
                rightPinInfo.initialize(rightSubBus);
        }

        // make sure that an output pin is only fed once by a part's output pin
        if ((rightType == OUTPUT_PIN_TYPE) && (leftType == OUTPUT_PIN_TYPE)) {
            if (rightPinInfo.isInitialized(rightSubBus))
                input.HDLError("An output pin may only be fed once by a part's output pin");
            else
                rightPinInfo.initialize(rightSubBus);
        }

        // find connection type
        switch (leftType) {

            case INPUT_PIN_TYPE:
                switch (rightType) {
                    case INPUT_PIN_TYPE:
                        connectionType = Connection.FROM_INPUT;
                        break;
                    case INTERNAL_PIN_TYPE:
                        connectionType = Connection.FROM_INTERNAL;
                        break;
                    case OUTPUT_PIN_TYPE:
                        input.HDLError("Can't connect gate's output pin to part");
                }
                break;

            case OUTPUT_PIN_TYPE:
                switch (rightType) {
                    case INPUT_PIN_TYPE:
                        input.HDLError("Can't connect part's output pin to gate's input pin");
                    case INTERNAL_PIN_TYPE:
                        connectionType = Connection.TO_INTERNAL;
                        break;
                    case OUTPUT_PIN_TYPE:
                        connectionType = Connection.TO_OUTPUT;
                        break;
                }
                break;
        }

        Connection connection = new Connection(connectionType, rightNumber, partNumber,
                                               leftName, rightSubBus, leftSubBus);
        connections.add(connection);
    }

    /*
      Creates and returns the graph of the connections in the chip.
      The nodes in the graph are:
      1. Internal parts, represented with Integer objects containing the part's numbers.
      2. Input, Output, Internal and special nodes, represented with their PinInfo objects.
      3. One "master part" node that connects to all the inernal parts, represented with the
         partsList vector.
      4. One "master output" node that all output nodes connect to, represented with the
         outputPinsInfo array.
      5. One "master input" node that connects to all input nodes, represented with the
         inputPinsInfo array.
      Edges are not created between inetrnal nodes and clocked part inputs.
    */
    private Graph createConnectionsGraph() {
        Graph graph = new Graph();
        Iterator connectionIter = connections.iterator();

        while (connectionIter.hasNext()) {
            Connection connection = (Connection)connectionIter.next();
            Integer part = new Integer(connection.getPartNumber());
            int gatePinNumber = connection.getGatePinNumber();

            switch (connection.getType()) {
                case Connection.TO_INTERNAL:
                    if (isLegalFromPartEdge(connection, part))
                        graph.addEdge(part, getPinInfo(INTERNAL_PIN_TYPE, gatePinNumber));
                    break;

                case Connection.FROM_INTERNAL:
                    if (isLegalToPartEdge(connection, part))
                        graph.addEdge(getPinInfo(INTERNAL_PIN_TYPE, gatePinNumber), part);
                    break;

                case Connection.TO_OUTPUT:
                    if (isLegalFromPartEdge(connection, part))
                        graph.addEdge(part, getPinInfo(OUTPUT_PIN_TYPE, gatePinNumber));
                    break;

                case Connection.FROM_INPUT:
                    if (isLegalToPartEdge(connection, part))
                        graph.addEdge(getPinInfo(INPUT_PIN_TYPE, gatePinNumber), part);
                    break;

                case Connection.FROM_TRUE:
                    if (isLegalToPartEdge(connection, part))
                        graph.addEdge(TRUE_NODE_INFO, part);
                    break;

                case Connection.FROM_FALSE:
                    if (isLegalToPartEdge(connection, part))
                        graph.addEdge(FALSE_NODE_INFO, part);
                    break;

                case Connection.FROM_CLOCK:
                    if (isLegalToPartEdge(connection, part))
                        graph.addEdge(CLOCK_NODE_INFO, part);
                    break;
            }
        }

        // connect the "master part" node to all the parts.
        for (int i = 0; i < partsList.size(); i++)
            graph.addEdge(partsList, new Integer(i));

        // connect all output pins to the "master output" node
        for (int i = 0; i < outputPinsInfo.length; i++)
            graph.addEdge(outputPinsInfo[i], outputPinsInfo);

        // connect the "master input" node to all input pins
        for (int i = 0; i < inputPinsInfo.length; i++)
            graph.addEdge(inputPinsInfo, inputPinsInfo[i]);

        return graph;
    }

    // Returns true if an edge should be connected to the given part.
    // a connection to a clocked input is not considered as a connection
    // in the graph.
    private boolean isLegalToPartEdge(Connection connection, Integer part) {
        GateClass partGateClass = (GateClass)partsList.elementAt(part.intValue());
        int partPinNumber = partGateClass.getPinNumber(connection.getPartPinName());
        return !partGateClass.isInputClocked[partPinNumber];
    }

    // Returns true if an edge should be connected from the given part.
    // a connection from a clocked output is not considered as a connection
    // in the graph.
    private boolean isLegalFromPartEdge(Connection connection, Integer part) {
        GateClass partGateClass = (GateClass)partsList.elementAt(part.intValue());
        int partPinNumber = partGateClass.getPinNumber(connection.getPartPinName());
        return !partGateClass.isOutputClocked[partPinNumber];
    }

    /**
     * Returns the PinInfo according to the given pin type and number.
     * If doesn't exist, return null.
     */
    public PinInfo getPinInfo(byte type, int number) {
        PinInfo result = null;

        if (type == INTERNAL_PIN_TYPE) {
            if (number < internalPinsInfo.size())
                return (PinInfo)internalPinsInfo.elementAt(number);
        }
        else
            result = super.getPinInfo(type, number);

        return result;
    }

    /**
     * Creates and returns a new instance of CompositeGate.
     */
    public Gate newInstance() throws InstantiationException {
        Node[] inputNodes = new Node[inputPinsInfo.length];
        Node[] outputNodes = new Node[outputPinsInfo.length];
        Node[] internalNodes = new Node[internalPinsInfo.size()];

        CompositeGate result = new CompositeGate();

        // Create instances (Gates) from all parts in the parts list (which are GateClasses).
        // The created array is sorted in the original parts order
        Gate[] parts = new Gate[partsList.size()];
        for (int i = 0; i < parts.length; i++) {
            parts[i] = ((GateClass)partsList.elementAt(i)).newInstance();
            if (parts[i] instanceof BuiltInGateWithGUI) // save the parent of gates with gui
                ((BuiltInGateWithGUI)parts[i]).setParent(result);
        }

        // Creates another parts array in which the parts are sorted in topological order.
        Gate[] sortedParts = new Gate[parts.length];
        for (int i = 0; i < parts.length; i++)
            sortedParts[i] = parts[partsOrder[i]];

        for (int i = 0; i < inputNodes.length; i++)
            inputNodes[i] = new Node();

        for (int i = 0; i < outputNodes.length; i++)
            outputNodes[i] = new Node();

        // Add a DirtyGateAdapter as a listener to all the non-clocked inputs,
        // so the gate will become dirty when one of its non-clocked input changes.
        Node adapter = new DirtyGateAdapter(result);
        for (int i = 0; i < isInputClocked.length; i++)
            if (!isInputClocked[i])
                inputNodes[i].addListener(adapter);

        // First scan: creates internal Nodes (or SubNodes) and their connections to
        // their source part nodes. Also creates the connections between gate's
        // input or putput nodes and part's input nodes and between part's output nodes and gate's
        // output nodes.
        ConnectionSet internalConnections = new ConnectionSet();
        Node partNode, source, target;
        byte[] gateSubBus, partSubBus;
        Iterator connectionIter = connections.iterator();
        while (connectionIter.hasNext()) {
            Connection connection = (Connection)connectionIter.next();
            gateSubBus = connection.getGateSubBus();
            partSubBus = connection.getPartSubBus();
            partNode = parts[connection.getPartNumber()].getNode(connection.getPartPinName());

            switch (connection.getType()) {
                case Connection.FROM_INPUT:
                    connectGateToPart(inputNodes[connection.getGatePinNumber()], gateSubBus,
                                      partNode, partSubBus);
                    break;

                case Connection.TO_OUTPUT:
                    connectGateToPart(partNode, partSubBus,
                                      outputNodes[connection.getGatePinNumber()], gateSubBus);
                    break;

                case Connection.TO_INTERNAL:
                    target = null;
                    if (partSubBus == null)
                        target = new Node();
                    else
                        target = new SubNode(partSubBus[0], partSubBus[1]);
                    partNode.addListener(target);
                    internalNodes[connection.getGatePinNumber()] = target;
                    break;

                case Connection.FROM_INTERNAL:
                case Connection.FROM_TRUE:
                case Connection.FROM_FALSE:
                case Connection.FROM_CLOCK:
                    internalConnections.add(connection);
                    break;
            }
        }

        // Second scan: Creates the connections between internal nodes or true node
        // or false node to a part's input nodes.
        connectionIter = internalConnections.iterator();
        boolean isClockParticipating = false;
        while (connectionIter.hasNext()) {
            Connection connection = (Connection)connectionIter.next();
            partNode = parts[connection.getPartNumber()].getNode(connection.getPartPinName());
            partSubBus = connection.getPartSubBus();
            gateSubBus = connection.getGateSubBus();
            source = null;

            // find source node
            switch (connection.getType()) {
                case Connection.FROM_INTERNAL:
                    source = internalNodes[connection.getGatePinNumber()];
                    if (partSubBus == null)
                        source.addListener(partNode);
                    else {
                        Node node = new SubBusListeningAdapter(partNode, partSubBus[0], partSubBus[1]);
                        source.addListener(node);
                    }
                    break;
                case Connection.FROM_TRUE:
                    SubNode subNode = new SubNode(gateSubBus[0], gateSubBus[1]);
                    subNode.set(Gate.TRUE_NODE.get());

                    if (partSubBus == null)
                        partNode.set(subNode.get());
                    else {
                        Node node = new SubBusListeningAdapter(partNode, partSubBus[0], partSubBus[1]);
                        node.set(subNode.get());
                    }

                    break;
                case Connection.FROM_FALSE:
                    subNode = new SubNode(gateSubBus[0], gateSubBus[1]);
                    subNode.set(Gate.FALSE_NODE.get());

                    if (partSubBus == null)
                        partNode.set(subNode.get());
                    else {
                        Node node = new SubBusListeningAdapter(partNode, partSubBus[0], partSubBus[1]);
                        node.set(subNode.get());
                    }

                    break;
                case Connection.FROM_CLOCK:
                    partNode.set(Gate.CLOCK_NODE.get());
                    Gate.CLOCK_NODE.addListener(partNode);
                    isClockParticipating = true;
                    break;
            }
        }

        // If the clock special node appears in this gate, Add a dirty gate adapter
        // such that changes in clock state will cause this gate to recompute.
        if (isClockParticipating)
            Gate.CLOCK_NODE.addListener(new DirtyGateAdapter(result));

        result.init(inputNodes, outputNodes, internalNodes, sortedParts, this);

        return result;
    }

    // Connects the given source node to the given target node.
    private void connectGateToPart(Node sourceNode, byte[] sourceSubBus,
                                   Node targetNode, byte[] targetSubBus) {
        Node source = sourceNode;
        Node target = targetNode;
        if (targetSubBus != null)
            target = new SubBusListeningAdapter(target, targetSubBus[0], targetSubBus[1]);

        if (sourceSubBus == null)
            source.addListener(target);
        else {
            Node subNode = new SubNode(sourceSubBus[0], sourceSubBus[1]);
            source.addListener(subNode);
            subNode.addListener(target);
        }
    }
}
