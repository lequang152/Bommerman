package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.score;
import static uet.oop.bomberman.Sound.SoundPlayer.mob_dead;

public class Minvo extends Mob {

    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected boolean checkBlock(int x,int y) {
        if (BombermanGame.getWallAt(x,y)) return true;
        return false;
    }

    protected boolean MinvocanMove() {
        if (direction == 3) return !checkBlock((int)(getX()+0.99), (int)(getY())) && !checkBlock((int)getX(), (int)(getY()));
        if (direction == 2) return !checkBlock((int)(getX()+0.99), (int)(getY()+1)) && !checkBlock((int)(getX()), (int)(getY()+1));
        if (direction == 1) return !checkBlock((int)(getX()), (int)(getY()+0.99)) && !checkBlock((int)(getX()), (int)(getY()));
        if (direction == 0) return !checkBlock((int)(getX()+1), (int)(getY()+0.99)) && !checkBlock((int)(getX()+1), (int)(getY()));
        return true;
    }

    private int delay = 0;
    protected void calculateMinvoMove() {
        if (delay > 0) {
            delay--;
        } else {
            delay = new Random().nextInt(2);
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

            if(MinvocanMove()) {
                step --;
                x += xa;
                y += ya;
            } else {
                step = 0;
            }
        }
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (direction == 3) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 2) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 1) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 0) {
                Sprite sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
        }
    }

    @Override
    public void update() {
        if(!die) {
            animate();
            calculateMinvoMove();
            IsDeath();
        } else {
            if(!scored) {
                score += 300;
                scored = true;
            }
            setImg(Sprite.minvo_dead.getFxImage());
            if(timedie>0) {
                timedie--;
            } else {
                SoundPlayer.play(mob_dead);
                remove = true;
            }
            if(timedie<BombermanGame.FPS) {
                chooseImageDeath(timedie+BombermanGame.FPS);
            }
            animate();
        }
    }
}
