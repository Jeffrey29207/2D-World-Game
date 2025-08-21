package core.HUD;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;

public class HUD {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final int HUD_BAR_HEIGHT = 2;

    public static void init() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.enableDoubleBuffering();
    }

    public static void renderHUD(TERenderer ter, TETile[][] world, long time_, int HP) {
        ter.renderFrame(world);
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT + HUD_BAR_HEIGHT);
        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.filledRectangle(WIDTH / 2.0, HEIGHT + HUD_BAR_HEIGHT / 2.0, WIDTH / 2.0, HUD_BAR_HEIGHT / 2.0);

        // Determine hovered tile
        int mouseX = (int) Math.floor(StdDraw.mouseX());
        int mouseY = (int) Math.floor(StdDraw.mouseY());
        String tileType = "None";

        if (mouseX >= 0 && mouseX < WIDTH && mouseY >= 0 && mouseY < HEIGHT) {
            tileType = world[mouseX][mouseY].description();

            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.rectangle(mouseX + 0.5, mouseY + 0.5, 0.5, 0.5);
        }

        Font fontSmall = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontSmall);
        StdDraw.textLeft(1, HEIGHT + HUD_BAR_HEIGHT - 0.75, "Tile Type: " + tileType);
        StdDraw.text(WIDTH / 2.0, HEIGHT + HUD_BAR_HEIGHT - 0.75, "HP: " + HP);
        StdDraw.textRight(WIDTH - 2, HEIGHT + HUD_BAR_HEIGHT - 0.75, "Time " + time_ + "s");
        StdDraw.show();
        StdDraw.pause(100);

        }

    public static void main(String[] args) {
    }
}
