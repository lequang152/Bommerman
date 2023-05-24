package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends AnimatedEntitiy {

    private boolean disapered = false;

    private int timedisaper = BombermanGame.FPS*3/2;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void chooseImage() {
        Sprite sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, _animate, BombermanGame.FPS*3/2);
        this.setImg(sprite.getFxImage());
    }

    @Override
    public void update() {
        if (BombermanGame.getFlameAt((int) getX(), (int) getY()) || disapered) {
            disapered = true;
            if (timedisaper > 0) {
                timedisaper--;
                animate();
            } else remove = true;
        }
    }



}
