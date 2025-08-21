package core.Interact;

import core.generation.Point;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;


public class CharacterMove {

    public Point a;

    public static void Move(TETile[][] world, Point avatarCoord, EnemyFeature ef, int newX, int newY, TETile a){
        if (newX < 0 || newX >= world.length || newY < 0 || newY >= world[0].length){
            return;
        }
        // not moving for walls
        if (world[newX][newY] == Tileset.MOUNTAIN){
            return;
        }

        if (world[newX][newY] == Tileset.ENEMY){
            Point pt = new Point(newX, newY);
            ef.deleteEnemy(world, pt);
        }

        world[avatarCoord.getX()][avatarCoord.getY()] = Tileset.FLOOR;
        avatarCoord.setX(newX);
        avatarCoord.setY(newY);

        world[newX][newY] = a;
    }

    public static void moveUp(TETile[][] world, Point point, EnemyFeature ef, TETile a){
        Move(world, point, ef, point.getX(), point.getY() + 1, a);
    }

    public static void moveDown(TETile[][] world, Point point, EnemyFeature ef, TETile a){
        Move(world, point, ef, point.getX(), point.getY() - 1, a);
    }

    public static void moveLeft(TETile[][] world, Point point, EnemyFeature ef, TETile a){
        Move(world, point, ef, point.getX() - 1, point.getY(), a);
    }

    public static void moveRight(TETile[][] world, Point point, EnemyFeature ef, TETile a){
        Move(world, point, ef, point.getX() + 1, point.getY(), a);
    }
}
