package finalproject.flappybird;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import org.w3c.dom.css.Rect;

import java.io.FileNotFoundException;

/** Owns the visual token, its local rotation pivot, and position. */
public class FlappyToken {
    private final Group view;
    public double yVel = 0;

    public FlappyToken() {
        Image image = null;
        Node drone;
        System.out.println("loading image 3");
        try {
            System.out.println("loading image 4");
            image = new Image(FlappyToken.class.getResource("/images/FireflyFlappy.png").toExternalForm());
            System.out.println("loading image 5");
            if (image == null) {
                System.out.println("found null image exception");
                throw new IllegalArgumentException("Image not found null exception");
            }
        }
        catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        if (image != null) {
            drone = new ImageView(image);
            ((ImageView)drone).setFitWidth(90);
            ((ImageView)drone).setPreserveRatio(true);
        }
        else {
            javafx.scene.shape.Rectangle fallback = new Rectangle(0, 0, 90, 90);
            fallback.setFill(Color.GREY);
            drone = fallback;
        }
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
    }
    public void reset() {
        view.setTranslateX(180);
        view.setTranslateY(160);
    }
}
