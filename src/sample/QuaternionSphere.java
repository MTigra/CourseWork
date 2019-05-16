package sample;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class QuaternionSphere extends Sphere {

    private static final double EPSILON = 1e-6;
    private ArrayList<Point3D> points = new ArrayList<>();
    private QuaternionTransition qrt;

    public Quaternion getState() {
        return state;
    }

    private Quaternion state = new Quaternion();

    public QuaternionSphere(double radius) {
        super(radius);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("/images/earth.jpeg"));
        setMaterial(material);
        AtomicInteger clicks = new AtomicInteger();
        setOnMousePressed(event -> {
            clicks.getAndIncrement();
            if(clicks.get()>=3){
                points.clear();;
                clicks.set(1);
            }
            points.add(event.getPickResult().getIntersectedPoint());
        });

        qrt = new QuaternionTransition(this);
        //qrt.setInterpolator(Interpolator.TANGENT());
        qrt.setOnFinished(event -> {
//            Quaternion newState = new Quaternion();
//            Point3D startPoint = new Point3D(0, 0, 0);
//            if (!points.isEmpty()) {
//                startPoint = points.get(0);
//                points.remove(0);
//            }
//            if (points.size() >= 2) {
//                qrt.setStart(state);
//                newState = Quaternion.fromRotationBetween(points.get(0), points.get(1));
//                qrt.setEnd(newState);
//               qrt.play();
//            }
//            state = newState;
        });
    }



    public void setDuration(Duration duration) {
        qrt.durationProperty().setValue(duration);
    }

    public void play() {
        qrt.setStart(state);
        state = Quaternion.fromRotationBetween(points.get(0),points.get(1));
        state.normalize();
        //rotateByQuaternion(state);
        qrt.setEnd(state);
        qrt.play();
    }

    public void rotateByQuaternion(Quaternion q){
        Matrix4x3 mat = new Matrix4x3();

        getParent().getTransforms().setAll(mat.rotateGeneric(q));

    }

}
