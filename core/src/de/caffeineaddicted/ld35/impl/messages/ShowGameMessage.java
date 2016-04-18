package de.caffeineaddicted.ld35.impl.messages;

import de.caffeineaddicted.ld35.logic.Message;

/**
 * Created by malte on 4/16/16.
 */
public class ShowGameMessage implements Message {

    public final boolean hardcore;

    public ShowGameMessage() {
        this(false);
    }

    public ShowGameMessage(boolean hardcore) {
        this.hardcore = hardcore;
    }
}
