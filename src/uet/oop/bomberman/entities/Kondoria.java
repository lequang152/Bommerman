package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.flames;
import static uet.oop.bomberman.BombermanGame.score;
import static uet.oop.bomberman.Sound.SoundPlayer.mob_dead;

public class Kondoria extends Mob {
    private int heart = 160;

    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void chooseImage() {
        if(!die) {
            if (direction == 3) {
                Sprite sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 2) {
                Sprite sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 1) {
                Sprite sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
            if (direction == 0) {
                Sprite sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, BombermanGame.FPS/2);
                this.setImg(sprite.getFxImage());
            }
        }
    }

    public void IsDead() {
        if(intersects(flames)) heart --;
        if (heart <= 0) {
            die = true;
        }
    }


    @Override
    public void update() {
        if(!die) {
            animate();
            calculateMove(1.0);
            IsDead();
        } else {
            if(!scored) {
                score += 500;
                scored = true;
            }
            setImg(Sprite.kondoria_dead.getFxImage());
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
