package controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.PlayerOld;
import view.Map;

public class GameLauncher extends Application {

    private final long FPS = 1;
    private long lastFrame = System.nanoTime();
    private boolean addLife = true;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DragonFight");

        Group root = new Group();
        Group background = new Group();
        Group players = new Group();
        Group auraAttacks = new Group();
        Group UI = new Group();

        root.getChildren().add(0,background);
        root.getChildren().add(1,players);
        root.getChildren().add(2,auraAttacks);
        root.getChildren().add(3,UI);

        Scene scene = new Scene(root,1280,720);

        primaryStage.setScene(scene);

        Map map = new Map(background,"resources/bg.png");

        PlayerOld goku = new PlayerOld(1,players,auraAttacks,"resources/Goku.png");
        PlayerOld vegeta = new PlayerOld(2,players,auraAttacks,"resources/Vegeta.png");

        goku.setTarget(vegeta);
        vegeta.setTarget(goku);

        Text tgoku = new Text("Goku: " + goku.getLife());
        tgoku.setX(20);
        tgoku.setY(20);
        Text tvegeta = new Text("Vegeta: " + vegeta.getLife());
        tvegeta.setX(1200);
        tvegeta.setY(20);

        UI.getChildren().add(tgoku);
        UI.getChildren().add(tvegeta);


        // Hitbox test
        Ellipse hitGoku = new Ellipse(goku.getX(),goku.getY(),goku.getWidth() / 2d,goku.getHeight() / 2d);
        hitGoku.setStroke(Color.ORANGE);
        Ellipse hitVegeta = new Ellipse(vegeta.getX(),vegeta.getY(),vegeta.getWidth() / 2d,vegeta.getHeight() / 2d);
        hitVegeta.setStroke(Color.BLUE);
        UI.getChildren().add(hitGoku);
        UI.getChildren().add(hitVegeta);
        //


        new AnimationTimer() {
            @Override
            public void handle(long now) {

                int frameJump = (int) Math.floor((double)(now - lastFrame) / (double)(1000000000L / FPS));
                if (frameJump >= 1) {
                    lastFrame = now;

                    addLife = !addLife;
                    if (addLife) {
                        if (goku.getLife() < 100) goku.addLife(1);
                        if (vegeta.getLife() < 100) vegeta.addLife(1);
                    }

                    // Hitbox test
                    hitGoku.setCenterX(goku.getX());
                    hitGoku.setCenterY(goku.getY());
                    hitVegeta.setCenterX(vegeta.getX());
                    hitVegeta.setCenterY(vegeta.getY());
                    //
                }

                tgoku.setText("Goku: " + goku.getLife());
                tvegeta.setText("Vegeta: " + vegeta.getLife());

                if (goku.isDead() || vegeta.isDead()) {
                    Rectangle overlay = new Rectangle(0,0, 1280,720);
                    overlay.setFill(Color.WHITE);
                    UI.getChildren().add(overlay);
                    String winner = goku.isDead() ? "Vegeta" : "Goku";
                    UI.getChildren().add(new Text( 600, 300, winner + " win!"));
                }
            }
        }.start();


        /*
        AI intelligence = new AI(vegeta);
        intelligence.start();
        */

        scene.setOnKeyPressed(event -> {
            boolean isChanged = false;
            boolean isChanged2 = false;

            switch (event.getCode()) {
                case W: isChanged = goku.goUp(true); break;
                case S: isChanged = goku.goDown(true); break;
                case D: isChanged = goku.goRight(true); break;
                case A: isChanged = goku.goLeft(true); break;

                case Y: isChanged2 = vegeta.goUp(true); break;
                case H: isChanged2 = vegeta.goDown(true); break;
                case J: isChanged2 = vegeta.goRight(true); break;
                case G: isChanged2 = vegeta.goLeft(true); break;
            }
            if (isChanged) goku.getSpriteEngine().direction();
            if (isChanged2) vegeta.getSpriteEngine().direction();

        });

        scene.setOnKeyReleased(event -> {
            boolean isChanged = false;
            boolean isChanged2 = false;
            switch (event.getCode()) {
                case W: isChanged = goku.goUp(false); break;
                case S: isChanged = goku.goDown(false); break;
                case D: isChanged = goku.goRight(false); break;
                case A: isChanged = goku.goLeft(false); break;
                case E: goku.punch(); break;
                case Q: goku.kick(); break;
                case R: goku.auraOne(); break;
                case F: goku.auraTwo(); break;
                case C: goku.auraThree(); break;

                case Y: isChanged2 = vegeta.goUp(false); break;
                case H: isChanged2 = vegeta.goDown(false); break;
                case J: isChanged2 = vegeta.goRight(false); break;
                case G: isChanged2 = vegeta.goLeft(false); break;
                case U: vegeta.punch(); break;
                case T: vegeta.kick(); break;
                case I: vegeta.auraOne(); break;
                case K: vegeta.auraTwo(); break;
                case M: vegeta.auraThree(); break;
            }
            if (isChanged) goku.getSpriteEngine().direction();
            if (isChanged2) vegeta.getSpriteEngine().direction();

        });

        primaryStage.show();
    }
}
