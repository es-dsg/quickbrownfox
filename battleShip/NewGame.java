package battleShip;

import java.awt.*;

public class NewGame {
    static String getImage; // = "start_game.png";
    static String function;
    static Image startImage; // = Toolkit.getDefaultToolkit().getImage(getImage);
    static Image tutorialImage = Toolkit.getDefaultToolkit().getImage("Adobe Express - file.png");
    static Image chooseImage = Toolkit.getDefaultToolkit().getImage("chooseNumShips.png");

    public static void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta,
            String _getImage, String _function) {
        getImage = _getImage;
        function = _function;
        startImage = Toolkit.getDefaultToolkit().getImage(getImage);
        // drawImage(g, thisObj, startImage,
        //         Window.getX(((column - 1) * xdelta) - xdelta / 5),
        //         Window.getY((row * ydelta) + ydelta / 2), 0.0, xdelta / 100.0, ydelta / 100.0);
        drawImage(g, thisObj, tutorialImage,
                Window.getX(((column - 1) * xdelta) - xdelta / 5),
                Window.getY(((row-2) * ydelta) + ydelta / 2), 0.0, xdelta / 100.0, ydelta / 100.0);
        drawImage(g, thisObj, chooseImage,
                Window.getX(((column - 1) * xdelta) - xdelta / 5),
                Window.getY(((row-4) * ydelta) + ydelta / 2), 0.0, xdelta / 100.0, ydelta / 100.0);
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
        if (function.equals("start")) {
            if (xpixel > 188 && xpixel < 443) {
                if (ypixel > 409 && ypixel < 462) {
                    return (true);
                }
            }
        }
//        else if (function.equals("choose numships")) {
//            if (xpixel > 188 && xpixel < 443) {
//                if (ypixel > 209 && ypixel < 262) {
//                    return (true);
//                }
//            }
//        }
        return (false);
    }

}
