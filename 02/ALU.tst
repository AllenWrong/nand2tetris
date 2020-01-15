// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/02/ALU.tst

load ALU.hdl,
output-file ALU.out,
compare-to ALU.cmp,
output-list x%B1.16.1 y%B1.16.1 zx%B1.1.1 nx%B1.1.1 zy%B1.1.1 
            ny%B1.1.1 f%B1.1.1 no%B1.1.1 out%B1.16.1 zr%B1.1.1
            ng%B1.1.1;

set x %B0000000000000000,  // x = 0
set y %B1111111111111111;  // y = -1

// Compute 0
set zx 1,
set nx 0,
set zy 1,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute 1
set zx 1,
set nx 1,
set zy 1,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute -1
set zx 1,
set nx 1,
set zy 1,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute x
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  0,
set no 0,
eval,
output;

// Compute y
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  0,
set no 0,
eval,
output;

// Compute !x
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  0,
set no 1,
eval,
output;

// Compute !y
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  0,
set no 1,
eval,
output;

// Compute -x
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute -y
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  1,
set no 1,
eval,
output;

// Compute x + 1
set zx 0,
set nx 1,
set zy 1,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute y + 1
set zx 1,
set nx 1,
set zy 0,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute x - 1
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  1,
set no 0,
eval,
output;

// Compute y - 1
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute x + y
set zx 0,
set nx 0,
set zy 0,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute x - y
set zx 0,
set nx 1,
set zy 0,
set ny 0,
set f  1,
set no 1,
eval,
output;

// Compute y - x
set zx 0,
set nx 0,
set zy 0,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute x & y
set zx 0,
set nx 0,
set zy 0,
set ny 0,
set f  0,
set no 0,
eval,
output;

// Compute x | y
set zx 0,
set nx 1,
set zy 0,
set ny 1,
set f  0,
set no 1,
eval,
output;

set x %B000000000010001,  // x = 17
set y %B000000000000011;  // y =  3

// Compute 0
set zx 1,
set nx 0,
set zy 1,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute 1
set zx 1,
set nx 1,
set zy 1,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute -1
set zx 1,
set nx 1,
set zy 1,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute x
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  0,
set no 0,
eval,
output;

// Compute y
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  0,
set no 0,
eval,
output;

// Compute !x
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  0,
set no 1,
eval,
output;

// Compute !y
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  0,
set no 1,
eval,
output;

// Compute -x
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute -y
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  1,
set no 1,
eval,
output;

// Compute x + 1
set zx 0,
set nx 1,
set zy 1,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute y + 1
set zx 1,
set nx 1,
set zy 0,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute x - 1
set zx 0,
set nx 0,
set zy 1,
set ny 1,
set f  1,
set no 0,
eval,
output;

// Compute y - 1
set zx 1,
set nx 1,
set zy 0,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute x + y
set zx 0,
set nx 0,
set zy 0,
set ny 0,
set f  1,
set no 0,
eval,
output;

// Compute x - y
set zx 0,
set nx 1,
set zy 0,
set ny 0,
set f  1,
set no 1,
eval,
output;

// Compute y - x
set zx 0,
set nx 0,
set zy 0,
set ny 1,
set f  1,
set no 1,
eval,
output;

// Compute x & y
set zx 0,
set nx 0,
set zy 0,
set ny 0,
set f  0,
set no 0,
eval,
output;

// Compute x | y
set zx 0,
set nx 1,
set zy 0,
set ny 1,
set f  0,
set no 1,
eval,
output;
