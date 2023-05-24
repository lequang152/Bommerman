package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends Mob {
    protected int direction = -1;

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected int superCalculateDirection() {
        if (BombermanGame.bomberman == null) {
            return new Random().nextInt(4);
        }

        int vertical = new Random().nextInt(2);

        if(vertical == 1) {
            int v = calculateRowDirection();
            if(v != -1)
                return v;
            else
                return calculateColDirection();

        } else {
            int h = calculateColDirection();

            if(h != -1)
                return h;
            else
                return calculateRowDirection();
        }

    }

    private int delay = 0;
    protected void superCalculateMove() {
        if (delay > 0) {
            delay--;
        } else {
            delay = new Random().nextInt(5);
            int xa = 0;
            int ya = 0;

            if (step <= 0) {
                direction = superCalculateDirection();
                step = 32;
            }

            if (direction == 0) xa++;
            if (direction == 2) ya++;
            if (direction == 3) ya--;
            if (direction == 1) xa--;

            if(OnealcanMove()) {
                step --;
                x += xa;
                y += ya;
            } else {
                step = 0;
            }
        }
    }

    protected boolean OnealcanMove() {
        if (direction == 3) return !mobcheckBlock((int)(getX()+0.9), (int)(getY())) && !mobcheckBlock((int)getX(), (int)(getY()));
        if (direction == 2) return !mobcheckBlock((int)(getX()+0.9), (int)(getY()+1)) && !mobcheckBlock((int)(getX()), (int)(getY()+1));
        if (direction == 1) return !mobcheckBlock((int)(getX()-0.05), (int)(getY()+0.2)) && !mobcheckBlock((int)(getX()-0.05), (int)(getY()+0.95));
        if (direction == 0) return !mobcheckBlock((int)(getX()+0.8), (int)(getY()+0.2)) && !mobcheckBlock((int)(getX()+0.8), (int)(getY()+0.95));
        return true;
    }

    protected boolean mobcheckBlock(int x,int y) {
        if(BombermanGame.getBrickAt(x,y)) return true;
        if(BombermanGame.getWallAt(x,y)) return true;
        return false;
    }

    protected int calculateColDirection() {
        if(BombermanGame.bomberman.getXTile() < this.getXTile())
            return 1;
        else if(BombermanGame.bomberman.getXTile() > this.getXTile())
            return 0;
        return -1;
    }

    protected int calculateRowDirection() {
        if(BombermanGame.bomberman.getYTile() < this.getYTile())
            return 3;
        else if(BombermanGame.bomberman.getYTile() > this.getYTile())
            return 2;
        return -1;
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (direction == 3) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 2) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 1) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 0) {
                Sprite sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
        }
    }

    @Override
    public void update() {
        if(!die) {
            animate();
            superCalculateMove();
            IsDeath();
        } else {
            setImg(Sprite.oneal_dead.getFxImage());
            if(timedie>0) {
                timedie--;
            } else remove = true;
            if(timedie<BombermanGame.FPS) {
                chooseImageDeath(timedie+BombermanGame.FPS);
            }
            animate();
        }
    }
}
