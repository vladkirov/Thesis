package reversi.board;

import reversi.additional.ConstGUI;

/**
 * Facade for safety access to private {@link GameBoard} in {@link reversi.games.Game}
 */
public interface GameBoardStatistic {
    /**
     * Get count white chips on game board
     */
    int getCountWhites();

    /**
     * Get count black chips on game board
     * @return count chips
     */
    int getCountBlacks();

    /**
     * Get color chip by coordinates (from 0 to {@link ConstGUI#FIELD_COUNT} - 1 )
     * @param row row
     * @param col column
     * @return color or null
     */
    ChipColor getColorByIndex(int row, int col);

    /**
     * Check coordinates by game board
     * @param row row
     * @param col column
     * @return true, if coordinates is square of game board
     */
    boolean inSquare(int row, int col);
}
