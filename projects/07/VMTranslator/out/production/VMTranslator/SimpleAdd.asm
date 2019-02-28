// C_PUSH constant 7
@7
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 8
@8
D=A
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
