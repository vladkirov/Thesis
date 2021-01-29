package reversi.gui;

import lombok.Getter;

/**
 * Field coordinates of the game board
 */
public class Coordinate {
    /**
     * Row
     */
    @Getter
    private int row;

    /**
     * Column
     */
    @Getter
    private int col;

    /**
     * Count of chips to flip
     */
    @Getter
    private int count;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Union setter for all fields in object
     * @param row row
     * @param col column
     * @param count count chips
     */
    public void update(int row, int col, int count) {
        this.row = row;
        this.col = col;
        this.count = count;
    }
}
