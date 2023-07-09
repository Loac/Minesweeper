package joac.minesweeper.game;

public class Cell {

    public static final int MINE = 9;

    public static final int CLEAR = 0;

    private final int x;

    private final int y;

    private int danger; // 0 пусто; 1-8 количество мин; 9 мина.

    private State state;

    public Cell(int x, int y, int danger) {
        this.x = x;
        this.y = y;
        this.danger = danger;
        this.state = State.DEFAULT;
    }

    public int getDanger() {
        return danger;
    }

    public String getDangerText() {
        return String.valueOf(danger);
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public void incDanger() {
        if (!isMine())
            danger++;
    }

    public boolean isMine() {
        return danger == MINE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void switchState() {
        switch (state) {
            case DEFAULT -> state = State.MARKED;
            case MARKED -> state = State.UNKNOWN;
            case UNKNOWN -> state = State.DEFAULT;
        }
    }

    public boolean canOpened() {
        return state.equals(State.DEFAULT);
    }

    public void open() {
        state = State.OPENED;
    }

    /**
     * Состояние клетки (кнопки), которым управляет игрок.
     */
    public enum State {
        DEFAULT,
        MARKED,
        UNKNOWN,
        OPENED
    }
}
