package finalproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
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
        Label titleLabel = new Label("CSSE220 Final Project - Wolphram Beta™");
        Label statusLabel = new Label("Starter app is ready.");

        Rectangle sky = new Rectangle(0, 0, 185, 50);
        sky.setFill(Color.GREY);
        Rectangle ground = new Rectangle(0, 50, 185, 2500);
        ground.setFill(Color.LIGHTGRAY);

        Button start = new Button("Diffrentiate");
        Button reset = new Button("Clear Output");
        Button jump = new Button("Flap");
        Label status = new Label("Wolfram Beta");
        TextField textField = new TextField();
        Label fillerDisp = new Label("");

        VBox inputOutput = new VBox(textField,fillerDisp);
        inputOutput.setPadding(new Insets(12));

        start.setOnAction(event -> {
            String output = Integrate(textField.getText());
            System.out.println("calculated and found " + output);
            DisplayOutput(output,inputOutput);
        });

        Pane myPane = new Pane(sky, ground,inputOutput);
        myPane.setFocusTraversable(true);
        myPane.setOnMouseClicked(event -> myPane.requestFocus());
        myPane.setPrefSize(500, 300);
        myPane.setMinSize(500, 300);
        myPane.setMaxSize(1800, 1000); //we need for flappybird

        FlappyToken bird = new FlappyToken();
        bird.getView().setVisible(false);
        myPane.getChildren().add(bird.getView());
        Pipes pipes = new Pipes();

        reset.setOnAction(event -> {
            ClearOutputs(inputOutput);
            flappy.stop();
            bird.getView().setVisible(false);
            pipes.ClearAllPipes(myPane);
        });

        flappy = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            if (!bird.getView().isVisible())
            {
                bird.getView().setVisible(true);
            }
            bird.step();
            if (!pipes.step(myPane,bird.getView().getTranslateY(),bird.getView().getTranslateX())) //checking if we have lost
            {
                pipes.ClearAllPipes(myPane);
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
        root.setCenter(myPane);
        root.setTop(controls);
        //root.setTop(header);
        root.setBottom(status);
        stage.setTitle("Derivative Calculator");
        stage.setScene(new Scene(root, 640, 400));
        //stage.setResizable(false);
        stage.show();
        myPane.requestFocus();


        // TODO 2: Add your first interactive control (button, text field, etc.).
        Button actionButton = new Button("Tap to update status");
        actionButton.setOnAction(event -> {
            statusLabel.setText("Your team has launched the shared starter.");
        });

    }

    public String Integrate(String input)
    {
        return "Now some people are saying we have the best functions, and those people, very smart people, they know a lot about functions, and they say that our functions are the best, and they have seen a lot of functions, so they know what they are talking about....";
    }
        GraphCanvas graphCanvas = new GraphCanvas(Color.RED);
        //graphCanvas.drawLine(20, 30, 40, 50);
        graphCanvas.drawLine(new Point2D(20, 30), new Point2D(40, 50));

        // Some tests.
        /*Point2D[] points = new Point2D[3];
        points[0] = new Point2D(400, 400);
        points[1] = new Point2D(605, 605);
        points[2] = new Point2D(400, 700);
        graphCanvas.tempPlotName(points);*/

        Point2D[] points = new Point2D[3];
        points[0] = new Point2D(100, 100);
        points[1] = new Point2D(205, 205);
        points[2] = new Point2D(300, 800);
        graphCanvas.tempPlotName(points);

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
        // TODO 5: Replace this starter view with your real app design.
        /*for(int i = 0; i < 1000000000; ++i) {
            ;
        }
        webEngine.executeScript("new Grapher();");*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
