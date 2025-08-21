package core.generation;

import tileengine.TETile;
import tileengine.Tileset;

public class WallGenerator {
    public static void generateWalls(TETile[][] world, int width, int height) {
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                boolean isFloorTile = world[x][y] == Tileset.FLOOR;
                boolean NoTopTile = world[x][y + 1] == Tileset.NOTHING;
                boolean NoBottomTile = world[x][y - 1] == Tileset.NOTHING;
                boolean NoLeftTile = world[x - 1][y] == Tileset.NOTHING;
                boolean NoRightTile = world[x + 1][y] == Tileset.NOTHING;

                if (isFloorTile) {
                    if (NoTopTile) {
                        world[x][y + 1] = Tileset.MOUNTAIN;
                    }
                    if (NoBottomTile) {
                        world[x][y - 1] = Tileset.MOUNTAIN;
                    }
                    if (NoLeftTile) {
                        world[x - 1][y] = Tileset.MOUNTAIN;
                    }
                    if (NoRightTile) {
                        world[x + 1][y] = Tileset.MOUNTAIN;
                    }
                }
            }
        }
    }
}
