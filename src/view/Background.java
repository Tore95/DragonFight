package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.GlobalManager;

public class Background {

    private ImageView bg;

    private double bgX;

    public Background() {
        GlobalManager globalManager = GlobalManager.getInstance();
        bg = new ImageView(new Image("resources/bg.png",2266,720,true,false));
        bgX = -439;
        bg.setX(bgX);
        bg.setY(0);
        globalManager.getBackground().getChildren().add(bg);
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
