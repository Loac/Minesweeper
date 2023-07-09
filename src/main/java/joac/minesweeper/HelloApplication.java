package joac.minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;
import joac.minesweeper.gui.Board;
import joac.minesweeper.gui.Square;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private final Engine engine = new Engine();

    private Game game;

    private Board board;

    @Override
    public void start(Stage stage) throws IOException {

        game = engine.newGame();
        game.getField().printCells();

        board = new Board(game.getField());
        board.setHandler(this::actionMinefield);

        Scene scene = new Scene(board, board.width(), board.height());
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void actionMinefield(MouseEvent event) {
        Square.Pin pin = (Square.Pin) event.getSource();

        switch (event.getButton()) {
            case PRIMARY -> {
                game.openCell(pin.getCell());
                board.update();
            }
            case SECONDARY -> {
                game.markCell(pin.getCell());
                board.update(pin);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
