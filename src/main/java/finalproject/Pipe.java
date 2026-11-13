package finalproject;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;


public class Pipe {
    private int gap = 10;
    private int height;
    private final Group view;
    private static double Gamespeed = 1;
    public static void updateSpeed(double speed)
    {
        Gamespeed = speed;
    }
    public Pipe(int height, int gap,double sceneWidth,double sceneHeight,Color color)
    {
        this.height = height;
        this.gap = gap;
        Rectangle pipeTop = new Rectangle(sceneWidth, 0, 50, height-gap);
        pipeTop.setFill(color);
        Rectangle pipeBottom = new Rectangle(sceneWidth, height + gap, 50, sceneHeight-(height + gap));
        pipeBottom.setFill(color);
        view = new Group(pipeTop,pipeBottom);
    }
    public void step()
    {
        view.setTranslateX(view.getTranslateX() - Gamespeed);
    }
    public Group getView()
    {
        return view;
    }
    public int getGap()
    {
        return gap;
    }
    public int getHeight()
    {
        return height;
    }
}
