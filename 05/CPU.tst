// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/05/CPU.tst

load CPU.hdl,
output-file CPU.out,
compare-to CPU.cmp,
output-list time%S0.4.0 inM%D0.6.0 instruction%B0.16.0 reset%B2.1.2 outM%D1.6.0 writeM%B3.1.3 addressM%D0.5.0 pc%D0.5.0 DRegister[]%D1.6.1;


set instruction %B0011000000111001, // @12345
tick, output, tock, output;

set instruction %B1110110000010000, // D=A
tick, output, tock, output;

set instruction %B0101101110100000, // @23456
tick, output, tock, output;

set instruction %B1110000111010000, // D=A-D
tick, output, tock, output;

set instruction %B0000001111101000, // @1000
tick, output, tock, output;

set instruction %B1110001100001000, // M=D
tick, output, tock, output;

set instruction %B0000001111101001, // @1001
tick, output, tock, output;

set instruction %B1110001110011000, // MD=D-1
tick, output, tock, output;

set instruction %B0000001111101000, // @1000
tick, output, tock, output;

set instruction %B1111010011010000, // D=D-M
set inM 11111,
tick, output, tock, output;

set instruction %B0000000000001110, // @14
tick, output, tock, output;

set instruction %B1110001100000100, // D;jlt
tick, output, tock, output;

set instruction %B0000001111100111, // @999
tick, output, tock, output;

set instruction %B1110110111100000, // A=A+1
tick, output, tock, output;

set instruction %B1110001100001000, // M=D
tick, output, tock, output;

set instruction %B0000000000010101, // @21
tick, output, tock, output;

set instruction %B1110011111000010, // D+1;jeq
tick, output, tock, output;

set instruction %B0000000000000010, // @2
tick, output, tock, output;

set instruction %B1110000010010000, // D=D+A
tick, output, tock, output;

set instruction %B0000001111101000, // @1000
tick, output, tock, output;

set instruction %B1110111010010000, // D=-1
tick, output, tock, output;

set instruction %B1110001100000001, // D;JGT
tick, output, tock, output;

set instruction %B1110001100000010, // D;JEQ
tick, output, tock, output;

set instruction %B1110001100000011, // D;JGE
tick, output, tock, output;

set instruction %B1110001100000100, // D;JLT
tick, output, tock, output;

set instruction %B1110001100000101, // D;JNE
tick, output, tock, output;

set instruction %B1110001100000110, // D;JLE
tick, output, tock, output;

set instruction %B1110001100000111, // D;JMP
tick, output, tock, output;

set instruction %B1110101010010000, // D=0
tick, output, tock, output;

set instruction %B1110001100000001, // D;JGT
tick, output, tock, output;

set instruction %B1110001100000010, // D;JEQ
tick, output, tock, output;

set instruction %B1110001100000011, // D;JGE
tick, output, tock, output;

set instruction %B1110001100000100, // D;JLT
tick, output, tock, output;

set instruction %B1110001100000101, // D;JNE
tick, output, tock, output;

set instruction %B1110001100000110, // D;JLE
tick, output, tock, output;

set instruction %B1110001100000111, // D;JMP
tick, output, tock, output;

set instruction %B1110111111010000, // D=1
tick, output, tock, output;

set instruction %B1110001100000001, // D;JGT
tick, output, tock, output;

set instruction %B1110001100000010, // D;JEQ
tick, output, tock, output;

set instruction %B1110001100000011, // D;JGE
tick, output, tock, output;

set instruction %B1110001100000100, // D;JLT
tick, output, tock, output;

set instruction %B1110001100000101, // D;JNE
tick, output, tock, output;

set instruction %B1110001100000110, // D;JLE
tick, output, tock, output;

set instruction %B1110001100000111, // D;JMP
tick, output, tock, output;

set reset 1;
tick, output, tock, output;

set instruction %B0111111111111111, // @32767
set reset 0;
tick, output, tock, output;
