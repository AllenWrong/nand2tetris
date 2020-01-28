// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/07/MemoryAccess/StaticTest/StaticTestVME.tst

load StaticTest.vm,
output-file StaticTest.out,
compare-to StaticTest.cmp,
output-list RAM[256]%D1.6.1;

set sp 256,    // initializes the stack pointer

repeat 11 {    // StaticTest.vm has 11 instructions
  vmstep;
}

output;        // the stack base
