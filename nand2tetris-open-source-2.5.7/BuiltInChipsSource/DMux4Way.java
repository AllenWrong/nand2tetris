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

package builtInChips;

import Hack.Gates.BuiltInGate;

/**
 * 1 bit 4-way demultiplexor.
 * The 2-bit sel choose to which output to channel the input (0->a .. 3->d).
 * The other outputs are set to 0.
 */
public class DMux4Way extends BuiltInGate {

    protected void reCompute() {
        short in = inputPins[0].get();
        short sel = inputPins[1].get();
        outputPins[0].set((short)(sel == 0 ? in : 0));
        outputPins[1].set((short)(sel == 1 ? in : 0));
        outputPins[2].set((short)(sel == 2 ? in : 0));
        outputPins[3].set((short)(sel == 3 ? in : 0));
    }
}
