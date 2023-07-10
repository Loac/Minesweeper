package joac.minesweeper.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class TimerLabel extends Label {

    public TimerLabel() {
        setMaxHeight(30);
        setMinHeight(30);
        setAlignment(Pos.BASELINE_RIGHT);
        setMaxWidth(400);
    }
}
