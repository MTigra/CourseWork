package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoContoller  extends StackPane {

    @FXML
    private Button hideStatus;


    @FXML
    private StackPane stackPane;

    @FXML
    private Label depth;

    @FXML
    private Label width;

    @FXML
    private Label nodeCount;

    @FXML
    private HBox headerArea;

    @FXML
    private Label sampleTitle;

    @FXML
    private Label faces;

    @FXML
    private HBox content;

    @FXML
    private Label timeToBuild;

    @FXML
    private Label height;

    @FXML
    private Label points;


    public InfoContoller(){
        super();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/org/fxyz3d/client/ModelInfo.fxml"));
            loader.setController(InfoContoller.this);
            loader.setRoot(InfoContoller.this);

            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
