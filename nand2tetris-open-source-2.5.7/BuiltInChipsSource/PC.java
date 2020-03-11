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
import HackGUI.*;
import Hack.ComputerParts.*;
import java.awt.*;

/**
/* A 16-bit counter with load and reset controls.
/*      if      (reset[t]=1) out[t+1] = 0
/*      else if (load[t]=1)  out[t+1] = in[t]
/*      else if (inc[t]=1)   out[t+1] = out[t] + 1  (integer addition)
/*      else                 out[t+1] = out[t]
 */
public class PC extends BuiltInGateWithGUI implements ComputerPartEventListener {

    // The 16 bit value
    private short value;

    // The gui
    private RegisterComponent gui;

    /**
     * Constructs a new PC.
     */
    public PC() {
        if (GatesManager.getInstance().isChipsGUIEnabled()) {
            gui = new RegisterComponent();
            gui.setName("PC:");
            gui.reset();
            gui.setLocation(355,442);
            gui.addListener(this);
            gui.addErrorListener(this);
        }
    }

    protected void clockUp() {
        short in = inputPins[0].get(); // 16 bit input
        short load = inputPins[1].get(); // load bit
        short inc = inputPins[2].get(); // incerement bit
        short reset = inputPins[3].get(); // reset bit
        if (reset == 1)
            value = 0;
        else if (load == 1)
            value = in;
        else if (inc == 1)
            value++;

        if (gui != null)
            gui.setValueAt(0, value);
    }

    protected void clockDown() {
        outputPins[0].set(value);
    }

    public Component getGUIComponent() {
        return gui;
    }

    public void valueChanged(ComputerPartEvent event) {
        short newValue = event.getValue();
        clearErrorListeners();
        if (newValue < 0 || newValue > 32767) {
            notifyErrorListeners("Illegal address value");
            if (gui != null)
                gui.setValueAt(0, value);
        }
        else
            updateValue(newValue);
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
            throw new GateException("PC has no index. Use PC[]");
    }

    public void setValueAt(int index, short value) throws GateException {
        checkIndex(index);
        updateValue(value);
    }
}
