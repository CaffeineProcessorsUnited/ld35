package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.MenuScreen;
import de.caffeineaddicted.ld35.messages.ResumeGameMessage;
import de.caffeineaddicted.ld35.messages.ShowMainMenuMessage;

/**
 * Created by malte on 4/16/16.
 */
public class PauseScreen extends MenuScreen {

    float btnMarginTop = 100;
    float btnWidth = 200;
    float btnHeight = 40;
    TextButton btnResume, btnBack;

    public PauseScreen(CoffeeGame g) {
        super(g);
        setNavigation(NAVIGATION.Vertical);
        create();
    }

    public void create() {
        super.create();
        game.debug("Creating PauseScreen");

        setTitle("LD 35: Paused");

        // Play button
        btnResume = new TextButton("Resume game", game.getAssets().get("uiskin.json", Skin.class));
        btnResume.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ResumeGameMessage());
            }
        });
        btnResume.setWidth(btnWidth);
        btnResume.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(0));
        addButton(btnResume);

        // Back button
        btnBack = new TextButton("Back to main menu", game.getAssets().get("uiskin.json", Skin.class));
        btnBack.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ShowMainMenuMessage());
            }
        });
        btnBack.setWidth(btnWidth);
        btnBack.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(2));
        addButton(btnBack);

    }

    private float getButtonY(int number) {
        return (stage.getHeight() - btnMarginTop) - number * btnHeight;
    }
}
