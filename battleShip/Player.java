package battleShip;

import java.awt.Color;

public class Player {
    final private static int numPlayers = 2;
    private static Player players[] = new Player[numPlayers];
    private static Player currentPlayer;
    private int sum;
    private int numBombs;
    private static final int originalBombs = 1;

    // Class methods.
    public static void Reset() {
        // If we have not created any instances yet, create the instances of the 2
        // players.
        // Have the first player be the current player.
        if (players[0] == null) {
            players[0] = new Player();
            players[1] = new Player();
        }
        currentPlayer = players[0];
        getPlayer1().setSum(0);
        getPlayer2().setSum(0);
        getPlayer1().setNumBombs(originalBombs);
        getPlayer2().setNumBombs(originalBombs);
    }

    public static Player getCurrentPlayer() {
        return (currentPlayer);
    }

    public static int getCurrentIndex() {
        if (getCurrentPlayer() == players[0])
            return 0;
        else
            return 1;
    }

    public static void switchCurrentPlayer() {
        if (currentPlayer == players[1])
            currentPlayer = players[0];
        else
            currentPlayer = players[1];
    }

    public static Player getPlayer1() {
        return (players[0]);
    }

    public static Player getPlayer2() {
        return (players[1]);
    }

    public static Player[] getPlayers() {
        return (players);
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int _sum) {
        sum = _sum;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public void setNumBombs(int num) {
        numBombs = num;
    }

    public Player() {
        sum = 0;
        numBombs = originalBombs;
    }
}
