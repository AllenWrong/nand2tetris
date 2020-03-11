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

package Hack.VMEmulator;

import Hack.ComputerParts.*;
import java.util.*;

/**
 * A call stack. Holds a vector of called function names.
 */
public class CallStack extends ComputerPart {

    // the vector of function names.
    private Vector names;

    // the stack gui
    private CallStackGUI gui;

    /**
     * Constructs a new call stack with the given GUI (optional).
     */
    public CallStack(CallStackGUI gui) {
        super(gui != null);
        names = new Vector();
        this.gui = gui;
    }

    /**
     * Returns the name of the function at the top of the stack.
     */
    public String getTopFunction() {
        return (names.size() > 0 ? (String)names.elementAt(names.size() - 1) : "");
    }

    /**
     * Adds the given function name at the top of the stack.
     */
    public void pushFunction(String functionName) {
        names.addElement(functionName);
        if (displayChanges)
            gui.setContents(names);
    }

    /**
     * Removes the function at the top of the stack.
     */
    public void popFunction() {
        if (names.size() > 0) {
            names.removeElementAt(names.size() - 1);
            if (displayChanges)
                gui.setContents(names);
        }
    }

    /**
     * Resets the contents of the computer part.
     */
    public void reset() {
        super.reset();
        names.removeAllElements();
    }

    /**
     * Returns the GUI of the computer part.
     */
    public ComputerPartGUI getGUI() {
        return gui;
    }

    public void refreshGUI() {
        if (displayChanges)
            gui.setContents(names);
    }
}
