package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.flames;
import static uet.oop.bomberman.BombermanGame.getBrickAt;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Item extends Entity {

    private int itemtype;

    public Item(int xUnit, int yUnit, Image img, int itemtype) {
        super(xUnit, yUnit, img);
        this.itemtype = itemtype;
    }

    public int getItemtype(){
        return itemtype;
    }



    @Override
    public void update() {
        if(intersects(flames) && !getBrickAt((int)getX(),(int)getY())) remove = true;
    }

}