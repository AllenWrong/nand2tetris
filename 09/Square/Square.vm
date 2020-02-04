function Square.new 0
push constant 3
call Memory.alloc 1
pop pointer 0
push argument 0
pop this 0
push argument 1
pop this 1
push argument 2
pop this 2
push pointer 0
call Square.draw 1
pop temp 0
push pointer 0
return
function Square.dispose 0
push argument 0
pop pointer 0
push pointer 0
call Memory.deAlloc 1
pop temp 0
push constant 0
return
function Square.draw 0
push argument 0
pop pointer 0
push constant 0
not
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 0
push this 2
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
push constant 0
return
function Square.erase 0
push argument 0
pop pointer 0
push constant 0
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 0
push this 2
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
push constant 0
return
function Square.incSize 0
push argument 0
pop pointer 0
push this 1
push this 2
add
push constant 254
lt
push this 0
push this 2
add
push constant 510
lt
and
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push pointer 0
call Square.erase 1
pop temp 0
push this 2
push constant 2
add
pop this 2
push pointer 0
call Square.draw 1
pop temp 0
label IF_FALSE0
push constant 0
return
function Square.decSize 0
push argument 0
pop pointer 0
push this 2
push constant 2
gt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push pointer 0
call Square.erase 1
pop temp 0
push this 2
push constant 2
sub
pop this 2
push pointer 0
call Square.draw 1
pop temp 0
label IF_FALSE0
push constant 0
return
function Square.moveUp 0
push argument 0
pop pointer 0
push this 1
push constant 1
gt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 0
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 2
add
push constant 1
sub
push this 0
push this 2
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
push this 1
push constant 2
sub
pop this 1
push constant 0
not
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 0
push this 2
add
push this 1
push constant 1
add
call Screen.drawRectangle 4
pop temp 0
label IF_FALSE0
push constant 0
return
function Square.moveDown 0
push argument 0
pop pointer 0
push this 1
push this 2
add
push constant 254
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 0
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 0
push this 2
add
push this 1
push constant 1
add
call Screen.drawRectangle 4
pop temp 0
push this 1
push constant 2
add
pop this 1
push constant 0
not
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 2
add
push constant 1
sub
push this 0
push this 2
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
label IF_FALSE0
push constant 0
return
function Square.moveLeft 0
push argument 0
pop pointer 0
push this 0
push constant 1
gt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 0
call Screen.setColor 1
pop temp 0
push this 0
push this 2
add
push constant 1
sub
push this 1
push this 0
push this 2
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
push this 0
push constant 2
sub
pop this 0
push constant 0
not
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 0
push constant 1
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
label IF_FALSE0
push constant 0
return
function Square.moveRight 0
push argument 0
pop pointer 0
push this 0
push this 2
add
push constant 510
lt
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 0
call Screen.setColor 1
pop temp 0
push this 0
push this 1
push this 0
push constant 1
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
push this 0
push constant 2
add
pop this 0
push constant 0
not
call Screen.setColor 1
pop temp 0
push this 0
push this 2
add
push constant 1
sub
push this 1
push this 0
push this 2
add
push this 1
push this 2
add
call Screen.drawRectangle 4
pop temp 0
label IF_FALSE0
push constant 0
return
