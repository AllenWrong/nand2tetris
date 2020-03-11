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

import java.util.*;

/**
 * A set of GateClasses.
 */
public class GateClassSet extends HashSet {

    /**
     * Creates a new GateClassSet.
     */
    public GateClassSet() {
        super();
    }

    /**
     * Adds the given GateClass to the set.
     */
    public boolean add(GateClass gateClass) {
        return super.add(gateClass);
    }

    /**
     * Removes the given GateClass from the set.
     */
    public boolean remove(GateClass gateClass) {
        return super.remove(gateClass);
    }

    /**
     * Returns true if this set contains the given GateClass.
     */
    public boolean contains(GateClass gateClass) {
        return super.contains(gateClass);
    }
}
