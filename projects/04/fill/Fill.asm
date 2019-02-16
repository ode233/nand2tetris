// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
(LOOP)	
	@i
	M=0
	@8192
	D=A
	@n
	M=D
	
	@KBD
	D=M
	@WHITELOOP
	D;JEQ	// if KBD=0 goto WHITELOOP

(BLACKLOOP)
	@i
	D=M
	@n
	D=D-M
	@LOOP
	D;JGE	// if i >= n goto LOOP
	
	@SCREEN
	D=A
	@i
	A=D+M
	M=-1	// make the ith SCREEN regester relatived pixel to black
	
	@i
	M=M+1	// i++
	
	@BLACKLOOP
	0;JMP	// goto BLACKLOOP
	
(WHITELOOP)
	@i
	D=M
	@n
	D=D-M
	@LOOP
	D;JGE	// if i >= n goto LOOP
	
	@SCREEN
	D=A
	@i
	A=D+M
	M=0	// make the ith SCREEN regester relatived pixel to white
	
	@i
	M=M+1	// i++
	
	@WHITELOOP
	0;JMP	// goto WHITELOOP
	
	