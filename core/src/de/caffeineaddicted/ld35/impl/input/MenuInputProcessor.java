package de.caffeineaddicted.ld35.impl.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import de.caffeineaddicted.ld35.screens.MenuScreen;

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
                switch (screen.getNavigation()) {
                    case Both:
                    case Vertical:
                        screen.prev();
                        break;
                }
                break;
            case Input.Keys.DOWN:
                switch (screen.getNavigation()) {
                    case Both:
                    case Vertical:
                        screen.next();
                        break;
                }
                break;
            case Input.Keys.LEFT:
                switch (screen.getNavigation()) {
                    case Both:
                    case Horizontal:
                        screen.prev();
                        break;
                }
                break;
            case Input.Keys.RIGHT:
                switch (screen.getNavigation()) {
                    case Both:
                    case Horizontal:
                        screen.next();
                        break;
                }
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
