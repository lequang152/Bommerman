package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Objects;

import static uet.oop.bomberman.BombermanGame.FPS;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;


    public boolean isRemove() {
        return remove;
    }

    protected boolean remove = false;

    public double getX() {
        return (double)x/Sprite.SCALED_SIZE;
    }

    public int getXPixel() {
        return x;
    }


    public double getY() {
        return (double) y/Sprite.SCALED_SIZE;
    }

    public int getYPixel() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x+4, y+4, img.getWidth()-8, img.getHeight()-8);
    }

  //  public Rectangle2D getBoundaryX() {
  //      return new Rectangle2D(x-3, y, img.getWidth()+6, img.getHeight()-1);
  //  }
  //  public boolean intersectsX(List<Entity> entities) {
  //      for(Entity entity : entities) {
  //          if(entity.getBoundaryX().intersects(this.getBoundary())) return true;
  //      }
  //      return false;
  //  }
//
  //  public Rectangle2D getBoundaryY() {
  //      return new Rectangle2D(x, y-3, img.getWidth()-1, img.getHeight()+6);
  //  }
  //  public boolean intersectsY(List<Entity> entities) {
  //      for(Entity entity : entities) {
  //          if(entity.getBoundaryY().intersects(this.getBoundary())) return true;
  //      }
  //      return false;
  //  }

    public boolean intersects(List<Entity> entities) {
        for(Entity entity : entities) {
            if(entity.getBoundary().intersects(this.getBoundary())) return true;
        }
        return false;
    }
    public boolean intersects(Entity entity) {
        if(entity.getBoundary().intersects(this.getBoundary())) return true;
        return false;
    }

    public static int pixelToTile(double i) {
        return (int)(i / 16);
    }

    public int getXTile() {
        return pixelToTile(x + 16 / 2);
    }

    public int getYTile() {
        return pixelToTile(y - 16 / 2);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

}
