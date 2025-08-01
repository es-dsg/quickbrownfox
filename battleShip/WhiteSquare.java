
package battleShip;

import java.awt.*;

public class WhiteSquare extends Square {
    private int currplayer;
    WhiteSquare(int _currplayer) {
        super(Color.white , _currplayer);
        currplayer = _currplayer;

    }

    public void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta) {
        g.setColor(getColor());
        super.draw(g, thisObj, row, column, xdelta, ydelta);

    }
}
