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

package Hack.HardwareSimulator;

import Hack.Controller.*;
import Hack.Gates.*;
import java.io.*;

/**
 * A HackController for the Hardware Simulator.
 */
public class HardwareSimulatorController extends HackController {

    /**
     * Constructs a new HardwareSimulatorController with the given HardwareSimulatorControllerGUI
     * component, the HardwareSimulator and the default script file for this simulator.
     * The gui is optional.
     */
    public HardwareSimulatorController(HardwareSimulatorControllerGUI gui,
                                       HardwareSimulator simulator, String defaultScriptName)
     throws ScriptException, ControllerException  {
        super(gui, simulator, defaultScriptName);

        gui.disableEval();
        gui.disableTickTock();
    }

    protected void updateProgramFile(String programFileName) {
        super.updateProgramFile(programFileName);
        File file = (new File(programFileName)).getParentFile();
        GatesManager.getInstance().setWorkingDir(file);
    }

    /**
     * Executes an unknown controller action event.
     */
    protected void doUnknownAction(byte action, Object data) {
        switch (action) {
            case HardwareSimulatorControllerEvent.CHIP_CHANGED:
                File file = (File)data;
                updateProgramFile(file.getPath());
                if (!singleStepLocked) // new HDL was loaded manually
                    reloadDefaultScript();

                LoadChipTask loadChipTask = new LoadChipTask(file.getPath());
                Thread t = new Thread(loadChipTask);
                t.start();
                break;

            case HardwareSimulatorControllerEvent.EVAL_CLICKED:
                ((HardwareSimulator)simulator).runEvalTask();
                break;

            case HardwareSimulatorControllerEvent.TICKTOCK_CLICKED:
                ((HardwareSimulator)simulator).runTickTockTask();
                break;

            case HardwareSimulatorControllerEvent.DISABLE_EVAL:
                ((HardwareSimulatorControllerGUI)gui).disableEval();
                break;

            case HardwareSimulatorControllerEvent.ENABLE_EVAL:
                ((HardwareSimulatorControllerGUI)gui).enableEval();
                break;

            case HardwareSimulatorControllerEvent.DISABLE_TICKTOCK:
                ((HardwareSimulatorControllerGUI)gui).disableTickTock();
                ((HardwareSimulatorControllerGUI)gui).disableTickTock();
                break;

            case HardwareSimulatorControllerEvent.ENABLE_TICKTOCK:
                ((HardwareSimulatorControllerGUI)gui).enableTickTock();
                break;

        }
    }

    class LoadChipTask implements Runnable {

        private String chipName;

        public LoadChipTask(String chipName) {
            this.chipName = chipName;
        }

        public void run() {
            try {
                ((HardwareSimulator)simulator).loadGate(chipName, true);
            } catch (GateException ge) {
                gui.displayMessage(ge.getMessage(), true);
            }
        }
    }

}
