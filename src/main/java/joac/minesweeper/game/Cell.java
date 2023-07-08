package joac.minesweeper.game;

public class Cell {
    // 00 10 20 30
    // 01 11 21 31
    // 02 12 22 32
    // 00 10 20 30 01 11 21 31 02 12 22 32

    // 01 02 03 04
    // 05 06 07 08
    // 09 10 11 12

    // 01 02 03 04 05 06 07 08 09 10 11 12
    // 12 / 4 = 3 х 0
    // 9 / 4 = 2 остаток 1
    // 7 / 4
    public static final int MINE = 9;
    public static final int FREE = 0;

    private int x;
    private int y;
    private int danger; // 0 пусто; 1-8 количество мин; 9 мина.

    private CellState state;

    public Cell(int x, int y, int danger) {
        this.x = x;
        this.y = y;
        this.danger = danger;
        this.state = CellState.HIDDEN;
    }

    public int getDanger() {
        return danger;
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

    private enum CellState {
        HIDDEN,
        HOLD,
        OPENED
    }
}
