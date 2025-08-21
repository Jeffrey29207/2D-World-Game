package core.algorithm;


import core.generation.Edge;
import core.generation.Point;
import core.generation.Room;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class KruskalMST {

    public static ArrayList<Edge> createMST(ArrayList<Room> rooms) {
        int numRooms = rooms.size();
        // create priority with all possible edges between rooms
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        for (int i = 0; i < numRooms; i++) {
            Point from = rooms.get(i).center;
            for (int j = i + 1; j < numRooms; j++) {
                Point to = rooms.get(j).center;
                pq.add(new Edge(from, to));
            }
        }
        // run kruskal's algorithm
        UnionFind uf = new UnionFind(numRooms);
        ArrayList<Edge> mst = new ArrayList<Edge>();
        while (!pq.isEmpty() && mst.size() < numRooms - 1) {
            Edge edge = pq.poll();
            int roomAIndex = findRoomIndex(rooms, edge.p1);
            int roomBIndex = findRoomIndex(rooms, edge.p2);
            if (!uf.isConnected(roomAIndex, roomBIndex)) {
                uf.union(roomAIndex, roomBIndex);
                mst.add(edge);
            }
        }
        return mst;
    }

        private static int findRoomIndex(ArrayList<Room> rooms, Point point) {
            for (int i = 0; i < rooms.size(); i++) {
                Point c = rooms.get(i).center;
                if (c.getX() == point.getX() && c.getY() == point.getY()) {
                    return i;
                }

            }
            throw new NoSuchElementException();
        }
    }
