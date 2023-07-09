package joac.minesweeper.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CellValue extends Label {

    public CellValue(int danger) {
        setMaxWidth(40);
        setMaxHeight(40);
        setMinWidth(40);
        setMinHeight(40);
        setAlignment(Pos.CENTER);
        setFont(Font.font("Monaco", 16));
        setBorder(new Border(new BorderStroke(Color.rgb(220,220,220), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        switch (danger) {
            case 1, 2, 3, 4, 5, 6, 7, 8 -> setText(String.valueOf(danger));
            case 9 -> setText("✖");
        }

        switch (danger) {
            case 1 -> setStyle("-fx-text-fill: blue;");
            case 2 -> setStyle("-fx-text-fill: green;");
            case 3 -> setStyle("-fx-text-fill: red;");
            case 4 -> setStyle("-fx-text-fill: darkblue;");
            case 5, 6, 7, 8 -> setStyle("-fx-text-fill: brown;");
        }
    }
}
