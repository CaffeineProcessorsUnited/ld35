package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.MenuScreen;
import de.caffeineaddicted.ld35.messages.ShowMainMenuMessage;

/**
 * Created by malte on 4/16/16.
 */
public class GameOverScreen extends MenuScreen {

    float txtMarginTop = 100;
    float txtWidth = 380;
    float txtHeight = 25;
    final int score;
    Label txtScoreLabel, txtScore, txtNameLabel;
    TextField inputName;
    TextButton btnEnter, btnAbort;

    public GameOverScreen(CoffeeGame g, int score) {
        super(g);
        this.score = score;
        create();
    }

    public void create() {
        g.debug("Creating GameOverScreen");

        setTitle("LD 35: Noob");

        int i = 0;

        txtScoreLabel = new Label("Score:", g.getAssets().get("uiskin.json", Skin.class), "default");
        txtScoreLabel.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(txtScoreLabel);

        txtScore = new Label(score + "", g.getAssets().get("uiskin.json", Skin.class), "default");
        txtScore.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(txtScore);

        txtNameLabel = new Label("Score:", g.getAssets().get("uiskin.json", Skin.class), "default");
        txtNameLabel.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(txtNameLabel);

        inputName = new TextField("", g.getAssets().get("uiskin.json", Skin.class), "default");
        inputName.setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - (i++) * txtHeight);
        stage.addActor(inputName);

        // Save button
        btnEnter = new TextButton("Enter", g.getAssets().get("uiskin.json", Skin.class));
        btnEnter.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.getHighscores().addScore(inputName.getText(), score);
            }
        });
        btnEnter.setWidth(120);
        btnEnter.setPosition(stage.getWidth() / 2 - 260, 100);
        addButton(btnEnter);

        // Abort button
        btnAbort = new TextButton("Back", g.getAssets().get("uiskin.json", Skin.class));
        btnAbort.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.message(new ShowMainMenuMessage());
            }
        });
        btnAbort.setWidth(120);
        btnAbort.setPosition(stage.getWidth() / 2 + 130 / 2, 100);
        addButton(btnAbort);

    }

    @Override
    public void dispose () {

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

}
