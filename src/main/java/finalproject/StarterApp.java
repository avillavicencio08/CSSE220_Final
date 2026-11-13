package finalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import finalproject.flappybird.FlappyToken;
import finalproject.flappybird.Pipes;

/** Derivative calculator with a Flappy Bird easter egg. */
public class StarterApp extends Application {
    private Timeline flappy;
    private int score = 0;
    private int highScore = 0;

    @Override
    public void start(Stage stage) {
        Label status = new Label("Wolfram Beta");

        Rectangle sky = new Rectangle(0, 0, 185, 50);
        sky.setFill(Color.WHITESMOKE);
        Rectangle ground = new Rectangle(0, 50, 185, 2500);
        ground.setFill(Color.LIGHTGRAY);

        Button differentiate = new Button("Differentiate");
        Button reset = new Button("Clear Output");
        Button jump = new Button("Flap");
        TextField textField = new TextField();
        textField.setPromptText("e.g. x^2 + sin(x)");
        Label fillerDisp = new Label("");

        VBox inputOutput = new VBox(textField, fillerDisp);
        inputOutput.setPadding(new Insets(12));

        Runnable runDifferentiate = () -> {
            String output = differentiate(textField.getText());
            System.out.println("calculated and found " + output);
            DisplayOutput(output, inputOutput);
        };
        differentiate.setOnAction(event -> runDifferentiate.run());
        textField.setOnAction(event -> runDifferentiate.run()); // Enter also works

        Rectangle startBlock = new Rectangle(0, 0, 10085, 5000);
        Button startButton = new Button("Start");

        Pane myPane = new Pane(sky, ground);
        myPane.setFocusTraversable(true);
        myPane.setOnMouseClicked(event -> myPane.requestFocus());
        myPane.setPrefSize(500, 300);
        myPane.setMinSize(500, 300);
        myPane.setMaxSize(1800, 1000); // we need this for flappybird

        FlappyToken bird = new FlappyToken();
        bird.getView().setVisible(false);

        GraphCanvas graphCanvas = new GraphCanvas(Color.RED);
        graphCanvas.drawLine(new finalproject.Point2D(20, 30), new finalproject.Point2D(40, 50));
        HBox comboWGraph = new HBox(inputOutput,graphCanvas);
        myPane.getChildren().add(comboWGraph);

        myPane.getChildren().add(bird.getView());

        myPane.getChildren().add(startBlock);
        myPane.getChildren().add(startButton);

        Pipes pipes = new Pipes();

        startButton.setOnAction(event -> {
            startBlock.setVisible(false);
            startButton.setVisible(false);
        });

        reset.setOnAction(event -> {
            ClearOutputs(inputOutput);
            flappy.stop();
            score = 0;
            bird.getView().setVisible(false);
            pipes.ClearAllPipes(myPane);
            fillerDisp.setVisible(false);
        });

        flappy = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            if (!bird.getView().isVisible()) {
                bird.getView().setVisible(true);
            }
            bird.step();
            score += 1;
            if (score > highScore) {
                highScore = score;
            }
            fillerDisp.setText("Score: " + score + "  High Score: " + highScore);
            if (!pipes.step(myPane, bird.getView().getTranslateY(), bird.getView().getTranslateX(), score)) { // lost?
                pipes.ClearAllPipes(myPane);
                bird.reset();
                bird.getView().setVisible(false);
                flappy.stop();
                score = 0;
                fillerDisp.setVisible(false);
            }
        }));
        flappy.setCycleCount(Timeline.INDEFINITE);
        jump.setOnAction(event -> {
            flappy.play();
            fillerDisp.setVisible(true);
            bird.yVel = 3;
        });

        HBox controls = new HBox(15, differentiate, jump, reset);
        controls.setPadding(new Insets(12));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(myPane);
        rootPane.setTop(controls);
        rootPane.setBottom(status);
        stage.setTitle("Derivative Calculator");
        stage.setScene(new Scene(rootPane, 640, 400));
        stage.show();
        myPane.requestFocus();
    }

    /** Parses the input and returns its derivative as text (or "Error: ..."). */
    public String differentiate(String input) {
        return Differentiator.differentiate(input);
    }

    public void DisplayOutput(String output, VBox inputOutput) {
        Label out = new Label(output);
        inputOutput.getChildren().add(out);
    }

    public void ClearOutputs(VBox inputOutput) {
        while (inputOutput.getChildren().size() > 2) {
            inputOutput.getChildren().remove(2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
