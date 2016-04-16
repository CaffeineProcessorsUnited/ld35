package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.messages.ShowMainMenuMessage;

/**
 * Created by malte on 4/16/16.
 */
public class LoadingScreen implements Screen {

    CoffeeGame g;
    Stage stage;

    public LoadingScreen(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create(){
        g.debug("Creating LoadingScreen");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        g.getAssets().load();
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if (g.getAssets().update()) {
            g.debug("Finished loading assets");
            g.message(ShowMainMenuMessage.class);
        } else {
            g.debug("Loading assets " + (g.getAssets().getProgress() * 100) + " %");
        }
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
