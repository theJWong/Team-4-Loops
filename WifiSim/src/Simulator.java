import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

class Simulator
{
    public static void main(String[] args) {

        //user input
        int arrivalRate = 5000000; //5Mbps
        int packetSize = 1000; //bits
        int numPackets = 100;
        int numNodes = 4;

        //simulation setup
        Node[] nodes = new Node[numNodes];
        Queue<Transmission> transmissions = new LinkedList<Transmission>();
        Map<Node.ID,Integer> receivers = new HashMap<Node.ID,Integer>();

        for (int i=0; i<numNodes; i++) {
            nodes[i] = new Node(i);
        }

        for (int i=0; i<numNodes; i++) {
            for (int j=0; j<numNodes; j++) {
                if(i != j) {
                    if (nodes[i].isInRange(nodes[j]) && !receivers.containsKey(nodes[j].getID())) {
                        Transmission t = new Transmission(nodes[i],nodes[j],arrivalRate,packetSize,numPackets);
                        transmissions.add(t);
                        receivers.put(nodes[j].getID(),new Integer(0));
                    }
                }
            }
        }

        //simulation start
        while (transmissions.size() > 0) {
            Transmission t = transmissions.poll();
            try {
                t.start();
            } catch(IllegalThreadStateException ie) {
                System.err.println("Simulator: "+ie.toString());
            }
        }
    }
}
