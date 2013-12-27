package info.penguincat.pitiful;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * GUI関係の処理を担う
 */
final public class PitifulController implements Initializable {
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private SplitPane rootSplitPane;

    private final ContextMenu popup = new ContextMenu();
    //private final ContextMenu splitPanePopup = new ContextMenu();
    //private final ContextMenu tabPanePopup = new ContextMenu();
    private final ContextMenu nodePopup = new ContextMenu();
    private final Menu pluginMenu = new Menu("plugins");
    private final Map<Node, AnchorPane> parentMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final MenuItem addSplitPane_Horizontal = new MenuItem("+ horizontal");
        final MenuItem addSplitPane_Vertical = new MenuItem("+ vertical");
        final MenuItem removeSplitPane = new MenuItem("- SplitPane");

        final MenuItem addTabPane = new MenuItem("+ TabPane");
        final MenuItem removeTabPane = new MenuItem("- TabPane");
        final MenuItem addTab = new MenuItem("+ Tab");
        final MenuItem removeTab = new MenuItem("- Tab");

        final MenuItem removeItem = new MenuItem("- Plugin");


        addSplitPane_Horizontal.setOnAction(e -> {
            System.out.println("+ horizontal");

            if(! (((MenuItem)e.getTarget()).getParentPopup().getOwnerNode() instanceof AnchorPane)){
                e.consume();
                return;
            }

            AnchorPane ownerPane = (AnchorPane) ((MenuItem) e.getTarget()).getParentPopup().getOwnerNode();
            SplitPane splitPane1 = new SplitPane();
            splitPane1.setOrientation(Orientation.HORIZONTAL);
            splitPane1.getItems().addAll(new AnchorPane(), new AnchorPane());
            addSplitPane(ownerPane, splitPane1);
            parentMap.put(splitPane1, ownerPane);

            e.consume();
        });
        addSplitPane_Vertical.setOnAction(e -> {
            System.out.println("+ vertical");

            if(! (((MenuItem)e.getTarget()).getParentPopup().getOwnerNode() instanceof AnchorPane)){
                e.consume();
                return;
            }

            AnchorPane ownerPane = (AnchorPane) ((MenuItem) e.getTarget()).getParentPopup().getOwnerNode();
            SplitPane splitPane1 = new SplitPane();
            splitPane1.setOrientation(Orientation.VERTICAL);
            splitPane1.getItems().addAll(new AnchorPane(), new AnchorPane());
            addSplitPane(ownerPane, splitPane1);
            parentMap.put(splitPane1, ownerPane);

            e.consume();
        });

        addTabPane.setOnAction(e -> {
            System.out.println("+ addTabPane");
            if(! (((MenuItem)e.getTarget()).getParentPopup().getOwnerNode() instanceof AnchorPane)){
                e.consume();
                return;
            }

            AnchorPane ownerPane = (AnchorPane) ((MenuItem) e.getTarget()).getParentPopup().getOwnerNode();
            addTabPane(ownerPane);

            e.consume();
        });

        addTab.setOnAction(e ->{
            System.out.println("+ addTab");
            Parent parent = ((MenuItem)e.getTarget()).getParentPopup().getOwnerNode().getParent();
            if(parent == null || !(parent.getParent() instanceof TabPane)){
                e.consume();
                return;
            }

            TabPane ownerTabPane =  (TabPane)parent.getParent();
            this.addTab(ownerTabPane);

            e.consume();
        });

        removeTab.setOnAction(e -> {
            System.out.println("- removeTab");
            Parent parent = ((MenuItem)e.getTarget()).getParentPopup().getOwnerNode().getParent();
            if(parent == null || !(parent.getParent() instanceof TabPane)){
                e.consume();
                return;
            }

            TabPane ownerTabPane =  (TabPane)parent.getParent();
            ownerTabPane.getTabs().remove(ownerTabPane.getSelectionModel().getSelectedItem());

            if(ownerTabPane.getTabs().size() == 0){
                parentMap.get(ownerTabPane).getChildren().remove(ownerTabPane);
            }

            e.consume();
        });

        removeItem.setOnAction(e -> {
            System.out.println("- remove");
            if(! (((MenuItem)e.getTarget()).getParentPopup().getOwnerNode() instanceof Node)){
                e.consume();
                return;
            }

            Node node = ((MenuItem) e.getTarget()).getParentPopup().getOwnerNode();
            AnchorPane parent = parentMap.get(node);
            if (parent != null) {
                parent.getChildren().remove(node);
            }
            e.consume();

        });

        removeSplitPane.setOnAction(e -> {
            System.out.println("- removeSplitPane");
            if(! (((MenuItem)e.getTarget()).getParentPopup().getOwnerNode() instanceof Node)){
                e.consume();
                return;
            }

            Node node = ((MenuItem) e.getTarget()).getParentPopup().getOwnerNode();
            Node removeTarget = node.getParent().getParent();
            AnchorPane parent = parentMap.get(removeTarget);
            if (parent != null) {
                parent.getChildren().remove(removeTarget);
            }
            e.consume();
        });


        this.popup.getItems().addAll(addSplitPane_Horizontal, addSplitPane_Vertical, removeSplitPane, addTabPane, addTab, removeTab, pluginMenu);
        //this.splitPanePopup.getItems().addAll(addSplitPane_Horizontal, addSplitPane_Vertical, removeSplitPane, addTabPane, pluginMenu);
        //this.tabPanePopup.getItems().addAll(addTab, removeTab);
        this.nodePopup.getItems().add(removeItem);

        // ownerNodeがnullになる
        //rootSplitPane.setContextMenu(splitPanePopup);

        this.rootAnchorPane.getChildren().clear();
        rootAnchorPane.setOnMouseClicked(event -> {
            // 右クリック時にポップアップメニューを表示
            if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("rootAnchorPane#onMouseClick");
                //splitPanePopup.show(rootAnchorPane, event.getScreenX(), event.getScreenY());
                popup.show(rootAnchorPane, event.getScreenX(), event.getScreenY());
            } else {
                //splitPanePopup.hide();
                popup.hide();
            }
            event.consume();
        });

    }

    public void addNode(String name, Node node) {
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        node.getStyleClass().add("rounded");
        node.setOnMouseClicked(event -> {
            // 右クリック時にポップアップメニューを表示
            if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("graphic#onMouseClick");
                nodePopup.show(node, event.getScreenX(), event.getScreenY());
            } else {
                nodePopup.hide();
            }
            event.consume();
        });

        final MenuItem add = new MenuItem("+ " + name);
        add.setOnAction(e -> {
            System.out.println("+ " + node.toString());

            AnchorPane ownerPane = (AnchorPane) ((MenuItem) e.getTarget()).getParentMenu().getParentPopup().getOwnerNode();
            parentMap.put(node, ownerPane);
            ownerPane.getChildren().add(node);
            e.consume();
        });
        pluginMenu.getItems().add(add);

    }


    private void addSplitPane(AnchorPane ownerAnchorPane, SplitPane splitPane) {
        //Platform.execute(() -> {
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        //});

        splitPane.setId("hiddenSplitter");
        splitPane.getStylesheets().add(PitifulController.class.getResource("HiddenSplitPane.css").toExternalForm());
        splitPane.getItems().forEach(n -> {
            n.getStyleClass().add("rounded");
            n.setOnMouseClicked(event -> {
                // 右クリック時にポップアップメニューを表示
                if (event.getButton() == MouseButton.SECONDARY) {
                    //this.splitPanePopup.show(n, event.getScreenX(), event.getScreenY());
                    this.popup.show(n, event.getScreenX(), event.getScreenY());
                } else {
                    //this.splitPanePopup.hide();
                    this.popup.hide();
                }

                event.consume();
            });
        });
        ownerAnchorPane.getChildren().addAll(splitPane);
    }

    private void addTabPane(AnchorPane ownerAnchorPane) {
        TabPane tabPane = new TabPane();
        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);

        Tab tab1 = this.createEditableTab("tab");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setOnMouseClicked(event ->{
            // 右クリック時にポップアップメニューを表示
            if (event.getButton() == MouseButton.SECONDARY) {
                //this.tabPanePopup.show(t.getContent(), event.getScreenX(), event.getScreenY());
                this.popup.show(anchorPane, event.getScreenX(), event.getScreenY());
            } else {
                //this.tabPanePopup.hide();
                this.popup.hide();
            }

            event.consume();
        });
        tab1.setContent(anchorPane);
        tab1.setClosable(false);

        tabPane.getTabs().add(tab1);

        ownerAnchorPane.getChildren().add(tabPane);

        parentMap.put(tabPane, ownerAnchorPane);
    }

    private void addTab(TabPane ownerTabPane){
        Tab tab = this.createEditableTab("Tab");
        tab.setClosable(false);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setOnMouseClicked(event ->{
            // 右クリック時にポップアップメニューを表示
            if (event.getButton() == MouseButton.SECONDARY) {
                //this.tabPanePopup.show(anchorPane, event.getScreenX(), event.getScreenY());
                this.popup.show(anchorPane, event.getScreenX(), event.getScreenY());
            } else {
                //this.tabPanePopup.hide();
                this.popup.hide();
            }

            event.consume();
        });

        tab.setContent(anchorPane);
        ownerTabPane.getTabs().add(tab);
    }

    public Tab createEditableTab(String text) {
        final Label label = new Label(text);
        final Tab tab = new Tab();
        tab.setGraphic(label);
        final TextField textField = new TextField();
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2) {
                    textField.setText(label.getText());
                    tab.setGraphic(textField);
                    textField.selectAll();
                    textField.requestFocus();
                }
            }
        });


        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                label.setText(textField.getText());
                tab.setGraphic(label);
            }
        });


        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean oldValue, Boolean newValue) {
                if (! newValue) {
                    label.setText(textField.getText());
                    tab.setGraphic(label);
                }
            }
        });
        return tab ;
    }


        /*
    public void add(final Node node) {
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);

        node.setOnMouseClicked(event -> {
            // 右クリック時にポップアップメニューを表示
            if (event.getButton() == MouseButton.SECONDARY) {
                this.nodePopup.show(node, event.getScreenX(), event.getScreenY());
            } else {
                this.nodePopup.hide();
            }

        });

        this.rootAnchorPane.getChildren().add(node);

        node.requestFocus();
    }
        */

    /*
    public void remove(final Node node) {
        Platform.execute(() -> {
            this.rootSplitPane.getItems().remove(node);
            if (this.rootSplitPane.getItems().size() > 0) {
                this.rootSplitPane.getItems().get(0).requestFocus();
            }
        });
    }
    */

    /*
    public void add(final Node node) {
        Platform.execute(() -> {
            if (!this.rootSplitPane.getItems().contains(node)) {
                this.rootSplitPane.getItems().add(node);
                node.requestFocus();
            }
        });
    }

    /*
    public void remove(final Node node) {
        Platform.execute(() -> {
            this.rootSplitPane.getItems().remove(node);
            if (this.rootSplitPane.getItems().size() > 0) {
                this.rootSplitPane.getItems().get(0).requestFocus();
            }
        });
    }
    */

    public void shutdown() {
        this.rootAnchorPane.getChildren().clear();
    }

}
