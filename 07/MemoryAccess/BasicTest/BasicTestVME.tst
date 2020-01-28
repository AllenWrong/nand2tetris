// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/07/MemoryAccess/BasicTest/BasicTestVME.tst

load BasicTest.vm,
output-file BasicTest.out,
compare-to BasicTest.cmp,
output-list RAM[256]%D1.6.1 RAM[300]%D1.6.1 RAM[401]%D1.6.1 
            RAM[402]%D1.6.1 RAM[3006]%D1.6.1 RAM[3012]%D1.6.1
            RAM[3015]%D1.6.1 RAM[11]%D1.6.1;

set sp 256,        // stack pointer
set local 300,     // base address of the local segment
set argument 400,  // base address of the argument segment
set this 3000,     // base address of the this segment
set that 3010,     // base address of the that segment

repeat 25 {        // BasicTest.vm has 25 instructions
  vmstep;
}

// Outputs the stack base and some values
// from the tested memory segments
output;
