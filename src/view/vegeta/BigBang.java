package view.vegeta;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;

public class BigBang extends GameObject {
    private ImageView imageView;
    private int damage;
    private boolean finish = false;
    private Player target;
    private int distance = 0;
    private int frame = 0;
    private boolean loading = true;
    private int viewX;
    private int viewY;
    private int size;


    public BigBang(double x, double y, int damage, Direction direction, Player target) {
        super(x, y, 256, 256, 20, direction);
        this.damage = damage;
        this.target = target;
        viewX = 128 * 4;
        viewY = 128 * 22;
        size = 256;
        imageView = new ImageView(gs.getVegetaImage());
        imageView.setViewport(new Rectangle2D(viewX, viewY, size, size));
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
            if (loading) {
                viewX += 256;
                imageView.setViewport(new Rectangle2D(viewX, viewY, size, size));
                frame++;
                if (frame == 4) loading = false;
            } else {
                moveImage();
                if (isCollide(target)) {
                    target.hitted(damage);
                    finish = true;
                } else if (distance > 1000) finish = true;
            }
        } else {
            gs.getAuraAttacks().getChildren().remove(imageView);
            gs.getToRemoveObjects().add(this);
        }
    }

}
