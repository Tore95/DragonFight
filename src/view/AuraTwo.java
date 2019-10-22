package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;
import model.enums.Turned;

public class AuraTwo extends GameObject {

    private Player target;
    private ImageView imageView;
    private int damage;
    private int distance;
    private int range;
    private int tick;
    private int countTick;
    private int viewX;
    private int viewY;
    private int sizeX;
    private int sizeY;
    private boolean finish;

    public AuraTwo(Character owner) {
        super(
                owner.getX() + owner.getAuraTwoOffsetX(),
                owner.getY() + owner.getAuraTwoOffsetY(),
                owner.getAuraTwoSizeY(),
                owner.getAuraTwoSizeX(),
                owner.getVelocity() + 6,
                owner.getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT
        );
        this.damage = owner.getDamage() * 4;
        this.target = owner.getTarget();
        this.viewX = owner.getAuraTwoImgX();
        this.viewY = owner.getAuraTwoImgY();
        this.sizeX = owner.getAuraTwoSizeX();
        this.sizeY = owner.getAuraTwoSizeY();
        this.range = owner.getAuraTwoRange();
        this.tick = owner.getAuraTwoTick();
        this.distance = 0;
        this.countTick = 0;
        this.finish = false;
        imageView = new ImageView(owner.getSprite());
        imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
        imageView.setX(getX() - (getWidth() / 2d));
        imageView.setY(getY() - (getHeight() / 2d));
        gs.getAuraAttacks().getChildren().add(imageView);
    }

    private void moveImage() {
        distance += getVelocity();
        countTick += getVelocity();
    }

    @Override
    public void draw() {
        if (!finish) {
            moveImage();
            if (countTick >= tick) {
                sizeX += tick;
                if (getDirection() == Direction.LEFT) {
                    viewX -= tick;
                    imageView.setX(imageView.getX() - tick);
                }
                viewY += tick;
                imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
                countTick = 0;
            }
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
