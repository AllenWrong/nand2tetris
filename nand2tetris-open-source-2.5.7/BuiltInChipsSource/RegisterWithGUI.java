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

import Hack.Gates.*;
import Hack.ComputerParts.*;
import HackGUI.*;
import java.awt.*;

/**
 * A 16 bit memory register.
 * If load[t]=1 then out[t+1] = in[t]
 * else out does not change
 */
public class RegisterWithGUI extends BuiltInGateWithGUI implements ComputerPartEventListener {

    // The 16 bit value
    protected short value;

    // The gui
    protected RegisterComponent gui;

    /**
     * Constructs a new RegisterWithGUI.
     */
    public RegisterWithGUI() {
        if (GatesManager.getInstance().isChipsGUIEnabled()) {
            gui = new RegisterComponent();
            gui.setName("Reg:");
            gui.setLocation(180, 10);
            gui.reset();
            gui.addListener(this);
            gui.addErrorListener(this);
        }
    }

    protected void clockUp() {
        short in = inputPins[0].get(); // 16 bit input
        short load = inputPins[1].get(); // load bit
        if (load == 1) {
            value = in;
            if (gui != null)
                gui.setValueAt(0, value);
        }
    }

    protected void clockDown() {
        outputPins[0].set(value);
    }

    public Component getGUIComponent() {
        return gui;
    }

    public void valueChanged(ComputerPartEvent event) {
        clearErrorListeners();
        updateValue(event.getValue());
    }

    // updates the given value
    private void updateValue(short newValue) {
        value = newValue;
        outputPins[0].set(newValue);
        evalParent();
        if (gui != null)
            gui.setValueAt(0, newValue);
    }

    public void guiGainedFocus() {
    }

    public short getValueAt(int index) throws GateException {
        checkIndex(index);
        return value;
    }

    // checks the given index. If illegal throws GateException.
    private void checkIndex(int index) throws GateException {
        if (index != 0)
            throw new GateException("Register has no index. Use Register[]");
    }

    public void setValueAt(int index, short value) throws GateException {
        checkIndex(index);
        updateValue(value);
    }
}
