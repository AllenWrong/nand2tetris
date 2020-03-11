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

package SimulatorsGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Hack.CPUEmulator.*;
import Hack.Utilities.*;

/**
 * A Screen GUI component.
 */
public class ScreenComponent extends JPanel implements ScreenGUI, ActionListener {

    // the clock intervals for animation
    private static final int ANIMATION_CLOCK_INTERVALS = 50;
    private static final int STATIC_CLOCK_INTERVALS = 500;

    // The screen memory array
    private short[] data;

    // redraw flag
    private boolean redraw = true;

    // screen location at a given index
    private int[] x, y;

    // The screen redrawing timer
    protected Timer timer;

    /**
     * Constructs a new Sceen with given height & width (in words)
     * and amount of bits per word.
     */
    public ScreenComponent() {
        setOpaque(true);
        setBackground(Color.white);
        setBorder(BorderFactory.createEtchedBorder());
        Insets borderInsets = getBorder().getBorderInsets(this);
        int borderWidth = borderInsets.left + borderInsets.right;
        int borderHeight = borderInsets.top + borderInsets.bottom;
        setPreferredSize(new Dimension(Definitions.SCREEN_WIDTH + borderWidth,
                                       Definitions.SCREEN_HEIGHT + borderHeight));
        setSize(Definitions.SCREEN_WIDTH + borderWidth,
                Definitions.SCREEN_HEIGHT + borderHeight);

        data = new short[Definitions.SCREEN_SIZE];
        x = new int[Definitions.SCREEN_SIZE];
        y = new int[Definitions.SCREEN_SIZE];
        x[0] = borderInsets.left;
        y[0] = borderInsets.top;

        // updates pixels indice
        for (int i = 1; i < Definitions.SCREEN_SIZE; i++) {
            x[i] = x[i - 1] + Definitions.BITS_PER_WORD;
            y[i] = y[i - 1];
            if (x[i] == Definitions.SCREEN_WIDTH + borderInsets.left) {
                x[i] = borderInsets.left;
                y[i]++;
            }
        }

        timer = new Timer(STATIC_CLOCK_INTERVALS, this);
        timer.start();
    }

    /**
     * Updates the screen at the given index with the given value
     * (Assumes legal index)
     */
    public void setValueAt(int index, short value) {
        data[index] = value;
        redraw = true;
    }

    /**
     * Updates the screen's contents with the given values array.
     * (Assumes that the length of the values array equals the screen memory size.
     */
    public void setContents(short[] values) {
        data = values;
        redraw = true;
    }

    /**
     * Resets the content of this component.
     */
    public void reset(){
        for (int i = 0; i < data.length; i++)
            data[i] = 0;

        redraw = true;
    }

    /**
     * Refreshes this component.
     */
    public void refresh() {
        if (redraw) {
            repaint();
            redraw = false;
        }
    }

    /**
     * Starts the animation.
     */
    public void startAnimation() {
        timer.setDelay(ANIMATION_CLOCK_INTERVALS);
    }

    /**
     * Stops the animation.
     */
    public void stopAnimation() {
        timer.setDelay(STATIC_CLOCK_INTERVALS);
    }

    /**
     * Called at constant intervals
     */
    public void actionPerformed(ActionEvent e) {
        if (redraw) {
            repaint();
            redraw = false;
        }
    }

    /**
     * Called when the screen needs to be painted.
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        for (int i = 0; i < Definitions.SCREEN_SIZE; i++) {
            if (data[i] != 0) {
                if (data[i] == 0xffff) // draw a full line
                    g.drawLine(x[i], y[i], x[i] + 15, y[i]);
                else {
                    short value = data[i];
                    for (int j = 0; j < 16; j++) {
                        if ((value & 0x1) == 1)
                            // since there's no drawPixel, uses drawLine to draw one pixel
                            g.drawLine(x[i] + j, y[i], x[i] + j, y[i]);

                        value = (short)(value >> 1);
                    }
                }
            }
        }
    }
}
