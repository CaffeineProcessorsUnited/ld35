package de.caffeineaddicted.ld35.logic;

import com.badlogic.gdx.Game;

/**
 * Created by malte on 4/16/16.
 */
public abstract class MessageBasedGame extends Game {

    public MessageBasedGame() {
        this(null);
    }

    public MessageBasedGame(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
    }

}
