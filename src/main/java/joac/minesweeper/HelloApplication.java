package joac.minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;
import joac.minesweeper.gui.Minefield;
import joac.minesweeper.gui.Square;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private final Engine engine = new Engine();

    private Game game;

    private Minefield minefield;

    @Override
    public void start(Stage stage) throws IOException {

        game = engine.newGame();
        game.getField().printCells();

        minefield = new Minefield(game.getField());
        minefield.build(this::actionMinefield);

        Scene scene = new Scene(minefield, minefield.width(), minefield.height());
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
                minefield.update();
            }
            case SECONDARY -> {
                game.markCell(pin.getCell());
                minefield.update(pin);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
