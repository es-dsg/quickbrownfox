package battleShip;

import java.awt.*;

public class Submarine extends Ship{
    private static int length = 3;
    public Submarine(int _row, int _column, int _orientation){
        super(_row, _column, _orientation, "./image__2_-removebg-preview.png", length);
    }

    public void draw(Graphics2D g, BattleShip thisObj, int _row, int _column, int xdelta, int ydelta) {
        if (getOrientation() == 0) { //left
            drawImage(g, thisObj, getShipImage(),
                    Window.getX(((_column-1 ) * xdelta) +xdelta/2),
                    Window.getY((_row * ydelta) + ydelta / 2),
                    90.0, xdelta / 140.0, ydelta / 155.0);
        } else if (getOrientation() == 1) { //up
            drawImage(g, thisObj, getShipImage(),
                    Window.getX((_column * xdelta) + xdelta / 2),
                    Window.getY(((_row - 1) * ydelta) + ydelta / 2),
                    0.0, xdelta / 120.0, ydelta / 180.0);
        } else if (getOrientation() == 2) {//right
            drawImage(g, thisObj, getShipImage(),
                    Window.getX(((_column-1 ) * xdelta) +xdelta/2),
                    Window.getY((_row * ydelta) + ydelta / 2),
                    270.0, xdelta / 140.0, ydelta / 155.0);
        } else { //down
            drawImage(g, thisObj, getShipImage(),
                    Window.getX((_column * xdelta) + xdelta / 2),
                    Window.getY(((_row - 1) * ydelta) + ydelta / 2),
                    180.0, xdelta / 120.0, ydelta / 180.0);
        }
    }
}