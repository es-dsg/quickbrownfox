package battleShip;

import java.awt.*;

public class Bomb extends Piece {
    private int Dimensions;
    private int currplayer;
    private boolean Whitehit;

    Bomb(int _Dimensions, int _currplayer) {
        super(Color.black, _currplayer);
        Dimensions = _Dimensions;
        currplayer = _currplayer;
        Whitehit = false;
    }

    public int getCurrplayer() {
        return (currplayer);
    }

    public void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta) {
        g.setColor(Color.red);
        if (Board2.isWhiteSquare(row, column)) {
            g.setColor(Color.white);
            Whitehit = true;
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(Window.getX(column * xdelta), Window.getY(row * ydelta),
                xdelta, ydelta);
    }

    public boolean getWhitehit(){
        return(Whitehit);
    }

    public void setWhitehit(boolean _Whitehit){
        Whitehit = _Whitehit;
    }


    private int helperRow(int row) {
        int Newrow;
        if (row < 0) {
            Newrow = 0;
        } else if (row > Board2.getNumRows() - 1) {
            Newrow = Board2.getNumRows() - 1;
        } else {
            Newrow = row;
        }
        return Newrow;
    }

    private int helperCol(int col) {
        int Newcol;
        if (col < 0) {
            Newcol = 0;
        } else if (col > Board2.getNumCols() - 1) {
            Newcol = Board2.getNumCols() - 1;
        } else {
            Newcol = col;
        }
        return Newcol;
    }

    public int getDimensions() {
        return Dimensions;
    }
}
