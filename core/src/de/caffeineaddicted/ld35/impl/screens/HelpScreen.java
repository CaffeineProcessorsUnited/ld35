package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.messages.ShowMainMenuMessage;
import de.caffeineaddicted.ld35.screens.MenuScreen;

/**
 * Created by malte on 4/16/16.
 */
public class HelpScreen extends MenuScreen {

    float txtMarginTop = 100;
    float txtWidth = 600;
    TextButton btnBack;
    String help = "Learn\n" +
            "to\n" +
            "play\n" +
            "scrub\n" +
            "!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "";

    public HelpScreen(CoffeeGame g) {
        super(g);
    }

    public void create() {
        super.create();
        game.debug("Creating HelpScreen");

        setTitle("LD 35: Help");

        Label txtHelp = new Label(help, game.getDefaultSkin(), "default");
        txtHelp.setWrap(true);

        txtHelp.pack();
        txtHelp.setWidth(txtWidth);

        txtHelp.pack();
        txtHelp.setWidth(txtWidth);

        txtHelp.setPosition(stage.getWidth() / 2 - txtHelp.getWidth() / 2, stage.getHeight() - txtMarginTop - txtHelp.getHeight());
        stage.addActor(txtHelp);

        // Back button
        btnBack = new TextButton("Back", game.getDefaultSkin());
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
