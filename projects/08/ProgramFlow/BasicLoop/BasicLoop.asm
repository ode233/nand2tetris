// push constant 0    
@0
D=A
@SP
M=M+1
A=M-1
M=D
// pop local 0         // initializes sum = 0
@0
D=A
@LCL
D=D+M
@5
M=D
@SP
M=M-1
A=M
D=M
@5
A=M
M=D
// label LOOP_START
(BasicLoop.$LOOP_START)
// push argument 0    
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// add
@SP
M=M-1
A=M
D=M
A=A-1
D=D+M
M=D
// pop local 0	        // sum = sum + counter
@0
D=A
@LCL
D=D+M
@5
M=D
@SP
M=M-1
A=M
D=M
@5
A=M
M=D
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
// sub
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=D
// pop argument 0      // counter--
@0
D=A
@ARG
D=D+M
@5
M=D
@SP
M=M-1
A=M
D=M
@5
A=M
M=D
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// if-goto LOOP_START  // If counter > 0, goto LOOP_START
@SP
M=M-1
A=M
D=M
@BasicLoop.$LOOP_START
D;JNE
// push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
