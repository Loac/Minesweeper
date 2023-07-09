package joac.minesweeper.game;

import java.util.Date;

public class Game {

    private final Minefield minefield;

    public State state = State.NEW;

    public Date startedAt;

    public Game(GameProperties properties) {
        minefield = new Minefield(properties.getFieldWith(), properties.getFieldHeight());

        minefield.fillCells();
        minefield.plantMines(properties.getMineCount());
        minefield.calcCells();
    }

    public Minefield getField() {
        return minefield;
    }

    public void switchMarker(Cell cell) {
        minefield.switchMarker(cell);
        progress();
    }

    public void openCell(Cell cell) {
        if (!cell.canOpened())
            return;

        minefield.openCell(cell);

        if (cell.isMine()) {
            gameLose();
            return;
        }

        if (checkWinCondition()) {
            gameWin();
            return;
        }

        progress();
    }

    /**
     * Проверить условия игры.
     * Если есть хотя бы одна закрытая клетка, которая не является миной, вернет `false`;
     */
    public boolean checkWinCondition() {
        for (Cell cell : minefield.getCells())
            if (!cell.isOpened() && !cell.isMine())
                return false;

        return true;
    }

    /**
     * Игра начинается, когда пользователь совершит какое-либо действие.
     * В этот момент запускается таймер.
     */
    public void progress() {
        if (isNew()) {
            gameStart();
            state = State.IN_PROGRESS;
        }
    }

    public void gameStart() {
        startedAt = new Date();
    }

    public void gameWin() {
        minefield.markMines();
        state = State.WIN;
    }

    public void gameLose() {
        minefield.openCells();
        state = State.LOSE;
    }

    public boolean isNew() {
        return state.equals(State.NEW);
    }

    public enum State {
        NEW,
        IN_PROGRESS,
        LOSE,
        WIN
    }
}
