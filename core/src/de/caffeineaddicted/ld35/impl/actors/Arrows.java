package de.caffeineaddicted.ld35.impl.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    private Image upp;
    private Image downp;
    private Image leftp;
    private Image rightp;
    private CoffeeGame g;

    private float margin = 10f;

    private KeyDisplay kd;

    public Arrows(CoffeeGame g, KeyDisplay kd) {
        this.g = g;
        this.kd = kd;
        create();
    }

    public void create () {
        left =      new Image(g.getAssets().get("key_left.png", Texture.class));
        leftp =     new Image(g.getAssets().get("key_leftp.png", Texture.class));
        down =      new Image(g.getAssets().get("key_down.png", Texture.class));
        downp =     new Image(g.getAssets().get("key_downp.png", Texture.class));
        right =     new Image(g.getAssets().get("key_right.png", Texture.class));
        rightp =    new Image(g.getAssets().get("key_rightp.png", Texture.class));
        up =        new Image(g.getAssets().get("key_up.png", Texture.class));
        upp =       new Image(g.getAssets().get("key_upp.png", Texture.class));

        setSize(left.getWidth() + down.getWidth() + right.getWidth() + 2 * margin, down.getHeight() + up.getHeight() + margin);

        left.setPosition(0, 0);
        leftp.setPosition(0, 0);
        down.setPosition(up.getWidth() + margin, 0);
        downp.setPosition(up.getWidth() + margin, 0);
        right.setPosition(up.getWidth() + left.getWidth() + 2 * margin, 0);
        rightp.setPosition(up.getWidth() + left.getWidth() + 2 * margin, 0);
        up.setPosition(down.getX(), down.getHeight() + margin);
        upp.setPosition(down.getX(), down.getHeight() + margin);



        addActor(up);
        addActor(down);
        addActor(left);
        addActor(right);
        addActor(upp);
        addActor(downp);
        addActor(leftp);
        addActor(rightp);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        if (kd.getHud().getGameScreen().isRunning()) {
            up.setVisible(!Gdx.input.isKeyPressed(Input.Keys.UP));
            upp.setVisible(Gdx.input.isKeyPressed(Input.Keys.UP));
            down.setVisible(!Gdx.input.isKeyPressed(Input.Keys.DOWN));
            downp.setVisible(Gdx.input.isKeyPressed(Input.Keys.DOWN));
            left.setVisible(!Gdx.input.isKeyPressed(Input.Keys.LEFT));
            leftp.setVisible(Gdx.input.isKeyPressed(Input.Keys.LEFT));
            right.setVisible(!Gdx.input.isKeyPressed(Input.Keys.RIGHT));
            rightp.setVisible(Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        }
        super.draw(batch, parentAlpha);
    }

}
