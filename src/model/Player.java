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
    protected Characters character;
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
    // AuraOne Parameters
    private int auraOneRow;
    private int auraOneFrames;
    private int auraOneAttackFrame;
    private int auraOneImgX;
    private int auraOneImgY;
    private int auraOneSize;
    private int auraOneRange;
    private int auraOneOffsetX;
    private int auraOneOffsetY;
    // AuraTwo Parameters
    private int auraTwoRow;
    private int auraTwoFrames;
    private int auraTwoAttackFrame;
    private int auraTwoImgX;
    private int auraTwoImgY;
    private int auraTwoSizeX;
    private int auraTwoSizeY;
    private int auraTwoRange;
    private int auraTwoOffsetX;
    private int auraTwoOffsetY;
    private int auraTwoTick;
    // AuraFinal Parameters
    private int auraFinalRow;
    private int auraFinalFrames;
    private int auraFinalAttackFrame;
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
            auraOneOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "aura1.parameters.offsetx"));
            auraTwoOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.parameters.offsetx"));
            auraTwoImgX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.imgxr"));
            auraFinalOffsetX = Integer.parseInt(sm.getProperty(currCharacter + "final.offsetx"));
        } else {
            auraOneOffsetX = -(Integer.parseInt(sm.getProperty(currCharacter + "aura1.parameters.offsetx")));
            auraTwoOffsetX = -(Integer.parseInt(sm.getProperty(currCharacter + "aura2.parameters.offsetx")));
            auraTwoImgX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.imgxl"));
            auraFinalOffsetX = -(Integer.parseInt(sm.getProperty(currCharacter + "final.offsetx")));
        }
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

    public Player(int number, Characters character) {
        super(number == 1 ? 100 : 1000, 500, 128, 64, 10, Direction.STOP);
        setTurned(number == 1 ? Turned.RIGHT : Turned.LEFT);
        this.damage = 2;
        this.life = 100;
        this.aura = 100;
        this.playerAction = PlayerAction.REST;
        this.character = character;
        currCharacter = character.toString().toLowerCase() + '.';
        sm = gs.getSpriteMapping();
        // set AuraOne Parameters
        auraOneRow = Integer.parseInt(sm.getProperty(currCharacter + "aura1.row"));
        auraOneFrames = Integer.parseInt(sm.getProperty(currCharacter + "aura1.frames"));
        auraOneAttackFrame = Integer.parseInt(sm.getProperty(currCharacter + "aura1.attackframe"));
        auraOneImgX = Integer.parseInt(sm.getProperty(currCharacter + "aura1.imgx"));
        auraOneImgY = Integer.parseInt(sm.getProperty(currCharacter + "aura1.imgy"));
        auraOneSize = Integer.parseInt(sm.getProperty(currCharacter + "aura1.size"));
        auraOneRange = Integer.parseInt(sm.getProperty(currCharacter + "aura1.range"));
        auraOneOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "aura1.offsety"));
        // set AuraTwo Parameters
        auraTwoRow = Integer.parseInt(sm.getProperty(currCharacter + "aura2.row"));
        auraTwoFrames = Integer.parseInt(sm.getProperty(currCharacter + "aura2.frames"));
        auraTwoAttackFrame = Integer.parseInt(sm.getProperty(currCharacter + "aura2.attackframe"));
        auraTwoImgY = Integer.parseInt(sm.getProperty(currCharacter + "aura2.imgy"));
        auraTwoSizeX = Integer.parseInt(sm.getProperty(currCharacter + "aura2.sizex"));
        auraTwoSizeY = Integer.parseInt(sm.getProperty(currCharacter + "aura2.sizey"));
        auraTwoRange = Integer.parseInt(sm.getProperty(currCharacter + "aura2.range"));
        auraTwoOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "aura2.offsety"));
        auraTwoTick = Integer.parseInt(sm.getProperty(currCharacter + "aura2.tick"));
        // set AuraFinal Parameters
        auraFinalRow = Integer.parseInt(sm.getProperty(currCharacter + "final.row"));
        auraFinalFrames = Integer.parseInt(sm.getProperty(currCharacter + "final.frames"));
        auraFinalAttackFrame = Integer.parseInt(sm.getProperty(currCharacter + "final.attackframe"));
        auraFinalImgX = Integer.parseInt(sm.getProperty(currCharacter + "final.imgx"));
        auraFinalImgY = Integer.parseInt(sm.getProperty(currCharacter + "final.imgy"));
        auraFinalSize = Integer.parseInt(sm.getProperty(currCharacter + "final.size"));
        auraFinalRange = Integer.parseInt(sm.getProperty(currCharacter + "final.range"));
        auraFinalOffsetY = Integer.parseInt(sm.getProperty(currCharacter + "final.offsety"));
        auraFinalDirection = sm.getProperty(currCharacter + "final.dir");
    }

    // getter
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
    public int getAuraOneRow() {
        return auraOneRow;
    }
    public int getAuraOneFrames() {
        return auraOneFrames;
    }
    public int getAuraOneAttackFrame() {
        return auraOneAttackFrame;
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
    public int getAuraTwoRow() {
        return auraTwoRow;
    }
    public int getAuraTwoFrames() {
        return auraTwoFrames;
    }
    public int getAuraTwoAttackFrame() {
        return auraTwoAttackFrame;
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
    public int getAuraFinalRow() {
        return auraFinalRow;
    }
    public int getAuraFinalFrames() {
        return auraFinalFrames;
    }
    public int getAuraFinalAttackFrame() {
        return auraFinalAttackFrame;
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
        if (this.playerAction == playerAction) return false;
        else this.playerAction = playerAction;
        return true;
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
