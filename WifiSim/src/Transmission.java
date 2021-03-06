import java.util.ArrayList;
import java.util.Random;

class Transmission extends Thread
{
    private static final double bandwidth = 54000000.0; //54Mbps
    private static final double C = 3.0e8;
    private Node sender;
    private Node receiver;
    private byte[] data;
    private int arrivalRate;
    private int packetSize;
    private int numPackets;
    private double length;
    private double delay;
    private double throughput;
    private ArrayList<Double> delayOfPacket = new ArrayList<Double>();

    Transmission(Node sender, Node receiver, int rate, 
        int size, int num) {
        this.sender = sender;
        this.receiver = receiver;
        data = new byte[size*num];
        length = sender.getDistance(receiver);
//        arrivalRate = poisson(rate);
        arrivalRate = rate;
        packetSize = exponential(size);
    }

    public void run() {
//      double size = (double) receiver.getBuffer().getTotalSize();
        double size = data.length;
        double propagation = (double) length / C;
        double transmit = size / bandwidth;
        double latency = propagation + transmit;
        double RTT = 2 * latency;

        System.out.println(sender.getID()+" try sending to "+receiver.getID());
        sender.init(receiver,arrivalRate,packetSize,data,propagation);
        sender.setDelay(transmit);
        try {
            sender.start();
        } catch(IllegalThreadStateException ie) {
        }

        while(sender.getState() != Thread.State.TERMINATED) {
        	try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        // Calculate transfer time (varied based on asychronous transfer)
        double totalSize = size;
        double transferTime = 0;
        double sum_delay = sender.getSumDelay() + receiver.getSumDelay();
        transferTime = (RTT + poisson(arrivalRate, size)) + (1 / bandwidth) * size;
        double tempTime = transferTime;
        while (totalSize > 0)
        {
            double temp = poisson(RTT)  + (1 / bandwidth) * size;
            delayOfPacket.add(temp);
            tempTime += temp;
            totalSize -= packetSize;
        }
        
        double jitter = 0;
        if (!delayOfPacket.isEmpty()) {
         double avgDelay = sum_delay / delayOfPacket.size();
         for (int i = 0; i < delayOfPacket.size(); i++)
         {
            double temp = ((sender.getSumDelay() - (receiver.getSumDelay() - poisson(RTT))) - avgDelay);
            jitter += Math.abs(temp);
         }
         jitter = jitter / delayOfPacket.size();
        }

        double throughput = size / tempTime;

        System.out.println(prefix()+"---------------Results---------------");
        System.out.println(prefix()+"File Size : " + size + " bits");
        System.out.println(prefix()+"Total Transfer Time: " + transferTime + " seconds");
        System.out.println(prefix()+"Throughput: " + throughput + " bps");
        System.out.println(prefix()+"Delay: " + sum_delay + " seconds");
        System.out.println(prefix()+"Jitter: " + jitter + " ms");
    }

    public int poisson(double lambda) {
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
    public int uniform(int N) {
        Random random = new Random();
        return random.nextInt(N);
    }

    private double poisson(int avg_arrival_rate, double packet_size) {
        Random random = new Random();
        int r = random.nextInt(10);
        double lambda = avg_arrival_rate/packet_size;
        double actual_arrival_rate = (Math.exp(-lambda)*Math.pow(lambda,r))/factorial(r);
        return actual_arrival_rate;
    }

    private int factorial(int r) {
        int total = 1;
        while(r>0) {
            total *= r--;
        }
        return total;
    }
   
    private int exponential(int size) {
        return size;
    }

    private String prefix() {
        return("["+sender.getID()+"->"+receiver.getID()+"] ");
    }
}
