package core.Interact;

import core.generation.Point;

import java.util.Random;


public class Enemy {

    private Point pos;
    private int HP;
    private boolean active;
    private int direction;
    private Random random = new Random();

    public Enemy(Point p){
        this.pos = p;
        this.HP = 1;
        this.active = true;
        this.direction = random.nextInt(4);
    }

    public int getDirection() {
        return direction;
    }

    public void changeDirection(){
        this.direction = random.nextInt(4);
    }

    public boolean isActive(){
        return active && !dead();
    }

    public void deActive(){
        this.active = false;
    }

    public int getEnemyX(){
        return pos.getX();
    }

    public int getEnemyY(){
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

    public void damage(int x){
        this.HP -= x;
    }

    public boolean dead(){
        return this.HP <= 0;
    }

}
