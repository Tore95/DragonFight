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
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import javafx.application.Application;
import javafx.application.Platform;
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
    private InputProgram facts;

    private static final String encodingResource = "src/model/dlv/encoding";
    private static final Handler handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));

    private void doAction(PlayerAction action) {
        switch (action) {
            case KICK: kick(); break;
            case PUNCH: punch(); break;
            case AURA1: auraOne(); break;
            case AURA2: auraTwo(); break;
            case FINAL: auraFinal(); break;
        }
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

    private void ia() throws Exception {
        facts = new ASPInputProgram();
        facts.addObjectInput(playerOnePack);
        facts.addObjectInput(playerTwoPack);
        for (AuraPack auraPack : auraPacks) {
            facts.addObjectInput(auraPack);
        }

        handler.addProgram(facts);
        Output o = handler.startSync();

        AnswerSets answerSets = (AnswerSets) o;

        AnswerSet answerSet = answerSets.getAnswersets().get(0);
        System.out.println(answerSet);

        for (Object obj: answerSet.getAtoms()) {
            if (obj instanceof Do) {
                Do action = (Do) obj;
                doAction(action.getRealAction());
            } else if (obj instanceof Go) {
                Go goTo = (Go) obj;
                goTo(goTo.getRealDirection(),goTo.getDistance());
            }
        }
        handler.removeProgram(facts);
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
        InputProgram encoding = new InputProgram();
        encoding.addFilesPath(encodingResource);
        handler.addProgram(encoding);
    }

    @Override
    public void update(AuraPack pack) {
        auraPacks.add(pack);
    }

    @Override
    public void update(PlayerOnePack pack) {
        this.playerOnePack = pack;
        this.playerTwoPack = new PlayerTwoPack(getX(),getY(),getLife(),getAura(),getDirection(),getTurned(),getPlayerAction());
        try {
            ia();
        } catch (Exception e) {
            System.err.println("Problem with IA");
            Platform.exit();
        }
        auraPacks.clear();
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
