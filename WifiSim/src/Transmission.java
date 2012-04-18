class Transmission extends Thread
{
    private static double bandwidth = 54000000.0; //54Mbps
    private Node sender;
    private Node receiver;
    private byte[] data;
    private int arrivalRate;
    private int packetSize;
    private int numPackets;
    private double length;
    private double delay;
    private double throughput;

    Transmission(Node sender, Node receiver, int rate, 
        int size, int num) {
        this.sender = sender;
        this.receiver = receiver;
        data = new byte[size*num];
        length = sender.getDistance(receiver);
        arrivalRate = poisson(rate);
        packetSize = exponential(size);
    }

    public void run() {
        System.out.println(sender.getID()+" try sending to "+receiver.getID());
        try {
            sender.init(receiver,arrivalRate,packetSize,data);
            System.out.println("sender("+sender.getID()+") status: "+sender.getStatus());
            sender.start();
            System.out.println("receiver("+receiver.getID()+") status: "+receiver.getStatus());
            receiver.start();
        } catch(IllegalThreadStateException ie) {
            System.err.println("sender("+sender.getID()+"): "+sender.getState());
            System.err.println("receiver("+receiver.getID()+"): "+receiver.getState());
//            System.err.println("Sender: "+ie.toString());
        }
    }

    private int poisson(int rate) {
        return rate;
    }

    private int exponential(int size) {
        return size;
    }
}
