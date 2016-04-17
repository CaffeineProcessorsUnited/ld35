package de.caffeineaddicted.ld35;

import com.badlogic.gdx.Screen;

/**
 * Created by malte on 4/17/16.
 */
public  abstract class CoffeeScreen implements Screen {
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
}
