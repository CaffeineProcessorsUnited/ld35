package de.caffeineaddicted.ld35.impl.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.screens.GameScreen;

/**
 * Created by maria on 16.04.16.
 */
public class ObstacleDisplay extends Group {

    private Image down;
    private Image right;
    private Image up;
    private Image left;

    private CoffeeGame g;

    private float margin = 10f;

    private HUD hud;

    public ObstacleDisplay(CoffeeGame g, HUD hud) {
        this.g = g;
        this.hud = hud;
        create();
    }

    public void create () {
        down =  new Image(g.getAssets().get("unicorn.png",Texture.class));
        right = new Image(g.getAssets().get("unicorn.png",Texture.class));
        up =    new Image(g.getAssets().get("unicorn.png",Texture.class));
        left =  new Image(g.getAssets().get("unicorn.png",Texture.class));

        setSize(down.getWidth()+right.getWidth()+left.getWidth()+2*margin,Math.max(left.getHeight(), up.getHeight() + Math.max(down.getHeight(), right.getHeight())) + margin);

        left.setPosition(0, 0);
        down.setPosition(left.getWidth() + margin, 0);
        up.setPosition(down.getX(), down.getHeight() + margin);
        right.setPosition(left.getWidth() + down.getWidth() + 2 * margin, 0);

        addActor(left);
        addActor(right);
        addActor(up);
        addActor(down);
    };

    private float scaledWidth(Actor a) {
        return a.getWidth() * a.getScaleX();
    }

    private float scaledHeight(Actor a) {
        return a.getHeight() * a.getScaleY();
    }


    @Override
    public void setScale (float scaleX, float scaleY) {
        up.setScale(scaleX, scaleY);
        down.setScale(scaleX, scaleY);
        left.setScale(scaleX, scaleY);
        right.setScale(scaleX, scaleY);
        setSize(scaledWidth(down)+scaledWidth(right)+scaledWidth(left)+2*margin,
                Math.max(scaledHeight(left), scaledHeight(up) + Math.max(scaledHeight(down), scaledHeight(right))) + margin);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (hud.getGameScreen().isRunning()) {
            left.setDrawable(new TextureRegionDrawable(new TextureRegion(hud.getGameScreen().GetTextureByIncomingShape(2,false))));
            right.setDrawable(new TextureRegionDrawable(new TextureRegion(hud.getGameScreen().GetTextureByIncomingShape(0,false))));
            up.setDrawable(new TextureRegionDrawable(new TextureRegion(hud.getGameScreen().GetTextureByIncomingShape(3,false))));
            down.setDrawable(new TextureRegionDrawable(new TextureRegion(hud.getGameScreen().GetTextureByIncomingShape(1,false))));
        }
        left.setPosition(0, 0);
        down.setPosition(scaledWidth(left) + margin, 0);
        up.setPosition(down.getX(), scaledHeight(down) + margin);
        right.setPosition(scaledWidth(left) + scaledWidth(down) + 2 * margin, 0);
        super.draw(batch, parentAlpha);
    }

}
