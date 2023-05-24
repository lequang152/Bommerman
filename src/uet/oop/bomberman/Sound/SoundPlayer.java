package uet.oop.bomberman.Sound;

import javafx.scene.media.Media;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.entities.Doll;

import java.io.File;

public class SoundPlayer {
    public static MediaPlayer theme;
    public static MediaPlayer soundeffect;
    public static double themeVolume=0.5;
    public static double soundeffectVolume = 0.5;

    public static String stage_theme = "stage_theme";
    public static String menu = "menu";
    public static String bomb_explode = "bomb_explode";
    public static String bomber_dead = "bomber_dead";
    public static String mob_dead = "mob_dead";
    public static String victory = "victory";
    public static String loseS = "lose";
    public static String stage_start = "stage_start";


    public static void play(String name_sound) {
        String path = "res/music/" + name_sound + ".mp3";
        File file = new File(path);
        if (file.exists()) {
            Media media = new Media(file.toURI().toString());
            soundeffect = new MediaPlayer(media);
            soundeffect.setVolume(soundeffectVolume);
            soundeffect.play();
        } else {
            System.out.println("soundeffect not found");
        }
    }

    public static void playLoop(String name_sound) {
        if (theme != null) theme.stop();
        String path = "res/music/" + name_sound + ".mp3";
        File file = new File(path);
        if (file.exists()) {
            Media media = new Media(file.toURI().toString());
            theme = new MediaPlayer(media);
            theme.setVolume(themeVolume);
            theme.setOnEndOfMedia(new Runnable() {
                public void run() {
                    theme.seek(Duration.ZERO);
                }
            });
            theme.play();
        } else {
            System.out.println("theme not found");
        }
    }
}
