package sample;


import javafx.geometry.Point3D;

public class Quaternion {
    private static final double EPSILON = 1e-6;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }

    private double x, y, z, w;

    private static double cos(double i) {
        return Math.cos(i * Math.PI / 180);
    }

    private static double sin(double i) {
        return Math.sin(i * Math.PI / 180);
    }

    public Quaternion() {
        x = 0;
        y = 0;
        z = 0;
        w = 1;
    }

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quaternion mul(Quaternion src) {
        return this.set(w * src.x + x * src.w + y * src.z - z * src.y, w * src.y - x * src.z + y * src.w + z * src.x, w * src.z + x * src.y - y * src.x + z * src.w, w * src.w - x * src.x - y * src.y - z * src.z);
    }

    public double dot(Quaternion src) {
        return w * src.w + x * src.x + y * src.y + z * src.z;
    }

    public static Quaternion fromRotationBetween(Point3D a, Point3D b) {
        a = a.normalize();
        b = b.normalize();
        double dot = a.dotProduct(b);
        double dotError = 1.0 - Math.abs(clamp(dot, -1d, 1f));
        Point3D tmp = new Point3D(0, 0, 0);
        if (dotError <= EPSILON) {
            if (dot < 0.0) {
                tmp = new Point3D(1, 0, 0).crossProduct(a);
                if (tmp.magnitude() < EPSILON) {
                    tmp = new Point3D(0, 1, 0).crossProduct(a);
                }
                tmp.normalize();

                if (tmp.magnitude() == 0.0)
                    return new Quaternion(0, 0, 0, 1);
                double angle = 180;
                return new Quaternion(tmp.getX() * sin(angle / 2), tmp.getY() * sin(angle / 2), tmp.getZ() * sin(angle / 2), cos(angle / 2));
            } else {
                return new Quaternion(0, 0, 0, 1);
            }
        }


        tmp = a.crossProduct(b);
        return new Quaternion(tmp.getX(), tmp.getY(), tmp.getZ(), 1 + dot);
    }

    public static double clamp(double value, double min, double max) {
        return value < min ? min : value > max ? max : value;
    }

    public void normalize() {
        double norm = (x * x + y * y + z * z + w * w);

        if (norm > 0.0) {
            norm = 1.0 / Math.sqrt(norm);
            this.x *= norm;
            this.y *= norm;
            this.z *= norm;
            this.w *= norm;
        } else {
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            this.w = 0.0;
        }
    }
}