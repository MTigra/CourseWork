package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
<<<<<<< HEAD
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
=======
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
>>>>>>> develop
import javafx.stage.Stage;

public class Main extends Application {

    MainWindowController mainWindowController;
<<<<<<< HEAD
=======

>>>>>>> develop
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        loader.setController(new MainWindowController());
        VBox mainWindow = loader.load();
        mainWindowController = loader.getController();

        primaryStage.setTitle("JavaFx 3D Quaternions");
        Scene scene = new Scene(mainWindow, 1100, 768);

<<<<<<< HEAD
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        loader.setController(new MainWindowController());
        VBox mainWindow = loader.load();
        mainWindowController = loader.getController();

        primaryStage.setTitle("JavaFx 3D Quaternions");
        Scene scene = new Scene(mainWindow, 1024, 768);


=======
>>>>>>> develop

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
