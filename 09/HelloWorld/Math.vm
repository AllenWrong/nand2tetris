function Math.init 1
push constant 16
call Array.new 1
pop static 1
push constant 16
call Array.new 1
pop static 0
push constant 0
push static 0
add
push constant 1
pop temp 0
pop pointer 1
push temp 0
pop that 0
label WHILE_EXP0
push local 0
push constant 15
lt
not
if-goto WHILE_END0
push local 0
push constant 1
add
pop local 0
push local 0
push static 0
add
push local 0
push constant 1
sub
push static 0
add
pop pointer 1
push that 0
push local 0
push constant 1
sub
push static 0
add
pop pointer 1
push that 0
add
pop temp 0
pop pointer 1
push temp 0
pop that 0
goto WHILE_EXP0
label WHILE_END0
push constant 0
return
function Math.abs 0
push argument 0
push constant 0
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push argument 0
neg
pop argument 0
label IF_FALSE0
push argument 0
return
function Math.multiply 5
push argument 0
push constant 0
lt
push argument 1
push constant 0
gt
and
push argument 0
push constant 0
gt
push argument 1
push constant 0
lt
and
or
pop local 4
push argument 0
call Math.abs 1
pop argument 0
push argument 1
call Math.abs 1
pop argument 1
push argument 0
push argument 1
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push argument 0
pop local 1
push argument 1
pop argument 0
push local 1
pop argument 1
label IF_FALSE0
label WHILE_EXP0
push local 2
push constant 1
sub
push argument 1
push constant 1
sub
lt
not
if-goto WHILE_END0
push local 3
push static 0
add
pop pointer 1
push that 0
push argument 1
and
push constant 0
eq
not
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push local 0
push argument 0
add
pop local 0
push local 2
push local 3
push static 0
add
pop pointer 1
push that 0
add
pop local 2
label IF_FALSE1
push argument 0
push argument 0
add
pop argument 0
push local 3
push constant 1
add
pop local 3
goto WHILE_EXP0
label WHILE_END0
push local 4
if-goto IF_TRUE2
goto IF_FALSE2
label IF_TRUE2
push local 0
neg
pop local 0
label IF_FALSE2
push local 0
return
function Math.divide 4
push argument 1
push constant 0
eq
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 3
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 0
push constant 0
lt
push argument 1
push constant 0
gt
and
push argument 0
push constant 0
gt
push argument 1
push constant 0
lt
and
or
pop local 2
push constant 0
push static 1
add
push argument 1
call Math.abs 1
pop temp 0
pop pointer 1
push temp 0
pop that 0
push argument 0
call Math.abs 1
pop argument 0
label WHILE_EXP0
push local 0
push constant 15
lt
push local 3
not
and
not
if-goto WHILE_END0
push constant 32767
push local 0
push static 1
add
pop pointer 1
push that 0
push constant 1
sub
sub
push local 0
push static 1
add
pop pointer 1
push that 0
push constant 1
sub
lt
pop local 3
push local 3
not
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push local 0
push constant 1
add
push static 1
add
push local 0
push static 1
add
pop pointer 1
push that 0
push local 0
push static 1
add
pop pointer 1
push that 0
add
pop temp 0
pop pointer 1
push temp 0
pop that 0
push local 0
push constant 1
add
push static 1
add
pop pointer 1
push that 0
push constant 1
sub
push argument 0
push constant 1
sub
gt
pop local 3
push local 3
not
if-goto IF_TRUE2
goto IF_FALSE2
label IF_TRUE2
push local 0
push constant 1
add
pop local 0
label IF_FALSE2
label IF_FALSE1
goto WHILE_EXP0
label WHILE_END0
label WHILE_EXP1
push local 0
push constant 1
neg
gt
not
if-goto WHILE_END1
push local 0
push static 1
add
pop pointer 1
push that 0
push constant 1
sub
push argument 0
push constant 1
sub
gt
not
if-goto IF_TRUE3
goto IF_FALSE3
label IF_TRUE3
push local 1
push local 0
push static 0
add
pop pointer 1
push that 0
add
pop local 1
push argument 0
push local 0
push static 1
add
pop pointer 1
push that 0
sub
pop argument 0
label IF_FALSE3
push local 0
push constant 1
sub
pop local 0
goto WHILE_EXP1
label WHILE_END1
push local 2
if-goto IF_TRUE4
goto IF_FALSE4
label IF_TRUE4
push local 1
neg
pop local 1
label IF_FALSE4
push local 1
return
function Math.sqrt 4
push argument 0
push constant 0
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 4
call Sys.error 1
pop temp 0
label IF_FALSE0
push constant 7
pop local 0
label WHILE_EXP0
push local 0
push constant 1
neg
gt
not
if-goto WHILE_END0
push local 3
push local 0
push static 0
add
pop pointer 1
push that 0
add
pop local 1
push local 1
push local 1
call Math.multiply 2
pop local 2
push local 2
push argument 0
gt
not
push local 2
push constant 0
lt
not
and
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push local 1
pop local 3
label IF_FALSE1
push local 0
push constant 1
sub
pop local 0
goto WHILE_EXP0
label WHILE_END0
push local 3
return
function Math.max 0
push argument 0
push argument 1
gt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push argument 0
pop argument 1
label IF_FALSE0
push argument 1
return
function Math.min 0
push argument 0
push argument 1
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push argument 0
pop argument 1
label IF_FALSE0
push argument 1
return
