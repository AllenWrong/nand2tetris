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
import Hack.CPUEmulator.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.border.*;


/**
 * This class represents the gui of an ALU.
 */
public class ALUComponent extends JPanel implements ALUGUI{

    // location constants
    private final static int START_LOCATION_ZERO_X = 7;
    private final static int START_LOCATION_ZERO_Y = 39;
    private final static int START_LOCATION_ONE_Y = 85;
    private final static int START_LOCATION_TWO_X = 237;
    private final static int START_LOCATION_TWO_Y = 61;
    private final static int START_ALU_X = 159;
    private final static int FINISH_ALU_X = 216;
    private final static int LOCATION_WIDTH = 124;
    private final static int LOCATION_HEIGHT = 19;

    // A wide stroke for painting the bounds of the alu.
    private final static BasicStroke wideStroke = new BasicStroke(3.0f);

    // A regular thin stroke.
    private final static BasicStroke regularStroke = new BasicStroke(1.0f);

    // The format in which the value is represented: decimal, hexadecimal
    // or binary.
    protected int dataFormat;

    // The value in location0
    protected short location0Value;

    // The value in location1
    protected short location1Value;

    // The value in location2
    protected short location2Value;

    // Creating the three text fields.
    protected JTextField location0 = new JTextField();
    protected JTextField location1 = new JTextField();
    protected JTextField location2 = new JTextField();

    // the command of this ALU.
    private JTextField commandLbl = new JTextField();

    // The initial ALU color.
    private Color aluColor = new Color(107,194,46);

    // The label with the string "ALU".
     private JLabel nameLbl = new JLabel();

    // The border of the alu's command.
    private Border commandBorder;

    // creating the labels of the inputs and outputs.
    private JLabel location0Lbl = new JLabel();
    private JLabel location1Lbl = new JLabel();
    private JLabel location2Lbl = new JLabel();

    // The null value of this ALU
    protected short nullValue;

    // a boolean field specifying if the null value should be activated or not.
    protected boolean hideNullValue;


    /**
     * Constructs a new ALUComponent.
     */
    public ALUComponent() {
        dataFormat = Format.DEC_FORMAT;
        jbInit();
    }

    /**
     * Sets the null value.
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
                return Format.translateValueToString(value, dataFormat);
        }
        else return Format.translateValueToString(value, dataFormat);

    }

    /**
     * Enabling and diabling user inputs. those methods aren't implemented
     * because in the ALU the text fields are always disabled.
     */
    public void disableUserInput() {}
    public void enableUserInput() {}


    /**
     * Flashes the ALU command.
     */
    public void commandFlash() {
        commandLbl.setBackground(Color.red);
        repaint();
    }

    /**
     * Hides the ALU's command flash.
     */
    public void hideCommandFlash() {
        commandLbl.setBackground(new Color(107,194,46));
        repaint();
    }

    /**
     * Starts the alu's flashing.
     */
    public void bodyFlash() {
        aluColor = Color.red;
        commandLbl.setBackground(Color.red);
        repaint();
    }

    /**
     * Stops the alu's flashing.
     */
    public void hideBodyFlash() {
        aluColor = new Color(107,194,46);
        commandLbl.setBackground(new Color(107,194,46));
        repaint();
    }

    /**
     * flashes the value at the given index.
     */
    public void flash (int index) {
        switch(index) {
            case 0:
                location0.setBackground(Color.orange);
                break;
            case 1:
                location1.setBackground(Color.orange);
                break;
            case 2:
                location2.setBackground(Color.orange);
                break;
        }
    }

    /**
     * hides the existing flash.
     */
    public void hideFlash () {
        location0.setBackground(null);
        location1.setBackground(null);
        location2.setBackground(null);
    }

    /**
     * Hides all highlightes.
     */
    public void hideHighlight() {
        location0.setDisabledTextColor(Color.black);
        location1.setDisabledTextColor(Color.black);
        location2.setDisabledTextColor(Color.black);
        repaint();
    }

    /**
     * Highlights the value at the given index.
     */
    public void highlight(int index) {

        switch(index) {
            case 0:
                location0.setDisabledTextColor(Color.blue);
                break;
            case 1:
                location1.setDisabledTextColor(Color.blue);
                break;
            case 2:
                location2.setDisabledTextColor(Color.blue);
                break;
        }
        repaint();
    }

    /**
     * Returns the coordinates of the top left corner of the value at the given index.
     */
    public Point getCoordinates(int index) {
        Point location = getLocation();
        switch(index) {
            case 0:
                return new Point((int)(location.getX() + location0.getLocation().getX()), (int)(location.getY() + location0.getLocation().getY()));
            case 1:
                return new Point ((int)(location.getX() + location1.getLocation().getX()), (int)(location.getY() + location1.getLocation().getY()));
            case 2:
                return new Point ((int)(location.getX() + location2.getLocation().getX()), (int)(location.getY() + location2.getLocation().getY()));
            default:
                return null;
        }
    }

    /**
     * Returns the value at the given index in its string representation.
     */
    public String getValueAsString (int index) {

        switch(index) {
            case 0:
                return location0.getText();
            case 1:
                return location1.getText();
            case 2:
                return location2.getText();
            default:
                return null;
        }
    }

    /**
     * Resets the contents of this ALUComponent.
     */
    public void reset() {
        location0.setText(Format.translateValueToString(nullValue,dataFormat));
        location1.setText(Format.translateValueToString(nullValue,dataFormat));
        location2.setText(Format.translateValueToString(nullValue,dataFormat));
        setCommand("");
        hideFlash();
        hideHighlight();
    }

    /**
     * Sets the element at the given index with the given value.
     */
    public void setValueAt(int index, short value) {

        String data = Format.translateValueToString(value,dataFormat);
        switch(index) {
            case 0:
                this.location0Value = value;
                location0.setText(data);
                break;
            case 1:
                this.location1Value = value;
                location1.setText(data);
                break;
            case 2:
                this.location2Value = value;
                location2.setText(data);
                break;
        }
    }

    /**
     * Sets the command with the given one.
     */
     public void setCommand(String command) {
        commandLbl.setText(command);
     }

    /**
     * Paint this ALUComponent.
     */
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.black);

        // fill and stroke GeneralPath
        int x4Points[] = {START_ALU_X, FINISH_ALU_X, FINISH_ALU_X, START_ALU_X};
        int y4Points[] = {23, 56, 83, 116};

        GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,x4Points.length);
        filledPolygon.moveTo(x4Points[0],y4Points[0]);

        for (int index = 1; index < x4Points.length; index++) 	{
            filledPolygon.lineTo(x4Points[index], y4Points[index]);

        };
        filledPolygon.closePath();
        g2.setPaint(aluColor);
        g2.fill(filledPolygon);
        g2.setStroke(wideStroke);
        g2.setPaint(Color.black);
        g2.draw(filledPolygon);
        g2.setStroke(regularStroke);

        // Drawing the lines.
        g2.draw(new Line2D.Double(START_LOCATION_ZERO_X + LOCATION_WIDTH,START_LOCATION_ZERO_Y + (LOCATION_HEIGHT / 2),START_ALU_X,START_LOCATION_ZERO_Y + (LOCATION_HEIGHT / 2)));
        g2.draw(new Line2D.Double(START_LOCATION_ZERO_X + LOCATION_WIDTH,START_LOCATION_ONE_Y + (LOCATION_HEIGHT / 2),START_ALU_X,START_LOCATION_ONE_Y + (LOCATION_HEIGHT / 2)));
        g2.draw(new Line2D.Double(FINISH_ALU_X,START_LOCATION_TWO_Y + (LOCATION_HEIGHT / 2),START_LOCATION_TWO_X - 1,START_LOCATION_TWO_Y + (LOCATION_HEIGHT / 2)));

    }
    /**
     * Sets the numeric format with the given code (out of the format constants
     * in HackController).
     */
    public void setNumericFormat(int formatCode) {
        dataFormat = formatCode;
        location0.setText(Format.translateValueToString(location0Value,formatCode));
        location1.setText(Format.translateValueToString(location1Value,formatCode));
        location2.setText(Format.translateValueToString(location2Value,formatCode));
    }

    // Initializes this component.
    private void jbInit()  {
        setOpaque(false);
        commandBorder = BorderFactory.createLineBorder(Color.black,1);
        this.setLayout(null);
        location0.setForeground(Color.black);
        location0.setDisabledTextColor(Color.black);
        location0.setEditable(false);
        location0.setHorizontalAlignment(SwingConstants.RIGHT);
        location0.setBounds(new Rectangle(START_LOCATION_ZERO_X, START_LOCATION_ZERO_Y, LOCATION_WIDTH, LOCATION_HEIGHT));
        location0.setBackground(UIManager.getColor("Button.background"));
        location0.setEnabled(false);
        location0.setFont(Utilities.valueFont);
        location1.setHorizontalAlignment(SwingConstants.RIGHT);
        location1.setBounds(new Rectangle(START_LOCATION_ZERO_X, START_LOCATION_ONE_Y, LOCATION_WIDTH, LOCATION_HEIGHT));
        location1.setForeground(Color.black);
        location1.setDisabledTextColor(Color.black);
        location1.setEditable(false);
        location1.setBackground(UIManager.getColor("Button.background"));
        location1.setEnabled(false);
        location1.setFont(Utilities.valueFont);
        location2.setHorizontalAlignment(SwingConstants.RIGHT);
        location2.setBounds(new Rectangle(START_LOCATION_TWO_X, START_LOCATION_TWO_Y, LOCATION_WIDTH, LOCATION_HEIGHT));
        location2.setForeground(Color.black);
        location2.setDisabledTextColor(Color.black);
        location2.setEditable(false);
        location2.setBackground(UIManager.getColor("Button.background"));
        location2.setEnabled(false);
        location2.setFont(Utilities.valueFont);
        commandLbl.setBackground(new Color(107, 194, 46));
        commandLbl.setEnabled(false);
        commandLbl.setFont(Utilities.labelsFont);
        commandLbl.setForeground(Color.black);
        commandLbl.setBorder(commandBorder);
        commandLbl.setDisabledTextColor(Color.black);
        commandLbl.setEditable(false);
        commandLbl.setHorizontalAlignment(SwingConstants.CENTER);
        commandLbl.setBounds(new Rectangle(163, 62, 50, 16));
        location0Lbl.setText("D Input :");
        location0Lbl.setBounds(new Rectangle(START_LOCATION_ZERO_X, START_LOCATION_ZERO_Y - 16, 56, 16));
        location0Lbl.setFont(Utilities.smallLabelsFont);
        location0Lbl.setForeground(Color.black);
        location1Lbl.setText("M/A Input :");
        location1Lbl.setBounds(new Rectangle(START_LOCATION_ZERO_X, START_LOCATION_ONE_Y - 16, 70, 16));
        location1Lbl.setFont(Utilities.smallLabelsFont);
        location1Lbl.setForeground(Color.black);
        location2Lbl.setText("ALU output :");
        location2Lbl.setBounds(new Rectangle(START_LOCATION_TWO_X, START_LOCATION_TWO_Y - 16, 72, 16));
        location2Lbl.setFont(Utilities.smallLabelsFont);
        location2Lbl.setForeground(Color.black);
        nameLbl.setText("ALU");
        nameLbl.setFont(Utilities.labelsFont);
        nameLbl.setBounds(new Rectangle(6, 0, 50, 22));
        this.add(commandLbl, null);
        this.add(location1, null);
        this.add(location0, null);
        this.add(location2, null);
        this.add(location0Lbl, null);
        this.add(location1Lbl, null);
        this.add(location2Lbl, null);
        this.add(nameLbl, null);

        setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(new Dimension(368, 122));
        setSize(368, 122);
    }
}
