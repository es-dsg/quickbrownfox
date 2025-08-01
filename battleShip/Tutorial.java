package battleShip;

import java.awt.*;
/*Left click to place a square on the board. If the square is red, you missed. If the square is white, you hit a ship and will be allowed to go again until you miss.
Right click to place a bomb. Each player has one 3x3 bomb. If you hit ships with the bomb, you will be allowed to go again until you miss.
Once your turn is over, click the g key on your keyboard to switch to the other player's turn.
If you want to view your ships on the board, click the k key. Make sure the other player's back is turned!
Each player has 5 ships. You win when you sink all 5 of the other player's ships.*/

public class Tutorial {
    // static String getImage; 
    // static String function;
    // static Image tutorialImage;
    // public static void draw(Graphics2D g, BattleShip thisObj, int row, int column, int xdelta, int ydelta,
    //         String _getImage, String _function) {
    //     getImage = _getImage;
    //     function = _function;
    //     tutorialImage = Toolkit.getDefaultToolkit().getImage(getImage);
    //     drawImage(g, thisObj, tutorialImage, Window.getX(((column - 1) * xdelta) - xdelta / 5),
    //             Window.getY((row * ydelta) + ydelta / 2), 0.0, xdelta / 100.0, ydelta / 100.0);
    // }
    // private static void drawImage(Graphics2D g, BattleShip thisObj, Image image,
    //         int xpos, int ypos, double rot, double xscale, double yscale) {
    //     int width = image.getWidth(thisObj);
    //     int height = image.getHeight(thisObj);
    //     g.translate(xpos, ypos);
    //     g.rotate(rot * Math.PI / 180.0);
    //     g.scale(xscale, yscale);

    //     g.drawImage(image, -width / 2, -height / 2, width, height, thisObj);

    //     g.scale(1.0 / xscale, 1.0 / yscale);
    //     g.rotate(-rot * Math.PI / 180.0);
    //     g.translate(-xpos, -ypos);
    // }
    public static boolean startTutorial(int xpixel, int ypixel) {
        System.out.println(xpixel + " " + ypixel);
            if (xpixel > 188 && xpixel < 443) {
                if (ypixel > 309 && ypixel < 362) {
                    return (true);
                }
            }
        return (false);
    }
    public static void tutorialDirections(Graphics2D g){
        g.setColor(Color.black);
        g.setFont(new Font("Impact", Font.PLAIN, 18));
        String[] tutorialText = {
        "To access this tutorial at any time, click the T key on your keyboard.",
        "Toggle the T key to exit the tutorial.",
        "Left click to place a square on the board. If the square is red, you missed.",
        "If the square is white, you hit a ship and will be allowed to go again until you miss.",
        "Right click to place a bomb. Each player has one 3x3 bomb.",
        "If you hit ships with the bomb, you will be allowed to go again until you miss.",
        "Once your turn is over, click the G key on your keyboard to switch",
        "to the other player's turn.",
        "If you want to view your ships on the board, click the K key.",
        "Make sure the other player's back is turned!",
        "Each player has 5 ships. You win when you sink all 5 of the other",
        "player's ships."
        };
        int i = 0;
        for (String text : tutorialText) {
            g.drawString(text, Window.getX(10), Window.getHeight2()/2+i);
            i+=30;
        }
    }
}
