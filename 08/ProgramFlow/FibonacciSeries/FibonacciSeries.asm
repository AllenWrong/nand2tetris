// vm command:push argument 1
@ARG
D=M
@1
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop pointer 1
@THIS
D=A
@1
D=D+A
// store the result temporarily
@R13
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R13
A=M
M=D

// vm command:push constant 0
@0
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop that 0
@THAT
D=M
@0
D=D+A
// store the result temporarily
@R13
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R13
A=M
M=D

// vm command:push constant 1
@1
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop that 1
@THAT
D=M
@1
D=D+A
// store the result temporarily
@R13
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R13
A=M
M=D

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

// vm command:pop argument 0
@ARG
D=M
@0
D=D+A
// store the result temporarily
@R13
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R13
A=M
M=D

// vm command: label MAIN_LOOP_START
(FibonacciSeries$MAIN_LOOP_START)

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

// vm command: if-goto COMPUTE_ELEMENT
// get the top element of stack
@SP
M=M-1
A=M
D=M
@FibonacciSeries$COMPUTE_ELEMENT
D;JNE

// vm command: goto END_PROGRAM
@FibonacciSeries$END_PROGRAM
0;JMP

// vm command: label COMPUTE_ELEMENT
(FibonacciSeries$COMPUTE_ELEMENT)

// vm command:push that 0
@THAT
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

// vm command:push that 1
@THAT
D=M
@1
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

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

// vm command:pop that 2
@THAT
D=M
@2
D=D+A
// store the result temporarily
@R13
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R13
A=M
M=D

// vm command:push pointer 1
@THIS
D=A
@1
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

// vm command:pop pointer 1
@THIS
D=A
@1
D=D+A
// store the result temporarily
@R13
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R13
A=M
M=D

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

// vm command:pop argument 0
@ARG
D=M
@0
D=D+A
// store the result temporarily
@R13
M=D
// get the top element of stack
@SP
M=M-1
A=M
D=M
// store the top value
@R13
A=M
M=D

// vm command: goto MAIN_LOOP_START
@FibonacciSeries$MAIN_LOOP_START
0;JMP

// vm command: label END_PROGRAM
(FibonacciSeries$END_PROGRAM)

