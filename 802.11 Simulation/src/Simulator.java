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
      maxPacketSize = toBits(maxPacketSize);
              
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
      double size = 100000000;
      double bandwidth = 54000000; //54Mbps
      int rate = 5000000; //5Mbps
      int queue = 0;
      
      // Should probably have some kind of loop to increase the file size
      for (int i = 0; i < 5; i++)
      {
         double propagation = (double) distance / C;
         double transmit = size / bandwidth;
         double latency = propagation + transmit + queue;
         double RTT = 2 * latency;

         // Calculate transfer time (varied based on asychronous transfer)
         double totalSize = size;
         double transferTime = 0;
         transferTime = (RTT + poisson(rate, size)) + (1 / bandwidth) * size;
         double tempTime = transferTime;
         while (totalSize > 0)
         {
            tempTime += poisson(RTT)  + (1 / bandwidth) * size;
            totalSize -= maxPacketSize;
         }

         double throughput = size / tempTime;
         
         System.out.println("");
         System.out.println("---------------Results---------------");
         System.out.println("File Size : " + size + " bits");
         System.out.println("Total Transfer Time: " + transferTime + " seconds");
         System.out.println("Throughput: " + throughput + " bps");
         size += 100000000;
      }
   }
   
   public static int poisson(double lambda) {
      // using algorithm given by Knuth
      // see http://en.wikipedia.org/wiki/Poisson_distribution
      int k = 0;
      double p = 1.0;
      double L = Math.exp(-lambda);
      do {
         k++;
         p *= uniform(10);
      } while (p >= L);
      return k - 1;
   }

   /**
    * Return an integer uniformly between 0 and N-1.
    */
   public static int uniform(int N) {
      Random random = new Random();
      return random.nextInt(N);
   }


   private static double poisson(int avg_arrival_rate, double packet_size)
   {
       Random random = new Random();
       int r = random.nextInt(10);
       double lambda = avg_arrival_rate/packet_size;
       double actual_arrival_rate = (Math.exp(-lambda)*Math.pow(lambda,r))/factorial(r);
       return actual_arrival_rate;
   }

   private static int factorial(int r)
   {
       int total = 1;
       while(r>0) {
           total *= r--;
       }
       return total;
   }
   
   private static int toBits(int bytes)
   {
       /*Declarations*/
       int bits;
       final int eight = 8;
       
       bits = eight * bytes; //formula to convert
       
       return bits;
       
   }
   
   public static void main(String[] args)
   {
      Simulator sim = new Simulator();
      sim.run();
      
   }
}
