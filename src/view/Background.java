package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background {

    public Background(String url) {
        GlobalManager globalManager = GlobalManager.getInstance();
        ImageView bg = new ImageView(new Image(url));
        bg.setX(0);
        bg.setY(0);
        globalManager.getBackground().getChildren().add(bg);
    }
}
