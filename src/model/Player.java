package model;

import model.enums.Direction;
import model.enums.PlayerAction;
import model.enums.Turned;

public abstract class Player extends GameObject {

    private Player target;
    private Turned turned;
    private PlayerAction playerAction;

    private boolean right = false;
    private boolean left = false;
    private boolean top = false;
    private boolean bottom = false;

    private int damage;
    private int life;
    private int aura;

    public Player(int number) {
        super(number == 1 ? 100 : 1000, 500, 128, 64, 10, Direction.STOP);
        this.damage = 2;
        this.life = 100;
        this.aura = 100;
        this.turned = number == 1 ? Turned.RIGHT : Turned.LEFT;
        this.playerAction = PlayerAction.REST;
    }

    public Player getTarget() {
        return target;
    }
    public void setTarget(Player target) {
        this.target = target;
    }

    public Turned getTurned() {
        return turned;
    }
    public void setTurned(Turned turned) {
        this.turned = turned;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }
    public boolean setPlayerAction(PlayerAction playerAction) {
        if (this.playerAction == playerAction) return false;
        else this.playerAction = playerAction;
        return true;
    }

    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }

    public int getAura() {
        return aura;
    }
    public void setAura(int aura) {
        this.aura = aura;
    }



    public void subLife(int damage) {
        this.life -= damage;
    }
    public void addLife(int care) {
        this.life += care;
    }

    public void subAura(int val) {
        this.aura -= val;
    }
    public void addAura(int val) {
        this.aura += val;
    }

    public boolean isDead() {
        return life <= 0;
    }

    public void hitted(int damage) {
        if (damage <= 3) playerAction = PlayerAction.SOFT_HITTED;
        else playerAction = PlayerAction.HARD_HITTED;
        subLife(damage);
    }

    private boolean updateState() {
        try {
            if (right && top) return setDirection(Direction.UP_RIGHT);
            if (right && bottom) return setDirection(Direction.DOWN_RIGHT);
            if (left && top) return setDirection(Direction.UP_LEFT);
            if (left && bottom) return setDirection(Direction.DOWN_LEFT);
            if (right) return setDirection(Direction.RIGHT);
            if (left) return setDirection(Direction.LEFT);
            if (top) return setDirection(Direction.UP);
            if (bottom) return setDirection(Direction.DOWN);
            return setDirection(Direction.STOP);
        } finally {
            switch (getDirection()) {
                case DOWN_LEFT:
                case LEFT:
                case UP_LEFT: setTurned(Turned.LEFT); break;
                case DOWN_RIGHT:
                case RIGHT:
                case UP_RIGHT: setTurned(Turned.RIGHT); break;
            }
        }
    }

    public boolean setRight(boolean right) {
        if (this.right == right) return false;
        this.right = right;
        return updateState();
    }
    public boolean setLeft(boolean left) {
        if (this.left == left) return false;
        this.left = left;
        return updateState();
    }
    public boolean setTop(boolean top) {
        if (this.top == top) return false;
        this.top = top;
        return updateState();
    }
    public boolean setBottom(boolean bottom) {
        if (this.bottom == bottom) return false;
        this.bottom = bottom;
        return updateState();
    }

    @Override
    public void update() {
        super.update();
        updateState();
        if (playerAction != PlayerAction.REST) setVelocity(0);
        else if (getVelocity() == 0) setVelocity(10);
    }
}
