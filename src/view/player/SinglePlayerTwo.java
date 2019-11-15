package view.player;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import model.dlv.*;
import model.Observer;
import model.enums.Characters;
import model.enums.Direction;
import model.enums.PlayerAction;

import java.util.LinkedList;

public class SinglePlayerTwo extends MultiPlayerTwo implements Observer {

    private boolean inMovement;
    private int currDistance;
    private Direction goToDirection;
    private int maxDistance;
    private PlayerOnePack playerOnePack;
    private PlayerTwoPack playerTwoPack;
    private LinkedList<AuraPack> auraPacks;

    private static final String encodingResource = "src/model/dlv/encoding";
    private static final Handler handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));

    private void doAction(PlayerAction action) {
        if (setPlayerAction(action)) action();
    }

    private void goTo(Direction direction, int distance) {
        if (getDirection().equals(Direction.STOP) && getPlayerAction().equals(PlayerAction.REST)) {
            switch (direction) {
                case UP: goUp(true); break;
                case DOWN: goDown(true); break;
                case RIGHT: goRight(true); break;
                case LEFT: goLeft(true); break;
            }
            goToDirection = direction;
            currDistance = 0;
            maxDistance = distance;
        }
    }

    private void stopGoTo() {
        switch (goToDirection) {
            case UP: goUp(false); break;
            case DOWN: goDown(false); break;
            case RIGHT: goRight(false); break;
            case LEFT: goLeft(false); break;
        }
    }

    public SinglePlayerTwo(Characters character) {
        super(character);
        auraPacks = new LinkedList<>();
        inMovement = false;
        try {
            ASPMapper.getInstance().registerClass(Do.class);
            ASPMapper.getInstance().registerClass(Go.class);
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AuraPack pack) {
        auraPacks.add(pack);
    }

    @Override
    public void update(PlayerOnePack pack) {
        this.playerOnePack = pack;
        this.playerTwoPack = new PlayerTwoPack(getX(),getY(),getLife(),getAura(),getDirection(),getTurned(),getPlayerAction());
        ia();
        auraPacks.clear();
    }

    private void ia() {

        handler.removeAll();

        InputProgram facts = new ASPInputProgram();
        try {
            facts.addObjectInput(playerOnePack);
            facts.addObjectInput(playerTwoPack);
            for (AuraPack auraPack : auraPacks) {
                facts.addObjectInput(auraPack);
            }
            facts.addFilesPath(encodingResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);

        Output o = handler.startSync();
        AnswerSets answer = (AnswerSets) o;

        try {
            for (AnswerSet as: answer.getAnswersets()) {
                System.out.println(as.getAnswerSet());
                for (Object obj: as.getAtoms()) {
                    if (obj instanceof Do) {
                        Do action = (Do) obj;
                        doAction(action.getRealAction());
                    } else if (obj instanceof Go) {
                        Go goTo = (Go) obj;
                        goTo(goTo.getRealDirection(),goTo.getDistance());
                    }
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ia2() {
        if (canAttack() && !isDanger()) {
            //TODO scelgo come attaccare
            if (getAura() > 80) {
                // attaccon finale
            } else if (getAura() > 40) {
                // attaccon con onda2

            } else if (getAura() > 20) {
                // attaccon con onda1
            } else {
                // attacco con pugno o calcio
            }

        } else if (!canAttack() && !isDanger()){
            // cerco di allinearmi per attaccare
            int x_distance;
            int y_distance;
            Direction x_dir;
            Direction y_dir;

            if (playerOnePack.getX() > (int) getX()) {
                x_distance = playerOnePack.getX() - (int) getX();
                x_dir = Direction.RIGHT;
            }
            else {
                x_distance = (int) getX() - playerOnePack.getX();
                x_dir = Direction.LEFT;
            }
            if (playerOnePack.getY() > (int) getY()) {
                y_distance = playerOnePack.getY() - (int) getY();
                y_dir = Direction.UP;
            }
            else {
                y_distance = (int) getY() - playerOnePack.getY();
                y_dir = Direction.DOWN;
            }

            goTo(y_dir,y_distance);

            if (getAura() > 20) {
                if (x_distance > 500) {
                    x_distance -= (x_distance - 500);
                    goTo(x_dir,x_distance);
                }
            } else {
                goTo(x_dir,x_distance - 32);
            }

        } else {
            //cerco di schivare l'attacco
            if (playerOnePack.getRealAction() != PlayerAction.PUNCH && playerOnePack.getRealAction() != PlayerAction.KICK && !auraPacks.isEmpty()) {
                if (auraPacks.getFirst().getY() >= getY()) goTo(Direction.UP,getHeight());
                else goTo(Direction.DOWN,getHeight());
            } else {
                if (getY() > (1280 / 2d)) goTo(Direction.LEFT,200);
                else goTo(Direction.RIGHT,200);
            }
        }
    }

    private boolean canAttack() {
        //se sono allineato per l'attacco ritorno true

        int y_distance;
        int x_distance;

        if (playerOnePack.getY() > getY()) y_distance = playerOnePack.getY() - (int) getY();
        else y_distance = (int) getY() - playerOnePack.getY();
        if (playerOnePack.getX() > getX()) x_distance = playerOnePack.getX() - (int) getX();
        else x_distance = (int) getX() - playerOnePack.getX();

        if (getAura() > 20 && x_distance < 550) {
            if (y_distance < getHeight() / 2) return true;
            else return false;
        } else {
            if (x_distance < getWidth()) return true;
            else return false;
        }
    }

    private boolean isDanger() {
        // se mi arriva un attacco addosso ritorno true
        int y_distance;
        int x_distance;
        int xa_distance;
        int ya_distance;

        if (playerOnePack.getY() > getY()) y_distance = playerOnePack.getY() - (int) getY();
        else y_distance = (int) getY() - playerOnePack.getY();
        if (playerOnePack.getX() > getX()) x_distance = playerOnePack.getX() - (int) getX();
        else x_distance = (int) getX() - playerOnePack.getX();

        if (!auraPacks.isEmpty()) {
            if (auraPacks.getFirst().getY() > getY()) ya_distance = auraPacks.getFirst().getY() - (int)getY();
            else ya_distance = (int) getY() - auraPacks.getFirst().getY();
            if (ya_distance < getHeight() / 2) return true;
            else return false;
        } else {
            if (playerOnePack.getRealAction() != PlayerAction.PUNCH && playerOnePack.getRealAction() != PlayerAction.KICK) {
                if (x_distance < getWidth() && y_distance < getHeight()) return true;
                else return false;
            } else return false;
        }
    }

    @Override
    public void move() {
        super.move();
        if (getDirection() != Direction.STOP) {
            if (currDistance < maxDistance) currDistance += getVelocity();
            else stopGoTo();
        }
    }
}
