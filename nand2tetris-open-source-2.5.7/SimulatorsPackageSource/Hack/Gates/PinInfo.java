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
 * Holds information on a gate's pin.
 */
public class PinInfo {

    /**
     * The name of the gate's pin.
     */
    public String name;

    /**
     * The width of the pin's bus.
     */
    public byte width;

    /**
     * The value at the pin.
     */
    public short value;

    // Initialization marking.
    private boolean[] initialized;

    /**
     * Constructs a new empty PinInfo.
     */
    public PinInfo() {
        initialized = new boolean[16];
    }

    /**
     * Constructs a new PinInfo with the given name and width.
     */
    public PinInfo(String name, byte width) {
        this.name = name;
        this.width = width;
        initialized = new boolean[width];
    }

    /**
     * Marks the given sub bus as initialized.
     * If subBus is null, all the pin is initialized.
     */
    public void initialize(byte[] subBus) {
        byte from, to;

        if (subBus != null) {
            from = subBus[0];
            to = subBus[1];
        }
        else {
            from = 0;
            to = (byte)(width - 1);
        }

        for (byte i = from; i <= to; i++)
            initialized[i] = true;
    }

    /**
     * Checks whether the given sub bus is marked as initialized.
     * If subBus is null, all the pin is checked.
     */
    public boolean isInitialized(byte[] subBus) {
        boolean found = false;
        byte from, to;

        if (subBus != null) {
            from = subBus[0];
            to = subBus[1];
        }
        else {
            from = 0;
            to = (byte)(width - 1);
        }

        for (byte i = from; i <= to && !found; i++)
            found = initialized[i];

        return found;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object other) {
        return (other instanceof PinInfo) && name.equals(((PinInfo)other).name);
    }
}
