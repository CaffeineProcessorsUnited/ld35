package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.logic.Bundle;
import de.caffeineaddicted.ld35.logic.Message;
import de.caffeineaddicted.ld35.screens.MenuScreen;
import de.caffeineaddicted.ld35.impl.messages.ShowMainMenuMessage;

import java.util.ArrayList;

/**
 * Created by malte on 4/16/16.
 */
public class CreditsScreen extends MenuScreen {

    float txtMarginTop = 100;
    float txtWidth = 600;
    TextButton btnBack;
    String credits = "Credits:\n" +
            "This game is our submission for the Ludum Dare 35!\n" +
            "A lot of coffee, cola, chicken wings, curry, chili and chocolate" +
            " (all these things start wit an c...coincidence?) " +
            " was consumed during development.\n" +
            "\n" +
            "People developing this game:\n" +
            "Malte Heinzelmann\n" +
            "Niels Bernloehr\n" +
            "Felix Richter\n" +
            "Maria Stepanov\n" +
            "You can find us under CaffeineProccesorsUnited on Github: https://github.com/CaffeineProcessorsUnited";
    ArrayList<Label> txtCreditLabels;

    public CreditsScreen(CoffeeGame g) {
        super(g);
    }

    public void create() {
        super.create();
        game.debug("Creating CreditsScreen");

        setTitle("LD 35: Credits");

        /*txtCreditLabels = new ArrayList<Label>();
        for (int i = 0; i < txtCredits.length; i++) {
            txtCreditLabels.add(new Label(txtCredits[i], game.getAssets().get("uiskin.json", Skin.class), "default"));
            txtWidth = Math.max(txtWidth, txtCreditLabels.get(i).getWidth());
            stage.addActor(txtCreditLabels.get(i));
        }
        for (int i = 0; i < txtCreditLabels.size(); i++) {
            txtCreditLabels.get(i).setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - i * txtHeight);
        }
        */
        Label txtCredits  = new Label(credits, game.getAssets().get("uiskin.json", Skin.class), "default");
        txtCredits.setWrap(true);

        txtCredits.pack();
        txtCredits.setWidth(txtWidth);

        txtCredits.pack();
        txtCredits.setWidth(txtWidth);

        txtCredits.setPosition(stage.getWidth() / 2 - txtCredits.getWidth() / 2, stage.getHeight() - txtMarginTop - txtCredits.getHeight());
        stage.addActor(txtCredits);

        // Back button
        btnBack = new TextButton("Back", game.getAssets().get("uiskin.json", Skin.class));
        btnBack.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.message(new ShowMainMenuMessage());
            }
        });
        btnBack.setWidth(120);
        btnBack.setPosition(stage.getWidth() / 2 - 120 / 2, 100);
        addButton(btnBack);

    }
}
