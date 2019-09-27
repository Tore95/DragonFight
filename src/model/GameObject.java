package model;

public class GameObject {

    private double x;
    private double y;
    private int height;
    private int width;
    private boolean visible;
    private boolean inBackground;

    public GameObject(double x, double y, int height, int width, boolean visible, boolean inBackground) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.visible = visible;
        this.inBackground = inBackground;
    }

    public GameObject(double x, double y, int height, int width) {
        this(x,y,height,width,true,false);
    }

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }

    protected void addX(double x) {
        this.x += x;
    }

    protected void addY(double y) {
        this.y += y;
    }

    protected void subX(double x) {
        this.x -= x;
    }

    protected void subY(double y) {
        this.y -= y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

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
        double right = (getX() + getWidth());
        double left = getX();
        double top = getY();
        double bottom = (getY() + getHeight());

        double oRight = (o.getX() + o.getWidth());
        double oLeft = o.getX();
        double oTop = o.getY();
        double oBottom = (o.getY() + o.getHeight());

        return (right >= oLeft && right <= oRight || left <= oRight && left >= oLeft) && (top >= oTop && top <= oBottom || bottom <= oBottom && bottom >= oTop);
    }

    public boolean isCollide(GameObject o, double intersectRatio) {

        intersectRatio *= 0.5;

        double ratioX = getWidth() * intersectRatio;
        double ratioY = getHeight() * intersectRatio;

        double oRatioX = o.getWidth() * intersectRatio;
        double oRatioY = o.getHeight() * intersectRatio;

        double right = (getX() + getWidth()) - ratioX;
        double left = getX() + ratioX;
        double top = getY() + ratioY;
        double bottom = (getY() + getHeight()) - ratioY;

        double oRight = (o.getX() + o.getWidth()) - oRatioX;
        double oLeft = o.getX() + oRatioX;
        double oTop = o.getY() + oRatioY;
        double oBottom = (o.getY() + o.getHeight()) - oRatioY;

        return (right >= oLeft && right <= oRight || left <= oRight && left >= oLeft) && (top >= oTop && top <= oBottom || bottom <= oBottom && bottom >= oTop);
    }
}
