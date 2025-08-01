
package battleShip;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class BattleShip extends JFrame implements Runnable {
    boolean animateFirstTime = true;
    Image image;
    Image waterImage;
    Image metalImage;
    Image battleshipImage;
    Image startgameImage;
    Graphics2D g;

    private int sum;
    private int low_size_x;
    private int low_size_y;
    private int size;
    private static int TimeCount;
    private static boolean GameOver = false;
    private static boolean hideships[] = new boolean[Player.getPlayers().length];

    private int currXpos;
    public static int ships = 5;
    boolean isStarted = false;
    boolean tutorialActive = false;
    boolean chooseNumShips = false;

    sound bgSound = null;

    sound squaresound = null;
    sound bombsound = null;

    // Ship ship = new Ship();
    // RedSquare redSquare = new RedSquare();

    public static void main(String[] args) {
        BattleShip frame = new BattleShip();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public BattleShip() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (GameOver) {
                    return;
                }
                if (e.BUTTON1 == e.getButton()) {
                    if (isStarted) {
                        if (Player.getCurrentPlayer().getSum() < 1) {
                            Board2.AddPiece(e.getX(), e.getY());
                        }
                        squaresound = new sound("video_sound.wav");
                    } else if (!isStarted && !tutorialActive && !chooseNumShips) {
                        // isStarted = NewGame.startGame(e.getX(), e.getY());
                        tutorialActive = Tutorial.startTutorial(e.getX(), e.getY());
                        chooseNumShips = ChooseNumShips.startGame(e.getX(), e.getY());
                    }
                    else if (!isStarted && chooseNumShips) {
                        ships = ChooseNumShips.checkNumShips(e.getX(), e.getY());
                        if (ships > 0) {
                            Board2.setNumShips(ships); // update the Board class
                            Board2.Reset();            // reset board with new ship count
                            isStarted = true;         // now start the game
                            chooseNumShips = false;   // done choosing
                        }
                    }
                }

                if (e.BUTTON3 == e.getButton()) {
                    if (isStarted) {
                        if (Player.getCurrentPlayer().getSum() < 1 && Player.getCurrentPlayer().getNumBombs() > 0) {
                            Board2.AddBomb(e.getX(), e.getY());
                            bombsound = new sound("mouse_game.wav");
                        }
                    }
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {

                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                } else if (e.VK_ESCAPE == e.getKeyCode()) {
                    reset();
                } else if (e.VK_S == e.getKeyCode()) {
                    bgSound.stopPlaying = !bgSound.stopPlaying;
                } else if (e.VK_P == e.getKeyCode()) {
                    bgSound.pausePlaying = !bgSound.pausePlaying;
                } else if (e.VK_B == e.getKeyCode()) {
                    isStarted = true;
                } else if (e.VK_G == e.getKeyCode()) {
                    Player.getCurrentPlayer().setSum(0);
                    Player.switchCurrentPlayer();

                } else if (e.VK_K == e.getKeyCode()) {
                    for (int i = 0; i < Player.getPlayers().length; i++) {
                        if (i == Player.getCurrentIndex()) {
                            hideships[i] = !hideships[i];
                        }
                    }
                } else if (e.VK_T == e.getKeyCode()) {
                    if (!tutorialActive)
                        tutorialActive = true;
                    else
                        tutorialActive = false;
                } else if (e.VK_E == e.getKeyCode()){
//                    System.out.println("destroyed");
                }
                repaint();
            }
        });
        init();
        start();
    }

    Thread relaxer;

    ////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }

    ////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }

    public static boolean getGameOver() {
        return (GameOver);
    }

    public static void setGameOver(boolean _gameOver) {
        GameOver = _gameOver;
    }

    ////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        // fill background

        // g.setColor(Color.gray);
        // g.fillRect(0, 0, Window.xsize, Window.ysize);
        g.drawImage(metalImage, 0, 0, Window.xsize, Window.ysize, this);

        int x[] = { Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0),
                Window.getX(0) };
        int y[] = { Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()),
                Window.getY(0) };
        // fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
        // draw border
        // g.setColor(Color.red);
        // g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
        g.drawImage(waterImage, Window.getX(0), Window.getY(0), Window.getWidth2(), Window.getHeight2(), this);
        if (!isStarted && !chooseNumShips) {
            g.drawImage(battleshipImage, currXpos, Window.getY(Window.getHeight2() / 4),
                    low_size_x, low_size_y, this);
            NewGame.draw(g, this, 6, 6, Window.getWidth2() / Board2.getNumCols(),
                    Window.getHeight2() / Board2.getNumRows(), "start_game.png", "start"); //draws main menu
        }
        if (!isStarted && chooseNumShips){
            ChooseNumShips.draw(g, this, 6, 6, Window.getWidth2() / Board2.getNumCols(),
                    Window.getHeight2() / Board2.getNumRows(), "start_game.png", "start");
        }

        if (isStarted) {
            g.drawImage(battleshipImage, Window.getX(Window.getWidth2() / 4), Window.getY(-Window.getHeight2() / 8),
                    Window.getWidth2() / 2,
                    Window.getHeight2() / 8, this);
            Board2.Draw(g, this);
        }
        if (tutorialActive){
            //g.setColor(Color.white);
            g.drawImage(waterImage, Window.getX(0), Window.getY(0), Window.getWidth2(), Window.getHeight2(), this);
            Tutorial.tutorialDirections(g);
        }
        
        gOld.drawImage(image, 0, 0, null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // needed for implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.5; // time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////
    public void reset() {
        Player.Reset();
        Board2.Reset();

        sum = 0;
        low_size_x = 100;
        low_size_y = 25;
        currXpos = Window.getWidth2() / 2;
        size = 1;
        TimeCount = 0;
        isStarted = false;
        tutorialActive = false;
        chooseNumShips = false;
        for (int i = 0; i < Player.getPlayers().length; i++) {
            hideships[i] = true;
        }

    }

    /////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            waterImage = Toolkit.getDefaultToolkit().getImage("./image.png");
            metalImage = Toolkit.getDefaultToolkit().getImage("./image (1).png");
            battleshipImage = Toolkit.getDefaultToolkit().getImage("./battleship.png");
            startgameImage = Toolkit.getDefaultToolkit().getImage("./start_game.png");
            reset();
            bgSound = new sound("hills.wav");
        }
        if (GameOver) {
            return;
        }

        if (bgSound.donePlaying && !bgSound.stopPlaying)
            bgSound = new sound("hills.wav");

        if (low_size_x > ((Window.getWidth2() / 3) * 2) || low_size_y > ((Window.getWidth2() / 6)) || low_size_x < 100
                || low_size_y < 25) {
            size = -size;
        }
        low_size_x += (4 * size);
        low_size_y += (size);
        if (TimeCount % 3 == 0) {
            currXpos -= (8 * size);
        }
        Board2.CheckWin();
        //Board.Animate(g);
        TimeCount++;
    }

    public static boolean getHideShips(int k) {
        return (hideships[k]);
    }

    public static int getTimeCount() {
        return (TimeCount);
    }

    ////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

}
