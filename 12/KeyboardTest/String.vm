function String.new 0
push constant 3
call Memory.alloc 1
pop pointer 0
push argument 0
push constant 0
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 14
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 0
push constant 0
gt
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push argument 0
call Array.new 1
pop this 1
label IF_FALSE1
push argument 0
pop this 0
push constant 0
pop this 2
push pointer 0
return
function String.dispose 0
push argument 0
pop pointer 0
push this 0
push constant 0
gt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push this 1
call Array.dispose 1
pop temp 0
label IF_FALSE0
push pointer 0
call Memory.deAlloc 1
pop temp 0
push constant 0
return
function String.length 0
push argument 0
pop pointer 0
push this 2
return
function String.charAt 0
push argument 0
pop pointer 0
push argument 1
push constant 0
lt
push argument 1
push this 2
gt
or
push argument 1
push this 2
eq
or
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 15
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 1
push this 1
add
pop pointer 1
push that 0
return
function String.setCharAt 0
push argument 0
pop pointer 0
push argument 1
push constant 0
lt
push argument 1
push this 2
gt
or
push argument 1
push this 2
eq
or
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 16
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 1
push this 1
add
push argument 2
pop temp 0
pop pointer 1
push temp 0
pop that 0
push constant 0
return
function String.appendChar 0
push argument 0
pop pointer 0
push this 2
push this 0
eq
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 17
call Sys.error 1
pop temp 0
label IF_FALSE0
push this 2
push this 1
add
push argument 1
pop temp 0
pop pointer 1
push temp 0
pop that 0
push this 2
push constant 1
add
pop this 2
push pointer 0
return
function String.eraseLastChar 0
push argument 0
pop pointer 0
push this 2
push constant 0
eq
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 18
call Sys.error 1
pop temp 0
label IF_FALSE0
push this 2
push constant 1
sub
pop this 2
push constant 0
return
function String.intValue 5
push argument 0
pop pointer 0
push this 2
push constant 0
eq
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 0
return
label IF_FALSE0
push constant 0
not
pop local 3
push constant 0
push this 1
add
pop pointer 1
push that 0
push constant 45
eq
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push constant 0
not
pop local 4
push constant 1
pop local 0
label IF_FALSE1
label WHILE_EXP0
push local 0
push this 2
lt
push local 3
and
not
if-goto WHILE_END0
push local 0
push this 1
add
pop pointer 1
push that 0
push constant 48
sub
pop local 2
push local 2
push constant 0
lt
push local 2
push constant 9
gt
or
not
pop local 3
push local 3
if-goto IF_TRUE2
goto IF_FALSE2
label IF_TRUE2
push local 1
push constant 10
call Math.multiply 2
push local 2
add
pop local 1
push local 0
push constant 1
add
pop local 0
label IF_FALSE2
goto WHILE_EXP0
label WHILE_END0
push local 4
if-goto IF_TRUE3
goto IF_FALSE3
label IF_TRUE3
push local 1
neg
pop local 1
label IF_FALSE3
push local 1
return
function String.setInt 4
push argument 0
pop pointer 0
push this 0
push constant 0
eq
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 19
call Sys.error 1
pop temp 0
label IF_FALSE0
push constant 6
call Array.new 1
pop local 2
push argument 1
push constant 0
lt
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push constant 0
not
pop local 3
push argument 1
neg
pop argument 1
label IF_FALSE1
push argument 1
pop local 1
label WHILE_EXP0
push local 1
push constant 0
gt
not
if-goto WHILE_END0
push argument 1
push constant 10
call Math.divide 2
pop local 1
push local 0
push local 2
add
push constant 48
push argument 1
push local 1
push constant 10
call Math.multiply 2
sub
add
pop temp 0
pop pointer 1
push temp 0
pop that 0
push local 0
push constant 1
add
pop local 0
push local 1
pop argument 1
goto WHILE_EXP0
label WHILE_END0
push local 3
if-goto IF_TRUE2
goto IF_FALSE2
label IF_TRUE2
push local 0
push local 2
add
push constant 45
pop temp 0
pop pointer 1
push temp 0
pop that 0
push local 0
push constant 1
add
pop local 0
label IF_FALSE2
push this 0
push local 0
lt
if-goto IF_TRUE3
goto IF_FALSE3
label IF_TRUE3
push constant 19
call Sys.error 1
pop temp 0
label IF_FALSE3
push local 0
push constant 0
eq
if-goto IF_TRUE4
goto IF_FALSE4
label IF_TRUE4
push constant 0
push this 1
add
push constant 48
pop temp 0
pop pointer 1
push temp 0
pop that 0
push constant 1
pop this 2
goto IF_END4
label IF_FALSE4
push constant 0
pop this 2
label WHILE_EXP1
push this 2
push local 0
lt
not
if-goto WHILE_END1
push this 2
push this 1
add
push local 0
push this 2
push constant 1
add
sub
push local 2
add
pop pointer 1
push that 0
pop temp 0
pop pointer 1
push temp 0
pop that 0
push this 2
push constant 1
add
pop this 2
goto WHILE_EXP1
label WHILE_END1
label IF_END4
push local 2
call Array.dispose 1
pop temp 0
push constant 0
return
function String.newLine 0
push constant 128
return
function String.backSpace 0
push constant 129
return
function String.doubleQuote 0
push constant 34
return
