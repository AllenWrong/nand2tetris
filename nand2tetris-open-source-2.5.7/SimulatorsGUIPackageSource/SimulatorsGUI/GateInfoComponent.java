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
import Hack.HardwareSimulator.*;
import javax.swing.*;
import java.awt.*;

/**
 * This class represents the GUI of a gate info.
 */
public class GateInfoComponent extends JPanel implements GateInfoGUI {

    // creating labels
    private JLabel chipNameLbl;
    private JLabel timeLbl;

    // creating text fields
    private JTextField chipNameTxt;
    private JTextField timeTxt;

    // true if the clock is currently up
    private boolean clockUp;

    // the name of the chip
    private String chipName;

    /**
     * Constructs a new GateInfoComponent.
     */
    public GateInfoComponent() {
        chipNameLbl = new JLabel();
        timeLbl = new JLabel();

        chipNameTxt = new JTextField();
        timeTxt = new JTextField();

        jbInit();
    }

    public void setChip (String chipName) {
        this.chipName = chipName;
        chipNameTxt.setText(chipName);
    }

    public void setClock (boolean up) {
        clockUp = up;
        if(up)
            timeTxt.setText(timeTxt.getText() + "+");
    }

    public void setClocked (boolean clocked) {
        if(clocked)
            chipNameTxt.setText(chipName + " (Clocked) ");
        else
            chipNameTxt.setText(chipName);
    }


    public void setTime (int time) {
         if (clockUp)
            timeTxt.setText(String.valueOf(time) + "+");
        else
            timeTxt.setText(String.valueOf(time));
    }


    public void reset() {
        chipNameTxt.setText("");
        timeTxt.setText("0");
    }

    public void enableTime() {
        timeLbl.setEnabled(true);
        timeTxt.setEnabled(true);
    }

    public void disableTime() {
        timeLbl.setEnabled(false);
        timeTxt.setEnabled(false);
    }

    // Initializes this component.
    private void jbInit() {

        this.setLayout(null);

        chipNameLbl.setText("Chip Name :");
        chipNameLbl.setBounds(new Rectangle(11, 7, 74, 21));

        timeLbl.setBounds(new Rectangle(341, 8, 42, 21));
        timeLbl.setText("Time :");

        chipNameTxt.setBackground(SystemColor.info);
        chipNameTxt.setFont(Utilities.thinBigLabelsFont);
        chipNameTxt.setEditable(false);
        chipNameTxt.setHorizontalAlignment(SwingConstants.LEFT);
        chipNameTxt.setBounds(new Rectangle(89, 8, 231, 20));
        timeTxt.setBackground(SystemColor.info);
        timeTxt.setFont(Utilities.thinBigLabelsFont);
        timeTxt.setEditable(false);
        timeTxt.setBounds(new Rectangle(388, 8, 69, 20));

        this.add(chipNameTxt, null);
        this.add(chipNameLbl, null);
        this.add(timeLbl, null);
        this.add(timeTxt, null);

        setSize(483,37);
        setBorder(BorderFactory.createEtchedBorder());
    }
}
