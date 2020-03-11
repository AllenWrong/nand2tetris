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

package Hack.Controller;

/**
 * A Hack Application. Creates a controller to control the given given simulator using the
 * given GUI components.
 * The createController method may be overwridden by simulators that inherit the
 * HackController.
 */
public abstract class HackApplication {

    /**
     * Constructs a new HackApplication with the given Hack simulator, the controller GUI
     * component, the simulator GUI component, the default
     * script name and the names of the help files.
     */
    public HackApplication(HackSimulator simulator, ControllerGUI controllerComponent,
                           HackSimulatorGUI simulatorComponent, String defaultScript,
                           String usageFileName, String aboutFileName) {
        try {
            simulatorComponent.setUsageFileName(usageFileName);
            simulatorComponent.setAboutFileName(aboutFileName);
            createController(simulator, controllerComponent, defaultScript);
        } catch (ScriptException se) {
            System.err.println(se.getMessage());
            System.exit(-1);
        } catch (ControllerException ce) {
            System.err.println(ce.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Creates the controller with the given simulator, controller gui component
     * and the default script.
     */
    protected void createController(HackSimulator simulator, ControllerGUI controllerComponent,
                                    String defaultScript)
     throws ScriptException, ControllerException {
        HackController c = new HackController(controllerComponent, simulator, defaultScript);
    }
}
