package sample;

import javafx.scene.transform.Affine;
import com.sun.javafx.geom.*;

public class Matrix4x3 extends Affine {

    public Matrix4x3 rotate(Quaternion quat) {
        double wp = quat.getW() * quat.getW(),
                xp = quat.getX() * quat.getX(),
                yp = quat.getY() * quat.getY(),
                zp = quat.getZ() * quat.getZ();
        double zw = quat.getZ() * quat.getW(), dzw = zw + zw;
        double xy = quat.getX() * quat.getY(), dxy = xy + xy;
        double xz = quat.getX() * quat.getZ(), dxz = xz + xz;
        double yw = quat.getY() * quat.getW(), dyw = yw + yw;
        double yz = quat.getY() * quat.getZ(), dyz = yz + yz;
        double xw = quat.getX() * quat.getW(), dxw = xw + xw;

        setMxx(wp + xp - zp - yp);
        setMxy(dxy + dzw);
        setMxz(dxz - dyw);
        setMyx(dxy - dzw);
        setMyy(yp - zp + wp - xp);
        setMyz(dyz + dxw);
        setMzx(dyw + dxz);
        setMzy(dyz - dxw);
        setMzz(zp - yp - xp + wp);
        setTx(0.0);
        setTy(0.0);
        setTz(0.0);
        return this;
    }

    public Matrix4x3 rotateGeneric(Quaternion quat) {
        double w2 = quat.getW() * quat.getW(), x2 = quat.getX() * quat.getX();
        double y2 = quat.getY() * quat.getY(), z2 = quat.getZ() * quat.getZ();
        double zw = quat.getZ() * quat.getW(), dzw = zw + zw, xy = quat.getX() * quat.getY(), dxy = xy + xy;
        double xz = quat.getX() * quat.getZ(), dxz = xz + xz, yw = quat.getY() * quat.getW(), dyw = yw + yw;
        double yz = quat.getY() * quat.getZ(), dyz = yz + yz, xw = quat.getX() * quat.getW(), dxw = xw + xw;
        double rm00 = w2 + x2 - z2 - y2, rm01 = dxy + dzw, rm02 = dxz - dyw;
        double rm10 = dxy - dzw, rm11 = y2 - z2 + w2 - x2, rm12 = dyz + dxw;
        double rm20 = dyw + dxz, rm21 = dyz - dxw, rm22 = z2 - y2 - x2 + w2;

        double nm00 = this.getMxx() * rm00 + this.getMyx() * rm01 + this.getMzx() * rm02;
        double nm01 = this.getMxy() * rm00 + this.getMyy() * rm01 + this.getMzy() * rm02;
        double nm02 = this.getMxz() * rm00 + this.getMyz() * rm01 + this.getMzz() * rm02;
        double nm10 = this.getMxx() * rm10 + this.getMyx() * rm11 + this.getMzx() * rm12;
        double nm11 = this.getMxy() * rm10 + this.getMyy() * rm11 + this.getMzy() * rm12;
        double nm12 = this.getMxz() * rm10 + this.getMyz() * rm11 + this.getMzz() * rm12;
        setMzx(this.getMxx() * rm20 + this.getMyx() * rm21 + this.getMzx() * rm22);
        setMzy(this.getMxy() * rm20 + this.getMyy() * rm21 + this.getMzy() * rm22);
        setMzz(this.getMxz() * rm20 + this.getMyz() * rm21 + this.getMzz() * rm22);
        setMxx(nm00);
        setMxy(nm01);
        setMxz(nm02);
        setMyx(nm10);
        setMyy(nm11);
        setMyz(nm12);
        return this;
    }


}
