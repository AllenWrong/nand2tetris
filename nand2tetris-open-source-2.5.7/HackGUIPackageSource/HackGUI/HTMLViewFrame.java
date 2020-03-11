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
import java.io.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

/**
 * A frame for viewing HTML files.
 */
public class HTMLViewFrame extends JFrame {

    // The scroll pane for this frame
    private JScrollPane scrollPane;

    // The editor pane for displaying the HTML file.
    private JEditorPane ep = new JEditorPane();

    /**
     * Constructs a new HTMLViewFrame for the given HTML file.
     */
    public HTMLViewFrame(String fileName) {
        setTitle("Help");
        ep.setEditable(false);
        ep.setContentType("text/html");

        try {
            ep.setPage("file:" + fileName);
        } catch (IOException ioe) {
            System.err.println("Error while reading file: " + fileName);
            System.exit(-1);
        }

        ep.addHyperlinkListener(new Hyperactive());
        scrollPane = new JScrollPane(ep);
        setBounds(30,44,750,460);
        setDefaultCloseOperation(1);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}

// Implements the HTML link properties.
class Hyperactive implements HyperlinkListener {

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                HTMLDocument doc = (HTMLDocument)pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                try {
                    pane.setPage(e.getURL());
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                    System.exit(0);
                }
            }
        }
    }
}
