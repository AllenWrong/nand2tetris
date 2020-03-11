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
 * Represents an internal gate connection between two pins.
 */
public class Connection {

    /**
     * A connection from one of the gate's inputs to a part's input.
     */
    public static final byte FROM_INPUT = 1;

    /**
     * A connection from a part's output to one of the gate's internal nodes.
     */
    public static final byte TO_INTERNAL = 2;

    /**
     * A connection from one of the gate's internal node to a part's input.
     */
    public static final byte FROM_INTERNAL = 3;

    /**
     * A connection from a part's output to one of the gate's outputs.
     */
    public static final byte TO_OUTPUT = 5;

    /**
     * A connection from the "true" special node to a part's input.
     */
    public static final byte FROM_TRUE = 6;

    /**
     * A connection from the "false" special node to a part's input.
     */
    public static final byte FROM_FALSE = 7;

    /**
     * A connection from the "clock" special node to a part's input.
     */
    public static final byte FROM_CLOCK = 8;


    // The type of connection (out of the above constants)
    private byte type;

    // The number of the gate's pin (input, internal or output, according to the type)
    private int gatePinNumber;

    // The number of the gate's part
    private int partNumber;

    // The name of the part's pin
    private String partPinName;

    // The bit indice of the parts's sub node (index 0 is low bit and index 1 is high bit)
    private byte[] partSubBus;

    // The bit indice of the gate's sub node (index 0 is low bit and index 1 is high bit)
    private byte[] gateSubBus;

    /**
     * Constructs a connection according to the given type and pin information.
     * The sub-busses of the gate & part are optional.
     */
    public Connection(byte type, int gatePinNumber, int partNumber, String partPinName,
                      byte[] gateSubBus, byte[] partSubBus) {
        this.type = type;
        this.gatePinNumber = gatePinNumber;
        this.partNumber = partNumber;
        this.partPinName = partPinName;
        this.gateSubBus = gateSubBus;
        this.partSubBus = partSubBus;
    }

    /**
     * Returns the type of this connection
     */
    public byte getType() {
        return type;
    }

    /**
     * Returns the gate's pin number.
     */
    public int getGatePinNumber() {
        return gatePinNumber;
    }

    /**
     * Returns the part's number.
     */
    public int getPartNumber() {
        return partNumber;
    }

    /**
     * Returns the part's pin name.
     */
    public String getPartPinName() {
        return partPinName;
    }

    /**
     * Returns the gate's sub-bus indice (may be null).
     */
    public byte[] getGateSubBus() {
        return gateSubBus;
    }

    /**
     * Returns the part's sub-bus indice (may be null).
     */
    public byte[] getPartSubBus() {
        return partSubBus;
    }

}
