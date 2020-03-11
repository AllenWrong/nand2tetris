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

import Hack.Gates.*;
import javax.swing.*;
import java.awt.*;

/**
 * A GUI for displaying gates.
 */
public class GatesPanel implements GatesPanelGUI {

    // two panels with different layouts.
    private JPanel nullLayoutGatesPanel;
    private JPanel flowLayoutGatesPanel;

    // true if the current layout is flow layout.
    private boolean flowLayout = false;

    /**
     * Constructs a new GatesPanel.
     */
    public GatesPanel() {
        nullLayoutGatesPanel = new JPanel();
        flowLayoutGatesPanel = new JPanel();
        nullLayoutGatesPanel.setLayout(null);
        flowLayoutGatesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
    }

    /**
     * Adds the given gate component to the gates panel.
     */
    public void addGateComponent(Component gateComponent) {
        flowLayoutGatesPanel.add(gateComponent);
        if(flowLayout) {
            flowLayoutGatesPanel.revalidate();
            flowLayoutGatesPanel.repaint();
        }
        else  {
            Component[] components = nullLayoutGatesPanel.getComponents();
            for(int i=0; i<components.length; i++) {
                Rectangle componentBounds = components[i].getBounds();
                int x1 = (int)componentBounds.getX();
                int y1 = (int)componentBounds.getY();
                int x2 = (int)(componentBounds.getX() + componentBounds.getWidth() -1);
                int y2 = (int)(componentBounds.getY() + componentBounds.getHeight() -1);
                if(!(gateComponent.getY() > y2 || gateComponent.getX() > x2 ||
                     gateComponent.getY() + gateComponent.getHeight()-1 < y1  ||
                     gateComponent.getX() + gateComponent.getWidth()-1 < x1)) {

                    flowLayout = true;
                    break;
                }
            }
            if(!flowLayout) {
                nullLayoutGatesPanel.add(gateComponent);
                nullLayoutGatesPanel.revalidate();
                nullLayoutGatesPanel.repaint();
            }
        }
    }

    /**
     * Removes the given gate component from the gates panel.
     */
    public void removeGateComponent(Component gateComponent) {
        nullLayoutGatesPanel.remove(gateComponent);
        flowLayoutGatesPanel.remove(gateComponent);
        nullLayoutGatesPanel.revalidate();
        flowLayoutGatesPanel.revalidate();
        //getParent().repaint();
        nullLayoutGatesPanel.repaint();
        flowLayoutGatesPanel.repaint();
    }

    /**
     * Removes all the gate components from the gates panel.
     */
    public void removeAllGateComponents() {
        flowLayout = false;
        nullLayoutGatesPanel.removeAll();
        flowLayoutGatesPanel.removeAll();
        nullLayoutGatesPanel.revalidate();
        flowLayoutGatesPanel.revalidate();
        //getParent().repaint();
        nullLayoutGatesPanel.repaint();
        flowLayoutGatesPanel.repaint();
    }

    /**
     * Returns the gate panel.
     */
    public JPanel getGatesPanel() {
        if(flowLayout)
            return flowLayoutGatesPanel;
        else
            return nullLayoutGatesPanel;
    }
}
