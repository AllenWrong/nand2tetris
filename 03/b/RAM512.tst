// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/03/b/RAM512.tst

load RAM512.hdl,
output-file RAM512.out,
compare-to RAM512.cmp,
output-list time%S1.4.1 in%D1.6.1 load%B2.1.2 address%D2.3.2 out%D1.6.1;

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

set in 13099,
set load 0,
tick,
output;
tock,
output;

set load 1,
set address 130,
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

set in 4729,
set address 472,
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

set address 130,
eval,
output;

set in 5119,
tick,
output;
tock,
output;

set load 1,
set address 511,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 472,
eval,
output;

set address 511,
eval,
output;


set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
tick,
output,
tock,
output;
set address %B010101010,
tick,
output,
tock,
output;
set address %B010101011,
tick,
output,
tock,
output;
set address %B010101100,
tick,
output,
tock,
output;
set address %B010101101,
tick,
output,
tock,
output;
set address %B010101110,
tick,
output,
tock,
output;
set address %B010101111,
tick,
output,
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101000,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101000,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101001,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101001,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101011,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101011,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101100,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101100,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101110,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101110,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101111,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;

set load 1,
set address %B010101111,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B010101000,
tick,
output;
tock,
output;
set address %B010101001,
eval,
output;
set address %B010101010,
eval,
output;
set address %B010101011,
eval,
output;
set address %B010101100,
eval,
output;
set address %B010101101,
eval,
output;
set address %B010101110,
eval,
output;
set address %B010101111,
eval,
output;


set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
tick,
output,
tock,
output;
set address %B010101010,
tick,
output,
tock,
output;
set address %B011101010,
tick,
output,
tock,
output;
set address %B100101010,
tick,
output,
tock,
output;
set address %B101101010,
tick,
output,
tock,
output;
set address %B110101010,
tick,
output,
tock,
output;
set address %B111101010,
tick,
output,
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B000101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B000101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B001101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B001101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B010101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B011101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B011101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B100101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B100101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B101101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B110101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B110101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B111101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

set load 1,
set address %B111101010,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B000101010,
tick,
output;
tock,
output;
set address %B001101010,
eval,
output;
set address %B010101010,
eval,
output;
set address %B011101010,
eval,
output;
set address %B100101010,
eval,
output;
set address %B101101010,
eval,
output;
set address %B110101010,
eval,
output;
set address %B111101010,
eval,
output;

