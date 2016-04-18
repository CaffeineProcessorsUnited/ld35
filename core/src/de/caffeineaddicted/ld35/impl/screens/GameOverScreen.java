package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.messages.AbortGameMessage;
import de.caffeineaddicted.ld35.impl.messages.HideGameOverMenuMessage;
import de.caffeineaddicted.ld35.impl.messages.ShowMainMenuMessage;
import de.caffeineaddicted.ld35.logic.Bundle;
import de.caffeineaddicted.ld35.logic.Message;
import de.caffeineaddicted.ld35.screens.MenuScreen;

/**
 * Created by malte on 4/16/16.
 */
public class GameOverScreen extends MenuScreen {

    float txtMarginTop = 100;
    float txtWidth = 380;
    float txtHeight = 25;
    Label txtScoreLabel, txtScore, txtNameLabel;
    TextField inputName;
    TextButton btnEnter, btnAbort;
    private int score = 0;

    public GameOverScreen(CoffeeGame g) {
        super(g);
        setNavigation(NAVIGATION.Horizontal);
    }

    public void create() {
        super.create();
        game.debug("Creating GameOverScreen");

        setTitle("LD 35: Noob");

        int i = 0;

        txtScoreLabel = new Label("Score:", game.getDefaultSkin(), "default");
        txtScoreLabel.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(txtScoreLabel);

        txtScore = new Label(score + "", game.getDefaultSkin(), "default");
        txtScore.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(txtScore);

        txtNameLabel = new Label("Score:", game.getDefaultSkin(), "default");
        txtNameLabel.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(txtNameLabel);

        inputName = new TextField("", game.getDefaultSkin(), "default");
        inputName.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(inputName);

        // Save button
        btnEnter = new TextButton("Enter", game.getDefaultSkin());
        btnEnter.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.getHighscores().addScore(inputName.getText(), score);
                game.message(new AbortGameMessage());
                game.message(new HideGameOverMenuMessage());
                game.message(new ShowMainMenuMessage());
            }
        });
        btnEnter.setWidth(120);
        btnEnter.setPosition(stage.getWidth() / 2 - 260, 100);
        addButton(btnEnter);

        // Abort button
        btnAbort = new TextButton("Back", game.getDefaultSkin());
        btnAbort.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.message(new AbortGameMessage());
                game.message(new HideGameOverMenuMessage());
                game.message(new ShowMainMenuMessage());
            }
        });
        btnAbort.setWidth(120);
        btnAbort.setPosition(stage.getWidth() / 2 + 130 / 2, 100);
        addButton(btnAbort);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.getShape().begin(ShapeRenderer.ShapeType.Filled);
        game.getShape().setColor(0f, 0f, 0f, 0.6f);
        game.getShape().rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getShape().end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        super.render(delta);
    }

    @Override
    public void onMessageReceived(Message message, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        score = bundle.get(CoffeeGame.CONSTANTS.BUNDLE_SCORE, Integer.class, 0);
    }

}
