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

package Hack.Translators;

import java.io.*;

/**
 * A tokenizer for lines of a program.
 */
public class LineTokenizer extends StreamTokenizer {

    /**
     * Constructs a new LineTokenizer for the given line.
     * Throws IOException if an IO error occured
     */
    public LineTokenizer(String line) throws IOException {
        super(new StringReader(line));
        slashSlashComments(true);
    }

    /**
     * Advances the parser to the next token.
     * Throws HackTranslatorException if a token is expected and there are no
     * more tokens.
     */
    public void advance(boolean expectToken) throws IOException, HackTranslatorException {
        nextToken();

        if (expectToken && ttype == TT_EOF)
            throw new HackTranslatorException("Unexpected end of line", lineno());
    }

    /**
     * Return the current token, or null if no more tokens.
     */
    public String token() {
        String token = null;

        switch (ttype) {
            case StreamTokenizer.TT_NUMBER:
                token = String.valueOf((int)nval);
                break;
            case StreamTokenizer.TT_WORD:
                token = sval;
                break;
            default:
                token = String.valueOf((char)ttype);
                break;
        }

        return token;
    }

    /**
     * If the current token is a number, returns its numeric value, otherwise returns 0.
     */
    public int number() {
        if (ttype == TT_NUMBER)
            return (int)nval;
        else
            return 0;
    }

    /**
     * If the current token is a symbol, returns it. Otherwise returns 0.
     */
    public char symbol() {
        if (ttype > 0)
            return (char)ttype;
        else
            return 0;
    }

    /**
     * Checks whether the current token matches the given token
     */
    public boolean isToken(String token) {
        return token().equalsIgnoreCase(token);
    }

    /**
     * Checks whether the current token is a word.
     */
    public boolean isWord() {
        return ttype == TT_WORD;
    }

    /**
     * Checks whether the current token is a number.
     */
    public boolean isNumber() {
        return ttype == TT_NUMBER;
    }

    /**
     * Checks whether the current token is a symbol.
     */
    public boolean isSymbol() {
        return ttype > 0;
    }

    /**
     * Checks whether the tokenizer reached its end.
     */
    public boolean isEnd() {
        return (ttype == TT_EOF);
    }

    /**
     * Makes sure that there are no more tokens. If there are, throw an exception.
     */
    public void ensureEnd() throws HackTranslatorException, IOException {
        advance(false);

        if (!isEnd())
            throw new HackTranslatorException("end of line expected, '" + token() + "' is found");
    }

    /**
     * Returns true if the tokenizer contains the given token.
     * The tokenizer advances until its end to find the token.
     */
    public boolean contains(String token) throws IOException {
        boolean found = false;

        while (!found && !isEnd()) {
            if (!(found = token().equals(token))) {
                try {
                    advance(false);
                } catch (HackTranslatorException hte) {}
            }
        }

        return found;
    }
}
