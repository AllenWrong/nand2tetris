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

/**
/* A 512X256 screen, implemented with 8K registers, each register represents 16 pixels.
 */
public class Screen extends BuiltInGateWithGUI {

    // The gui
    private ScreenComponent gui;

    // The memory array
    private short[] values;

    /**
     * Constructs a new Screen.
     */
    public Screen() {
        values = new short[Definitions.SCREEN_SIZE_IN_WORDS];
        if (GatesManager.getInstance().isChipsGUIEnabled()) {
            gui = new ScreenComponent();
            gui.setLocation(4,2);
        }
    }

    protected void clockUp() {
        short in = inputPins[0].get(); // 16 bit input
        short load = inputPins[1].get(); // load bit
        short address = inputPins[2].get(); // 13 bit address
        if (load == 1) {
            values[address] = in;
            if (gui != null)
                gui.setValueAt(address, in);
        }
    }

    protected void reCompute() {
        short address = inputPins[2].get(); // 13 bit address
        outputPins[0].set(values[address]);
    }

    protected void clockDown() {
        reCompute();
    }

    public Component getGUIComponent() {
        return gui;
    }

    // updates the given value
    private void updateValue(int address, short value) {
        values[address] = value;
        if (gui != null)
            gui.setValueAt(address, value);
        reCompute();
        evalParent();
    }

    public short getValueAt(int index) throws GateException {
        checkIndex(index);
        return values[index];
    }

    // checks the given index. If illegal throws GateException.
    private void checkIndex(int index) throws GateException {
        if (index < 0 || index >= values.length)
            throw new GateException("Illegal index");
    }

    public void setValueAt(int index, short value) throws GateException {
        checkIndex(index);
        updateValue(index, value);
    }
}
