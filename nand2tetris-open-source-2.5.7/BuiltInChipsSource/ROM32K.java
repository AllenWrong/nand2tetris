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
import Hack.ComputerParts.*;
import Hack.Events.*;
import Hack.Utilities.*;
import SimulatorsGUI.*;
import Hack.CPUEmulator.*;
import Hack.Assembler.*;

/**
/* A Read only memory of 32K registers, each 16 bit-wide.  The output is the value
/* stored at the memory location specified by the 15-bit address.
 */
public class ROM32K extends BuiltInGateWithGUI
 implements ComputerPartEventListener, ProgramEventListener {

    // The gui
    private ROMComponent gui;

    // The memory array
    private short[] values;

    /**
     * Constructs a new ROM32K.
     */
    public ROM32K() {
        values = new short[Definitions.ROM_SIZE];

        if (GatesManager.getInstance().isChipsGUIEnabled()) {
            gui = new ROMComponent();
            gui.setContents(values);
            gui.setVisibleRows(7);
            gui.setLocation(326,295);
            gui.setName("ROM:");
            gui.reset();
            gui.addListener(this);
            gui.addProgramListener(this);
            gui.addErrorListener(this);
        }
    }

    protected void reCompute() {
        short address = inputPins[0].get(); // 15 bit address
        outputPins[0].set(values[address]);
        if (gui != null)
            gui.setPointer(address);
    }

    public Component getGUIComponent() {
        return gui;
    }

    /**
     * Loads the given file into the rom.
     */
    protected void loadProgram(String fileName) throws AssemblerException {
        short[] program = HackAssemblerTranslator.loadProgram(fileName,
                                                              Definitions.ROM_SIZE,
                                                              (short)0);

        if (gui != null) {
            if (fileName.endsWith(".hack"))
                gui.setNumericFormat(ROM.BINARY_FORMAT);
            else
                gui.setNumericFormat(ROM.ASM_FORMAT);
        }

        values = program;
        if (gui != null) {
            gui.setProgram(fileName);
            gui.setContents(values);
        }
        reCompute();
        evalParent();
    }

    /**
     * Called when the ROM's current program is changed.
     * The event contains the source object, and the new program's file name.
     */
    public void programChanged(ProgramEvent event) {
        clearErrorListeners();
        if (gui != null)
            gui.showMessage("Loading...");

        try {
            loadProgram(event.getProgramFileName());
        } catch (AssemblerException pe) {
            notifyErrorListeners(pe.getMessage());
        }

        if (gui != null)
            gui.hideMessage();
    }

    /**
     * Called when the contents of the memory are changed through the memory gui.
     */
    public void valueChanged(ComputerPartEvent event) {
        short newValue = event.getValue();
        int newAddress = event.getIndex();
        clearErrorListeners();
        try {
            HackAssemblerTranslator.getInstance().codeToText(newValue);
            updateValue(newAddress, newValue);
        } catch (AssemblerException ae) {
            notifyErrorListeners("Illegal instruction");
            if (gui != null)
                gui.setValueAt(newAddress, values[newAddress]);
        }
    }

    // updates the given value
    private void updateValue(int address, short value) {
        values[address] = value;
        if (gui != null)
            gui.setValueAt(address, value);
        reCompute();
        evalParent();
    }

    public void guiGainedFocus() {
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

    /**
     * Executes the given command, given in args[] style.
     * Subclasses may override this method to implement commands.
     */
    public void doCommand(String[] command) throws GateException {
        if (command[0].toUpperCase().equalsIgnoreCase("LOAD")) {
            if (command.length != 2)
                throw new GateException("Illegal number of arguments");

            if (gui != null)
                gui.showMessage("Loading...");

            String fileName = GatesManager.getInstance().getWorkingDir() + "/" + command[1];

            try {
                loadProgram(fileName);
            } catch (AssemblerException pe) {
                if (gui != null)
                    gui.hideMessage();
                throw new GateException(pe.getMessage());
            }

            if (gui != null)
                gui.hideMessage();
        }
        else
            throw new GateException("This chip doesn't support this command");
    }
}
