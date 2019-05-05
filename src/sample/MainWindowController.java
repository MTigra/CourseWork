package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Accordion accordion;

    @FXML
    private Pane southEastResize;

    @FXML
    private VBox MainWindow;

    @FXML
    private StackPane contentPane;

    @FXML
    private TitledPane titledPane1;

    @FXML
    private TitledPane titledPane2;

    @FXML
    private TitledPane titledPane3;

    private PerspectiveCamera camera;

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Accordion getAccordion() {
        return accordion;
    }

    public Pane getSouthEastResize() {
        return southEastResize;
    }

    public VBox getMainWindow() {
        return MainWindow;
    }

    public StackPane getContentPane() {
        return contentPane;
    }

    public TitledPane getTitledPane1() {
        return titledPane1;
    }

    public TitledPane getTitledPane2() {
        return titledPane2;
    }

    public TitledPane getTitledPane3() {
        return titledPane3;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InfoContoller ic = new InfoContoller();
        SubScene subScene = prepareSubscene();


        subScene.widthProperty().bind(contentPane.widthProperty());
        subScene.heightProperty().bind(contentPane.heightProperty());
        ic.prefWidthProperty().bind(contentPane.widthProperty());

        contentPane.getChildren().add(subScene);
        contentPane.getChildren().add(ic);
        contentPane.setAlignment(ic, Pos.BOTTOM_CENTER);

      }

    private SubScene prepareSubscene(){
        Sphere sphere = new Sphere();
        Group g = new Group(sphere);

        SubScene subScene = new SubScene(g,400,400,true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.TRANSPARENT);
        camera = prepareCamera();
        camera.setTranslateZ(-3 * Math.max(g.getBoundsInParent().getWidth(), g.getBoundsInParent().getHeight()));
        subScene.setCamera(camera);
        return subScene;
    }

    private PerspectiveCamera prepareCamera(){
        PerspectiveCamera cam =  new PerspectiveCamera(true);
        cam.setNearClip(0.1);
        cam.setFarClip(100000.0);
        cam.setTranslateZ(-50);
        cam.setVerticalFieldOfView(false);
        cam.setFieldOfView(42);
        return cam;
    }
}

