package joac.minesweeper.game;

public class Cell {

    public static final int MINE = 9;

    public static final int CLEAR = 0;

    private final int x;

    private final int y;

    private int danger; // 0 пусто; 1-8 количество мин; 9 мина.

    private boolean isOpened;

    private Marker marker;

    public Cell(int x, int y, int danger) {
        this.x = x;
        this.y = y;
        this.danger = danger;
        this.isOpened = false;
        this.marker = Marker.DEFAULT;
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

    public boolean isClear() {
        return danger == CLEAR;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Marker getState() {
        return marker;
    }

    public void setState(Marker marker) {
        this.marker = marker;
    }

    public void switchMarker() {
        switch (marker) {
            case DEFAULT -> marker = Marker.FLAG;
            case FLAG -> marker = Marker.UNKNOWN;
            case UNKNOWN -> marker = Marker.DEFAULT;
        }
    }

    public void markFlag() {
        marker = Marker.FLAG;
    }

    public boolean canOpened() {
        return !isOpened && marker.equals(Marker.DEFAULT);
    }

    public void open() {
        isOpened = true;
    }

    /**
     * Маркер клетки (кнопки), которым управляет игрок.
     */
    public enum Marker {
        DEFAULT,
        FLAG,
        UNKNOWN
    }
}
