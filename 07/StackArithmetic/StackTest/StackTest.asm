// vm command:push constant 17
@17
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 17
@17
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:eq
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
@EQ0
D;JEQ
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDEQ0
0;JMP
(EQ0)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDEQ0)

// vm command:push constant 17
@17
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 16
@16
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:eq
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
@EQ1
D;JEQ
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDEQ1
0;JMP
(EQ1)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDEQ1)

// vm command:push constant 16
@16
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 17
@17
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:eq
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
@EQ2
D;JEQ
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDEQ2
0;JMP
(EQ2)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDEQ2)

// vm command:push constant 892
@892
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 891
@891
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
@LT3
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDLT3
0;JMP
(LT3)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDLT3)

// vm command:push constant 891
@891
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 892
@892
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
@LT4
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDLT4
0;JMP
(LT4)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDLT4)

// vm command:push constant 891
@891
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 891
@891
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
@LT5
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDLT5
0;JMP
(LT5)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDLT5)

// vm command:push constant 32767
@32767
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 32766
@32766
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:gt
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
@GT6
D;JGT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDGT6
0;JMP
(GT6)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDGT6)

// vm command:push constant 32766
@32766
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 32767
@32767
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:gt
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
@GT7
D;JGT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDGT7
0;JMP
(GT7)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDGT7)

// vm command:push constant 32766
@32766
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 32766
@32766
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:gt
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
@GT8
D;JGT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@ENDGT8
0;JMP
(GT8)
// push the value into stack
@SP
A=M
M=-1
@SP
M=M+1
(ENDGT8)

// vm command:push constant 57
@57
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 31
@31
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 53
@53
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

// vm command:push constant 112
@112
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

// vm command:neg
// get the top element of stack
@SP
M=M-1
A=M
D=M
@0
D=A-D
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:and
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
D=D&M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 82
@82
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:or
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
D=D|M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:not
// get the top element of stack
@SP
M=M-1
A=M
D=M
D=!D
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

