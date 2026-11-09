package finalproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Starter JavaFX app for CSSE220 Milestone 1.
 * Replace this simple window with the first version of your project.
 */
public class StarterApp extends Application {

    @Override
    public void start(Stage stage) {
        // TODO 1: Choose a simple app title and create a small root layout.
        Label titleLabel = new Label("CSSE220 Final Project - Wolphram Beta™");
        Label statusLabel = new Label("Starter app is ready.");

        // TODO 2: Add your first interactive control (button, text field, etc.).
        Button actionButton = new Button("Tap to update status");
        actionButton.setOnAction(event -> {
            statusLabel.setText("Your team has launched the shared starter.");
        });

        GraphCanvas graphCanvas = new GraphCanvas(Color.RED);
        graphCanvas.drawLine(20, 30, 40, 50);

        /*WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://127.0.0.1:8000/index.html");//"file:///home/Matthew/CSSE-220/final/bloormg_branch/index.html");//"/home/Matthew/CSSE-220/final/bloormg_branch/index.html");*/


        // TODO 3: Arrange the title, message, and control in a clean layout.
        VBox root = new VBox(12, titleLabel, statusLabel, actionButton, graphCanvas);//, webView);
        root.setPadding(new Insets(20));



        // TODO 4: Set the window title and size.
        Scene scene = new Scene(root, 1920, 1080); //420, 220);
        stage.setTitle("CSSE220 Team Starter - Wolphram Beta™");
        stage.setScene(scene);
        stage.show();

        //I'm throwing the cat! I'm throwing the dog!

        // TODO 5: Replace this starter view with your real app design.
        for(int i = 0; i < 1000000000; ++i) {
            ;
        }
        webEngine.executeScript("new Grapher();");*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
