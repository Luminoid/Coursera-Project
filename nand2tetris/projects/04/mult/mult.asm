// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

@R2
M=0     // R2=0

(LOOP)
@R1
D=M     // D=R1
@END
D;JEQ   // If R1=0 goto END
@R1
M=M-1   // R1=R1-1
@R0
D=M     // D=R0
@R2
M=D+M   // R2=R0+R2
@LOOP
0;JMP   // goto LOOP

(END)
@END
0;JMP   // Infinite loop
