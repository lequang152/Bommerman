package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

import static uet.oop.bomberman.BombermanGame.FPS;

public abstract class AnimatedEntitiy extends Entity {
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public final double SPEED = 165/FPS;

    public final double MOVECROSSSPEED = SPEED / Math.sqrt(2);

    public AnimatedEntitiy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void chooseImage();

    public int get_animate() {
        return _animate;
    }

    public void set_animate(int _animate) {
        this._animate = _animate;
    }

    public void animate() {
        chooseImage();
        if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
    }


}
