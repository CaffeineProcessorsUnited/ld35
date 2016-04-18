package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.messages.AbortGameMessage;
import de.caffeineaddicted.ld35.impl.messages.HidePauseMenuMessage;
import de.caffeineaddicted.ld35.screens.MenuScreen;
import de.caffeineaddicted.ld35.impl.messages.ResumeGameMessage;
import de.caffeineaddicted.ld35.impl.messages.ShowMainMenuMessage;

/**
 * Created by malte on 4/16/16.
 */
public class PauseMenuScreen extends MenuScreen {

    float btnMarginTop = 100;
    float btnWidth = 200;
    float btnHeight = 40;
    TextButton btnResume, btnBack;

    public PauseMenuScreen(CoffeeGame g) {
        super(g);
        setNavigation(NAVIGATION.Vertical);
    }

    public void create() {
        super.create();
        game.debug("Creating PauseMenuScreen");

        setTitle("LD 35: Paused");

        // Play button
        btnResume = new TextButton("Resume game", game.getAssets().get("uiskin.json", Skin.class));
        btnResume.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ResumeGameMessage());
                game.message(new HidePauseMenuMessage());
            }
        });
        btnResume.setWidth(btnWidth);
        btnResume.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(0));
        addButton(btnResume);

        // Back button
        btnBack = new TextButton("Back to main menu", game.getAssets().get("uiskin.json", Skin.class));
        btnBack.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new AbortGameMessage());
            }
        });
        btnBack.setWidth(btnWidth);
        btnBack.setPosition(stage.getWidth() / 2 - btnWidth / 2, getButtonY(2));
        addButton(btnBack);

    }

    public void render (float delta) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.getShape().begin(ShapeRenderer.ShapeType.Filled);
        game.getShape().setColor(0.2f, 0.2f, 0.2f, 0.4f);
        game.getShape().rect(0, 0, stage.getWidth(), stage.getHeight());
        game.getShape().end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        super.render(delta);
    }

    private float getButtonY(int number) {
        return (stage.getHeight() - btnMarginTop) - number * btnHeight;
    }
}
