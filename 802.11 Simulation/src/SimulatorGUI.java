import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/* Simulator: Jeremy Lozano  */
public class SimulatorGUI
{  
    //inputs go here from simulator.java
    public SimulatorGUI()
    {
        
    }
    /*
     * test playground
     */
   public static void main(String[] args)
   {
       //frame settings
        JFrame frame = new JFrame("Team 4 Loops Simulator");
        frame.setLayout(new BorderLayout());
        
        //top panel + bottom panel declaration
        JPanel topPanel = new JPanel();
        final JPanel bottomPanel = new JPanel();

        

        //TOP PANEL
        JLabel distanceLabel = new JLabel("Distance");
        final JTextField distance = new JTextField("Enter distance here.");
        JLabel maxPacketSizeLabel = new JLabel("MaxPacketSize");
        JTextField maxPacketSize = new JTextField("Enter max packet size here.");
        JLabel numOfNodesLabel = new JLabel("Number of nodes");
        JTextField numOfNodes = new JTextField("Enter number of nodes here.");
        JButton submitButton = new JButton("Submit");


        //BOTTOM PANEL 
        final JTextArea resultsBox = new JTextArea(30,50);
        resultsBox.setLineWrap(true);
        final JScrollPane scrollPane = new JScrollPane(resultsBox); 
        scrollPane.setSize(40, 30);
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


        frame.add(topPanel, BorderLayout.WEST);
        frame.add(bottomPanel, BorderLayout.CENTER);

        frame.setSize(1000, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
       
       submitButton.addActionListener(new ActionListener(){
            @Override
			  public void actionPerformed(ActionEvent e)
			  {

                                  //use Append not settext
				  resultsBox.append( distance.getText() + "\n");
                                  resultsBox.append("make this box pretty" + "\n");
            
			  }
			    
		   });

      
   }
   
}
