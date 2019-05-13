package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.PickResult;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.net.URL;
import java.util.ResourceBundle;

public class RotatingCylinder extends Cylinder {

    private double anchorX = 0;
    private double anchorY = 0;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Z_AXIS);

    RotatingCylinder(){
        super(3,130);
       // Rotations();
    }

    RotatingCylinder(Point3D start, Point3D end){
        super();
//        this.setTranslateX(start.getX());
//        this.setTranslateY(start.getY());
//        this.setTranslateZ(start.getZ());
        createConnection(start,end);
        //Rotations();
    }

    private void Rotations(){
        getTransforms().addAll(rotateX,rotateY);
        rotateX.angleProperty().bind(angleX);
        rotateY.angleProperty().bind(angleY);
        System.out.println("KKFZDD");
        setOnMousePressed(event -> {
            System.out.println("Cylinder Dragged");
            //System.out.println(me);
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        setOnMouseDragged(event -> {
            angleX.set(((anchorAngleX - (anchorY - event.getSceneY())) % 360 + 540) % 360 - 180);
            angleY.set(((anchorAngleY + anchorX - event.getSceneX()) % 360 + 540) % 360 - 180);
        });
    }

    public void createConnection(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        //Cylinder line = new Cylinder(1, height);
        this.setHeight(height);

        this.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
    }

}
