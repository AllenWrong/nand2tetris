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

package Hack.HardwareSimulator;

import Hack.Gates.HDLTokenizer;
import java.io.*;
import Hack.Gates.*;

/**
 * An HDL tokenizer for a single string line.
 */
public class HDLLineTokenizer extends HDLTokenizer {

    /**
     * Constructs a new HDLLineTokenizer with the given string line.
     */
    public HDLLineTokenizer(String line) throws HDLException {
        Reader input = new BufferedReader(new InputStreamReader(new
             ByteArrayInputStream(line.getBytes())));

        try {
            initizalizeInput(input);
        } catch (IOException ioe) {
            throw new HDLException("Error while initializing HDL for reading");
        }
    }

    /**
     * Generates an HDLException with the given message.
     */
    public void HDLError(String message) throws HDLException {
        throw new HDLException(message);
    }
}
