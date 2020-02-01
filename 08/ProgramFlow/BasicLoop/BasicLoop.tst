// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/08/ProgramFlow/BasicLoop/BasicLoop.tst

load BasicLoop.asm,
output-file BasicLoop.out,
compare-to BasicLoop.cmp,
output-list RAM[0]%D1.6.1 RAM[256]%D1.6.1;

set RAM[0] 256,
set RAM[1] 300,
set RAM[2] 400,
set RAM[400] 3,

repeat 600 {
  ticktock;
}

output;
