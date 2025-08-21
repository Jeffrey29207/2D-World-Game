package core.generation;

// Room Class
// Stores BOTTOM-LEFT coordinate of the room
// Stores room width and height
public class Room {

    public Point startPoint; // bottom left coordinate of the room
    public int roomWidth;
    public int roomHeight;
    public Point center;

    public Room(Point startPoint, int roomWidth, int roomHeight) {
        this.startPoint = startPoint;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.center = new Point(centerX(), centerY());
    }

    public int centerX() { // get centerX coordinate
        return startPoint.getX() + roomWidth / 2;
    }

    public int centerY() { // get centerY coordinate
        return startPoint.getY() + roomHeight / 2;
    }

    public double distance(Room data) { // get the hypotenuse
        int cx = startPoint.getX() - data.centerX();
        int cy = startPoint.getY() - data.centerY();
        return Math.hypot(cx, cy);
    }


    @Override
    public String toString() { //  for testing
        return "room " + startPoint.toString();
    }
}
