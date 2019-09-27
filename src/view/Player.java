package view;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Character;
import model.enums.PlayerAction;
import model.enums.Turned;

public class Player extends Character {

    private SpriteEngine spriteEngine;
    private ImageView playerSprite;
    private Group auraAttacks;

    public Player(int number, Group playerGroup, Group auraAttacks, String spriteUrl) {
        super(number);
        playerSprite = new ImageView(spriteUrl);
        spriteEngine = new SpriteEngine(playerSprite,this);
        playerGroup.getChildren().add(number - 1, playerSprite);
        this.auraAttacks = auraAttacks;
        spriteEngine.start();
    }

    public Image getSpriteImage() { return playerSprite.getImage(); }

    public SpriteEngine getSpriteEngine() {
        return spriteEngine;
    }

    @Override
    public void punch() {
        setPlayerAction(PlayerAction.PUNCH);
        spriteEngine.action();
    }

    @Override
    public void kick() {
        setPlayerAction(PlayerAction.KICK);
        spriteEngine.action();
    }

    @Override
    public void auraOne() {
        setPlayerAction(PlayerAction.AURA1);
        spriteEngine.action();
    }

    @Override
    public void auraTwo() {
        setPlayerAction(PlayerAction.AURA2);
        spriteEngine.action();
    }

    @Override
    public void auraThree() {
        setPlayerAction(PlayerAction.FIANL);
        spriteEngine.action();
    }

    @Override
    public void hittingMe(int damage) {
        if (damage <= 3) setPlayerAction(PlayerAction.SOFT_HITTED);
        else setPlayerAction(PlayerAction.HARD_HITTED);
        spriteEngine.action();
        subLife(damage);
    }

    @Override
    public void launchAttack() {
        switch (getPlayerAction()) {
            case PUNCH: if (isCollide(target)) target.hittingMe(damage); break;
            case KICK: if (isCollide(target)) target.hittingMe(damage * 2); break;
            case AURA1:
                new AuraAttack(this,1).start();
                break;
            case AURA2:
                new AuraAttack(this,2).start();
                break;
            case FIANL:
                new AuraAttack(this,3).start();
                break;
        }
    }

    public Group getAuraAttacks() {
        return auraAttacks;
    }
}
