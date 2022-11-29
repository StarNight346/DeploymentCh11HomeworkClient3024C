import java.net.*;
import java.io.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PrimeClient {


		private static void constructGUI() {
			 JFrame.setDefaultLookAndFeelDecorated(true);
			 MyFrame frame = new MyFrame();
			 frame.setVisible(true); 
			}

		//Main method
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
				constructGUI();
				}
				});				
		}
	}

	//Creating a class for the button listener
	class MyButtonListener implements ActionListener {
		MyFrame fr;
		public MyButtonListener(MyFrame frame)
		{
			fr = frame;
		}

		//Assigning a button for to be used to determine if a number is prime 
		public void actionPerformed(ActionEvent e) 
		{
		
			try {
				//creating the Readers and connection 
				BufferedReader userInput = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fr.searchField.getText().getBytes("UTF-8")))); 
				String userString = userInput.readLine();
				Socket connection = new Socket("127.0.0.1", 1237);
				InputStream input = connection.getInputStream();
				OutputStream output = connection.getOutputStream();
				
				//Writing the length to server
				output.write(userString.length());
				output.write(userString.getBytes());
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				
			    String serverResponse = "";
				
			    //A while loop to read all of the responses from the server and add them to the listmodel
				while(serverResponse != null) 
				{
					serverResponse = reader.readLine();
					if (serverResponse != null)
					{
						fr.resultField.setText(serverResponse);
					}
				}	
				
				if(!connection.isClosed())
				{
					connection.close();
				}
				
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		
		}
	}

	//Creating my Frame
	class MyFrame extends JFrame {
		public JTextField searchField = new JTextField();
		public JTextField resultField = new JTextField();
		
		
		
		public MyFrame() {
		super();
		init();
	}
		
		//Setting up the GUI
		private void init() {
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("Prime Number Detector");
			this.setLayout(new GridLayout(3, 2));
			this.add(new JLabel("Number to be checked"));
			this.add(searchField);
			this.add(new JLabel("Prime Number: Yes or No"));
			resultField.setEditable(false);
			this.add(resultField);
			JButton determine = new JButton("Determine"); //Determine button
			determine.addActionListener(new MyButtonListener(this)); //Actionlistener for the determine button
			this.add(new JLabel());
			this.add(determine);
			int frameWidth = 500;
			int frameHeight = 500;
	    	Dimension screenSize =
	    	Toolkit.getDefaultToolkit().getScreenSize();
	        this.setBounds((int) screenSize.getWidth() - frameWidth * 2, 0,
			frameWidth, frameHeight);
			}
}

