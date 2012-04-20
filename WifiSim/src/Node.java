class Node extends Thread
{
    public enum ID {
        A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;
        public static ID get(int i) {
            return values()[i];
        }
    }

    public enum Status {
        IDLE,GOT_RTS,GOT_CTS,GOT_ACK,BUSY;
    }
   
    private ID id;
    private Status status;
    private Buffer buffer;
    private Location location;
    private Node node;

    Node(int i) {
        id = ID.get(i);
        status = Status.IDLE;
        buffer = new Buffer();
        location = new Location();
    }

    public void init(Node receiver, int arrivalRate, int packetSize, byte[] data) {
        node = receiver;
        buffer.init(arrivalRate,packetSize,data);
        send(Frame.Type.RTS,0);
    }

    public void run() {
        if (buffer.getData() != null) {
            if (status == Status.GOT_CTS) {
                transmit();
            } else {
                backoff();
            }
        }
        System.out.println(id+" finish");
    }

    private void send(Frame.Type type, int length) {
        Frame frame = new Frame(type, getID(), node.getID(), length);
        node.receive(frame, this);
        if (type != Frame.Type.DATA && type != Frame.Type.ACK) {
            System.out.println(id+" sent "+type+" to "+node.getID());
        }
    }

    private void receive(Frame frame, Node node) {
        this.node = node;
        if (frame.getType() != Frame.Type.DATA && frame.getType() != Frame.Type.ACK) {
            System.out.println(id+" receive "+frame.getType()+" from "+frame.getSource());
        }
        if (status == Status.IDLE) {
            if (frame.getType() == Frame.Type.RTS) {
                status = Status.GOT_RTS;
                send(Frame.Type.CTS, 0);
            } else if (frame.getType() == Frame.Type.CTS) {
                status = Status.GOT_CTS;
            }
        } else {
            if (frame.getType() == Frame.Type.RTS) {
                System.out.println(frame.getSource()+" should backoff");
            } else if (frame.getType() == Frame.Type.CTS) {
                status = Status.GOT_CTS;
            } else if (frame.getType() == Frame.Type.ACK) {
                status = Status.GOT_ACK;
            } else if (frame.getType() == Frame.Type.DATA) {
                status = Status.BUSY;
                buffer.receive(frame);
                send(Frame.Type.ACK, 0);
            }
        }
    }

    public void transmit() {
        status = Status.BUSY;
        int data_len = buffer.getData().length;
        while (data_len > 0) {
            send(Frame.Type.DATA,buffer.getPacketSize());
            data_len -= buffer.getPacketSize();
        }
    }

    private void backoff() {
        try {
            Thread.currentThread().sleep(5000);
        } catch(InterruptedException ie) {
            System.err.println(ie.toString());
        }
    }

    public ID getID() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Location getLocation() {
        return location;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public double getDistance(Node node) {
        return location.getDistance(node.getLocation());
    }

    public boolean isInRange(Node node) {
        return location.isInRange(node.getLocation());
    }

    public boolean isEmpty() {
        return(buffer.isEmpty());
    }
}
