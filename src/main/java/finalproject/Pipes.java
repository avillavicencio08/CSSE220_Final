package finalproject;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;


public class Pipes {
    private ArrayList<Pipe> allPipes;
    private int pipeTime = 9999999;
    // private Group view = new Group();
    public Pipes()
    {
        allPipes = new ArrayList<>();
    }
    public boolean step(javafx.scene.layout.Pane canvas,double birdY)
    {

        for(int i = 0; i < allPipes.size();i++)
        {
            allPipes.get(i).step();
            Group pipeView = allPipes.get(i).getView();
            double pipeX = allPipes.get(i).getView().getTranslateX() + canvas.getMaxWidth();
            double birdX = canvas.getMaxWidth() / 2;
            if (pipeX < birdX + 5 && pipeX > birdX - 55) //it happens a bit before we reach pipe
            {
                int pipeY = allPipes.get(i).getHeight();
                if (pipeY + 10 < birdY || pipeY - 90 > birdY)//low cieling fake floor
                {
                    return false;
                }
            }
            if (pipeX < -50)
            {
                canvas.getChildren().remove(allPipes.get(i).getView());
                allPipes.remove(i);
                i--;
            }
        }
        pipeTime += 1;
        if (pipeTime > 400)
        {
            SummonPipe(canvas);
            pipeTime = 0;
        }
        return true;
    }
    public void SummonPipe(javafx.scene.layout.Pane canvas)
    {
        double ran = Math.random() * 200;
        Pipe p1 = new Pipe((int)ran,50, canvas.getWidth(), canvas.getHeight());
        allPipes.add(p1);
        canvas.getChildren().add(p1.getView());
        //view = new Group(view,p1.getView());
    }
    public void ClearAllPipes(javafx.scene.layout.Pane canvas)
    {
        while (allPipes.size() > 0)
        {
            canvas.getChildren().remove(allPipes.get(0).getView());
            allPipes.remove(0);
        }
    }
//    public Group getView()
//    {
//        return view;
//    }
}
