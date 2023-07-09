package joac.minesweeper.gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import joac.minesweeper.game.Cell;

/**
 * Игровое поле, значение и кнопка.
 */
public class Square extends Group {

    private final Pin pin;

    private final Value value;

    public Square(Cell cell) {
        this.pin = new Pin(cell);
        this.value = new Value(cell);

        getChildren().add(value);
        getChildren().add(pin);
    }

    public Pin getPin() {
        return pin;
    }

    public Value getValue() {
        return value;
    }

    public static class Pin extends Button {

        private final Cell cell;

        public Pin(Cell cell) {
            this.cell = cell;

            setMaxWidth(40);
            setMaxHeight(40);
            setMinWidth(40);
            setMinHeight(40);
            setFocusTraversable(false);
        }

        public Cell getCell() {
            return cell;
        }
    }

    public static class Value extends Label {

        public Value(Cell cell) {
            setMaxWidth(40);
            setMaxHeight(40);
            setMinWidth(40);
            setMinHeight(40);
            setAlignment(Pos.CENTER);
            setFont(Font.font("Monaco", 16));
            setBorder(new Border(new BorderStroke(Color.rgb(220,220,220), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

            switch (cell.getDanger()) {
                case 1, 2, 3, 4, 5, 6, 7, 8 -> setText(String.valueOf(cell.getDanger()));
                case 9 -> setText("✖");
            }

            switch (cell.getDanger()) {
                case 1 -> setStyle("-fx-text-fill: blue;");
                case 2 -> setStyle("-fx-text-fill: green;");
                case 3 -> setStyle("-fx-text-fill: red;");
                case 4 -> setStyle("-fx-text-fill: darkblue;");
                case 5, 6, 7, 8 -> setStyle("-fx-text-fill: brown;");
            }
        }
    }
}
