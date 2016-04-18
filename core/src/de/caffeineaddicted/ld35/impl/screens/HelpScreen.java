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
    String help = "To put it simple (not that it's even getting complicated...)" +
            " You have to fit shapes in the front to the cut out shapes on the wall" +
            " to be able to pass through it. \n" +
            "The shapes are moving faster and faster, so be quick! To select which " +
            "shape to change press the accordingly positioned arrow key then select" +
            " the desired shape by pressing the according WASD key or the space bar." +
            " For ease of learning these keys are shown on screen. \n" +
            "In case you already got the shapes right and no time to chase for the wall" +
            " use your magical teleportation powers by pressing the n key. \n" +
            "Do you want a challenge? Quick observation and reactions? Then hardcore mode" +
            " is just right for you. You start with increased speed and disabled shape hints.\n";

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
