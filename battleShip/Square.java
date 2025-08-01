
package battleShip;

import java.awt.*;

abstract class Square extends Piece {

    private Color color;

    Square(Color _color, int currplayer) {
        super(_color, currplayer);
        color = _color;

    }

    Square(){
    }

    public Color getColor() {
        return (color);
    }

    public void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta) {
        g.fillRect(Window.getX(column * xdelta), Window.getY(row * ydelta), xdelta, ydelta);
    }
    // Make a method that decides if something is a hit or a miss. JD
}
