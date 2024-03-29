package model.dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import model.enums.Direction;
import model.enums.PlayerAction;

@Id("opponent")
public class PlayerOnePack {

    @Param(0)
    private double x;

    @Param(1)
    private double y;

    @Param(2)
    private int life;

    @Param(3)
    private int aura;

    @Param(4)
    private Direction direction;

    @Param(5)
    private PlayerAction action;

    public PlayerOnePack() {}

    public PlayerOnePack(double x, double y, int life, int aura, Direction direction, PlayerAction action) {
        this.x = x;
        this.y = y;
        this.life = life;
        this.aura = aura;
        this.direction = direction;
        this.action = action;
    }

    public int getX() {
        return (int) x;
    }
    public int getY() {
        return (int) y;
    }
    public int getLife() {
        return life;
    }
    public int getAura() {
        return aura;
    }
    public String getAction() {
        return action.toString().toLowerCase();
    }
    public String getDirection() {
        return direction.toString().toLowerCase();
    }
    public PlayerAction getRealAction() {
        return action;
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
    public void setLife(int life) {
        this.life = life;
    }
    public void setAura(int aura) {
        this.aura = aura;
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
    public void setAction(String action) {
        String act = action.substring(1,action.length() - 1);
        switch (act) {
            case "punch": this.action = PlayerAction.PUNCH; break;
            case "kick": this.action = PlayerAction.KICK; break;
            case "aura1": this.action = PlayerAction.AURA1; break;
            case "aura2": this.action = PlayerAction.AURA2; break;
            case "final": this.action = PlayerAction.FINAL; break;
            case "soft_hitted": this.action = PlayerAction.SOFT_HITTED; break;
            case "hard_hitted": this.action = PlayerAction.HARD_HITTED; break;
            case "rest": this.action = PlayerAction.REST; break;
        }
    }

}
