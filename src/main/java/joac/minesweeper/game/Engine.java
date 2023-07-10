package joac.minesweeper.game;

public class Engine {

    public Game newGame(GameProperties properties) {
        return new Game(properties);
    }
}
