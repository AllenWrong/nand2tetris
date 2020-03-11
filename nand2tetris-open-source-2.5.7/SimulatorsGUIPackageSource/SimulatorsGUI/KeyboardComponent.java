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

import Hack.CPUEmulator.*;
import HackGUI.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A keyboard GUI component. Receives key input by using key events.
 */
public class KeyboardComponent extends JPanel implements KeyboardGUI {

    // The icon of the keyboard.
    private ImageIcon keyboardIcon = new ImageIcon(Utilities.imagesDir + "keyboard.gif");

    // The text field on which the letter are appearing.
    private JTextField keyNameText = new JTextField();

    // The keyboard's button.
    private JButton keyButton = new JButton();

    /**
     * Constructs a new keyboard component.
     */
    public KeyboardComponent() {
        jbInit();
    }

    /**
     * Displayes the given key name.
     */
    public void setKey(String keyName) {
        keyNameText.setText(keyName);
    }

    /**
     * Clears the key display.
     */
    public void clearKey() {
        keyNameText.setText("");
    }

    /**
     * Resets the contents of this KeyboardComponent.
     */
    public void reset() {}

    // Initializes this component.
    private void jbInit() {
        keyNameText.setBounds(new Rectangle(258, 0, 258, 27));
        keyNameText.setEnabled(false);
        keyNameText.setFont(new Font("Times New Roman", 1, 14));
        keyNameText.setDisabledTextColor(Color.black);
        keyNameText.setEditable(false);
        keyNameText.setHorizontalAlignment(SwingConstants.CENTER);
        keyNameText.setBackground(SystemColor.info);
        this.setLayout(null);
        keyButton.setIcon(keyboardIcon);
        keyButton.setBounds(new Rectangle(0, 0, 258, 27));
        keyButton.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                keyButton_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                keyButton_focusLost(e);
            }
        });
        this.add(keyButton, null);
        this.add(keyNameText, null);

        setPreferredSize(new Dimension(516, 27));
        setSize(516, 27);
    }

    /**
     * Returns the keyboard's button.
     */
    public JComponent getKeyEventHandler() {
        return keyButton;
    }

    /**
     * Implements the action of gaining the focus (changing the background
     * of this component).
     */
    public void keyButton_focusGained(FocusEvent e) {
        keyButton.setBackground(UIManager.getColor("TextField.selectionBackground"));
    }

    /**
     * Implements the action of losing the focus (changing the background of
     * this component back to the original color).
     */
    public void keyButton_focusLost(FocusEvent e) {
        keyButton.setBackground(UIManager.getColor("Button.background"));
    }
}
