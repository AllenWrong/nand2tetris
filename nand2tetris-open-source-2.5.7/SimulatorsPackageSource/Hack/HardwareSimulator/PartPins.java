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

package Hack.HardwareSimulator;

import Hack.ComputerParts.*;
import Hack.Gates.*;
import java.util.*;

/**
 * Represents a collection of pins of a specific part.
 */
public class PartPins extends ValueComputerPart {


    // The gui
    private PartPinsGUI gui;

    // The part pins.
    private Vector partPins;

    // The current gate
    private Gate gate;

    // The GateClass of the part
    private GateClass partGateClass;

    // mapping from the pins' nodes to their gui adapters.
    private Hashtable nodes;

    /**
     * Constructs a new Part Pins with the given gui.
     */
    public PartPins(PartPinsGUI gui) {
        super(gui != null);
        this.gui = gui;
        partPins = new Vector();
        nodes = new Hashtable();
        clearGate();
    }

    // Removes the current gate
    private void clearGate() {
        gate = null;
        clearPart();
    }

    // Removes the current part
    private void clearPart() {
        partPins.removeAllElements();
        partGateClass = null;

        // remove all node gui adapters
        Enumeration enum = nodes.keys();
        while (enum.hasMoreElements()) {
            Node node = (Node)enum.nextElement();
            Node nodeAdapter = (Node)nodes.get(node);
            node.removeListener(nodeAdapter);
        }

        refreshGUI();
    }

    // Sets the current gate.
    public void setGate(Gate gate) {
        clearGate();
        this.gate = gate;
    }

    // Sets the current part GateClass.
    public void setPart(GateClass partGateClass, String partName) {
        clearPart();
        this.partGateClass = partGateClass;

        if (hasGUI)
            gui.setPartName(partName);
    }

    // Adds the given pin to list
    public void addPin(String partPinName, String gatePinName) {
        if (gate != null && partGateClass != null) {
            String cleanPartPinName = partPinName;
            String cleanGatePinName = gatePinName;

            // find names without sub bus specifications
            if (partPinName.indexOf("[") >= 0)
                cleanPartPinName = partPinName.substring(0, partPinName.indexOf("["));
            if (gatePinName.indexOf("[") >= 0)
                cleanGatePinName = gatePinName.substring(0, gatePinName.indexOf("["));

            // prepare part pin info
            PinInfo partInfo = partGateClass.getPinInfo(cleanPartPinName);
            PartPinInfo info = new PartPinInfo();
            info.partPinName = partInfo.name;
            try {
                info.partPinSubBus = CompositeGateClass.getSubBus(partPinName);
            } catch (Exception e) {}

            // find gate's node and info
            Node node;
            boolean selfFittingWidth = false;
            if (cleanGatePinName.equals(CompositeGateClass.TRUE_NODE_INFO.name)) {
                node = Gate.TRUE_NODE;
                info.gatePinName = CompositeGateClass.TRUE_NODE_INFO.name;
                selfFittingWidth = true;
            }
            else if (cleanGatePinName.equals(CompositeGateClass.FALSE_NODE_INFO.name)) {
                node = Gate.FALSE_NODE;
                info.gatePinName = CompositeGateClass.FALSE_NODE_INFO.name;
                selfFittingWidth = true;
            }
            else if (cleanGatePinName.equals(CompositeGateClass.CLOCK_NODE_INFO.name)) {
                node = Gate.CLOCK_NODE;
                info.gatePinName = CompositeGateClass.CLOCK_NODE_INFO.name;
            }
            else {
                node = gate.getNode(cleanGatePinName);
                PinInfo gateInfo = gate.getGateClass().getPinInfo(cleanGatePinName);
                info.gatePinName = gateInfo.name;
            }

            if (selfFittingWidth)
                info.gatePinSubBus = new byte[]{0, (byte)(partInfo.width - 1)};
            else {
                try {
                    info.gatePinSubBus = CompositeGateClass.getSubBus(gatePinName);
                } catch (Exception e) {}
            }

            // create node adapter for notifying gui on value changes
            Node nodeAdapter = null;
            if (info.gatePinSubBus == null)
                nodeAdapter = new NodePartPinsAdapter(this, partPins.size());
            else
                nodeAdapter = new SubNodePartPinsAdapter(info.gatePinSubBus[0],
                                                         info.gatePinSubBus[1],
                                                         this, partPins.size());
            node.addListener(nodeAdapter);
            nodes.put(node, nodeAdapter);
            partPins.addElement(info);
            refreshGUI();
            nodeAdapter.set(node.get());
            reset();
        }
    }

    public ComputerPartGUI getGUI() {
        return gui;
    }

    public short getValueAt(int index) {
        return ((PartPinInfo)partPins.elementAt(index)).value;
    }

    public void refreshGUI() {
        if (displayChanges)
            gui.setContents(partPins);
    }

    public void setValueAt(int index, short value, boolean quiet) {
        if (getValueAt(index) != value)
            super.setValueAt(index, value, quiet);
    }

    public void doSetValueAt(int index, short value) {}
}
