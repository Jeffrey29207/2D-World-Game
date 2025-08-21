package core.Menu;
import core.Interact.Char;
import core.Interact.CharacterMove;
import core.Interact.EnemyFeature;
import core.HUD.HUD;
import core.Interact.Treasure;
import core.generation.Point;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import static core.test.World.*;

public class Menu {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** Error Message. */
    private String errorMsg = "";
    private long errorDisplayTime = 0L;
    /** State Change variables. */
    private enum State {MAIN, SEED_ENTRY, IN_WORLD}
    private State state = State.MAIN;
    /** Save Game variables. */
    private long activeSeed = -1;
    private Point avatarCoord;
    private ArrayList<Point> enemyCoord;
    private ArrayList<Point> treasureCoord;
    /** for in-world rendering HUD*/
    private final TERenderer ter = new TERenderer();
    private boolean HUDInitialized = false;
    private long gameTime = 300;
    private int HP = 3;
    private Char avatar;
    private EnemyFeature ef;
    private Treasure treasure;
    private TETile char_;
//    private boolean alive = true;

    public Menu(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);
    }


    private void drawMainMenu() {
        StdDraw.clear(Color.BLACK);

        // Main title
        Font titleFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(titleFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2.0, height * 0.75, "CS61B: BYOW");

        // Game Options
        Font optFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(optFont);
        StdDraw.text(width / 2.0, height * 0.55, "(N) New Game");
        StdDraw.text(width / 2.0, height * 0.45, "(L) Load Game");
        StdDraw.text(width / 2.0, height * 0.35, "(C) Change Avatar");
        StdDraw.text(width / 2.0, height * 0.25, "(Q) Quit Game");

        // Error Message
        if (!errorMsg.isEmpty() && System.currentTimeMillis() < errorDisplayTime) {
            Font errFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(errFont);
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(width / 2.0, height * 0.18, errorMsg);
        }

        StdDraw.show();
    }

    private Long promptSeed() {
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }

        String seedString = "";
        while (true) {
            drawSeedPrompt(seedString);
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (Character.isDigit(c)) {
                    seedString += c;
                } else if (c == 'S' || c == 's') {
                    if (!seedString.isEmpty()) {
                        try {
                            return Long.parseLong(seedString);
                        } catch (NumberFormatException e) {
                            setError("Seed Out of Range");
                            seedString = "";
                        }
                    } else {
                        setError("No Seed Entered");
                    }
                } else if (c == 'Q' || c == 'q') {
                    return null;
                } else {
                    setError("Invalid Keyboard Input");
                }
            }
            StdDraw.pause(20);
        }
    }

    private void drawSeedPrompt(String seedString) {
        StdDraw.clear(Color.BLACK);

        // Main title
        Font titleFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(titleFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2.0, height * 0.75, "CS61B: BYOW");

        // Enter Seed Message
        Font msgFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(msgFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2.0, height * 0.50, "Enter seed followed by S");

        // Seed display
        Font seedFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(seedFont);
        StdDraw.setPenColor(Color.YELLOW);
        String inputDisplay = "";
        if (seedString.isEmpty()) {
            inputDisplay = "_";
        } else {
            inputDisplay = seedString;
        }
        StdDraw.text(width / 2.0, height * 0.20, inputDisplay);

        // Error Message
        if (!errorMsg.isEmpty() && System.currentTimeMillis() < errorDisplayTime) {
            Font errFont = new Font("Monaco", Font.BOLD, 16);
            StdDraw.setFont(errFont);
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(width / 2.0, height * 0.10, errorMsg);
        }

        StdDraw.show();
    }

    private void setError(String message) {
        this.errorMsg = message;
        this.errorDisplayTime = System.currentTimeMillis() + 1000;
    }

    private void saveGame(long seed, Point avatarCoord, List<Point> enemyCoord) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("save.txt"))) {
            bw.write(seed + " " + avatarCoord.getX() + " " + avatarCoord.getY()
                         + " " + avatar.getHP());
//            System.out.println(enemyCoord);
            ArrayList<Point> curr = ef.getAllEnemyPos();
            for (Point p : curr){
                if (ef.ActiveEnemy(p)){
                    bw.write(" " + p.getX() + " " + p.getY());
                }
            }

            bw.newLine();
        } catch (IOException e) {
            setError("Failed to Save Game");
        }
    }

    private SavedWorld readSavedGame() {
        File f = new File("save.txt");
        if (!f.exists()) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("save.txt"))) {
            String line = br.readLine();
            if (line == null || line.isBlank()) {
                return null;
            }
            String[] lineArray = line.trim().split("\\s+");
            long seed = Long.parseLong(lineArray[0]);
            int x = Integer.parseInt(lineArray[1]);
            int y = Integer.parseInt(lineArray[2]);
            int hp = Integer.parseInt(lineArray[3]);

            ArrayList<Point> enemy = new ArrayList<>();
            for (int i = 4; i < lineArray.length; i += 2){
                System.out.println(lineArray.length);
                int v1 = Integer.parseInt(lineArray[i]);
                int v2 = Integer.parseInt(lineArray[i + 1]);
                System.out.println(enemy);
                enemy.add(new Point(v1, v2));
            }
            System.out.println(enemy);
            return new SavedWorld(seed, new Point(x, y), enemy, hp);

        } catch (Exception e) {
            return null;
        }
    }


    private TETile getAvatar() {
        if (char_ == Tileset.AVATAR){
            return Tileset.AVATAR2;
        }
        return Tileset.AVATAR;
    }

    public void startGame() {
        //Set any relevant variables before the game starts
        //Establish Engine loop
        while (true) {
            switch (state) {
                case MAIN -> {
                    // wait for user key input
                    drawMainMenu();
                    if (StdDraw.hasNextKeyTyped()) {
//                        String inputString = String.valueOf(StdDraw.nextKeyTyped());
                        char inputString = StdDraw.nextKeyTyped();
                        if (inputString == 'N' || inputString == 'n') {
                            // switch state to SEED_ENTRY & start new game
                            state = State.SEED_ENTRY;
                            while (StdDraw.hasNextKeyTyped()) {
                                StdDraw.nextKeyTyped();
                            }
                            StdDraw.pause(100);
                        } else if (inputString == 'L' || inputString == 'l') {
                            SavedWorld savedWorld = readSavedGame();
                            if (savedWorld == null) {
                                setError("No Saved Game");
                            } else {
                                activeSeed = savedWorld.seed();
                                avatarCoord = savedWorld.avatarCoord();
                                avatar = new Char();
                                avatar.setHP(savedWorld.HP());
                                avatar.addCharacter(world, avatarCoord, char_);

                                enemyCoord = savedWorld.enemyCoord();
//                                StdDraw.clear(Color.BLACK);
                                SavedWorld result = generateLoadedWorld(activeSeed, avatarCoord, enemyCoord, avatar.getHP());
                                activeSeed = result.seed();
                                avatarCoord = result.avatarCoord();
                                enemyCoord = result.enemyCoord();
                                char_ = getAvatar();
                                ef = new EnemyFeature();
                                ef.addAllEnemy(world, enemyCoord); // add all enemy

                                treasure = new Treasure();
                                treasure.addTreasure(world, new Point(avatar.getCharX(), avatar.getCharY()));
                                avatar.addCharacter(world, avatarCoord, char_); //  add

                                state = State.IN_WORLD;
                            }
                        } else if (inputString == 'C' || inputString == 'c'){
                            char_ = getAvatar();
                        } else if (inputString == 'Q' || inputString == 'q') {
                            // quit the game
                            System.exit(0);
                        } else {
                            // drawframe "invalid keyboard input"
                            setError("Invalid Keyboard Input");
                        }
                    }
                }

                case SEED_ENTRY -> {
                    Long seed = promptSeed();
                    if (seed != null) {
                        activeSeed = seed;
                        StdDraw.clear(Color.BLACK);

                        SavedWorld result = generateNewWorld(activeSeed);
                        activeSeed = result.seed();
                        avatarCoord = result.avatarCoord();
                        enemyCoord = result.enemyCoord();
                        char_ = getAvatar();
                        avatar = new Char();
                        avatar.addCharacter(world, avatarCoord, char_);
                        ef = new EnemyFeature();
                        ef.addAllEnemy(world, enemyCoord);

                        treasure = new Treasure();
                        treasure.addTreasure(world, new Point(avatar.getCharX(), avatar.getCharY()));

                        while (StdDraw.hasNextKeyTyped()) {
                            StdDraw.nextKeyTyped();
                        }
                        StdDraw.pause(100);

                        state = State.IN_WORLD;
                    } else {
                        state = State.MAIN;
                    }
                }
                // All commands within world happens here
                case IN_WORLD -> {
                    long start = System.currentTimeMillis();
                    int time_ = 180; // 3 minutes

                    while (true) {
                        long now = (System.currentTimeMillis() - start) / 1000;
                        long remaining = time_ - now;

                        HUD.renderHUD(ter, world, remaining, avatar.getHP());
                        Point pos = avatarCoord;

                        ef.enemyMove(world); // enemy moving function called

                        if (ef.InEnemyRange(pos, 1) && !ef.ActiveEnemy(pos)){
                            avatar.getDamage(1);
                            if (avatar.dead()){
                                System.exit(0);
//                                System.out.println("sss");
//                                state = State.MAIN;
                            }
                        }

                        if (remaining == 0){
                            System.exit(0);
                        }

                        if (StdDraw.hasNextKeyTyped()) {
                            char c = StdDraw.nextKeyTyped();

                            switch (c) {

                                case 'w', 'W':
                                    CharacterMove.moveUp(world, avatarCoord, ef, char_);
                                    break;

                                case 'a', 'A':
                                    CharacterMove.moveLeft(world, avatarCoord, ef, char_);
                                    break;

                                case 's', 'S':
                                    CharacterMove.moveDown(world, avatarCoord, ef, char_);
                                    break;

                                case 'd', 'D':
                                    CharacterMove.moveRight(world, avatarCoord, ef, char_);
                                    break;

                                case 'm', 'M':
                                    if (activeSeed != -1) {
                                        saveGame(activeSeed, avatarCoord, enemyCoord);
                                    }
                                    state = State.MAIN;
                                    break;

                                case ':':
                                    while (!StdDraw.hasNextKeyTyped()) {
                                        StdDraw.pause(20);
                                    }
                                    char a = StdDraw.nextKeyTyped();
                                    if (a == 'q' || a == 'Q') {
                                        if (activeSeed != -1 && avatarCoord != null) {
                                            saveGame(activeSeed, avatarCoord, enemyCoord);
                                        }
                                        System.exit(0);
                                    }
                                    break;

                                default:
                                    break;

                            }
                        }

                    }
                }
            }
            StdDraw.pause(100);
        }
    }

    // run this to test the new menu
    public static void main(String[] args) {
        Menu menu = new Menu(60, 30);
        menu.startGame();
    }
}
