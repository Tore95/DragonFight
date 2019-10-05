package view.goku;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;

public class Kamehameha extends GameObject {

    private ImageView imageView;
    private int damage;
    private boolean finish;
    private Player target;
    private int distance;
    private int viewX;
    private int viewY;
    private int sizeX;
    private int sizeY;
    private int distTick;

    public Kamehameha(double x, double y, int damage, Direction direction, Player target) {
        super(x, y, 64, 64, 16, direction);
        this.damage = damage;
        this.target = target;
        viewX = direction == Direction.RIGHT ? 0 : (18 * 128) + 64;
        viewY = 22 * 128;
        sizeX = 64;
        sizeY = 64;
        distTick = 0;
        distance = 0;
        imageView = new ImageView(gs.getGokuImage());
        imageView.setViewport(new Rectangle2D(viewX, viewY, sizeX, sizeY));
        imageView.setX(getX() - (getWidth() / 2d));
        imageView.setY(getY() - (getHeight() / 2d));
        gs.getAuraAttacks().getChildren().add(imageView);

    }

    private void moveImage() {
        distance += getVelocity();
        distTick += getVelocity();
    }

    @Override
    public void draw() {
        if (!finish) {
            moveImage();
            if (distTick >= 64) {
                sizeX += 64;
                if (getDirection() == Direction.LEFT) {
                    viewX -= 64;
                    imageView.setX(imageView.getX() - 64);
                }
                viewY += 64;
                imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
                distTick = 0;
            }
            if (isCollide(target)) {
                target.hitted(damage);
                finish = true;
            } else if (distance > 800) finish = true;
        } else {
            gs.getAuraAttacks().getChildren().remove(imageView);
            gs.getToRemoveObjects().add(this);
        }

    }
}
