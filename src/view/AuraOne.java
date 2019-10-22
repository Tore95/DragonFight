package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;
import model.enums.Turned;

import java.util.HashMap;

public class AuraOne extends GameObject {

    private Player target;
    private ImageView imageView;
    private int damage;
    private int distance;
    private int range;
    private boolean finish;

    public AuraOne(Character owner) {
        super(
                owner.getX() + owner.getHashAuraOne().get("offsetx"),
                owner.getY() + owner.getHashAuraOne().get("offsety"),
                owner.getHashAuraOne().get("size"),
                owner.getHashAuraOne().get("size"),
                owner.getVelocity() + 10,
                owner.getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT
        );
        this.target = owner.getTarget();
        this.damage = owner.getDamage() * 3;
        this.range = owner.getHashAuraOne().get("range");
        this.distance = 0;
        this.finish = false;
        imageView = new ImageView(owner.getSprite());
        imageView.setViewport(new Rectangle2D(
                owner.getHashAuraOne().get("imgx"),
                owner.getHashAuraOne().get("imgy"),
                owner.getHashAuraOne().get("size"),
                owner.getHashAuraOne().get("size")
        ));
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
