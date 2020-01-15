// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/03/b/RAM4K.tst

load RAM4K.hdl,
output-file RAM4K.out,
compare-to RAM4K.cmp,
output-list time%S1.4.1 in%D1.6.1 load%B2.1.2 address%D2.4.2 out%D1.6.1;

set in 0,
set load 0,
set address 0,
tick,
output;
tock,
output;

set load 1,
tick,
output;
tock,
output;

set in 1111,
set load 0,
tick,
output;
tock,
output;

set load 1,
set address 1111,
tick,
output;
tock,
output;

set load 0,
set address 0,
tick,
output;
tock,
output;

set in 3513,
set address 3513,
tick,
output;
tock,
output;

set load 1,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 1111,
eval,
output;

set in 4095,
tick,
output;
tock,
output;

set load 1,
set address 4095,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 3513,
eval,
output;

set address 4095,
eval,
output;


set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
tick,
output,
tock,
output;
set address %B101010101010,
tick,
output,
tock,
output;
set address %B101010101011,
tick,
output,
tock,
output;
set address %B101010101100,
tick,
output,
tock,
output;
set address %B101010101101,
tick,
output,
tock,
output;
set address %B101010101110,
tick,
output,
tock,
output;
set address %B101010101111,
tick,
output,
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101000,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101000,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010101001,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101001,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010101011,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101011,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010101100,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101100,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010101101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010101110,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101110,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010101111,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;

set load 1,
set address %B101010101111,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B101010101000,
tick,
output;
tock,
output;
set address %B101010101001,
eval,
output;
set address %B101010101010,
eval,
output;
set address %B101010101011,
eval,
output;
set address %B101010101100,
eval,
output;
set address %B101010101101,
eval,
output;
set address %B101010101110,
eval,
output;
set address %B101010101111,
eval,
output;


set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
tick,
output,
tock,
output;
set address %B010101010101,
tick,
output,
tock,
output;
set address %B011101010101,
tick,
output,
tock,
output;
set address %B100101010101,
tick,
output,
tock,
output;
set address %B101101010101,
tick,
output,
tock,
output;
set address %B110101010101,
tick,
output,
tock,
output;
set address %B111101010101,
tick,
output,
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B000101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B000101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B001101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B001101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B010101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B011101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B011101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B100101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B100101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B101101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B110101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B110101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B111101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;

set load 1,
set address %B111101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B000101010101,
tick,
output;
tock,
output;
set address %B001101010101,
eval,
output;
set address %B010101010101,
eval,
output;
set address %B011101010101,
eval,
output;
set address %B100101010101,
eval,
output;
set address %B101101010101,
eval,
output;
set address %B110101010101,
eval,
output;
set address %B111101010101,
eval,
output;
