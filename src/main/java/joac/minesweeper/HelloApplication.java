package joac.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;
import joac.minesweeper.game.GameProperties;
import joac.minesweeper.gui.*;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {

    private final Engine engine;

    private Stage stage;

    private Game game;

    private Board board;

    private final VBox boardContainer;

    private final Scene scene;

    private final TimerLabel timer;

    private final StateLabel state;

    private final ScheduledExecutorService scheduler;

    /**
     * Основные элементы интерфейса вынесены в свойства класса.
     * Цикл, периодически проверяет состояние игры и выводит данные в интерфейс.
     *
     * <p> Компонент boardContainer содержит игровое поле, которое создается заново для каждой новой игры.
     * Он является своего рода ссылкой на конкретный элемент сцены, к которому можно получить доступ
     * напрямую, минуя всех родителей.
     */
    public HelloApplication() {
        // Game.
        engine = new Engine();

        // GUI.
        timer = new TimerLabel();
        state = new StateLabel();

        TopGridPane grid = new TopGridPane();
        grid.setState(state);
        grid.setTimer(timer);

        boardContainer = new VBox();
        VBox root = new VBox();
        root.getChildren().addAll(buildApplicationMenu(), grid, boardContainer);

        scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Service.
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(() ->
                Platform.runLater(() -> {
                    if (null == game)
                        return;

                    timer.setText(game.getTimeText());
                    state.setText(game.getStateText());
                }),
        0, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        actionNewGame(GameProperties.easy());

        stage.setTitle("Minesweeper!");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> scheduler.shutdown());
    }

    public Scene buildGame(GameProperties properties) {
        game = engine.newGame(properties);

        board = new Board(game.getField());
        board.setHandler(this::actionMinefield);
        boardContainer.getChildren().clear();
        boardContainer.getChildren().add(board);
        stage.sizeToScene();

        return scene;
    }

    public AppMenu buildApplicationMenu() {
        AppMenu appMenu = new AppMenu();
        appMenu.getMainMenu().setOnNewEasyGameAction(event -> actionNewGame(GameProperties.easy()));
        appMenu.getMainMenu().setOnNewMediumGameAction(event -> actionNewGame(GameProperties.medium()));
        appMenu.getMainMenu().setOnNewHardGameAction(event -> actionNewGame(GameProperties.hard()));
        appMenu.getMainMenu().setOnCloseAction(event -> actionExit());

        return appMenu;
    }

    public void actionNewGame(GameProperties properties) {
        stage.setScene(buildGame(properties));
    }

    public void actionExit() {
        System.exit(0);
    }

    public void actionMinefield(MouseEvent event) {
        Square.Pin pin = (Square.Pin) event.getSource();

        switch (event.getButton()) {
            case PRIMARY -> {
                game.openCell(pin.getCell());
                board.update();
            }
            case SECONDARY -> {
                game.switchMarker(pin.getCell());
                board.update(pin);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
