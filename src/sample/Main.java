package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    MainWindowController mainWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        loader.setController(new MainWindowController());
        VBox mainWindow = loader.load();
        mainWindowController = loader.getController();

        primaryStage.setTitle("JavaFx 3D Quaternions");
        Scene scene = new Scene(mainWindow, 1100, 768);


        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
