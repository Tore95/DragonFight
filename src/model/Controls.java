package model;

public interface Controls {
    boolean goUp(boolean val);
    boolean goDown(boolean val);
    boolean goRight(boolean val);
    boolean goLeft(boolean val);

    void punch();
    void kick();
    void auraOne();
    void auraTwo();
    void auraFinal();
}
