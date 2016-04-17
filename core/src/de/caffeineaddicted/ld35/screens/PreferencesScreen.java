package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.MenuScreen;
import de.caffeineaddicted.ld35.messages.PreferencesUpdatedMessage;
import de.caffeineaddicted.ld35.messages.ShowMainMenuMessage;

import static de.caffeineaddicted.ld35.CoffeeGame.CONSTANTS.*;

/**
 * Created by malte on 4/16/16.
 */
public class PreferencesScreen extends MenuScreen {

    float prefMarginTop = 100;
    float prefWidth = 200;
    float prefHeight = 40;
    Label txtPrefMusicMenuActivated, txtPrefMusicMenuVolume;
    CheckBox ckbPrefMusicMenuActivated;
    Slider sldrPrefMusicMenuVolume;
    TextButton btnSave, btnAbort;

    public PreferencesScreen(CoffeeGame g) {
        super(g);
        create();
        setNavigation(NAVIGATION.Horizontal);
        sync();
    }

    public void create() {
        g.debug("Creating PreferensScreen");

        setTitle("LD 35: Preferences");

        int i = 0;

        txtPrefMusicMenuActivated = new Label("Activate music", g.getAssets().get("uiskin.json", Skin.class), "default");
        txtPrefMusicMenuActivated.setPosition(stage.getWidth() / 2 - prefWidth / 2, (stage.getHeight() - prefMarginTop) - (i++) * prefHeight);
        stage.addActor(txtPrefMusicMenuActivated);
        ckbPrefMusicMenuActivated = new CheckBox("Activate music", g.getAssets().get("uiskin.json", Skin.class), "default");
        ckbPrefMusicMenuActivated.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                g.debug("hure");
            }
        });
        ckbPrefMusicMenuActivated.setPosition(stage.getWidth() / 2 - prefWidth / 2, (stage.getHeight() - prefMarginTop) - (i++) * prefHeight);
        stage.addActor(ckbPrefMusicMenuActivated);


        txtPrefMusicMenuVolume = new Label("Activate music", g.getAssets().get("uiskin.json", Skin.class), "default");
        txtPrefMusicMenuVolume.setPosition(stage.getWidth() / 2 - prefWidth / 2, (stage.getHeight() - prefMarginTop) - (i++) * prefHeight);
        stage.addActor(txtPrefMusicMenuVolume);
        sldrPrefMusicMenuVolume = new Slider(0f, 1f, 0.05f, false, g.getAssets().get("uiskin.json", Skin.class));
        sldrPrefMusicMenuVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                g.debug("huren silder");
            }
        });
        sldrPrefMusicMenuVolume.setPosition(stage.getWidth() / 2 - prefWidth / 2, (stage.getHeight() - prefMarginTop) - (i++) * prefHeight);
        stage.addActor(sldrPrefMusicMenuVolume);

        // Save button
        btnSave = new TextButton("Apply", g.getAssets().get("uiskin.json", Skin.class));
        btnSave.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.getPreferences().putBoolean(PREF_KEY_MUSIC_MENU_ACTIVATED, ckbPrefMusicMenuActivated.isChecked());
                g.getPreferences().putFloat(PREF_KEY_MUSIC_MENU_VOLUME, sldrPrefMusicMenuVolume.getValue());
                g.getPreferences().flush();
                g.message(new PreferencesUpdatedMessage());
            }
        });
        btnSave.setWidth(120);
        btnSave.setPosition(stage.getWidth() / 2 - 260, 100);
        addButton(btnSave);

        // Abort button
        btnAbort = new TextButton("Back", g.getAssets().get("uiskin.json", Skin.class));
        btnAbort.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.getPreferences().abort();
                g.message(new ShowMainMenuMessage());
            }
        });
        btnAbort.setWidth(120);
        btnAbort.setPosition(stage.getWidth() / 2 + 130 / 2, 100);
        addButton(btnAbort);

    }

    public void sync() {
        ckbPrefMusicMenuActivated.setChecked(g.getPreferences().getBoolean(PREF_KEY_MUSIC_MENU_ACTIVATED, PREF_DEF_MUSIC_MENU_ACTIVATED));
        sldrPrefMusicMenuVolume.setValue(g.getPreferences().getFloat(PREF_KEY_MUSIC_MENU_VOLUME, PREF_DEF_MUSIC_MENU_VOLUME));
    }
}
