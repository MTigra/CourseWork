package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoContoller  extends StackPane implements Initializable {

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
    public Label faces;

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
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Info.fxml"));
            loader.setController(InfoContoller.this);
            loader.setRoot(InfoContoller.this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    void hideButtonClicked(ActionEvent event) {
        stackPane.setOpacity(0);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content.setOnMouseEntered(event -> {
            if(stackPane.getOpacity()!=100)
                stackPane.setOpacity(100);
        });

    }
}
