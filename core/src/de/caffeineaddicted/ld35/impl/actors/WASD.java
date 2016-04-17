package de.caffeineaddicted.ld35.impl.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.caffeineaddicted.ld35.CoffeeGame;

/**
 * Created by maria on 16.04.16.
 */
public class WASD extends Group {

    private Actor a;
    private Actor w;
    private Actor s;
    private Actor d;
    private CoffeeGame g;

    public WASD(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create () {
        a = new Image(g.getAssets().get("key_a.png", Texture.class));
        a.setPosition(0, 0);
        s = new Image(g.getAssets().get("key_s.png", Texture.class));
        s.setPosition(a.getWidth(), 0);
        w = new Image(g.getAssets().get("key_w.png", Texture.class));
        w.setPosition(a.getWidth(),s.getHeight());
        d = new Image(g.getAssets().get("key_d.png", Texture.class));
        d.setPosition(a.getWidth()+s.getWidth(), 0);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
    }

}
