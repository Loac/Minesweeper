package joac.minesweeper.game;

public class GameProperties {

    private int fieldWith;

    private int fieldHeight;

    private int mineCount;

    public int getFieldWith() {
        return fieldWith;
    }

    public void setFieldWith(int fieldWith) {
        this.fieldWith = fieldWith;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public static GameProperties easy() {
        GameProperties properties = new GameProperties();
        properties.setFieldWith(7);
        properties.setFieldHeight(5);
        properties.setMineCount(4);
        return properties;
    }

    public static GameProperties medium() {
        GameProperties properties = new GameProperties();
        properties.setFieldWith(9);
        properties.setFieldHeight(7);
        properties.setMineCount(6);
        return properties;
    }
}
