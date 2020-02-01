// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/08/FunctionCalls/FibonacciElement/FibonacciElement.tst

// FibonacciElement.asm results from translating both Main.vm and Sys.vm into
// a single assembly program, stored in the file FibonacciElement.asm.

load FibonacciElement.asm,
output-file FibonacciElement.out,
compare-to FibonacciElement.cmp,
output-list RAM[0]%D1.6.1 RAM[261]%D1.6.1;

set RAM[0] 261,

repeat 6000 {
  ticktock;
}

output;
