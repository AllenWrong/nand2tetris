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

import Hack.Controller.*;
import Hack.ComputerParts.*;
import java.awt.event.*;

/**
 * An interface for a GUI of the CPU emulator.
 */
public interface CPUEmulatorGUI extends HackSimulatorGUI {

    /**
     * Returns the bus GUI component.
     */
    public BusGUI getBus();

    /**
     * Returns the screen GUI component.
     */
    public ScreenGUI getScreen();

    /**
     * Returns the keyboard GUI component.
     */
    public KeyboardGUI getKeyboard();

    /**
     * Returns the RAM GUI component.
     */
    public PointedMemoryGUI getRAM();

    /**
     * Returns the ROM GUI component.
     */
    public ROMGUI getROM();

    /**
     * Returns the A register GUI component.
     */
    public RegisterGUI getA();

    /**
     * Returns the D register GUI component.
     */
    public RegisterGUI getD();

    /**
     * Returns the PC register GUI component.
     */
    public RegisterGUI getPC();

    /**
     * Returns the ALU GUI component.
     */
    public ALUGUI getALU();

    /**
     * Registers the given listener to listen to key events.
     */
    public void addKeyListener(KeyListener listener);

    /**
     * Sets the focus on the CPUEmulator's frame
     */
    public void requestFocus();
}
