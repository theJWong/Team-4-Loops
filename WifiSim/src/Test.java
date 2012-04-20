class Test
{
    public static void main(String[] args) {
        Location a = new Location();
        Location b = new Location(300);
        Location c = new Location(120,50);
//        System.out.println(a.x+" "+a.y);
//        System.out.println(b.x+" "+b.y);
//        System.out.println(c.x+" "+c.y);
        double ab = a.getDistance(b);
        double bc = b.getDistance(c);
        System.out.println("ab = "+ab);
        System.out.println("bc = "+bc);
        if(b.isInRange(c)) {
            System.out.println("bc is in range");
        } else {
            System.out.println("bc is not in range");
        }

//        for(int i=0; i<5; i++) {
//            Node A = new Node(i*2);
//            Node B = new Node(i*2+1);
//            System.out.println(A.getID());
//            System.out.println(B.getID());
//        }
    }
}
