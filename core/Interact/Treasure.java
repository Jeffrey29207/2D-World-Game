package core.Interact;

import core.generation.Point;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.List;

public class Treasure {
    private Point pos;
    private List<Point> t;

    public Treasure(){
        t = new ArrayList<>();
    }

    public void addTreasure(TETile[][] world, Point pos){
        t.add(pos);
        world[pos.getX()][pos.getY()] = Tileset.CELL;
    }

    public void addAllTreasure(){

    }

    public Point getTreasureCoord(){
        return pos;
    }


}
