// function Sys.init 0
(Sys.init)
// push constant 4000	// test THIS and THAT context save
@4000
D=A
@SP
M=M+1
A=M-1
M=D
// pop pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
// push constant 5000
@5000
D=A
@SP
M=M+1
A=M-1
M=D
// pop pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
// call Sys.main 0
@Sys.init$ret.0
D=A
@SP
A=M
M=D
@LCL
D=M
@SP
M=M+1
A=M
M=D
@ARG
D=M
@SP
M=M+1
A=M
M=D
@THIS
D=M
@SP
M=M+1
A=M
M=D
@THAT
D=M
@SP
M=M+1
A=M
M=D
@SP
M=M+1
@5
D=A
@SP
D=M-D
@0
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.main
0;JMP
(Sys.init$ret.0)
// pop temp 1
@SP
M=M-1
A=M
D=M
@6
M=D
// label LOOP
(Sys.init$LOOP)
// goto LOOP
@Sys.init$LOOP
0;JMP
// function Sys.main 5
(Sys.main)
@0
D=A
@SP
M=M+1
A=M-1
M=D
@0
D=A
@SP
M=M+1
A=M-1
M=D
@0
D=A
@SP
M=M+1
A=M-1
M=D
@0
D=A
@SP
M=M+1
A=M-1
M=D
@0
D=A
@SP
M=M+1
A=M-1
M=D
// push constant 4001
@4001
D=A
@SP
M=M+1
A=M-1
M=D
// pop pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
// push constant 5001
@5001
D=A
@SP
M=M+1
A=M-1
M=D
// pop pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
// push constant 200
@200
D=A
@SP
M=M+1
A=M-1
M=D
// pop local 1
@1
D=A
@LCL
D=D+M
@temp
M=D
@SP
M=M-1
A=M
D=M
@temp
A=M
M=D
// push constant 40
@40
D=A
@SP
M=M+1
A=M-1
M=D
// pop local 2
@2
D=A
@LCL
D=D+M
@temp
M=D
@SP
M=M-1
A=M
D=M
@temp
A=M
M=D
// push constant 6
@6
D=A
@SP
M=M+1
A=M-1
M=D
// pop local 3
@3
D=A
@LCL
D=D+M
@temp
M=D
@SP
M=M-1
A=M
D=M
@temp
A=M
M=D
// push constant 123
@123
D=A
@SP
M=M+1
A=M-1
M=D
// call Sys.add12 1
@Sys.main$ret.0
D=A
@SP
A=M
M=D
@LCL
D=M
@SP
M=M+1
A=M
M=D
@ARG
D=M
@SP
M=M+1
A=M
M=D
@THIS
D=M
@SP
M=M+1
A=M
M=D
@THAT
D=M
@SP
M=M+1
A=M
M=D
@SP
M=M+1
@5
D=A
@SP
D=M-D
@1
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.add12
0;JMP
(Sys.main$ret.0)
// pop temp 0
@SP
M=M-1
A=M
D=M
@5
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
// push local 1
@1
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push local 2
@2
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push local 3
@3
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push local 4
@4
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
// add
@SP
M=M-1
A=M
D=M
A=A-1
D=D+M
M=D
// add
@SP
M=M-1
A=M
D=M
A=A-1
D=D+M
M=D
// add
@SP
M=M-1
A=M
D=M
A=A-1
D=D+M
M=D
// return
@LCL
D=M
@5
A=D-A
D=M
@temp
M=D
@SP
A=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@LCL
A=M-1
D=M
@THAT
M=D
@LCL
D=M
@2
A=D-A
D=M
@THIS
M=D
@LCL
D=M
@3
A=D-A
D=M
@ARG
M=D
@LCL
D=M
@4
A=D-A
D=M
@LCL
M=D
@temp
A=M
0;JMP
// function Sys.add12 0
(Sys.add12)
// push constant 4002
@4002
D=A
@SP
M=M+1
A=M-1
M=D
// pop pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
// push constant 5002
@5002
D=A
@SP
M=M+1
A=M-1
M=D
// pop pointer 1
@SP
M=M-1
A=M
D=M
@THAT
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
// push constant 12
@12
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
// return
@LCL
D=M
@5
A=D-A
D=M
@temp
M=D
@SP
A=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@LCL
A=M-1
D=M
@THAT
M=D
@LCL
D=M
@2
A=D-A
D=M
@THIS
M=D
@LCL
D=M
@3
A=D-A
D=M
@ARG
M=D
@LCL
D=M
@4
A=D-A
D=M
@LCL
M=D
@temp
A=M
0;JMP
