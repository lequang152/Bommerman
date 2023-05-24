package uet.oop.bomberman.LevelLoader;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.ImageLoader;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    private int width, height;
    private int levelnumber;
    private int playerx, playery;

    public Bomber getPlayer() {
        return new Bomber(playerx, playery, Sprite.player_right.getFxImage());
    }

    public int getWidth() {
        return Sprite.SCALED_SIZE * width;
    }

    public int getHeight() {
        return Sprite.SCALED_SIZE * height;
    }

    public int getLevelnumber() {
        return levelnumber;
    }

    public void loadLevel(int level, List<Entity> entities, List<Entity> stillObjects) {
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level" + level + ".txt");//doc tep luu map
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (!line.equals("")) {
                list.add(line);
                line = br.readLine();
                //doc file txt luu vao list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arrays = list.get(0).trim().split(" ");
        levelnumber = Integer.parseInt(arrays[0]);
        height = Integer.parseInt(arrays[1]);
        width = Integer.parseInt(arrays[2]);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(level==1) stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                if(level==2) stillObjects.add(new Grass(j, i, ImageLoader.Sand.getImage()));
                if(level==3) stillObjects.add(new Grass(j, i, ImageLoader.Lava_rock.getImage()));
                switch (list.get(i+1).charAt(j)) {
                    case '#':
                        if(level == 1) stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        if(level == 2) stillObjects.add(new Wall(j, i, ImageLoader.Water.getImage()));
                        if(level == 3) stillObjects.add(new Wall(j, i, ImageLoader.Lava.getImage()));
                        break;
                    // Thêm Portal
                    case 'x':
                        stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm brick
                    case '*':
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm Bomber
                     case 'p':
                        playerx = j;
                        playery = j;
                        break;

                    // Thêm balloon
                    case '1':
                        entities.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                        break;
                    // Thêm oneal
                    case '2':
                        entities.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    // Thêm doll
                    case '3':
                        entities.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                        break;
                    case '4':
                        entities.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                        break;
                    case '5':
                        entities.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
                        break;
                    //Thêm BomItem
                    case 'b':
                        entities.add(new Item(j, i, Sprite.powerup_bombs.getFxImage(), 0));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm FlameItem
                    case 'f':
                        entities.add(new Item(j, i, Sprite.powerup_flames.getFxImage(), 1));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm SpeedItem
                    case 's':
                        entities.add(new Item(j, i, Sprite.powerup_speed.getFxImage(), 2));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm BombPassItem
                    case 'B':
                        entities.add(new Item(j, i, Sprite.powerup_bombpass.getFxImage(), 3));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm FlamePassItem
                    case 'F':
                        entities.add(new Item(j, i, Sprite.powerup_flamepass.getFxImage(), 4));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm WallPassItem
                    case 'W':
                        entities.add(new Item(j, i, Sprite.powerup_wallpass.getFxImage(), 5));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // Thêm DetonatorItem
                    case 'D':
                        entities.add(new Item(j, i, Sprite.powerup_detonator.getFxImage(), 6));
                        entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                }

            }
        }
    }


}
