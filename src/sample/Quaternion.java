package sample;

import com.sun.javafx.geom.Quat4f;

public class Quaternion {

    public double x, y, z, w;

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

}