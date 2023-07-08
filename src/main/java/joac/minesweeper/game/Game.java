package joac.minesweeper.game;

public class Game {

    private final Field field;

    public Game(GameProperties properties) {
        field = new Field(properties.getFieldWith(), properties.getFieldHeight());
    }

    public Field getField() {
        return field;
    }
}
