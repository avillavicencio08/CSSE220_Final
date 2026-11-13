package finalproject.flappybird;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import org.w3c.dom.css.RGBColor;

import java.util.ArrayList;


public class Pipes {
    private ArrayList<Pipe> allPipes;
    private int pipeTime = 9999999;
    // private Group view = new Group();
    public Pipes()
    {
        allPipes = new ArrayList<>();
    }
    public boolean step(javafx.scene.layout.Pane canvas,double birdY,double birdX,int score)
    {
        Pipe.updateSpeed(1 + score/1000);
        for(int i = 0; i < allPipes.size();i++)
        {
            allPipes.get(i).step();
            Group pipeView = allPipes.get(i).getView();
            double pipeX = allPipes.get(i).getView().getTranslateX() + canvas.getWidth();
            //double birdX = canvas.getMaxWidth() / 2;
            if (pipeX < birdX + 75 && pipeX > birdX - 20) //it happens a bit before we reach pipe
            {
                int pipeY = allPipes.get(i).getHeight();
                if (pipeY + 10 < birdY || pipeY - 90 > birdY)//low cieling fake floor
                {
                    return false;
                }
            }
            if (pipeX < canvas.getWidth() * -1)
            {
                canvas.getChildren().remove(allPipes.get(i).getView());
                allPipes.remove(i);
                i--;
            }
        }
        pipeTime += 1;
        if (pipeTime > 400)
        {
            SummonPipe(canvas,score);
            pipeTime = 0;
        }
        return true;
    }
    public void SummonPipe(javafx.scene.layout.Pane canvas,int score)
    {
        double ran = Math.random() * canvas.getHeight();// * 2000;
        double dissapearing = 1/(1 +(score/5000));
        Color turnRed = new Color(1,dissapearing,dissapearing,1);
        Pipe p1 = new Pipe((int)ran,50, canvas.getWidth(), canvas.getHeight(),turnRed);
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
