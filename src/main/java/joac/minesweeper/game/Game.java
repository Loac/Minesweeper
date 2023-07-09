package joac.minesweeper.game;

public class Game {

    private final Minefield minefield;

    public State state = State.NEW;

    public Game(GameProperties properties) {
        minefield = new Minefield(properties.getFieldWith(), properties.getFieldHeight());

        minefield.fillCells();
        minefield.plantMines(properties.getMineCount());
        minefield.calcCells();
    }

    public Minefield getField() {
        return minefield;
    }

    public void markCell(Cell cell) {
        minefield.markCell(cell);
        inProgress();
    }

    public void openCell(Cell cell) {
        if (!cell.canOpened())
            return;

        minefield.openCell(cell);

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
        minefield.openCells();
        state = State.LOSE;
    }

    public enum State {
        NEW,
        IN_PROGRESS,
        LOSE,
        WIN
    }
}
