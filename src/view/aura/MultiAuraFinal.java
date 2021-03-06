package view.aura;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.Player;
import model.enums.Direction;
import model.enums.Turned;

public class MultiAuraFinal extends GameObject {

    private Player target;
    private Direction toSet;
    private int damage;
    private int distance;
    private int frame;
    private int viewX;
    private int viewY;
    private int size;
    private int range;
    private boolean loading;
    private boolean finish;

    protected void setTarget(Player target) {
        this.target = target;
    }

    public MultiAuraFinal(Player owner) {
        super(
                owner.getX() + owner.getHashAuraFinal().get("offsetx"),
                owner.getY() + owner.getHashAuraFinal().get("offsety"),
                owner.getHashAuraFinal().get("size"),
                owner.getHashAuraFinal().get("size"),
                owner.getVelocity() + 10,
                Direction.STOP
        );
        setTarget(owner.getTarget());
        this.damage = owner.getDamage() * 5;
        this.viewX = owner.getHashAuraFinal().get("imgx");
        this.viewY = owner.getHashAuraFinal().get("imgy");
        this.size = owner.getHashAuraFinal().get("size");
        this.range = owner.getHashAuraFinal().get("range");
        this.loading = true;
        this.finish = false;
        this.distance = 0;
        this.frame = 0;
        imageView = new ImageView(owner.getSprite());
        imageView.setViewport(new Rectangle2D(viewX, viewY, size, size));
        moveImage();
        globalManager.getAuraAttacks().getChildren().add(imageView);
        if (owner.getHashAuraFinal().get("dir") != 0) {
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

    private void verifyCollision() {
        if (!loading && isCollide(target)) {
            target.hitted(damage);
            finish = true;
        } else if (distance > range) finish = true;
    }

    @Override
    public void move() {
        super.move();
        moveImage();
        verifyCollision();
    }

    public void launch(Direction toSet) {
        setDirection(toSet);
    }

    @Override
    public void draw() {
        if (!finish && loading) {
            viewX += size;
            imageView.setViewport(new Rectangle2D(viewX, viewY, size, size));
            frame++;
            if (frame == 4) {
                loading = false;
                launch(toSet);
            }
        } else if (finish) {
            globalManager.getAuraAttacks().getChildren().remove(imageView);
            globalManager.getToRemoveObjects().add(this);
        }
    }
}
