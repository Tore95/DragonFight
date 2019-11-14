package model.dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import model.enums.Direction;

@Id("aura")
public class AuraPack {

    @Param(0)
    private double x;

    @Param(1)
    private double y;

    @Param(2)
    private Direction direction;

    public AuraPack() {}

    public AuraPack(double x, double y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return (int) x;
    }
    public int getY() {
        return (int) y;
    }
    public String getDirection() {
        return direction.toString().toLowerCase();
    }
    public Direction getRealDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setDirection(String direction) {
        String dir = direction.substring(1,direction.length() - 1);
        switch (dir) {
            case "down": this.direction = Direction.DOWN; break;
            case "up": this.direction = Direction.UP; break;
            case "left": this.direction = Direction.LEFT; break;
            case "right": this.direction = Direction.RIGHT; break;
            case "stop": this.direction = Direction.STOP; break;
        }
    }

}
