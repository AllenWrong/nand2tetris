// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/08/FunctionCalls/StaticsTest/StaticsTestVME.tst

load,  // loads all the VM files from the current directory.
output-file StaticsTest.out,
compare-to StaticsTest.cmp,
output-list RAM[0]%D1.6.1 RAM[261]%D1.6.1 RAM[262]%D1.6.1;

set sp 261,

repeat 36 {
  vmstep;
}

output;
