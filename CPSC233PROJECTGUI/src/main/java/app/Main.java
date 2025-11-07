package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Main class serves as the entry point for the Fifa Stats Tracker application.
 * It handles command-line arguments, loads the FXML UI, and initializes the primary stage.
 * If a file is passed as an argument, it verifies the file's existence before launching the application.
 *
 * The application UI is defined in the 'project.fxml' file and is loaded into a 1000x1000 scene.
 * The class also stores the filename provided via command-line for later access.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */

public class Main extends Application {

    private static String saveFileName;

    @Override
    public void start(Stage stage) throws IOException {
        // Retrieve parameter object from launch
        Parameters parameters = getParameters();
        if (!parameters.getRaw().isEmpty()) {
            // Get the filename and check if file exists
            saveFileName = parameters.getRaw().getFirst();
            File saveFile = new File(saveFileName);
            if (!saveFile.exists()) {
                System.out.println("File not found");
                System.exit(1);
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("project.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 1000);
        scene.getStylesheets().add(getClass().getResource("/app/style-dark.css").toExternalForm());
        stage.setTitle("Fifa Stats Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static String getSaveFileName() {
        return saveFileName;
    }

    public static void main(String[] args) {
        // If file specified and args have the correct length then launch program with arguments
        if (args.length > 1) {
            System.out.println("Only 1 file allowed");
            System.exit(1);
        } else if (args.length == 1) {
            launch(args);
        } else {
            launch();
        }
    }


}