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

import HackGUI.*;
import Hack.VMEmulator.CalculatorGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * This class represents an operation performed on the Stack.
 */
public class StackCalculator extends JPanel implements CalculatorGUI {

    // The input, command and output textfields.
    private JTextField firstInput = new JTextField();
    private JTextField command = new JTextField();
    private JTextField secondInput = new JTextField();
    private JTextField output = new JTextField();

    //wide and regular strokes for the painting.
    private final static BasicStroke wideStroke = new BasicStroke(3.0f);
    private final static BasicStroke regularStroke = new BasicStroke(1.0f);

    // The null value of this component.
    protected short nullValue;

    // A boolean field specifying if the null value should be activated or not.
    protected boolean hideNullValue;

    /**
     * Constructs a new StackCalculator.
     */
    public StackCalculator() {

        jbInit();
    }

    /**
     * Disabling user inputs. this method isn't implemented
     * because in this component the text fields are always disabled.
     */
    public void disableUserInput() {}

    /**
     * Enabling user inputs. this method isn't implemented
     * because in this component the text fields are always disabled.
     */
    public void enableUserInput() {}

    /**
     * Sets the null value of this component.
     */
    public void setNullValue (short value, boolean hideNullValue) {
        nullValue = value;
        this.hideNullValue = hideNullValue;
    }

    /**
     * Translates a given short to a string according to the current format.
     */
    protected String translateValueToString(short value) {
        if(hideNullValue) {
            if(value == nullValue)
                return "";
            else
                return Format.translateValueToString(value, Format.DEC_FORMAT);
        }
        else return Format.translateValueToString(value, Format.DEC_FORMAT);

    }

    /**
     * Hides the calculator GUI.
     */
    public void hideCalculator() {
        setVisible(false);
    }

    /**
     * Displays the calculator GUI.
     */
    public void showCalculator() {
        setVisible(true);
    }

    /**
     * Hides the left input.
     */
    public void hideLeftInput() {
        firstInput.setVisible(false);
    }

    /**
     * Displays the left input.
     */
    public void showLeftInput() {
        firstInput.setVisible(true);
    }

    /**
     * Sets the operator of the calculator with the given operator.
     */
    public void setOperator(char operator){
        command.setText(String.valueOf(operator));
    }

    /**
     * hides the existing flash.
     */
    public void hideFlash() {
        firstInput.setBackground(UIManager.getColor("Button.background"));
        secondInput.setBackground(UIManager.getColor("Button.background"));
        output.setBackground(UIManager.getColor("Button.background"));
    }

    /**
     * flashes the value at the given index.
     */
    public void flash(int index) {
         switch(index) {
            case 0:
                firstInput.setBackground(Color.orange);
                break;
            case 1:
                secondInput.setBackground(Color.orange);
                break;
            case 2:
                output.setBackground(Color.orange);
                break;
        }
    }

    /**
     * Hides all highlightes.
     */
    public void hideHighlight() {
        firstInput.setForeground(Color.black);
        secondInput.setForeground(Color.black);
        output.setForeground(Color.black);
    }

    /**
     * Highlights the value at the given index.
     */
    public void highlight (int index) {
         switch(index) {
            case 0:
                firstInput.setForeground(Color.blue);
                break;
            case 1:
                secondInput.setForeground(Color.blue);
                break;
            case 2:
                output.setForeground(Color.blue);
                break;
        }
    }

    /**
     * Returns the value at the given index in its string representation.
     */
    public String getValueAsString (int index) {
        switch(index) {
            case 0:
                return firstInput.getText();
            case 1:
                return secondInput.getText();
            case 2:
                return output.getText();
            default:
                return "";
        }
    }

    /**
     * Resets the contents of this component.
     */
    public void reset() {
        firstInput.setText(translateValueToString(nullValue));
        secondInput.setText(translateValueToString(nullValue));
        output.setText(translateValueToString(nullValue));
        hideFlash();
        hideHighlight();
    }

    /**
     * Returns the coordinates of the top left corner of the value at the given index.
     */
    public Point getCoordinates(int index) {
        Point location = getLocation();
        switch(index) {
            // The first input
            case 0:
                return new Point((int)(location.getX() + firstInput.getLocation().getX()), (int)(location.getY() + firstInput.getLocation().getY()));
            // The second input
            case 1:
                return new Point ((int)(location.getX() + secondInput.getLocation().getX()), (int)(location.getY() + secondInput.getLocation().getY()));
            // The output
            case 2:
                return new Point ((int)(location.getX() + output.getLocation().getX()), (int)(location.getY() + output.getLocation().getY()));
            default:
                return null;
        }
    }

    /**
     * Sets the element at the given index with the given value.
     */
    public void setValueAt(int index, short value) {
        String data = translateValueToString(value);
        switch(index) {
            case 0:
                firstInput.setText(data);
                break;
            case 1:
                secondInput.setText(data);
                break;
            case 2:
                output.setText(data);
                break;
        }
    }

    /**
     * Paints the line of this component.
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(new Line2D.Double(18,60,142,60));
        g2.setStroke(regularStroke);
    }

    /**
     * Sets the numeric format with the given code (out of the format constants
     * in HackController).
     */
    public void setNumericFormat(int formatCode) {}

    // Initialization of this component.
    private void jbInit() {
        this.setLayout(null);
        firstInput.setHorizontalAlignment(SwingConstants.RIGHT);
        firstInput.setBounds(new Rectangle(18, 8, 124, 19));
        firstInput.setBackground(UIManager.getColor("Button.background"));
        firstInput.setFont(Utilities.valueFont);
        command.setFont(Utilities.bigLabelsFont);
        command.setHorizontalAlignment(SwingConstants.CENTER);
        command.setBounds(new Rectangle(2, 34, 13, 19));
        command.setBackground(UIManager.getColor("Button.background"));
        command.setBorder(null);
        secondInput.setHorizontalAlignment(SwingConstants.RIGHT);
        secondInput.setBounds(new Rectangle(18, 34, 124, 19));
        secondInput.setBackground(UIManager.getColor("Button.background"));
        secondInput.setFont(new java.awt.Font("Courier New", 0, 12));
        output.setHorizontalAlignment(SwingConstants.RIGHT);
        output.setBounds(new Rectangle(18, 70, 124, 19));
        output.setBackground(UIManager.getColor("Button.background"));
        output.setFont(Utilities.valueFont);
        this.add(secondInput, null);
        this.add(firstInput, null);
        this.add(output, null);
        this.add(command, null);
    }
}
