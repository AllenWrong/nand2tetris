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
 * A register which receives a PointedMemory object.
 * Whenever the register's value changes, the PointedMemory's address pointer
 * changes accordingly.
 */
public class PointerAddressRegisterAdapter extends Register {

    // The pointed memory.
    protected PointedMemory memory;

    // If true, changes in the register's value will set the pointer.
    protected boolean updatePointer;

    /**
     * Constructs a new PointerAddressRegisterAdapter with the given optional gui and
     * the pointedMemory object.
     */
    public PointerAddressRegisterAdapter(RegisterGUI gui, PointedMemory memory) {
        super(gui, (short)(-32768), (short)32767);
        this.memory = memory;
        updatePointer = true;
    }

    public void setValueAt(int index, short value, boolean quiet) {
        super.setValueAt(0, value, quiet);
        if (updatePointer)
            memory.setPointerAddress(value);
    }

    /**
     * Called when the value of a register is changed.
     */
    public void valueChanged(ComputerPartEvent event) {
        super.valueChanged(event);
        if (updatePointer)
            memory.setPointerAddress(event.getValue());
    }

    public void reset() {
        super.reset();

        if (updatePointer)
            memory.setPointerAddress(0);
    }

    public void refreshGUI() {
        quietUpdateGUI(0, value);
        if (updatePointer)
            memory.setPointerAddress(value);
    }

    /**
     * If updatePointer is true, changes in the register's value will set the ram
     * pointer accordingly. Otherwise, changes in the value will not affect the
     * ram's pointer.
     */
    public void setUpdatePointer(boolean updatePointer) {
        this.updatePointer = updatePointer;
        if (updatePointer)
            memory.setPointerAddress(value);
    }
}
