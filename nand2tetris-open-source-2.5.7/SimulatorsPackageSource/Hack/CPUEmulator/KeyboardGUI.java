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
import javax.swing.*;
import Hack.ComputerParts.*;

/**
 * An interface for the GUI of a computer Keyboard.
 * This GUI displays the currently pressed key.
 * It should disable direct input into the GUI.
 */
public interface KeyboardGUI extends ComputerPartGUI {

    /**
     * Displayes the given key name.
     */
    public void setKey(String keyName);

    /**
     * Clears the key display.
     */
    public void clearKey();

    /**
     * Returns the GUI component that handles Key events.
     */
    public JComponent getKeyEventHandler();
}
