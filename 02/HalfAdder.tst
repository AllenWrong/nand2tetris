// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/02/HalfAdder.tst

load HalfAdder.hdl,
output-file HalfAdder.out,
compare-to HalfAdder.cmp,
output-list a%B3.1.3 b%B3.1.3 sum%B3.1.3 carry%B3.1.3;

set a 0,
set b 0,
eval,
output;

set a 0,
set b 1,
eval,
output;

set a 1,
set b 0,
eval,
output;

set a 1,
set b 1,
eval,
output;
