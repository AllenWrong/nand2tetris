// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/mult/Mult.tst

load Mult.hack,
output-file Mult.out,
compare-to Mult.cmp,
output-list RAM[0]%D2.6.2 RAM[1]%D2.6.2 RAM[2]%D2.6.2;

set RAM[0] 0,   // Set test arguments
set RAM[1] 0,
set RAM[2] -1;  // Test that program initialized product to 0
repeat 20 {
  ticktock;
}
set RAM[0] 0,   // Restore arguments in case program used them as loop counter
set RAM[1] 0,
output;

set PC 0,
set RAM[0] 1,   // Set test arguments
set RAM[1] 0,
set RAM[2] -1;  // Ensure that program initialized product to 0
repeat 50 {
  ticktock;
}
set RAM[0] 1,   // Restore arguments in case program used them as loop counter
set RAM[1] 0,
output;

set PC 0,
set RAM[0] 0,   // Set test arguments
set RAM[1] 2,
set RAM[2] -1;  // Ensure that program initialized product to 0
repeat 80 {
  ticktock;
}
set RAM[0] 0,   // Restore arguments in case program used them as loop counter
set RAM[1] 2,
output;

set PC 0,
set RAM[0] 3,   // Set test arguments
set RAM[1] 1,
set RAM[2] -1;  // Ensure that program initialized product to 0
repeat 120 {
  ticktock;
}
set RAM[0] 3,   // Restore arguments in case program used them as loop counter
set RAM[1] 1,
output;

set PC 0,
set RAM[0] 2,   // Set test arguments
set RAM[1] 4,
set RAM[2] -1;  // Ensure that program initialized product to 0
repeat 150 {
  ticktock;
}
set RAM[0] 2,   // Restore arguments in case program used them as loop counter
set RAM[1] 4,
output;

set PC 0,
set RAM[0] 6,   // Set test arguments
set RAM[1] 7,
set RAM[2] -1;  // Ensure that program initialized product to 0
repeat 210 {
  ticktock;
}
set RAM[0] 6,   // Restore arguments in case program used them as loop counter
set RAM[1] 7,
output;
