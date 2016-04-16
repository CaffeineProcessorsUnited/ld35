package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.MenuScreen;
import de.caffeineaddicted.ld35.messages.ExitGameMessage;
import de.caffeineaddicted.ld35.messages.ShowCreditsMessage;
import de.caffeineaddicted.ld35.messages.ShowGameMessage;
import de.caffeineaddicted.ld35.messages.ShowHighscoresMessage;

/**
 * Created by malte on 4/16/16.
 */
public class MainMenuScreen extends MenuScreen {

    float btnMarginTop = 100;
    float btnWidth = 200;
    float btnHeight = 40;
    TextButton btnPlay, btnHighscores, btnHelp, btnSettings, btnCredits, btnExit;

    public MainMenuScreen(CoffeeGame g) {
        super(g);
        create();
    }

    public void create() {
        g.debug("Creating MainMenuScreen");
        setTitle("LD 35");

        int btnCounter = 0;


        // Play button
        btnPlay = new TextButton("Play", g.getAssets().get("uiskin.json", Skin.class));
        btnPlay.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.message(ShowGameMessage.class);
            }
        });
        btnPlay.setWidth(btnWidth);
        btnPlay.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnPlay);

        // Highscores button
        btnHighscores = new TextButton("Highscores", g.getAssets().get("uiskin.json", Skin.class));
        btnHighscores.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.message(ShowHighscoresMessage.class);
            }
        });
        btnHighscores.setWidth(btnWidth);
        btnHighscores.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnHighscores);

        // Help button
        btnHelp = new TextButton("Help", g.getAssets().get("uiskin.json", Skin.class));
        btnHelp.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            }
        });
        btnHelp.setWidth(btnWidth);
        btnHelp.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnHelp);

        // Settings button
        btnSettings = new TextButton("Settings", g.getAssets().get("uiskin.json", Skin.class));
        btnSettings.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            }
        });
        btnSettings.setWidth(btnWidth);
        btnSettings.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnSettings);

        // Credits button
        btnCredits = new TextButton("Credits", g.getAssets().get("uiskin.json", Skin.class));
        btnCredits.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.message(ShowCreditsMessage.class);
            }
        });
        btnCredits.setWidth(btnWidth);
        btnCredits.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnCredits);

        // Exit button
        btnExit = new TextButton("Exit", g.getAssets().get("uiskin.json", Skin.class));
        btnExit.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.message(ExitGameMessage.class);
            }
        });
        btnExit.setWidth(btnWidth);
        btnExit.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnExit);

    }

    public void render (float delta) {
        super.render(delta);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    private float getButtonY(int number) {
        return (stage.getHeight() - btnMarginTop) - number * btnHeight;
    }
}
