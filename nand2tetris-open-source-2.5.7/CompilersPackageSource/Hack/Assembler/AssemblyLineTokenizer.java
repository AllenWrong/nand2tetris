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

package Hack.Assembler;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.io.*;
import Hack.Translators.*;

/**
 * A tokenizer for lines of an Assembly program.
 */
public class AssemblyLineTokenizer extends LineTokenizer {

    /**
     * Constructs a new AssemblyLineTokenizer for the given line.
     */
    public AssemblyLineTokenizer(String line) throws IOException {
		// Remove spaces from line. This needs to be done here
		// manually and not via whitespaceChars(' ', ' ') because
		// A + 1 for example should be regarded as the SINGLE
		// token A+1.
        super(removeSpaces(line));

        resetSyntax();
        slashSlashComments(true);

        whitespaceChars(' ',' ');
        whitespaceChars('\n','\n');
        whitespaceChars('\r','\r');
        whitespaceChars('\t','\t');

        wordChars('0','9');
        wordChars('A','Z');
        wordChars('a','z');
        wordChars('_','_');
        wordChars('+','+');
        wordChars('-','-');
        wordChars('.','.');
        wordChars(':',':');
        wordChars('!','!');
        wordChars('&','&');
        wordChars('|','|');
        wordChars('$','$');

        nextToken();
    }

	/**
	 * Removes space characters from the given string.
	 */
	private static String removeSpaces(String line) {
		StringBuffer nospc = new StringBuffer();
        StringCharacterIterator i = new StringCharacterIterator(line);
        for (i.first(); i.current() != CharacterIterator.DONE; i.next()) {
			if (i.current() != ' ') {
				nospc.append(i.current());
			}
		}
		return nospc.toString();
	}

}
