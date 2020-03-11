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

import Hack.ComputerParts.*;

/**
 * An interface for the GUI of the ALU.
 * An ALU has two inputs (index 0,1) and an output (index 2).
 * The two inputs and output should be read-only.
 */
public interface ALUGUI extends ValueComputerPartGUI {

    /**
     * Sets the command of the ALU with the given command.
     */
    public void setCommand(String command);

    /**
     * Flashes the ALU body.
     */
    public void bodyFlash();

    /**
     * Hides the ALU's body flash.
     */
    public void hideBodyFlash();

    /**
     * Flashes the ALU command.
     */
    public void commandFlash();

    /**
     * Hides the ALU's command flash.
     */
    public void hideCommandFlash();
}
