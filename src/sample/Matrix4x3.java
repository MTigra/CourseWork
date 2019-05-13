package sample;/*
 * The MIT License
 *
 * Copyright 2018 Vitor Mac.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import javafx.scene.transform.Affine;
import com.sun.javafx.geom.*;

public class Matrix4x3 extends Affine {

    public Matrix4x3 rotate(Quaternion quat) {
        double wp = quat.w * quat.w,
                xp = quat.x * quat.x,
                yp = quat.y * quat.y,
                zp = quat.z * quat.z;
        double zw = quat.z * quat.w, dzw = zw + zw;
        double xy = quat.x * quat.y, dxy = xy + xy;
        double xz = quat.x * quat.z, dxz = xz + xz;
        double yw = quat.y * quat.w, dyw = yw + yw;
        double yz = quat.y * quat.z, dyz = yz + yz;
        double xw = quat.x * quat.w, dxw = xw + xw;

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

    public Matrix4x3 rotateGeneric(Quat4f quat) {
        double w2 = quat.w * quat.w, x2 = quat.x * quat.x;
        double y2 = quat.y * quat.y, z2 = quat.z * quat.z;
        double zw = quat.z * quat.w, dzw = zw + zw, xy = quat.x * quat.y, dxy = xy + xy;
        double xz = quat.x * quat.z, dxz = xz + xz, yw = quat.y * quat.w, dyw = yw + yw;
        double yz = quat.y * quat.z, dyz = yz + yz, xw = quat.x * quat.w, dxw = xw + xw;
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
