package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.input.GameInputProcessor;

/**
 * Created by malte on 4/16/16.
 */
public class GameScreen implements Screen {

    CoffeeGame g;
    PerspectiveCamera camera;


    public GameScreen(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create(){
        g.debug("Creating GameScreen");
        // give it to the multiplexer
        //Gdx.input.setInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GameInputProcessor());
        Gdx.input.setInputProcessor(multiplexer);

    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        g.getBatch().setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize (int width, int height) {
        g.debug("Im resizing...");
        float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
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
