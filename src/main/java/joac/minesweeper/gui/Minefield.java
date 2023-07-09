package joac.minesweeper.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import joac.minesweeper.game.Cell;
import joac.minesweeper.game.Field;

public class Minefield extends VBox {

    private final Field field;

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
}
