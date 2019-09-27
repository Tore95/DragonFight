package view;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Map {

    private Group map;
    private ImageView bg;

    private double bgX;

    public Map(Group background, String bgUrl) {
        this.map = background;
        bg = new ImageView(new Image(bgUrl,2266,720,true,false));
        bgX = -439;
        bg.setX(bgX);
        bg.setY(0);
        map.getChildren().add(bg);
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

    public Group getMap() {
        return map;
    }
}
