package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class P2PCylinder extends Cylinder {

    private double anchorX = 0;
    private double anchorY = 0;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Z_AXIS);
    Point3D origin;
    Point3D target;

    P2PCylinder(){
        this(new Point3D(0,0,0),new Point3D(0,0,0));
    }

    P2PCylinder(Point3D origin, Point3D target) {
        super();
        this.origin=origin;
        this.target=target;
        createConnection(origin, target);

    }

    private void Rotations() {
        getTransforms().addAll(rotateX, rotateY);
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
        this.getTransforms().removeAll(this.getTransforms());
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
        this.origin=origin;
        this.target=target;
    }

}
