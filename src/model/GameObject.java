package model;

import javafx.scene.image.ImageView;
import view.GlobalManager;
import model.enums.Direction;

public abstract class GameObject {

    private Point position;
    private Ellipse hitbox;
    private Direction direction;
    private int height;
    private int width;
    private double velocity;

    private void addX(double x) {
        position.x += x;
        hitbox.setXc(position.x);
    }
    private void addY(double y) {
        position.y += y;
        hitbox.setYc(position.y);
    }
    private void subX(double x) {
        position.x -= x;
        hitbox.setXc(position.x);
    }
    private void subY(double y) {
        position.y -= y;
        hitbox.setYc(position.y);
    }

    protected GlobalManager globalManager;
    protected ImageView imageView;

    public abstract void draw();

    public GameObject(double x, double y, int height, int width, double velocity, Direction direction) {
        globalManager = GlobalManager.getInstance();
        position = new Point(x,y);
        hitbox = new Ellipse(x,y,width,height);
        this.height = height;
        this.width = width;
        this.velocity = velocity;
        this.direction = direction;
        globalManager.getToAddObjects().add(this);
    }

    public double getX() {
        return position.x;
    }

    public double getY() {
        return position.y;
    }

    public Point getPosition() { return position; }

    public Ellipse getHitbox() { return hitbox; }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double getVelocity() {
        return velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case UP:
                subY(velocity);
                break;
            case UP_RIGHT:
                subY(velocity);
                addX(velocity);
                break;
            case UP_LEFT:
                subY(velocity);
                subX(velocity);
                break;
            case RIGHT:
                addX(velocity);
                break;
            case LEFT:
                subX(velocity);
                break;
            case DOWN_RIGHT:
                addY(velocity);
                addX(velocity);
                break;
            case DOWN_LEFT:
                addY(velocity);
                subX(velocity);
                break;
            case DOWN:
                addY(velocity);
        }
    }

    public boolean isCollide(GameObject o) {
        return hitbox.intersect(o.getHitbox());
    }

    public boolean isCollide(Point p) {
        return hitbox.isInside(p);
    }

}
