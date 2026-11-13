package finalproject.flappybird;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.FileNotFoundException;

/** Owns the visual token, its local rotation pivot, and position. */
public class FlappyToken {
    private final Group view;
    public double yVel = 0;

    public FlappyToken() {
        Image image = null;
        try {
            image = new Image(FlappyToken.class.getResource("/images/FireflyFlappy.png").toExternalForm());
            if (image == null) {
                throw new IllegalArgumentException("Image not found null exception");
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            //image = new Rectangle(25,25,25,25).get;
        }
        ImageView drone;
        if (image != null) {
            drone = new ImageView(image);
        }
        else {
            //drone = Rectangle(0, 0, 185, 50);
            //drone.setFill(Color.GREY);
        }

        drone.setFitWidth(90);
        drone.setPreserveRatio(true);
        view = new Group(drone);
        reset();
    }

    public Group getView() { return view; }

    /** Move one tick; return true when the right edge is reached. */
    public void step() {
        //view.setTranslateX(view.getTranslateX() + SPEED);
        view.setTranslateY(view.getTranslateY() - yVel);
        view.setRotate(yVel * -5);
        if (yVel > -6) {
            yVel -= .1;
        }
        //keepInside(playWidth);
    }


    public void keepInArena(double width, double height) {
        if (view.getBoundsInParent().getMaxY() > height)
            view.setTranslateY(view.getTranslateY() - (view.getBoundsInParent().getMaxY() - height));
        if (view.getBoundsInParent().getMinY() < 0)
            view.setTranslateY(view.getTranslateY() - view.getBoundsInParent().getMinY());
    }

    public void reset() {
        view.setTranslateX(180);
        view.setTranslateY(160);
    }
}
