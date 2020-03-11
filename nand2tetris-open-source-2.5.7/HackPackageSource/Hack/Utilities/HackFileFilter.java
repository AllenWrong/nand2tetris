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

package Hack.Utilities;

import java.io.*;

/**
 * A File filter that only accepts files with the extension that is given
 * in the constructor
 */
public class HackFileFilter implements FilenameFilter {

    // the accepted extension
    private String extension;

    /**
     * Constucts a new HackFileFilter with the given extension
     * @param extension The given extension
     */
    public HackFileFilter(String extension) {
        this.extension = extension;
    }

    public boolean accept(File directory, String name) {
        return name.endsWith(extension);
    }

    /**
     * Returns the accepted extension
     */
    public String getAcceptedExtension() {
        return extension;
    }
}
