import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/* Simulator: Jeremy Lozano  */
public class SimulatorGUI
{  
   public static void main(String[] args)
   {
	   JFrame frame = new JFrame();
	   JPanel topPanel = new JPanel();
	   final JPanel bottomPanel = new JPanel();
	   
	   frame.setLayout(new FlowLayout());
	   
	   //TOP PANEL
	   JLabel distanceLabel = new JLabel("Distance");
	   final JTextField distance = new JTextField("Enter distance here.");
	   JLabel maxPacketSizeLabel = new JLabel("MaxPacketSize");
	   JTextField maxPacketSize = new JTextField("Enter max packet size here.");
	   JLabel numOfNodesLabel = new JLabel("Number of nodes");
	   JTextField numOfNodes = new JTextField("Enter number of nodes here.");
	   JButton submitButton = new JButton("Submit");
	   
	   
	   //BOTTOM PANEL
	   JTextArea resultsBox = new JTextArea(50, 600);
	   JScrollPane scrollPane = new JScrollPane(resultsBox); 
	   resultsBox.setEditable(false);
	   
	   
	   topPanel.add(distanceLabel);
	   topPanel.add(distance);
	   topPanel.add(maxPacketSizeLabel);
	   topPanel.add(maxPacketSize);
	   topPanel.add(numOfNodesLabel);
	   topPanel.add(numOfNodes);
	   topPanel.add(submitButton);
	   
	   submitButton.addActionListener(new ActionListener(){
            @Override
			  public void actionPerformed(ActionEvent e)
			  {
				  //resultsBox.setText(distance.getText());
			  }
			    
		   });
	   
	   resultsBox.setText("THIS IS A TEST");
	   
	   bottomPanel.add(scrollPane);
	   
	   frame.add(topPanel);
	   frame.add(bottomPanel);
	   
	   frame.setSize(1000, 200);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
	   
      
   }
   
}
