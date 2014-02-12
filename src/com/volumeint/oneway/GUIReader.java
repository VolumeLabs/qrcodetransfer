package com.volumeint.oneway;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.ImageReader;
import com.google.zxing.common.HybridBinarizer;

public class GUIReader implements WebcamMotionListener {
	String old_text;
	public GUIReader() {
		old_text = "";
		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(500); // one check per 500 ms
		detector.addMotionListener(this);
		detector.start();
	}

	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		
		//System.out.println("Change");
		// get default webcam and open it
		Webcam webcam = Webcam.getDefault();
		webcam.open();

		// get image
		BufferedImage image = webcam.getImage();
		
		String text = getDecodeText(image);
		if (!text.equals(old_text)) {
			old_text = text;
	
			try {
				//PrintWriter out = new PrintWriter("C:\\Users\\brad\\fileRecieved.txt");
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\brad\\fileRecieved.txt", true)));
				out.print(text);
				System.out.println(text);
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	  private static String getDecodeText(BufferedImage image) {
	    
	    LuminanceSource source = new BufferedImageLuminanceSource(image);
	    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	    Result result;
	    try {
	      result = new MultiFormatReader().decode(bitmap);
	    } catch (ReaderException re) {
	      return "";
	    }
	    return String.valueOf(result.getText());
	 }
	
	public static void main(String[] args) throws IOException {
		new GUIReader();
		System.in.read(); // keep program open
	}
}