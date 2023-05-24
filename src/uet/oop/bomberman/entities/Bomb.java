package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.Sound.SoundPlayer.bomb_explode;

public class Bomb extends AnimatedEntitiy {
    protected double _timeToExplode = BombermanGame.FPS * 2;

    private boolean move = true;
    public static boolean BomPass = false;
    public static int size = 1;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (_timeToExplode > 0 && !(BombermanGame.getFlameAt((int) getX(), (int) getY()))) {
            if(!detonator) _timeToExplode--;
            chooseImage();
            animate();
            if (move && !BomPass) canMove();
        } else {
            explode();
        }
    }

    public boolean isMove() {
        return move;
    }

    public void canMove() {
        if (!intersects(bomberman)) move = false;
    }

    public void explode() {
        makeFlames();
        Bomber.maxBombs++;
        SoundPlayer.play(bomb_explode);
        remove = true;
    }

    public void makeFlames() {

        flames.add(new Flame((int) getX(), (int) getY(), Sprite.bomb_exploded.getFxImage(), 0));
        //top
        for (int i = 1; i <= size; i++) {
            if (getWallAt((int) getX(), (int) getY() - i)) break;
            if (getBrickAt((int) getX(), (int) getY() - i)) {
                flames.add(new Flame((int) getX(), (int) getY() - i, Sprite.explosion_vertical_top_last.getFxImage(), 1));
                break;
            }
            if (i < size) {
                flames.add(new Flame((int) getX(), (int) getY() - i, Sprite.explosion_vertical.getFxImage(), 5));
            } else {
                flames.add(new Flame((int) getX(), (int) getY() - i, Sprite.explosion_vertical_top_last.getFxImage(), 1));
            }
        }

        //down
        for (int i = 1; i <= size; i++) {
            if (getWallAt((int) getX(), (int) getY() + i)) break;
            if (getBrickAt((int) getX(), (int) getY() + i)) {
                flames.add(new Flame((int) getX(), (int) getY() + i, Sprite.explosion_vertical_down_last.getFxImage(), 2));
                break;
            }
            if (i < size) {
                flames.add(new Flame((int) getX(), (int) getY() + i, Sprite.explosion_vertical.getFxImage(), 5));
            } else {
                flames.add(new Flame((int) getX(), (int) getY() + i, Sprite.explosion_vertical_down_last.getFxImage(), 2));
            }
        }

        //left
        for (int i = 1; i <= size; i++) {
            if (getWallAt((int) getX() - i, (int) getY())) break;
            if (getBrickAt((int) getX() - i, (int) getY())) {
                flames.add(new Flame((int) getX() - i, (int) getY(), Sprite.explosion_horizontal_left_last.getFxImage(), 3));
                break;
            }
            if (i < size) {
                flames.add(new Flame((int) getX() - i, (int) getY(), Sprite.explosion_horizontal.getFxImage(), 6));
            } else {
                flames.add(new Flame((int) getX() - i, (int) getY(), Sprite.explosion_horizontal_left_last.getFxImage(), 3));
            }
        }

        //right
        for (int i = 1; i <= size; i++) {
            if (getWallAt((int) getX() + i, (int) getY())) break;
            if (getBrickAt((int) getX() + i, (int) getY())) {
                flames.add(new Flame((int) getX() + i, (int) getY(), Sprite.explosion_horizontal_right_last.getFxImage(), 4));
                break;
            }
            if (i < size) {
                flames.add(new Flame((int) getX() + i, (int) getY(), Sprite.explosion_horizontal.getFxImage(), 6));
            } else {
                flames.add(new Flame((int) getX() + i, (int) getY(), Sprite.explosion_horizontal_right_last.getFxImage(), 4));
            }
        }
    }


    @Override
    public void chooseImage() {
        Sprite sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, BombermanGame.FPS / 2);
        this.setImg(sprite.getFxImage());
    }


}
