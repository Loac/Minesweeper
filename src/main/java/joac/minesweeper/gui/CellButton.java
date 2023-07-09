package joac.minesweeper.gui;

import javafx.scene.control.Button;
import joac.minesweeper.game.Cell;

public class CellButton extends Button {

    private final Cell cell;

    public CellButton(Cell cell) {
        this.cell = cell;

        setMaxWidth(40);
        setMaxHeight(40);
        setMinWidth(40);
        setMinHeight(40);
    }

    public Cell getCell() {
        return cell;
    }
}
