package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuaternionGridPane extends GridPane implements Initializable {
    @FXML
    private TextField qtX;

    @FXML
    private TextField qtW;

    @FXML
    private TextField qtZ;

    @FXML
    private TextField qtY;

    @FXML
    private GridPane gridpane;

    public QuaternionGridPane() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/QuaternionGridPane.fxml"));
            loader.setController(QuaternionGridPane.this);
            loader.setRoot(QuaternionGridPane.this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

///////////////////////////////////////////////////
    //////                                       //////
    //////                *Getters*              //////
    //////                                       //////
    ///////////////////////////////////////////////////

    public TextField getQtX() {
        return qtX;
    }

    public TextField getQtW() {
        return qtW;
    }

    public TextField getQtZ() {
        return qtZ;
    }

    public TextField getQtY() {
        return qtY;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setInfo(Quaternion qt) {
        qtX.setText(String.valueOf(qt.getX()));
        qtY.setText(String.valueOf(qt.getY()));
        qtZ.setText(String.valueOf(qt.getZ()));
        qtW.setText(String.valueOf(qt.getW()));
    }
}
