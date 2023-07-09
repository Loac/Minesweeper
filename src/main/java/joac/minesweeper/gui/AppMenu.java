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

        private final MenuItem newGame = new MenuItem();
        private final MenuItem closeApp = new MenuItem();

        public MainMenu() {
            newGame.setText("New game");
            closeApp.setText("Close");

            getItems().add(newGame);
            getItems().add(new SeparatorMenuItem());
            getItems().add(closeApp);
        }

        public final void setOnNewGameAction(EventHandler<ActionEvent> eventHandler) {
            newGame.setOnAction(eventHandler);
        }

        public final void setOnCloseAction(EventHandler<ActionEvent> eventHandler) {
            closeApp.setOnAction(eventHandler);
        }
    }

}
