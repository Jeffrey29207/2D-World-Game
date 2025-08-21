package core.Interact;

import core.generation.Point;
import tileengine.TETile;
import tileengine.Tileset;

public class EnemyMove {

        public Point a;

        public static void Move(TETile[][] world, Point Coord, int newX, int newY){
            if (newX < 0 || newX >= world.length || newY < 0 || newY >= world[0].length){
                return;
            }
            // not moving for walls
            if (world[newX][newY] == Tileset.MOUNTAIN){
                return;
            }

            world[Coord.getX()][Coord.getY()] = Tileset.FLOOR;
            Coord.setX(newX);
            Coord.setY(newY);

            world[newX][newY] = Tileset.ENEMY;
        }

        public static void moveUp(TETile[][] world, Point point, EnemyFeature ef){
            Move(world, point, point.getX(), point.getY() + 1);
        }

        public static void moveDown(TETile[][] world, Point point, EnemyFeature ef){
            Move(world, point, point.getX(), point.getY() - 1);
        }

        public static void moveLeft(TETile[][] world, Point point, EnemyFeature ef){
            Move(world, point,point.getX() - 1, point.getY());
        }

        public static void moveRight(TETile[][] world, Point point, EnemyFeature ef){
            Move(world, point,point.getX() + 1, point.getY());
        }

}
