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
import Hack.VMEmulator.*;
import Hack.CPUEmulator.*;
import Hack.ComputerParts.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
 * This class represents the gui of the VMEmulator.
 */
public class VMEmulatorComponent extends HackSimulatorComponent implements VMEmulatorGUI {

    // The dimension of this window.
    private static final int WIDTH = 1018;
    private static final int HEIGHT = 611;

    // The keyboard of the VMEmulator component.
    private KeyboardComponent keyboard;

    // The screen of the VMEmulator component.
    private ScreenComponent screen;

    // The call stack of the VMEmulator component.
    private CallStackComponent callStack;

    // The program of the VMEmulator component.
    private ProgramComponent program;

    // The ram of the VMEmulator component.
    private LabeledMemoryComponent ram;

    // The stack of the VMEmulator component.
    private AbsolutePointedMemorySegmentComponent stack;

    // The memory segments of the VMEmulator component.
    private MemorySegmentsComponent segments;

    // The bus of the VMEmulator component.
    private BusComponent bus;

    // The calculator of this emulator.
    private StackCalculator calculator;

    // The working stack of the VMEmulator component.
    private TrimmedValuesOnlyAbsoluteMemorySegmentComponent workingStack;


    /**
     * Constructs a new VMEmulatorGUI.
     */
    public VMEmulatorComponent() {
        bus = new BusComponent();
        screen = new ScreenComponent();
        keyboard = new KeyboardComponent();
        ram = new LabeledMemoryComponent();
        ram.setName("RAM");
        callStack = new CallStackComponent();
        program = new ProgramComponent();
        segments = new MemorySegmentsComponent();
        workingStack = new TrimmedValuesOnlyAbsoluteMemorySegmentComponent();
        workingStack.setSegmentName("Stack");
        stack = new AbsolutePointedMemorySegmentComponent();
        calculator = new StackCalculator();
        setSegmentsRam();
        setStackName();

        jbInit();

        // Setting the top level location of the components.
        ram.setTopLevelLocation(this);
        segments.getStaticSegment().setTopLevelLocation(this);
        segments.getLocalSegment().setTopLevelLocation(this);
        segments.getArgSegment().setTopLevelLocation(this);
        segments.getThisSegment().setTopLevelLocation(this);
        segments.getThatSegment().setTopLevelLocation(this);
        segments.getTempSegment().setTopLevelLocation(this);
        stack.setTopLevelLocation(this);
        workingStack.setTopLevelLocation(this);
    }

    public void setWorkingDir(File file) {
        program.setWorkingDir(file);
    }

    public void loadProgram() {
        program.loadProgram();
    }

    /**
     * Returns the calculator GUI component.
     */
    public CalculatorGUI getCalculator() {
        return calculator;
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
    public LabeledPointedMemoryGUI getRAM() {
        return ram;
    }

    /**
     * Returns the Program GUI component.
     */
    public VMProgramGUI getProgram() {
        return program;
    }

    /**
     * Returns the call stack GUI component.
     */
    public CallStackGUI getCallStack() {
        return callStack;
    }

    /**
     * Returns the Stack GUI component.
     */
    public PointedMemorySegmentGUI getStack() {
        return stack;
    }

    /**
     * Returns the static memory segment component.
     */
    public MemorySegmentGUI getStaticSegment() {
        return segments.getStaticSegment();
    }

    /**
     * Returns the local memory segment component.
     */
    public MemorySegmentGUI getLocalSegment() {
        return segments.getLocalSegment();
    }

    /**
     * Returns the arg memory segment component.
     */
    public MemorySegmentGUI getArgSegment() {
        return segments.getArgSegment();
    }

    /**
     * Returns the this memory segment component.
     */
    public MemorySegmentGUI getThisSegment() {
        return segments.getThisSegment();
    }

    /**
     * Returns the that memory segment component.
     */
    public MemorySegmentGUI getThatSegment() {
        return segments.getThatSegment();
    }

    /**
     * Returns the temp memory segment component.
     */
    public MemorySegmentGUI getTempSegment() {
        return segments.getTempSegment();
    }

    /**
     * Returns the working stack.
     */
    public PointedMemorySegmentGUI getWorkingStack() {
        return workingStack;
    }

    public Point getAdditionalDisplayLocation() {
        return new Point(492, 10);
    }

    // Sets the memory component of the memory segments with the current RAM.
    private void setSegmentsRam() {
        // Setting the memory of the segments.
        segments.getStaticSegment().setMemoryComponent(ram);
        segments.getLocalSegment().setMemoryComponent(ram);
        segments.getArgSegment().setMemoryComponent(ram);
        segments.getThisSegment().setMemoryComponent(ram);
        segments.getThatSegment().setMemoryComponent(ram);
        segments.getTempSegment().setMemoryComponent(ram);
        stack.setMemoryComponent(ram);
        workingStack.setMemoryComponent(ram);
        //registers the segments to listen to the repain event of the ram.
        ram.addChangeListener(segments.getStaticSegment());
        ram.addChangeListener(segments.getLocalSegment());
        ram.addChangeListener(segments.getArgSegment());
        ram.addChangeListener(segments.getThisSegment());
        ram.addChangeListener(segments.getThatSegment());
        ram.addChangeListener(segments.getTempSegment());
        ram.addChangeListener(stack);
        ram.addChangeListener(workingStack);
    }

    // Sets the name of the stack.
    private void setStackName() {
        stack.setSegmentName("Global Stack");
    }

    // Initialization of this component.
    private void jbInit() {
        this.setLayout(null);
        keyboard.setBounds(492, 270, keyboard.getWidth(), keyboard.getHeight());
        screen.setBounds(492, 10, screen.getWidth(), screen.getHeight());
        program.setVisibleRows(15);
        program.setBounds(new Rectangle(6, 10, program.getWidth(), program.getHeight()));
        ram.setVisibleRows(15);
        ram.setBounds(new Rectangle(766, 327, ram.getWidth(), ram.getHeight()));

        stack.setVisibleRows(15);
        stack.setBounds(new Rectangle(561, 327, stack.getWidth(), stack.getHeight()));
        segments.getSplitPane().setBounds(new Rectangle(289, 10, segments.getSplitPane().getWidth(), segments.getSplitPane().getHeight()));
        bus.setBounds(new Rectangle(0, 0, WIDTH , HEIGHT));

        calculator.setBorder(BorderFactory.createLoweredBevelBorder());
        calculator.setBounds(new Rectangle(137, 331, 148, 103));
        calculator.setVisible(false);
        workingStack.setVisibleRows(7);
        workingStack.setBounds(new Rectangle(8,304,workingStack.getWidth(), workingStack.getHeight()));

        callStack.setVisibleRows(7);
        callStack.setBounds(new Rectangle(8, 458, callStack.getWidth(), callStack.getHeight()));

        this.add(bus, null);
        this.add(screen, null);
        this.add(keyboard, null);
        this.add(program, null);
        this.add(workingStack, null);
        this.add(callStack, null);
        this.add(calculator, null);
        this.add(stack, null);
        this.add(ram, null);
        this.add(callStack, null);
        this.add(segments.getSplitPane(),null);

        setSize(WIDTH,HEIGHT);
    }
}
