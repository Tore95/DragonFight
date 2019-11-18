package view.aura;

import model.dlv.AuraPack;
import model.Observer;
import model.Player;
import model.Subject;
import model.enums.Direction;

public class SingleAuraFinal extends MultiAuraFinal implements Subject {

    private Observer observer;

    public SingleAuraFinal(Player owner) {
        super(owner);
    }

    // MULTI AURA FINAL OVERRIDES

    @Override
    public void launch(Direction toSet) {
        super.launch(toSet);
        notifyObserver();
    }

    @Override
    protected void setTarget(Player target) {
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
        observer.update(new AuraPack(getX(),getY(),getDirection()));
    }
}
