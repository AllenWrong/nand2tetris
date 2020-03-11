function Keyboard.init 0
push constant 0
return
function Keyboard.keyPressed 0
push constant 24576
call Memory.peek 1
return
function Keyboard.readChar 2
push constant 0
call Output.printChar 1
pop temp 0
label WHILE_EXP0
push local 1
push constant 0
eq
push local 0
push constant 0
gt
or
not
if-goto WHILE_END0
call Keyboard.keyPressed 0
pop local 0
push local 0
push constant 0
gt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push local 0
pop local 1
label IF_FALSE0
goto WHILE_EXP0
label WHILE_END0
call String.backSpace 0
call Output.printChar 1
pop temp 0
push local 1
call Output.printChar 1
pop temp 0
push local 1
return
function Keyboard.readLine 5
push constant 80
call String.new 1
pop local 3
push argument 0
call Output.printString 1
pop temp 0
call String.newLine 0
pop local 1
call String.backSpace 0
pop local 2
label WHILE_EXP0
push local 4
not
not
if-goto WHILE_END0
call Keyboard.readChar 0
pop local 0
push local 0
push local 1
eq
pop local 4
push local 4
not
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push local 0
push local 2
eq
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push local 3
call String.eraseLastChar 1
pop temp 0
goto IF_END1
label IF_FALSE1
push local 3
push local 0
call String.appendChar 2
pop local 3
label IF_END1
label IF_FALSE0
goto WHILE_EXP0
label WHILE_END0
push local 3
return
function Keyboard.readInt 2
push argument 0
call Keyboard.readLine 1
pop local 0
push local 0
call String.intValue 1
pop local 1
push local 0
call String.dispose 1
pop temp 0
push local 1
return
