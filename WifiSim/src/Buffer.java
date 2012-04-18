import java.util.Vector;

class Buffer
{
    private Vector<Frame> buffer;
    private int arrivalRate;
    private int packetSize;
    private byte[] data;

    Buffer() {
        buffer = new Vector<Frame>();
    }

    public void init(int arrivalRate, int packetSize, byte[] data) {
        this.arrivalRate = arrivalRate;
        this.packetSize = packetSize;
        this.data = data;
    }

    public int getSize() {
        return buffer.capacity();
    }

    public int getPacketSize() {
        return packetSize;
    }

    public byte[] getData() {
        return(data);
    }

    public void receive(Frame frame) {
        buffer.add(frame);
    }

    public boolean isEmpty() {
        return(buffer.size() == 0);
    }

}
