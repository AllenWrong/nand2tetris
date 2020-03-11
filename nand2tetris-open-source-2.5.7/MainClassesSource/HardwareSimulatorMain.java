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

import Hack.Controller.*;
import Hack.HardwareSimulator.*;
import SimulatorsGUI.*;
import javax.swing.*;


/**
 * The Hardware Simulator.
 */
public class HardwareSimulatorMain
{
  /**
   * The command line Hardware Simulator program.
   */
  public static void main(String[] args) {
        if (args.length > 1)
            System.err.println("Usage: java HardwareSimulatorMain [script name]");
        else if (args.length == 0) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
            }

            HardwareSimulatorGUI simulatorGUI = new HardwareSimulatorComponent();
            HardwareSimulatorControllerGUI controllerGUI = new HardwareSimulatorControllerComponent();

            HardwareSimulatorApplication application =
                new HardwareSimulatorApplication(controllerGUI, simulatorGUI,
                                                 "bin/scripts/defaultHW.txt",
                                                 "bin/help/hwUsage.html", "bin/help/hwAbout.html");
        }
        else
            new HackController(new HardwareSimulator(), args[0]);
    }
}
