package finalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Time;

/**
 * Starter JavaFX app for CSSE220 Milestone 1.
 * Replace this simple window with the first version of your project.
 */
public class StarterApp extends Application {
    private Timeline flappy;
    @Override
    public void start(Stage stage) {
        // TODO 1: Choose a simple app title and create a small root layout.
        //Label titleLabel = new Label("CSSE220 Final Project");
        Label statusLabel = new Label("Starter app is ready.");

        Rectangle sky = new Rectangle(0, 0, 500, 50);
        sky.setFill(Color.LIGHTSKYBLUE);
        Rectangle ground = new Rectangle(0, 50, 500, 250);
        ground.setFill(Color.LIGHTGREEN);

        Button start = new Button("Diffrentiate");
        Button reset = new Button("Clear Output");
        Button jump = new Button("Flap");
        Label status = new Label("");
        TextField textField = new TextField();
        Label fillerDisp = new Label("");

        VBox inputOutput = new VBox(textField,fillerDisp);
        inputOutput.setPadding(new Insets(12));

        start.setOnAction(event -> {
            String output = Integrate(textField.getText());
            System.out.println("calculated and found " + output);
            DisplayOutput(output,inputOutput);
        });

        Pane canvas = new Pane(sky, ground,inputOutput);
        canvas.setFocusTraversable(true);
        canvas.setOnMouseClicked(event -> canvas.requestFocus());
        canvas.setPrefSize(500, 300);
        canvas.setMinSize(500, 300);
        //canvas.setMaxSize(500, 300); //I dont think we need

        FlappyToken bird = new FlappyToken();
        bird.getView().setVisible(false);
        canvas.getChildren().add(bird.getView());
        Pipes pipes = new Pipes();

        reset.setOnAction(event -> {
            ClearOutputs(inputOutput);
            flappy.stop();
            bird.getView().setVisible(false);
            pipes.ClearAllPipes(canvas);
        });

        flappy = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            if (!bird.getView().isVisible())
            {
                bird.getView().setVisible(true);
            }
            bird.step();
            if (!pipes.step(canvas,bird.getView().getTranslateY())) //checking if we have lost
            {
                pipes.ClearAllPipes(canvas);
                bird.reset();
                bird.getView().setVisible(false);
                flappy.stop();
            }
        }));
        flappy.setCycleCount(Timeline.INDEFINITE);
        jump.setOnAction( event -> {
            flappy.play();
            bird.yVel = 3;
        });


//        canvas.setOnKeyPressed(event -> {
//            KeyCode code = event.getCode();
//            if (code == KeyCode.RIGHT) {
//                writeIndex += 1;
//            }
        //probably dont need this yet, we have the textbox luckily

        HBox controls = new HBox(15, start, jump, reset);
        controls.setPadding(new Insets(12));
        HBox header = new HBox(12, new Label("Lock in nolan!"));
        header.setPadding(new Insets(12));
        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setTop(controls);
        //root.setTop(header);
        root.setBottom(status);
        stage.setTitle("Derivative Calculator");
        stage.setScene(new Scene(root, 640, 400));
        //stage.setResizable(false);
        stage.show();
        canvas.requestFocus();


        // TODO 2: Add your first interactive control (button, text field, etc.).
        Button actionButton = new Button("Tap to update status");
        actionButton.setOnAction(event -> {
            statusLabel.setText("Your team has launched the shared starter.");
        });

    }

    public String Integrate(String input)
    {
        return "lol TBD";
    }

    public void DisplayOutput(String output,VBox inputOutput)
    {
        Label out = new Label(output);
        inputOutput.getChildren().add(out);
    }

    public void ClearOutputs(VBox inputOutput)
    {
        while (inputOutput.getChildren().size() > 2)
        {
            inputOutput.getChildren().remove(2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
