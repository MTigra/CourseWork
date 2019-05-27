package sample;

import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.util.Duration;

public class QuaternionTransition extends Transition {

    QuaternionSphere sphere;
    private ObjectProperty<Duration> duration;
    private static final Duration DEFAULT_DURATION = Duration.millis(400);
    Quaternion start;
    Quaternion end;

    QuaternionTransition(QuaternionSphere sphere) {
        this.sphere = sphere;
    }

    @Override
    protected void interpolate(double frac) {
        double result = start.dot(end);

        if (result < 0.0f) {
            end.setX(-end.getX());
            end.setY(-end.getY());
            end.setZ(-end.getZ());
            end.setW(-end.getW());
            result = -result;
        }


        double scale0 = 1 - frac;
        double scale1 = frac;

        if ( (1 - result) >0.0) {
            double theta = Math.acos(result);
            double invSinTheta = 1 / Math.sin(theta);

            scale0 = Math.sin((1 - frac) * theta) * invSinTheta;
            scale1 = Math.sin((frac * theta)) * invSinTheta;
        }

        double x = (scale0 * start.getX()) + (scale1 * end.getX());
        double y = (scale0 * start.getY()) + (scale1 * end.getY());
        double z = (scale0 * start.getZ()) + (scale1 * end.getZ());
        double w = (scale0 * start.getW()) + (scale1 * end.getW());
        Quaternion q = new Quaternion(x,y,z,w);
        q.normalize();
        sphere.rotateByQuaternion(q);
    }

    public final Duration getDuration() {
        return (duration == null) ? DEFAULT_DURATION : duration.get();
    }

    public final ObjectProperty<Duration> durationProperty() {
        if (duration == null) {
            duration = new ObjectPropertyBase<Duration>(DEFAULT_DURATION) {

                @Override
                public void invalidated() {
                    try {
                        setCycleDuration(getDuration());
                    } catch (IllegalArgumentException e) {
                        if (isBound()) {
                            unbind();
                        }
                        set(getCycleDuration());
                        throw e;
                    }
                }

                @Override
                public Object getBean() {
                    return QuaternionTransition.this;
                }

                @Override
                public String getName() {
                    return "duration";
                }
            };
        }
        return duration;
    }

    public void setStart(Quaternion startQuaternion){
        start=startQuaternion;
    }

    public void setEnd(Quaternion endQuaternion){
        end=endQuaternion;
    }


}
