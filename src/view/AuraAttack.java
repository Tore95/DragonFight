package view;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.GameObject;
import model.enums.Direction;
import model.enums.Turned;

public class AuraAttack extends AnimationTimer {

    private GameObject auraObj;
    private final long FPS = 32;

    private long lastFrame;

    private double x;
    private double y;
    private double velocity;
    private int height;
    private int width;
    private Direction direction;

    private int viewX;
    private int viewY;
    private int sizeY;
    private int sizeX;

    private int damage;
    private Player target;
    private Group auraAttacks;
    private ImageView imageView;
    private Image playerSprite;
    private long timer;
    private long seconds;
    private long timerStop;
    private boolean start;
    private int attackNumber;
    private long distTick;
    private boolean loading;
    private int frame;

    public AuraAttack(Player owner, int attackNumber) {
        switch (attackNumber) {
            case 1:
                x = owner.getTurned() == Turned.RIGHT ? owner.getX() + 32 : owner.getX() - 32;
                y = owner.getY() - 16;
                height = 64;
                width = 64;
                velocity = owner.getVelocity();
                direction = owner.getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT;
                damage = owner.getDamage() * 3;
                seconds = 1;
                break;
            case 2:
                x = owner.getTurned() == Turned.RIGHT ? owner.getX() + 64 + 32 : owner.getX() - 32;
                y = owner.getY() + 16;
                height = 64;
                width = 64;
                velocity = owner.getVelocity();
                direction = owner.getTurned() == Turned.RIGHT ? Direction.RIGHT : Direction.LEFT;
                damage = owner.getDamage() * 4;
                seconds = 1;
                break;
            case 3:
                x = owner.getX() - 64;
                y = owner.getY() - 256;
                height = 256;
                width = 256;
                velocity = owner.getVelocity();
                direction = owner.getTurned() == Turned.RIGHT ? Direction.DOWN_RIGHT : Direction.DOWN_LEFT;
                damage = owner.getDamage() * 5;
                seconds = 1;
                break;
        }

        this.auraObj = new GameObject(x, y, height, width, velocity, direction);
        this.target = (Player) owner.getTarget();
        this.auraAttacks = owner.getAuraAttacks();
        this.playerSprite = owner.getSpriteImage();
        this.attackNumber = attackNumber;
        this.lastFrame = timer;
        this.distTick = 0;
        this.start = false;
    }

    @Override
    public void stop() {
        super.stop();
        start = false;
        auraAttacks.getChildren().remove(imageView);
        if (auraObj.isCollide(target)) target.hittingMe(damage);
    }

    @Override
    public void start() {
        super.start();
        start = true;
        timer = System.nanoTime();
        timerStop = timer + (seconds * 1000000000L);

        switch (attackNumber) {
            case 1:
            case 2:
                imageView = new ImageView(playerSprite);
                viewX = 0;
                viewX = auraObj.getDirection() == Direction.RIGHT ? 0 : (18 * 128) + 64;
                viewY = 22 * 128;
                sizeX = 64;
                sizeY = 64;
                imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
                // image set here
                imageView.setX(auraObj.getX() - ((1d/2) * auraObj.getWidth()));
                imageView.setY(auraObj.getY() - ((1d/2) * auraObj.getHeight()));
                //
                auraAttacks.getChildren().add(imageView);
                break;
            case 3:
                imageView = new ImageView(playerSprite);
                viewX = 128 * 4;
                viewY = 128 * 22;
                sizeX = auraObj.getWidth();
                sizeY = auraObj.getHeight();
                imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
                imageView.setX(auraObj.getX());
                imageView.setY(auraObj.getY());
                auraAttacks.getChildren().add(imageView);
                loading = true;
                frame = 0;
                break;
        }
    }

    @Override
    public void handle(long now) {
        int frameJump = (int) Math.floor((double) (now - lastFrame) / (double) (1000000000L / FPS));
        if (now > timerStop) stop();
        if (frameJump >= 1) {
            if (auraObj.isCollide(target)) stop();
            switch (attackNumber) {
                case 1:
                    auraObj.move();
                    // image set here
                    imageView.setX(auraObj.getX() - ((1d/2) * auraObj.getWidth()));
                    //
                    break;
                case 2:
                    distTick += auraObj.getVelocity();
                    auraObj.move();
                    if (distTick >= 32 + 16) {
                        sizeX += 64;
                        if (auraObj.getDirection() == Direction.LEFT) {
                            viewX -= 64;
                            imageView.setX(imageView.getX() - 64);
                        }
                        viewY += 64;
                        imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
                        distTick = 0;
                    }
                    break;
                case 3:
                    if (loading) {
                        viewX += 256;
                        imageView.setViewport(new Rectangle2D(viewX,viewY,sizeX,sizeY));
                        frame++;
                        if (frame == 4) loading = false;
                    } else {
                        auraObj.move();
                        imageView.setX(auraObj.getX());
                        imageView.setY(auraObj.getY());
                    }
                    break;
            }
            lastFrame = now;
        }
    }
}
