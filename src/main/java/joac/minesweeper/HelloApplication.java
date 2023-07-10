package joac.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;
import joac.minesweeper.gui.AppMenu;
import joac.minesweeper.gui.Board;
import joac.minesweeper.gui.Square;

import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {

    private final Engine engine = new Engine();

    private Game game;

    private Board board;

    private Label timer;

    private Label state;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(Stage stage) {

        game = engine.newGame();
        game.getField().printCells();

        board = new Board(game.getField());
        board.setHandler(this::actionMinefield);

        timer = new Label();
        timer.setText(game.getTimeText());
        timer.setMaxHeight(30);
        timer.setMinHeight(30);
        timer.setAlignment(Pos.BASELINE_RIGHT);
        timer.setMaxWidth(400);

        state = new Label();
        state.setAlignment(Pos.BASELINE_CENTER);
        state.setMaxWidth(400);

        scheduler.scheduleWithFixedDelay(() ->
                Platform.runLater(() -> {
                    timer.setText(game.getTimeText());
                    state.setText(game.getStateText());
                }),
                0, 500, TimeUnit.MILLISECONDS);



        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        grid.getColumnConstraints().addAll(col1,col2,col3);


        grid.add(state, 1, 0);
        grid.add(timer, 2, 0);
        grid.setPadding(new Insets(20, 20, 0, 20));


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
