package model;

import model.shape.Ellipse;
import model.shape.Point;

public class GameObject {

    private Point position;
    private Ellipse hitbox;
    private int height;
    private int width;
    private boolean visible;
    private boolean inBackground;

    public GameObject(double x, double y, int height, int width, boolean visible, boolean inBackground) {
        position = new Point(x,y);
        hitbox = new Ellipse(x,y,width,height);
        this.height = height;
        this.width = width;
        this.visible = visible;
        this.inBackground = inBackground;
    }

    public GameObject(double x, double y, int height, int width) {
        this(x,y,height,width,true,false);
    }

    protected void addX(double x) {
        position.x += x;
        hitbox.setXc(position.x);
    }

    protected void addY(double y) {
        position.y += y;
        hitbox.setYc(position.y);
    }

    protected void subX(double x) {
        position.x -= x;
        hitbox.setXc(position.x);
    }

    protected void subY(double y) {
        position.y -= y;
        hitbox.setYc(position.y);
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isInBackground() {
        return inBackground;
    }

    public void setInBackground(boolean inBackground) {
        this.inBackground = inBackground;
    }

    public boolean isCollide(GameObject o) {
        return hitbox.intersect(o.getHitbox());
    }
}
