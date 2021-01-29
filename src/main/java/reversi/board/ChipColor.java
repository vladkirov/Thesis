package reversi.board;

/**
 * Colors for chips on
 */
public enum ChipColor {
    BLACK,
    WHITE;

    /**
     * Get inverse color based on input color
     * @param chipColor color for invert
     * @return inverted color
     */
    public static ChipColor getInvertedColor(ChipColor chipColor) {
        if (chipColor == BLACK) return WHITE;
        else return BLACK;
    }
}
