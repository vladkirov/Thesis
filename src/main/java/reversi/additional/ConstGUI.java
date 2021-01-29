package reversi.additional;

import reversi.board.ChipColor;

/**
 * Constants for visualization
 */
public class ConstGUI {
    public static final String PATH_IMG_BACK = "/img/back.png";
    public static final String PATH_IMG_WHITE = "/img/wh_50.png";
    public static final String PATH_IMG_BLACK = "/img/bl_50.png";

    public static final int FIELD_COUNT = 8;
    public static final int FIELD_PADDING = 5;

    public static final long SECOND_IMITATE_THINKING_AI = 1;

    public static final String WINDOW_MAIN_TITLE = "Reversi";
    /**
     * Get relative path to image for field by color form game board
     * @param chipColor color of Chip
     * @return path to white or black img (for white and black color) and path to background img in all other cases
     */
    public static String getPathByColor(ChipColor chipColor) {
        if (chipColor == ChipColor.BLACK) return PATH_IMG_BLACK;
        if (chipColor == ChipColor.WHITE) return PATH_IMG_WHITE;
        return PATH_IMG_BACK;
    }
}
