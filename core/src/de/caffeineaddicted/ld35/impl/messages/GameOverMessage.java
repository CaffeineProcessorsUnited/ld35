package de.caffeineaddicted.ld35.impl.messages;

import de.caffeineaddicted.ld35.logic.Message;

/**
 * Created by malte on 4/16/16.
 */
public class GameOverMessage implements Message {

    public final int score;

    public GameOverMessage(int score) {
        this.score = score;
    }
}
