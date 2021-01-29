package reversi.players;

import reversi.board.ChipColor;

/**
 * Player for local game with only private constructor for Fabric methods
 */
public class LocalPlayer extends Player {
    /**
     * Constructor for human player
     * @param nickName Nick
     * @param color color
     */
    private LocalPlayer(String nickName, ChipColor color) {
        this.nickName = nickName;
        this.color = color;
    }

    /**
     * Constructor for AI player
     * @param nickName Nick
     * @param color color
     * @param isAI true if Computer (AI)
     */
    private LocalPlayer(String nickName, ChipColor color, boolean isAI) {
        this.nickName = nickName;
        this.color = color;
        this.isAI = true;
    }

    /**
     * Create Human player with color White
     * @param nickName Nick
     * @return {@link LocalPlayer}
     * @see Player
     */
    public static LocalPlayer createWhiteHuman(String nickName) {
        if (nickName.isBlank()) nickName = "PlayerWhite";
        return new LocalPlayer(nickName, ChipColor.WHITE);
    }

    /**
     * Create Human player with color Black
     * @param nickName Nick
     * @return {@link LocalPlayer}
     * @see Player
     */
    public static LocalPlayer createBlackHuman(String nickName) {
        if (nickName.isBlank()) nickName = "PlayerBlack";
        return new LocalPlayer(nickName, ChipColor.BLACK);
    }

    /**
     * Create Computer player with color Black
     * @return {@link LocalPlayer}
     * @see Player
     */
    public static LocalPlayer createBlackAI() {
        return new LocalPlayer("ComputerBlack", ChipColor.BLACK, true);
    }
}
