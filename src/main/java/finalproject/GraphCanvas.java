package finalproject;

import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/*
 * Resources used: Class Resources, https://docs.oracle.com/en/java/java-components/javafx/21/docs/javafx.graphics/module-summary.html
 * Some pages I used were found after clicking 2-5 links from the above link.
 */
public class GraphCanvas extends Canvas {
    private GraphicsContext ctx;
    private Color lineColor;

    public GraphCanvas(Color lineColor) {
        super(1000, 1000);
        ctx = this.getGraphicsContext2D();
        this.lineColor = lineColor;

        this.reset();
    }

    public void reset() {
        // Fill background.
        ctx.setFill(Color.LIGHTGRAY);
        ctx.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw the grid lines.
        ctx.setFill(Color.GRAY);

        for(int i = 0; (this.getWidth() / 10) > i; ++i) {
            ctx.fillRect(i * 10, 0, 1, this.getHeight());
        }

        for(int i = 0; (this.getHeight() / 10) > i; ++i) {
            ctx.fillRect(0, i * 10, this.getWidth(), 1);
        }

        // Draw thi cross shape of the grid lines.
        ctx.setFill(Color.BLACK);
        ctx.fillRect((this.getWidth() / 2) - 2, 0, 3, this.getHeight());
        ctx.fillRect(0, (this.getHeight() / 2) - 2, this.getWidth(), 3);

        ctx.setFill(this.lineColor);
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

    // TODO: Split this method into one that only plots, and another that draws lines between each point.
    public void drawPoints(Point2D[] points) {
        for(int i = 0; points.length > i; ++i) {
            Point2D point = points[i];
            this.ctx.fillRect(point.x, point.y, 5, 5);
        }
    }
}