package de.caffeineaddicted.ld35.actors;

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

    private Actor wasd;
    private Actor arrows;
    private Image space;
    private CoffeeGame g;

    public KeyDisplay(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create () {
        wasd = new WASD(g);
        wasd.setPosition(0, 0);
        arrows = new Arrows(g);
        arrows.setPosition(getWidth() - arrows.getWidth(), 0);
        space = new Image(g.getAssets().get("key_space.png", Texture.class));
        space.setPosition(getWidth() / 2 - space.getWidth() / 2, 0);
        addActor(wasd);
        addActor(space);
        addActor(arrows);
    }

    @Override
    public void draw (Batch batch, float parentAlpha ) {
        super.draw(batch, parentAlpha);
    }

}
