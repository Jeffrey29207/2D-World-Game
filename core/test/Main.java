package core.test;

import core.generation.Room;
import core.generation.HallWayGenerator;
import tileengine.TERenderer;
import tileengine.TETile;

import java.util.ArrayList;
import java.util.Random;

import static core.generation.RoomGenerator.generateRooms;
import static core.generation.WallGenerator.generateWalls;
import static core.test.World.generateEmptyMap;

public class Main {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final long SEED = 6206686636164176845L;
//    private static final Random RANDOM = new Random(SEED);
    private static TETile[][] world = new TETile[WIDTH][HEIGHT];

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        generateEmptyMap(world);
        ArrayList<Room> rooms = generateRooms(world, WIDTH, HEIGHT, 15, SEED);
        HallWayGenerator.generateHall(world, rooms);
        generateWalls(world, WIDTH, HEIGHT);
        ter.renderFrame(world);
    }
}
