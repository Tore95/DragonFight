package model;

public class FPS {

    private static long FPS = 60;

    public void waitFrame() {
        try {
            Thread.sleep(1000/FPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitFrame(long fps) {
        try {
            Thread.sleep(1000/fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
