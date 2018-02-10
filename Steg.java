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
				for(byte b: a_ray) {
					String curbs = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
					messageBits += curbs.substring(1,curbs.length());
				}
				
			}

			img = ImageIO.read(new File("/u/tschaff/Desktop/Steganography_Project/StegTestImage.bmp"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "bmp", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			int counter;
			int picByteCounter = 4096;

			for(counter = 0; counter < messageBits.length(); counter += 2) {
				StringBuilder bitString = new StringBuilder(String.format("%8s", Integer.toBinaryString(imageInByte[picByteCounter] & 0xFF)).replace(' ', '0')); //This will represent each bitstring in the message
				String firstBit = "" +  messageBits.charAt(counter);
				String secondBit = "" + messageBits.charAt(counter + 1);

				//Replace LSB with message bits
				bitString.replace(6, 7, firstBit);
				bitString.replace(7, 8, secondBit);
				
				//Convert bit string back to byte form
				int intRep = Integer.parseInt(bitString.toString(), 2);
				byte byteRep = (byte)intRep;

				//Replace old byte in array with new byte value
				imageInByte[picByteCounter] = byteRep;
				picByteCounter++;
			}

			//New Steg bmp image
			File stegPic = new File("NewStegImage.bmp");
			FileOutputStream fos = new FileOutputStream(stegPic);
			fos.write(imageInByte);
			fos.flush();
			fos.close();
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
