package battleShip;

import java.awt.*;

abstract class Ship extends Piece {
    private int row;
    private int column;
//    private static int length;
    private int length;
    private int[][] coordinates; // Stores [row, col] pairs
    private Image shipImage = null;
    private int orientation = 0;
    private String getImage = "";
    boolean destroyed = false;
    boolean justDestroyed = false;

    public Ship(int _row, int _column, int _orientation, String _getImage , int _length) {//another parameter
        row = _row;
        column = _column;
        getImage = _getImage;
        orientation = _orientation;
        length = _length;
        coordinates = new int[length][2];

        if (shipImage == null) { //subclass
            shipImage = Toolkit.getDefaultToolkit().getImage(getImage); //pass in a string.
        }

        if (_orientation == 1 || _orientation == 3) {
            // Store vertical coordinates (e.g., downwards from starting point)
            for (int i = 0; i < length; i++) {
                coordinates[i][0] = _row - i; // row (e.g., 5, 4, 3, 2)
                coordinates[i][1] = _column; // column stays fixed
            }
        } else {
            for (int i = 0; i < length; i++) {
                coordinates[i][0] = _row; // row (e.g., 5, 4, 3, 2)
                coordinates[i][1] = _column - i; // column stays fixed
            }
        }

    }

    public Image getShipImage(){
        return(shipImage);
    }

    public int getLength() {
        return (length);
    }

    public int getOrientation() {
        return (orientation);
    }

    public boolean locations(int r, int c) {
        for (int i = 0; i < length; i++) {
            int shipRow = coordinates[i][0];
            int shipCol = coordinates[i][1];
            if (shipRow == r && shipCol == c) {
                return true;
            }
        }
        return false;
    }

    public int[][] getOccupiedPositions() {
        return coordinates;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


    public void printCoordinates() {
        System.out.println("Ship starting at (" + row + "," + column + ") occupies:");
        for (int i = 0; i < coordinates.length; i++) {
            System.out.println("  Row: " + coordinates[i][0] + ", Column: " + coordinates[i][1]);
        }
        System.out.println("///////////////////////////////////////////////////////////");
    }

    public void draw(Graphics2D g, BattleShip thisObj, int _row, int _column, int xdelta, int ydelta) {
        if (orientation == 0) { //left
            drawImage(g, thisObj, shipImage,
                    Window.getX(((_column - 1) * xdelta) - xdelta / 5),
                    Window.getY((_row * ydelta) + ydelta / 2),
                    90.0, xdelta / 120.0, ydelta / 130.0);
        } else if (orientation == 1) { //up
            drawImage(g, thisObj, shipImage,
                    Window.getX((_column * xdelta) + xdelta / 2),
                    Window.getY(((_row - 1) * ydelta) + ydelta / 10 - 5),
                    0.0, xdelta / 120.0, ydelta / 130.0);
        } else if (orientation == 2) {//right
            drawImage(g, thisObj, shipImage,
                    Window.getX(((_column - 1) * xdelta) - xdelta / 5),
                    Window.getY((_row * ydelta) + ydelta / 2),
                    270.0, xdelta / 120.0, ydelta / 130.0);
        } else { //down
            drawImage(g, thisObj, shipImage,
                    Window.getX((_column * xdelta) + xdelta / 2),
                    Window.getY(((_row - 1) * ydelta) + ydelta / 10 - 5),
                    180.0, xdelta / 120.0, ydelta / 130.0);
        }
    }

    public void drawImage(Graphics2D g, BattleShip thisObj, Image image,
            int xpos, int ypos, double rot, double xscale, double yscale) {
        int width = image.getWidth(thisObj);
        int height = image.getHeight(thisObj);
        g.translate(xpos, ypos);
        g.rotate(rot * Math.PI / 180.0);
        g.scale(xscale, yscale);

        g.drawImage(image, -width / 2, -height / 2, width, height, thisObj);

        g.scale(1.0 / xscale, 1.0 / yscale);
        g.rotate(-rot * Math.PI / 180.0);
        g.translate(-xpos, -ypos);
    }
    public boolean getDestroyed(){
        return destroyed;
    }
    public void setDestroyed(boolean d){
        destroyed = d;
    }
    public boolean getJustDestroyed(){
        return justDestroyed;
    }
    public void setJustDestroyed(boolean d){
        justDestroyed = d;
    }
}
