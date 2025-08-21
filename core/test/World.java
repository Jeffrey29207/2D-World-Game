package core.test;

import core.Interact.Char;
import core.Menu.Menu;
import core.Menu.SavedWorld;
import core.generation.Room;
import core.generation.HallWayGenerator;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import core.generation.Point;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static core.generation.RoomGenerator.generateRooms;
import static core.generation.WallGenerator.generateWalls;

public class World {

    private static final int WIDTH = 90;
    private static final int HEIGHT = 50;
    private static final long SEED = 287312;
    private static final Random RANDOM = new Random(SEED);
    public static TETile[][] world = new TETile[WIDTH][HEIGHT];

    // initialize the map
    public static void generateEmptyMap(TETile[][] world) {
        // generates an empty world of dimensions WIDTH, HEIGHT
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    // How to create a new SavedWorld? -- SavedWorld(long seed, Point avatarCoord, ...)
    // Edit SavedWorld class in Menu folder
    //TODO: avatarCoord of returned 'SavedWorld' is set to (0,0), need to replace with the actual starting point of the avatar
    public static Point getValidPlace(TETile[][] world, ArrayList<Room> rooms){
        Random random = new Random();
        int val = random.nextInt(rooms.size());
        int x = rooms.get(0).centerX();
        int y = rooms.get(0).centerY();
        if (!(x >= 0 && x < world.length && y >= 0 && y < world[0].length)){ // just in case, useless here
            return new Point(0, 0);
        }
        return new Point(x, y);
    }

    public static Point EnemyPos(TETile[][] world, ArrayList<Room> rooms, Point avatarPoint){
        Random random = new Random();
        Point enemyPt;
        do {
            int val = random.nextInt(rooms.size());
            int x = rooms.get(val).centerX();
            int y = rooms.get(val).centerY();
            enemyPt = new Point(x, y);
        } while (enemyPt.equals(avatarPoint));

        if (!(enemyPt.getX() >= 0 && enemyPt.getX() < world.length && enemyPt.getY() >= 0 && enemyPt.getY() < world[0].length)){ // just in case, useless here
            return new Point(0, 0);
        }
        return enemyPt;
    }

    //TODO: modify generateNewWorld to return a 'SavedWorld' with all info needed to reconstruct saved world
    public static SavedWorld generateNewWorld(long seed) {
        StdDraw.clear(Color.BLACK);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        StdDraw.setYscale(0, HEIGHT + 2);
        generateEmptyMap(world);
        ArrayList<Room> rooms = generateRooms(world, WIDTH, HEIGHT, 30, seed);
        HallWayGenerator.generateHall(world, rooms);
        generateWalls(world, WIDTH, HEIGHT);
        //ter.renderFrame(world);

        Point pt = getValidPlace(world, rooms);
        ArrayList<Point> Ept = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Point Ept1 = EnemyPos(world, rooms, pt);
            Ept.add(Ept1);
        }
        Char a = new Char();
        return new SavedWorld(seed, pt, Ept, a.getHP());
    }

    // How to create a new SavedWorld? -- SavedWorld(long seed, Point avatarCoord, ...)
    // for now, only seed and avatarCoord are saved
    //TODO: modify generateLoadedWorld to return SavedWorld containing all info needed to reconstruct saved world
    public static SavedWorld generateLoadedWorld(long seed, Point avatarCoord, ArrayList<Point> enemyCoord, int HP) {
        StdDraw.clear(Color.BLACK);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        StdDraw.setYscale(0, HEIGHT + 2); // for canvas
        generateEmptyMap(world);
        ArrayList<Room> rooms = generateRooms(world, WIDTH, HEIGHT, 30, seed);
        HallWayGenerator.generateHall(world, rooms);
        generateWalls(world, WIDTH, HEIGHT);
        return new SavedWorld(seed, avatarCoord, enemyCoord, HP); // add new items inside the brackets here...
    }

    // For tests only
    public static void main(String[] args) {
        Menu menu = new Menu(90, 50);
        menu.startGame();
    }
}
