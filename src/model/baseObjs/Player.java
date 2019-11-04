package model.baseObjs;

import javafx.scene.image.Image;
import model.enums.Characters;
import model.enums.Direction;
import model.enums.PlayerAction;
import model.enums.Turned;

import java.util.HashMap;
import java.util.Properties;

public abstract class Player extends GameObject {

    // movement utilities
    private boolean right = false;
    private boolean left = false;
    private boolean top = false;
    private boolean bottom = false;

    // stats
    private int damage;
    private int life;
    private int aura;
    private PlayerAction playerAction;
    protected Player target;
    protected Turned turned;
    protected String currCharacter;
    protected Properties spriteMapping;

    // Properties HashMaps
    protected HashMap<String,Integer> hashPunch;
    protected HashMap<String,Integer> hashKick;
    protected HashMap<String,Integer> hashAuraOne;
    protected HashMap<String,Integer> hashAuraTwo;
    protected HashMap<String,Integer> hashAuraFinal;

    private void setTurned(Turned turned) {
        this.turned = turned;
        if (turned == Turned.RIGHT) {
            hashPunch.put("offsetx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "punch.offsetxr")));
            hashKick.put("offsetx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "kick.offsetxr")));
            hashAuraOne.put("offsetx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura1.offsetx")));
            hashAuraTwo.put("offsetx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.offsetx")));
            hashAuraTwo.put("imgx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.imgxr")));
            hashAuraFinal.put("offsetx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.offsetx")));
        } else {
            hashPunch.put("offsetx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "punch.offsetxl")));
            hashKick.put("offsetx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "kick.offsetxl")));
            hashAuraOne.put("offsetx",-(Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura1.offsetx"))));
            hashAuraTwo.put("offsetx",-(Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.offsetx"))));
            hashAuraTwo.put("imgx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.imgxl")));
            hashAuraFinal.put("offsetx",-(Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.offsetx"))));
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

    public Player(int number, Characters character) {
        super(number == 1 ? 164 : 1064, 564, 128, 64, 10, Direction.STOP);
        this.damage = 2;
        this.life = 100;
        this.aura = 100;
        this.playerAction = PlayerAction.REST;
        currCharacter = character.toString().toLowerCase() + '.';
        spriteMapping = globalManager.getSpriteMapping();
        hashPunch = new HashMap<>();
        hashKick = new HashMap<>();
        hashAuraOne = new HashMap<>();
        hashAuraTwo = new HashMap<>();
        hashAuraFinal = new HashMap<>();
        setTurned(number == 1 ? Turned.RIGHT : Turned.LEFT);
        // set Punch Parameters
        hashPunch.put("offsety",Integer.parseInt(spriteMapping.getProperty(currCharacter + "punch.offsety")));
        // set Kick Parameters
        hashKick.put("offsety",Integer.parseInt(spriteMapping.getProperty(currCharacter + "kick.offsety")));
        // set AuraOne Parameters
        hashAuraOne.put("imgx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura1.imgx")));
        hashAuraOne.put("imgy",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura1.imgy")));
        hashAuraOne.put("size",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura1.size")));
        hashAuraOne.put("range",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura1.range")));
        hashAuraOne.put("offsety",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura1.offsety")));
        // set AuraTwo Parameters
        hashAuraTwo.put("imgy",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.imgy")));
        hashAuraTwo.put("sizex",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.sizex")));
        hashAuraTwo.put("sizey",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.sizey")));
        hashAuraTwo.put("range",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.range")));
        hashAuraTwo.put("offsety",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.offsety")));
        hashAuraTwo.put("tick",Integer.parseInt(spriteMapping.getProperty(currCharacter + "aura2.tick")));
        // set AuraFinal Parameters
        hashAuraFinal.put("imgx",Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.imgx")));
        hashAuraFinal.put("imgy",Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.imgy")));
        hashAuraFinal.put("size",Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.size")));
        hashAuraFinal.put("range",Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.range")));
        hashAuraFinal.put("offsety",Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.offsety")));
        hashAuraFinal.put("dir",Integer.parseInt(spriteMapping.getProperty(currCharacter + "final.dir")));
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
    public HashMap<String, Integer> getHashAuraOne() {
        return hashAuraOne;
    }
    public HashMap<String, Integer> getHashAuraTwo() {
        return hashAuraTwo;
    }
    public HashMap<String, Integer> getHashAuraFinal() {
        return hashAuraFinal;
    }
    public Image getSprite() {
        //TODO
        return new Image("");
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
    public void forceSetPlayerAction(PlayerAction playerAction) {
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

    // stats modifiers
    public void hitted(int damage) {
        if (damage <= 3) playerAction = PlayerAction.SOFT_HITTED;
        else playerAction = PlayerAction.HARD_HITTED;
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

    // info
    public boolean isDead() {
        return life <= 0;
    }

}
