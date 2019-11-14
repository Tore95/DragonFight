package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import model.enums.Characters;
import model.enums.Direction;
import model.enums.PlayerAction;
import model.enums.Turned;

import java.util.HashMap;
import java.util.Properties;

public abstract class Player extends GameObject {

    // sprite
    private static final int viewportSize = 128;
    private Image sprite;
    private int currCol;
    private int currRow;
    private int rowFrames;
    private int launchAttackFrame;
    private int lockedFrames;

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
    private Player target;
    private Turned turned;
    private String currCharacter;
    private Properties spriteMapping;

    // Properties HashMaps
    private HashMap<String,Integer> hashAuraOne;
    private HashMap<String,Integer> hashAuraTwo;
    private HashMap<String,Integer> hashAuraFinal;
    protected HashMap<String,Integer> hashPunch;
    protected HashMap<String,Integer> hashKick;

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
    private void updateState() {
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
    private boolean setRight(boolean right) {
        if (this.right == right) return false;
        this.right = right;
        return true;
    }
    private boolean setLeft(boolean left) {
        if (this.left == left) return false;
        this.left = left;
        return true;
    }
    private boolean setTop(boolean top) {
        if (this.top == top) return false;
        this.top = top;
        return true;
    }
    private boolean setBottom(boolean bottom) {
        if (this.bottom == bottom) return false;
        this.bottom = bottom;
        return true;
    }

    protected void goUp(boolean val) {
        if (setTop(val)) direction();
    }
    protected void goDown(boolean val) {
        if (setBottom(val)) direction();
    }
    protected void goRight(boolean val) {
        if (setRight(val)) direction();
    }
    protected void goLeft(boolean val) {
        if (setLeft(val)) direction();
    }

    protected void punch() {
        if (setPlayerAction(PlayerAction.PUNCH)) action();
    }
    protected void kick() {
        if (setPlayerAction(PlayerAction.KICK)) action();
    }
    protected void auraOne() {
        if (setPlayerAction(PlayerAction.AURA1)) {
            subAura(10);
            action();
        }
    }
    protected void auraTwo() {
        if (setPlayerAction(PlayerAction.AURA2)) {
            subAura(30);
            action();
        }
    }
    protected void auraFinal() {
        if (setPlayerAction(PlayerAction.FINAL)) {
            subAura(50);
            action();
        }
    }

    protected abstract void launchAttack();
    public abstract void pressedKeyEvent(KeyEvent ev);
    public abstract void releasedKeyEvent(KeyEvent ev);

    protected void direction() {
        if (getPlayerAction() != PlayerAction.REST) return;
        updateState();
        String currDirection = getDirection().toString().toLowerCase() + '.';
        currRow = Integer.parseInt(spriteMapping.getProperty(currCharacter + currDirection + "row"));
        rowFrames = Integer.parseInt(spriteMapping.getProperty(currCharacter + currDirection + "frames"));
        currCol = 0;
        currRow = turned == Turned.RIGHT ? currRow : currRow + 1;
    }

    protected void action() {
        setDirection(Direction.STOP);
        String currAction = getPlayerAction().toString().toLowerCase() + '.';
        currRow = Integer.parseInt(spriteMapping.getProperty(currCharacter + currAction + "row"));
        rowFrames = Integer.parseInt(spriteMapping.getProperty(currCharacter + currAction + "frames"));
        launchAttackFrame = Integer.parseInt(spriteMapping.getProperty(currCharacter + currAction + "attackframe"));
        currCol = 0;
        currRow = turned == Turned.RIGHT ? currRow : currRow + 1;
        lockedFrames = rowFrames - 1;
    }

    public Player(double positionX, Turned t, Characters character) {
        super(positionX, 564d, 128, 64, 10d, Direction.STOP);
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
        setTurned(t);
        // set Punch Parameters
        hashPunch.put("offsety",Integer.parseInt(spriteMapping.getProperty(currCharacter + "punch.offsety")));
        // set Kick Parameters
        hashKick.put("offsety",Integer.parseInt(spriteMapping.getProperty(currCharacter + "kick.offsety")));
        // set Atom Parameters
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
        // set sprite
        sprite = new Image(spriteMapping.getProperty(currCharacter + "imgurl"));
        imageView = new ImageView(sprite);
        currCol = 0;
        currRow = 0;
        rowFrames = 0;
        launchAttackFrame = -1;
        lockedFrames = -1;
        direction();
        globalManager.getPlayers().getChildren().add(imageView);

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
        return sprite;
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
        action();
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

    // sprite
    @Override
    public void move() {
        altMove();
        imageView.setX(getX() - getWidth());
        imageView.setY(getY() - (getHeight() / 2d));
    }

    @Override
    public void draw() {
        imageView.setViewport(new Rectangle2D(currCol * viewportSize, currRow * viewportSize, viewportSize, viewportSize));
        if (lockedFrames < 0) {
            currCol = (currCol + 1) % rowFrames;
        } else if (lockedFrames > 0) {
            if (currCol == launchAttackFrame) launchAttack();
            currCol += 1;
            lockedFrames -= 1;
        } else {
            lockedFrames -= 1;
            forceSetPlayerAction(PlayerAction.REST);
            direction();
        }
    }
}
