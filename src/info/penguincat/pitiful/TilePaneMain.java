package info.penguincat.pitiful;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Calendar;

/**
 *
 * @web http://zoranpavlovic.blogspot.com/
 */
public class TilePaneMain extends Application {

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TilePane");

        // Adding TilePane
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10, 10, 10, 10));
        tilePane.setVgap(4);
        tilePane.setHgap(4);

        final Button target = new Button("Button");


        tilePane.getChildren().add(target);
        for (int i = 0; i < 8; i++) {

            Button btn = new Button("Button");
            btn.setPrefSize(100, 50);
            tilePane.getChildren().add(btn);

        }

        // Adding TilePane to the scene
        Scene scene = new Scene(tilePane);
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Platform.runLater(() -> {
                            target.setPrefSize(Calendar.getInstance().get(Calendar.SECOND)*5, Calendar.getInstance().get(Calendar.SECOND)*5);
                        });
                    }

                }));
        timeline.play();
    }
}
