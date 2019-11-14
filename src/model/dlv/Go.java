package model.dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import model.enums.Direction;

@Id("go")
public class Go {

    @Param(0)
    private Direction direction;

    @Param(1)
    private int distance;

    public Go() {}

    public String getDirection() {
        return direction.toString().toLowerCase();
    }
    public Direction getRealDirection() {
        return direction;
    }
    public int getDistance() {
        return distance;
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
    public void setDistance(int distance) {
        this.distance = distance;
    }
}
