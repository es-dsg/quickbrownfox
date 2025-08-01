package battleShip;
import java.awt.*;

abstract class Piece {
    //abstract void draw()
    Color color;
    int currplayer;
    Piece(Color _color , int _currplayer){
       color = _color;
       currplayer = _currplayer;
    }
    Piece(){}

    public Color getColor(){
        return(color);
    }
    public int getPlayer(){
        return(currplayer);
    }


    abstract void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta);
}