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

import Hack.Controller.*;
import javax.swing.*;
import java.awt.*;

/**
 * The GUI component of a Hack Simulator.
 */
public abstract class HackSimulatorComponent extends JPanel implements HackSimulatorGUI {

    // The current additional display
    protected JComponent currentAdditionalDisplay = null;

    // The names of the help files
    protected String usageFileName, aboutFileName;

    public void setAdditionalDisplay (JComponent additionalComponent) {
        if(currentAdditionalDisplay != null) {
            remove(currentAdditionalDisplay);
        }
        JComponent c = additionalComponent;
        currentAdditionalDisplay = additionalComponent;

        if (additionalComponent != null) {
            additionalComponent.setLocation(getAdditionalDisplayLocation());
            add(additionalComponent, 1);
            additionalComponent.revalidate();
        }

        revalidate();
        repaint();
    }

    /**
     * Returns the location on the simulator panel of the additional display.
     */
    protected abstract Point getAdditionalDisplayLocation();

    public void setUsageFileName(String fileName) {
        usageFileName = fileName;
    }

    public void setAboutFileName(String fileName) {
        aboutFileName = fileName;
    }

    public String getUsageFileName() {
        return usageFileName;
    }

    public String getAboutFileName() {
        return aboutFileName;
    }

}
