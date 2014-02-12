package com.volumeint.oneway;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.google.zxing.WriterException;


public final class GUIPlayer extends JFrame {

	  public JLabel imageLabel;
	  private final JTextArea textArea;

	  private GUIPlayer() {
	    imageLabel = new JLabel();
	    textArea = new JTextArea();
	    textArea.setEditable(false);
	    textArea.setMaximumSize(new Dimension(400, 200));
	    Container panel = new JPanel();
	    panel.setLayout(new FlowLayout());
	    panel.add(imageLabel);
	    panel.add(textArea);
	    setTitle("Player");
	    setSize(1000, 1000);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setContentPane(panel);
	    setLocationRelativeTo(null);
	  }

	  public static void main(String[] args) throws WriterException, IOException, InterruptedException {
	    GUIPlayer runner = new GUIPlayer();
	    GenerateQRCode generate = new GenerateQRCode();
	    runner.setVisible(true);
	    String qrCodeText = "http://www.volumeintigration.com";
        String filePath = "filetosend.txt";
        int size = 800;
        
        // 4296 characters allowed
        File fileToSend = new File(filePath);
        qrCodeText = readFile(filePath);
        
        
        
        String[] textArray = splitStringEvery(qrCodeText, 100);
        
        for (String s : textArray) {
        
        	BufferedImage image  = generate.createQRImage(s, size);

        	runner.imageLabel.setIcon(new ImageIcon(image));
        	Thread.sleep(1000);
        }
	  }
	  
	  public static String[] splitStringEvery(String s, int interval) {
		    int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
		    String[] result = new String[arrayLength];

		    int j = 0;
		    int lastIndex = result.length - 1;
		    for (int i = 0; i < lastIndex; i++) {
		        result[i] = s.substring(j, j + interval);
		        j += interval;
		    } //Add the last bit
		    result[lastIndex] = s.substring(j);

		    return result;
		}
	  
	  private static String readFile(String pathname) throws IOException {

		    File file = new File(pathname);
		    StringBuilder fileContents = new StringBuilder((int)file.length());
		    Scanner scanner = new Scanner(file);
		    String lineSeparator = System.getProperty("line.separator");

		    try {
		        while(scanner.hasNextLine()) {        
		            fileContents.append(scanner.nextLine() + lineSeparator);
		        }
		        return fileContents.toString();
		    } finally {
		        scanner.close();
		    }
		}
	  
}