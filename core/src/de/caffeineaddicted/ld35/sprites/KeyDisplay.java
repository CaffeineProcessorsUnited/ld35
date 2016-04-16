package de.caffeineaddicted.ld35.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import de.caffeineaddicted.ld35.CoffeeGame;

/**
 * Created by maria on 16.04.16.
 */
public class KeyDisplay extends Sprite {

    private SpriteBatch batch;
    private Texture arrow;
    private ShapeRenderer shapes;
    private PerspectiveCamera camera;
    private Sprite sarrow;
    private CoffeeGame g;

    public KeyDisplay(CoffeeGame g) {
        this.g = g;
        create();
    }

    public void create () {
        shapes = new ShapeRenderer();
        batch = new SpriteBatch();
        camera = new PerspectiveCamera();
        arrow = g.getAssets().get("arrow.png", Texture.class);
        sarrow = new Sprite(arrow);
    }

    public void render () {
        camera.update();
        shapes.setProjectionMatrix(camera.combined);

        float xc = this.getX();
        float yc = this.getY();
        float height = this.getHeight();
        float width = this.getWidth();

        shapes.begin(ShapeType.Line);

        shapes.setColor(Color.PINK);
        shapes.circle(xc + (width/6), yc + (height/4), (float)0.8*(width/6));
        shapes.end();

        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.PINK);
        shapes.circle(xc + (width/2), yc + (height/4),  (float)0.8*(width/6));
        shapes.end();

        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.PINK);
        shapes.circle(xc + (width*5/6), yc + (height/4),  (float)0.8*(width/6));
        shapes.end();

        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.PINK);
        shapes.circle(xc + (width/2), yc + (height*3/4),  (float)0.8*(width/6));

        shapes.end();

        batch.begin();
        //    ^
        //  < v >
        sarrow.setSize((float)1.2*sarrow.getWidth(), (float)1.2*sarrow.getHeight());
        batch.draw(sarrow, xc + 2*(width/3), yc); // ->
        sarrow.rotate90(true);
        batch.draw(sarrow, xc + (width/3), yc); // v
        sarrow.rotate90(true);
        batch.draw(sarrow, xc, yc); // <-
        sarrow.rotate90(true);
        batch.draw(sarrow, xc + (width/3), yc + height); // ^

        batch.end();

    }

}
