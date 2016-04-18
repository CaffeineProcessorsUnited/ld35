package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.screens.CoffeeScreen;

/**
 * Created by malte on 18.04.16.
 */
public class BackgroundScreen  extends CoffeeScreen {

    private Sprite background;

    public BackgroundScreen(CoffeeGame game) {
        super(game);
    }

    public void create() {
        game.debug("Creating BackgroundScreen");
        Texture texBackground = game.getAssets().get("menu_background.png", Texture.class);
        texBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background = new Sprite(texBackground);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        background.draw(game.getBatch());
        game.getBatch().end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.getShape().begin(ShapeRenderer.ShapeType.Filled);
        game.getShape().setColor(0.2f, 0.2f, 0.2f, 0.5f);
        game.getShape().rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getShape().end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}