package de.caffeineaddicted.ld35.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.caffeineaddicted.ld35.CoffeeGame;

/**
 * Created by maria on 16.04.16.
 */
public class Arrows extends Group {

    private Image up;
    private Image down;
    private Image left;
    private Image right;
    private CoffeeGame g;

    public Arrows(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create () {
        up = new Image(g.getAssets().get("key_up.png", Texture.class));
        up.setPosition(left.getWidth(),down.getHeight());
        down = new Image(g.getAssets().get("key_down.png", Texture.class));
        down.setPosition(up.getWidth(),0);
        left = new Image(g.getAssets().get("key_left.png", Texture.class));
        left.setPosition(0,0);
        right = new Image(g.getAssets().get("key_right.png", Texture.class));
        right.setPosition(up.getWidth()+left.getWidth(),0);

        addActor(up);
        addActor(down);
        addActor(left);
        addActor(right);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
