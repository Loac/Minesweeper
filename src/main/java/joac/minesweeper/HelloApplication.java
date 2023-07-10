package joac.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;
import joac.minesweeper.gui.*;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {

    private final Engine engine = new Engine();

    private Game game;

    private Board board;

    private final TopGridPane grid = new TopGridPane();

    private final TimerLabel timer = new TimerLabel();

    private final StateLabel state = new StateLabel();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public HelloApplication() {
        grid.setState(state);
        grid.setTimer(timer);

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
        game = engine.newGame();
        game.getField().printCells();

        board = new Board(game.getField());
        board.setHandler(this::actionMinefield);

        VBox root = new VBox();
        root.getChildren().addAll(buildApplicationMenu(), grid, board);
        root.autosize();

        Scene scene = new Scene(root, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(event -> scheduler.shutdown());
    }

    public BorderPane buildApplicationMenu() {
        AppMenu appMenu = new AppMenu();
        appMenu.getMainMenu().setOnCloseAction(event -> actionExit());
        appMenu.getMainMenu().setOnNewGameAction(event -> actionExit());

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(appMenu);

        return borderPane;
    }

    public void actionNewGame() {

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
