package view.goku;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;

public class Onda extends GameObject {

    private ImageView imageView;
    private int damage;
    private boolean finish = false;
    private Player target;
    private int distance = 0;


    public Onda(double x, double y, int damage, Direction direction, Player target) {
        super(x, y, 64, 64, 20, direction);
        this.damage = damage;
        this.target = target;
        int imgX = (128 * 5) - 32;
        int imgY = (128 * 23) - 32;
        int size = 64;
        imageView = new ImageView(gs.getGokuImage());
        imageView.setViewport(new Rectangle2D(imgX, imgY, size, size));
        moveImage();
        gs.getAuraAttacks().getChildren().add(imageView);
    }

    private void moveImage() {
        imageView.setX(getX() - (getWidth() / 2d));
        imageView.setY(getY() - (getHeight() / 2d));
        distance += getVelocity();
    }

    @Override
    public void draw() {
        if (!finish) {
            moveImage();
            if (isCollide(target)) {
                target.hitted(damage);
                finish = true;
            } else if (distance > 600) finish = true;
        } else {
            gs.getAuraAttacks().getChildren().remove(imageView);
            gs.getToRemoveObjects().add(this);
        }
    }
}

