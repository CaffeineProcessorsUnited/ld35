package de.caffeineaddicted.ld35.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import de.caffeineaddicted.ld35.MenuScreen;

/**
 * Created by malte on 4/16/16.
 */
public class MenuInputProcessor implements InputProcessor {

    MenuScreen screen;

    public MenuInputProcessor(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                screen.up();
                break;
            case Input.Keys.DOWN:
                screen.down();
                break;
            case Input.Keys.ENTER:
                screen.click();
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screen.loseOver();
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
