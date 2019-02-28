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
@EQ
D;JEQ
(EQ_END
(EQ)
@SP
A=A-1
M=-1
(EQ_END)
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
@EQ
D;JEQ
(EQ_END
(EQ)
@SP
A=A-1
M=-1
(EQ_END)
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
@EQ
D;JEQ
(EQ_END
(EQ)
@SP
A=A-1
M=-1
(EQ_END)
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
@LT
D;JLT
(LT_END
(LT)
@SP
A=A-1
M=-1
(LT_END)
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
@LT
D;JLT
(LT_END
(LT)
@SP
A=A-1
M=-1
(LT_END)
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
@LT
D;JLT
(LT_END
(LT)
@SP
A=A-1
M=-1
(LT_END)
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
@GT
D;JGT
(GT_END
(GT)
@SP
A=A-1
M=-1
(GT_END)
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
@GT
D;JGT
(GT_END
(GT)
@SP
A=A-1
M=-1
(GT_END)
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
@GT
D;JGT
(GT_END
(GT)
@SP
A=A-1
M=-1
(GT_END)
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
