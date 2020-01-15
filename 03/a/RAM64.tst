// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/03/a/RAM64.tst

load RAM64.hdl,
output-file RAM64.out,
compare-to RAM64.cmp,
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

set in 1313,
set load 0,
tick,
output;
tock,
output;

set load 1,
set address 13,
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

set in 4747,
set address 47,
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

set address 13,
eval,
output;

set in 6363,
tick,
output;
tock,
output;

set load 1,
set address 63,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 47,
eval,
output;

set address 63,
eval,
output;


set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
tick,
output,
tock,
output;
set address %B101010,
tick,
output,
tock,
output;
set address %B101011,
tick,
output,
tock,
output;
set address %B101100,
tick,
output,
tock,
output;
set address %B101101,
tick,
output,
tock,
output;
set address %B101110,
tick,
output,
tock,
output;
set address %B101111,
tick,
output,
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101000,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101000,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101001,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101001,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101011,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101011,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101100,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101100,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101110,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101110,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101111,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;

set load 1,
set address %B101111,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B101000,
tick,
output;
tock,
output;
set address %B101001,
eval,
output;
set address %B101010,
eval,
output;
set address %B101011,
eval,
output;
set address %B101100,
eval,
output;
set address %B101101,
eval,
output;
set address %B101110,
eval,
output;
set address %B101111,
eval,
output;


set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
tick,
output,
tock,
output;
set address %B010101,
tick,
output,
tock,
output;
set address %B011101,
tick,
output,
tock,
output;
set address %B100101,
tick,
output,
tock,
output;
set address %B101101,
tick,
output,
tock,
output;
set address %B110101,
tick,
output,
tock,
output;
set address %B111101,
tick,
output,
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B000101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B000101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B001101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B001101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B011101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B011101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B100101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B100101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B101101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B101101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B110101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B110101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B111101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;

set load 1,
set address %B111101,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B000101,
tick,
output;
tock,
output;
set address %B001101,
eval,
output;
set address %B010101,
eval,
output;
set address %B011101,
eval,
output;
set address %B100101,
eval,
output;
set address %B101101,
eval,
output;
set address %B110101,
eval,
output;
set address %B111101,
eval,
output;
