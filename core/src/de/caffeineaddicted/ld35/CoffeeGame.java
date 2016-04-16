package de.caffeineaddicted.ld35;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.caffeineaddicted.ld35.messages.*;
import de.caffeineaddicted.ld35.screens.*;

public class CoffeeGame extends MessageBasedGame {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Assets assets;
    private Highscores highscores;

    private Music menuMusic;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        debug("Creating game");
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        assets = new Assets();
        assets.preload();
        highscores = new Highscores();
        setScreen(new LoadingScreen(this));
    }

    public void message(Class<? extends Message> message) {
        if (message == null) {
            message = new DefaultMessage().getClass();
        }
        // Here comes the logic
        if (message == ShowMainMenuMessage.class) {
            debug("Received message after finishing loading assets");
            // Finished loading show menu screen
            setScreen(new MainMenuScreen(this));
        }
        if (message == ShowGameMessage.class) {
            debug("Showing the game screen");
            setScreen(new GameScreen(this));
        }
        if (message == ShowCreditsMessage.class) {
            debug("Showing the credits screen");
            setScreen(new CreditsScreen(this));
        }
        if (message == ShowHighscoresMessage.class) {
            debug("Showing the highscore screen");
            setScreen(new HighscoresScreen(this));
        }
        if (message == ExitGameMessage.class) {
            debug("Bye");
            Gdx.app.exit();
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

    public void setMusic(M) {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/music_menu.ogg");
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    public String getLogTag() {
        return "CoffeeGame";
    }

    public void log(String message) {
        Gdx.app.log(getLogTag(), message);
    }

    public void debug(String message) {
        Gdx.app.debug(getLogTag(), message);
    }

    public void error(String message) {
        Gdx.app.error(getLogTag(), message);
    }

}
