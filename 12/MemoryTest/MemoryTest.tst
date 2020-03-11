// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/12/MemoryTest/MemoryTest.tst

load,
output-file MemoryTest.out,
compare-to MemoryTest.cmp,
output-list RAM[8000]%D2.6.1 RAM[8001]%D2.6.1 RAM[8002]%D2.6.1 RAM[8003]%D2.6.1 RAM[8004]%D2.6.1 RAM[8005]%D2.6.1;

repeat 1000000 {
  vmstep;
}

output;
