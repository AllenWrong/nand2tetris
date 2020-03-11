// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/12/MathTest/MathTest.tst

load,
output-file MathTest.out,
compare-to MathTest.cmp,
output-list RAM[8000]%D2.6.1 RAM[8001]%D2.6.1 RAM[8002]%D2.6.1 RAM[8003]%D2.6.1 RAM[8004]%D2.6.1 RAM[8005]%D2.6.1 RAM[8006]%D2.6.1 RAM[8007]%D2.6.1 RAM[8008]%D2.6.1 RAM[8009]%D2.6.1 RAM[8010]%D2.6.1 RAM[8011]%D2.6.1 RAM[8012]%D2.6.1 RAM[8013]%D2.6.1;

repeat 1000000 {
  vmstep;
}

output;
