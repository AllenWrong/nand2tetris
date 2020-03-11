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

package builtInVMCode;

import Hack.VMEmulator.BuiltInVMClass;
import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A built-in implementation for the Sys class of the Jack OS.
 */

public class Memory extends JackOSClass {

    public static void init()
			throws TerminateVMProgramThrowable {
		writeMemory(HEAP_START_ADDRESS,
					(HEAP_END_ADDRESS+1)-(HEAP_START_ADDRESS+2));
		writeMemory(HEAP_START_ADDRESS+1, HEAP_END_ADDRESS+1);
    }

	public static short peek(short address)
			throws TerminateVMProgramThrowable {
		return readMemory(address);
	}

    public static void poke(short address, short value)
			throws TerminateVMProgramThrowable {
		writeMemory(address, value);
    }

    public static short alloc(short size)
			throws TerminateVMProgramThrowable {
        if (size < 1) {
            callFunction("Sys.error", MEMORY_ALLOC_NONPOSITIVE_SIZE);
        }
		short segmentAddress = HEAP_START_ADDRESS;
		short segmentCapacity = 0;
        while (segmentAddress <= HEAP_END_ADDRESS &&
			   (segmentCapacity=readMemory(segmentAddress)) < size) {
			segmentAddress = readMemory(segmentAddress+1);
        }
        if (segmentAddress > HEAP_END_ADDRESS) {
            callFunction("Sys.error", MEMORY_ALLOC_HEAP_OVERFLOW);
        }
		if (segmentCapacity > size+2) {
			writeMemory(segmentAddress+size+2, segmentCapacity-size-2);
			writeMemory(segmentAddress+size+3, readMemory(segmentAddress+1));
			writeMemory(segmentAddress+1, segmentAddress+size+2);
		}
		writeMemory(segmentAddress, 0);
		return (short)(segmentAddress+2);
    }

    public static void deAlloc(short arr)
			throws TerminateVMProgramThrowable {
		short segmentAddress = (short)(arr-2);
		short segmentCapacity = readMemory(segmentAddress);
		short nextSegmentAddress = readMemory(segmentAddress+1);
		short nextCapacity;
        if (nextSegmentAddress > HEAP_END_ADDRESS ||
			(nextCapacity=readMemory(nextSegmentAddress)) == 0) {
			writeMemory(segmentAddress, nextSegmentAddress-segmentAddress-2);
        } else {
			writeMemory(segmentAddress,
						nextSegmentAddress-segmentAddress+nextCapacity);
			writeMemory(segmentAddress+1,
						readMemory(nextSegmentAddress+1));
        }
    }

}
