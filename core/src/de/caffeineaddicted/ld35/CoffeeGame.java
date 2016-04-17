package de.caffeineaddicted.ld35;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.caffeineaddicted.ld35.input.GlobalInputProcessor;
import de.caffeineaddicted.ld35.messages.*;
import de.caffeineaddicted.ld35.screens.*;

import static de.caffeineaddicted.ld35.CoffeeGame.CONSTANTS.*;

public class CoffeeGame extends MessageBasedGame {

    public static class CONSTANTS {

        public final static String PREFERENCES_FILENAME = "caffeine-ld35";

        public final static String  PREF_KEY_MUSIC_MENU_ACTIVATED = "music_menu_activated";
        public final static boolean PREF_DEF_MUSIC_MENU_ACTIVATED = true;

        public final static String  PREF_KEY_MUSIC_MENU_VOLUME = "music_menu_volume";
        public final static  float   PREF_DEF_MUSIC_MENU_VOLUME = 1.f;
    }

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Assets assets;
    private Highscores highscores;
    private GameScreen gamescreen;
    private CoffeeScreenInputMultiplexer screenInput;

    Preferences preferences;

    private Music menuMusic;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
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
        assets.preload();
        highscores = new Highscores();
        preferences = new Preferences(PREFERENCES_FILENAME);
        setScreen(new LoadingScreen(this));

    }

    public void message(Message message) {
        if (message == null) {
            message = new DefaultMessage();
        }
        // Here comes the logic
        if (message.getClass() == ExitGameMessage.class) {
            debug("Bye");
            Gdx.app.exit();
        }
        if (message.getClass() == FinishedLoadingMessage.class) {
            debug("Received message after finishing loading assets");
            menuMusic = getAssets().get("theme_game.wav", Music.class);
            menuMusic.setLooping(true);
            loadPreferences();
            setScreen(new MainMenuScreen(this));
        }
        if (message.getClass() == GameOverMessage.class) {
            debug("Go to the lose screen");
            setScreen(new GameOverScreen(this, ((GameOverMessage) message).score));
        }
        if(message.getClass() == PauseGameMessage.class){
            debug("Showing the pause screen");
            if(gamescreen != null){
                gamescreen.pause();
            }
            setScreen(new PauseScreen(this));
        }
        if (message.getClass() == PreferencesUpdatedMessage.class) {
            debug("Preferences have changed. Update Interface");
            getPreferences().flush();
            loadPreferences();
        }
        if (message.getClass() == ResumeGameMessage.class) {
            debug("Resuming Game");
            //setScreen(new MainMenuScreen(this));
            if(gamescreen != null){
                gamescreen.resume();
            }
            setScreen(gamescreen);
        }
        if (message.getClass() == ShowCreditsMessage.class) {
            debug("Showing the credits screen");
            setScreen(new CreditsScreen(this));
        }
        if (message.getClass() == ShowGameMessage.class) {
            debug("Showing the game screen");
            if(gamescreen == null)
                gamescreen = new GameScreen(this);
            setScreen(gamescreen);
        }
        if (message.getClass() == ShowHighscoresMessage.class) {
            debug("Showing the highscore screen");
            setScreen(new HighscoresScreen(this));
        }
        if (message.getClass() == ShowMainMenuMessage.class) {
            debug("Go to main menu screen");
            setScreen(new MainMenuScreen(this));
        }
        if (message.getClass() == ShowPreferenceScreen.class) {
            debug("Showing the preference screen");
            setScreen(new PreferencesScreen(this));
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

    public boolean reloadScreen() {
        if (CreditsScreen.class.isInstance(getScreen())) {
            setScreen(new CreditsScreen(this));
        } else if (GameScreen.class.isInstance(getScreen())) {
            setScreen(new GameScreen(this));
        } else if (HighscoresScreen.class.isInstance(getScreen())) {
            setScreen(new HighscoresScreen(this));
        } else if (LoadingScreen.class.isInstance(getScreen())) {
            setScreen(new LoadingScreen(this));
        } else if (MainMenuScreen.class.isInstance(getScreen())) {
            setScreen(new MainMenuScreen(this));
        } else {
            return false;
        }
        return true;
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

}
