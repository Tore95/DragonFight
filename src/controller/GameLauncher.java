package controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import model.enums.Characters;
import view.Character;
import view.Map;

public class GameLauncher extends Application {

    private GameStatus gs;
    private long lastFrame = System.nanoTime();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gs = GameStatus.getInstance();

        primaryStage.setTitle("DragonFight");
        primaryStage.setScene(gs.getScene());

        Map map = new Map();

        Character goku = new Character(1, Characters.GOKU);
        Character vegeta = new Character(2, Characters.VEGETA);

        goku.setTarget(vegeta);
        vegeta.setTarget(goku);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                int frameJump = (int) Math.floor((double)(now - lastFrame) / (double)(1000000000L / gs.getFPS()));
                if (frameJump >= 1) {
                    lastFrame = now;
                    gs.update();
                }
            }
        }.start();

        gs.getScene().setOnKeyPressed(event -> {
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
            if (isChanged) goku.direction();
            if (isChanged2) vegeta.direction();

        });

        gs.getScene().setOnKeyReleased(event -> {
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
                case C: goku.auraFinal(); break;

                case Y: isChanged2 = vegeta.goUp(false); break;
                case H: isChanged2 = vegeta.goDown(false); break;
                case J: isChanged2 = vegeta.goRight(false); break;
                case G: isChanged2 = vegeta.goLeft(false); break;
                case U: vegeta.punch(); break;
                case T: vegeta.kick(); break;
                case I: vegeta.auraOne(); break;
                case K: vegeta.auraTwo(); break;
                case M: vegeta.auraFinal(); break;
            }
            if (isChanged) goku.direction();
            if (isChanged2) vegeta.direction();

        });

        primaryStage.show();
    }
}
