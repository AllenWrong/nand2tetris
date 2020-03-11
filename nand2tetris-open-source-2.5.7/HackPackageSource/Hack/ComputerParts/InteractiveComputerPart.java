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
import java.util.*;

/**
 * An interactive computer part - a computer part that enables input to its GUI.
 * This is the abstract base class for all interactive computer parts.
 * This computer part notifies its listeners on errors using the ComputerPartErrorEvent.
 * It also listens to ComputerPartGUIErrorEvents from the GUI (and therefore should register
 * as a ComputerPartGUIErrorEventlistener to it). When such an event occures,
 * the error is sent to the error listeners of the computer part itself.
 */
public abstract class InteractiveComputerPart extends ComputerPart
 implements ErrorEventListener {

    private Vector errorListeners;

    /**
     * Constructs a new interactive computer part.
     * If hasGUI is true, the ComputerPart will display its contents.
     */
    public InteractiveComputerPart(boolean hasGUI) {
        super(hasGUI);
        errorListeners = new Vector();
    }

    /**
     * Registers the given ComputerPartErrorEventListener as a listener to this ComputerPart.
     */
    public void addErrorListener(ComputerPartErrorEventListener listener) {
        errorListeners.addElement(listener);
    }

    /**
     * Un-registers the given ComputerPartErrorEventListener from being a listener
     * to this ComputerPart.
     */
    public void removeErrorListener(ComputerPartErrorEventListener listener) {
        errorListeners.removeElement(listener);
    }

    /**
     * Notifies all the ComputerPartErrorEventListeners on an error that occured in the
     * computer part by creating a ComputerPartErrorEvent (with the error message)
     * and sending it using the computerPartErrorOccured method to all the listeners.
     */
    public void notifyErrorListeners(String errorMessage) {
        ComputerPartErrorEvent event = new ComputerPartErrorEvent(this, errorMessage);

        for (int i = 0; i < errorListeners.size(); i++)
            ((ComputerPartErrorEventListener)errorListeners.elementAt(i)).computerPartErrorOccured(event);
    }

    /**
     * Clears all the ComputerPartErrorEventListeners from errors.
     */
    public void clearErrorListeners() {
        ComputerPartErrorEvent event = new ComputerPartErrorEvent(this, null);

        for (int i = 0; i < errorListeners.size(); i++)
            ((ComputerPartErrorEventListener)errorListeners.elementAt(i)).computerPartErrorOccured(event);
    }

    /**
     * Called when an error occured in the GUI.
     * The event contains the source object and the error message.
     */
    public void errorOccured(ErrorEvent event) {
        notifyErrorListeners(event.getErrorMessage());
    }
}
