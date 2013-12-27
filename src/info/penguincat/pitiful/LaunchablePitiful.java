package info.penguincat.pitiful;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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


        /* add Node code */
        ListView<Text> testListView1 = new ListView<>();
        testListView1.getItems().addAll(new Text("test1"), new Text("test2"));

        ListView<Text> testListView2 = new ListView<>();
        testListView2.getItems().addAll(new Text("test3"), new Text("test4"));

        Pane testPane1 = new Pane();
        testPane1.setStyle("-fx-background-color:RED;");

        Pane testPane2 = new Pane();
        testPane2.setStyle("-fx-background-color:BLACK;");

        Pane testPane3 = new Pane();
        testPane3.setStyle("-fx-background-color:GREEN;");

        this.controller.addNode("testListView1", testListView1);
        this.controller.addNode("testListView2", testListView2);
        this.controller.addNode("testPane1", testPane1);
        this.controller.addNode("testPane2", testPane2);
        this.controller.addNode("testPane3", testPane3);



        stage.setScene(new Scene(view, 400, 400));
        stage.show();

    }

    public static void main(String... args){
        launch(args);
    }

}
