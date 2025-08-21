package core.Interact;

import core.generation.Point;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;

public class EnemyFeature {

    private ArrayList<Enemy> enemies;
    private int a = 0;
    public EnemyFeature(){
        this.enemies = new ArrayList<>();
    }

    public void addEnemy(TETile[][] world, Point pos){
        Enemy enemy = new Enemy(pos);
        enemies.add(enemy);
        world[pos.getX()][pos.getY()] = Tileset.ENEMY;
    }

    public void addAllEnemy(TETile[][] world, ArrayList<Point> pos){
        for (Point p : pos){
            addEnemy(world, p);
        }
    }

    public void deleteEnemy(TETile[][] world, Point pos){ // need improvement
        world[pos.getX()][pos.getY()] = Tileset.FLOOR;
        for (int i = 0; i < enemies.size(); i++){
            Enemy enemy = enemies.get(i);
            if (enemy.getPosition().getX() == pos.getX() && enemy.getPosition().getY() == pos.getY()){
                enemy.deActive();
                enemies.remove(i);
                break;
            }
        }
    }

    public ArrayList<Point> getAllEnemyPos(){
        ArrayList<Point> pos = new ArrayList<>();
        for (Enemy e : enemies){
            pos.add(e.getPosition());
        }
        return pos;
    }

    public boolean InEnemyRange(Point p, int range){
        for (Enemy enemy : enemies){
            if (!enemy.isActive()){
                continue;
            }
            Point enemy_ = enemy.getPosition();

            if (enemy_.equals(p)){
                continue;
            }

            int x = Math.abs(enemy_.getX() - p.getX());
            int y = Math.abs(enemy_.getY() - p.getY());
            if (x <= range && y <= range){
                return true;
            }
        }
        return false;
    }

    public boolean ActiveEnemy(Point pos) {
        for (Enemy enemy : enemies) {
            if (enemy.isActive() && enemy.getPosition().getX() == pos.getX() && enemy.getPosition().getY() == pos.getY()) {
                return true;
            }
        }
        return false;
    }

    public void enemyMove(TETile[][] world){
        a++;
        for (Enemy enemy : enemies){
//            if (a % 5 == 0){
                if (!enemy.isActive()){ // check if it is killed or not, deactivate
                    continue;
                }
                Point pos = enemy.getPosition();
                int x = pos.getX();
                int y = pos.getY();
                // Setting inside enemy class
                // -> keep moving in same direction until hit a wall;

                switch (enemy.getDirection()){
                    case 0:
                        x++;
                        break;
                    case 1:
                        x--;
                        break;
                    case 2:
                        y++;
                        break;
                    case 3:
                        y--;
                        break;
                }

                if (x < 0 || x >= world.length || y < 0 || y >= world[0].length ||
                        world[x][y] == Tileset.MOUNTAIN || world[x][y] == Tileset.CELL){
                    enemy.changeDirection();
                } else {
                    if (world[x][y] == Tileset.CELL){
                        continue;
                    }
                    world[pos.getX()][pos.getY()] = Tileset.FLOOR;
                    enemy.setPosition(x, y);
                    world[x][y] = Tileset.ENEMY;
//                StdDraw.pause(20);
//                }
            }

        }
    }
}
