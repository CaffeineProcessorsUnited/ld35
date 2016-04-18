package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.messages.HideModeSelectionScreenMessage;
import de.caffeineaddicted.ld35.impl.messages.ShowGameMessage;
import de.caffeineaddicted.ld35.impl.messages.ShowMainMenuMessage;
import de.caffeineaddicted.ld35.screens.MenuScreen;

/**
 * Created by malte on 4/16/16.
 */
public class ModeSelectionScreen extends MenuScreen {

    int btnMarginTop = 100;
    float btnWidth = 200f;
    float btnHeight = 40f;

    TextButton btnNormal, btnHardcore, btnBack;

    public ModeSelectionScreen(CoffeeGame g) {
        super(g);
        setNavigation(NAVIGATION.Vertical);
    }

    public void create() {
        super.create();
        game.debug("Creating ModeSelectionScreen");
        setTitle("LD 35: Select difficulty");

        int btnCounter = 0;

        // Play button
        btnNormal = new TextButton("Normal", game.getDefaultSkin());
        btnNormal.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.message(new ShowGameMessage(false));
            }
        });
        btnNormal.setWidth(btnWidth);
        btnNormal.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnNormal);

        // Highscores button
        btnHardcore = new TextButton("Hardcore", game.getDefaultSkin());
        btnHardcore.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.message(new ShowGameMessage(true));
            }
        });
        btnHardcore.setWidth(btnWidth);
        btnHardcore.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnHardcore);

        // Exit button
        btnBack = new TextButton("Exit", game.getDefaultSkin());
        btnBack.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.message(new HideModeSelectionScreenMessage());
                game.message(new ShowMainMenuMessage());
            }
        });
        btnBack.setWidth(btnWidth);
        btnBack.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(btnCounter++));
        addButton(btnBack);

    }

    private float getButtonY(int number) {
        game.debug("margin: " + btnMarginTop + ", height: " + stage.getHeight() + ", button " + number + " at " + ((stage.getHeight() - btnMarginTop) - number * btnHeight));
        return (stage.getHeight() - btnMarginTop) - number * btnHeight;
    }
}
