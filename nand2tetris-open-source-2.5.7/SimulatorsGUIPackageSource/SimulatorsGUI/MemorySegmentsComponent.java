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

import javax.swing.*;
import java.awt.*;
import HackGUI.*;

/**
 * This Panel contains six MemorySegmentComponents: static, local, arg,
 * this, that, and temp - and provides the split pane feature between
 * them.
 */
public class MemorySegmentsComponent extends JPanel {

    // The spllit pane containing static and local.
    private JSplitPane segmentsSplitPane1;

    // The split pane between arg and the previous split pane.
    private JSplitPane segmentsSplitPane2;

    // The split pane between this and the previous split pane.
    private JSplitPane segmentsSplitPane3;

    // The split pane between that and the previous split pane.
    private JSplitPane segmentsSplitPane4;

    // The split pane between temp and the previous split pane.
    private JSplitPane segmentsSplitPane5;

    // 'Static' memory segment
    private MemorySegmentComponent staticSegment;

    // 'Local' memory segment
    private MemorySegmentComponent localSegment;

    // 'Arg' memory segment
    private MemorySegmentComponent argSegment;

    // 'This' memory segment
    private MemorySegmentComponent thisSegment;

    // 'That' memory segment
    private MemorySegmentComponent thatSegment;

    // 'Temp' memory segment
    private MemorySegmentComponent tempSegment;

    /**
     * Constructs a new MemorySegmentsComponent.
     */
    public MemorySegmentsComponent() {
        // creating the segments and giving them names.
        staticSegment = new MemorySegmentComponent();
        staticSegment.setSegmentName("Static");
        localSegment = new MemorySegmentComponent();
        localSegment.setSegmentName("Local");
        argSegment = new MemorySegmentComponent();
        argSegment.setSegmentName("Argument");
        thisSegment = new MemorySegmentComponent();
        thisSegment.setSegmentName("This");
        thatSegment = new MemorySegmentComponent();
        thatSegment.setSegmentName("That");
        tempSegment = new MemorySegmentComponent();
        tempSegment.setSegmentName("Temp");

        // creating the split panes.
        segmentsSplitPane5 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, thatSegment, tempSegment);
        segmentsSplitPane4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, thisSegment, segmentsSplitPane5);
        segmentsSplitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, argSegment, segmentsSplitPane4);
        segmentsSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, localSegment, segmentsSplitPane3);
        segmentsSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, staticSegment, segmentsSplitPane2);

        // providing a one touch expandable feature to the split panes.
        segmentsSplitPane1.setOneTouchExpandable(true);
        segmentsSplitPane2.setOneTouchExpandable(true);
        segmentsSplitPane3.setOneTouchExpandable(true);
        segmentsSplitPane4.setOneTouchExpandable(true);
        segmentsSplitPane5.setOneTouchExpandable(true);

        // disabling the automatic border of each one of the first four
        // split panes. enabling the border of the fifth one.
        segmentsSplitPane5.setBorder(null);
        segmentsSplitPane4.setBorder(null);
        segmentsSplitPane3.setBorder(null);
        segmentsSplitPane2.setBorder(null);

        segmentsSplitPane1.setDividerLocation(30 + staticSegment.getTable().getRowHeight() * 5);
        segmentsSplitPane2.setDividerLocation(30 + localSegment.getTable().getRowHeight() * 5);
        segmentsSplitPane3.setDividerLocation(30 + argSegment.getTable().getRowHeight() * 5);
        segmentsSplitPane4.setDividerLocation(30 + thisSegment.getTable().getRowHeight() * 5);
        segmentsSplitPane5.setDividerLocation(30 + thatSegment.getTable().getRowHeight() * 2);

        segmentsSplitPane1.setSize(new Dimension(195, 587));
        segmentsSplitPane1.setPreferredSize(new Dimension(195, 587));
    }

    /**
     * Returns the split pane which contains all of the other split peanes.
     */
    public JSplitPane getSplitPane() {
        return segmentsSplitPane1;
    }

    /**
     * Returns static memory segment.
     */
    public MemorySegmentComponent getStaticSegment() {
        return staticSegment;
    }
    /**
     * Returns local memory segment.
     */
    public MemorySegmentComponent getLocalSegment() {
        return localSegment;
    }

    /**
     * Returns arg memory segment.
     */
    public MemorySegmentComponent getArgSegment() {
        return argSegment;
    }

    /**
     * Returns this memory segment.
     */
    public MemorySegmentComponent getThisSegment() {
        return thisSegment;
    }

    /**
     * Returns that memory segment.
     */
    public MemorySegmentComponent getThatSegment() {
        return thatSegment;
    }

    /**
     * Returns temp memory segment.
     */
    public MemorySegmentComponent getTempSegment() {
        return tempSegment;
    }
}
