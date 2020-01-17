// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

@SCREEN
D=A    // get the basic address of the screen memory-map
@0
M=D    // store the basic address in the M[0]
@24575
D=A    // get the max address of the screen memory-map
@1
M=D    // store the max address in the M[1]

(LOOP)
	@KBD
	D=M    // get the basic address of th keyboard memory-map
	@FILL
	D;JGT  // if D is greater than zero, jump to FILL
	@CLEAR // if D is not greater than zero, jump to CLEAR
	0;JMP

(FILL)
	@1    
	D=M    // get the max address of the screen memory-map
	@0
	D=D-M  // get the current address of the screen memory-map, and let the max address minus the current address
	@LOOP
	D;JLT  // if the result of this minus operation is lower then zero, jump to LOOP

	@0
	D=M   // get the current address of the screen memory-map
	A=D   // store the current address in the A register
	M=-1  // let the value of the current address is "black"
	@0    
	D=M   // get the current address of the screen memory-map
	D=D+1 // add the current address
	M=D
@LOOP
0;JMP // return to the LOOP

(CLEAR)
	@0
	D=M     // get the current address
	D=D-1   // do the minus operation
	M=D
	@SCREEN // get the basic address
	D=D-A   // let current address minus basic address
	@LOOP
	D;JLT   // do just, if D is lower than zero, jump to LOOP
	@0      
	D=M     // get the current address
	A=D     // store the current address in the A register
	M=0     // clear the "black", in another word
	@LOOP
	0;JMP   // return to LOOP

