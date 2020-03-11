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

package builtInVMCode;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import Hack.VMEmulator.BuiltInVMClass;
import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A common superclass for all of the built-in implementations for the various
 * Jack OS classes. Contains common functions and definitions.
 */

class JackOSClass extends BuiltInVMClass {

	/* Jack OS Error Codes */
	public static final short SYS_WAIT_NEGATIVE_DURATION = 1;
	public static final short ARRAY_NEW_NONPOSITIVE_SIZE = 2;
	public static final short MATH_DIVIDE_ZERO = 3;
	public static final short MATH_SQRT_NEGATIVE = 4;
	public static final short MEMORY_ALLOC_NONPOSITIVE_SIZE = 5;
	public static final short MEMORY_ALLOC_HEAP_OVERFLOW = 6;
	public static final short SCREEN_DRAWPIXEL_ILLEGAL_COORDS = 7;
	public static final short SCREEN_DRAWLINE_ILLEGAL_COORDS = 8;
	public static final short SCREEN_DRAWRECTANGLE_ILLEGAL_COORDS = 9;
	public static final short SCREEN_DRAWCIRCLE_ILLEGAL_CENTER = 12;
	public static final short SCREEN_DRAWCIRCLE_ILLEGAL_RADIUS = 13;
	public static final short STRING_NEW_NEGATIVE_LENGTH = 14;
	public static final short STRING_CHARAT_ILLEGAL_INDEX = 15;
	public static final short STRING_SETCHARAT_ILLEGAL_INDEX = 16;
	public static final short STRING_APPENDCHAR_FULL = 17;
	public static final short STRING_ERASELASTCHAR_EMPTY = 18;
	public static final short STRING_SETINT_INSUFFICIENT_CAPACITY = 19;
	public static final short OUTPUT_MOVECURSOR_ILLEGAL_POSITION = 20;

	/**
	 * Converts a java string to a Jack String by using whatever implementation
	 * of the Jack class String is available (String.vm if available, else
	 * built-in): Constructs a string using String.new and then fills it
	 * using String.appendChar.
	 * Returns the VM address to the Jack String.
	 */
	public static short javaStringToJackStringUsingVM(java.lang.String javaStr) 
			throws TerminateVMProgramThrowable {
		if (javaStr.length() == 0) {
			return callFunction("String.new", 1);
		}
		short jackStr = callFunction("String.new", javaStr.length());
        StringCharacterIterator i = new StringCharacterIterator(javaStr);
        for (i.first(); i.current() != CharacterIterator.DONE; i.next()) {
			callFunction("String.appendChar", jackStr, i.current());
		}
		return jackStr;
	}

	// Not Used
	/**
	 * Converts a Jack string to a Java String by using whatever implementation
	 * of the Jack class String is available (String.vm if available, else
	 * built-in) calling String.length and then consecutively calling 
	 * String.getChar
	 */
	/*
	public static java.lang.String jackStringToJavaStringUsingVM(short jackStr) 
			throws TerminateVMProgramThrowable {
		StringBuffer javaStr = new StringBuffer();
		int l = callFunction("String.length", jackStr);
		for (int i=0; i<l; ++i) {
			javaStr.append((char)callFunction("String.charAt", jackStr, i));
		}
		return javaStr.toString();
	}*/

	/**
	 * Converts a java string to a Javk Int according to the conversion
	 * of the Jack OS API: converts until the first non-digit character in
	 * the line (a - as a first character os allowed and is not considered
	 * such a non-digit character).
	 */
	public static short javaStringToInt(java.lang.String str) {
        StringCharacterIterator i = new StringCharacterIterator(str);
		i.first();
		boolean neg = false;
		if (i.current() == '-') {
			neg = true;
			i.next();
		}
		int value = 0;
		for (;i.current() != CharacterIterator.DONE; i.next()) {
			char c = i.current();
			if (i.current() < '0' || c > '9')
				break;
			value = value*10 + (c-'0');
		}
		if (neg) {
			return (short)-value;
		} else {
			return (short)value;
		}
	}
}
