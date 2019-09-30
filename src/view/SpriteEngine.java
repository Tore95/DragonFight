package view;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.Character;
import model.enums.PlayerAction;
import model.enums.Turned;

public class SpriteEngine extends AnimationTimer {

    private ImageView playerImage;
    private Character character;

    private final long FPS = 16;
    private long lastFrame;

    private int frameSize = 128;
    private int currCol = 0;
    private int currRow = 0;
    private int rowFrames = 0;
    private int launchAttackFrame = -1;
    private int lockedFrames = -1;

    public SpriteEngine(ImageView playerImage, Character character) {
        this.playerImage = playerImage;
        this.character = character;
        this.lastFrame = System.nanoTime();
        direction();
    }

    public void direction() {
        if (character.getPlayerAction() != PlayerAction.REST) return;
        switch (character.getDirection()) {
            case DOWN_RIGHT:
            case UP_RIGHT:
            case RIGHT:
            case DOWN_LEFT:
            case UP_LEFT:
            case LEFT: currRow = 2; rowFrames = 4; break;
            case UP: currRow = 4; rowFrames = 1; break;
            case DOWN: currRow = 6; rowFrames = 1; break;
            case STOP: currRow = 0; rowFrames = 4; break;
        }
        currCol = 0;
        currRow = character.getTurned() == Turned.RIGHT ? currRow : currRow + 1;
    }

    public void action() {
        if (lockedFrames != -1) return;
        switch (character.getPlayerAction()) {
            case PUNCH:
                currRow = 8;
                rowFrames = 6;
                launchAttackFrame = 4;
                break;
            case KICK:
                currRow = 10;
                rowFrames = 6;
                launchAttackFrame = 4;
                break;
            case AURA1:
                currRow = 12;
                rowFrames = 5;
                launchAttackFrame = 3;
                break;
            case AURA2:
                currRow = 14;
                rowFrames = 19;
                launchAttackFrame = 6;
                break;
            case FINAL:
                currRow = 16;
                rowFrames = 19;
                launchAttackFrame = 4;
                break;
            case SOFT_HITTED:
                currRow = 18;
                rowFrames = 6;
                launchAttackFrame = -1;
                break;
            case HARD_HITTED:
                currRow = 20;
                rowFrames = 9;
                launchAttackFrame = -1;
                break;
        }
        currCol = 0;
        currRow = character.getTurned() == Turned.RIGHT ? currRow : currRow + 1;
        lockedFrames = rowFrames - 1;
    }

    @Override
    public void handle(long now) {
        int frameJump = (int) Math.floor((now - lastFrame) / (1000000000L / FPS));
        if (frameJump >= 1) {
            lastFrame = now;
            playerImage.setViewport(new Rectangle2D(currCol * frameSize, currRow * frameSize, frameSize, frameSize));
            if (lockedFrames < 0) {
                currCol = (currCol + 1) % rowFrames;
                character.move();
                playerImage.setX(character.getX());
                playerImage.setY(character.getY());
            } else if (lockedFrames > 0) {
                if (currCol == launchAttackFrame) character.launchAttack();
                currCol += 1;
                lockedFrames -= 1;
            } else {
                lockedFrames -= 1;
                character.setPlayerAction(PlayerAction.REST);
                direction();
            }
        }
    }
}
