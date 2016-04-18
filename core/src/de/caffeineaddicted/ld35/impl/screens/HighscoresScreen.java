package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    float tblMarginTop = 100;
    float tblWidth = 400;

    TextButton btnBack;
    ArrayList<Label> txtNames, txtScores;
    Table table;

    public HighscoresScreen(CoffeeGame g) {
        super(g);
        setNavigation(NAVIGATION.Horizontal);
    }

    public void create() {
        super.create();
        game.debug("Creating CreditsScreen");
        setTitle("LD 35: Highscores");

        txtNames = new ArrayList<Label>();
        txtScores = new ArrayList<Label>();
        table = new Table();
        table.setWidth(tblWidth);
        //ptable.setDebug(true);
        for (int i = 0; i < Math.min(game.getHighscores().getScores().size(), 10); i++) {
            txtNames.add(new Label(game.getHighscores().getScores().get(i).getName(), game.getAssets().get("uiskin.json", Skin.class), "default"));
            txtScores.add(new Label("" + game.getHighscores().getScores().get(i).getScore(), game.getAssets().get("uiskin.json", Skin.class), "default"));
            table.add(txtNames.get(i)).left().expandX();
            table.add(txtScores.get(i)).right();
            table.row();
        }
        table.pack();
        table.setWidth(tblWidth);
        game.error(table.getHeight()+ "");
        table.setPosition(stage.getWidth() / 2 - table.getWidth() / 2, stage.getHeight() - table.getHeight() - tblMarginTop);
        stage.addActor(table);

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
