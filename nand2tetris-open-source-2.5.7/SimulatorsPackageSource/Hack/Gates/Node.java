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

/**
 * A node - a wire (or a complete bus) in a circuit.
 */
public class Node {

    // the value of the node
    protected short value;

    // listeners list
    protected NodeSet listeners;

    /**
     * Constructs a new node.
     */
    public Node() {
    }

    /**
     * Constructs a new Node with the given initial value.
     */
    public Node(short initialValue) {
        value = initialValue;
    }

    /**
     * Adds the given node as a listener.
     */
    public void addListener(Node node) {
        if (listeners == null)
            listeners = new NodeSet();

        listeners.add(node);
    }

    /**
     * Removes the given node from being a listener.
     */
    public void removeListener(Node node) {
        if (listeners != null)
            listeners.remove(node);
    }

    /**
     * Returns the value of this node.
     */
    public short get() {
        return value;
    }

    /**
     * Sets the node's value with the given value.
     * Notifies the listeners on the change by calling their set() method.
     */
    public void set(short value) {
        if (this.value != value) {
            this.value = value;

            if (listeners != null)
                for (int i = 0; i < listeners.size(); i++)
                    listeners.getNodeAt(i).set(get());
        }
    }
}
