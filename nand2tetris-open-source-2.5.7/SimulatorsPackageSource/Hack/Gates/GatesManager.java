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

package Hack.Gates;

import java.io.*;
import java.util.Vector;

/**
 * A singleton - manager for common gates properties.
 */
public class GatesManager {

    // The single instance.
    private static GatesManager singleton;

    // The working HDL dir
    private File workingDir;

    // The BuiltIn HDL dir
    private File builtInDir;

    // The gates panel on which gate components are added
    private GatesPanelGUI gatesPanel;

    // The error handler for errors that occur in the gate components.
    private GateErrorEventListener errorHandler;

    // The list of built in chips with gui
    private Vector chips;

    // When true, BuiltIn chips with gui should create and update their gui.
    // otherwise, their gui shouldn't be created.
    private boolean updateChipsGUI;

    /**
     * Constructs a new GatesManager.
     */
    private GatesManager() {
        chips = new Vector();
        updateChipsGUI = true;
    }

    /**
     * Returns the single instance of GatesManager.
     */
    public static GatesManager getInstance() {
        if (singleton == null)
            singleton = new GatesManager();

        return singleton;
    }

    /**
     * Returns the current HDL dir.
     */
    public File getWorkingDir() {
        return workingDir;
    }

    /**
     * Sets the current HDL dir with the given dir.
     */
    public void setWorkingDir(File file) {
        workingDir = file;
    }

    /**
     * Returnss the BuiltIn HDL dir.
     */
    public File getBuiltInDir() {
        return builtInDir;
    }

    /**
     * Sets the BuiltIn HDL dir with the given dir.
     */
    public void setBuiltInDir(File file) {
        builtInDir = file;
    }

    /**
     * Returns all the chips in the gate manager.
     */
    public BuiltInGateWithGUI[] getChips() {
        BuiltInGateWithGUI[] array = new BuiltInGateWithGUI[chips.size()];
        chips.toArray(array);
        return array;
    }

    /**
     * Adds the given chip with gui to the chips' list and to the gates panel.
     */
     public void addChip(BuiltInGateWithGUI chip) {
        chips.add(chip);
        chip.addErrorListener(errorHandler);
        chip.setParent(chip); // set the chip to be its own parent for Eval notifications.

        if (gatesPanel != null)
            gatesPanel.addGateComponent(chip.getGUIComponent());
     }

    /**
     * Removes the given chip with gui from the chips' list and from the gates panel.
     */
     public void removeChip(BuiltInGateWithGUI chip) {
        chips.remove(chip);
        chip.removeErrorListener(errorHandler);

        if (gatesPanel != null)
            gatesPanel.removeGateComponent(chip.getGUIComponent());
     }

     /**
      * Remove all the chips from the list and from the gates panel.
      */
     public void removeAllChips() {
        for (int i = 0; i < chips.size(); i++)
            ((BuiltInGateWithGUI)chips.elementAt(i)).removeErrorListener(errorHandler);

        chips.removeAllElements();

        if (gatesPanel != null)
            gatesPanel.removeAllGateComponents();
     }

    /**
     * Sets the gates panel with the given gate panel.
     */
    public void setGatesPanel(GatesPanelGUI gatesPanel) {
        this.gatesPanel = gatesPanel;
    }

    /**
     * Returns the error handler.
     */
    public GateErrorEventListener getErrorHandler() {
        return errorHandler;
    }

    /**
     * Sets the error handler.
     */
    public void setErrorHandler(GateErrorEventListener errorHandler) {
        this.errorHandler = errorHandler;
    }

    /**
     * Returns the full HDL file name that matches the given gate name.
     * The HDL file is searched first in the current dir, and if not found, in the BuiltIn dir.
     * If not found in any of them, returns null.
     */
    public String getHDLFileName(String gateName) {
        String result = null;
        String name = gateName + ".hdl";

        File file = new File(workingDir, name);
        if (file.exists())
            result = file.getAbsolutePath();
        else {
            file = new File(builtInDir, name);
            if (file.exists())
                result = file.getAbsolutePath();
        }

        return result;
    }

    /**
     * Returns true if built in chips with gui should create and update their gui components.
     */
    public boolean isChipsGUIEnabled() {
        return updateChipsGUI;
    }

    /**
     * Sets whether built in chips with gui should create and update their gui components
     * or not.
     */
    public void enableChipsGUI(boolean value) {
        updateChipsGUI = value;
    }

}
