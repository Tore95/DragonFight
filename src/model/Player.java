package model;

import model.enums.Characters;
import model.enums.Direction;
import model.enums.PlayerAction;
import model.enums.Turned;

import java.util.Properties;

public abstract class Player extends GameObject {
    // members
    protected Player target;
    protected Turned turned;
    protected String currCharacter;
    protected Properties sm;
    private PlayerAction playerAction;
    // movement utilities
    private boolean right = false;
    private boolean left = false;
    private boolean top = false;
    private boolean bottom = false;
    // stats
    private int damage;
    private int life;
    private int aura;
    // Punch Parameters
    private int punchOffsetX;
    private int punchOffsetY;
    // Kick Parameters
    private int kickOffsetX;
    private int kickOffsetY;
    // AuraOne Parameters
    private int auraOneImgX;
    private int auraOneImgY;
    private int auraOneSize;
    private int auraOneRange;
    private int auraOneOffsetX;
    private int auraOneOffsetY;
    // AuraTwo Parameters
    private int auraTwoImgX;
    private int auraTwoImgY;
    private int auraTwoSizeX;
    private int auraTwoSizeY;
    private int auraTwoRange;
    private int auraTwoOffsetX;
    private int auraTwoOffsetY;
    private int auraTwoTick;
    // AuraFinal Parameters
    private int auraFinalImgX;
    private int auraFinalImgY;
    private int auraFinalSize;
    private int auraFinalRange;
    private int auraFinalOffsetX;
    private int auraFinalOffsetY;
    private String auraFinalDirection;

    private void setTurned(Turned turned) {
        this.turned = turned;
        if (turned == Turned.RIGHT) {
            punchOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "punch.offsetxr"));
            kickOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "kick.offsetxr"));
            auraOneOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "aura1.offsetx"));
            auraTwoOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.offsetx"));
            auraTwoImgX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.imgxr"));
            auraFinalOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "final.offsetx"));
        } else {
            punchOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "punch.offsetxl"));
            kickOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "kick.offsetxl"));
            auraOneOffsetX = -(Integer.parseInt(sm.getProperty(currCharacter + "aura1.offsetx")));
            auraTwoOffsetX = -(Integer.parseInt(sm.getProperty(currCharacter + "aura2.offsetx")));
            auraTwoImgX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.imgxl"));
            auraFinalOffsetX = -(Integer.parseInt(sm.getProperty(currCharacter + "final.offsetx")));
        }
    }
    protected void updateState() {
        if (right && top) setDirection(Direction.UP_RIGHT);
        else if (right && bottom) setDirection(Direction.DOWN_RIGHT);
        else if (left && top) setDirection(Direction.UP_LEFT);
        else if (left && bottom) setDirection(Direction.DOWN_LEFT);
        else if (right) setDirection(Direction.RIGHT);
        else if (left) setDirection(Direction.LEFT);
        else if (top) setDirection(Direction.UP);
        else if (bottom) setDirection(Direction.DOWN);
        else setDirection(Direction.STOP);
        switch (getDirection()) {
            case DOWN_LEFT:
            case LEFT:
            case UP_LEFT: setTurned(Turned.LEFT); break;
            case DOWN_RIGHT:
            case RIGHT:
            case UP_RIGHT: setTurned(Turned.RIGHT); break;
        }
    }

    public Player(int number, Characters character) {
        super(number == 1 ? 164 : 1064, 564, 128, 64, 10, Direction.STOP);
        this.damage = 2;
        this.life = 100;
        this.aura = 100;
        this.playerAction = PlayerAction.REST;
        currCharacter = character.toString().toLowerCase() + '.';
        sm = gs.getSpriteMapping();
        setTurned(number == 1 ? Turned.RIGHT : Turned.LEFT);
        // set Punch Parameters
        punchOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "punch.offsety"));
        // set Kick Parameters
        kickOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "kick.offsety"));
        // set AuraOne Parameters
        auraOneImgX = Integer.parseInt(sm.getProperty(currCharacter + "aura1.imgx"));
        auraOneImgY = Integer.parseInt(sm.getProperty(currCharacter + "aura1.imgy"));
        auraOneSize = Integer.parseInt(sm.getProperty(currCharacter + "aura1.size"));
        auraOneRange = Integer.parseInt(sm.getProperty(currCharacter + "aura1.range"));
        auraOneOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "aura1.offsety"));
        // set AuraTwo Parameters
        auraTwoImgY = Integer.parseInt(sm.getProperty(currCharacter + "aura2.imgy"));
        auraTwoSizeX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.sizex"));
        auraTwoSizeY = Integer.parseInt(sm.getProperty(currCharacter + "aura2.sizey"));
        auraTwoRange = Integer.parseInt(sm.getProperty(currCharacter + "aura2.range"));
        auraTwoOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "aura2.offsety"));
        auraTwoTick = Integer.parseInt(sm.getProperty(currCharacter + "aura2.tick"));
        // set AuraFinal Parameters
        auraFinalImgX = Integer.parseInt(sm.getProperty(currCharacter + "final.imgx"));
        auraFinalImgY = Integer.parseInt(sm.getProperty(currCharacter + "final.imgy"));
        auraFinalSize = Integer.parseInt(sm.getProperty(currCharacter + "final.size"));
        auraFinalRange = Integer.parseInt(sm.getProperty(currCharacter + "final.range"));
        auraFinalOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "final.offsety"));
        auraFinalDirection = sm.getProperty(currCharacter + "final.dir");
    }

    // getter

    public int getPunchOffsetX() {
        return punchOffsetX;
    }

    public int getPunchOffsetY() {
        return punchOffsetY;
    }

    public int getKickOffsetX() {
        return kickOffsetX;
    }

    public int getKickOffsetY() {
        return kickOffsetY;
    }

    public Player getTarget() {
        return target;
    }
    public Turned getTurned() {
        return turned;
    }
    public PlayerAction getPlayerAction() {
        return playerAction;
    }
    public String getCurrCharacter() {
        return currCharacter;
    }
    public int getDamage() {
        return damage;
    }
    public int getLife() {
        return life;
    }
    public int getAura() {
        return aura;
    }
    public int getAuraOneImgX() {
        return auraOneImgX;
    }
    public int getAuraOneImgY() {
        return auraOneImgY;
    }
    public int getAuraOneSize() {
        return auraOneSize;
    }
    public int getAuraOneRange() {
        return auraOneRange;
    }
    public int getAuraOneOffsetX() {
        return auraOneOffsetX;
    }
    public int getAuraOneOffsetY() {
        return auraOneOffsetY;
    }
    public int getAuraTwoImgX() {
        return auraTwoImgX;
    }
    public int getAuraTwoImgY() {
        return auraTwoImgY;
    }
    public int getAuraTwoSizeX() {
        return auraTwoSizeX;
    }
    public int getAuraTwoSizeY() {
        return auraTwoSizeY;
    }
    public int getAuraTwoRange() {
        return auraTwoRange;
    }
    public int getAuraTwoOffsetX() {
        return auraTwoOffsetX;
    }
    public int getAuraTwoOffsetY() {
        return auraTwoOffsetY;
    }
    public int getAuraTwoTick() {
        return auraTwoTick;
    }
    public int getAuraFinalImgX() {
        return auraFinalImgX;
    }
    public int getAuraFinalImgY() {
        return auraFinalImgY;
    }
    public int getAuraFinalSize() {
        return auraFinalSize;
    }
    public int getAuraFinalRange() {
        return auraFinalRange;
    }
    public int getAuraFinalOffsetX() {
        return auraFinalOffsetX;
    }
    public int getAuraFinalOffsetY() {
        return auraFinalOffsetY;
    }
    public String getAuraFinalDirection() {
        return auraFinalDirection;
    }
    // setter
    public void setTarget(Player target) {
        this.target = target;
    }
    public boolean setPlayerAction(PlayerAction playerAction) {
        if (this.playerAction == playerAction || this.playerAction != PlayerAction.REST) return false;
        this.playerAction = playerAction;
        return true;
    }
    public void forcedSetPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setLife(int life) {
        this.life = life;
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

    protected boolean setRight(boolean right) {
        if (this.right == right) return false;
        this.right = right;
        return true;
    }
    protected boolean setLeft(boolean left) {
        if (this.left == left) return false;
        this.left = left;
        return true;
    }
    protected boolean setTop(boolean top) {
        if (this.top == top) return false;
        this.top = top;
        return true;
    }
    protected boolean setBottom(boolean bottom) {
        if (this.bottom == bottom) return false;
        this.bottom = bottom;
        return true;
    }
}
