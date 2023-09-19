import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import javax.swing.JFrame;


public class Base {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create a frame
        Frame frame = new Frame("My GUI");
        Button closeTester = new Button("Click for Luck");
        TextField tf = new TextField();
        closeTester.setBounds(150, 100, 100, 50);
        frame.add(closeTester);
        
        // Create a label
        Label label = new Label("This is a label");

        // Add the label to the frame
        frame.add(label);
        
        // Set the size of the frame
        frame.setSize(300, 200);

        // Display the frame
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		System.exit(0);
        	}
        });
        
        
    }
}
