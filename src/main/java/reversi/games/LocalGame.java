package reversi.games;

import reversi.board.GameBoardStatistic;
import reversi.gui.Coordinate;
import reversi.players.Player;
import reversi.board.GameBoard;

/**
 * Family of local games
 */
public abstract class LocalGame implements Game {
    protected boolean inProgress;
    protected GameBoard gameBoard;
    protected Player playerWhite;
    protected Player playerBlack;

    /**
     * Only for playerWhite or playerBlack value
     */
    protected Player activePlayer;

    public LocalGame(int size) {
        this.gameBoard = new GameBoard(size);
    }

    @Override
    public boolean notProgress() {
        return !inProgress;
    }

    @Override
    public void newGame() {
        gameBoard.startPosition();
    }

    @Override
    public void start() {
        inProgress = true;
        activePlayer = playerWhite;
    }

    @Override
    public void finish() {
        inProgress = false;
    }

    @Override
    public GameBoardStatistic getGameBoardStatistic() {
        return gameBoard;
    }

    @Override
    public Player getActivePlayer() {
        return activePlayer;
    }

    @Override
    public Coordinate getCoordinatesOfBestStep() {
        return gameBoard.getCoordinateFieldOfBestStep();
    }

    /**
     * Change active player in the game
     */
    protected void nextPlayer() {
        if (activePlayer == playerBlack) activePlayer = playerWhite;
        else activePlayer = playerBlack;

        gameBoard.nextPlayer();
    }
}
