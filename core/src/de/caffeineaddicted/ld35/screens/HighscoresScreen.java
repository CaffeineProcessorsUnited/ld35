package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.messages.ShowMainMenuMessage;

import java.util.ArrayList;

/**
 * Created by malte on 4/16/16.
 */
public class HighscoresScreen implements Screen {

    CoffeeGame g;
    Stage stage;

    Label title;

    float txtMarginTop = 100;
    float txtWidth = 400;
    float txtHeight = 25;
    TextButton btnBack;
    String[] txtCredits = { "Credits:", "Zwile zwei du kacklappen", "", "People developing this game:", "", "", "bla bla newline test"};
    ArrayList<Label> txtScores;

    public HighscoresScreen(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create(){
        g.debug("Creating CreditsScreen");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        title = new Label("LD 35: Highscores", g.getAssets().get("uiskin.json", Skin.class), "title");
        title.setPosition(stage.getWidth() / 2 - title.getWidth() / 2, stage.getHeight() - title.getHeight() - 10);
        stage.addActor(title);

        txtScores = new ArrayList<Label>();
        for (int i = 0; i < Math.min(g.getHighscores().getScores().size(), 10); i++) {
            txtScores.add(new Label(g.getHighscores().getScores().get(i).getName() + "" + g.getHighscores().getScores().get(i).getScore(), g.getAssets().get("uiskin.json", Skin.class), "default"));
            txtScores.get(i).setWidth(txtWidth);
            txtScores.get(i).setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - i * txtHeight);
            stage.addActor(txtScores.get(i));
        }

        // Back button
        btnBack = new TextButton("Back", g.getAssets().get("uiskin.json", Skin.class));
        btnBack.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.message(ShowMainMenuMessage.class);
            }
        });
        btnBack.setWidth(120);
        btnBack.setPosition(stage.getWidth() / 2 - 120 / 2, 100);
        stage.addActor(btnBack);

    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
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
