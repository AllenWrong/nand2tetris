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

import Hack.VMEmulator.BuiltInVMClass;
import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A built-in implementation for the Keyboard class of the Jack OS.
 */

public class Keyboard extends JackOSClass {

	public static void init() { }

	public static char keyPressed() throws TerminateVMProgramThrowable {
		return (char)readMemory(KEYBOARD_ADDRESS);
	}

	public static char readChar() throws TerminateVMProgramThrowable {
		callFunction("Output.printChar", 0);
		char c = readCharNoEcho();
		callFunction("Output.printChar", BACKSPACE_KEY);
		callFunction("Output.printChar", c);
		return c;
	}

	public static short readLine(short message)
			throws TerminateVMProgramThrowable {
		return javaStringToJackStringUsingVM(readLineToJavaString(message));
	}

	public static short readInt(short message)
			throws TerminateVMProgramThrowable {
		return javaStringToInt(readLineToJavaString(message));
	}

	private static char readCharNoEcho() throws TerminateVMProgramThrowable {
		char current = 0, saved = 0;
		while (saved == 0 || current != 0) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) { }
			current = keyPressed();
			if (current != 0) {
				saved = current;
			}
		}
		return saved;
	}

	private static java.lang.String readLineToJavaString(short message)
			throws TerminateVMProgramThrowable {
		callFunction("Output.printString", message);
		StringBuffer s = new StringBuffer();
		char c;
		callFunction("Output.printChar", 0);
		while ((c=readCharNoEcho()) != NEWLINE_KEY) {
			if (c == BACKSPACE_KEY) {
				int deleteAt = s.length()-1;
				if (deleteAt >= 0) {
					callFunction("Output.printChar", BACKSPACE_KEY);
					callFunction("Output.printChar", c);
					callFunction("Output.printChar", 0);
					s.deleteCharAt(deleteAt);
				}
			} else {
				callFunction("Output.printChar", BACKSPACE_KEY);
				callFunction("Output.printChar", c);
				callFunction("Output.printChar", 0);
				s.append(c);
			}
		}
		callFunction("Output.printChar", BACKSPACE_KEY);
		callFunction("Output.printChar", c);
		return s.toString();
	}

}
