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
 * A built-in implementation for the Output class of the Jack OS.
 */

public class Output extends JackOSClass {
	private static final int N_COLS = SCREEN_WIDTH/8;
	private static final int N_ROWS = SCREEN_HEIGHT/11;
	private static final int START_ADDRESS = SCREEN_WIDTH>>4;

    static int wordInLine, address;
    static boolean firstInWord;
	static int map[][];

	public static void init() {
        firstInWord = true;
        address = START_ADDRESS;
        wordInLine = 0;
		map = new int[127][11];
        create(0, 63, 63, 63, 63, 63, 63, 63, 63, 63, 0, 0);
        create(32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        create(33, 12, 30, 30, 30, 12, 12, 0, 12, 12, 0, 0);
        create(34, 54, 54, 20, 0, 0, 0, 0, 0, 0, 0, 0);
        create(35, 0, 18, 18, 63, 18, 18, 63, 18, 18, 0, 0);
        create(36, 12, 30, 51, 3, 30, 48, 51, 30, 12, 12, 0);
        create(37, 0, 0, 35, 51, 24, 12, 6, 51, 49, 0, 0);
        create(38, 12, 30, 30, 12, 54, 27, 27, 27, 54, 0, 0);
        create(39, 12, 12, 6, 0, 0, 0, 0, 0, 0, 0, 0);
        create(40, 24, 12, 6, 6, 6, 6, 6, 12, 24, 0, 0);
        create(41, 6, 12, 24, 24, 24, 24, 24, 12, 6, 0, 0);
        create(42, 0, 0, 0, 51, 30, 63, 30, 51, 0, 0, 0);
        create(43, 0, 0, 0, 12, 12, 63, 12, 12, 0, 0, 0);
        create(44, 0, 0, 0, 0, 0, 0, 0, 12, 12, 6, 0);
        create(45, 0, 0, 0, 0, 0, 63, 0, 0, 0, 0, 0);
        create(46, 0, 0, 0, 0, 0, 0, 0, 12, 12, 0, 0);
        create(47, 0, 0, 32, 48, 24, 12, 6, 3, 1, 0, 0);
        create(48, 12, 30, 51, 51, 51, 51, 51, 30, 12, 0, 0);
        create(49, 12, 14, 15, 12, 12, 12, 12, 12, 63, 0, 0);
        create(50, 30, 51, 48, 24, 12, 6, 3, 51, 63, 0, 0);
        create(51, 30, 51, 48, 48, 28, 48, 48, 51, 30, 0, 0);
        create(52, 16, 24, 28, 26, 25, 63, 24, 24, 60, 0, 0);
        create(53, 63, 3, 3, 31, 48, 48, 48, 51, 30, 0, 0);
        create(54, 28, 6, 3, 3, 31, 51, 51, 51, 30, 0, 0);
        create(55, 63, 49, 48, 48, 24, 12, 12, 12, 12, 0, 0);
        create(56, 30, 51, 51, 51, 30, 51, 51, 51, 30, 0, 0);
        create(57, 30, 51, 51, 51, 62, 48, 48, 24, 14, 0, 0);
        create(58, 0, 0, 12, 12, 0, 0, 12, 12, 0, 0, 0);
        create(59, 0, 0, 12, 12, 0, 0, 12, 12, 6, 0, 0);
        create(60, 0, 0, 24, 12, 6, 3, 6, 12, 24, 0, 0);
        create(61, 0, 0, 0, 63, 0, 0, 63, 0, 0, 0, 0);
        create(62, 0, 0, 3, 6, 12, 24, 12, 6, 3, 0, 0);
        create(64, 30, 51, 51, 59, 59, 59, 27, 3, 30, 0, 0);
        create(63, 30, 51, 51, 24, 12, 12, 0, 12, 12, 0, 0);
        create(65, 12, 30, 51, 51, 63, 51, 51, 51, 51, 0, 0);
        create(66, 31, 51, 51, 51, 31, 51, 51, 51, 31, 0, 0);
        create(67, 28, 54, 35, 3, 3, 3, 35, 54, 28, 0, 0);
        create(68, 15, 27, 51, 51, 51, 51, 51, 27, 15, 0, 0);
        create(69, 63, 51, 35, 11, 15, 11, 35, 51, 63, 0, 0);
        create(70, 63, 51, 35, 11, 15, 11, 3, 3, 3, 0, 0);
        create(71, 28, 54, 35, 3, 59, 51, 51, 54, 44, 0, 0);
        create(72, 51, 51, 51, 51, 63, 51, 51, 51, 51, 0, 0);
        create(73, 30, 12, 12, 12, 12, 12, 12, 12, 30, 0, 0);
        create(74, 60, 24, 24, 24, 24, 24, 27, 27, 14, 0, 0);
        create(75, 51, 51, 51, 27, 15, 27, 51, 51, 51, 0, 0);
        create(76, 3, 3, 3, 3, 3, 3, 35, 51, 63, 0, 0);
        create(77, 33, 51, 63, 63, 51, 51, 51, 51, 51, 0, 0);
        create(78, 51, 51, 55, 55, 63, 59, 59, 51, 51, 0, 0);
        create(79, 30, 51, 51, 51, 51, 51, 51, 51, 30, 0, 0);
        create(80, 31, 51, 51, 51, 31, 3, 3, 3, 3, 0, 0);
        create(81, 30, 51, 51, 51, 51, 51, 63, 59, 30, 48, 0);
        create(82, 31, 51, 51, 51, 31, 27, 51, 51, 51, 0, 0);
        create(83, 30, 51, 51, 6, 28, 48, 51, 51, 30, 0, 0);
        create(84, 63, 63, 45, 12, 12, 12, 12, 12, 30, 0, 0);
        create(85, 51, 51, 51, 51, 51, 51, 51, 51, 30, 0, 0);
        create(86, 51, 51, 51, 51, 51, 30, 30, 12, 12, 0, 0);
        create(87, 51, 51, 51, 51, 51, 63, 63, 63, 18, 0, 0);
        create(88, 51, 51, 30, 30, 12, 30, 30, 51, 51, 0, 0);
        create(89, 51, 51, 51, 51, 30, 12, 12, 12, 30, 0, 0);
        create(90, 63, 51, 49, 24, 12, 6, 35, 51, 63, 0, 0);
        create(91, 30, 6, 6, 6, 6, 6, 6, 6, 30, 0, 0);
        create(92, 0, 0, 1, 3, 6, 12, 24, 48, 32, 0, 0);
        create(93, 30, 24, 24, 24, 24, 24, 24, 24, 30, 0, 0);
        create(94, 8, 28, 54, 0, 0, 0, 0, 0, 0, 0, 0);
        create(95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 0);
        create(96, 6, 12, 24, 0, 0, 0, 0, 0, 0, 0, 0);
        create(97, 0, 0, 0, 14, 24, 30, 27, 27, 54, 0, 0);
        create(98, 3, 3, 3, 15, 27, 51, 51, 51, 30, 0, 0);
        create(99, 0, 0, 0, 30, 51, 3, 3, 51, 30, 0, 0);
        create(100, 48, 48, 48, 60, 54, 51, 51, 51, 30, 0, 0);
        create(101, 0, 0, 0, 30, 51, 63, 3, 51, 30, 0, 0);
        create(102, 28, 54, 38, 6, 15, 6, 6, 6, 15, 0, 0);
        create(103, 0, 0, 30, 51, 51, 51, 62, 48, 51, 30, 0);
        create(104, 3, 3, 3, 27, 55, 51, 51, 51, 51, 0, 0);
        create(105, 12, 12, 0, 14, 12, 12, 12, 12, 30, 0, 0);
        create(106, 48, 48, 0, 56, 48, 48, 48, 48, 51, 30, 0);
        create(107, 3, 3, 3, 51, 27, 15, 15, 27, 51, 0, 0);
        create(108, 14, 12, 12, 12, 12, 12, 12, 12, 30, 0, 0);
        create(109, 0, 0, 0, 29, 63, 43, 43, 43, 43, 0, 0);
        create(110, 0, 0, 0, 29, 51, 51, 51, 51, 51, 0, 0);
        create(111, 0, 0, 0, 30, 51, 51, 51, 51, 30, 0, 0);
        create(112, 0, 0, 0, 30, 51, 51, 51, 31, 3, 3, 0);
        create(113, 0, 0, 0, 30, 51, 51, 51, 62, 48, 48, 0);
        create(114, 0, 0, 0, 29, 55, 51, 3, 3, 7, 0, 0);
        create(115, 0, 0, 0, 30, 51, 6, 24, 51, 30, 0, 0);
        create(116, 4, 6, 6, 15, 6, 6, 6, 54, 28, 0, 0);
        create(117, 0, 0, 0, 27, 27, 27, 27, 27, 54, 0, 0);
        create(118, 0, 0, 0, 51, 51, 51, 51, 30, 12, 0, 0);
        create(119, 0, 0, 0, 51, 51, 51, 63, 63, 18, 0, 0);
        create(120, 0, 0, 0, 51, 30, 12, 12, 30, 51, 0, 0);
        create(121, 0, 0, 0, 51, 51, 51, 62, 48, 24, 15, 0);
        create(122, 0, 0, 0, 63, 27, 12, 6, 51, 63, 0, 0);
        create(123, 56, 12, 12, 12, 7, 12, 12, 12, 56, 0, 0);
        create(124, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0, 0);
        create(125, 7, 12, 12, 12, 56, 12, 12, 12, 7, 0, 0);
        create(126, 38, 45, 25, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    private static void create(int c, int line0, int line1, int line2,
							   int line3, int line4, int line5,
							   int line6, int line7, int line8,
   							   int line9, int line10) {
        map[c][0] = line0;
        map[c][1] = line1;
        map[c][2] = line2;
        map[c][3] = line3;
        map[c][4] = line4;
        map[c][5] = line5;
        map[c][6] = line6;
        map[c][7] = line7;
        map[c][8] = line8;
        map[c][9] = line9;
        map[c][10] = line10;
    }

    private static void drawChar(int c) throws TerminateVMProgramThrowable {
		if (c < 32 || c >= 127) c = 0;
		int mask;
		int shift;
		if (firstInWord) {
			mask = 0xFF00;
			shift = 0;
		} else {
			mask = 0x00FF;
			shift = 8;
		}
        for (int i=0, j=address; i<11; ++i, j+=(SCREEN_WIDTH>>4)) {
			writeMemory(SCREEN_START_ADDRESS+j,
						(readMemory(SCREEN_START_ADDRESS+j)&mask) |
						(map[c][i]<<shift));
        }
    }

    public static void moveCursor(short row, short col)
			throws TerminateVMProgramThrowable  {
        if (row < 0 || row >= N_ROWS || col < 0 || col >= N_COLS) {
			callFunction("Sys.error", OUTPUT_MOVECURSOR_ILLEGAL_POSITION);
        }
        wordInLine = col / 2;
        address = START_ADDRESS + (row * (11*(SCREEN_WIDTH>>4))) + wordInLine;
        firstInWord = ((col&1) == 0);
        drawChar(' ');
    }

    public static void printChar(short c) throws TerminateVMProgramThrowable {
        if (c == NEWLINE_KEY) {
            println();
        } else if (c == BACKSPACE_KEY) {
			backSpace();
		} else {
			drawChar(c);
			if (!firstInWord) {
				++wordInLine;
				++address;
				if (wordInLine == (SCREEN_WIDTH>>4)) {
					println();
				} else {
					firstInWord = !firstInWord;
				}
			} else {
				firstInWord = !firstInWord;
			}
		}
    }

    public static void printString(short s) throws TerminateVMProgramThrowable {
		int l = callFunction("String.length", s);
		for (int i=0; i<l; ++i) {
			printChar(callFunction("String.charAt", s, i));
		}
    }

    public static void printInt(short i) throws TerminateVMProgramThrowable {
        StringCharacterIterator iter = new StringCharacterIterator(""+i);
        for (iter.first(); iter.current() != CharacterIterator.DONE;
			 iter.next()) {
			printChar((short)iter.current());
		}
    }

    public static void println() throws TerminateVMProgramThrowable {
        address = (address + 11*(SCREEN_WIDTH>>4)) - wordInLine;
        wordInLine = 0;
        firstInWord = true;
        if (address == START_ADDRESS+N_ROWS*11*(SCREEN_WIDTH>>4)) {
            address = START_ADDRESS;
        }
    }

    public static void backSpace() throws TerminateVMProgramThrowable {
        if (firstInWord) {
            if (wordInLine > 0) {
                --wordInLine;
				--address;
			} else {
				wordInLine = (SCREEN_WIDTH>>4)-1;
				if (address == START_ADDRESS) {
					address = START_ADDRESS+N_ROWS*11*(SCREEN_WIDTH>>4);
				}
				address -= 10*(SCREEN_WIDTH>>4) + 1;
			}
			firstInWord = false;
        } else {
            firstInWord = true;
        }
        drawChar(' ');
    }

}
