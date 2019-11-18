package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GameObject;
import model.Player;
import model.enums.Direction;

public class UIPlayerOne extends GameObject {

    private Player owner;
    private Rectangle borderL;
    private Rectangle life;
    private Rectangle borderA;
    private Rectangle aura;

    private double maxLifeX;
    private double maxAuraX;

    public UIPlayerOne(Player player) {
        super(0,0,780,1280,0,Direction.STOP);
        this.owner = player;
        maxLifeX = 598d;
        maxAuraX = 498d;
        borderL = new Rectangle(20,20,600,40);
        borderL.setFill(Color.LIGHTSLATEGRAY);
        borderA = new Rectangle(20,65,500,30);
        borderA.setFill(Color.LIGHTSLATEGRAY);
        life = new Rectangle(21,21, maxLifeX, 38);
        life.setFill(Color.LIGHTGREEN);
        aura = new Rectangle(21,66, maxAuraX, 28);
        aura.setFill(Color.LIGHTBLUE);
        globalManager.getUI().getChildren().addAll(borderL,borderA,life,aura);
    }

    @Override
    public void draw() {
        double lifeWidth = (maxLifeX / 100d) * owner.getLife();
        life.setWidth(lifeWidth);
        double auraWidth = (maxAuraX / 100d) * owner.getAura();
        aura.setWidth(auraWidth);
    }
}
