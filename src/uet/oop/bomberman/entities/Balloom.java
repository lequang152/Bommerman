package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.BombermanGame.score;
import static uet.oop.bomberman.Sound.SoundPlayer.mob_dead;

public class Balloom extends Mob {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (direction == 3) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 2) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 1) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 0) {
                Sprite sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, FPS/2);
                this.setImg(sprite.getFxImage());
            }
        }
    }

    private int time = 0;

    @Override
    public void update() {
        if(!die) {
            animate();
            calculateMove(1.0);
            IsDeath();
        } else if (die) {
            if(!scored) {
                SoundPlayer.play(mob_dead);
                score += 100;
                scored = true;
            }
            setImg(Sprite.balloom_dead.getFxImage());
            if(timedie>0) {
                timedie--;
            } else remove = true;
            if(timedie< FPS) {
                chooseImageDeath(timedie+ FPS);
            }
            animate();
        }
    }
}
