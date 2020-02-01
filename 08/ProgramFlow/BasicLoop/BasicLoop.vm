// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/08/ProgramFlow/BasicLoop/BasicLoop.vm

// Computes the sum 1 + 2 + ... + argument[0] and pushes the 
// result onto the stack. Argument[0] is initialized by the test 
// script before this code starts running.
push constant 0    
pop local 0         // initializes sum = 0
label LOOP_START
push argument 0    
push local 0
add
pop local 0	        // sum = sum + counter
push argument 0
push constant 1
sub
pop argument 0      // counter--
push argument 0
if-goto LOOP_START  // If counter > 0, goto LOOP_START
push local 0
