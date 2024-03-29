package view.aura;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;
import model.enums.Turned;

public class MultiAuraTwo extends GameObject {

    private Player target;
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

    protected void setTarget(Player target) {
        this.target = target;
    }

    public MultiAuraTwo(Player owner) {
        super(
                owner.getX() + owner.getHashAuraTwo().get("offsetx"),
                owner.getY() + owner.getHashAuraTwo().get("offsety"),
                owner.getHashAuraTwo().get("sizey"),
                owner.getHashAuraTwo().get("sizex"),
                owner.getVelocity() + 6,
                owner.getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT
        );
        this.damage = owner.getDamage() * 4;
        setTarget(owner.getTarget());
        this.viewX = owner.getHashAuraTwo().get("imgx");
        this.viewY = owner.getHashAuraTwo().get("imgy");
        this.sizeX = owner.getHashAuraTwo().get("sizex");
        this.sizeY = owner.getHashAuraTwo().get("sizey");
        this.range = owner.getHashAuraTwo().get("range");
        this.tick = owner.getHashAuraTwo().get("tick");
        this.distance = 0;
        this.countTick = 0;
        this.finish = false;
        imageView = new ImageView(owner.getSprite());
        imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
        imageView.setX(getX() - (getWidth() / 2d));
        imageView.setY(getY() - (getHeight() / 2d));
        globalManager.getAuraAttacks().getChildren().add(imageView);

    }

    private void moveImage() {
        distance += getVelocity();
        countTick += getVelocity();
    }

    private void verifyCollision() {
        if (!finish) {
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
