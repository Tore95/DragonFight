package old.view;

import old.controller.GameStatus;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Map {

    private GameStatus gs;
    private ImageView bg;

    private double bgX;

    public Map() {
        gs = GameStatus.getInstance();
        bg = new ImageView(new Image("resources/bg.png",2266,720,true,false));
        bgX = -439;
        bg.setX(bgX);
        bg.setY(0);
        gs.getBackground().getChildren().add(bg);
    }

    public void moveBgRight(double val) {
        if (bgX - val >= -986) {
            bgX -= val;
            bg.setX(bgX);
        } else {
            bgX = -986;
            bg.setX(bgX);
        }
    }

    public void moveBgLeft(double val) {
        if (bgX + val <= 0) {
            bgX += val;
            bg.setX(bgX);
        } else {
            bgX = 0;
            bg.setX(bgX);
        }
    }
}
