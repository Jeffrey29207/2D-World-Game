package core.generation;

import org.checkerframework.checker.units.qual.A;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static core.algorithm.KruskalMST.createMST;

public class HallWayGenerator {
    private static final long SEED = 287312;
    private static final Random RANDOM = new Random(SEED);

    public static void generateHall(TETile[][] world, ArrayList<Room> rooms) {
        ArrayList<Edge> edges = createMST(rooms);
//        ArrayList<Edge> moreedges = createConnections(rooms, edges);
//        edges.addAll(moreedges);
        // iterate through each edge in the MST
        for (Edge e : edges) {
            Point p1 = e.p1;
            Point p2 = e.p2;

            // draw horizontal hallway between two rooms
            for (int x = Math.min(p1.getX(), p2.getX()); x <= Math.max(p1.getX(), p2.getX()); x++) {
                world[x][p1.getY()] = Tileset.FLOOR;
            }

            // draw vertical hallway between two rooms
            for (int y = Math.min(p1.getY(), p2.getY()); y <= Math.max(p1.getY(), p2.getY()); y++) {
                world[p2.getX()][y] = Tileset.FLOOR;
            }
        }
    }

    private static ArrayList<Edge> createConnections(ArrayList<Room> rooms, ArrayList<Edge> edges) {
        ArrayList<Edge> newHallway = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++){
            for (int j = i + 1; j < rooms.size(); j++){ // loop through deifferent rooms
                Room r1 = rooms.get(i);
                Room r2 = rooms.get(j);
                double dis = r1.distance(r2);

                if (dis < 10 && !isConnected(r1.center, r2.center, edges)){
                    newHallway.add(new Edge(r1.center, r2.center));
                }
            }
        }
        return newHallway;
    }

    private static boolean isConnected(Point p1, Point p2, ArrayList<Edge> edges){
        for (Edge edge : edges){
            if ((edge.p1.equals(p1) && edge.p2.equals(p2)) || (edge.p1.equals(p2) && edge.p2.equals(p1))){
                return true;
            }
        }
        return false;
    }
}
