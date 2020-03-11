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
 * A built-in implementation for the Sys class of the Jack OS.
 */

public class Sys extends JackOSClass {

	public static void init() throws TerminateVMProgramThrowable {
		callFunction("Memory.init");
		callFunction("Math.init");
		callFunction("Screen.init");
		callFunction("Output.init");
		callFunction("Keyboard.init");
		callFunction("Main.main");
		infiniteLoop("Program Halted: Main.main finished execution");
	}

	public static void halt() throws TerminateVMProgramThrowable {
		infiniteLoop("Program Halted");
	}

	public static void wait(short duration) throws TerminateVMProgramThrowable {
		if (duration < 0) {
			error(SYS_WAIT_NEGATIVE_DURATION);
		}
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) { }
	}

	public static void error(short errorCode)
			throws TerminateVMProgramThrowable {
		callFunction("Output.printString",
					 javaStringToJackStringUsingVM("ERR"));
		callFunction("Output.printInt", errorCode);

		java.lang.String errorDescription = null;
		switch(errorCode) {
		case SYS_WAIT_NEGATIVE_DURATION:
			errorDescription = "Duration must be positive";
			break;
		case ARRAY_NEW_NONPOSITIVE_SIZE:
			errorDescription = "Array size must be positive";
			break;
		case MATH_DIVIDE_ZERO:
			errorDescription = "Division by zero";
			break;
		case MATH_SQRT_NEGATIVE:
			errorDescription ="Cannot compute square root of a negative number";
			break;
		case MEMORY_ALLOC_NONPOSITIVE_SIZE:
			errorDescription = "Allocated memory size must be positive";
			break;
		case MEMORY_ALLOC_HEAP_OVERFLOW:
			errorDescription = "Heap overflow";
			break;
		case SCREEN_DRAWPIXEL_ILLEGAL_COORDS:
			errorDescription = "Illegal pixel coordinated";
			break;
		case SCREEN_DRAWLINE_ILLEGAL_COORDS:
			errorDescription = "Illegal line coordinates";
			break;
		case SCREEN_DRAWRECTANGLE_ILLEGAL_COORDS:
			errorDescription = "Illegal rectangle coordinates";
			break;
		case SCREEN_DRAWCIRCLE_ILLEGAL_CENTER:
			errorDescription = "Illegal center coordinates";
			break;
		case SCREEN_DRAWCIRCLE_ILLEGAL_RADIUS:
			errorDescription = "Illegal radius";
			break;
		case STRING_NEW_NEGATIVE_LENGTH:
			errorDescription = "Maximum length must be non-negative";
			break;
		case STRING_CHARAT_ILLEGAL_INDEX:
			errorDescription = "String index out of bounds";
			break;
		case STRING_SETCHARAT_ILLEGAL_INDEX:
			errorDescription = "String index out of bounds";
			break;
		case STRING_APPENDCHAR_FULL:
			errorDescription = "String is full";
			break;
		case STRING_ERASELASTCHAR_EMPTY:
			errorDescription = "String is empty";
			break;
		case STRING_SETINT_INSUFFICIENT_CAPACITY:
			errorDescription = "Insufficient string capacity";
			break;
		case OUTPUT_MOVECURSOR_ILLEGAL_POSITION:
			errorDescription = "Illegal cursor location";
			break;
		}
		infiniteLoop("Program Halted: "+errorDescription);
	}
}
