package joac.minesweeper.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class AppMenu extends MenuBar {

    private final MainMenu mainMenu = new MainMenu();

    public AppMenu() {
        mainMenu.setText("Main");

        getMenus().add(mainMenu);

        String osName = System.getProperty("os.name", "unknown");
        if (osName.startsWith("Mac")) {
            setUseSystemMenuBar(true);
            setMaxHeight(0);
            setMinHeight(0);
        }
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public static class MainMenu extends Menu {

        private final MenuItem newEasyGame = new MenuItem();

        private final MenuItem newMediumGame = new MenuItem();

        private final MenuItem newHardGame = new MenuItem();

        private final MenuItem closeApp = new MenuItem();

        public MainMenu() {
            Menu newGame = new Menu();
            newGame.setText("New game");
            newEasyGame.setText("Easy");
            newMediumGame.setText("Medium");
            newHardGame.setText("Hard");
            closeApp.setText("Close");

            newGame.getItems().add(newEasyGame);
            newGame.getItems().add(newMediumGame);
            newGame.getItems().add(newHardGame);

            getItems().add(newGame);
            getItems().add(new SeparatorMenuItem());
            getItems().add(closeApp);
        }

        public final void setOnNewEasyGameAction(EventHandler<ActionEvent> eventHandler) {
            newEasyGame.setOnAction(eventHandler);
        }

        public final void setOnNewMediumGameAction(EventHandler<ActionEvent> eventHandler) {
            newMediumGame.setOnAction(eventHandler);
        }

        public final void setOnNewHardGameAction(EventHandler<ActionEvent> eventHandler) {
            newHardGame.setOnAction(eventHandler);
        }

        public final void setOnCloseAction(EventHandler<ActionEvent> eventHandler) {
            closeApp.setOnAction(eventHandler);
        }
    }

}
