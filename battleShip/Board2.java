package battleShip;

import java.awt.*;

public class Board2 {
    private final static int NUM_ROWS = 10;
    private final static int NUM_COLUMNS = 10;
    private static Piece board[][][] = new Piece[NUM_ROWS][NUM_COLUMNS][Player.getPlayers().length];
    private static int Numships = 5; //change this somewhere
    private static Ship ships[][] = new Ship[Numships][Player.getPlayers().length];
    private static int val = 0;
    private static boolean shipDestroyed = true;
    public static int[] shipsDestroyed = new int[2]; // index 0 for player 1, 1 for player 2
    // private static int boardTimeCount = 0;
    private static boolean drawDestroyed = false; //add to reset
    private static String theShip;

    private static int shipsdestroyedByCurrentPlayer;

    // static Ship = new Ship(5, 4, Color.gray);

    public static int getNumRows() {
        return NUM_ROWS;
    }

    public static int getNumCols() {
        return NUM_COLUMNS;
    }
    public static int getNumShips() {
        return Numships;
    }

    public static void setNumShips(int n) {
        Numships = n;
    }

    public static void Reset() {
        shipDestroyed=true;
        val = 0;
        // boardTimeCount = 0;
        drawDestroyed = false; //add to reset
        shipsDestroyed[0] = 0;
        shipsDestroyed[1] = 0;
        Numships =5;
        // Clear the board
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                for (int p = 0; p < Player.getPlayers().length; p++) {
                    board[row][col][p] = null;
                }
            }
        }

        // Place ships for each player
        for (int playerIndex = 0; playerIndex < Player.getPlayers().length; playerIndex++) {
            for (int shipIndex = 0; shipIndex < Numships; shipIndex++) {
                boolean placed = false;

                while (!placed) {
                    int randRow = (int) (Math.random() * NUM_ROWS);
                    int randCol = (int) (Math.random() * NUM_COLUMNS);
                    int randOrient = (int) (Math.random() * 4);

                    Ship candidate = null;

                    if (shipIndex == 0) {
                        candidate = new Carrier(randRow, randCol, randOrient);
                    } else if (shipIndex == 1) {
                        candidate = new Battle_Ship(randRow, randCol, randOrient);
                    } else if (shipIndex == 2) {
                        candidate = new Submarine(randRow, randCol, randOrient);
                    } else if (shipIndex == 3) {
                        candidate = new Destroyer(randRow, randCol, randOrient);
                    } else if (shipIndex == 4) {
                        candidate = new Patrol_Boat(randRow, randCol, randOrient);
                    }
                    if (Numships == 6 || Numships ==7) {
                        if (shipIndex == 5) {
                            candidate = new Patrol_Boat(randRow, randCol, randOrient);
                        }
                    }
                    if (Numships ==7){
                        if (shipIndex == 6) {
                            candidate = new Patrol_Boat(randRow, randCol, randOrient);
                        }
                    }

                    // Basic safety check (just in case)
                    if (candidate == null)
                        continue;

                    // Check for collision
                    boolean collision = false;
                    for (int[] pos : candidate.getOccupiedPositions()) {
                        int r = pos[0];
                        int c = pos[1];
                        if (r < 0 || r >= NUM_ROWS || c < 0 || c >= NUM_COLUMNS || board[r][c][playerIndex] != null) {
                            collision = true;
                            break;
                        }
                    }

                    // Place if no collision
                    if (!collision) {
                        ships[shipIndex][playerIndex] = candidate;
                        for (int[] pos : candidate.getOccupiedPositions()) {
                            int r = pos[0];
                            int c = pos[1];
                            board[r][c][playerIndex] = candidate;
                        }
                        placed = true;
                    }
                }
            }
        }
    }

    public static void AddPiece(int xpixel, int ypixel) {
        int ydelta = Window.getHeight2() / NUM_ROWS;
        int xdelta = Window.getWidth2() / NUM_COLUMNS;

        int modXPixel = xpixel - Window.getX(0);
        int modYPixel = ypixel - Window.getY(0);
        int column = modXPixel / xdelta;
        int row = modYPixel / ydelta;

        if (modXPixel <= 0 || modXPixel >= Window.getWidth2() ||
                modYPixel <= 0 || modYPixel >= Window.getHeight2()) {
            return;
        }

        int currentPlayer = Player.getCurrentIndex();
        int opponent = (currentPlayer == 0) ? 1 : 0;

        boolean isHit = false;

        // Always re-check opponent ships, not past board state
        for (int i = 0; i < ships.length; i++) {
            Ship enemyShip = ships[i][opponent];
            if (enemyShip != null && enemyShip.locations(row, column)) {
                isHit = true;
                break;
            }
        }

        if (isHit) {
            // Check current player's board view
            if (!(board[row][column][currentPlayer] instanceof WhiteSquare)) {
                board[row][column][currentPlayer] = new WhiteSquare(currentPlayer);
                System.out.println("Player " + currentPlayer + " HIT at [" + row + "][" + column + "]");
                //Player.getCurrentPlayer().setSum(Player.getCurrentPlayer().getSum() + 1);
            }
        } else {
            // If this is not already marked, record a miss
            if (!(board[row][column][currentPlayer] instanceof WhiteSquare) &&
                    !(board[row][column][currentPlayer] instanceof RedSquare) &&
                    !(board[row][column][currentPlayer] instanceof Bomb)) {
                board[row][column][currentPlayer] = new RedSquare(currentPlayer);
                Player.getCurrentPlayer().setSum(Player.getCurrentPlayer().getSum() + 1);
                System.out.println("Player " + currentPlayer + " MISS at [" + row + "][" + column + "]");
            }
        }
    }

public static void CheckWin() {
    int destroyedByCurrentPlayer = 0;
    int currentPlayer = Player.getCurrentIndex();
    int opp = (currentPlayer == 0) ? 1 : 0;

    // Reset all justDestroyed flags for opponent's ships
    for (int i = 0; i < Numships; i++) {
        if (ships[i][opp] != null) {
            ships[i][opp].setJustDestroyed(false);
        }
    }

    String typeDestroyedShip = null;

    for (int i = 0; i < Numships; i++) {
        shipDestroyed = true;
        for (int[] pos : ships[i][opp].getOccupiedPositions()) {
            int r = pos[0];
            int c = pos[1];
            Piece p = board[r][c][Player.getCurrentIndex()];
            if (!(p instanceof WhiteSquare || p instanceof Bomb) || p.getPlayer() != currentPlayer) {
                shipDestroyed = false;
                break;
            }
        }

        if (shipDestroyed && !ships[i][opp].getDestroyed()) { // Avoid re-marking already destroyed ships
            ships[i][opp].setJustDestroyed(true); // Only one ship will have this true
            ships[i][opp].setDestroyed(true);

            if (ships[i][opp] instanceof Carrier) {
                typeDestroyedShip = "Carrier";
            } else if (ships[i][opp] instanceof Submarine) {
                typeDestroyedShip = "Submarine";
            } else if (ships[i][opp] instanceof Destroyer) {
                typeDestroyedShip = "Destroyer";
            } else if (ships[i][opp] instanceof Battle_Ship) {
                typeDestroyedShip = "Battleship";
            } else if (ships[i][opp] instanceof Patrol_Boat) {
                typeDestroyedShip = "Patrol Boat";
            }
            shipsDestroyed[currentPlayer]++;
            theShip = typeDestroyedShip;
            break; // Stop checking further ships since only one can be justDestroyed
        }
    }

    shipsdestroyedByCurrentPlayer = destroyedByCurrentPlayer;
    System.out.println("Ships destroyed by player " + (currentPlayer + 1) + ": " + shipsDestroyed[currentPlayer]);

    if (shipsDestroyed[currentPlayer] == Numships) {
        System.out.println("Player " + (currentPlayer + 1) + " wins!!");
        BattleShip.setGameOver(true);
    }
}

    public static boolean isWhiteSquare(int row, int column) {
        for (int k = 0; k < Player.getPlayers().length; k++) {
            if (k != Player.getCurrentIndex()) {
                for (int i = 0; i < ships.length; i++) {
                    if (ships[i][k] != null && ships[i][k].locations(row, column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getVal() {
        return (val);
    }

    public static void AddBomb(int xpixel, int ypixel) {
        int ydelta = Window.getHeight2() / NUM_ROWS;
        int xdelta = Window.getWidth2() / NUM_COLUMNS;
        int modXPixel = xpixel - Window.getX(0);
        int modYPixel = ypixel - Window.getY(0);
        int column = modXPixel / xdelta;
        int row = modYPixel / ydelta;
        boolean a[] = new boolean[9];
        boolean d = true;
        int num = 0;

        if (modXPixel <= 0 || modXPixel >= Window.getWidth2() ||
                modYPixel <= 0 || modYPixel >= Window.getHeight2())
            return;

        for (int r = -1; r < 2; r++) {
            for (int c = -1; c < 2; c++) {
                Bomb bomb = new Bomb(3, Player.getCurrentIndex());
                board[helperRow(row + r)][helperCol(column + c)][Player.getCurrentIndex()] = bomb;
                if (Board2.isWhiteSquare(helperRow(row + r), helperCol(column + c))) {
                    a[num] = false;
                }
                else{
                    a[num] = true;
                }
                num++;
            }
        }

        for(int i = 0; i < a.length;i++) {
            if (!a[i]) {
                Player.getCurrentPlayer().setSum(0);
                d = false;
            }
        }

        if(d){
            Player.getCurrentPlayer().setSum(1);
        }
        Player.getCurrentPlayer().setNumBombs(Player.getCurrentPlayer().getNumBombs() - 1);
    }

    private static int helperRow(int row) {
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

    private static int helperCol(int col) {
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

    public static void Draw(Graphics2D g, BattleShip thisObj) {
        // draw grid
        int ydelta = Window.getHeight2() / NUM_ROWS;
        int xdelta = Window.getWidth2() / NUM_COLUMNS;

        g.setColor(Color.black);
        for (int zi = 1; zi < NUM_ROWS; zi++) {
            g.drawLine(Window.getX(0), Window.getY(zi * ydelta),
                    Window.getX(Window.getWidth2()), Window.getY(zi * ydelta));
        }

        for (int zi = 1; zi < NUM_COLUMNS; zi++) {
            g.drawLine(Window.getX(zi * xdelta), Window.getY(0),
                    Window.getX(zi * xdelta), Window.getY(Window.getHeight2()));
        }

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                Piece p = board[row][col][Player.getCurrentIndex()];
                if (p != null && !(p instanceof Ship) && p.getPlayer() == Player.getCurrentIndex()) {
                    p.draw(g, thisObj, row, col, xdelta, ydelta);
                }
            }
        }

        for (int k = 0; k < Player.getPlayers().length; k++) {
            for (int s = 0; s < Numships; s++) {
                if (k == Player.getCurrentIndex() && !BattleShip.getHideShips(k))
                    ships[s][k].draw(g, thisObj, ships[s][k].getRow(), ships[s][k].getColumn(),
                            xdelta, ydelta);
            }
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (Player.getCurrentPlayer() == Player.getPlayer1()) {
            g.drawString("Player 1s Turn", 50, 60);
            g.drawString("Number of Bombs: " + Player.getPlayer1().getNumBombs(), 250, 60);
            g.drawString("Ships Destroyed: "+  shipsDestroyed[0],  450, 60);
        } else {
            g.drawString("Player 2s Turn", 50, 60);
            g.drawString("Number of Bombs: " + Player.getPlayer2().getNumBombs(), 250, 60);
            g.drawString("Ships Destroyed: "+  shipsDestroyed[1],  450, 60);
        }
        if (BattleShip.getGameOver()) {
            if (Player.getCurrentPlayer() == Player.getPlayer1()) {
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("Player 1s Win", 100, 300);
            } else {
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("Player 2s Win", 100, 300);
            }
        }
        int opponentPlayer;
        if (Player.getCurrentIndex() == 0) {
            opponentPlayer = 1;
            //opp = opponentPlayer;
        } else {
            opponentPlayer = 0;
            //opp = opponentPlayer;
        }
        //drawDestroyed = true;
        for (int i = 0; i < Numships; i++) {
            Ship ship = ships[i][opponentPlayer];
            if (ship != null && ship.getJustDestroyed()) {
                g.setColor(Color.darkGray);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString(theShip + " destroyed!", Window.getX(0), Window.getHeight2() / 2);
                ship.setJustDestroyed(false);
                // try {
                //     Thread.sleep(2000); // 2000 ms = 2 seconds
                // } catch (InterruptedException e) {
                //     e.printStackTrace(); // required to handle InterruptedException
                // }
            }
            //System.out.println(Board.drawDestroyed);
        }
    }
//    public static void Animate(){
//        if (drawDestroyed){
//            boardTimeCount++;
//            System.out.println(boardTimeCount);
//            System.out.println(drawDestroyed);
//        }
//        if(boardTimeCount%20==19){
//            System.out.println("exit the loop");
//            drawDestroyed = false;
//            boardTimeCount = 0;
//            System.out.println(drawDestroyed);
//        }
//    }
    public static boolean checkDestroyed(){
        int opponentPlayer;
        if (Player.getCurrentIndex() == 0) {
            opponentPlayer = 1;
        } else {
            opponentPlayer = 0;
        }
        for (int i = 0; i < Numships; i++) {
            Ship ship = ships[i][opponentPlayer];
            if (ship != null && ship.getJustDestroyed()) {
                drawDestroyed=true;
                return true;
            }
        }
        return false;
    }
}
