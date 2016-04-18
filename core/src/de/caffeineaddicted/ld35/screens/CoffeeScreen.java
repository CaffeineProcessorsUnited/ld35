package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Screen;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.listener.MessageReceiver;
import de.caffeineaddicted.ld35.logic.Bundle;
import de.caffeineaddicted.ld35.logic.Message;
import de.caffeineaddicted.ld35.messages.DefaultMessage;

/**
 * Created by malte on 4/17/16.
 */
public  abstract class CoffeeScreen implements Screen, MessageReceiver {
    public final CoffeeGame game;
    private boolean paused;
    private boolean visible;
    private boolean created;

    public CoffeeScreen(CoffeeGame game) {
        this.game = game;
        created = false;
        created = false;
        visible = false;
    }

    public abstract void render(float delta);

    public abstract void create();

    @Override
    public void show() {
        if (!created) {
            create();
        }
        visible = true;
        game.debug(getClass().getSimpleName(), "visible=" + (visible ? "true" : "false"));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
        visible = false;
        game.debug(getClass().getSimpleName(), "visible=" + (visible ? "true" : "false"));
    }

    @Override
    public void dispose() {

    }

    public boolean isCreated() {
        return created;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void onMessageReceived(Message message) {
        onMessageReceived(message, new Bundle());
    }

    @Override
    public void onMessageReceived(Bundle bundle) {
        onMessageReceived(new DefaultMessage(), bundle);
    }

    @Override
    public void onMessageReceived(Message message, Bundle bundle) {

    }
}

