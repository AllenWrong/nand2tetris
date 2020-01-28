// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/07/StackArithmetic/StackTest/StackTestVME.tst

load StackTest.vm,
output-file StackTest.out,
compare-to StackTest.cmp,
output-list RAM[0]%D2.6.2 
        RAM[256]%D2.6.2 RAM[257]%D2.6.2 RAM[258]%D2.6.2 RAM[259]%D2.6.2 RAM[260]%D2.6.2;

set RAM[0] 256,  // initializes the stack pointer

repeat 38 {      // StackTest.vm consists of 38 instructions
  vmstep;
}

// outputs the stack pointer (RAM[0]) and 
// the stack contents: RAM[256]-RAM[265]
output;
output-list RAM[261]%D2.6.2 RAM[262]%D2.6.2 RAM[263]%D2.6.2 RAM[264]%D2.6.2 RAM[265]%D2.6.2;
output;
