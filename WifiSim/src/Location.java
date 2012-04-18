import java.util.Random;

class Location
{
    Location loction;
    int MAX_POS = 150;
    int MAX_DIS = 100;
    private int x;
    private int y;

    Location() {
        Random random = new Random();
        x = random.nextInt(MAX_POS);
        y = random.nextInt(MAX_POS);
    }

    Location(int max_pos) {
        Random random = new Random();
        x = random.nextInt(max_pos);
        y = random.nextInt(max_pos);
    }

    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getDistance(Location loc) {
        double x = (double) Math.abs(loc.x - this.x);
        double y = (double) Math.abs(loc.y - this.y);
        double distance = Math.sqrt(Math.pow(x,2.0)+Math.pow(y,2.0));
        return distance;
    }

    public boolean isInRange(Location loc) {
        return(getDistance(loc) <= (double) MAX_DIS);
    }
}
