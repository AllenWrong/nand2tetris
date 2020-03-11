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

package HackGUI;

import java.util.EventObject;
import Hack.Controller.Breakpoint;

/**
 * An event for notifying a BreakpointChangedListener on a change in one of the
 * breakpoints.
 */
public class BreakpointChangedEvent extends EventObject {

    // The breakpoint
    private Breakpoint breakpoint;

    /**
     * Constructs a new BreakpointChangedEvent with the given source and the changed breakpoint.
     */
    public BreakpointChangedEvent(Object source, Breakpoint breakpoint) {
        super(source);
        this.breakpoint = breakpoint;
    }

    /**
     * Returns the breakpoints vector
     */
    public Breakpoint getBreakpoint() {
        return breakpoint;
    }
}
