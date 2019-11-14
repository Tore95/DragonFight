package model;

public interface Subject {
    void attach(Observer o);
    void notifyObserver();
}
