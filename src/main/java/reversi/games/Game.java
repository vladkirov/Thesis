package reversi.games;

import reversi.additional.ConstGUI;
import reversi.board.GameBoardStatistic;
import reversi.gui.Coordinate;
import reversi.players.Player;

public interface Game {
    /**
     * Initialize a new game (set parameters by default and clear game board)
     */
    void newGame();

    /**
     * Start game process
     */
    void start();

    /**
     * Finish game process
     */
    void finish();

    /**
     * Check the game in progress
     */
    boolean notProgress();

    /**
     * Trying to make step by coordinates (from 0 to {@link ConstGUI#FIELD_COUNT} - 1 )
     * @param row row
     * @param col column
     * @return true, if step was made
     */
    boolean makeStep(int row, int col);

    /**
     * Get Facade of {@link reversi.board.GameBoard}
     * @return facade of game board {@link GameBoardStatistic}
     */
    GameBoardStatistic getGameBoardStatistic();

    /**
     * Get active player of the game
     * @return active player {@link Player}
     */
    Player getActivePlayer();

    /**
     * AI best step analysis (MinMax algorithm in future)
     * @return best coordinates (row, col, countChips) {@link Coordinate}
     */
    Coordinate getCoordinatesOfBestStep();
}
