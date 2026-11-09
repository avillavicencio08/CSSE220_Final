package finalproject;

import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/*
 * Resources used: https://docs.oracle.com/en/java/java-components/javafx/21/docs/javafx.graphics/module-summary.html
 * Some pages I used were found after clicking 2 links in the above link.
 */
public class GraphCanvas extends Canvas {
    private GraphicsContext ctx;

    public GraphCanvas() {
        super(500, 500);
        ctx = this.getGraphicsContext2D();

        this.resetGraph();
    }

    public void resetGraph() {
        // Fill background.
        ctx.setFill(Color.LIGHTGRAY);
        ctx.fillRect(0, 0, 500, 500);

        // Draw the grid lines.
        ctx.setFill(Color.GRAY);

        for(int i = 0; (this.getWidth() / 10) > i; ++i) {
            ctx.fillRect(i * 10, 0, 1, 500);
        }

        for(int i = 0; (this.getHeight() / 10) > i; ++i) {
            ctx.fillRect(0, i * 10, 500, 1);
        }

        ctx.setFill(Color.BLACK);

        // Make the cross shape of the grid lines.
        ctx.fillRect((500 / 2) - 2, 0, 3, 500);
        ctx.fillRect(0, (500 / 2) - 2, 500, 3);
    }

    public void drawLine(double x1, double y1, double x2, double y2) {
        if (x1 > x2) {
            double tempX = x1;
            double tempY = y1;
            x1 = x2;
            y1 = y2;
            x2 = tempX;
            y2 = tempY;
        }

        double denominator = x2 - x1;

        if (denominator == 0) {
            System.out.println("Warning: I cannot handle undefined slope yet.");
            return;
        }

        double slope = (y2 - y1) / denominator;

        for (; x1 < x2; x1 += 1) {
            ctx.fillRect(x1, y1, /*x1 + */5, /*y1 + */5);
            y1 += slope;
        }
    }
}