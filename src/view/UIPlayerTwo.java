package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GameObject;
import model.Player;
import model.enums.Direction;

public class UIPlayerTwo extends GameObject {

    private Player owner;
    private Rectangle borderL;
    private Rectangle life;
    private Rectangle borderA;
    private Rectangle aura;

    private double oldXL;
    private double oldXA;
    private double precLifeW;
    private double precAuraW;

    private double maxLifeX;
    private double maxAuraX;

    public UIPlayerTwo(Player player) {
        super(0,0,780,1280,0,Direction.STOP);
        this.owner = player;
        maxLifeX = 598d;
        maxAuraX = 498d;
        oldXL = 661;
        oldXA = 761;
        precLifeW = maxLifeX;
        precAuraW = maxAuraX;
        borderL = new Rectangle(660,20,600,40);
        borderL.setFill(Color.LIGHTSLATEGRAY);
        borderA = new Rectangle(760,65,500,30);
        borderA.setFill(Color.LIGHTSLATEGRAY);
        life = new Rectangle(oldXL,21, maxLifeX, 38);
        life.setFill(Color.LIGHTGREEN);
        aura = new Rectangle(oldXA,66, maxAuraX, 28);
        aura.setFill(Color.LIGHTBLUE);
        globalManager.getUI().getChildren().addAll(borderL,borderA,life,aura);
    }

    @Override
    public void draw() {
        double lifeWidth = (maxLifeX / 100d) * owner.getLife();
        double newX = oldXL + (precLifeW - lifeWidth);
        life.setWidth(lifeWidth);
        life.setX(newX);
        oldXL = newX;
        precLifeW = lifeWidth;

        double auraWidth = (maxAuraX / 100d) * owner.getAura();
        newX = oldXA + (precAuraW - auraWidth);
        aura.setWidth(auraWidth);
        aura.setX(newX);
        oldXA = newX;
        precAuraW = auraWidth;
    }
}
