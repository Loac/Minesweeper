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
        properties.setFieldWith(8);
        properties.setFieldHeight(6);
        properties.setMineCount(8);
        return properties;
    }

    public static GameProperties medium() {
        GameProperties properties = new GameProperties();
        properties.setFieldWith(12);
        properties.setFieldHeight(8);
        properties.setMineCount(14);
        return properties;
    }

    public static GameProperties hard() {
        GameProperties properties = new GameProperties();
        properties.setFieldWith(16);
        properties.setFieldHeight(12);
        properties.setMineCount(24);
        return properties;
    }
}
