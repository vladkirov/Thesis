package reversi.players;

import lombok.Getter;
import reversi.board.ChipColor;

/**
 * Player in the game
 */
public abstract class Player {
    @Getter
    protected String nickName;

    /**
     * Black or White
     */
    @Getter
    protected ChipColor color;

    /**
     * Computer or Player
     */
    @Getter
    protected boolean isAI;
}
