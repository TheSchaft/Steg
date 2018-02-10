Instructions on how to run the code:
	Make sure you have an up-to-date version of the java compiler.
	Go to line 24 and input your message of choice.
	Go to line 36 and input the path of your original steg photo.
	Go to line 64 and input the name you desire for the new steg photo.
	If using terminal/bash, go to project directory and type "javac Steg.java" then type "java Steg"
	It should take your original photo, encode the desired message, and produce the new steganographic message.


Group Information (name, group members, etc)
	The Bad Briyonces
	Briana Bradshaw: bmb3377
	Taylor Schaffner: tls3494

b) An introduction to your overall project
	Our project was to encrypt a message into a photo using a steganographic algorithm of our choosing. We wrote it
	in java. We used the LSB algorithm and the BMP image format.

c) A brief explanation of the algorithm you selected to implement
	First we convert the desired message into a chain of bits. Then we decompose the photo into an arrya of bytes.
	After that we convert some of the bytes to bitstrings and replace the last 2 bits on the bitstring with 2 of
	our message bits. We do this until we have encoded the entire message. Then we convert the string back to 
	a byte and replace the original byte in the byte array with our new encoded byte.

d) A quick example of how the algorithm functions (by example) nothing big or extensive but enough to show the working of the algorithm

	Original byte array:	00011001 01010101 10101010		Message: 00110101 00110101	New BA:	00011000 01010111 10101001
							11111111 11110001 01010111											11111101 11110000 01010111		
							00001110 11011010 11010101											00001101 11011001 11010101

e) A flowchart, bulletpoint list, or something that outlines the major points of your program and algorithm

	*lines 24-35: Input message, create string of literal bits to represent target message.
	*lines 37-42: Read in input photo and create byte array representation.
	*line  44: We chose an arbitrary byte value to start our encryption at. We knew this value was large enough to not be contained within the BMP file header.
	*line  47: Grab the current bitstring the represent the byte we are going to manipulate.
	*lines 48-49: Grab the next bits to encode from the message.
	*lines 52-53: Replace original bits from byte w/in photo to the message bits
	*lines 56-60: Replace the original byte in the photo with the new encrypted byte.
	*lines 46-62: Repeat this process for message.length()/2 iterations. 
	*line  65: Create new output file.
	*lines 66-69: Write out new encrpyted photo.
