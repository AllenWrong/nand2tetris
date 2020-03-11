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

import Hack.ComputerParts.BusGUI;
import Hack.Controller.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class BusComponent extends JPanel implements ActionListener, BusGUI {

    // Minimum and maximum miliseconds per one unit of movement
    private static final int MIN_MS = 10;
    private static final int MAX_MS = 40;

    // Length in pixels per each unit of movement
    private static final double MIN_STEP_LENGTH = 3;
    private static final double MAX_STEP_LENGTH = 11;

    // The height of the text field.
    private static final int HEIGHT = 22;

    // The width of the text field.
    private static final int WIDTH = 128;

    // The textField to be moved
    protected JTextField txtField;

    // The timer for the animation.
    private Timer timer;

    // The border of the text field.
    protected Border txtBorder;

    // the delay between each movement as a function of the current speed
    private int[] delays;

    // the length of each movement as a function of the current speed
    private double[] stepLengths;

    private int counter = 0;

    // current movement distance in each direction
    private double dx = 0;
    private double dy = 0;

    // current position
    private double x = 0;
    private double y = 0;

    // current step length
    private double currentStepLength;


    /**
     * Constructs a new Animator.
     * Sets the delays and step lengths according to the definitions in HackController.
     */
    public BusComponent() {
        txtField = new JTextField();
        timer = new Timer(1000, this);
        int range = HackController.NUMBER_OF_SPEED_UNITS;
        float[] function = HackController.SPEED_FUNCTION;

        stepLengths = new double[range];
        delays = new int[range];
        for (int i = 0; i < range; i++) {
            stepLengths[i] = function[i] * (double)(MAX_STEP_LENGTH - MIN_STEP_LENGTH) + MIN_STEP_LENGTH;
            delays[i] = (int)(MAX_MS - function[i] * (double)(MAX_MS - MIN_MS));
        }

        setSpeed(3);

        jbInit();

    }

    /**
     * The action to be performed every clock interval.
     */
    public synchronized void actionPerformed(ActionEvent e) {
        x = x + dx;
        y = y + dy;
        txtField.setLocation((int)x,(int)y);
        counter--;
        if(counter==0) {
            timer.stop();
            txtField.setVisible(false);
            notify();
        }
    }

    /**
     * Moves the given value from the source coordinates to the target coordinates.
     */
    public synchronized void move (Point p1, Point p2, String value) {
        txtField.setText(value);
        x = p1.getX() - 2;
        y = p1.getY() - 2;
        txtField.setLocation((int)x,(int)y);
        txtField.setVisible(true);

        int totalX = (int)(p2.getX() - p1.getX()) + 2;
        int totalY = (int)(p2.getY() - p1.getY()) + 2;

        int absX = Math.abs(totalX);
        int absY = Math.abs(totalY);

        dy = (double)(currentStepLength * absY) / (double)(absX + absY);
        dx = currentStepLength - dy;
        counter = (int)((double)absX / dx);
        if (totalX < 0)
            dx = -dx;
        if (totalY < 0)
            dy = -dy;

        timer.start();
        try {
            wait();
        } catch (InterruptedException ie) {
        }
    }


    /**
     * Sets the sending speed (in the range 1..HackController.NUMBER_OF_SPEED_UNITS).
     */
    public void setSpeed(int speedUnit) {
        timer.setDelay(delays[speedUnit - 1]);
        currentStepLength = stepLengths[speedUnit - 1];
    }

    /**
     * Resets the content of this BusComponent.
     */
    public void reset() {}

    /**
     * Sets the font to be used in the bus.
     */
    public void setBusFont(Font font) {
        txtField.setFont(font);
    }

    /**
     * Sets the size of the bus according to the given Rectangle.
     */
    public void setBusSize(Rectangle r) {
        txtField.setBounds(r);
    }

    // Initializes this component.
    private void jbInit() {
        txtBorder = BorderFactory.createMatteBorder(4,4,4,4,Color.orange);
        txtField.setBounds(new Rectangle(10, 8, WIDTH, HEIGHT));
        txtField.setBackground(Color.white);
        txtField.setEnabled(false);
        txtField.setBorder(txtBorder);
        txtField.setDisabledTextColor(Color.black);
        txtField.setEditable(false);
        txtField.setHorizontalAlignment(SwingConstants.RIGHT);
        txtField.setFont(Utilities.valueFont);
        this.setLayout(null);
        this.add(txtField, null);
        txtField.setVisible(false);
        this.setOpaque(false);
    }
}
