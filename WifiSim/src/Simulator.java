import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JFrame;

class Simulator
{
	Node[] nodes;
	Queue<Transmission> transmissions;
	Map<Node.ID,Integer> receivers;
	
    public Simulator(int size, int nNodes) {

        //user input
        int arrivalRate = 5000000; //5Mbps
        int packetSize = 1000; //bits
        packetSize = size;
        int numPackets = 100;
        int numNodes = 10;
        numNodes = nNodes;

        //simulation setup
        nodes = new Node[numNodes];
        transmissions = new LinkedList<Transmission>();
        receivers = new HashMap<Node.ID,Integer>();

        for (int i=0; i<numNodes; i++) {
            nodes[i] = new Node(i);
        }

        for (int i=0; i<numNodes; i++) {
            for (int j=0; j<numNodes; j++) {
                if(i != j) {
                    if (nodes[i].isInRange(nodes[j]) && !receivers.containsKey(nodes[j].getID())) {
                        Transmission t = new Transmission(nodes[i],nodes[j],arrivalRate,packetSize,numPackets*(receivers.size()+1));
                        transmissions.add(t);
                        receivers.put(nodes[j].getID(),new Integer(0));
                    }
                }
            }
        }

    }
    
    public Node[] getNodes()
    {
    	return nodes;
    }
    
    public String run()
    {
    	//simulation start
        while (transmissions.size() > 0) {
            Transmission t = transmissions.poll();
            try {
                t.start();
            } catch(IllegalThreadStateException ie) {
                System.err.println("Simulator: "+ie.toString());
            }
        }
        return "";
    }
}
