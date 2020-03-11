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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * A combo box with a title.
 */
public class TitledComboBox extends JPanel {

    // The total height of this component
    private static final int TOTAL_HEIGHT = 37;

    // The height of the combo box only (without the title).
    private static final int COMBO_HEIGHT = 22;

    // The combo box
    private JComboBox combo;

    // The title
    private JLabel title;

    // The listeners to this combo box
    private LinkedList listeners;

    /**
     * Constructs a new TitledComboBox.
     */
    public TitledComboBox(String titleText, String toolTipText, String[] items, int width) {
        title = new JLabel(titleText);
        combo = new JComboBox(items);
        combo.setToolTipText(toolTipText);

        Dimension d = new Dimension(width, TOTAL_HEIGHT);
        setMaximumSize(d);
        setPreferredSize(d);
        setSize(d);

        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(combo, BorderLayout.SOUTH);

        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notifyListeners(e);
            }
        });

        title.setFont(Utilities.thinLabelsFont);

        combo.setFont(Utilities.thinLabelsFont);
        combo.setPreferredSize(new Dimension(width, COMBO_HEIGHT));

        listeners = new LinkedList();
    }

    /**
     * Registers the given listener.
     */
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    // Notifies the registered listeners on an action in the combo box.
    private void notifyListeners(ActionEvent e) {
        Iterator iter = listeners.iterator();
        while(iter.hasNext())
            ((ActionListener)iter.next()).actionPerformed(e);
    }

    /**
     * Returns true if the given index is the selected one.
     */
    public boolean isSelectedIndex(int index) {
        return combo.getSelectedIndex() == index;
    }

    /**
     * Return true if the given item is the slected one.
     */
    public boolean isSelectedItem(String item) {
        return combo.getSelectedItem().equals(item);
    }

    /**
     * Returns the selected index.
     */
    public int getSelectedIndex() {
        return combo.getSelectedIndex();
    }

    /**
     * Sets the selected index.
     */
    public void setSelectedIndex(int index) {
        combo.setSelectedIndex(index);
    }

    public void setEnabled(boolean value) {
        combo.setEnabled(value);
        title.setEnabled(value);
    }
}
