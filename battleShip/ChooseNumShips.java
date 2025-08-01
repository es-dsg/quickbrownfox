package battleShip;

import java.awt.*;

public class ChooseNumShips {
    static String getImage; // = "start_game.png";
    static String function;
    static Image startImage = Toolkit.getDefaultToolkit().getImage("fivebutton.png");
    static Image sixImage = Toolkit.getDefaultToolkit().getImage("sevenbutton.png");
    static Image sevenImage = Toolkit.getDefaultToolkit().getImage("sixbutton.png");
    public static int ships;



    public static void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta,
                            String _getImage, String _function) {
        getImage = _getImage;
        function = _function;
        drawImage(g, thisObj, startImage,
                Window.getX(((column - 1) * xdelta) - xdelta / 5),
                Window.getY(((row-4) * ydelta) + ydelta / 2), 0.0, xdelta / 175.0, ydelta / 175.0);
        drawImage(g, thisObj, sixImage,
                Window.getX(((column - 1) * xdelta) - xdelta / 5),
                Window.getY(((row) * ydelta) + ydelta / 2), 0.0, xdelta / 175.0, ydelta / 175.0);
        drawImage(g, thisObj, sevenImage,
                Window.getX(((column - 1) * xdelta) - xdelta / 5),
                Window.getY(((row-2) * ydelta) + ydelta / 2), 0.0, xdelta / 175.0, ydelta / 175.0);

    }

    private static void drawImage(Graphics2D g, BattleShip thisObj, Image image,
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

    public static boolean startGame(int xpixel, int ypixel) {
        System.out.println(xpixel + " " + ypixel);
            if (xpixel > 188 && xpixel < 443) {
                if (ypixel > 209 && ypixel < 262) {
                    return (true);
                }

        }
        return (false);
    }
    public static int checkNumShips(int xpixel, int ypixel){
        System.out.println(xpixel + " " + ypixel);
        if (xpixel > 250 && xpixel < 380) {
            if  (ypixel > 210 && ypixel < 251) {
                return 5;
            }
            else if (ypixel > 315 && ypixel < 355) {
                return 6;
            }
            else if (ypixel > 415 && ypixel < 455) {
                return 7;
            }
        }
        return 5;
    }

}
