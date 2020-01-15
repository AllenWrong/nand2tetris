// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/03/a/RAM8.tst

load RAM8.hdl,
output-file RAM8.out,
compare-to RAM8.cmp,
output-list time%S1.4.1 in%D1.6.1 load%B2.1.2 address%D3.1.3 out%D1.6.1;

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

set in 11111,
set load 0,
tick,
output;
tock,
output;

set load 1,
set address 1,
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

set in 3333,
set address 3,
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

set address 1,
eval,
output;

set in 7777,
tick,
output;
tock,
output;

set load 1,
set address 7,
tick,
output;
tock,
output;

set load 0,
tick,
output;
tock,
output;

set address 3,
eval,
output;

set address 7,
eval,
output;

set load 0,
set address 0,
tick,
output;
tock,
output;
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set in %B0101010101010101,
set address 0,
tick,
output;
tock,
output;
set address 1,
tick,
output,
tock,
output;
set address 2,
tick,
output,
tock,
output;
set address 3,
tick,
output,
tock,
output;
set address 4,
tick,
output,
tock,
output;
set address 5,
tick,
output,
tock,
output;
set address 6,
tick,
output,
tock,
output;
set address 7,
tick,
output,
tock,
output;

set load 0,
set address 0,
tick,
output;
tock,
output;
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 0,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 0,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address 1,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 1,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address 2,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 2,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address 3,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 3,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address 4,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 4,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address 5,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 5,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address 6,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 6,
set in %B0101010101010101,
tick,
output,
tock,
output;
set address 7,
set in %B1010101010101010,
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
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

set load 1,
set address 7,
set in %B0101010101010101,
tick,
output,
tock,
output;

set load 0,
set address 0,
tick,
output;
tock,
output;
set address 1,
eval,
output;
set address 2,
eval,
output;
set address 3,
eval,
output;
set address 4,
eval,
output;
set address 5,
eval,
output;
set address 6,
eval,
output;
set address 7,
eval,
output;

