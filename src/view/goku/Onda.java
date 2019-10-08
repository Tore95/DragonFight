package view.goku;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;

public class Onda extends GameObject {

    private Player target;
    private ImageView imageView;
    private int damage;
    private int distance;
    private int range;
    private boolean finish = false;

    public Onda(double x, double y, int damage, Direction direction, Player target) {
        super(x, y, 64, 64, 20, direction);
        this.damage = damage;
        this.target = target;
        this.distance = 0;
        String character = "goku.aura1.parameters.";
        this.range = Integer.parseInt(gs.getSpriteMapping().getProperty(character + "range"));
        int imgX = Integer.parseInt(gs.getSpriteMapping().getProperty(character + 'x'));
        int imgY = Integer.parseInt(gs.getSpriteMapping().getProperty(character + 'y'));
        int size = Integer.parseInt(gs.getSpriteMapping().getProperty(character + "size"));
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
            } else if (distance > range) finish = true;
        } else {
            gs.getAuraAttacks().getChildren().remove(imageView);
            gs.getToRemoveObjects().add(this);
        }
    }
}

