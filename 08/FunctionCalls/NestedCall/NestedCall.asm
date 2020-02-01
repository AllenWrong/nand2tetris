// vm command: function Sys.init 0
// Sys.init function start
(Sys.init)

// vm command:push constant 4000
@4000
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

// vm command:push constant 5000
@5000
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

// vm command: call Sys.main 0
// save work
@Sys.main1$retAddr1
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
@Sys.main
0;JMP
(Sys.main1$retAddr1)

// vm command:pop temp 1
@5
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

// vm command: label LOOP
(Sys.main1$LOOP)

// vm command: goto LOOP
@Sys.main1$LOOP
0;JMP

// vm command: function Sys.main 5
(Sys.main)
// initialize local segment
@5
D=A
(Sys.main$LOOP)
D=D-1
@Sys.main$END
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@Sys.main$LOOP
0;JMP
(Sys.main$END)

// vm command:push constant 4001
@4001
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

// vm command:push constant 5001
@5001
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

// vm command:push constant 200
@200
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop local 1
@LCL
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

// vm command:push constant 40
@40
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop local 2
@LCL
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

// vm command:push constant 6
@6
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:pop local 3
@LCL
D=M
@3
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

// vm command:push constant 123
@123
D=A
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command: call Sys.add12 1
// save work
@Sys.add122$retAddr2
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
@Sys.add12
0;JMP
(Sys.add122$retAddr2)

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

// vm command:push local 1
@LCL
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

// vm command:push local 2
@LCL
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

// vm command:push local 3
@LCL
D=M
@3
A=D+A
D=M
// push the value into stack
@SP
A=M
M=D
@SP
M=M+1

// vm command:push local 4
@LCL
D=M
@4
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

// vm command: function Sys.add12 0
(Sys.add12)
// initialize local segment
@0
D=A
(Sys.add12$LOOP)
D=D-1
@Sys.add12$END
D;JLT
// push the value into stack
@SP
A=M
M=0
@SP
M=M+1
@Sys.add12$LOOP
0;JMP
(Sys.add12$END)

// vm command:push constant 4002
@4002
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

// vm command:push constant 5002
@5002
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

// vm command:push constant 12
@12
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

