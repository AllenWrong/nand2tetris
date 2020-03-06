function Screen.init 1
push constant 16384
pop static 1
push constant 0
not
pop static 2
push constant 17
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
push constant 16
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
function Screen.clearScreen 1
label WHILE_EXP0
push local 0
push constant 8192
lt
not
if-goto WHILE_END0
push local 0
push static 1
add
push constant 0
pop temp 0
pop pointer 1
push temp 0
pop that 0
push local 0
push constant 1
add
pop local 0
goto WHILE_EXP0
label WHILE_END0
push constant 0
return
function Screen.updateLocation 0
push static 2
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push argument 0
push static 1
add
push argument 0
push static 1
add
pop pointer 1
push that 0
push argument 1
or
pop temp 0
pop pointer 1
push temp 0
pop that 0
goto IF_END0
label IF_FALSE0
push argument 0
push static 1
add
push argument 0
push static 1
add
pop pointer 1
push that 0
push argument 1
not
and
pop temp 0
pop pointer 1
push temp 0
pop that 0
label IF_END0
push constant 0
return
function Screen.setColor 0
push argument 0
pop static 2
push constant 0
return
function Screen.drawPixel 3
push argument 0
push constant 0
lt
push argument 0
push constant 511
gt
or
push argument 1
push constant 0
lt
or
push argument 1
push constant 255
gt
or
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 7
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 0
push constant 16
call Math.divide 2
pop local 0
push argument 0
push local 0
push constant 16
call Math.multiply 2
sub
pop local 1
push argument 1
push constant 32
call Math.multiply 2
push local 0
add
pop local 2
push local 2
push local 1
push static 0
add
pop pointer 1
push that 0
call Screen.updateLocation 2
pop temp 0
push constant 0
return
function Screen.drawConditional 0
push argument 2
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push argument 1
push argument 0
call Screen.drawPixel 2
pop temp 0
goto IF_END0
label IF_FALSE0
push argument 0
push argument 1
call Screen.drawPixel 2
pop temp 0
label IF_END0
push constant 0
return
function Screen.drawLine 11
push argument 0
push constant 0
lt
push argument 2
push constant 511
gt
or
push argument 1
push constant 0
lt
or
push argument 3
push constant 255
gt
or
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 8
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 2
push argument 0
sub
call Math.abs 1
pop local 3
push argument 3
push argument 1
sub
call Math.abs 1
pop local 2
push local 3
push local 2
lt
pop local 6
push local 6
push argument 3
push argument 1
lt
and
push local 6
not
push argument 2
push argument 0
lt
and
or
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push argument 0
pop local 4
push argument 2
pop argument 0
push local 4
pop argument 2
push argument 1
pop local 4
push argument 3
pop argument 1
push local 4
pop argument 3
label IF_FALSE1
push local 6
if-goto IF_TRUE2
goto IF_FALSE2
label IF_TRUE2
push local 3
pop local 4
push local 2
pop local 3
push local 4
pop local 2
push argument 1
pop local 1
push argument 0
pop local 0
push argument 3
pop local 8
push argument 0
push argument 2
gt
pop local 7
goto IF_END2
label IF_FALSE2
push argument 0
pop local 1
push argument 1
pop local 0
push argument 2
pop local 8
push argument 1
push argument 3
gt
pop local 7
label IF_END2
push constant 2
push local 2
call Math.multiply 2
push local 3
sub
pop local 5
push constant 2
push local 2
call Math.multiply 2
pop local 9
push constant 2
push local 2
push local 3
sub
call Math.multiply 2
pop local 10
push local 1
push local 0
push local 6
call Screen.drawConditional 3
pop temp 0
label WHILE_EXP0
push local 1
push local 8
lt
not
if-goto WHILE_END0
push local 5
push constant 0
lt
if-goto IF_TRUE3
goto IF_FALSE3
label IF_TRUE3
push local 5
push local 9
add
pop local 5
goto IF_END3
label IF_FALSE3
push local 5
push local 10
add
pop local 5
push local 7
if-goto IF_TRUE4
goto IF_FALSE4
label IF_TRUE4
push local 0
push constant 1
sub
pop local 0
goto IF_END4
label IF_FALSE4
push local 0
push constant 1
add
pop local 0
label IF_END4
label IF_END3
push local 1
push constant 1
add
pop local 1
push local 1
push local 0
push local 6
call Screen.drawConditional 3
pop temp 0
goto WHILE_EXP0
label WHILE_END0
push constant 0
return
function Screen.drawRectangle 9
push argument 0
push argument 2
gt
push argument 1
push argument 3
gt
or
push argument 0
push constant 0
lt
or
push argument 2
push constant 511
gt
or
push argument 1
push constant 0
lt
or
push argument 3
push constant 255
gt
or
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 9
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 0
push constant 16
call Math.divide 2
pop local 3
push argument 0
push local 3
push constant 16
call Math.multiply 2
sub
pop local 7
push argument 2
push constant 16
call Math.divide 2
pop local 4
push argument 2
push local 4
push constant 16
call Math.multiply 2
sub
pop local 8
push local 7
push static 0
add
pop pointer 1
push that 0
push constant 1
sub
not
pop local 6
push local 8
push constant 1
add
push static 0
add
pop pointer 1
push that 0
push constant 1
sub
pop local 5
push argument 1
push constant 32
call Math.multiply 2
push local 3
add
pop local 0
push local 4
push local 3
sub
pop local 2
label WHILE_EXP0
push argument 1
push argument 3
gt
not
not
if-goto WHILE_END0
push local 0
push local 2
add
pop local 1
push local 2
push constant 0
eq
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push local 0
push local 5
push local 6
and
call Screen.updateLocation 2
pop temp 0
goto IF_END1
label IF_FALSE1
push local 0
push local 6
call Screen.updateLocation 2
pop temp 0
push local 0
push constant 1
add
pop local 0
label WHILE_EXP1
push local 0
push local 1
lt
not
if-goto WHILE_END1
push local 0
push constant 1
neg
call Screen.updateLocation 2
pop temp 0
push local 0
push constant 1
add
pop local 0
goto WHILE_EXP1
label WHILE_END1
push local 1
push local 5
call Screen.updateLocation 2
pop temp 0
label IF_END1
push argument 1
push constant 1
add
pop argument 1
push local 1
push constant 32
add
push local 2
sub
pop local 0
goto WHILE_EXP0
label WHILE_END0
push constant 0
return
function Screen.drawHorizontal 11
push argument 1
push argument 2
call Math.min 2
pop local 7
push argument 1
push argument 2
call Math.max 2
pop local 8
push argument 0
push constant 1
neg
gt
push argument 0
push constant 256
lt
and
push local 7
push constant 512
lt
and
push local 8
push constant 1
neg
gt
and
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push local 7
push constant 0
call Math.max 2
pop local 7
push local 8
push constant 511
call Math.min 2
pop local 8
push local 7
push constant 16
call Math.divide 2
pop local 1
push local 7
push local 1
push constant 16
call Math.multiply 2
sub
pop local 9
push local 8
push constant 16
call Math.divide 2
pop local 2
push local 8
push local 2
push constant 16
call Math.multiply 2
sub
pop local 10
push local 9
push static 0
add
pop pointer 1
push that 0
push constant 1
sub
not
pop local 5
push local 10
push constant 1
add
push static 0
add
pop pointer 1
push that 0
push constant 1
sub
pop local 4
push argument 0
push constant 32
call Math.multiply 2
push local 1
add
pop local 0
push local 2
push local 1
sub
pop local 6
push local 0
push local 6
add
pop local 3
push local 6
push constant 0
eq
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push local 0
push local 4
push local 5
and
call Screen.updateLocation 2
pop temp 0
goto IF_END1
label IF_FALSE1
push local 0
push local 5
call Screen.updateLocation 2
pop temp 0
push local 0
push constant 1
add
pop local 0
label WHILE_EXP0
push local 0
push local 3
lt
not
if-goto WHILE_END0
push local 0
push constant 1
neg
call Screen.updateLocation 2
pop temp 0
push local 0
push constant 1
add
pop local 0
goto WHILE_EXP0
label WHILE_END0
push local 3
push local 4
call Screen.updateLocation 2
pop temp 0
label IF_END1
label IF_FALSE0
push constant 0
return
function Screen.drawSymetric 0
push argument 1
push argument 3
sub
push argument 0
push argument 2
add
push argument 0
push argument 2
sub
call Screen.drawHorizontal 3
pop temp 0
push argument 1
push argument 3
add
push argument 0
push argument 2
add
push argument 0
push argument 2
sub
call Screen.drawHorizontal 3
pop temp 0
push argument 1
push argument 2
sub
push argument 0
push argument 3
sub
push argument 0
push argument 3
add
call Screen.drawHorizontal 3
pop temp 0
push argument 1
push argument 2
add
push argument 0
push argument 3
sub
push argument 0
push argument 3
add
call Screen.drawHorizontal 3
pop temp 0
push constant 0
return
function Screen.drawCircle 3
push argument 0
push constant 0
lt
push argument 0
push constant 511
gt
or
push argument 1
push constant 0
lt
or
push argument 1
push constant 255
gt
or
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 12
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 0
push argument 2
sub
push constant 0
lt
push argument 0
push argument 2
add
push constant 511
gt
or
push argument 1
push argument 2
sub
push constant 0
lt
or
push argument 1
push argument 2
add
push constant 255
gt
or
if-goto IF_TRUE1
goto IF_FALSE1
label IF_TRUE1
push constant 13
call Sys.error 1
pop temp 0
label IF_FALSE1
push argument 2
pop local 1
push constant 1
push argument 2
sub
pop local 2
push argument 0
push argument 1
push local 0
push local 1
call Screen.drawSymetric 4
pop temp 0
label WHILE_EXP0
push local 1
push local 0
gt
not
if-goto WHILE_END0
push local 2
push constant 0
lt
if-goto IF_TRUE2
goto IF_FALSE2
label IF_TRUE2
push local 2
push constant 2
push local 0
call Math.multiply 2
add
push constant 3
add
pop local 2
goto IF_END2
label IF_FALSE2
push local 2
push constant 2
push local 0
push local 1
sub
call Math.multiply 2
add
push constant 5
add
pop local 2
push local 1
push constant 1
sub
pop local 1
label IF_END2
push local 0
push constant 1
add
pop local 0
push argument 0
push argument 1
push local 0
push local 1
call Screen.drawSymetric 4
pop temp 0
goto WHILE_EXP0
label WHILE_END0
push constant 0
return
