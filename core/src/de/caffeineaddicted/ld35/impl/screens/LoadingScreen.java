package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.messages.FinishedLoadingMessage;
import de.caffeineaddicted.ld35.screens.CoffeeScreen;

/**
 * Created by malte on 4/16/16.
 */
public class LoadingScreen extends CoffeeScreen {

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

    public LoadingScreen(CoffeeGame game) {
        super(game);
    }

    public void create() {
        game.debug("Creating LoadingScreen");

        game.getAssets().preload();

        game.getAssets().finishLoading();

        Texture texBackground = game.getAssets().get("loading_background.jpg", Texture.class);
        texBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background = new Sprite(texBackground);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.getAssets().load();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        game.getBatch().begin();
        background.draw(game.getBatch());
        game.getBatch().end();

        game.getShape().setColor(bar_r, bar_g, bar_b, bar_a);
        Gdx.gl.glLineWidth(line_thickness);

        game.getShape().begin(ShapeRenderer.ShapeType.Line);
        game.getShape().rect(x(), y(), w(), h());
        game.getShape().end();

        game.getShape().begin(ShapeRenderer.ShapeType.Filled);
        game.getShape().rect(x(), y(), wp(), h());
        game.getShape().end();

        game.debug("Drawing rect: " + x() + ", " + y() + ", " + w() + ", " + h());
        game.debug("Drawing box: " + x() + ", " + y() + ", " + wp() + ", " + h());

        if (time >= wait_time) {
            if (game.getAssets().update()) {
                if (time >= min_time) {
                    game.debug("Finished loading assets");
                    game.message(new FinishedLoadingMessage());
                }
            } else {
                game.debug("Loading assets " + (game.getAssets().getProgress() * 100) + " %");
            }
        }
        time += delta;
        game.debug(time + "");
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
        return w() * Math.max(0, (game.getAssets().getProgress() - (time >= (min_time * 0.7) ? 0f : 0.15f)));
    }

    private float h() {
        return height;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

}
