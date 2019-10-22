package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;
import model.enums.Turned;

public class AuraFinal extends GameObject {

    private Player target;
    private ImageView imageView;
    private Direction toSet;
    private int damage;
    private int distance;
    private int frame;
    private int viewX;
    private int viewY;
    private int size;
    private int range;
    private long lastFrame;
    private boolean loading;
    private boolean finish;

    public AuraFinal(Character owner) {
        super(
                owner.getX() + owner.getAuraFinalOffsetX(),
                owner.getY() + owner.getAuraFinalOffsetY(),
                owner.getAuraFinalSize(),
                owner.getAuraFinalSize(),
                owner.getVelocity() + 10,
                Direction.STOP
        );
        this.target = owner.getTarget();
        this.damage = owner.getDamage() * 5;
        this.viewX = owner.getAuraFinalImgX();
        this.viewY = owner.getAuraFinalImgY();
        this.size = owner.getAuraFinalSize();
        this.range = owner.getAuraFinalRange();
        this.lastFrame = System.nanoTime();
        this.loading = true;
        this.finish = false;
        this.distance = 0;
        this.frame = 0;
        imageView = new ImageView(owner.getSprite());
        imageView.setViewport(new Rectangle2D(viewX, viewY, size, size));
        moveImage();
        gs.getAuraAttacks().getChildren().add(imageView);
        if (owner.getAuraFinalDirection().equals("oblique")) {
            toSet = owner.getTurned() == Turned.RIGHT ? Direction.DOWN_RIGHT : Direction.DOWN_LEFT;
        } else {
            toSet = owner.getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT;
        }
    }

    private void moveImage() {
        imageView.setX(getX() - (getWidth() / 2d));
        imageView.setY(getY() - (getHeight() / 2d));
        distance += getVelocity();
    }

    @Override
    public void draw() {
        moveImage();
        int frameJump = (int) Math.floor((double) (System.nanoTime() - lastFrame) / (double)(1000000000L / gs.getSpriteFPS()));
        if (frameJump >= 1) {
            lastFrame = System.nanoTime();

            if (!finish) {
                if (loading) {
                    viewX += size;
                    imageView.setViewport(new Rectangle2D(viewX, viewY, size, size));
                    frame++;
                    if (frame == 4) {
                        loading = false;
                        setDirection(toSet);
                    }
                } else {
                    if (isCollide(target)) {
                        target.hitted(damage);
                        finish = true;
                    } else if (distance > range) finish = true;
                }
            } else {
                gs.getAuraAttacks().getChildren().remove(imageView);
                gs.getToRemoveObjects().add(this);
            }
        }
    }


}
