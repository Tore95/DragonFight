package model;

import model.enums.Direction;
import model.enums.PlayerAction;
import model.enums.Turned;

public abstract class Character extends GameObject {

    protected int damage;
    protected int life;
    protected int aura;
    protected Character target;

    private boolean right = false;
    private boolean left = false;
    private boolean top = false;
    private boolean bottom = false;

    private Turned turned;
    private PlayerAction playerAction;

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
                case UP_LEFT: turned = Turned.LEFT; break;
                case DOWN_RIGHT:
                case RIGHT:
                case UP_RIGHT: turned = Turned.RIGHT; break;
            }
        }
    }

    public Character(int number) {
        super(number == 1 ? 100 : 1000, 500, 128, 128, 20, Direction.STOP);
        this.damage = 2;
        this.life = 100;
        this.aura = 100;
        this.turned = number == 1 ? Turned.RIGHT : Turned.LEFT;
        this.playerAction = PlayerAction.REST;
    }

    public Character getTarget() {
        return target;
    }

    public void setTarget(Character target) {
        this.target = target;
    }

    public boolean goRight(boolean set) {
        if (this.right == set) return false;
        this.right = set;
        return updateState();
    }

    public boolean goLeft(boolean set) {
        if (this.left == set) return false;
        this.left = set;
        return updateState();
    }

    public boolean goUp(boolean set) {
        if (this.top == set) return false;
        this.top = set;
        return updateState();
    }

    public boolean goDown(boolean set) {
        if (this.bottom == set) return false;
        this.bottom = set;
        return updateState();
    }

    public abstract void punch();

    public abstract void kick();

    public abstract void auraOne();

    public abstract void auraTwo();

    public abstract void auraThree();

    public abstract void hittingMe(int damage);

    public void subLife(int damage) {
        this.life -= damage;
    }

    public void addLife(int care) {
        this.life += care;
    }

    public boolean isDead() {
        return life <= 0;
    }

    public Turned getTurned() {
        return turned;
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

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public boolean setPlayerAction(PlayerAction playerAction) {
        if (this.playerAction == playerAction) return false;
        this.playerAction = playerAction;
        return true;
    }

    public abstract void launchAttack();
}
