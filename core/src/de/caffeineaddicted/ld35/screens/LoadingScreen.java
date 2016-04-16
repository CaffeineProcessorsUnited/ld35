package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.messages.FinishedLoadingMessage;

/**
 * Created by malte on 4/16/16.
 */
public class LoadingScreen implements Screen {

    private CoffeeGame g;
    private Sprite background;

    private float height = 15f;
    private float percent_width = 0.8f;
    private float margin_bottom = 40f;
    private float line_thickness = 2f;

    private float bar_r = 0.957f;
    private float bar_g = 0f;
    private float bar_b = 0.541f;
    private float bar_a = 0.8f;

    private float time = 0;
    private float wait_time = 0.5f;
    private float min_time = 1.5f;

    public LoadingScreen(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create(){
        g.debug("Creating LoadingScreen");

        g.getAssets().preload();

        g.getAssets().finishLoading();

        Texture texBackground = g.getAssets().get("loading_background.jpg", Texture.class);
        texBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background = new Sprite(texBackground);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        g.getAssets().load();
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        g.getBatch().begin();
        background.draw(g.getBatch());
        g.getBatch().end();

        g.getShape().setColor(bar_r, bar_g, bar_b, bar_a);
        Gdx.gl.glLineWidth(line_thickness);

        g.getShape().begin(ShapeRenderer.ShapeType.Line);
        g.getShape().rect(x(), y(), w(), h());
        g.getShape().end();

        g.getShape().begin(ShapeRenderer.ShapeType.Filled);
        g.getShape().rect(x(), y(), wp(), h());
        g.getShape().end();

        g.debug("Drawing rect: " + x() + ", " + y() + ", " + w() + ", " + h());
        g.debug("Drawing box: " + x() + ", " + y() + ", " + wp() + ", " + h());

        if (time >= wait_time) {
            if (g.getAssets().update()) {
                if (time >= min_time) {
                    g.debug("Finished loading assets");
                    g.message(new FinishedLoadingMessage());
                }
            } else {
                g.debug("Loading assets " + (g.getAssets().getProgress() * 100) + " %");
            }
        }
        time += delta;
        g.debug(time + "");
    }

    private float x() {
        return ((1 - percent_width) / 2) * Gdx.graphics.getWidth();
    }

    private float y() {
        return margin_bottom;
    }

    private float w() {
        return (Gdx.graphics.getWidth() * percent_width);
    }

    private float wp() {
        return w() * (g.getAssets().getProgress() - (time >= (min_time * 0.7) ? 0f : 0.15f));
    }

    private float h() {
        return height;
    }

    @Override
    public void resize (int width, int height) {
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
