package info.penguincat.pitiful;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX上でタイル型WMをシミュレートする
 */

final public class Pitiful {
    private final Stage primaryStage;
    private final String name;

    private PitifulController controller;

    public Pitiful(Stage stage, String name) {
        this.primaryStage = stage;
        this.name = name;
    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pitiful.fxml"));
        Parent view = loader.load();
        this.controller = loader.getController();

        Platform.runLater(() -> {
            this.primaryStage.setScene(new Scene(view, 800, 800));
            this.primaryStage.setTitle(this.name + " on Pitiful");
            this.primaryStage.show();
        });
    }

    public void add(String name, Node node){
        this.controller.addNode(name, node);
    }
    /*
    public void remove(Node node){
        this.controller.remove(node);
    }
    */

    public void shutdown(){
        this.controller.shutdown();
    }


}
