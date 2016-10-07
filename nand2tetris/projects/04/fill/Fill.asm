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

(INIT)
// R0(pixel)=screen
@SCREEN
D=A
@R0
M=D
// R1(pressed)=0
@R1
M=0

// Get key pressed event
(KEYPRESSED)
@KBD
D=M
@UNPRESSED
D;JEQ

// Set pressed value
(PRESSED)
@R1
M=-1
@LOOP
0;JMP
(UNPRESSED)
@R1
M=0

(LOOP)
// if(R0==16384+8192) goto END
@24576
D=A
@R0
D=D-M
@INIT
D;JEQ

// RAM[SCREEN+i]=pressed
@R1
D=M
@R0
A=M
M=D

// i++
@R0
M=M+1

@LOOP
0;JMP
