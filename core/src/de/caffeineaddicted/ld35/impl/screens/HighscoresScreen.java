package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.screens.MenuScreen;
import de.caffeineaddicted.ld35.impl.messages.ShowMainMenuMessage;

import java.util.ArrayList;

/**
 * Created by malte on 4/16/16.
 */
public class HighscoresScreen extends MenuScreen {

    float txtMarginTop = 100;
    float txtWidth = 400;
    float txtHeight = 25;

    TextButton btnBack;
    ArrayList<Label> txtScores;

    public HighscoresScreen(CoffeeGame g) {
        super(g);
        setNavigation(NAVIGATION.Horizontal);
    }

    public void create() {
        super.create();
        game.debug("Creating CreditsScreen");
        setTitle("LD 35: Highscores");

        txtScores = new ArrayList<Label>();
        for (int i = 0; i < Math.min(game.getHighscores().getScores().size(), 10); i++) {
            txtScores.add(new Label(game.getHighscores().getScores().get(i).getName() + "" + game.getHighscores().getScores().get(i).getScore(), game.getAssets().get("uiskin.json", Skin.class), "default"));
            txtScores.get(i).setWidth(txtWidth);
            txtScores.get(i).setPosition(stage.getWidth() / 2 - txtWidth / 2, (stage.getHeight() - txtMarginTop) - i * txtHeight);
            stage.addActor(txtScores.get(i));
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
}
