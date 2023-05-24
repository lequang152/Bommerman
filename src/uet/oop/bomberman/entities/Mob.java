package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.flames;


public abstract class Mob extends AnimatedEntitiy {

    protected int timedie = BombermanGame.FPS*2;

    protected boolean die = false;
    protected int direction = -1;
    protected final double rest;
    protected final double MAX_STEPS;
    protected double step = 0;
    public boolean scored = false;

    public boolean isDie() {
        return die;
    }

    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);

        MAX_STEPS = 16 / 0.5;
        rest = (MAX_STEPS - (int)MAX_STEPS) / MAX_STEPS;
        step = MAX_STEPS;
    }


    public void chooseImageDeath(int time) {
        Sprite sprite = Sprite.movingSprite(Sprite.mob_dead3, Sprite.mob_dead2, Sprite.mob_dead1, time, BombermanGame.FPS);
        this.setImg(sprite.getFxImage());
    }

    public void IsDeath() {
        if(intersects(flames)) die = true;
    }

    protected void calculateMove(double speed) {
        int xa = 0;
        int ya = 0;

        Random random = new Random();
        if (step <= 0) {
            direction = random.nextInt(4);
            step = 32;
        }

        if (direction == 0) xa++;
        if (direction == 2) ya++;
        if (direction == 3) ya--;
        if (direction == 1) xa--;

        if(canMove()) {
            step --;
            x += xa * speed;
            y += ya * speed;
        } else {
           step = 0;
        }
    }

    protected boolean canMove() {
        if (direction == 3) return !mobcheckBlock((int)(getX()+0.99), (int)(getY())) && !mobcheckBlock((int)getX(), (int)(getY()));
        if (direction == 2) return !mobcheckBlock((int)(getX()+0.99), (int)(getY()+1)) && !mobcheckBlock((int)(getX()), (int)(getY()+1));
        if (direction == 1) return !mobcheckBlock((int)(getX()), (int)(getY()+0.99)) && !mobcheckBlock((int)(getX()), (int)(getY()));
        if (direction == 0) return !mobcheckBlock((int)(getX()+1), (int)(getY()+0.99)) && !mobcheckBlock((int)(getX()+1), (int)(getY()));
        return true;
    }

    protected boolean mobcheckBlock(int x,int y) {
        if(BombermanGame.getBrickAt(x,y)) return true;
        if(BombermanGame.getWallAt(x,y)) return true;
        if(BombermanGame.getBombAt(x,y)) return true;
        return false;
    }
}
