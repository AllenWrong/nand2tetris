function Array.new 0
push argument 0
push constant 0
gt
not
if-goto IF_TRUE0
goto IF_FALSE0
label IF_TRUE0
push constant 2
call Sys.error 1
pop temp 0
label IF_FALSE0
push argument 0
call Memory.alloc 1
return
function Array.dispose 0
push argument 0
pop pointer 0
push pointer 0
call Memory.deAlloc 1
pop temp 0
push constant 0
return
