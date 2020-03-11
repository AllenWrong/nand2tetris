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

package Hack.CPUEmulator;

import java.awt.event.*;
import Hack.Utilities.*;
import Hack.CPUEmulator.*;
import Hack.ComputerParts.*;

/**
 * A computer keyboard.
 */
public class Keyboard extends ComputerPart implements KeyListener {

    // The ram (the keyboard address is changed according to the current key)
    private RAM ram;

    // The gui of the keyboard
    private KeyboardGUI gui;

    /**
     * Constructs a new keyboard with the given RAM and keyboard GUI.
     */
    public Keyboard(RAM ram, KeyboardGUI gui) {
        super(gui != null);

        this.ram = ram;
        this.gui = gui;

        if (hasGUI)
            gui.getKeyEventHandler().addKeyListener(this);
    }

    /**
     * Returns the GUI of the computer part.
     */
    public ComputerPartGUI getGUI() {
        return gui;
    }

    /**
     * Activated when a key is pressed.
     */
    public void keyPressed(KeyEvent e) {
        short key = Definitions.getInstance().getKeyCode(e);
        if (key > 0) {
            ram.setValueAt(Definitions.KEYBOARD_ADDRESS, key, true);
            if (hasGUI)
                gui.setKey(Definitions.getInstance().getKeyName(e));
        }
    }

    /**
     * Activated when a key is released.
     */
    public void keyReleased(KeyEvent e) {
        ram.setValueAt(Definitions.KEYBOARD_ADDRESS, (short)0, true);
        gui.clearKey();
    }

    /**
     * Activated when a key is typed.
     */
    public void keyTyped(KeyEvent e) {}

    public void refreshGUI() {}

    public void requestFocus() {
        if (hasGUI)
            gui.getKeyEventHandler().requestFocus();
    }
}
