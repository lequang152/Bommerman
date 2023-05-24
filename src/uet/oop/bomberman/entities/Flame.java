package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;


public class Flame extends AnimatedEntitiy {

    private int time = BombermanGame.FPS*13/20;

    private int flametype;

    public Flame(int xUnit, int yUnit, Image img, int flametype) {
        super(xUnit, yUnit, img);
        this.flametype = flametype;
    }

    @Override
    public void update() {
        if (time > 0) {
            time--;
            chooseImage();
            animate();
        } else {
            remove = true;
        }
    }

    @Override
    public void chooseImage() {
        switch (flametype) {
            case 0:
                Sprite sprite0 = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, _animate, BombermanGame.FPS/2);
                this.setImg(sprite0.getFxImage());
                break;
            case 1:
                Sprite sprite1 = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, _animate, BombermanGame.FPS/2);
                this.setImg(sprite1.getFxImage());
                break;
            case 2:
                Sprite sprite2 = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, _animate, BombermanGame.FPS/2);
                this.setImg(sprite2.getFxImage());
                break;
            case 3:
                Sprite sprite3 = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, _animate, BombermanGame.FPS/2);
                this.setImg(sprite3.getFxImage());
                break;
            case 4:
                Sprite sprite4 = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, _animate, BombermanGame.FPS/2);
                this.setImg(sprite4.getFxImage());
                break;
            case 5:
                Sprite sprite5 = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, BombermanGame.FPS/2);
                this.setImg(sprite5.getFxImage());
                break;
            case 6:
                Sprite sprite6 = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, BombermanGame.FPS/2);
                this.setImg(sprite6.getFxImage());
                break;
        }
    }

}
