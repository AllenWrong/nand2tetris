// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/08/ProgramFlow/FibonacciSeries/FibonacciSeriesVME.tst

load FibonacciSeries.vm,
output-file FibonacciSeries.out,
compare-to FibonacciSeries.cmp,
output-list RAM[3000]%D1.6.2 RAM[3001]%D1.6.2 RAM[3002]%D1.6.2 
            RAM[3003]%D1.6.2 RAM[3004]%D1.6.2 RAM[3005]%D1.6.2;

set sp 256,
set local 300,
set argument 400,
set argument[0] 6,
set argument[1] 3000,

repeat 73 {
  vmstep;
}

output;
