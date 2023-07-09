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
import joac.minesweeper.game.Field;
import joac.minesweeper.game.Game;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

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

    private static class CellButton extends Button {
        public CellButton() {
            setMaxWidth(40);
            setMaxHeight(40);
            setMinWidth(40);
            setMinHeight(40);
            setOnMouseClicked(e -> {
                System.out.println(e.getButton());
                if (e.getButton().equals(MouseButton.PRIMARY))
                    setVisible(false);
                if (e.getButton().equals(MouseButton.SECONDARY)) {
                    setDisable(true);
                    setText("X");
                    setOpacity(1);
                }
            });
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
        Group group = new Group();

        // Field field = new Field(7, 5);
        // field.plantMines(4);
        // field.calcCells();
        // field.printCells();
        Engine engine = new Engine();
        Game game = engine.newGame();
        game.getField().printCells();



        // Arrays.stream(arr).forEach(item ->
        //         System.out.printf("%s: %s x %s%n", item, item % 4, item / 4)
        // );

        VBox minefield = new VBox();
        minefield.setPadding(new Insets(20, 20, 20, 20));
        VBox rows = new VBox();
        rows.setBorder(new Border(new BorderStroke(Color.rgb(220,220,220), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        HBox row = new HBox();
        for (Cell cell : game.getField().getCells()) {
            row.getChildren().add(new CellValue(cell.getDanger()));

            if (row.getChildren().size() >= game.getField().getWidth()) {
                rows.getChildren().add(row);
                row = new HBox();
            }
        }
        minefield.getChildren().add(rows);
        minefield.autosize();

        // for (int y = 0; y < game.getField().getHeight(); y++) {
        //     HBox row = new HBox();
        //     for (int x = 0; x < game.getField().getWidth(); x++) {
        //         row.getChildren().add(new CellValue("0"));
        //     }
        //     minefield.getChildren().add(row);
        // }


        // group.getChildren().add(hBox);
        // vBox.getChildren().add(hBox);
        // group.getChildren().add(label2);
        // group.getChildren().add(button);


        Scene scene = new Scene(minefield, minefield.getBoundsInParent().getWidth(), minefield.getBoundsInParent().getHeight());
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
