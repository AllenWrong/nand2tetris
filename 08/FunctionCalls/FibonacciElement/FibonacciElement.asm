// vm command: function Sys.init 0
// Sys.init function start
(Sys.init)

// vm command:push constant 4
@4
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: call Main.fibonacci 1
// save work
@Main.fibonacci1$retAddr1
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
// argument process
@SP
D=M
@5
D=D-A
@1
D=D-A
@ARG
M=D
// LCL=SP
@SP
D=M
@LCL
M=D
// go to called function
@Main.fibonacci
0;JMP
(Main.fibonacci1$retAddr1)

// vm command: label WHILE
(Main.fibonacci1$WHILE)

// vm command: goto WHILE
@Main.fibonacci1$WHILE
0;JMP

// vm command: function Main.fibonacci 0
(Main.fibonacci)
// initialize local segment
@0
D=A
(Main.fibonacci$LOOP)
D=D-1
@Main.fibonacci$END
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@Main.fibonacci$LOOP
0;JMP
(Main.fibonacci$END)

// vm command:push argument 0
@ARG
D=M
@0
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 2
@2
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:lt
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R14
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R13
M=D
@R13
D=M
@R14
D=D-M
@LT1
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDLT1
0;JMP
(LT1)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDLT1)

// vm command: if-goto IF_TRUE
// get the top element of stack
@SP
M=M-1
A=M
D=M
@Main.fibonacci1$IF_TRUE
D;JNE

// vm command: goto IF_FALSE
@Main.fibonacci1$IF_FALSE
0;JMP

// vm command: label IF_TRUE
(Main.fibonacci1$IF_TRUE)

// vm command:push argument 0
@ARG
D=M
@0
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: return
@LCL
D=M
@R13
M=D        // temporarily store the endFrame
@R13
D=M
@5
A=D-A      // get the return address
D=M
@R14
M=D        // temporarily store the return address
@ARG
D=M
@0
D=D+A
// store the result temporarily
@R15
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R15
A=M
M=D
// set the SP
@ARG
D=M
@SP
M=D+1
// restore scene
@R13
D=M
@R15
M=D

@R15
M=M-1
A=M
D=M
@THAT
M=D

@R15
M=M-1
A=M
D=M
@THIS
M=D

@R15
M=M-1
A=M
D=M
@ARG
M=D

@R15
M=M-1
A=M
D=M
@LCL
M=D

// goto return address
@R14
A=M
0;JMP

// vm command: label IF_FALSE
(Main.fibonacci1$IF_FALSE)

// vm command:push argument 0
@ARG
D=M
@0
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 2
@2
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:sub
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R14
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R13
M=D
@R13
D=M
@R14
D=D-M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: call Main.fibonacci 1
// save work
@Main.fibonacci3$retAddr3
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
// argument process
@SP
D=M
@5
D=D-A
@1
D=D-A
@ARG
M=D
// LCL=SP
@SP
D=M
@LCL
M=D
// go to called function
@Main.fibonacci
0;JMP
(Main.fibonacci3$retAddr3)

// vm command:push argument 0
@ARG
D=M
@0
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 1
@1
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:sub
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R14
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R13
M=D
@R13
D=M
@R14
D=D-M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: call Main.fibonacci 1
// save work
@Main.fibonacci4$retAddr4
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1
// argument process
@SP
D=M
@5
D=D-A
@1
D=D-A
@ARG
M=D
// LCL=SP
@SP
D=M
@LCL
M=D
// go to called function
@Main.fibonacci
0;JMP
(Main.fibonacci4$retAddr4)

// vm command:add
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R14
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the result temporarily
@R13
M=D
@R13
D=M
@R14
D=D+M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: return
@LCL
D=M
@R13
M=D        // temporarily store the endFrame
@R13
D=M
@5
A=D-A      // get the return address
D=M
@R14
M=D        // temporarily store the return address
@ARG
D=M
@0
D=D+A
// store the result temporarily
@R15
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R15
A=M
M=D
// set the SP
@ARG
D=M
@SP
M=D+1
// restore scene
@R13
D=M
@R15
M=D

@R15
M=M-1
A=M
D=M
@THAT
M=D

@R15
M=M-1
A=M
D=M
@THIS
M=D

@R15
M=M-1
A=M
D=M
@ARG
M=D

@R15
M=M-1
A=M
D=M
@LCL
M=D

// goto return address
@R14
A=M
0;JMP

