package joac.minesweeper.game;

public class Engine {

    public Game newGame() {
        return new Game(GameProperties.easy());
    }
}
