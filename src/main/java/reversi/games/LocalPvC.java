package reversi.games;

import reversi.players.Player;

/**
 * The game Player versus Computer
 */
public class LocalPvC extends LocalGame {
    public LocalPvC(Player playerWhite, Player playerBlack, int size) {
        super(size);
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
    }

    /**
     * Check current step and make step by coordinates, possibility check next steps
     */
    @Override
    public boolean makeStep(int row, int col) {
        if (gameBoard.inSquare(row, col) && gameBoard.isExistStep() && gameBoard.getCost(row, col) > 0) {
            gameBoard.makeStep(row, col);
            nextPlayer();

            if (!gameBoard.isExistStep()) {
                nextPlayer();
                if (gameBoard.isExistStep()) finish();
            }
            return true;
        }
        return false;
    }
}
