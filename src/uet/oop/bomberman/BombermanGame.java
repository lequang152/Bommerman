package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uet.oop.bomberman.LevelLoader.LevelLoader;
import uet.oop.bomberman.Sound.SoundPlayer;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.ImageLoader;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static uet.oop.bomberman.Sound.SoundPlayer.*;
import static uet.oop.bomberman.Sound.SoundPlayer.menu;
import static uet.oop.bomberman.graphics.ImageLoader.BGM;
import static uet.oop.bomberman.graphics.ImageLoader.Sound_effect;
import static uet.oop.bomberman.graphics.Sprite.DEFAULT_SIZE;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombermanGame extends Application {

    public static final int FPS = 60;
    private final int timeMax = 999;
    private int time = timeMax;
    private int timeSleeper = 5000;
    private int level = 1;

    //private Timer timerCountDown = new Timer();


    public double x = 0;
    public static int score = 0;
    public static int scorestart = 0;
    private boolean paused = true;
    private boolean pausedInGame = false;
    private boolean levelstarted = true;
    private boolean sleep = true;
    private boolean gameScene = false;

    private Scene scene;
    private Scene sceneMenu;
    private GraphicsContext gc;
    private Canvas canvas;
    private GraphicsContext gc2;
    private Canvas canvasMenu;
    private final Font fontlarge  = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 50);
    private final Font fontsmall = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 30);
    private final Font fontmedium = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 40);
    private final Font fontmedium1 = Font.loadFont(BombermanGame.class.getResource("/font/Pixeboy.ttf").toExternalForm(), 35);
    private Text textScore = new Text(20,22,"Score: " + score);
    private Text textTimer = new Text(20,22,"Timer: " + time);
    LevelLoader levelLoader = new LevelLoader();

    public static Bomber bomberman;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> flames= new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static boolean lose = false;
    public static boolean win = false;
    public static boolean iswin = false;
    public static boolean detonator = false;


    public static Entity getAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && !(entity instanceof Bomber))
                return entity;
        }
        return null;
    }
    public static boolean getFlameAt(int x, int y) {
        for(Entity entity : flames) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Flame)
                return true;
        }
        return false;
    }

    public static boolean getBrickAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Brick)
                return true;
        }
        return false;
    }

    public static boolean getPlayerBombAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Bomb && !((Bomb) entity).isMove())
                return true;
        }
        return false;
    }

    public static boolean getBombAt(int x, int y) {
        for(Entity entity : entities) {
            if((int)entity.getX() == x && (int)entity.getY() == y && entity instanceof Bomb)
                return true;
        }
        return false;
    }

    public static Entity getPlayer() {
        for(Entity entity : entities) {
            if(entity instanceof Bomber)
                return entity;
        }
        return null;
    }

    public static List<Entity> getAllMob() {
        List<Entity> t = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Mob && !((Mob) entity).isDie()) {
                t.add(entity);
            }
        }
        return t;
    }

    public static List<Entity> getAllBlock() {
        List<Entity> t = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Brick) {
                t.add(entity);
            }
        }
        for(Entity entity : stillObjects) {
            if(entity instanceof Wall) {
                t.add(entity);
            }
        }
        return t;
    }

    public static void explodeAllBomb() {
        for(Entity entity : entities) {
            if(entity instanceof Bomb) {
                ((Bomb) entity).explode();
            }
        }
    }

    public static List<Entity> getAllPortal() {
        List<Entity> t = new ArrayList<>();
        for(Entity entity : entities) {
            if(entity instanceof Portal) {
                t.add(entity);
            }
        }
        return t;
    }

    public static boolean getWallAt(double x, double y) {
        for(Entity entity : stillObjects) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && entity instanceof Wall)
                return true;
        }
        return false;
    }

    public static boolean getPortalAt(double x, double y) {
        for(Entity entity : stillObjects) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && entity instanceof Portal)
                return true;
        }
        return false;
    }

    public static boolean stillMob() {
        for(Entity entity : entities) {
            if(entity instanceof Mob)
                return true;
        }
        return false;
    }

    public static Item getItemAt(double x, double y) {
        for(Entity entity : entities) {
            if((int)Math.round(entity.getX()) == x && (int)Math.round(entity.getY()) == y && entity instanceof Item)
                return (Item) entity;
        }
        return null;
    }





    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }



    @Override
    public void start(Stage stage) {

        startTimer();

        loadMenuScene(stage);

        stage.setTitle("Bomberman by Haiten Team");
        stage.getIcons().add(ImageLoader.Icon.getImage());
        stage.setResizable(false);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public void update() {
        flames.removeIf(Entity::isRemove);
        flames.forEach(Entity::update);
        entities.removeIf(Entity::isRemove);
        entities.forEach(Entity::update);
    }

    public void render() {
        checkCamera();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
        textScore.setText("Score  " + score);
        textTimer.setText("Time  " + time);
    }

    public void checkCamera() {

        if(bomberman.getXPixel() > x + (canvas.getHeight() / 2) - 1 && x < canvas.getWidth() - canvas.getHeight() + SCALED_SIZE/2) {
            gc.translate(-bomberman.SpeedPlayer,0);
            x += bomberman.SpeedPlayer;
        }

        if(bomberman.getXPixel() < x + (canvas.getHeight() / 2) - 1 && x > 0) {
            gc.translate(bomberman.SpeedPlayer,0);
            x -= bomberman.SpeedPlayer;
        }
    }

    public void moveCamera() {
        if(x >= 0 && x < canvas.getWidth() - canvas.getHeight() + SCALED_SIZE/2) {
            gc.translate(-165/FPS,0);
            x += 165/FPS;
        }
    }

    public void renderearly() {
        moveCamera();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Level " + level, x + canvas.getHeight()/2,  canvas.getHeight()/2);
        if(level == 1) gc.setFill(Color.LIGHTGREEN);
        if(level == 2) gc.setFill(Color.YELLOW);
        if(level == 3) gc.setFill(Color.RED);
        textScore.setText("Score  " + score);
        textTimer.setText("Time  " + time);
        textTimer.wrappingWidthProperty().bind(new TabPane().widthProperty());
    }

    public void sleeperStart() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(timeSleeper);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                SoundPlayer.playLoop(stage_theme);
                paused = false;
                levelstarted = false;
                gc.translate(x,0);
                x = 0;
            }
        });
        new Thread(sleeper).start();
    }

    public void startTimer() {
        Timer timerCountDown = new Timer();
        timerCountDown.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(!paused && gameScene && !pausedInGame) {
                    if(time > 0 )
                    {
                        time--;
                    }
                    else
                        lose = true;
                }
            }
        }, 1000,1000);
    }


    public void checkWin() {
        if(!stillMob() && !win) {
            win = true;
        }
    }


    public void clear() {
        time = timeMax;
        entities.clear();
        stillObjects.clear();
        flames.clear();
        paused = true;
        levelstarted = true;
        win = false;
        iswin = false;
        lose = false;
        pausedInGame = false;
        detonator = false;
        Bomb.size = 1;
        Bomber.maxBombs = 1;
    }

    public void loadScene(int level) {
        clear();

        gc.translate(x,0);
        x = 0;

        levelLoader.loadLevel(level,entities,stillObjects);
        bomberman = levelLoader.getPlayer();
        entities.add(bomberman);

        canvas.setWidth(levelLoader.getWidth());
        canvas.setHeight(levelLoader.getHeight());
        textTimer.setX(canvas.getHeight()*0.65);
        sleeperStart();
    }

    public void loadFirstScene(Stage stage ,boolean playOnce) {
        levelLoader.loadLevel(level ,entities,stillObjects);
        bomberman = levelLoader.getPlayer();
        entities.add(bomberman);
        scorestart = score;
        // Tao Canvas
        canvas = new Canvas(levelLoader.getWidth(), levelLoader.getHeight());
        gc = canvas.getGraphicsContext2D();
        gc.setFont(fontlarge);

        textScore.setFont(fontsmall);
        textTimer.setFont(fontsmall);

        textTimer.setX(canvas.getHeight()*0.65);

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(textScore);
        root.getChildren().add(textTimer);

        // Tao scene
        scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.setWidth(canvas.getHeight());
        stage.show();
        // Them am thanh stage theme
        SoundPlayer.play(stage_start);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (levelstarted) {
                    stage.setWidth(canvas.getHeight());
                    stage.setHeight(canvas.getHeight()+39);
                    renderearly();
                } else {
                    render();
                    update();
                    checkWin();
                    if (iswin) {
                        level++;
                        VBox vBox = new VBox();
                        //chat.setPrefSize(400, 400);
                        Label text = new Label("Win");
                        text.setFont(fontlarge);
                        text.setStyle("-fx-text-fill: lightgreen");
                        Label text1 = new Label("Score = " + score);
                        text1.setFont(fontsmall);
                        text1.setStyle("-fx-text-fill: white");
                        Label text2 = new Label("Time  = " + time);
                        text2.setFont(fontsmall);
                        text2.setStyle("-fx-text-fill: white");
                        Label text3 = new Label("Score = " + (time + score));
                        text3.setFont(fontmedium);
                        text3.setStyle("-fx-text-fill: yellow");
                        vBox.getChildren().addAll(text, text1, text2, text3);
                        vBox.setSpacing(15);
                        vBox.setAlignment(Pos.CENTER);
                        vBox.setTranslateY(stage.getHeight()/5);
                        vBox.setTranslateX(stage.getWidth()/4);

                        vBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");

                        theme.stop();
                        SoundPlayer.play(victory);

                        score += time;
                        scorestart = score;

                        if(playOnce || level > 3) {
                            root.getChildren().addAll(vBox, buttonReturntomenu(this, stage));
                        } else root.getChildren().addAll(vBox, buttonReturntomenu(this, stage), buttonRetry(this, stage, root, "Next"));
                        stop();
                    }
                    if (lose) {
                        theme.stop();
                        SoundPlayer.play(loseS);
                        score = scorestart;
                        TextField text = new TextField("Lose");
                        text.setBackground(Background.EMPTY);
                        text.setFont(fontlarge);
                        text.setTranslateY(stage.getHeight()/4);
                        text.setTranslateX(stage.getWidth()/3);
                        text.setStyle("-fx-text-fill: red");
                        root.getChildren().addAll(buttonReturntomenu(this, stage), buttonRetry(this, stage, root, "Retry"), text);
                        stop();
                    }
                }
            }
        };

        timer.start();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!paused) {
                    bomberman.bombermove(event.getCode(), true);
                    if (event.getCode() == KeyCode.SPACE) {
                        bomberman.placeBomb();
                    }
                    if (event.getCode() == KeyCode.ESCAPE) {
                        if (pausedInGame) {
                            root.getChildren().remove(root.getChildren().size() - 1);
                            root.getChildren().remove(root.getChildren().size() - 1);
                            timer.start();
                            pausedInGame = false;
                        } else {
                            FlowPane pausedFlowPane = volumeFlowPane();
                            pausedFlowPane.setAlignment(Pos.BOTTOM_LEFT);
                            Button pausedButton = buttonReturntomenu(timer, stage);
                            root.getChildren().addAll(pausedFlowPane,pausedButton);
                            gc.drawImage(ImageLoader.Pause.getImage(), x + canvas.getHeight() / 2 - DEFAULT_SIZE * 5, canvas.getHeight() / 2 - DEFAULT_SIZE * 5);
                            timer.stop();
                            pausedInGame = true;
                        }
                    }
                    if (detonator == true && event.getCode() == KeyCode.E) {
                        explodeAllBomb();
                    }
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                bomberman.bombermove(event.getCode(), false);
            }
        });
        sleeperStart();
    }

    public void loadMenuScene(Stage stage) {
        BorderPane borderPane = new BorderPane();
        FlowPane buttons = new FlowPane(Orientation.VERTICAL);
        // button play
        Button play = new Button("Play");
        play.setFont(fontlarge);
        play.setBackground(null);
        play.setStyle("-fx-text-fill: rgb(255, 251, 209); -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
        play.setOnMouseEntered(e -> play.setStyle("-fx-text-fill: blue; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
        play.setOnMouseExited(e -> play.setStyle("-fx-text-fill: rgb(255, 250, 179); -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
        play.setOnAction(e -> {
            theme.stop();
            score = 0;
            gameScene = true;
            loadFirstScene(stage, false);
        });

        // text by Haiten Team
        Text text = new Text("by Haiten Team");


        // selectLevel
        Button selectLevel = new Button("Select level");
        selectLevel.setFont(fontlarge);
        selectLevel.setBackground(null);
        selectLevel.setStyle("-fx-text-fill: cyan; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
        selectLevel.setOnMouseEntered(e -> selectLevel.setStyle("-fx-text-fill: green; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
        selectLevel.setOnMouseExited(e -> selectLevel.setStyle("-fx-text-fill: cyan; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
        selectLevel.setOnAction(e -> {
            FlowPane levels = new FlowPane(Orientation.VERTICAL);

            Button level1 = new Button("Level 1");
            level1.setFont(fontlarge);
            level1.setBackground(null);
            level1.setStyle("-fx-text-fill: lightgreen; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
            level1.setOnMouseEntered(r -> level1.setStyle("-fx-text-fill: cyan; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
            level1.setOnMouseExited(r -> level1.setStyle("-fx-text-fill: lightgreen; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
            level1.setOnAction(r -> {
                theme.stop();
                score = 0;
                gameScene = true;
                level = 1;
                loadFirstScene(stage, true);
            });

            Button level2 = new Button("Level 2");
            level2.setFont(fontlarge);
            level2.setBackground(null);
            level2.setStyle("-fx-text-fill: yellow; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
            level2.setOnMouseEntered(r -> level2.setStyle("-fx-text-fill: cyan; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
            level2.setOnMouseExited(r -> level2.setStyle("-fx-text-fill: yellow; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
            level2.setOnAction(r -> {
                theme.stop();
                score = 0;
                gameScene = true;
                level = 2;
                loadFirstScene(stage, true);
            });

            Button level3 = new Button("Level 3");
            level3.setFont(fontlarge);
            level3.setBackground(null);
            level3.setStyle("-fx-text-fill: red; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
            level3.setOnMouseEntered(r -> level3.setStyle("-fx-text-fill: cyan; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
            level3.setOnMouseExited(r -> level3.setStyle("-fx-text-fill: red; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
            level3.setOnAction(r -> {
                theme.stop();
                score = 0;
                gameScene = true;
                level = 3;
                loadFirstScene(stage, true);
            });

            levels.getChildren().addAll(level1,level2,level3);
            levels.setAlignment(Pos.CENTER_RIGHT);

            borderPane.setCenter(levels);
        });

        // button exit
        Button quit = new Button("quit");
        quit.setFont(fontlarge);
        quit.setBackground(null);
        quit.setStyle("-fx-text-fill: rgb(191, 202 ,230); -fx-effect: dropshadow( one-pass-box , rgb(191, 202 ,230) , 8 , 0.0 , 2 , 0 )");
        quit.setOnMouseEntered(e -> quit.setStyle("-fx-text-fill: red; -fx-effect: dropshadow( one-pass-box , rgb(191, 202 ,230) , 8 , 0.0 , 2 , 0 )"));
        quit.setOnMouseExited(e -> quit.setStyle("-fx-text-fill: rgb(191, 202 ,230); -fx-effect: dropshadow( one-pass-box , rgb(191, 202 ,230) , 8 , 0.0 , 2 , 0 )"));
        quit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        buttons.getChildren().addAll( play, selectLevel , quit, volumeFlowPane()) ;
        buttons.setAlignment(Pos.CENTER_LEFT);

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(ImageLoader.Background.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        borderPane.setBottom(text);
        BorderPane.setMargin(text, new Insets(12,12,12,12));
        borderPane.setAlignment(text, Pos.CENTER);
        borderPane.setCenter(buttons);


        sceneMenu = new Scene(borderPane);
        stage.setScene(sceneMenu);
        stage.setWidth(15 * SCALED_SIZE);
        stage.setHeight(15 * SCALED_SIZE);
        stage.show();

        SoundPlayer.playLoop(menu);

        sceneMenu.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    borderPane.setCenter(buttons);
                }
            }
        });
    }

    public FlowPane volumeFlowPane() {
        FlowPane flowPane = new FlowPane();

        Slider themeSlider = new Slider();
        themeSlider.setMaxWidth(SCALED_SIZE*4);
        themeSlider.setMinHeight(SCALED_SIZE);
        themeSlider.setTranslateX(40);
        themeSlider.setTranslateY(50);
        themeSlider.setValue(themeVolume*100);
        themeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(theme != null) theme.setVolume(themeSlider.getValue()/100);
                themeVolume = themeSlider.getValue()/100;
            }
        });

        Slider soundeffectSlider = new Slider();
        soundeffectSlider.setMaxWidth(SCALED_SIZE*4);
        soundeffectSlider.setMinHeight(SCALED_SIZE);
        soundeffectSlider.setTranslateX(40);
        soundeffectSlider.setTranslateY(50);
        soundeffectSlider.setValue(soundeffectVolume*100);
        soundeffectSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                soundeffectVolume = soundeffectSlider.getValue()/100;
            }
        });

        ImageView image1 = new ImageView(BGM.getImage());
        image1.setTranslateX(40);
        image1.setTranslateY(50);

        ImageView image2 = new ImageView(Sound_effect.getImage());
        image2.setTranslateX(40);
        image2.setTranslateY(50);

        flowPane.getChildren().addAll(image1, themeSlider, image2, soundeffectSlider);
        flowPane.setHgap(10);
        return flowPane;
    }

    public Button buttonReturntomenu(AnimationTimer timer, Stage stage) {
        Button rMenu = new Button("Return to menu");
        rMenu.setFont(fontmedium1);
        rMenu.setBackground(null);
        rMenu.setStyle("-fx-text-fill: white; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
        rMenu.setOnMouseEntered(e -> rMenu.setStyle("-fx-text-fill: red; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
        rMenu.setOnMouseExited(e -> rMenu.setStyle("-fx-text-fill: white; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )" ));
        rMenu.setTranslateY(stage.getHeight()-100);
        rMenu.setOnAction(e -> {
            clear();
            level = 1;
            gameScene = false;
            x = 0;
            loadMenuScene(stage);
        });
        return rMenu;
    }

    public Button buttonRetry(AnimationTimer timer,Stage stage,Group root, String s) {
        Button retry = new Button(s);
        retry.setFont(fontmedium);
        retry.setBackground(null);
        retry.setStyle("-fx-text-fill: green; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
        retry.setOnMouseEntered(e -> retry.setStyle("-fx-text-fill: yellow; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
        retry.setOnMouseExited(e -> retry.setStyle("-fx-text-fill: lightgreen; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )"));
        retry.setTranslateY(stage.getHeight()-100);
        retry.setTranslateX(stage.getWidth()-150);
        retry.setOnAction(e -> {
            SoundPlayer.play(stage_start);
            loadScene(level);
            timer.start();
            root.getChildren().remove(root.getChildren().size() - 1);
            root.getChildren().remove(root.getChildren().size() - 1);
            root.getChildren().remove(root.getChildren().size() - 1);
        });
        return retry;
    }
}