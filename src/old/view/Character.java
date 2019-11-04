package old.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import old.model.Controls;
import old.model.Player;
import old.model.enums.Characters;
import old.model.enums.Direction;
import old.model.enums.PlayerAction;
import old.model.enums.Turned;
import old.model.shape.Point;

public class Character extends Player implements Controls {

    private static final int viewportSize = 128;

    private ImageView imageView;
    private Image sprite;
    private long lastFrame;
    private int currCol;
    private int currRow;
    private int rowFrames;
    private int launchAttackFrame;
    private int lockedFrames;

    public Character(int number, Characters character) {
        super(number,character);
        sprite = new Image(sm.getProperty(this.currCharacter + "imgurl"));
        imageView = new ImageView(sprite);
        lastFrame = System.nanoTime();
        currCol = 0;
        currRow = 0;
        rowFrames = 0;
        launchAttackFrame = -1;
        lockedFrames = -1;
        direction();
        gs.getPlayers().getChildren().add(imageView);
    }

    public Image getSprite() {
        return sprite;
    }

    private void direction() {
        if (getPlayerAction() != PlayerAction.REST) return;
        updateState();
        String currDirection = getDirection().toString().toLowerCase() + '.';
        currRow = Integer.parseInt(sm.getProperty(currCharacter + currDirection + "row"));
        rowFrames = Integer.parseInt(sm.getProperty(currCharacter + currDirection + "frames"));
        currCol = 0;
        currRow = turned == Turned.RIGHT ? currRow : currRow + 1;
    }

    private void action() {
        setDirection(Direction.STOP);
        String currAction = getPlayerAction().toString().toLowerCase() + '.';
        currRow = Integer.parseInt(sm.getProperty(currCharacter + currAction + "row"));
        rowFrames = Integer.parseInt(sm.getProperty(currCharacter + currAction + "frames"));
        launchAttackFrame = Integer.parseInt(sm.getProperty(currCharacter + currAction + "attackframe"));
        currCol = 0;
        currRow = turned == Turned.RIGHT ? currRow : currRow + 1;
        lockedFrames = rowFrames - 1;
    }

    @Override
    public void draw() {
        imageView.setX(getX() - getWidth());
        imageView.setY(getY() - (getHeight() / 2d));

        int frameJump = (int) Math.floor((double) (System.nanoTime() - lastFrame) / (double)(1000000000L / gs.getSpriteFPS()));
        if (frameJump >= 1) {
            lastFrame = System.nanoTime();
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

    private void launchAttack() {
        switch (getPlayerAction()) {
            case PUNCH:
                if (target.isCollide(new Point(getX() + hashPunch.get("offsetx"), getY() + hashPunch.get("offsety")))) target.hitted(getDamage());
                break;
            case KICK:
                if (target.isCollide(new Point(getX() + hashKick.get("offsetx"), getY() + hashKick.get("offsety")))) target.hitted(getDamage() * 2);
                break;
            case AURA1: new AuraOne(this); break;
            case AURA2: new AuraTwo(this); break;
            case FINAL: new AuraFinal(this); break;
        }
    }

    @Override
    public void goUp(boolean val) {
        if (setTop(val)) direction();
    }

    @Override
    public void goDown(boolean val) {
        if (setBottom(val)) direction();
    }

    @Override
    public void goRight(boolean val) {
        if (setRight(val)) direction();
    }

    @Override
    public void goLeft(boolean val) {
        if (setLeft(val)) direction();
    }

    @Override
    public void punch() {
        if (setPlayerAction(PlayerAction.PUNCH)) action();
    }

    @Override
    public void kick() {
        if (setPlayerAction(PlayerAction.KICK)) action();
    }

    @Override
    public void auraOne() {
        if (setPlayerAction(PlayerAction.AURA1)) action();
    }

    @Override
    public void auraTwo() {
        if (setPlayerAction(PlayerAction.AURA2)) action();
    }

    @Override
    public void auraFinal() {
        if (setPlayerAction(PlayerAction.FINAL)) action();
    }

    @Override
    public void hitted(int damage) {
        super.hitted(damage);
        action();
    }
}
