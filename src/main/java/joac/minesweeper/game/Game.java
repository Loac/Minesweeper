package joac.minesweeper.game;

public class Game {

    private final Field field;

    public Game(GameProperties properties) {
        field = new Field(properties.getFieldWith(), properties.getFieldHeight());

        field.fillCells();
        field.plantMines(properties.getMineCount());
        field.calcCells();
    }

    public Field getField() {
        return field;
    }
}
