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
import Hack.HardwareSimulator.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

/**
 * The GUI Component of the Hardware Simulator.
 */
public class HardwareSimulatorControllerComponent extends ControllerComponent implements HardwareSimulatorControllerGUI/*, ChipNameListener */{

    // The buttons of this component.
    private MouseOverJButton loadChipButton;
    private MouseOverJButton tickTockButton;
    private MouseOverJButton evalButton;

    // The icons of the buttons.
    private ImageIcon loadChipIcon;
    private ImageIcon tickTockIcon;
    private ImageIcon evalIcon;

    // The settings window and chip loading window.
    private ChipLoaderFileChooser settingsWindow;

    // The menu items of this component.
    private JMenuItem loadChipMenuItem, evalMenuItem, tickTockMenuItem/*, folderSettingsMenuItem*/;

    // The chip file chooser
    private JFileChooser chipFileChooser;

    /**
     * Constructs a new HardwareSimulatorControllerComponent.
     */
    public HardwareSimulatorControllerComponent() {
        scriptComponent.updateSize(516, 592);
        outputComponent.updateSize(516, 592);
        comparisonComponent.updateSize(516, 592);
    }

    public void disableEval() {
        evalButton.setEnabled(false);
        evalMenuItem.setEnabled(false);
    }

    public void enableEval() {
        evalButton.setEnabled(true);
        evalMenuItem.setEnabled(true);
    }

    public void disableTickTock() {
        tickTockButton.setEnabled(false);
        tickTockMenuItem.setEnabled(false);
    }

    public void enableTickTock() {
        tickTockButton.setEnabled(true);
        tickTockMenuItem.setEnabled(true);
    }

    /**
     * Initializes this component.
     */
    protected void init() {
        super.init();

        settingsWindow = new ChipLoaderFileChooser();
        settingsWindow.addListener(this);

        chipFileChooser = new JFileChooser();
        chipFileChooser.setFileFilter(new HDLFileFilter());

        initLoadChipButton();
        initTickTockButton();
        initEvalButton();
    }

    public void setWorkingDir(File file) {
        super.setWorkingDir(file);
        chipFileChooser.setCurrentDirectory(file);
    }

    /**
     * Arranges the tool bar.
     */
    protected void arrangeToolBar() {
        toolBar.setSize(TOOLBAR_WIDTH, TOOLBAR_HEIGHT);
        toolBar.add(loadChipButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(singleStepButton);
        toolBar.add(ffwdButton);
        toolBar.add(stopButton);
        toolBar.add(rewindButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(evalButton);
        toolBar.add(tickTockButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(scriptButton);
        toolBar.add(breakButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(speedSlider);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(animationCombo);
        toolBar.add(formatCombo);
        toolBar.add(additionalDisplayCombo);
    }

    /**
     * Arranges the menu bar.
     */
    protected void arrangeMenu() {

        super.arrangeMenu();

        fileMenu.removeAll();

        loadChipMenuItem = new JMenuItem("Load Chip",KeyEvent.VK_L);
        loadChipMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadChipMenuItem_actionPerformed(e);
            }
        });
        fileMenu.add(loadChipMenuItem);


        fileMenu.add(scriptMenuItem);

        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        runMenu.removeAll();
        runMenu.add(singleStepMenuItem);
        runMenu.add(ffwdMenuItem);
        runMenu.add(stopMenuItem);
        runMenu.add(rewindMenuItem);
        runMenu.addSeparator();

        evalMenuItem = new JMenuItem("Eval",KeyEvent.VK_E);
        evalMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evalMenuItem_actionPerformed(e);
            }
        });
        runMenu.add(evalMenuItem);

        tickTockMenuItem = new JMenuItem("Tick Tock",KeyEvent.VK_C);
        tickTockMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tickTockMenuItem_actionPerformed(e);
            }
        });

        runMenu.add(tickTockMenuItem);
        runMenu.addSeparator();
        runMenu.add(breakpointsMenuItem);
    }

    // Initializing the load chip button.
    private void initLoadChipButton() {
        loadChipIcon = new ImageIcon(Utilities.imagesDir + "chip.gif");
        loadChipButton = new MouseOverJButton();
        loadChipButton.setMaximumSize(new Dimension(39, 39));
        loadChipButton.setMinimumSize(new Dimension(39, 39));
        loadChipButton.setPreferredSize(new Dimension(39, 39));
        loadChipButton.setToolTipText("Load Chip");
        loadChipButton.setIcon(loadChipIcon);
        loadChipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadChipButton_actionPerformed(e);
            }
        });
    }

    // Initializing the tick tock button.
    private void initTickTockButton() {
        tickTockIcon = new ImageIcon(Utilities.imagesDir + "clock2.gif");
        tickTockButton = new MouseOverJButton();
        tickTockButton.setMaximumSize(new Dimension(39, 39));
        tickTockButton.setMinimumSize(new Dimension(39, 39));
        tickTockButton.setPreferredSize(new Dimension(39, 39));
        tickTockButton.setToolTipText("Tick Tock");
        tickTockButton.setIcon(tickTockIcon);
        tickTockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tickTockButton_actionPerformed(e);
            }
        });
    }


    // Initializing the eval button.
    private void initEvalButton() {
        evalIcon = new ImageIcon(Utilities.imagesDir + "calculator2.gif");
        evalButton = new MouseOverJButton();
        evalButton.setMaximumSize(new Dimension(39, 39));
        evalButton.setMinimumSize(new Dimension(39, 39));
        evalButton.setPreferredSize(new Dimension(39, 39));
        evalButton.setToolTipText("Eval");
        evalButton.setIcon(evalIcon);
        evalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evalButton_actionPerformed(e);
            }
        });
    }

    // Called when the load chip button is pressed.
    private void loadChipPressed() {
        int returnVal = chipFileChooser.showDialog(this, "Load Chip");
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            notifyControllerListeners(HardwareSimulatorControllerEvent.CHIP_CHANGED, chipFileChooser.getSelectedFile().getAbsoluteFile());
        }
    }

    /**
     * Implementing the action of pressing the load chip button.
     */
    public void loadChipButton_actionPerformed(ActionEvent e) {
        loadChipPressed();
    }

    /**
     * Implementing the action of pressing the tick tock button.
     */
    public void tickTockButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.TICKTOCK_CLICKED, null);
    }

    /**
     * Implementing the action of pressing the eval button.
     */
    public void evalButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.EVAL_CLICKED, null);
    }

    /**
     * Implementing the action of choosing the load chip menu item from the menu bar.
     */
    public void loadChipMenuItem_actionPerformed(ActionEvent e) {
        loadChipPressed();
    }

    /**
     * Implementing the action of choosing the eval menu item from the menu bar.
     */
    public void evalMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.EVAL_CLICKED, null);
    }

    /**
     * Implementing the action of choosing the tick tock menu item from the menu bar.
     */
    public void tickTockMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.TICKTOCK_CLICKED, null);
    }
}
