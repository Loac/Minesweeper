package joac.minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import joac.minesweeper.game.Cell;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;
import joac.minesweeper.gui.CellButton;
import joac.minesweeper.gui.CellValue;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private final Engine engine = new Engine();

    private Game game;

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        game = engine.newGame();
        game.getField().printCells();


        VBox minefield = new VBox();
        minefield.setPadding(new Insets(20, 20, 20, 20));
        VBox rows = new VBox();
        rows.setBorder(new Border(new BorderStroke(Color.rgb(220,220,220), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        rows.setSpacing(2);
        HBox row = new HBox();
        row.setSpacing(2);
        for (Cell cell : game.getField().getCells()) {
            row.getChildren().add(new CellValue(cell.getDanger()));

            if (row.getChildren().size() >= game.getField().getWidth()) {
                rows.getChildren().add(row);
                row = new HBox();
                row.setSpacing(2);
            }
        }
        minefield.getChildren().add(rows);
        minefield.autosize();


        Group group = new Group();
        group.getChildren().add(minefield);
        group.getChildren().add(buttons(game));



        Scene scene = new Scene(group, minefield.getBoundsInParent().getWidth(), minefield.getBoundsInParent().getHeight());
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private VBox buttons(Game game) {
        VBox buttons = new VBox();
        buttons.setPadding(new Insets(20, 20, 20, 20));
        VBox rows = new VBox();
        rows.setBorder(new Border(new BorderStroke(Color.rgb(220,220,220), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        rows.setSpacing(2);
        HBox row = new HBox();
        row.setSpacing(2);
        for (Cell cell : game.getField().getCells()) {

            CellButton button = new CellButton(cell);
            button.setOnMouseClicked(this::action);

            button.setFocusTraversable(false);
            row.getChildren().add(button);

            if (row.getChildren().size() >= game.getField().getWidth()) {
                rows.getChildren().add(row);
                row = new HBox();
                row.setSpacing(2);
            }
        }
        buttons.getChildren().add(rows);
        return buttons;
    }


    public void action(MouseEvent event) {
        CellButton button = (CellButton) event.getTarget();
        if (event.getButton().equals(MouseButton.PRIMARY))
            primaryAction(button);
        if (event.getButton().equals(MouseButton.SECONDARY))
            secondaryAction(button);
    }

    public void primaryAction(CellButton button) {
        game.openCell(button.getCell());
        updateButton(button);
    }

    private void secondaryAction(CellButton button) {
        game.markCell(button.getCell());
        updateButton(button);
    }

    public void updateButton(CellButton button) {
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
