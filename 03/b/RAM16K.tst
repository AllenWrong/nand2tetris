// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/03/b/RAM16K.tst

load RAM16K.hdl,
output-file RAM16K.out,
compare-to RAM16K.cmp,
output-list time%S1.4.1 in%D1.6.1 load%B2.1.2 address%D2.5.2 out%D1.6.1;

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

set in 4321,
set load 0,
tick,
output;
tock,
output;

set load 1,
set address 4321,
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

set in 12345,
set address 12345,
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

set address 4321,
eval,
output;

set in 16383,
tick,
output;
tock,
output;

set load 1,
set address 16383,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 12345,
eval,
output;

set address 16383,
eval,
output;


set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
tick,
output,
tock,
output;
set address %B10101010101010,
tick,
output,
tock,
output;
set address %B10101010101011,
tick,
output,
tock,
output;
set address %B10101010101100,
tick,
output,
tock,
output;
set address %B10101010101101,
tick,
output,
tock,
output;
set address %B10101010101110,
tick,
output,
tock,
output;
set address %B10101010101111,
tick,
output,
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101000,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101000,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10101010101001,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101001,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10101010101010,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101010,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10101010101011,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101011,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10101010101100,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101100,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10101010101101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10101010101110,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101110,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10101010101111,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;

set load 1,
set address %B10101010101111,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B10101010101000,
tick,
output;
tock,
output;
set address %B10101010101001,
eval,
output;
set address %B10101010101010,
eval,
output;
set address %B10101010101011,
eval,
output;
set address %B10101010101100,
eval,
output;
set address %B10101010101101,
eval,
output;
set address %B10101010101110,
eval,
output;
set address %B10101010101111,
eval,
output;


set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set in %B0101010101010101,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
tick,
output,
tock,
output;
set address %B01010101010101,
tick,
output,
tock,
output;
set address %B01110101010101,
tick,
output,
tock,
output;
set address %B10010101010101,
tick,
output,
tock,
output;
set address %B10110101010101,
tick,
output,
tock,
output;
set address %B11010101010101,
tick,
output,
tock,
output;
set address %B11110101010101,
tick,
output,
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B00010101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B00010101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B00110101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B00110101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B01010101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B01010101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B01110101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B01110101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10010101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B10010101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B10110101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B10110101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B11010101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B11010101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address %B11110101010101,
set in %B1010101010101010,
tick,
output;
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;

set load 1,
set address %B11110101010101,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address %B00010101010101,
tick,
output;
tock,
output;
set address %B00110101010101,
eval,
output;
set address %B01010101010101,
eval,
output;
set address %B01110101010101,
eval,
output;
set address %B10010101010101,
eval,
output;
set address %B10110101010101,
eval,
output;
set address %B11010101010101,
eval,
output;
set address %B11110101010101,
eval,
output;
