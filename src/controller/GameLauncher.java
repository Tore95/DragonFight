package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.enums.MenuAction;
import model.enums.UserState;
import view.GlobalManager;

public class GameLauncher extends Application {

    private static GlobalManager globalManager = GlobalManager.getInstance();

    public static void updateMenu() {
        MenuAction currMenuAction = globalManager.getMenuAction();
        UserState currUserState = globalManager.getUserState();
        switch (currUserState) {
            case MAIN_MENU:
                switch (currMenuAction) {
                    case SINGLEPLAYER:
                    case MULTIPLAYER:
                        globalManager.playerSelection();
                        break;
                    case EXIT:
                        Platform.exit();
                }
                break;
            case SELECTION_MENU:
                switch (currMenuAction) {
                    case PG_SELECTED:
                        globalManager.gameStart();
                        break;
                    case BACK:
                        globalManager.mainMenu();
                        break;
                }
                break;
            case GAME:
                switch (currMenuAction) {
                    case BACK:
                        globalManager.playerSelection();
                        break;
                    case EXIT:
                        Platform.exit();
                }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        globalManager.initGame(primaryStage);
    }
}
