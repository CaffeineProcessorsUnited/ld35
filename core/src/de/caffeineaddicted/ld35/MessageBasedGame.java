package de.caffeineaddicted.ld35;

import com.badlogic.gdx.Game;

/**
 * Created by malte on 4/16/16.
 */
public abstract class MessageBasedGame extends Game {

    public MessageBasedGame() {
    }

    public interface Message {

    }

    public class DefaultMessage implements Message {

    }
}
