package de.caffeineaddicted.ld35.messages;

import de.caffeineaddicted.ld35.MessageBasedGame;

/**
 * Created by malte on 4/16/16.
 */
public class GameOverMessage implements MessageBasedGame.Message {

    public final int score;

    public GameOverMessage(int score) {
        this.score = score;
    }
}
