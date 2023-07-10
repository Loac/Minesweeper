package joac.minesweeper.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class TopGridPane extends GridPane {

    public TopGridPane() {
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(30);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(30);

        getColumnConstraints().addAll(column1, column2, column3);
        setPadding(new Insets(20, 20, 0, 20));
    }

    public void setState(StateLabel state) {
        add(state, 1, 0);
    }

    public void setTimer(TimerLabel timer) {
        add(timer, 2, 0);
    }
}
