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

import java.util.EventObject;

/**
 * An event for notifying a FilesTypeListener on a change in the name of one
 * file or more.
 */
public class FilesTypeEvent extends EventObject {

    // The name of the first file
    private String firstFileName;

    // The name of the second file
    private String secondFileName;

    // The name of the third file
    private String thirdFileName;

    /**
     * Constructs a new FilesTypeEvent with the given source and the three names of files.
     */
    public FilesTypeEvent(Object source, String firstFileName, String secondFileName, String thirdFileName ) {
        super(source);
        this.firstFileName = firstFileName;
        this.secondFileName = secondFileName;
        this.thirdFileName = thirdFileName;
    }

    /**
     * Returns the name of the script file.
     */
    public String getFirstFile() {
        return firstFileName;
    }

    /**
     * Returns the name of the output file.
     */
    public String getSecondFile() {
        return secondFileName;
    }

    /**
     * Returns the name of the comparison file.
     */
    public String getThirdFile() {
        return thirdFileName;
    }
}
