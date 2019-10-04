package model.shape;

public class Ellipse {

    private static double[] radians = {
        0d,
        Math.PI / 4d,
        Math.PI / 2d,
        (3d * Math.PI) / 4d,
        Math.PI,
        (5d * Math.PI) / 4d,
        (3d/2d) * Math.PI,
        (7d/4d) * Math.PI
    };

    private Point center;
    private double a;
    private double b;

    private Point[] getPoints() {
        Point[] points = new Point[8];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point((Math.cos(radians[i]) * a) + center.x,(Math.sin(radians[i]) * b) + center.y);
        }
        return points;
    }

    public Ellipse(double xc, double yc, double width, double height) {
        center = new Point(xc,yc);
        this.a = width / 2;
        this.b = height / 2;
    }

    public Point getCenter() {
        return center;
    }

    public void setXc(double x) { center.x = x; }
    public void setYc(double y) { center.y = y; }

    private boolean isInside(Point p) {
        return (
            (Math.pow(p.x - center.x,2)/Math.pow(a,2)) +
            (Math.pow(p.y - center.y,2)/Math.pow(b,2))
        ) <= 1;
    }

    public boolean intersect(Ellipse e) {
        Point[] points = e.getPoints();
        for (Point p: points) {
            if (isInside(p)) return true;
        }
        return false;
    }
}
