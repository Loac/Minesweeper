package joac.minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import joac.minesweeper.game.Cell;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;
import joac.minesweeper.gui.Minefield;
import joac.minesweeper.gui.Square;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private final Engine engine = new Engine();

    private Game game;

    @Override
    public void start(Stage stage) throws IOException {

        game = engine.newGame();
        game.getField().printCells();


        Minefield minefield = new Minefield(game.getField());
        minefield.build(this::action);


        Scene scene = new Scene(minefield, minefield.getBoundsInParent().getWidth(), minefield.getBoundsInParent().getHeight());
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void action(MouseEvent event) {

        Square.Pin button = (Square.Pin) event.getSource();
        if (event.getButton().equals(MouseButton.PRIMARY))
            primaryAction(button);
        if (event.getButton().equals(MouseButton.SECONDARY))
            secondaryAction(button);
    }

    public void primaryAction(Square.Pin button) {
        game.openCell(button.getCell());
        updateButton(button);
    }

    private void secondaryAction(Square.Pin button) {
        game.markCell(button.getCell());
        updateButton(button);
    }

    public void updateButton(Square.Pin button) {
        switch (button.getCell().getState()) {
            case DEFAULT -> button.setText("");
            case MARKED -> button.setText("⚑");
            case UNKNOWN -> button.setText("?");
            case OPENED -> button.setVisible(false);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
