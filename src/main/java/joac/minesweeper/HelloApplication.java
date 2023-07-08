package joac.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import joac.minesweeper.game.Field;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Group group = new Group();

        Field field = new Field(7, 5);

        field.plantMines(4);

        field.calcCells();
        field.printCells();


        // Arrays.stream(arr).forEach(item ->
        //         System.out.printf("%s: %s x %s%n", item, item % 4, item / 4)
        // );

        Label label = new Label();
        label.setText("3");
        label.setMaxWidth(40);
        label.setMaxHeight(40);
        label.setMinWidth(40);
        label.setMinHeight(40);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Monaco", 16));
        label.setBorder(new Border(new BorderStroke(Color.rgb(0,0,0), BorderStrokeStyle.SOLID, null, new BorderWidths(0.2))));

        Button button = new Button();
        button.setMaxWidth(40);
        button.setMaxHeight(40);
        button.setMinWidth(40);
        button.setMinHeight(40);
        button.setOnMouseClicked(e -> {
            System.out.println(e.getButton());
            if (e.getButton().equals(MouseButton.PRIMARY))
                button.setVisible(false);
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                button.setDisable(true);
                button.setText("X");
                button.setOpacity(1);
            }
        });


        group.getChildren().add(label);
        group.getChildren().add(button);

        Scene scene = new Scene(group, 320, 240);
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
