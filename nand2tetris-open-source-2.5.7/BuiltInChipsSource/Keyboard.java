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
import java.awt.event.*;
import Hack.Gates.*;
import Hack.Utilities.*;
import SimulatorsGUI.*;

/**
/* A Keyboard, implemented as a 16 bit register that stores the currently pressed key code.
 */
public class Keyboard extends BuiltInGateWithGUI implements KeyListener {

    // The currently pressed key.
    private short key;

    // The gui.
    private KeyboardComponent gui;

    /**
     * Constructs a new Keyboard.
     */
    public Keyboard() {
        if (GatesManager.getInstance().isChipsGUIEnabled()) {
            gui = new KeyboardComponent();
            gui.setLocation(4,264);
            gui.getKeyEventHandler().addKeyListener(this);
            gui.reset();
        }
    }

    public Component getGUIComponent() {
        return gui;
    }

    /**
     * Activated when a key is pressed.
     */
    public void keyPressed(KeyEvent e) {
        key = Definitions.getInstance().getKeyCode(e);
        if (key > 0) {
            outputPins[0].set(key);
            evalParent();
            if (gui != null)
                gui.setKey(Definitions.getInstance().getKeyName(e));
        }
    }

    /**
     * Activated when a key is released.
     */
    public void keyReleased(KeyEvent e) {
        key = 0;
        outputPins[0].set(key);
        evalParent();
        if (gui != null)
            gui.clearKey();
    }

    /**
     * Activated when a key is typed.
     */
    public void keyTyped(KeyEvent e) {}

    public short getValueAt(int index) throws GateException {
        checkIndex(index);
        return key;
    }

    // checks the given index. If illegal throws GateException.
    private void checkIndex(int index) throws GateException {
        if (index != 0)
            throw new GateException("Keyboard has no index. Use ARegister[]");
    }

    public void setValueAt(int index, short value) throws GateException {
        throw new GateException("Keyboard is read only");
    }
}
