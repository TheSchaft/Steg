import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.lang.*;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class Steg {
	
	//Encode messages in images
	public static void main(String[] args) {
		BufferedImage img;
		try {
			String msg = "This is a secret message";
			Scanner strScan = new Scanner(msg);
			String messageBits = "";
			while(strScan.hasNext()) {
				String tok = strScan.next();
				byte[] a_ray = tok.getBytes("US-ASCII");
				System.out.println("Token: " + tok);
				for(byte b: a_ray) {
					String curbs = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
					System.out.println("Curbs: " + curbs);
					messageBits += curbs.substring(1,curbs.length());
				}
				
				//System.out.println("Bytes: " + Integer.toBinaryString(tok.getBytes("US-ASCII")));
			}
			//messageBits.replaceAll("\\s","");
			System.out.println(messageBits);
			img = ImageIO.read(new File("/u/tschaff/Desktop/Steganography_Project/StegTestImage.bmp"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "bmp", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			PrintWriter outputWriter = new PrintWriter("/u/tschaff/Desktop/Steganography_Project/postStegArray.txt");
			int counter;
			int picByteCounter = 4096;
			for(int i = 0; i < picByteCounter; ++i) {
				StringBuilder bitString = new StringBuilder(String.format("%8s", Integer.toBinaryString(imageInByte[i] & 0xFF)).replace(' ', '0'));
				outputWriter.write(bitString.toString() + " ");
			}

			for(counter = 0; counter < messageBits.length(); counter += 2) { //modified bitstrings
				StringBuilder bitString = new StringBuilder(String.format("%8s", Integer.toBinaryString(imageInByte[picByteCounter] & 0xFF)).replace(' ', '0')); //This will represent each bitstring in the message
				//System.out.println("BitString: " + bitString.toString());
				String firstBit = "" +  messageBits.charAt(counter);
				String secondBit = "" + messageBits.charAt(counter + 1); //substring format is (inclusive,exclusive)
				System.out.println("Bits to be added to: " + bitString.toString() + " , " + firstBit + " " + secondBit);
				
				//Replace LSB with message bits
				bitString.replace(6, 7, firstBit);
				bitString.replace(7, 8, secondBit);
				
				//Convert bit string back to byte form
				int intRep = Integer.parseInt(bitString.toString(), 2);
				byte byteRep = (byte)intRep;

				//Replace old byte in array with new byte value
				imageInByte[picByteCounter] = byteRep;

				System.out.println("Byte representation of bit string: " + byteRep);
				System.out.println(bitString.toString() + "\n");
				outputWriter.print(bitString.toString() + " ");
				picByteCounter++;
				StringBuilder currentBS = new StringBuilder();
			}
				
			//original bitstrings
			//Don't think we need any of this, just need to write the new array of bytes to a new bmp file
			System.out.println(picByteCounter);
			for(int i = picByteCounter; i < imageInByte.length; i++) {
				StringBuilder bitString = new StringBuilder(String.format("%8s", Integer.toBinaryString(imageInByte[i] & 0xFF)).replace(' ', '0')); //This will represent each bitstring in the message
				
				outputWriter.print(bitString.toString() + " ");
			}
			//New Steg bmp image
			/*File stegPic = new File("NewStegImage.bmp");
			FileOutputStream fos = new FileOutputStream(stegPic);
			fos.write(imageInByte);
			fos.flush();
			fos.close();*/

			outputWriter.close();
			/*BufferedImage newImg = ImageIO.read(new ByteArrayInputStream(imageInByte));
			File oldImage = new File("SecretMessageImage.bmp");
			ImageIO.write(newImg, "bmp", img);*/
			
			//OutputStream out = new BufferedOutputStream(new FileOutputStream("/u/tschaff/Desktop/Steganography_Project/SecretMessageImage.bmp"));
			//out.write(imageInByte);
			//ImageIO.write()

			System.out.println();
			System.out.println("Length of Byte array: " + imageInByte.length);
			
			System.out.println();
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*//Get user input
	public String getInput() {
		
		Scanner k =  new Scanner(System.in);
		String input;
		
		System.out.println("Give me a message to encode!");
		System.out.print("> ");
		input = k.next();
		
		k.close();
		return input;
	}*/
}
