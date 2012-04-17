import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/* Simulator: Jeremy Lozano  */
public class SimulatorGUI
{  
   public static void main(String[] args)
   {
	   JFrame frame = new JFrame("Team 4 Loops Simulator");
	   JPanel topPanel = new JPanel();
	   final JPanel bottomPanel = new JPanel();
	   
	   frame.setLayout(new BorderLayout());
	   
	   //TOP PANEL
	   JLabel distanceLabel = new JLabel("Distance");
	   final JTextField distance = new JTextField("Enter distance here.");
	   JLabel maxPacketSizeLabel = new JLabel("MaxPacketSize");
	   JTextField maxPacketSize = new JTextField("Enter max packet size here.");
	   JLabel numOfNodesLabel = new JLabel("Number of nodes");
	   JTextField numOfNodes = new JTextField("Enter number of nodes here.");
	   JButton submitButton = new JButton("Submit");
	   
	   
	   //BOTTOM PANEL
	   final JTextField resultsBox = new JTextField(50);
           final JTextArea textArea = new JTextArea(40,40);
           textArea.setLineWrap(true);
	   final JScrollPane scrollPane = new JScrollPane(textArea); 
             scrollPane.setSize(80, 80);
           scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
           scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	   resultsBox.setEditable(false);
	   
	   
	   topPanel.add(distanceLabel);
	   topPanel.add(distance);
	   topPanel.add(maxPacketSizeLabel);
	   topPanel.add(maxPacketSize);
	   topPanel.add(numOfNodesLabel);
	   topPanel.add(numOfNodes);
	   topPanel.add(submitButton);
	   
	   
	   
	     bottomPanel.add(scrollPane);
         //  bottomPanel.add(textArea);
	   
	   frame.add(topPanel, BorderLayout.NORTH);
	   frame.add(bottomPanel, BorderLayout.CENTER);
	   
       frame.setSize(1000, 200);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.pack();
       frame.setVisible(true);
       
       submitButton.addActionListener(new ActionListener(){
            @Override
			  public void actionPerformed(ActionEvent e)
			  {

                              
				  textArea.append( distance.getText() + "\n");
                                  textArea.append("make this box frame" + "\n");
            
			  }
			    
		   });
       
       
    
	   
      
   }
   
}
