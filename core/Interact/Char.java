package core.Interact;

import core.generation.Point;
import tileengine.TETile;
import tileengine.Tileset;

public class Char {
    private Point pos;
    int HP;

    public Char(){
        this.HP = 10;
    }

    public int getCharX(){
        return pos.getX();
    }

    public int getCharY(){
        return pos.getY();
    }

    public Point getPosition(){
        return new Point(pos.getX(), pos.getY());
    }

    public void setPosition(Point x){
        this.pos = x;
    }

    public void setPosition(int x, int y){ // case with coordinate
        this.pos = new Point(x, y);
    }

    public int getHP(){
        return HP;
    }

    public void setHP(int x){
        this.HP = x;
    }

    public void getDamage(int x){
        this.HP -= x;
    }

    public boolean dead(){
        return this.HP <= 0;
    }

    public void addCharacter(TETile[][] world, Point pos, TETile a){
        this.pos = pos;
        world[pos.getX()][pos.getY()] = a;
    }

}
