package de.caffeineaddicted.ld35.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by malte on 4/16/16.
 */
public class Assets extends AssetManager {

    public void preload() {
        load("loading_background.jpg", Texture.class);
        load("menu_background.png", Texture.class);
    }

    public void load() {
        load("theme_game.wav", Music.class);
        load("uiskin.json", Skin.class);
        load("key_a.png", Texture.class);
        load("key_ap.png", Texture.class);
        load("key_w.png", Texture.class);
        load("key_wp.png", Texture.class);
        load("key_s.png", Texture.class);
        load("key_sp.png", Texture.class);
        load("key_d.png", Texture.class);
        load("key_dp.png", Texture.class);
        load("key_space.png", Texture.class);
        load("key_spacep.png", Texture.class);

        load("key_left.png", Texture.class);
        load("key_leftp.png", Texture.class);
        load("key_down.png", Texture.class);
        load("key_downp.png", Texture.class);
        load("key_right.png", Texture.class);
        load("key_rightp.png", Texture.class);
        load("key_up.png", Texture.class);
        load("key_upp.png", Texture.class);

        load("BluetilesTexture.png", Texture.class);
        load("GreyTriagTexture.png", Texture.class);
        load("BrickTexture.png", Texture.class);

        load("unicorn.png", Texture.class);
        load("unicorninverted.png", Texture.class);
        load("square.png", Texture.class);
        load("squareinverted.png", Texture.class);
        load("circle.png", Texture.class);
        load("circleinverted.png", Texture.class);
        load("star.png", Texture.class);
        load("starinverted.png", Texture.class);
        load("triangle.png", Texture.class);
        load("triangleinverted.png", Texture.class);

        load("rainbow.png", Texture.class);
    }
}
