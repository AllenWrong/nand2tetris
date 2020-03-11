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

import Hack.Gates.*;
import Hack.ComputerParts.*;
import Hack.HardwareSimulator.*;
import HackGUI.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This class represents the gui of the hardware simulator.
 */
public class HardwareSimulatorComponent extends HackSimulatorComponent implements HardwareSimulatorGUI, GatesPanelGUI {

    // The dimension of this window.
    private static final int WIDTH = 1018;
    private static final int HEIGHT = 611;

    // The input pins of the hardware simulator.
    private PinsComponent inputPins;

    // The output pins of the hardware simulator.
    private PinsComponent outputPins;

    // The internal pins of the hardware simulator.
    private PinsComponent internalPins;

    // The hdl view of the hardware simulator.
    private TextFileComponent hdlView;

    // The part pins of the hardware simulator.
    private PartPinsComponent partPins;

    // The parts of the hardware simulator.
    private PartsComponent parts;

    // The message label
    private JLabel messageLbl = new JLabel();

    // The two optional gates panel (with different layouts)
    private JPanel nullLayoutGatesPanel;
    private JPanel flowLayoutGatesPanel;

    // True if the current layout is flow layout.
    private boolean flowLayout = false;

    // The gate info component of the current gate
    private GateInfoComponent gateInfo;

    /**
     * Constructs a new HardwareSimulatorComponent.
     */
    public HardwareSimulatorComponent() {
        nullLayoutGatesPanel = new JPanel();
        flowLayoutGatesPanel = new JPanel();
        nullLayoutGatesPanel.setLayout(null);
        flowLayoutGatesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));

        inputPins = new PinsComponent();
        inputPins.setPinsName("Input pins");
        outputPins = new PinsComponent();
        outputPins.setPinsName("Output pins");
        internalPins = new PinsComponent();
        internalPins.setPinsName("Internal pins");
        partPins = new PartPinsComponent();
        partPins.setPinsName("Part pins");
        parts = new PartsComponent();
        parts.setName("Internal Parts");
        hdlView = new TextFileComponent();

        gateInfo = new GateInfoComponent();

        jbInit();

        inputPins.setTopLevelLocation(this);
        outputPins.setTopLevelLocation(this);
        internalPins.setTopLevelLocation(this);
        partPins.setTopLevelLocation(this);
        hdlView.setName("HDL");
    }

    public void loadProgram() {}

    /**
     * Returns the Gates panel.
     */
    public GatesPanelGUI getGatesPanel() {
        return this;
    }

    /**
     * Returns the HDLView.
     */
    public TextFileGUI getHDLView() {
        return hdlView;
    }

    /**
     * Returns the input pins table.
     */
    public PinsGUI getInputPins() {
        return inputPins;
    }

    /**
     * Returns the output pins table.
     */
    public PinsGUI getOutputPins() {
        return outputPins;
    }

    /**
     * Returns the internal pins table.
     */
    public PinsGUI getInternalPins() {
        return internalPins;
    }

    public GateInfoGUI getGateInfo() {
        return gateInfo;
    }

    /**
     * Returns the part pins table.
     */
    public PartPinsGUI getPartPins() {
        return partPins;
    }

    /**
     * Returns the parts table.
     */
    public PartsGUI getParts() {
        return parts;
    }

    /**
     * Displays the Internal pins table.
     */
    public void showInternalPins() {
        internalPins.setVisible(true);
    }

    /**
     * Hides the Internal pins table.
     */
    public void hideInternalPins() {
        internalPins.setVisible(false);
    }

    /**
     * Displays the Part pins table.
     */
    public void showPartPins() {
        partPins.setVisible(true);
    }

    /**
     * Hides the Part pins table.
     */
    public void hidePartPins() {
        partPins.setVisible(false);
    }

    /**
     * Displays the Parts table.
     */
    public void showParts() {
        parts.setVisible(true);
    }

    /**
     * Hides the Parts table.
     */
    public void hideParts() {
        parts.setVisible(false);
    }

    /**
     * Displays the given message. The display color is chosen according to
     * the 'error' parameter.
     */
    public void displayMessage (String message, boolean error) {
        if(error)
            messageLbl.setForeground(Color.red);
        else
            messageLbl.setForeground(UIManager.getColor("Label.foreground"));
        messageLbl.setText(message);
    }

    public Point getAdditionalDisplayLocation() {
        return new Point(496, 13);
    }

    public void setWorkingDir(File file) {
    }

    /**
     * Adds the given gate component to the gates panel.
     */
    public void addGateComponent(Component gateComponent) {
        if (flowLayout) {
            flowLayoutGatesPanel.add(gateComponent);
            flowLayoutGatesPanel.revalidate();
            flowLayoutGatesPanel.repaint();
        }
        else {
            Component[] components = nullLayoutGatesPanel.getComponents();
            for(int i=0; i<components.length; i++) {
                Rectangle componentBounds = components[i].getBounds();
                int x1 = (int)componentBounds.getX();
                int y1 = (int)componentBounds.getY();
                int x2 = (int)(componentBounds.getX() + componentBounds.getWidth() -1);
                int y2 = (int)(componentBounds.getY() + componentBounds.getHeight() -1);
                if(!(gateComponent.getY() > y2 || gateComponent.getX() > x2 ||
                     gateComponent.getY() + gateComponent.getHeight()-1 < y1  ||
                     gateComponent.getX() + gateComponent.getWidth()-1 < x1)) {

                    flowLayout = true;
                    if (currentAdditionalDisplay == null) {
                        nullLayoutGatesPanel.setVisible(false);
                        flowLayoutGatesPanel.setVisible(true);
                    }

                    for(i=0; i<components.length; i++)
                        flowLayoutGatesPanel.add(components[i]);

                    flowLayoutGatesPanel.add(gateComponent);

                    break;
                }
            }
            if(!flowLayout) {
                nullLayoutGatesPanel.add(gateComponent);
                nullLayoutGatesPanel.revalidate();
                nullLayoutGatesPanel.repaint();
            }
        }
    }

    /**
     * Removes the given gate component from the gates panel.
     */
    public void removeGateComponent(Component gateComponent) {
        nullLayoutGatesPanel.remove(gateComponent);
        flowLayoutGatesPanel.remove(gateComponent);
        nullLayoutGatesPanel.revalidate();
        flowLayoutGatesPanel.revalidate();
        nullLayoutGatesPanel.repaint();
        flowLayoutGatesPanel.repaint();
    }

    /**
     * Removes all the gate components from the gates panel.
     */
    public void removeAllGateComponents() {
        nullLayoutGatesPanel.removeAll();
        flowLayoutGatesPanel.removeAll();
        nullLayoutGatesPanel.revalidate();
        flowLayoutGatesPanel.revalidate();
        nullLayoutGatesPanel.repaint();
        flowLayoutGatesPanel.repaint();

        flowLayout = false;
        if (currentAdditionalDisplay == null) {
            nullLayoutGatesPanel.setVisible(true);
            flowLayoutGatesPanel.setVisible(false);
        }
    }


    // Initialization of this component.
    private void jbInit() {
        this.setLayout(null);
        gateInfo.setBounds(5,10,gateInfo.getWidth(), gateInfo.getHeight());

        inputPins.setVisibleRows(15);
        inputPins.setBounds(5, 53, inputPins.getWidth(), inputPins.getHeight());

        outputPins.setVisibleRows(15);
        outputPins.setBounds(247, 53, outputPins.getWidth(), outputPins.getHeight());

        internalPins.setVisibleRows(15);
        internalPins.setBounds(247, 332, internalPins.getWidth(), internalPins.getHeight());
        internalPins.setVisible(false);

        hdlView.setVisibleRows(15);
        hdlView.setBounds(5, 332, hdlView.getWidth(), hdlView.getHeight());

        partPins.setVisibleRows(15);
        partPins.setBounds(247, 332, partPins.getWidth(), partPins.getHeight());
        partPins.setVisible(false);

        parts.setVisibleRows(15);
        parts.setBounds(247, 332, parts.getWidth(), parts.getHeight());
        parts.setVisible(false);

        nullLayoutGatesPanel.setBorder(BorderFactory.createEtchedBorder());
        nullLayoutGatesPanel.setBounds(new Rectangle(492, 10, 524, 592));

        flowLayoutGatesPanel.setBorder(BorderFactory.createEtchedBorder());
        flowLayoutGatesPanel.setBounds(new Rectangle(492, 10, 524, 592));
        flowLayoutGatesPanel.setVisible(false);

        messageLbl.setBorder(BorderFactory.createLoweredBevelBorder());
        messageLbl.setBounds(new Rectangle(0, 694, WIDTH - 8, 20));

        this.add(partPins, null);
        this.add(hdlView, null);
        this.add(inputPins, null);
        this.add(outputPins, null);
        this.add(internalPins, null);
        this.add(parts, null);
        this.add(messageLbl, null);
        this.add(gateInfo, null);
        this.add(nullLayoutGatesPanel, null);
        this.add(flowLayoutGatesPanel, null);

        setSize(WIDTH,HEIGHT);
    }

    public void setAdditionalDisplay(JComponent additionalComponent) {
        if (currentAdditionalDisplay == null && additionalComponent != null) {
            if (flowLayout)
                flowLayoutGatesPanel.setVisible(false);
            else
                nullLayoutGatesPanel.setVisible(false);
        }
        else if (currentAdditionalDisplay != null && additionalComponent == null) {
            if (flowLayout)
                flowLayoutGatesPanel.setVisible(true);
            else
                nullLayoutGatesPanel.setVisible(true);
        }

        super.setAdditionalDisplay(additionalComponent);
    }
}
