package de.caffeineaddicted.ld35;

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
        load("music/theme", Music.class);
    }

    public void load() {
        load("badlogic.jpg", Texture.class);
        load("menu_background.jpg", Texture.class);
        load("uiskin.json", Skin.class);
    }
}
