import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.ArrayList;

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
					String curbs = String.format("%8s", Integer.toBinaryString(b & 0xFF).replace(' ', '0'));
					System.out.println("Curbs: " + curbs);
					messageBits += curbs.substring(1,curbs.length());
				}
				
				//System.out.println("Bytes: " + Integer.toBinaryString(tok.getBytes("US-ASCII")));
			}
			messageBits.replaceAll("\\s","");
			System.out.println(messageBits);
			img = ImageIO.read(new File("/u/tschaff/Desktop/Steganography_Project/StegTestImage.bmp"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "bmp", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			PrintWriter outputWriter = new PrintWriter("/u/tschaff/Desktop/Steganography_Project/byteArray2.txt");
			int counter;
			int picByteCounter = 0;
			for(counter = 0; counter < messageBits.length(); counter += 2) { //modified bitstrings
				StringBuilder bitString = new StringBuilder(String.format("%8s", Integer.toBinaryString(imageInByte[picByteCounter] & 0xFF).replace(' ', '0'))); //This will represent each bitstring in the message
				System.out.println("BitString: " + bitString.toString());
				String firstBit = "" +  messageBits.charAt(counter);
				String secondBit = "" + messageBits.charAt(counter + 1); //substring format is (inclusive,exclusive)
				System.out.println("Bits to be added to: " + bitString.toString() + " , " + firstBit + " " + secondBit);
				bitString.replace(6, 7, firstBit);
				bitString.replace(7, 8, secondBit);
				System.out.println(bitString.toString());
				outputWriter.print(bitString.toString());
				picByteCounter++;
				StringBuilder currentBS = new StringBuilder();
			}
				
			//original bitstrings
			System.out.println(picByteCounter);
			for(int i = picByteCounter; i < imageInByte.length; i++) {
				StringBuilder bitString = new StringBuilder(String.format("%8s", Integer.toBinaryString(imageInByte[i] & 0xFF).replace(' ', '0'))); //This will represent each bitstring in the message
				
				outputWriter.print(bitString);
			}  
			outputWriter.close();
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
