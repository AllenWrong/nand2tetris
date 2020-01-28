// vm command:push constant 10
@10
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop local 0
@LCL
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

// vm command:push constant 21
@21
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 22
@22
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop argument 2
@ARG
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

// vm command:pop argument 1
@ARG
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

// vm command:push constant 36
@36
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop this 6
@THIS
D=M
@6
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

// vm command:push constant 42
@42
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 45
@45
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop that 5
@THAT
D=M
@5
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

// vm command:push constant 510
@510
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop temp 6
@5
D=A
@6
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

// vm command:push local 0
@LCL
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

// vm command:push that 5
@THAT
D=M
@5
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

// vm command:push this 6
@THIS
D=M
@6
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push this 6
@THIS
D=M
@6
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

// vm command:push temp 6
@R5
D=A
@6
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

