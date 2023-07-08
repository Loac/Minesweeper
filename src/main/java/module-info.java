module joac.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens joac.minesweeper to javafx.fxml;
    exports joac.minesweeper;
}
