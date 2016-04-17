package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Screen;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.listener.MessageReceiver;
import de.caffeineaddicted.ld35.logic.Bundle;
import de.caffeineaddicted.ld35.logic.Message;

/**
 * Created by malte on 4/17/16.
 */
public  abstract class CoffeeScreen implements Screen, MessageReceiver {
    public final CoffeeGame game;
    private boolean visible;

    public CoffeeScreen(CoffeeGame game) {
        this.game = game;
        visible = false;
    }

    public abstract void render(float delta);

    @Override
    public void show() {
        visible = true;
        game.debug(getClass().getSimpleName(), "visible=" + (visible ? "true" : "false"));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        visible = false;
        game.debug(getClass().getSimpleName(), "visible=" + (visible ? "true" : "false"));
    }

    @Override
    public void dispose() {

    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void onMessageReceived(Message message) {
        onMessageReceived(message, new Bundle());
    }

    @Override
    public void onMessageReceived(Message message, Bundle bundle) {

    }
}

