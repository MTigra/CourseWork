package sample;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import com.sun.javafx.geom.Quat4f;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.*;

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


    @FXML
    private Slider degreeSlider;

    @FXML
    private Label degreeLabel;

    @FXML
    private TextField axisX;

    @FXML
    private TextField axisY;

    @FXML
    private TextField axisZ;

    private static final double ZOOM_SENSITIVITY = 0.2;

    private static final int ZOOM_MIN = 80;
    private static final int ZOOM_MAX = 2000;

    private PerspectiveCamera camera;
    Group g;
    Shape3D box;
    SubScene axisScene;
    private SubScene subScene;
    Cylinder c;
    Point3D end;
    ObjectProperty<Point3D> point3dprop = new SimpleObjectProperty<Point3D>(new Point3D(0,0,0));
    private double anchorX = 0;
    private double anchorY = 0;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private final IntegerProperty degreeProperty = new SimpleIntegerProperty(0);
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Translate zoom = new Translate(0, -16, -200);
    private boolean drawC=false;

    InfoContoller ic;
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
    Matrix4x3 mat = new Matrix4x3();


    private static double cos(double i) {
        return Math.cos(i * Math.PI / 180);
    }

    private static double sin(double i) {
        return Math.sin(i * Math.PI / 180);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ic = new InfoContoller();
        prepareSubscene();
        prepareAxisScene();
        prepareQuaternionSupport();


        subScene.widthProperty().bind(contentPane.widthProperty());
        subScene.heightProperty().bind(contentPane.heightProperty());
        ic.prefWidthProperty().bind(contentPane.widthProperty());

        contentPane.getChildren().add(subScene);
        contentPane.getChildren().add(ic);
        contentPane.setAlignment(ic, Pos.BOTTOM_CENTER);
        axisScene.toFront();

    }

    Point3D pointStart;

    private void prepareQuaternionSupport() {

        subScene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (!drawC) {
                if (event.isAltDown()) {
                    System.out.println(event.getPickResult());
                    pointStart = new Point3D(0,0,0);
                    System.out.println(pointStart);
                    drawC=true;

                }
            }
        });

        subScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.isAltDown()) {
                if (drawC) {
                 //   int idx = g.getChildren().indexOf(c);
                    System.out.println(camera.getRotationAxis());
                    System.out.println(camera.getTransforms());
                    Point3D subsceneCenter = new Point3D(subScene.getWidth()/2, subScene.getHeight()/2,subScene.getTranslateZ());

                    end = new Point3D(event.getSceneX()-subsceneCenter.getX(),event.getSceneY()-subsceneCenter.getY(),event.getZ());

                    System.out.println(end);
                    end = rotateY.transform(end);
                    System.out.println(end);
                    end = rotateX.transform(end);
                    point3dprop.setValue(end);

                    axisX.textProperty().setValue(String.valueOf(point3dprop.get().getX()));
                    axisY.textProperty().setValue(String.valueOf(point3dprop.get().getY()));
                    axisZ.textProperty().setValue(String.valueOf(point3dprop.get().getZ()));

                    axisX.textProperty().addListener((observable, oldValue, newValue) -> {
                        point3dprop.setValue(new Point3D(Double.parseDouble(newValue),point3dprop.get().getY(),point3dprop.get().getZ()));
                        axisX.setText(String.valueOf(point3dprop.get().getX()));

                        int idx = g.getChildren().indexOf(c);
                        c = new RotatingCylinder(pointStart, point3dprop.get());
                        if(idx!=-1)
                            g.getChildren().set(idx,c);
                        else g.getChildren().add(c);
                    });

                    axisY.textProperty().addListener((observable, oldValue, newValue) -> {
                        point3dprop.setValue(new Point3D(point3dprop.get().getX(),Double.parseDouble(newValue),point3dprop.get().getZ()));
                        axisX.setText(String.valueOf(point3dprop.get().getX()));

                        int idx = g.getChildren().indexOf(c);
                        c = new RotatingCylinder(pointStart, point3dprop.get());
                        if(idx!=-1)
                            g.getChildren().set(idx,c);
                        else g.getChildren().add(c);
                    });

                    axisZ.textProperty().addListener((observable, oldValue, newValue) -> {
                        point3dprop.setValue(new Point3D(point3dprop.get().getX(),point3dprop.get().getY(),Double.parseDouble(newValue)));
                        axisX.setText(String.valueOf(point3dprop.get().getX()));

                        int idx = g.getChildren().indexOf(c);
                        c = new RotatingCylinder(pointStart, point3dprop.get());
                        if(idx!=-1)
                            g.getChildren().set(idx,c);
                        else g.getChildren().add(c);
                    });

//                    System.out.println(end);
//                    c = new RotatingCylinder(pointStart, end);
//
//                    if(idx!=-1)
//                        g.getChildren().set(idx,c);
//                    else g.getChildren().add(c);
                }
            }
        });

        subScene.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            drawC = false;
        });

        //box.getTransforms().setAll(mat);
        //binding degree slider
        degreeSlider.valueProperty().addListener((obs, oldval, newVal) ->
                degreeSlider.setValue(newVal.intValue()));

        degreeProperty.bind(degreeSlider.valueProperty());
        degreeLabel.textProperty().bind(degreeSlider.valueProperty().asString());

        degreeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double alpha = newValue.doubleValue();
            double cos = cos(alpha/2);
            double sin= sin(alpha/2);
            Point3D norm = point3dprop.get().normalize();
            Matrix4x3 mat = new Matrix4x3();
            Quat4f qt = new Quat4f((float)( norm.getX()*sin), (float)(norm.getY()*sin),(float)(norm.getZ()*sin), (float)cos);
            box.getTransforms().setAll(mat.rotateGeneric(qt));
            ic.getX().setText(String.valueOf(qt.x));
            ic.getY().setText(String.valueOf(qt.y));
            ic.getZ().setText(String.valueOf(qt.z));
            ic.getW().setText(String.valueOf(qt.w));
        });



    }


    private void prepareSubscene() {
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
            Point2D texcoord = pr.getIntersectedTexCoord();
            PhongMaterial pm = (PhongMaterial) box.getMaterial();
            // pr.getIntersectedNode().getme
            subScene.requestFocus();
            System.out.println(pr);
            //System.out.println(me);
            anchorX = me.getSceneX();
            anchorY = me.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        subScene.setOnMouseDragged(event -> {
            if (!event.isAltDown()) {
                //  System.out.println(camera.getTransforms());
                angleX.set(((anchorAngleX - (anchorY - event.getSceneY())) % 360 + 540) % 360 - 180);
                angleY.set(((anchorAngleY + anchorX - event.getSceneX()) % 360 + 540) % 360 - 180);
            }
        });

        subScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                angleX.set(0);
                angleY.set(0);
                g.getChildren().remove(c);
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

    private void prepareTextures(){

    }

}

