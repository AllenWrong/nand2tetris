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

import java.awt.*;
import Hack.Gates.*;
import Hack.Utilities.*;
import SimulatorsGUI.*;
import Hack.Assembler.*;

/**
/* The ALU.  d and m are two 16-bit inputs.  The other inputs are control bits.
/* Computes the output according to the following pseudocode:
/*        if (zd=1) d = 0            //  16-bit constant
/*        if (nd=1) d = !d           //  bitwise negation
/*        if (zm=1) m = 0            //  16-bit constant
/*        if (nm=1) m = !m           //  bitwise negation
/*        if (f=1) out = d + m       //  integer 2s complement addition
/*        else     out = d & m       //  bitwise "and"
/*        if (no=1) out = !out       //  bitwise negation
/*        if (out=0) zr=1 else zr=0  //  comparision is 16-bit equality
/*        if (out<0) ng=1 else ng=0  //  comparision in 2s-complement
 */
public class ALU extends BuiltInGateWithGUI {

    // The gui;
    private ALUComponent gui;

    // An assembler transltor
    protected HackAssemblerTranslator assemblerTranslator;

    /**
     * Constructs a new ALU.
     */
    public ALU() {
        if (GatesManager.getInstance().isChipsGUIEnabled()) {
            gui = new ALUComponent();
            gui.setLocation(80,468);
            gui.reset();
        }

        assemblerTranslator = HackAssemblerTranslator.getInstance();
    }

    public Component getGUIComponent() {
        return gui;
    }

    public void reCompute() {
        short x = inputPins[0].get(); // 16 bit d input
        short y = inputPins[1].get(); // 16 bit m/a input
        short zx = inputPins[2].get(); // 1 bit flag
        short nx = inputPins[3].get(); // 1 bit flag
        short zy = inputPins[4].get(); // 1 bit flag
        short ny = inputPins[5].get(); // 1 bit flag
        short f = inputPins[6].get(); // 1 bit flag
        short no = inputPins[7].get(); // 1 bit flag

        if (gui != null) {
            gui.setValueAt(0, x);
            gui.setValueAt(1, y);
        }

        int function = (zx << 11) | (nx << 10) | (zy << 9) | (ny << 8) | (f << 7) | (no << 6);

        try {
            String command = assemblerTranslator.getExpByCode((short)(function | 0xf000));
            if (command.equals(""))
                command = assemblerTranslator.getExpByCode((short)(function | 0xe000));
            if (gui != null)
                gui.setCommand(command);
        } catch (AssemblerException ae) {}

        short result = Definitions.computeALU(x, y, zx == 1, nx == 1, zy == 1, ny == 1,
                                              f == 1, no == 1);

        outputPins[0].set(result); // out
        outputPins[1].set((short)(result == 0 ? 1 : 0)); // zr
        outputPins[2].set((short)(result < 0 ? 1 : 0));  // ng

        if (gui != null)
            gui.setValueAt(2, result);
    }

    public short getValueAt(int index) throws GateException {
        throw new GateException("ALU cannot be used as a variable");
    }

    public void setValueAt(int index, short value) throws GateException {
        throw new GateException("ALU cannot be used as a variable");
    }
}
