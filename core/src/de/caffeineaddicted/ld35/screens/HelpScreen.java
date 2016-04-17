package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.MenuScreen;
import de.caffeineaddicted.ld35.messages.ShowMainMenuMessage;

import java.util.ArrayList;

/**
 * Created by malte on 4/16/16.
 */
public class HelpScreen extends MenuScreen {

    float txtMarginTop = 100;
    float txtWidth = 380;
    float txtHeight = 25;
    TextButton btnBack;
    String[] txtHelps = { "Help", "Help", "Help", "Help", "Help", "Help", "Help"};
    ArrayList<Label> txtHelpLabels;

    public HelpScreen(CoffeeGame g) {
        super(g);
        create();
    }

    public void create() {
        super.create();
        game.debug("Creating HelpScreen");

        setTitle("LD 35: Help");

        txtHelpLabels = new ArrayList<Label>();
        for (int i = 0; i < txtHelps.length; i++) {
            txtHelpLabels.add(new Label(txtHelps[i], game.getAssets().get("uiskin.json", Skin.class), "default"));
            txtHelpLabels.get(i).setWidth(txtWidth);
            txtHelpLabels.get(i).setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - i * txtHeight);
            stage.addActor(txtHelpLabels.get(i));
        }

        // Back button
        btnBack = new TextButton("Back", game.getAssets().get("uiskin.json", Skin.class));
        btnBack.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.message(new ShowMainMenuMessage());
            }
        });
        btnBack.setWidth(120);
        btnBack.setPosition(stage.getWidth() / 2 - 120 / 2, 100);
        addButton(btnBack);

    }

    @Override
    public void dispose () {

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }
}
