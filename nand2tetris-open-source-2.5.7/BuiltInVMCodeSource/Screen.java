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
 * A built-in implementation for the Screen class of the Jack OS.
 */

public class Screen extends JackOSClass {
	private static boolean black;

	public static void init() {
		black = true;
    }

    public static void clearScreen() throws TerminateVMProgramThrowable {
		for (int i=SCREEN_START_ADDRESS; i<=SCREEN_END_ADDRESS; ++i) {
			writeMemory(i, 0);
		}
    }

    private static void updateLocation(int address, int mask)
			throws TerminateVMProgramThrowable {
		address += SCREEN_START_ADDRESS;
		int value = readMemory(address);
		if (black) {
			value |= mask;
		} else {
			value &= ~mask;
		}
		writeMemory(address, value);
    }

    public static void setColor(short color) {
		black = (color!=0);
    }

    public static void drawPixel(short x, short y)
			throws TerminateVMProgramThrowable {
		if (x < 0 || x >= SCREEN_WIDTH || y < 0 || y >= SCREEN_HEIGHT) {
			callFunction("Sys.error", SCREEN_DRAWPIXEL_ILLEGAL_COORDS);
		}
		updateLocation((y*SCREEN_WIDTH+x)>>4, 1<<(x&15));
    }

    private static void drawConditional(int x, int y, boolean exchange)
	   		throws TerminateVMProgramThrowable {
        if (exchange) {
			updateLocation((x*SCREEN_WIDTH+y)>>4, 1<<(y&15));
        } else {
			updateLocation((y*SCREEN_WIDTH+x)>>4, 1<<(x&15));
        }
    }

    public static void drawLine(short x1, short y1, short x2, short y2)
			throws TerminateVMProgramThrowable {
		if (x1 < 0 || x1 >= SCREEN_WIDTH || y1 < 0 || y1 >= SCREEN_HEIGHT ||
			x2 < 0 || x2 >= SCREEN_WIDTH || y2 < 0 || y2 >= SCREEN_HEIGHT) {
			callFunction("Sys.error", SCREEN_DRAWLINE_ILLEGAL_COORDS);
        }
        int dx = x2 - x1;
		if (dx < 0) dx = -dx;
        int dy = y2 - y1;
		if (dy < 0) dy = -dy;
        boolean loopOverY = (dx < dy);
        if ((loopOverY && (y2 < y1)) || ((!loopOverY) && (x2 < x1))) {
            short tmp = x1;
            x1 = x2;
            x2 = tmp;
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
		int endX;
		int deltaY;
		int x, y;
        if (loopOverY) {
            int tmp = dx;
            dx = dy;
            dy = tmp;
            x = y1;
            y = x1;
            endX = y2;
            deltaY = (x1 > x2)?-1:1;
        } else {
            x = x1;
            y = y1;
            endX = x2;
            deltaY = (y1 > y2)?-1:1;
        }
		drawConditional(x, y, loopOverY);
		// var = 2*x*dy - 2*(|y|-0.5)*dx
		// ==> 	var >=0 iff 2*x*dy >= 2*(|y|-0.5)*dx
		// iff dy/dx >= x/(|y|-0.5)
        int var = 2*dy-dx;
		int twody = 2*dy;
		int twodyMinusTwodx = twody-2*dx;
        while (x < endX) {
            if (var < 0) {
				var += twody;
            } else {
                var += twodyMinusTwodx;
				y += deltaY;
			}
			++x;
			drawConditional(x, y, loopOverY);
        }
    }

    public static void drawRectangle(short x1, short y1, short x2, short y2)
			throws TerminateVMProgramThrowable {
        if (x1 > x2 || y1 > y2 || x1 <0 || x2 >= SCREEN_WIDTH ||
			y1 < 0 || y2 >= SCREEN_HEIGHT) {
			callFunction("Sys.error", SCREEN_DRAWRECTANGLE_ILLEGAL_COORDS);
        }
        int x1Word = x1 >> 4;
        int x2Word = x2 >> 4;
		int firstWordMask = 0xFFFF<<(x1&15);
		int lastWordMask = 0xFFFF>>>(15-(x2&15));
        int address = (y1 * (SCREEN_WIDTH>>4)) + x1Word;
        int wordsDiff = x2Word - x1Word;
		if (wordsDiff == 0) {
			int mask = lastWordMask&firstWordMask;
			for (;y1<=y2;++y1,address+=(SCREEN_WIDTH>>4)) {
				updateLocation(address, lastWordMask&firstWordMask);
			}
		} else {
			for (;y1<=y2;++y1,address += (SCREEN_WIDTH>>4)-wordsDiff) {
				int lastAddressInLine = address + wordsDiff;
				updateLocation(address, firstWordMask);
				for (++address; address < lastAddressInLine; ++address) {
					updateLocation(address, 0xFFFF);
				}
				updateLocation(address, lastWordMask);
			}
		}
    }

    private static void drawTwoHorizontal(int y1, int y2,
										  int minX, int maxX)
			throws TerminateVMProgramThrowable {
		int minXWord = minX >> 4;
		int maxXWord = maxX >> 4;
		int firstWordMask = 0xFFFF<<(minX&15);
		int lastWordMask = 0xFFFF>>>(15-(maxX&15));
		int wordsDiff = maxXWord - minXWord;
		int address1 = (y1 * (SCREEN_WIDTH>>4)) + minXWord;
		int address2 = (y2 * (SCREEN_WIDTH>>4)) + minXWord;
		if (wordsDiff == 0) {
			updateLocation(address1, lastWordMask & firstWordMask);
			updateLocation(address2, lastWordMask & firstWordMask);
		} else {
			int lastAddressInLine1 = address1 + wordsDiff;
			updateLocation(address1, firstWordMask);
			updateLocation(address2, firstWordMask);
			for (++address1, ++address2;address1 < lastAddressInLine1;
				 ++address1, ++address2) {
				updateLocation(address1, 0xFFFF);
				updateLocation(address2, 0xFFFF);
			}
			updateLocation(address1, lastWordMask);
			updateLocation(address2, lastWordMask);
		}
    }

    public static void drawCircle(short x, short y, short radius)
			throws TerminateVMProgramThrowable {
        if (x < 0 || x >= SCREEN_WIDTH || y < 0 || y >= SCREEN_HEIGHT) {
			callFunction("Sys.error", SCREEN_DRAWCIRCLE_ILLEGAL_CENTER);
        }
        if (x-radius < 0 || x+radius >= SCREEN_WIDTH ||
            y-radius < 0 || y+radius >= SCREEN_HEIGHT) {
			callFunction("Sys.error", SCREEN_DRAWCIRCLE_ILLEGAL_RADIUS);
        }
		int delta1 = 0;
        int delta2 = radius;
        int var = 1 - radius;
        drawTwoHorizontal(y-delta2, y+delta2, x-delta1, x+delta1);
        drawTwoHorizontal(y-delta1, y+delta1, x-delta2, x+delta2);
        while (delta2 > delta1) {
            if (var < 0) {
                var += 2*delta1+3;
            } else {
                var += 2*(delta1-delta2)+5;
				--delta2;
            }
			++delta1;
			drawTwoHorizontal(y-delta2, y+delta2, x-delta1, x+delta1);
			drawTwoHorizontal(y-delta1, y+delta1, x-delta2, x+delta2);
        }
    }
}
