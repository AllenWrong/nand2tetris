// Test file for NestedCall test.

load NestedCall.asm,
output-file NestedCall.out,
compare-to NestedCall.cmp,
output-list RAM[0]%D1.6.1 RAM[1]%D1.6.1 RAM[2]%D1.6.1 RAM[3]%D1.6.1 RAM[4]%D1.6.1 RAM[5]%D1.6.1 RAM[6]%D1.6.1;

set RAM[0] 261,
set RAM[1] 261,
set RAM[2] 256,
set RAM[3] -3,
set RAM[4] -4,
set RAM[5] -1, // test results
set RAM[6] -1,
set RAM[256] 1234, // fake stack frame from call Sys.init
set RAM[257] -1,
set RAM[258] -2,
set RAM[259] -3,
set RAM[260] -4,

set RAM[261] -1, // Initialize stack to check for local segment
set RAM[262] -1, // being cleared to zero.
set RAM[263] -1,
set RAM[264] -1,
set RAM[265] -1,
set RAM[266] -1,
set RAM[267] -1,
set RAM[268] -1,
set RAM[269] -1,
set RAM[270] -1,
set RAM[271] -1,
set RAM[272] -1,
set RAM[273] -1,
set RAM[274] -1,
set RAM[275] -1,
set RAM[276] -1,
set RAM[277] -1,
set RAM[278] -1,
set RAM[279] -1,
set RAM[280] -1,
set RAM[281] -1,
set RAM[282] -1,
set RAM[283] -1,
set RAM[284] -1,
set RAM[285] -1,
set RAM[286] -1,
set RAM[287] -1,
set RAM[288] -1,
set RAM[289] -1,
set RAM[290] -1,
set RAM[291] -1,
set RAM[292] -1,
set RAM[293] -1,
set RAM[294] -1,
set RAM[295] -1,
set RAM[296] -1,
set RAM[297] -1,
set RAM[298] -1,
set RAM[299] -1,

repeat 4000 {
  ticktock;
}

output;
