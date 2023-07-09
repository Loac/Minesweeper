package joac.minesweeper.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import joac.minesweeper.game.Cell;
import joac.minesweeper.game.Field;

import java.util.ArrayList;
import java.util.List;

public class Minefield extends VBox {

    private final Field field;

    private final List<Square> squares = new ArrayList<>();

    public Minefield(Field field) {
        this.field = field;
    }

    public void build(EventHandler<? super MouseEvent> eventHandler) {
        VBox rows = new VBox();
        HBox row = new HBox();
        for (Cell cell : field.getCells()) {
            Square square = new Square(cell);
            square.getPin().setOnMouseClicked(eventHandler);
            row.getChildren().add(square);
            squares.add(square);

            if (row.getChildren().size() >= field.getWidth()) {
                rows.getChildren().add(row);
                row = new HBox();
            }
        }
        rows.setSpacing(2);
        rows.getChildren().forEach(node -> ((HBox) node).setSpacing(2));

        getChildren().add(rows);
        setPadding(new Insets(20, 20, 20, 20));
        autosize();
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

}
