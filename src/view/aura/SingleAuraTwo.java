package view.aura;

import model.dlv.AuraPack;
import model.Observer;
import model.Player;
import model.Subject;

public class SingleAuraTwo extends MultiAuraTwo implements Subject {

    private Observer observer;

    public SingleAuraTwo(Player owner) {
        super(owner);
        notifyObserver();
    }

    @Override
    protected void setTarget(Player target) {
        super.setTarget(target);
        attach((Observer) target);
    }

    @Override
    public void attach(Observer o) {
        this.observer = o;
    }

    @Override
    public void notifyObserver() {
        observer.update(new AuraPack(getX(),getY(),getDirection()));
    }

}
