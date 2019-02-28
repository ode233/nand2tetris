// C_PUSH constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// eq
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@EQ_0
D;JEQ
@EQ_END_0
0;JEQ
(EQ_0)
@SP
A=M-1
M=-1
(EQ_END_0)
// C_PUSH constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 16
@16
D=A
@SP
M=M+1
A=M-1
M=D
// eq
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@EQ_1
D;JEQ
@EQ_END_1
0;JEQ
(EQ_1)
@SP
A=M-1
M=-1
(EQ_END_1)
// C_PUSH constant 16
@16
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 17
@17
D=A
@SP
M=M+1
A=M-1
M=D
// eq
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@EQ_2
D;JEQ
@EQ_END_2
0;JEQ
(EQ_2)
@SP
A=M-1
M=-1
(EQ_END_2)
// C_PUSH constant 892
@892
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@LT_3
D;JLT
@LT_END_3
0;JEQ
(LT_3)
@SP
A=M-1
M=-1
(LT_END_3)
// C_PUSH constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 892
@892
D=A
@SP
M=M+1
A=M-1
M=D
// lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@LT_4
D;JLT
@LT_END_4
0;JEQ
(LT_4)
@SP
A=M-1
M=-1
(LT_END_4)
// C_PUSH constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 891
@891
D=A
@SP
M=M+1
A=M-1
M=D
// lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@LT_5
D;JLT
@LT_END_5
0;JEQ
(LT_5)
@SP
A=M-1
M=-1
(LT_END_5)
// C_PUSH constant 32767
@32767
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// gt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@GT_6
D;JGT
@GT_END_6
0;JEQ
(GT_6)
@SP
A=M-1
M=-1
(GT_END_6)
// C_PUSH constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 32767
@32767
D=A
@SP
M=M+1
A=M-1
M=D
// gt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@GT_7
D;JGT
@GT_END_7
0;JEQ
(GT_7)
@SP
A=M-1
M=-1
(GT_END_7)
// C_PUSH constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 32766
@32766
D=A
@SP
M=M+1
A=M-1
M=D
// gt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
M=0
@GT_8
D;JGT
@GT_END_8
0;JEQ
(GT_8)
@SP
A=M-1
M=-1
(GT_END_8)
// C_PUSH constant 57
@57
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 31
@31
D=A
@SP
M=M+1
A=M-1
M=D
// C_PUSH constant 53
@53
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
// C_PUSH constant 112
@112
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
// neg
@SP
A=M-1
M=-M
// and
@SP
M=M-1
A=M
D=M
A=A-1
D=D&M
M=D
// C_PUSH constant 82
@82
D=A
@SP
M=M+1
A=M-1
M=D
// or
@SP
M=M-1
A=M
D=M
A=A-1
D=D|M
M=D
// not
@SP
A=M-1
M=!M
