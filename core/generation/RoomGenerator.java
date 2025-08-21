package core.generation;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class RoomGenerator {

    public static ArrayList<Room> generateRooms(TETile[][] world, int width, int height, int numRooms, long SEED) {
        // List to record existing rooms
        Random RANDOM = new Random(SEED);
        ArrayList<Room> rooms = new ArrayList<>();

        // loop to generate rooms
        for (int i = 0; i < numRooms; i++) {
            // create start point for new room
            int roomWidth = RANDOM.nextInt(5) + 3;
            int roomHeight = RANDOM.nextInt(5) + 3;
            int startX = RANDOM.nextInt(width - roomWidth - 2) + 1;
            int startY = RANDOM.nextInt(height - roomHeight - 2) + 1;

            // flag to detect overlaps
            boolean overlaps = false;

            // minimise x-axis and y-axis room collisions
            for (Room room : rooms) {
                int rx = room.startPoint.getX();
                int ry = room.startPoint.getY();
                int rw = room.roomWidth;
                int rh = room.roomHeight;

                boolean XCollisions = startX + roomWidth >= rx && startX <= rx + rw;
                boolean YCollisions = startY + roomHeight >= ry && startY <= ry + rh;
                if (XCollisions && YCollisions) {
                    overlaps = true;
                    break;
                }
            }

            // skip the current iteration if there are overlaps
            if (overlaps) {
                i--;
                continue;
            }

            // If no overlaps, create the room on world map
            rooms.add(new Room(new Point(startX, startY), roomWidth, roomHeight));
            for (int x = startX; x < startX + roomWidth; x++) {
                for (int y = startY; y < startY + roomHeight; y++) {
                    world[x][y] = Tileset.FLOOR;
                }
            }

//            System.out.println(rooms.toString()); // for debugging
        }
        return rooms;
    }
}
