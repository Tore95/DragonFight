package model;

import model.enums.Direction;

public class MoovableGO extends GameObject {

    private double velocity;
    private Direction direction;

    public MoovableGO(double x, double y, int height, int width, double velocity, Direction direction) {
        super(x, y, height, width);
        this.velocity = velocity;
        this.direction = direction;
    }

    public MoovableGO(double x, double y, int height, int width) {
        this(x,y,height,width,0d,Direction.STOP);
    }

    public void move() {
        // double obl = Math.sqrt(Math.pow(velocity,2) + Math.pow(velocity,2));
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

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean setDirection(Direction direction) {
        if (this.direction == direction) return false;
        this.direction = direction;
        return true;
    }
}
