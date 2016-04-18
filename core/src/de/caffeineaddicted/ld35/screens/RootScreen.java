package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import de.caffeineaddicted.ld35.CoffeeGame;

import java.util.*;

/**
 * Created by Malte on 17.04.2016.
 */
public class RootScreen implements Screen {

    private CoffeeGame game;

    ;
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

    public void showScreen(Class<? extends CoffeeScreen> screenclass, ZINDEX zindex) {
        hideScreen(zindex);
        CoffeeScreen screen;
        if ((screen = screens.get(screenclass)) != null) {
            activeScreens.put(zindex, screenclass);
            game.debug("showing screen " + screenclass.getSimpleName() + " on " + zindex.name());
            screen.show();
            screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else {
            game.error("The screen " + screenclass.getSimpleName() + " is not loaded! Load it before u show it!");
        }
    }

    public void hideScreen(ZINDEX zindex) {
        Class<? extends CoffeeScreen> oldScreenclass;
        if ((oldScreenclass = activeScreens.get(zindex)) != null) {
            CoffeeScreen oldScreen;
            if ((oldScreen = screens.get(oldScreenclass)) != null) {
                game.error("hiding screen " + oldScreenclass.getSimpleName() + " on " + zindex.name());
                activeScreens.put(zindex, null);
                oldScreen.hide();
            }
        }
    }

    public void hideScreen(Class<? extends CoffeeScreen> screenclass) {
        ZINDEX zindex = null;
        for (Map.Entry<ZINDEX, Class<? extends CoffeeScreen>> pair : activeScreens.entrySet()) {
            if (pair.getValue() == screenclass) {
                zindex = pair.getKey();
                break;
            }
        }
        if (zindex != null) {
            hideScreen(zindex);
        }
    }

    public boolean isLoaded(Class<? extends CoffeeScreen> screenclass) {
        return screens.containsKey(screenclass);
    }

    public <T> T get(Class<T> screenclass) {
        return (T) screens.get(screenclass);
    }

    private void renderIfNotNull(ZINDEX zindex, float delta) {
        Class<? extends CoffeeScreen> screenclass;
        if ((screenclass = activeScreens.get(zindex)) != null) {
            CoffeeScreen screen;
            if ((screen = screens.get(screenclass)) != null) {
                //game.debug("RootScreen:reder", "rendering " + screenclass.getSimpleName() + " on " + zindex.name());
                screen.render(delta);
            }
        }
    }

    @Override
    public void show() {
        // we have to present a screen
        for (CoffeeScreen screen : screens.values()) {
            screen.show();
        }
    }
    
    /*
    Redirect interface calls to affected children
     */

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
        for (ZINDEX zindex : ZINDEX.asSortedArray()) {
            //game.debug("Rendering screen: " + zindex.name());
            renderIfNotNull(zindex, delta);
        }
        //game.debug("done rendering");
    }

    @Override
    public void resize(int width, int height) {
        // why should i resize not drawn screens? they get resized when the are shown
        for (ZINDEX zindex : ZINDEX.asSortedArray()) {
            Class<? extends CoffeeScreen> screenclass;
            if ((screenclass = activeScreens.get(zindex)) != null) {
                CoffeeScreen screen;
                if ((screen = screens.get(screenclass)) != null) {
                    screen.resize(width, height);
                }
            }
        }
    }

    @Override
    public void pause() {
        // we paused the game
        paused = true;
        game.debug("U can't pause root!");
        for (CoffeeScreen screen : screens.values()) {
            screen.pause();
        }
    }

    @Override
    public void resume() {
        // we resumed the game
        paused = false;
        game.debug("Unleash the kraken");
        for (CoffeeScreen screen : screens.values()) {
            screen.resume();
        }
    }

    @Override
    public void hide() {
        // screen got hidden
        for (CoffeeScreen screen : screens.values()) {
            screen.hide();
        }
    }

    @Override
    public void dispose() {
        // why would you do that?
        for (CoffeeScreen screen : screens.values()) {
            screen.dispose();
        }
    }

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
    }
}
