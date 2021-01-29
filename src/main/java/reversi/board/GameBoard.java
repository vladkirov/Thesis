package reversi.board;

import lombok.Getter;
import reversi.gui.Coordinate;

/**
 * Game board of {@link reversi.games.Game} built from {@link BoardField}
 */
public class GameBoard implements GameBoardStatistic {
    /**
     * Size of square by field count, optimal - (8 x 8)
     */
    private final int size;

    /**
     * Color of active player
     */
    @Getter
    private ChipColor colorPlayer;

    /**
     * Array (size x size)
     */
    public BoardField[][] fields;

    public GameBoard(int size) {
        this.size = size;
        startPosition();
    }

    /**
     * Inverse color active player (change active player)
     */
    public void nextPlayer() {
        colorPlayer = ChipColor.getInvertedColor(colorPlayer);
    }

    /**
     * Set start position for new game
     */
    public void startPosition() {
        fields = new BoardField[size][size];
        fields[3][3] = BoardField.createWhite();
        fields[4][4] = BoardField.createWhite();
        fields[3][4] = BoardField.createBlack();
        fields[4][3] = BoardField.createBlack();

        colorPlayer = ChipColor.WHITE;
    }

    /**
     * Make step (only after check exist next step!). Check field is Enemy at each direction:
     * checking all fields around the current counterclockwise, starting from the upper left corner.
     * Next get cost by each direction and flip chips if is positive.
     * @param row row
     * @param col column
     */
    public void makeStep(int row, int col) {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (!(i == 0 && j == 0))
                    if (isEnemy(row + i, col + j))
                        if (getCostDirection(row, i, col, j) > 0)
                            flipChipsByDirection(row, i, col, j);
    }

    /**
     * Flip chip from current field to field with same color by direction.
     * Double cycle on two parameters
     * @param row row
     * @param rowInc direct for row
     * @param col column
     * @param colInc direct for column
     */
    private void flipChipsByDirection(int row, int rowInc, int col, int colInc) {
        fields[row][col] = new BoardField(colorPlayer);
        fields[row + rowInc][col + colInc].flip();

        for (int k = row + (2 * rowInc), l = col + (2 * colInc);
             k >= 0 && k < size && l >= 0 && l < size;
             k += rowInc, l += colInc) {

            if (inSquare(k, l) && fields[k][l] != null && fields[k][l].getColor() == colorPlayer) return;
            else if (fields[k][l] != null) fields[k][l].flip();
        }
    }

    /**
     * Get full cost of current field by all directions. Check field is Enemy at each direction:
     * checking all fields around the current counterclockwise, starting from the upper left corner.
     * @param row row
     * @param col column
     * @return count chips for flip
     */
    public int getCost(int row, int col) {
        int cost = 0;
        if (fields[row][col] == null)
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    if (!(i == 0 && j == 0))
                        if (isEnemy(row + i, col + j))
                            cost += getCostDirection(row, i, col, j);
        return cost;
    }

    /**
     * Get cost by direction. Count chips other color until a field with the same color is encountered.
     * Double cycle on two parameters
     * @param row row
     * @param rowInc direct for row
     * @param col column
     * @param colInc direct for column
     * @return count chips for flip
     */
    private int getCostDirection(int row, int rowInc, int col, int colInc) {
        int costDirection = 0;
        for (int k = row + (2 * rowInc), l = col + (2 * colInc);
             k >= 0 && k < size && l >= 0 && l < size;
             k += rowInc, l += colInc) {

            costDirection++;

            if (inSquare(k, l) && fields[k][l] != null && fields[k][l].getColor() == colorPlayer)
                return costDirection;
            else if (fields[k][l] == null) return 0;
        }
        return 0;
    }

    /**
     * Check Enemy by coordinates
     * @param row row
     * @param col column
     * @return true if color same {@link GameBoard#colorPlayer}
     */
    private boolean isEnemy(int row, int col) {
        if (inSquare(row, col))
            if (fields[row][col] != null)
                return fields[row][col].getColor() == ChipColor.getInvertedColor(colorPlayer);
        return false;
    }

    @Override
    public boolean inSquare(int row, int col) {
        return ((row >= 0) && (col >= 0) && (row < size) && (col < size));
    }

    /**
     * Checking the existence of a next step to the first positive value
     * @return true if exist
     */
    public boolean isExistStep() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (getCost(i, j) > 0) return true;
        return false;
    }

    /**
     * AI best step analysis (MinMax algorithm in future)
     * @return best coordinates (row, col, countChips) {@link Coordinate}
     */
    public Coordinate getCoordinateFieldOfBestStep() {
        Coordinate coordinate = new Coordinate(-1, -1);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                int countFlipChips = getCost(i, j);
                if (countFlipChips > coordinate.getCount()) coordinate.update(i, j, countFlipChips);
            }
        return coordinate;
    }

    @Override
    public ChipColor getColorByIndex(int row, int col) {
        return (inSquare(row, col) && fields[row][col] != null) ? fields[row][col].getColor() : null;
    }

    /**
     * Get count chip by color
     * @param chipColor color
     * @return count chips
     */
    private int getCountChips(ChipColor chipColor) {
        int count = 0;
        for (BoardField[] gameFieldsRow : fields)
            for (BoardField chip : gameFieldsRow)
                if (chip != null && chip.getColor() == chipColor) count++;
        return count;
    }

    @Override
    public int getCountWhites() {
        return getCountChips(ChipColor.WHITE);
    }

    @Override
    public int getCountBlacks() {
        return getCountChips(ChipColor.BLACK);
    }
}
