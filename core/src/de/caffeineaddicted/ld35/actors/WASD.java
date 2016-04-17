package de.caffeineaddicted.ld35.actors;

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
public class WASD extends Group {

    private Actor a;
    private Actor w;
    private Actor s;
    private Actor d;
    private Actor ap;
    private Actor wp;
    private Actor sp;
    private Actor dp;
    private CoffeeGame g;

    private float margin = 10f;

    public WASD(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create () {
        a = new Image(g.getAssets().get("key_a.png", Texture.class));
        s = new Image(g.getAssets().get("key_s.png", Texture.class));
        w = new Image(g.getAssets().get("key_w.png", Texture.class));
        d = new Image(g.getAssets().get("key_d.png", Texture.class));
        ap = new Image(g.getAssets().get("key_d.png", Texture.class));
        sp = new Image(g.getAssets().get("key_w.png", Texture.class));
        wp = new Image(g.getAssets().get("key_s.png", Texture.class));
        dp = new Image(g.getAssets().get("key_a.png", Texture.class));

        setSize(a.getWidth() + s.getWidth() + d.getWidth(), Math.max(a.getHeight() + 2 * margin, w.getHeight() + Math.max(s.getHeight(), d.getHeight())) + margin);

        a.setPosition(0, 0);
        ap.setPosition(0, 0);
        s.setPosition(a.getWidth() + margin, 0);
        sp.setPosition(a.getWidth() + margin, 0);
        w.setPosition(s.getX(), s.getHeight() + margin);
        wp.setPosition(s.getX(), s.getHeight() + margin);
        d.setPosition(a.getWidth() + s.getWidth() + 2 * margin, 0);
        dp.setPosition(a.getWidth() + s.getWidth() + 2 * margin, 0);

        ap.setVisible(false);
        sp.setVisible(false);
        dp.setVisible(false);
        wp.setVisible(false);

        addActor(a);
        addActor(s);
        addActor(d);
        addActor(w);
        addActor(ap);
        addActor(sp);
        addActor(dp);
        addActor(wp);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        a.setVisible(!Gdx.input.isKeyPressed(Input.Keys.A));
        ap.setVisible(Gdx.input.isKeyPressed(Input.Keys.A));
        s.setVisible(!Gdx.input.isKeyPressed(Input.Keys.S));
        sp.setVisible(Gdx.input.isKeyPressed(Input.Keys.S));
        d.setVisible(!Gdx.input.isKeyPressed(Input.Keys.D));
        dp.setVisible(Gdx.input.isKeyPressed(Input.Keys.D));
        w.setVisible(!Gdx.input.isKeyPressed(Input.Keys.W));
        wp.setVisible(Gdx.input.isKeyPressed(Input.Keys.W));
        super.draw(batch, parentAlpha);

    }

}
