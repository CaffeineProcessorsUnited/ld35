package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
public class CreditsScreen extends MenuScreen {

    CoffeeGame g;
    Stage stage;

    Label title;

    float txtMarginTop = 100;
    float txtWidth = 380;
    float txtHeight = 25;
    TextButton btnBack;
    String[] txtCredits = { "Credits:", "Zwile zwei du kacklappen", "", "People developing this game:", "", "", "bla bla newline test"};
    ArrayList<Label> txtCreditLabels;

    public CreditsScreen(CoffeeGame g) {
        super(g);
        create();
    }

    public void create() {
        g.debug("Creating CreditsScreen");

        setTitle("LD 35: Credits");

        txtCreditLabels = new ArrayList<Label>();
        for (int i = 0; i < txtCredits.length; i++) {
            txtCreditLabels.add(new Label(txtCredits[i], g.getAssets().get("uiskin.json", Skin.class), "default"));
            txtCreditLabels.get(i).setWidth(txtWidth);
            txtCreditLabels.get(i).setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - i * txtHeight);
            stage.addActor(txtCreditLabels.get(i));
        }

        // Back button
        btnBack = new TextButton("Back", g.getAssets().get("uiskin.json", Skin.class));
        btnBack.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                g.message(ShowMainMenuMessage.class);
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
