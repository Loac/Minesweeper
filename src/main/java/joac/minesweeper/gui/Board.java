package joac.minesweeper.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import joac.minesweeper.game.Cell;
import joac.minesweeper.game.Minefield;

import java.util.ArrayList;
import java.util.List;

public class Board extends VBox {

    private final Minefield minefield;

    private final List<Square> squares = new ArrayList<>();

    public Board(Minefield minefield) {
        this.minefield = minefield;
        setPadding(new Insets(20, 20, 20, 20));

        build();
    }

    private void build() {
        Rows rows = new Rows();
        Row row = new Row();
        for (Cell cell : minefield.getCells()) {
            Square square = new Square(cell);
            row.getChildren().add(square);
            squares.add(square);

            if (row.getChildren().size() >= minefield.getWidth()) {
                rows.getChildren().add(row);
                row = new Row();
            }
        }

        getChildren().add(rows);
        autosize();
    }

    public void setHandler(EventHandler<? super MouseEvent> eventHandler) {
        squares.forEach(square -> square.getPin().setOnMouseClicked(eventHandler));
    }

    public double width() {
        return getBoundsInParent().getWidth();
    }

    public double height() {
        return getBoundsInParent().getHeight();
    }

    public void update() {
        squares.forEach(this::update);
    }

    public void update(Square square) {
        square.getPin().update();
    }

    public void update(Square.Pin pin) {
        pin.update();
    }

    public static class Rows extends VBox {

        public Rows() {
            setSpacing(2);
        }
    }

    public static class Row extends HBox {

        public Row() {
            setSpacing(2);
            setMaxHeight(40);
            setMinHeight(40);
        }
    }
}
