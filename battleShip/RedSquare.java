package battleShip;
import java.awt.*;
public class RedSquare extends Square{
    private int currplayer;
    RedSquare(int _currplayer) {
        super(Color.red , _currplayer);
        currplayer = _currplayer;
    }
    public int getCurrplayer(){
        return(currplayer);
    }

    public void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta) {
        g.setColor(getColor());
        super.draw(g,thisObj, row,column,xdelta,ydelta);

    }

}
