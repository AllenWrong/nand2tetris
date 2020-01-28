// vm command:push constant 3030
@3030
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop pointer 0
@THIS
D=A
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

// vm command:push constant 3040
@3040
D=A
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

// vm command:push constant 32
@32
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop this 2
@THIS
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

// vm command:push constant 46
@46
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop that 6
@THAT
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

// vm command:push pointer 0
@THIS
D=A
@0
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

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

// vm command:push this 2
@THIS
D=M
@2
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

// vm command:push that 6
@THAT
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

