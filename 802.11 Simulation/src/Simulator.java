import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulator
{
   private static final double C = 3.0e8;
   
   // This should be the list containing all of the nodes that can SEE EACH OTHER.
   // This is used to simulate the "broadcasting" the message since we are sending
   // the data through the air frequency.
   private ArrayList<Node> nodes = new ArrayList<Node>();
   
   public void run()
   {
      // Grab user input
      Scanner input = new Scanner(System.in);
      
      System.out.print("Distance of each node (in Meters):");
      int distance = input.nextInt();
      
      System.out.print("Maximum packet size (in Bytes):");
      int maxPacketSize = input.nextInt();
              
      // Begin Simulation   
      System.out.println("---------------Beginning Simulation---------------");
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
      
      // Begin Calculations
      int size = 10000;
      int bandwidth = 1500;
      int queue = 0;
      
      int PoissonVal = 0; // Right now just 0, should be a method to change the value each time.
      // Should probably have some kind of loop to increase the file size
      double propagation = (double) distance / C;
      double transmit = size / bandwidth;
      double latency = propagation + transmit + queue;
      
      double RTT = latency * bandwidth;
      
      // Calculate transfer time (varied based on asychronous transfer)
      int totalSize = size;
      double transferTime = 0;
      while (totalSize >= 0)
      {
         transferTime = (RTT + PoissonVal) + 1/bandwidth * maxPacketSize; // Need to change maxPacketSize to bits.
         totalSize -= maxPacketSize;
      }
      
      double throughput = size / transferTime;
      
      System.out.println("");
      System.out.println("---------------Results---------------");
      System.out.println("Total Transfer Time: " + transferTime);
      System.out.println("Throughput: " + throughput);
   }
   
   public static void main(String[] args)
   {
      Simulator sim = new Simulator();
      sim.run();
   }
}