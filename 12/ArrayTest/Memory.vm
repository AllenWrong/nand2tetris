function Memory.init 0
push constant 0
pop static 0
push constant 2048
push static 0
add
push constant 14334
pop temp 0
pop pointer 1
push temp 0
pop that 0
push constant 2049
push static 0
add
push constant 2050
pop temp 0
pop pointer 1
push temp 0
pop that 0
push constant 0
return
function Memory.peek 0
push argument 0
push static 0
add
pop pointer 1
push that 0
return
function Memory.poke 0
push argument 0
push static 0
add
push argument 1
pop temp 0
pop pointer 1
push temp 0
pop that 0
push constant 0
return
function Memory.alloc 1
push argument 0
push constant 1
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 5
call Sys.error 1
pop temp 0
label IF_FALSE0
push constant 2048
pop local 0
label WHILE_EXP0
push constant 0
push local 0
add
pop pointer 1
push that 0
push argument 0
lt
not
if-goto WHILE_END0
push constant 1
push local 0
add
pop pointer 1
push that 0
pop local 0
goto WHILE_EXP0
label WHILE_END0
push local 0
push argument 0
add
push constant 16379
gt
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push constant 6
call Sys.error 1
pop temp 0
label IF_FALSE1
push constant 0
push local 0
add
pop pointer 1
push that 0
push argument 0
push constant 2
add
gt
if-goto IF_TRUE2
goto IF_FALSE2
label IF_TRUE2
push argument 0
push constant 2
add
push local 0
add
push constant 0
push local 0
add
pop pointer 1
push that 0
push argument 0
sub
push constant 2
sub
pop temp 0
pop pointer 1
push temp 0
pop that 0
push constant 1
push local 0
add
pop pointer 1
push that 0
push local 0
push constant 2
add
eq
if-goto IF_TRUE3
goto IF_FALSE3
label IF_TRUE3
push argument 0
push constant 3
add
push local 0
add
push local 0
push argument 0
add
push constant 4
add
pop temp 0
pop pointer 1
push temp 0
pop that 0
goto IF_END3
label IF_FALSE3
push argument 0
push constant 3
add
push local 0
add
push constant 1
push local 0
add
pop pointer 1
push that 0
pop temp 0
pop pointer 1
push temp 0
pop that 0
label IF_END3
push constant 1
push local 0
add
push local 0
push argument 0
add
push constant 2
add
pop temp 0
pop pointer 1
push temp 0
pop that 0
label IF_FALSE2
push constant 0
push local 0
add
push constant 0
pop temp 0
pop pointer 1
push temp 0
pop that 0
push local 0
push constant 2
add
return
function Memory.deAlloc 2
push argument 0
push constant 2
sub
pop local 0
push constant 1
push local 0
add
pop pointer 1
push that 0
pop local 1
push constant 0
push local 1
add
pop pointer 1
push that 0
push constant 0
eq
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 0
push local 0
add
push constant 1
push local 0
add
pop pointer 1
push that 0
push local 0
sub
push constant 2
sub
pop temp 0
pop pointer 1
push temp 0
pop that 0
goto IF_END0
label IF_FALSE0
push constant 0
push local 0
add
push constant 1
push local 0
add
pop pointer 1
push that 0
push local 0
sub
push constant 0
push local 1
add
pop pointer 1
push that 0
add
pop temp 0
pop pointer 1
push temp 0
pop that 0
push constant 1
push local 1
add
pop pointer 1
push that 0
push local 1
push constant 2
add
eq
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push constant 1
push local 0
add
push local 0
push constant 2
add
pop temp 0
pop pointer 1
push temp 0
pop that 0
goto IF_END1
label IF_FALSE1
push constant 1
push local 0
add
push constant 1
push local 1
add
pop pointer 1
push that 0
pop temp 0
pop pointer 1
push temp 0
pop that 0
label IF_END1
label IF_END0
push constant 0
return
