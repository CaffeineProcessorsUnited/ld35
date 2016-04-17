package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Screen;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.screens.GameOverScreen;
import de.caffeineaddicted.ld35.screens.CoffeeScreen;

import java.util.*;

/**
 * Created by Malte on 17.04.2016.
 */
public class RootScreen implements Screen {

    public enum ZINDEX {
        FAREST(0), FAR(1), MID(2), NEAR(3), NEAREST(4);

        public final Integer value;

        ZINDEX(Integer value) {
            this.value = value;
        }

        static ArrayList<ZINDEX> asSortedArray() {
            ArrayList<ZINDEX> list = new ArrayList<ZINDEX>(Arrays.asList(ZINDEX.values()));
            Collections.sort(list, new Comparator<ZINDEX>() {
                @Override
                public int compare(ZINDEX o1, ZINDEX o2) {
                    return o1.value.compareTo(o2.value);
                }
            });
            return list;
        }
    };

    private CoffeeGame game;
    private Map<Class<? extends CoffeeScreen>, CoffeeScreen> screens;
    private Map<ZINDEX, Class<? extends CoffeeScreen>> activeScreens;

    private boolean renderWhilePasued = true;
    private boolean paused = false;

    public RootScreen(CoffeeGame game) {
        this.game = game;
        screens = new HashMap<Class<? extends CoffeeScreen>, CoffeeScreen>();
        activeScreens = new HashMap<ZINDEX, Class<? extends CoffeeScreen>>();
    }

    public void loadScreen(CoffeeScreen screen) {
        loadScreen(screen, false);
    }

    public void loadScreen(CoffeeScreen screen, boolean override) {
        if (screens.containsKey(screen) && !override) {
            throw new RuntimeException("A screen of this type is already loaded. If you try to replace it u can either delete it and then add the screen oder use the override switch");
        } else {
            screens.put(screen.getClass(), screen);
        }
    }

    public void unloadScreen(Class<? extends CoffeeScreen> screen) {
        activeScreens.remove(screen);
        screens.remove(screen);
    }

    public void showScreen(ZINDEX zindex, Class<? extends CoffeeScreen> screen) {
        if (screens.get(activeScreens.get(zindex)) != null)
            screens.get(activeScreens.get(zindex)).hide();

        activeScreens.put(zindex, screen);
        screens.get(screen).show();
    }

    public void hideScreen(ZINDEX zindex) {
        if (screens.get(activeScreens.get(zindex)) != null) {
            screens.get(activeScreens.get(zindex)).hide();
            activeScreens.put(zindex, null);
        }
    }

    public <T> T get(Class<T> screenclass) {
        return (T) screens.get(screenclass);
    }

    private void renderIfNotNull(ZINDEX zindex, float delta) {
        Class<? extends CoffeeScreen> screenclass;
        if ((screenclass = activeScreens.get(zindex)) != null) {
            CoffeeScreen screen;
            if ((screen = screens.get(screenclass)) != null) {
                screen.render(delta);
            }
        }
    }
    
    /*
    Redirect interface calls to affected children
     */

    @Override
    public void show() {
        // we have to present a screen
        for (CoffeeScreen screen: screens.values()) {
            screen.show();
        }
    }

    public void render(float delta) {
        render(delta, false);
    }

    /**
     * Renders all currently active screens. Lower index gets drawn to the background.
     */
    public void render(float delta, boolean forceRender) {
        if (paused && !renderWhilePasued && !forceRender) {
            return;
        }
        for (ZINDEX zindex: ZINDEX.asSortedArray()) {
            //game.debug("Rendering screen: " + zindex.name());
            renderIfNotNull(zindex, delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        // let's just resize any screen
        for (CoffeeScreen screen: screens.values()) {
            screen.resize(width, height);
        }
    }

    @Override
    public void pause() {
        // we paused the game
        paused = true;
        game.debug("U can't pause root!");
        for (CoffeeScreen screen: screens.values()) {
            screen.pause();
        }
    }

    @Override
    public void resume() {
        // we resumed the game
        paused = false;
        game.debug("Unleash the kraken");
        for (CoffeeScreen screen: screens.values()) {
            screen.resume();
        }
    }

    @Override
    public void hide() {
        // screen got hidden
        for (CoffeeScreen screen: screens.values()) {
            screen.hide();
        }
    }

    @Override
    public void dispose() {
        // why would you do that?
        for (CoffeeScreen screen: screens.values()) {
            screen.dispose();
        }
    }
}
