package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;


public class XYZAxis extends Group {
    Cylinder CX = new  Cylinder(2,25);
    Cylinder CY = new  Cylinder(2,25);
    Cylinder CZ = new  Cylinder(2,25);
    Sphere S = new Sphere(4);
    MeshView yCone;
    MeshView xCone;
    MeshView zCone;


    public XYZAxis() {
        super();
        setMaterials();
        positioning();

        getChildren().addAll(CX,CY,CZ,S);

        getChildren().addAll(xCone,yCone,zCone);
    }

    private void setMaterials(){
        Material mat =new PhongMaterial(Color.WHITE);
        PhongMaterial Xmat = new PhongMaterial();
        Xmat.setDiffuseColor(Color.RED);
        PhongMaterial Ymat = new PhongMaterial();
        Ymat.setDiffuseColor(Color.GREEN);
        PhongMaterial Zmat = new PhongMaterial();
        Zmat.setDiffuseColor(Color.BLUE);

        S.setMaterial(Zmat);
        CY.setMaterial(mat);
        CX.setMaterial(mat);
        CZ.setMaterial(mat);

        TriangleMesh coneMeshY = createCone(3.5f, 7.5f);
        TriangleMesh coneMeshX = createCone(3.5f, 7.5f);
        TriangleMesh coneMeshZ = createCone(3.5f, 7.5f);
        yCone = new MeshView(coneMeshY);
        xCone = new MeshView(coneMeshX);
        zCone = new MeshView(coneMeshZ);

        yCone.setMaterial(Ymat);
        xCone.setMaterial(Xmat);
        zCone.setMaterial(Zmat);
        yCone.setDrawMode(DrawMode.FILL);
        xCone.setDrawMode(DrawMode.FILL);
        zCone.setDrawMode(DrawMode.FILL);

    }

    private void positioning(){
        CY.setTranslateY(-12.5);

        CX.setTranslateX(15);
        CX.setRotationAxis(Rotate.Z_AXIS);

        CX.setRotate(90);


        CZ.setRotationAxis(Rotate.X_AXIS);
        CZ.setRotate(90);
        CZ.setTranslateZ(-12.5);

        yCone.setTranslateY(-32.5);

        xCone.setTranslateY(-3.75);
        xCone.setRotationAxis(Rotate.Z_AXIS);
        xCone.setRotate(90);
        xCone.setTranslateX(28.5);

        zCone.setRotationAxis(Rotate.X_AXIS);
        zCone.setTranslateY(-3.75);
        zCone.setRotate(90);
        zCone.setTranslateZ(-28.5);
    }

    private TriangleMesh createCone( float radius, float height) {
        int divisions=500;
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(0,0,0);
        double segment_angle = 2.0 * Math.PI / divisions;
        float x, z;
        double angle;
        double halfCount = (Math.PI / 2 - Math.PI / (divisions / 2));
        for(int i=divisions+1;--i >= 0; ) {
            angle = segment_angle * i;
            x = (float)(radius * Math.cos(angle - halfCount));
            z = (float)(radius * Math.sin(angle - halfCount));
            mesh.getPoints().addAll(x,height,z);
        }
        mesh.getPoints().addAll(0,height,0);


        mesh.getTexCoords().addAll(0,0);

        for(int i=1;i<=divisions;i++) {
            mesh.getFaces().addAll(
                    0,0,i+1,0,i,0,           //COunter clock wise
                    divisions+2,0,i,0,i+1,0   // Clock wise
            );
        }
        return mesh;
    }
}
