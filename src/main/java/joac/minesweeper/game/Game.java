package joac.minesweeper.game;

public class Game {

    private final Field field;

    public State state = State.NEW;

    public Game(GameProperties properties) {
        field = new Field(properties.getFieldWith(), properties.getFieldHeight());

        field.fillCells();
        field.plantMines(properties.getMineCount());
        field.calcCells();
    }

    public Field getField() {
        return field;
    }

    public void markCell(Cell cell) {
        field.markCell(cell);
        inProgress();
    }

    public void openCell(Cell cell) {
        if (!cell.canOpened())
            return;

        field.openCell(cell);

        if (cell.isMine()) {
            gameOver();
            return;
        }

        inProgress();
    }

    public void inProgress() {
        state = State.IN_PROGRESS;
    }

    public void gameOver() {
        state = State.LOSE;
    }

    public enum State {
        NEW,
        IN_PROGRESS,
        LOSE,
        WIN
    }
}
