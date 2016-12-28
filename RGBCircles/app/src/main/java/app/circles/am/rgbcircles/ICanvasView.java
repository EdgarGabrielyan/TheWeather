package app.circles.am.rgbcircles;

/**
 * Created by Edgar on 14.11.2016.
 */

public interface ICanvasView {
    void drawCircle(SimpleCircle circle);


    void redraw();

    void showMessage(String text);
}
