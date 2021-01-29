package reversi.games;

import reversi.additional.ConstGUI;
import reversi.players.Player;

/**
 * Preparation, checking of players and external conditions
 */
public class GameFactory {
    private final Player playerWhite;
    private final Player playerBlack;

    public GameFactory(Player playerWhite, Player playerBlack) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
    }

    /**
     * Create the game "Player versus Player" only for 2 non AI players
     * @return game type {@link LocalPvP}
     */
    public Game createPvP() {
        return !playerBlack.isAI() ? new LocalPvP(playerWhite, playerBlack, ConstGUI.FIELD_COUNT) : null;
    }

    /**
     * Create the game "Player versus Computer" only for AI second player
     * @return game type {@link LocalPvC}
     */
    public Game createPvC() {
        return playerBlack.isAI() ? new LocalPvC(playerWhite, playerBlack, ConstGUI.FIELD_COUNT) : null;
    }
}
