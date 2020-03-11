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

package Hack.Controller;

import java.io.*;
import java.util.*;

/**
 * ScriptTokenizer object: Reads input from a reader and produces a stream of
 * tokens for the controller.
 */
public class ScriptTokenizer {

    // Token types

    /**
     * Keyword script token type
     */
    public static final int TYPE_KEYWORD		= 1;

    /**
     * Symbol script token type
     */
    public static final int TYPE_SYMBOL			= 2;

    /**
     * Identifier script token type
     */
    public static final int TYPE_IDENTIFIER		= 3;

    /**
     * Int const script token type
     */
    public static final int TYPE_INT_CONST		= 4;


    // Keywords of the scripting language

    /**
     * output-file script keyword
     */
    public static final int KW_OUTPUT_FILE		 = 1;

    /**
     * compare-to script keyword
     */
    public static final int KW_COMPARE_TO		 = 2;

    /**
     * output-list script keyword
     */
    public static final int KW_OUTPUT_LIST		 = 3;

    /**
     * output script keyword
     */
    public static final int KW_OUTPUT			 = 4;

    /**
     * breakpoint script keyword
     */
    public static final int KW_BREAKPOINT		 = 5;

    /**
     * clear-breakpoints script keyword
     */
    public static final int KW_CLEAR_BREAKPOINTS        = 6;

    /**
     * repeat script keyword
     */
    public static final int KW_REPEAT			 = 7;

    /**
     * while script keyword
     */
    public static final int KW_WHILE			 = 8;

    /**
     * echo script keyword
     */
    public static final int KW_ECHO			 = 9;

    /**
     * clear-echo script keyword
     */
    public static final int KW_CLEAR_ECHO	         = 10;

    // The parser
    private StreamTokenizer parser;

    // Hashtable containing the keywords of the language
    private Hashtable keywords;

    // Hashtable containing the symbols of the language
    private Hashtable symbols;

    // The type of the current token
    private int tokenType;

    // The type of the current keyword
    private int keyWordType;

    // The current symbol
    private char symbol;

    // The current int value
    private int intValue;

    // The current string value
    private String stringValue;

    // The current identifier
    private String identifier;

    // The current token
    private String currentToken;

    /**
     * Constructs a new ScriptTokenizer with the given input Reader.
     */
    public ScriptTokenizer(Reader input) throws ControllerException {
        try {
            parser = new StreamTokenizer(input);
            parser.parseNumbers();
            parser.slashSlashComments(true);
            parser.slashStarComments(true);
            parser.wordChars(':', ':');
            parser.wordChars('%', '%');
            parser.wordChars('[', '[');
            parser.wordChars(']', ']');
            parser.nextToken();
            initKeywords();
            initSymbols();
        } catch (IOException ioe) {
            throw new ControllerException("Error while initializing script for reading");
        }
    }

    /**
     * Advances the parser to the next token
     * May only be called when hasMoreToken() == true
     */
    public void advance() throws ControllerException {
        try {
            switch (parser.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    tokenType = TYPE_INT_CONST;
                    intValue = (int)parser.nval;
                    currentToken = String.valueOf(intValue);
                    break;
                case StreamTokenizer.TT_WORD:
                    currentToken = parser.sval;
                    Integer object = (Integer)keywords.get(currentToken);
                    if (object != null) {
                        tokenType = TYPE_KEYWORD;
                        keyWordType = object.intValue();
                    }
                    else {
                        tokenType = TYPE_IDENTIFIER;
                        identifier = currentToken;
                    }
                    break;
                default:
                    symbol = (char)parser.ttype;

                    // String quote
                    if (symbol == '"') {
                        currentToken = parser.sval;
                        tokenType = TYPE_IDENTIFIER;
                        identifier = currentToken;
                    }
                    else {
                        tokenType = TYPE_SYMBOL;
                        currentToken = String.valueOf(symbol);
                    }
                    break;
            }
            parser.nextToken();
        } catch (IOException ioe) {
            throw new ControllerException("Error while reading script");
        }
    }

    /**
     * Returns the current token as a String.
     */
    public String getToken() {
        return currentToken;
    }

    /**
     * Returns the current token type
     */
    public int getTokenType() {
        return tokenType;
    }

    /**
     * Returns the keyword type of the current token
     * May only be called when getTokenType() == KEYWORD
     */
    public int getKeywordType() {
        return keyWordType;
    }

    /**
     * Returns the symbol of the current token
     * May only be called when getTokenType() == SYMBOL
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns the int value of the current token
     * May only be called when getTokenType() == INT_CONST
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     * Returns the string value of the current token
     * May only be called when getTokenType() == STRING_CONST
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Returns the identifier value of the current token
     * May only be called when getTokenType() == IDENTIFIER
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns if there are more tokens in the stream
     */
    public boolean hasMoreTokens() {
        return (parser.ttype != parser.TT_EOF);
    }

    /**
     * Returns the current line number
     */
    public int getLineNumber() {
        return parser.lineno();
    }

    // Initializes the keywords hashtable
    private void initKeywords() {
        keywords = new Hashtable();
        keywords.put("output-file",new Integer(KW_OUTPUT_FILE));
        keywords.put("compare-to",new Integer(KW_COMPARE_TO));
        keywords.put("output-list",new Integer(KW_OUTPUT_LIST));
        keywords.put("output",new Integer(KW_OUTPUT));
        keywords.put("echo",new Integer(KW_ECHO));
        keywords.put("clear-echo",new Integer(KW_CLEAR_ECHO));
        keywords.put("breakpoint",new Integer(KW_BREAKPOINT));
        keywords.put("clear-breakpoints",new Integer(KW_CLEAR_BREAKPOINTS));
        keywords.put("repeat",new Integer(KW_REPEAT));
        keywords.put("while",new Integer(KW_WHILE));
    }

    // Initializes the symbols hashtable
    private void initSymbols() {
        symbols = new Hashtable();
        symbols.put("{","{");
        symbols.put("}","}");
        symbols.put(",",",");
        symbols.put(";",";");
        symbols.put("!","!");
        symbols.put("=","=");
        symbols.put(">",">");
        symbols.put("<","<");
    }
}
