package reversi.board;

import lombok.Getter;

/**
 * Field for {@link GameBoard}
 */
public class BoardField {
    @Getter
    private ChipColor color;

    public BoardField(ChipColor color) {
        this.color = color;
    }

    /**
     * Create field with white chip
     * @return field with color
     */
    public static BoardField createWhite() {
        return new BoardField(ChipColor.WHITE);
    }

    /**
     * Create field with black chip
     * @return field with color
     */
    public static BoardField createBlack() {
        return new BoardField(ChipColor.BLACK);
    }

    /**
     * Change color of the chip to the inverted
     */
    public void flip() {
        if (color != null)
            if (color == ChipColor.WHITE) color = ChipColor.BLACK;
            else color = ChipColor.WHITE;
    }
}
