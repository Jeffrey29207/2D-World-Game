package core.Menu;

import core.generation.Point;

import java.util.ArrayList;

public record SavedWorld(long seed, Point avatarCoord, ArrayList<Point> enemyCoord, int HP) {}
