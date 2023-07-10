package joac.minesweeper.game;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Game {

    private final GameProperties properties;

    private final Minefield minefield;

    public State state = State.NEW;

    public long startedAt;

    public long finishedAt;

    public Game(GameProperties properties) {
        this.properties = properties;
        minefield = new Minefield(properties.getFieldWith(), properties.getFieldHeight());
        minefield.fillCells();
    }

    public String getTimeText() {
        long millis = 0;
        switch (state) {
            case IN_PROGRESS -> millis = System.currentTimeMillis() - startedAt;
            case WIN, LOSE -> millis = finishedAt - startedAt;
        }

        LocalTime timer = LocalTime.ofSecondOfDay(millis / 1000);
        return timer.format(DateTimeFormatter.ofPattern("mm:ss"));
    }

    public String getStateText() {
        String text = "";
        switch (state) {
            case NEW -> text = "Ready!";
            case IN_PROGRESS -> text = "In progress";
            case LOSE -> text = "You lose";
            case WIN -> text = "You win";
        }
        return text;
    }

    public Minefield getField() {
        return minefield;
    }

    public void switchMarker(Cell cell) {
        minefield.switchMarker(cell);
    }

    public void openCell(Cell cell) {
        if (isNew())
            gameStart(cell);

        if (!cell.canOpened())
            return;

        minefield.openCell(cell);

        if (cell.isMine()) {
            gameLose();
        } else if (checkWinCondition()) {
            gameWin();
        }
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

    public void gameStart(Cell startCell) {
        minefield.plantMines(properties.getMineCount(), startCell);
        minefield.calcCells();

        state = State.IN_PROGRESS;
        startedAt = System.currentTimeMillis();

        System.out.println("Start game");
        minefield.printCells();
        System.out.println(getStateText());
    }

    public void gameWin() {
        minefield.markMines();
        state = State.WIN;
        finishedAt = System.currentTimeMillis();
        System.out.println(getStateText());
    }

    public void gameLose() {
        minefield.openCells();
        state = State.LOSE;
        finishedAt = System.currentTimeMillis();
        System.out.println(getStateText());
    }

    public boolean isNew() {
        return state.equals(State.NEW);
    }

    public boolean inProgress() {
        return state.equals(State.IN_PROGRESS);
    }

    public boolean isOver() {
        return state.equals(State.LOSE) || state.equals(State.WIN);
    }

    public enum State {
        NEW,
        IN_PROGRESS,
        LOSE,
        WIN
    }
}
