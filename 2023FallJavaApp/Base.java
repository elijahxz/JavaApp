import java.awt.*;


public class Base {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create a frame
        Frame frame = new Frame("My GUI");

        // Create a label
        Label label = new Label("This is a label");

        // Add the label to the frame
        frame.add(label);

        // Set the size of the frame
        frame.setSize(300, 200);

        // Display the frame
        frame.setVisible(true);
    }
}
