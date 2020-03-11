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

package SimulatorsGUI;

import HackGUI.*;
import Hack.CPUEmulator.*;
import Hack.ComputerParts.*;
import java.awt.*;
import java.io.*;

/**
 * This class represents the gui of the CPUEmulator.
 */
public class CPUEmulatorComponent extends HackSimulatorComponent implements CPUEmulatorGUI  {

    // The dimension of this window.
    private static final int EMULATOR_WIDTH = 1018;
    private static final int EMULATOR_HEIGHT = 611;

    // Creating the RegisterComponents a, d and pc.
    private RegisterComponent a;
    private RegisterComponent d;
    private RegisterComponent pc;

    // The screen of the CPUEmulator.
    private ScreenComponent screen;

    // The keyboard of the CPUEmulator.
    private KeyboardComponent keyboard;

    // The memory of the CPUEmulator.
    private PointedMemoryComponent ram;

    // The ROM of the CPUEmulator.
    private ROMComponent rom;

    // The ALU of the CPUEmulator.
    private ALUComponent alu;

    // The bus of the CPUEmulator.
    private BusComponent bus;


    /**
     * Constructs a new CPUEmulatorComponent.
     */
    public CPUEmulatorComponent() {
        screen = new ScreenComponent();
        keyboard = new KeyboardComponent();
        ram = new PointedMemoryComponent();
        ram.setName("RAM");
        rom = new ROMComponent();
        rom.setName("ROM");
        alu = new ALUComponent();
        a = new RegisterComponent();
        d = new RegisterComponent();
        pc = new RegisterComponent();
        setRegistersNames();
        bus = new BusComponent();
        jbInit();

        // Sets the top level location of RAM and ROM.
        ram.setTopLevelLocation(this);
        rom.setTopLevelLocation(this);
    }

    public void setWorkingDir(File file) {
        rom.setWorkingDir(file);
    }

    public void loadProgram() {
        rom.loadProgram();
    }

    // Sets the names of the registers.
    private void setRegistersNames() {
        a.setName("A");
        d.setName("D");
        pc.setName("PC");
    }

    public Point getAdditionalDisplayLocation() {
        return new Point(476, 25);
    }

    /**
     * Returns the alu GUI component.
     */
    public ALUGUI getALU() {
        return alu;
    }

    /**
     * Returns the bus GUI component.
     */
    public BusGUI getBus() {
        return bus;
    }

    /**
     * Returns the screen GUI component.
     */
    public ScreenGUI getScreen() {
        return screen;
    }

    /**
     * Returns the keyboard GUI component.
     */
    public KeyboardGUI getKeyboard() {
        return keyboard;
    }

    /**
     * Returns the RAM GUI component.
     */
    public PointedMemoryGUI getRAM() {
        return ram;
    }

    /**
     * Returns the ROM GUI component.
     */
    public ROMGUI getROM() {
        return rom;
    }

    /**
     * Returns the screen GUI component.
     */
    public RegisterGUI getA() {
        return a;
    }

    /**
     * Returns the screen GUI component.
     */
    public RegisterGUI getD() {
        return d;
    }

    /**
     * Returns the screen GUI component.
     */
    public RegisterGUI getPC() {
        return pc;
    }

    // Initialization of this component.
    private void jbInit() {
        this.setLayout(null);
        pc.setBounds(new Rectangle(35, 527, pc.getWidth(), pc.getHeight()));
        a.setBounds(new Rectangle(278, 527, a.getWidth(), a.getHeight()));
        d.setBounds(new Rectangle(646, 351, d.getWidth(), d.getHeight()));
        screen.setToolTipText("Screen");
        screen.setBounds(new Rectangle(476, 25, screen.getWidth(), screen.getHeight()));
        keyboard.setBounds(new Rectangle(476, 285, keyboard.getWidth(), keyboard.getHeight()));
        ram.setVisibleRows(29);
        ram.setBounds(new Rectangle(264, 25, ram.getWidth(), ram.getHeight()));
        rom.setVisibleRows(29);
        rom.setBounds(new Rectangle(20, 25, rom.getWidth(), rom.getHeight()));
        alu.setBounds(new Rectangle(551, 414, alu.getWidth(), alu.getHeight()));

        bus.setBounds(new Rectangle(0, 0, EMULATOR_WIDTH , EMULATOR_HEIGHT));

        this.add(bus, null);
        this.add(ram, null);
        this.add(screen, null);
        this.add(rom, null);
        this.add(a, null);
        this.add(pc, null);
        this.add(keyboard, null);
        this.add(alu, null);
        this.add(d, null);
        setSize(EMULATOR_WIDTH, EMULATOR_HEIGHT);
    }
}
