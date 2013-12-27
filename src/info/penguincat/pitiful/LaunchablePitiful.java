package info.penguincat.pitiful;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author keisuke
 */
public class LaunchablePitiful extends Application{
    private PitifulController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pitiful.fxml"));
        Parent view = loader.load();
        this.controller = loader.getController();


        stage.setScene(new Scene(view, 400, 400));
        stage.show();

    }

    public static void main(String... args){
        launch(args);
    }

}
