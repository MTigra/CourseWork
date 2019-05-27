package sample;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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
    private VBox quaternionVbox;
    @FXML
    private VBox axisVbox;
    @FXML
    private MenuItem cubeMenuButton;
    @FXML
    private MenuItem sphereMenuButton;
    @FXML
    private HBox sliderHbox;

    private static final double ZOOM_SENSITIVITY = 0.2;
    private static final int ZOOM_MIN = 80;
    private static final int ZOOM_MAX = 2000;

    private PerspectiveCamera camera;
    Group g;
    Shape3D box;
    private SubScene axisScene;
    private SubScene subScene;
    P2PCylinder c;
    Point3D end;
    QuaternionSphere sphere;
    ObjectProperty<Point3D> point3dprop = new SimpleObjectProperty<Point3D>(new Point3D(0, 0, 0));
    private double anchorX = 0;
    private double anchorY = 0;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private final IntegerProperty degreeProperty = new SimpleIntegerProperty(0);
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Translate zoom = new Translate(0, 0, -200);
    private boolean drawC = false;
    QuaternionGridPane quaternionGridPane;
    AxisGridPane axisGridPane;



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

    Matrix4x3 mat = new Matrix4x3();


    private static double cos(double i) {
        return Math.cos(i * Math.PI / 180);
    }

    private static double sin(double i) {
        return Math.sin(i * Math.PI / 180);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


//        quaternionGridPane.prefWidthProperty().bind(contentPane.widthProperty());

    }

    Point3D pointStart;

    private void prepareQuaternionSupport(ProjectType type) {
        c = new P2PCylinder();
        axisGridPane.setC(c);
        subScene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (!drawC) {
                if (event.isAltDown()) {
                    System.out.println(event.getPickResult());
                    pointStart = new Point3D(0, 0, 0);
                    System.out.println(pointStart);
                    drawC = true;

                }
            }
        });

        subScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.isAltDown()) {
                if (drawC) {
                    int idx = g.getChildren().indexOf(c);
                    System.out.println(camera.getRotationAxis());
                    System.out.println(camera.getTransforms());
                    Point3D subsceneCenter = new Point3D(subScene.getWidth() / 2, subScene.getHeight() / 2, subScene.getTranslateZ());

                    end = new Point3D(event.getSceneX() - subsceneCenter.getX(), event.getSceneY() - subsceneCenter.getY(), event.getZ());

                    System.out.println(end);
                    end = rotateY.transform(end);
                    System.out.println(end);
                    end = rotateX.transform(end);
                    point3dprop.setValue(end);

                    System.out.println(end);
                    c = new P2PCylinder(pointStart, end);
                    axisGridPane.setC(c);
                    if (idx != -1)
                        g.getChildren().set(idx, c);
                    else g.getChildren().add(c);
                }
            }
        });

        subScene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            drawC = false;
        });

        if (type == ProjectType.CUBE) {
            box.getTransforms().setAll(mat);
            //    binding degree slider
            Slider degreeSlider = new Slider(0, 360, 0);
            Label degreeLabel = new Label(null);
            sliderHbox.getChildren().removeAll(sliderHbox.getChildren());
            sliderHbox.getChildren().addAll(degreeSlider, degreeLabel);

            degreeSlider.valueProperty().addListener((obs, oldval, newVal) ->
                    degreeSlider.setValue(newVal.intValue()));

            degreeProperty.bind(degreeSlider.valueProperty());
            degreeLabel.textProperty().bind(degreeSlider.valueProperty().asString());

            degreeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                double alpha = newValue.doubleValue();
                double cos = cos(alpha / 2);
                double sin = sin(alpha / 2);
                Point3D norm = point3dprop.get().normalize();
                Matrix4x3 mat = new Matrix4x3();
                Quaternion qt = new Quaternion((norm.getX() * sin), (norm.getY() * sin), (norm.getZ() * sin), cos);
                box.getTransforms().setAll(mat.rotate(qt));
                quaternionGridPane.setInfo(qt);
            });
        }


    }


    private void prepareSubscene(ProjectType type) {
        if (type == ProjectType.CUBE) {
            g = new Group();
            box = new Box(100, 100, 100);
            box.setDrawMode(DrawMode.FILL);
            PhongMaterial material = new PhongMaterial();

            material.setDiffuseMap(new Image("/diamond.jpg"));
            box.setMaterial(material);
            box.setPickOnBounds(true);
            g.getChildren().add(box);


            subScene = new SubScene(g, 400, 400, true, SceneAntialiasing.BALANCED);
            subScene.setFill(Color.TRANSPARENT);
            prepareCamera();
            addCameraRotations();
            subScene.setCamera(camera);
        }
        if (type == ProjectType.SPHERE) {
            g = new Group();
            sphere = new QuaternionSphere(40);
            sphere.setDrawMode(DrawMode.FILL);
            Group sphereGroup = new Group();
            sphereGroup.getChildren().add(sphere);
            g.getChildren().add(sphereGroup);
            AtomicInteger clicks = new AtomicInteger();
            ArrayList<Node> sphereChildren = new ArrayList<>();
            ArrayList<Node> groupChildren = new ArrayList<>();
            g.setOnMousePressed(event -> {
                clicks.getAndIncrement();
                if(clicks.get()>=3){
                    sphereGroup.getChildren().removeAll(sphereChildren);
                    g.getChildren().removeAll(groupChildren);
                    clicks.set(1);
                }
                Sphere s = new Sphere(2);
                Point3D point = event.getPickResult().getIntersectedPoint();
                s.setTranslateX(point.getX());
                s.setTranslateY(point.getY());
                s.setTranslateZ(point.getZ());
                if(clicks.get()<2){
                    groupChildren.add(s);
                    g.getChildren().add(s);
                }else {
                    sphereChildren.add(s);
                    sphereGroup.getChildren().add(s);
                }

                System.out.println("Picked placed");
            });


            subScene = new SubScene(g, 400, 400, true, SceneAntialiasing.BALANCED);
            subScene.setFill(Color.TRANSPARENT);
            prepareCamera();
            addCameraRotations();
            subScene.setCamera(camera);
        }

    }

    private void prepareAxisScene() {
        Group axis = buildAxes();
        axisScene = new SubScene(axis, 200, 200, true, SceneAntialiasing.BALANCED);
        PerspectiveCamera axisCamera = new PerspectiveCamera(true);
        axisCamera.setNearClip(0.1);
        axisCamera.setFarClip(100000.0);
        axisCamera.setTranslateZ(-330);
        axisCamera.setVerticalFieldOfView(false);
        axisScene.setCamera(axisCamera);
        axis.getTransforms().addAll(rotateX, rotateY);
        contentPane.getChildren().add(axisScene);
        contentPane.setAlignment(axisScene, Pos.TOP_RIGHT);
    }

    private void prepareCamera() {
        camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(100000.0);
        camera.setVerticalFieldOfView(false);

        //camera.setFieldOfView(42);
    }


    private void addCameraRotations() {
        camera.getTransforms().addAll(rotateX, rotateY, zoom);
        rotateX.angleProperty().bind(angleX);
        rotateY.angleProperty().bind(angleY);

        subScene.setOnMousePressed(me -> {
            PickResult pr = me.getPickResult();
            subScene.requestFocus();
            System.out.println(pr);
            anchorX = me.getSceneX();
            anchorY = me.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        subScene.setOnMouseDragged(event -> {
            if (!event.isAltDown()) {
                angleX.set(((anchorAngleX - (anchorY - event.getSceneY())) % 360 + 540) % 360 - 180);
                angleY.set(((anchorAngleY + anchorX - event.getSceneX()) % 360 + 540) % 360 - 180);
            }
        });

        subScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                angleX.set(0);
                angleY.set(0);
                //g.getChildren().remove(c);
                drawC = false;
            }
        });
        subScene.setOnScroll(event -> zoomCameraBy(event.getDeltaY()));

    }

    private void zoomCameraBy(double diff) {
        if (diff == 0)
            return;
        if (-(zoom.getZ() + diff) >= ZOOM_MIN && -(zoom.getZ() + diff) <= ZOOM_MAX)
            zoom.setZ(zoom.getZ() + diff * ZOOM_SENSITIVITY);

    }


    private Group buildAxes() {
        Group axisGroup = new XYZAxis();
        System.out.println("buildAxes()");
        return axisGroup;
    }

    private void prepareTextures() {

    }

    @FXML
    void onCubeMenuButtonClicked(ActionEvent event) {
        newProject();
        axisGridPane = new AxisGridPane();
        quaternionGridPane = new QuaternionGridPane();
        prepareSubscene(ProjectType.CUBE);

        prepareAxisScene();
        prepareQuaternionSupport(ProjectType.CUBE);


        contentPane.getChildren().add(subScene);
        axisVbox.getChildren().add(axisGridPane);
        quaternionVbox.getChildren().add(quaternionGridPane);

        subScene.widthProperty().bind(contentPane.widthProperty());
        subScene.heightProperty().bind(contentPane.heightProperty());
        subScene.setManaged(false);
        axisScene.toFront();
    }

    private void newProject() {

        contentPane.getChildren().removeAll(contentPane.getChildren());
        axisVbox.getChildren().removeAll(axisVbox.getChildren());
        quaternionVbox.getChildren().removeAll(quaternionVbox.getChildren());
        angleX.set(0);
        angleY.set(0);

    }

    @FXML
    void onSphereMenuButtonClicked(ActionEvent event) {
        newProject();
        prepareSubscene(ProjectType.SPHERE);
        prepareAxisScene();
        // prepareQuaternionSupport();
        quaternionGridPane = new QuaternionGridPane();

        Slider durationSlider = new Slider(1, 15, 3);
        Label durationLabel = new Label(null);
        sliderHbox.getChildren().removeAll(sliderHbox.getChildren());
        sliderHbox.getChildren().addAll(durationSlider, durationLabel);

        durationSlider.valueProperty().addListener((obs, oldval, newVal) ->
                durationSlider.setValue(newVal.intValue()));


        durationLabel.textProperty().bind(durationSlider.valueProperty().asString());

        contentPane.getChildren().add(subScene);
        Button btn = new Button("Play");
        btn.setOnMouseClicked(event1 -> {
            sphere.setDuration(Duration.seconds(durationSlider.getValue()));
            sphere.play();
            quaternionGridPane.setInfo(sphere.getState());
        });
        axisVbox.getChildren().add(btn);
        quaternionVbox.getChildren().add(quaternionGridPane);

        subScene.widthProperty().bind(contentPane.widthProperty());
        subScene.heightProperty().bind(contentPane.heightProperty());
        subScene.setManaged(false);
        axisScene.toFront();
    }


}

enum ProjectType {
    CUBE, SPHERE, IMPORT;
}
