import java.util.Random;
import java.util.ArrayList;

public class Simulator
{
   // This should be the list containing all of the nodes that can SEE EACH OTHER.
   // This is used to simulate the "broadcasting" the message since we are sending
   // the data through the air frequency.
   private ArrayList<Node> nodes = new ArrayList<Node>();
   
   public void run()
   {
      // For now, just simulate two nodes sending 10X Mbits file 5 times so
      // 10 Mb
      // 20 Mb
      // 30 Mb
      // 40 Mb
      // 50 Mb
      Node nodeA = new Node("A");
      Node nodeB = new Node("B");
      nodes.add(nodeA);
      nodes.add(nodeB);

      // Now nodeA wants to send a message to nodeB
      Message RTS = new Message(Message.RTS);
      nodeA.send("B", RTS);
      nodeB.receive("B", RTS);
      
      // nodeB needs to send CTS back to nodeA
      Message CTS = new Message(Message.CTS);
      nodeB.send("A", CTS);
      nodeA.receive("A", CTS);
      
      // nodeA needs to begin sending DATA, need to add calcuations somewhere here
      Message data = new Message(Message.DATA);
      nodeA.send("B", data);
      nodeB.receive("B", data);
      
   }
   
   public static void main(String[] args)
   {
      Simulator sim = new Simulator();
      sim.run();
   }
}