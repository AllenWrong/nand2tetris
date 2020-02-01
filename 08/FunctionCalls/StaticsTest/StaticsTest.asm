// vm command: function Sys.init 0
// Sys.init function start
(Sys.init)

// vm command:push constant 6
@6
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 8
@8
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: call Class1.set 2
// save work
@Class1.set1$retAddr1
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
@2
D=D-A
@ARG
M=D
// LCL=SP
@SP
D=M
@LCL
M=D
// go to called function
@Class1.set
0;JMP
(Class1.set1$retAddr1)

// vm command:pop temp 0
@5
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

// vm command:push constant 23
@23
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push constant 15
@15
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: call Class2.set 2
// save work
@Class2.set2$retAddr2
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
@2
D=D-A
@ARG
M=D
// LCL=SP
@SP
D=M
@LCL
M=D
// go to called function
@Class2.set
0;JMP
(Class2.set2$retAddr2)

// vm command:pop temp 0
@5
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

// vm command: call Class1.get 0
// save work
@Class1.get3$retAddr3
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
@0
D=D-A
@ARG
M=D
// LCL=SP
@SP
D=M
@LCL
M=D
// go to called function
@Class1.get
0;JMP
(Class1.get3$retAddr3)

// vm command: call Class2.get 0
// save work
@Class2.get4$retAddr4
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
@0
D=D-A
@ARG
M=D
// LCL=SP
@SP
D=M
@LCL
M=D
// go to called function
@Class2.get
0;JMP
(Class2.get4$retAddr4)

// vm command: label WHILE
(Class2.get4$WHILE)

// vm command: goto WHILE
@Class2.get4$WHILE
0;JMP

// vm command: function Class1.set 0
(Class1.set)
// initialize local segment
@0
D=A
(Class1.set$LOOP)
D=D-1
@Class1.set$END
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@Class1.set$LOOP
0;JMP
(Class1.set$END)

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

// vm command:pop static 0
// get the top element of stack
@SP
M=M-1
A=M
D=M
@Class1.0
M=D

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

// vm command:pop static 1
// get the top element of stack
@SP
M=M-1
A=M
D=M
@Class1.1
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

// vm command: function Class1.get 0
(Class1.get)
// initialize local segment
@0
D=A
(Class1.get$LOOP)
D=D-1
@Class1.get$END
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@Class1.get$LOOP
0;JMP
(Class1.get$END)

// vm command:push static 0
@Class1.0
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push static 1
@Class1.1
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

// vm command: function Class2.set 0
(Class2.set)
// initialize local segment
@0
D=A
(Class2.set$LOOP)
D=D-1
@Class2.set$END
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@Class2.set$LOOP
0;JMP
(Class2.set$END)

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

// vm command:pop static 0
// get the top element of stack
@SP
M=M-1
A=M
D=M
@Class2.0
M=D

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

// vm command:pop static 1
// get the top element of stack
@SP
M=M-1
A=M
D=M
@Class2.1
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

// vm command: function Class2.get 0
(Class2.get)
// initialize local segment
@0
D=A
(Class2.get$LOOP)
D=D-1
@Class2.get$END
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@Class2.get$LOOP
0;JMP
(Class2.get$END)

// vm command:push static 0
@Class2.0
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push static 1
@Class2.1
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

