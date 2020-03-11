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
 * A set of nodes.
 */
public class NodeSet extends Vector {

    /**
     * Creates a new NodeSet.
     */
    public NodeSet() {
        super(1,1);
    }

    /**
     * Adds the given node to the set.
     */
    public boolean add(Node node) {
        return super.add(node);
    }

    /**
     * Removes the given node from the set.
     */
    public boolean remove(Node node) {
        return super.remove(node);
    }

    /**
     * Returns true if this set contains the given node.
     */
    public boolean contains(Node node) {
        return super.contains(node);
    }

    /**
     * Returns the Node at the given index.
     * (Assumes a legal index).
     */
    public Node getNodeAt(int index) {
        return (Node)elementAt(index);
    }
}
