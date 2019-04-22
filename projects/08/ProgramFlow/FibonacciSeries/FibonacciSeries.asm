// push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// pop pointer 1           // that = argument[1]
@SP
M=M-1
A=M
D=M
@THAT
M=D
// push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
// pop that 0              // first element in the series = 0
@0
D=A
@THAT
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
// push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
// pop that 1              // second element in the series = 1
@1
D=A
@THAT
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
// push constant 2
@2
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
// pop argument 0          // num_of_elements -= 2 (first 2 elements are set)
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
// label MAIN_LOOP_START
(FibonacciSeries.$MAIN_LOOP_START)
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
// if-goto COMPUTE_ELEMENT // if num_of_elements > 0, goto COMPUTE_ELEMENT
@SP
M=M-1
A=M
D=M
@FibonacciSeries.$COMPUTE_ELEMENT
D;JNE
// goto END_PROGRAM        // otherwise, goto END_PROGRAM
@FibonacciSeries.$END_PROGRAM
0;JMP
// label COMPUTE_ELEMENT
(FibonacciSeries.$COMPUTE_ELEMENT)
// push that 0
@0
D=A
@THAT
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push that 1
@1
D=A
@THAT
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
// pop that 2              // that[2] = that[0] + that[1]
@2
D=A
@THAT
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
// push pointer 1
@THAT
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
// add
@SP
M=M-1
A=M
D=M
A=A-1
D=D+M
M=D
// pop pointer 1           // that += 1
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
// pop argument 0          // num_of_elements--
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
// goto MAIN_LOOP_START
@FibonacciSeries.$MAIN_LOOP_START
0;JMP
// label END_PROGRAM
(FibonacciSeries.$END_PROGRAM)
