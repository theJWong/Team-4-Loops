class Frame
{
    public enum Type {
        RTS, CTS, ACK, DATA;
    }

    private Type type;
    private Node.ID SA;
    private Node.ID DA;
    private int LEN;
    private byte[] data;

    public Frame(Type type, Node.ID SA, Node.ID DA, int LEN) {
        this.type = type;
        this.SA = SA;
        this.DA = DA;
        this.LEN = LEN;
        if(this.type == Type.DATA) {
            setData();
        }
    }

    public Type getType() {
        return type;
    }

    public Node.ID getSource() {
        return SA;
    }

    public Node.ID getDestination() {
        return DA;
    }

    public byte[] getData() {
        return data;
    }

    public void setData() {
        data = new byte[LEN];
    }
}
