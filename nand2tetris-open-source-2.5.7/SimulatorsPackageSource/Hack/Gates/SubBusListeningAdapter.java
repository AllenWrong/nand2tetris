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
 * A Node that receives a target node and low & high bit indice of a sub bus. When the
 * value of this node changes, it only changes the appropriate sub bus of the target node.
 */
public class SubBusListeningAdapter extends Node {

    // The mask which filters out the non-relevant part of the sub-node
    private short mask;

    // The amount of bits to shift left before masking
    private byte shiftLeft;

    // The target node (the node that this node affects).
    private Node targetNode;

    /**
     * Constructs a new SubBusListeningAdapter with the given target node and the
     * low & high bit indice of the sub bus.
     */
    public SubBusListeningAdapter(Node targetNode, byte low, byte high) {
        mask = SubNode.getMask(low, high);
        shiftLeft = low;
        this.targetNode = targetNode;
    }

    /**
     * Sets the node's value with the given value.
     * Notifies the listeners on the change by calling their set() method.
     */
    public void set(short value) {
        short masked1 = (short)(targetNode.get() & (~mask));
        short masked2 = (short)((short)(value << shiftLeft) & mask);
        targetNode.set((short)(masked1 | masked2));
    }
}
