package de.caffeineaddicted.ld35.impl.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.caffeineaddicted.ld35.CoffeeGame;

/**
 * Created by maria on 16.04.16.
 */
public class KeyDisplay extends Group {

    private HUD hud;

    private Actor wasd;
    private Actor arrows;
    private Image space;
    private Image spacep;
    private CoffeeGame g;

    public KeyDisplay(CoffeeGame g, HUD hud) {
        this.g = g;
        this.hud = hud;
        create();
    }

    public void create () {
        wasd = new WASD(g);
        arrows = new Arrows(g);
        space = new Image(g.getAssets().get("key_space.png", Texture.class));
        spacep = new Image(g.getAssets().get("key_spacep.png", Texture.class));
        setSize(hud.getWidth(), Math.max(wasd.getHeight(), Math.max(arrows.getHeight(), space.getHeight())));
        wasd.setPosition(0, 0);
        space.setPosition(wasd.getWidth() * wasd.getScaleX() + 20, 0);
        spacep.setPosition(wasd.getWidth() * wasd.getScaleX() + 20, 0);
        arrows.setPosition(getWidth() - arrows.getWidth(), 0);

        spacep.setVisible(false);

        addActor(wasd);
        addActor(space);
        addActor(spacep);
        addActor(arrows);
    }

    @Override
    public void setScale (float scaleX, float scaleY) {
        wasd.setScale(scaleX, scaleY);
        arrows.setScale(scaleX, scaleY);
        space.setScale(scaleX, scaleY);
        spacep.setScale(scaleX, scaleY);
    }

    @Override
    public void draw (Batch batch, float parentAlpha ) {
        wasd.setPosition(0, 0);
        space.setPosition(wasd.getWidth() * wasd.getScaleX() + 20, 0);
        spacep.setPosition(wasd.getWidth() * wasd.getScaleX() + 20, 0);
        arrows.setPosition(getWidth() - arrows.getWidth() * arrows.getScaleX(), 0);
        space.setVisible(!Gdx.input.isKeyPressed(Input.Keys.SPACE));
        spacep.setVisible(Gdx.input.isKeyPressed(Input.Keys.SPACE));
        super.draw(batch, parentAlpha);
    }

}
