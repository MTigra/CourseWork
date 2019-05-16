package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Cylinder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AxisGridPane extends GridPane implements Initializable {
    @FXML
    public TextField axisX;

    @FXML
    public TextField axisY;

    @FXML
    public TextField axisZ;

    @FXML
    private GridPane gridpane;

    private P2PCylinder c;

    public TextField getAxisX() {
        return axisX;
    }

    public TextField getAxisY() {
        return axisY;
    }

    public TextField getAxisZ() {
        return axisZ;
    }

    public AxisGridPane() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AxisGridPane.fxml"));
            loader.setController(AxisGridPane.this);
            loader.setRoot(AxisGridPane.this);
            loader.load();

            axisX.textProperty().addListener((observable, oldValue, newValue) -> {

                double x = Double.parseDouble(newValue);
                double y = c.target.getY();
                double z = c.target.getZ();
                c.createConnection(new Point3D(0,0,0),new Point3D(x,y,z));
                this.setInfo(c.target);
            });

            axisY.textProperty().addListener((observable, oldValue, newValue) -> {

                double x = c.target.getX();
                double y = Double.parseDouble(newValue);
                double z = c.target.getZ();

                c.createConnection(new Point3D(0,0,0),new Point3D(x,y,z));
                this.setInfo(c.target);
            });

            axisZ.textProperty().addListener((observable, oldValue, newValue) -> {
                double x = c.target.getX();
                double y = c.target.getY();
                double z = Double.parseDouble(newValue);

                c.createConnection(new Point3D(0,0,0),new Point3D(x,y,z));
                this.setInfo(c.target);
            });

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setInfo(Point3D p){

        axisX.setText(String.valueOf(p.getX()));
        axisY.setText(String.valueOf(p.getY()));
        axisZ.setText(String.valueOf(p.getZ()));
    }

    public void setC(P2PCylinder c){
        this.c = c;
        setInfo(c.target);

    }



}
