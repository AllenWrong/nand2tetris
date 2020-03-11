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

package Hack.ComputerParts;

import Hack.Events.*;

/**
 * An interface for the GUI of an interactive computer part.
 * This GUI enables user input and therefore should handle errors
 * using the ErrorEvent.
 */
public interface InteractiveComputerPartGUI extends ComputerPartGUI {

    /**
     * Registers the given ErrorEventListener as a listener to this simulator.
     */
    public void addErrorListener(ErrorEventListener listener);

    /**
     * Un-registers the given ErrorEventListener from being a listener
     * to this GUI.
     */
    public void removeErrorListener(ErrorEventListener listener);

    /**
     * Notifies all the ErrorEventListeners on an error that occured in the
     * computer part gui by creating an ErrorEvent (with the error message)
     * and sending it using the errorOccured method to all the listeners.
     */
    public void notifyErrorListeners(String errorMessage);
}
