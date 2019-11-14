package view.player;

import model.*;
import model.dlv.PlayerOnePack;
import model.enums.Characters;
import view.aura.SingleAuraFinal;
import view.aura.SingleAuraOne;
import view.aura.SingleAuraTwo;

public class SinglePlayerOne extends MultiPlayerOne implements Subject {

    private Observer observer;

    public SinglePlayerOne(Characters character) {
        super(character);
        globalManager.setPlayerOne(this);
    }

    // MULTI PLAYER TWO OVERRIDES

    @Override
    protected void launchAuraOne(Player target) {
        new SingleAuraOne(target);
    }

    @Override
    protected void launchAuraTwo(Player target) {
        new SingleAuraTwo(target);
    }

    @Override
    protected void launchAuraFinal(Player target) {
        new SingleAuraFinal(target);
    }

    @Override
    public void setTarget(Player target) {
        super.setTarget(target);
        attach((Observer) target);
    }

    // SUBJECT OVERRIDES

    @Override
    public void attach(Observer o) {
        this.observer = o;
    }

    @Override
    public void notifyObserver() {
            observer.update(new PlayerOnePack(getX(),getY(),getLife(),getAura(),getDirection(),getPlayerAction()));
    }
}
