package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.MenuScreen;
import de.caffeineaddicted.ld35.messages.*;

/**
 * Created by malte on 4/16/16.
 */
public class MainMenuScreen extends MenuScreen {

    int btnMarginTop = 100;
    float btnWidth = 200f;
    float btnHeight = 40f;

    TextButton btnPlay, btnHighscores, btnHelp, btnSettings, btnCredits, btnExit;

    public MainMenuScreen(CoffeeGame g) {
        super(g);
        setNavigation(NAVIGATION.Vertical);
        create();
    }

    public void create() {
        super.create();
        game.debug("Creating MainMenuScreen");
        setTitle("LD 35");

        int btnCounter = 0;

        // Play button
        btnPlay = new TextButton("Play", game.getAssets().get("uiskin.json", Skin.class));
        btnPlay.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ShowGameMessage());
            }
        });
        btnPlay.setWidth(btnWidth);
        btnPlay.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnPlay);

        // Highscores button
        btnHighscores = new TextButton("Highscores", game.getAssets().get("uiskin.json", Skin.class));
        btnHighscores.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ShowHighscoresMessage());
            }
        });
        btnHighscores.setWidth(btnWidth);
        btnHighscores.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnHighscores);

        // Help button
        btnHelp = new TextButton("Help", game.getAssets().get("uiskin.json", Skin.class));
        btnHelp.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new GameOverMessage(42));
            }
        });
        btnHelp.setWidth(btnWidth);
        btnHelp.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnHelp);

        // Settings button
        btnSettings = new TextButton("Settings", game.getAssets().get("uiskin.json", Skin.class));
        btnSettings.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ShowPreferenceScreen());
            }
        });
        btnSettings.setWidth(btnWidth);
        btnSettings.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnSettings);

        // Credits button
        btnCredits = new TextButton("Credits", game.getAssets().get("uiskin.json", Skin.class));
        btnCredits.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ShowCreditsMessage());
            }
        });
        btnCredits.setWidth(btnWidth);
        btnCredits.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnCredits);

        // Exit button
        btnExit = new TextButton("Exit", game.getAssets().get("uiskin.json", Skin.class));
        btnExit.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ExitGameMessage());
            }
        });
        btnExit.setWidth(btnWidth);
        btnExit.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnExit);

    }

    private float getButtonY(int number) {
        game.debug("margin: " + btnMarginTop + ", height: " + stage.getHeight() + ", button " + number + " at " + ((stage.getHeight() - btnMarginTop) - number * btnHeight));
        return (stage.getHeight() - btnMarginTop) - number * btnHeight;
    }
}
