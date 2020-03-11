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

import Hack.VirtualMachine.*;

/**
 * An HVMInstruction for the use of the VMEmulator.
 */
public class VMEmulatorInstruction extends HVMInstruction {

    // The index of the instruction in its containing function.
    private short indexInFunction;

    /**
     * Constructs a new instruction with two arguments and the index in function.
     */
    public VMEmulatorInstruction(byte opCode, short arg0, short arg1, short indexInFunction) {
        super(opCode, arg0, arg1);
        this.indexInFunction = indexInFunction;
    }

    /**
     * Constructs a new instruction with one argument and the index in function.
     */
    public VMEmulatorInstruction(byte opCode, short arg0, short indexInFunction) {
        super(opCode, arg0);
        this.indexInFunction = indexInFunction;
    }

    /**
     * Constructs a new instruction with no arguments and the index in function.
     */
    public VMEmulatorInstruction(byte opCode, short indexInFunction) {
        super(opCode);
        this.indexInFunction = indexInFunction;
    }

    /**
     * Returns the index of this instruction in its containing function.
     * A negative value represents no index.
     */
    public short getIndexInFunction() {
        return indexInFunction;
    }
}
