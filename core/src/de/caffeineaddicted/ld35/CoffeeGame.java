package de.caffeineaddicted.ld35;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.caffeineaddicted.ld35.impl.screens.*;
import de.caffeineaddicted.ld35.impl.input.GlobalInputProcessor;
import de.caffeineaddicted.ld35.impl.messages.*;
import de.caffeineaddicted.ld35.input.CoffeeScreenInputMultiplexer;
import de.caffeineaddicted.ld35.logic.Message;
import de.caffeineaddicted.ld35.logic.MessageBasedGame;
import de.caffeineaddicted.ld35.screens.RootScreen;
import de.caffeineaddicted.ld35.util.Assets;
import de.caffeineaddicted.ld35.util.Highscores;
import de.caffeineaddicted.ld35.util.Preferences;

import static de.caffeineaddicted.ld35.CoffeeGame.CONSTANTS.*;

public class CoffeeGame extends MessageBasedGame {

    Preferences preferences;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Assets assets;
    private Highscores highscores;
    private CoffeeScreenInputMultiplexer screenInput;
    private RootScreen rootScreen;
    private Music menuMusic;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_ERROR);
        debug("Creating game");
        loadPreferences();
        InputMultiplexer multiplexer = new InputMultiplexer();
        screenInput = new CoffeeScreenInputMultiplexer();
        multiplexer.addProcessor(new GlobalInputProcessor(this));
        multiplexer.addProcessor(screenInput);
        Gdx.input.setInputProcessor(multiplexer);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        assets = new Assets();
        assets.getLogger().setLevel(Logger.ERROR);
        assets.preload();
        highscores = new Highscores(this);
        preferences = new Preferences(PREFERENCES_FILENAME);
        rootScreen = new RootScreen(this);
        setScreen(rootScreen);
        initScreens();
        assets.finishLoading();
        rootScreen.showScreen(BackgroundScreen.class, RootScreen.ZINDEX.FAREST);
        rootScreen.showScreen(LoadingScreen.class, RootScreen.ZINDEX.FAR);
    }

    private void initScreens() {
        rootScreen.loadScreen(new BackgroundScreen(this));
        rootScreen.loadScreen(new CreditsScreen(this));
        rootScreen.loadScreen(new GameOverScreen(this));
        rootScreen.loadScreen(new GameScreen(this));
        rootScreen.loadScreen(new HelpScreen(this));
        rootScreen.loadScreen(new HighscoresScreen(this));
        rootScreen.loadScreen(new LoadingScreen(this));
        rootScreen.loadScreen(new MainMenuScreen(this));
        rootScreen.loadScreen(new PauseMenuScreen(this));
        rootScreen.loadScreen(new PreferencesScreen(this));
    }

    public void message(Message message) {
        if (message == null) {
            message = new DefaultMessage();
        }
        debug("messagereceived", message.getClass().getSimpleName());
        // Here comes the logic
        if (message.getClass() == AbortGameMessage.class) {
            rootScreen.hideScreen(GameScreen.class);
            message(new HidePauseMenuMessage());
            message(new ShowMainMenuMessage());
        }
        if (message.getClass() == ExitGameMessage.class) {
            debug("Bye");
            Gdx.app.exit();
        }
        if (message.getClass() == FinishedLoadingMessage.class) {
            debug("Received message after finishing loading assets");
            menuMusic = getAssets().get("theme_game.wav", Music.class);
            menuMusic.setLooping(true);
            loadPreferences();
            message(new ShowMainMenuMessage());
        }
        if (message.getClass() == GameOverMessage.class) {
            debug("Go to the lose screen");
            if (rootScreen.isLoaded(GameScreen.class)) {
                rootScreen.get(GameScreen.class).pause();
            }
            rootScreen.get(GameOverScreen.class).onMessageReceived(message);
            rootScreen.showScreen(GameOverScreen.class, RootScreen.ZINDEX.NEAR);
        }
        if (message.getClass() == HideGameOverMenuMessage.class) {
            debug("Leave the lose screen");
            rootScreen.hideScreen(GameOverScreen.class);
        }
        if(message.getClass() == PauseGameMessage.class){
            debug("Showing the pause screen");
            if (rootScreen.isLoaded(GameScreen.class) && !rootScreen.get(GameScreen.class).isPaused()) {
                rootScreen.get(GameScreen.class).pause();
            }
            if (rootScreen.get(GameScreen.class).isVisible()) {
                rootScreen.showScreen(PauseMenuScreen.class, RootScreen.ZINDEX.NEAR);
            }
        }
        if (message.getClass() == PreferencesUpdatedMessage.class) {
            debug("Preferences have changed. Update Interface");
            getPreferences().flush();
            loadPreferences();
        }
        if (message.getClass() == ResumeGameMessage.class) {
            debug("Resuming Game");
            if (rootScreen.isLoaded(GameScreen.class)) {
                rootScreen.get(GameScreen.class).resume();
                rootScreen.get(GameScreen.class).setDoDraw(true);
            }
        }
        if (message.getClass() == ShowPauseScreenMessage.class) {
            rootScreen.showScreen(PauseMenuScreen.class, RootScreen.ZINDEX.NEAR);
        }
        if (message.getClass() == HidePauseMenuMessage.class) {
            rootScreen.hideScreen(PauseMenuScreen.class);
        }
        if (message.getClass() == ShowCreditsMessage.class) {
            debug("Showing the credits screen");
            rootScreen.showScreen(CreditsScreen.class, RootScreen.ZINDEX.FAR);
        }
        if (message.getClass() == ShowGameMessage.class) {
            debug("Showing the game screen");
            rootScreen.showScreen(GameScreen.class, RootScreen.ZINDEX.MID);
            message(new HideMainMenuMessage());
        }
        if (message.getClass() == ShowHighscoresMessage.class) {
            debug("Showing the highscore screen");
            rootScreen.showScreen(HighscoresScreen.class, RootScreen.ZINDEX.FAR);
        }
        if (message.getClass() == ShowMainMenuMessage.class) {
            debug("Go to main menu screen");
            rootScreen.showScreen(MainMenuScreen.class, RootScreen.ZINDEX.FAR);
        }
        if (message.getClass() == HideMainMenuMessage.class) {
            debug("Go to main menu screen");
            rootScreen.hideScreen(MainMenuScreen.class);
        }
        if (message.getClass() == ShowPreferenceScreen.class) {
            debug("Showing the preference screen");
            rootScreen.showScreen(PreferencesScreen.class, RootScreen.ZINDEX.FAR);
        }
        if (message.getClass() == ToggleFullscreenMessage.class) {
            debug("Toggeling fullscreen");
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
    }

    public void loadPreferences() {
        if (menuMusic != null) {
            if (getPreferences().getBoolean(PREF_KEY_MUSIC_MENU_ACTIVATED, PREF_DEF_MUSIC_MENU_ACTIVATED)) {
                menuMusic.play();
            } else {
                menuMusic.stop();
            }
            menuMusic.setVolume(getPreferences().getFloat(PREF_KEY_MUSIC_MENU_VOLUME, PREF_DEF_MUSIC_MENU_VOLUME));
        }
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShape() {
        return shape;
    }

    public Assets getAssets() {
        return assets;
    }

    public Highscores getHighscores() {
        return highscores;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public CoffeeScreenInputMultiplexer getScreenInput() {
        return screenInput;
    }

    public String getLogTag() {
        return getLogTag("");
    }

    public String getLogTag(String sub) {
        return "CoffeeGame" + (!sub.isEmpty() ? ":" + sub : "");
    }

    public void log(String message) {
        Gdx.app.log(getLogTag(), message);
    }

    public void debug(String message) {
        debug("", message);
    }

    public void debug(String sub, String message) {
        Gdx.app.debug(getLogTag(sub), message);
    }

    public void error(String message) {
        error("", message);
    }

    public void error(String sub, String message) {
        Gdx.app.error(getLogTag(sub), message);
    }

    public Viewport createViewport() {
        //return new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return new ScreenViewport();
        //return new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //return new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()
    }

    public static class CONSTANTS {

        public final static String PREFERENCES_FILENAME = "caffeine-ld35";

        /*
        Preferences keys
         */
        public final static String  PREF_KEY_MUSIC_MENU_ACTIVATED = "music_menu_activated";
        public final static boolean PREF_DEF_MUSIC_MENU_ACTIVATED = true;

        public final static String  PREF_KEY_MUSIC_MENU_VOLUME = "music_menu_volume";
        public final static float   PREF_DEF_MUSIC_MENU_VOLUME = 1.0f;
        /*
        Bundle keys
         */
        public static final String BUNDLE_SCORE = "score";
    }

}
