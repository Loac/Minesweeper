package joac.minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import joac.minesweeper.game.Cell;
import joac.minesweeper.game.Engine;
import joac.minesweeper.game.Game;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private final Engine engine = new Engine();

    private Game game;

    public static class CellValue extends Label {

        public CellValue(int danger) {
            setMaxWidth(40);
            setMaxHeight(40);
            setMinWidth(40);
            setMinHeight(40);
            setAlignment(Pos.CENTER);
            setFont(Font.font("Monaco", 16));
            setBorder(new Border(new BorderStroke(Color.rgb(220,220,220), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

            switch (danger) {
                case 1, 2, 3, 4, 5, 6, 7, 8 -> setText(String.valueOf(danger));
                case 9 -> setText("✖");
            }

            switch (danger) {
                case 1 -> setStyle("-fx-text-fill: blue;");
                case 2 -> setStyle("-fx-text-fill: green;");
                case 3 -> setStyle("-fx-text-fill: red;");
                case 4 -> setStyle("-fx-text-fill: darkblue;");
                case 5, 6, 7, 8 -> setStyle("-fx-text-fill: brown;");
            }
        }
    }

    private class CellButton extends Button {

        private final Cell cell;

        public CellButton(Cell cell) {
            this.cell = cell;

            setMaxWidth(40);
            setMaxHeight(40);
            setMinWidth(40);
            setMinHeight(40);
            setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY))
                    actionOpenCell();
                if (e.getButton().equals(MouseButton.SECONDARY)) {
                    actionSwitchCell();
                }
            });
        }

        public void actionSwitchCell() {
            game.markCell(cell);
            update();
        }

        private void actionOpenCell() {
            game.openCell(cell);
            update();
        }

        public void update() {
            switch (cell.getState()) {
                case DEFAULT -> setText("");
                case MARKED -> setText("⚑");
                case UNKNOWN -> setText("?");
                case OPENED -> setVisible(false);
            }
        }
    }

    public static class CellGui {

        private final Label label;

        private final Button button;

        public CellGui() {
            this.label = new Label();
            this.button = new Button();
        }
    }

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

    public static void main(String[] args) {
        launch();
    }
}
