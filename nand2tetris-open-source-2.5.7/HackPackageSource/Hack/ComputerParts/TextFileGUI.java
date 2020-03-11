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

package Hack.ComputerParts;

/**
 * An interface for the GUI of a text file.
 */
public interface TextFileGUI extends ComputerPartGUI {

    /**
     * Registers the given TextFileEventListener as a listener to this GUI.
     */
    public void addTextFileListener(TextFileEventListener listener);

    /**
     * Un-registers the given TextFileEventListener from being a listener to this GUI.
     */
    public void removeTextFileListener(TextFileEventListener listener);

    /**
     * Notifies all the TextFileEventListeners on a change in the selected row by creating
     * an TextFileEvent (with the selected row string and index) and sending it using the
     * rowSelected method to all the listeners.
     */
    public void notifyTextFileListeners(String rowSrting, int rowIndex);

    /**
     * Sets the TextFile's contents with the given file.
     */
    public void setContents(String fileName);

    /**
     * Sets the contents of the text file with the given String array.
     */
    public void setContents(String[] text);

    /**
     * Adds the given line at the end of the text file.
     */
    public void addLine(String line);

    /**
     * Highlights the line with the given index. This adds to the current highlighted lines.
     * If clear is true, other highlights will be cleared.
     */
    public void addHighlight(int index, boolean clear);

    /**
     * Clears all the current highlights.
     */
    public void clearHighlights();

    /**
     * Puts an emphasis on the line with the given index. This adds to the current
     * emphasized lines.
     */
    public void addEmphasis(int index);

    /**
     * Removes the emphasis from the line with the given index. This removes the line
     * from the current emphasized lines.
     */
    public void removeEmphasis(int index);

    /**
     * Returns the line at the given index (assumes a legal index).
     */
    public String getLineAt(int index);

    /**
     * Replaces the line at the given index (assumes a legal index) with the given line.
     */
    public void setLineAt(int index, String line);

    /**
     * Returns the number of lines in the file.
     */
    public int getNumberOfLines();

    /**
     * Selects the commands in the range fromIndex..toIndex
     */
    public void select(int fromIndex, int toIndex);

    /**
     * Hides all selections.
     */
    public void hideSelect();
}
