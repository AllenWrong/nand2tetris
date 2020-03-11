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

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;

/**
 * A class of utility methods.
 */
public class Utilities {

    /**
     * The directory of the images.
     */
    public static final String imagesDir = "bin/images/";

    // The name of the font for values
    private static final String VALUE_FONT_NAME = "Monospaced";

    // The name of the font for labels
    private static final String LABEL_FONT_NAME = "Dialog";

    /**
     * The regular font for values.
     */
    public static final Font valueFont = new FontUIResource(VALUE_FONT_NAME, 0, 12);

    /**
     * The bold font for values.
     */
    public static final Font boldValueFont = new FontUIResource(VALUE_FONT_NAME, 1, 12);

    /**
     * The big font for values.
     */
    public static final Font bigBoldValueFont = new FontUIResource(VALUE_FONT_NAME, 1, 13);

    /**
     * The regular font for labels.
     */
    public static final Font labelsFont = new FontUIResource(LABEL_FONT_NAME, 1, 12);

    /**
     * The thin font for labels.
     */
    public static final Font thinLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, 12);

    /**
     * The small font for labels.
     */
    public static final Font smallLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, 11);

    /**
     * The big font for labels.
     */
    public static final Font bigLabelsFont = new FontUIResource(LABEL_FONT_NAME, 1, 14);

    /**
     * The thin big font for labels.
     */
    public static final Font thinBigLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, 14);

    /**
     * The font of the status line.
     */
    public static final Font statusLineFont = new FontUIResource(LABEL_FONT_NAME, 1, 16);

    /**
     * Returns the location of the given bottom component relative to its given top level ancestor.
     */
    public static Point getTopLevelLocation(Component top, Component bottom) {
        Point point = new Point();
        Component c = bottom;
        while (c.getParent() != null && c != top) {
            point.x += c.getLocation().getX();
            point.y += c.getLocation().getY();
            c = c.getParent();
        }

        return point;
    }

    /**
     * Scrolls the given table such that the given row will be centered.
     * Also required are the containing panel and the number of visible rows in the table.
     */
    public static void tableCenterScroll(JPanel panel, JTable table, int row) {
        JScrollPane scrollPane = (JScrollPane)table.getParent().getParent();
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        int beforeScrollValue = bar.getValue();
        Rectangle r = table.getCellRect(row, 0, true);
        table.scrollRectToVisible(r);
        panel.repaint();
        int afterScrollValue = bar.getValue();
        double visibleRowsCount = computeVisibleRowsCount(table);

        // The scroller moved down
        if (afterScrollValue > beforeScrollValue) {
            Rectangle newRectangle = table.getCellRect((int)(Math.min(row + visibleRowsCount / 2,table.getRowCount()-1)) , 0, true);
            table.scrollRectToVisible(newRectangle);
            panel.repaint();
        }
        // The scroller moved up.
        else if (afterScrollValue < beforeScrollValue){
            Rectangle newRectangle = table.getCellRect((int)(Math.max(row - visibleRowsCount / 2,0)) , 0, true);
            table.scrollRectToVisible(newRectangle);
            panel.repaint();
        }
    }

    /**
     * Returns the number of visible rows in the given table.
     */
    public static double computeVisibleRowsCount(JTable table) {
        return table.getParent().getBounds().getHeight() / table.getRowHeight();
    }
}
