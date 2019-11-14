package view.aura;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;
import model.enums.Turned;

public class MultiAuraOne extends GameObject {

    private Player target;
    private int damage;
    private int distance;
    private int range;
    private boolean finish;

    protected void setTarget(Player target) {
        this.target = target;
    }

    public MultiAuraOne(Player owner) {
        super(
                owner.getX() + owner.getHashAuraOne().get("offsetx"),
                owner.getY() + owner.getHashAuraOne().get("offsety"),
                owner.getHashAuraOne().get("size"),
                owner.getHashAuraOne().get("size"),
                owner.getVelocity() + 10,
                owner.getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT
        );
        setTarget(owner.getTarget());
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
        globalManager.getAuraAttacks().getChildren().add(imageView);
    }

    private void moveImage() {
        imageView.setX(getX() - (getWidth() / 2d));
        imageView.setY(getY() - (getHeight() / 2d));
        distance += getVelocity();
    }

    private void verifyCollision() {
        if (!finish) {
            if (isCollide(target)) {
                target.hitted(damage);
                finish = true;
            } else if (distance > range) finish = true;
        } else {
            globalManager.getAuraAttacks().getChildren().remove(imageView);
            globalManager.getToRemoveObjects().add(this);
        }
    }

    @Override
    public void move() {
        super.move();
        moveImage();
        verifyCollision();
    }

    @Override
    public void draw() {}
}
