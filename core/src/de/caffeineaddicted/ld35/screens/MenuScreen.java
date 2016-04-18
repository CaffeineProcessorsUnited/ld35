package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.input.MenuInputProcessor;

import java.util.ArrayList;

/**
 * Created by malte on 4/16/16.
 */
public class MenuScreen extends CoffeeScreen {

    public enum NAVIGATION {
        Both, Horizontal, Vertical
    }

    protected Stage stage;

    private Label title;
    private Sprite background;
    private int tabindex = -1;

    private ArrayList<Button> buttons;

    private NAVIGATION navigation = NAVIGATION.Both;

    public MenuScreen(CoffeeGame g) {
        super(g);
    }

    public void create() {
        game.debug("Creating MenuScreen");
        stage = new Stage(game.createViewport());

        Texture texBackground = game.getAssets().get("menu_background.png", Texture.class);
        texBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background = new Sprite(texBackground);
        background.setSize(stage.getWidth(), stage.getHeight());

        title = new Label("", game.getAssets().get("uiskin.json", Skin.class), "title");
        title.setPosition(stage.getWidth() / 2 - title.getWidth() / 2, stage.getHeight() - title.getHeight() - 10);
        stage.addActor(title);

        buttons = new ArrayList<Button>();
    }

    public void setTitle(String titleStr) {
        for (Actor actor: stage.getActors()) {
            if (actor == title) {
                actor.remove();
            }
        }
        title = new Label(titleStr, game.getAssets().get("uiskin.json", Skin.class), "title");
        title.setPosition(stage.getWidth() / 2 - title.getWidth() / 2, stage.getHeight() - title.getHeight() - 10);
        stage.addActor(title);
    }

    public void addButton(Button button) {
        addButton(button, true);
    }

    public void addButton(Button button, boolean selectable) {
        addButton(button, null, selectable);
    }

    public void addButton(Button button, ChangeListener listener, boolean selectable) {
        addButton(button, button.getX(), button.getY(), listener, selectable);
    }

    public void addButton(Button button, float x, float y, ChangeListener listener, boolean selectable) {
        if (button == null) {
            return;
        }
        if (listener == null) {
            listener = new ChangeListener() {
                public void changed (ChangeEvent event, Actor actor) {
                    // Do nothing
                }
            };
        }
        button.setPosition(x, y);
        button.addListener(listener);
        stage.addActor(button);
        game.debug("Adding button at " + x + "," + y);
        if (selectable) {
            buttons.add(button);
        }
    }

    public void loseOver() {
        if (!(tabindex < 0)) {
            game.debug("Change back to default style on index " + tabindex);
            setStyle(buttons.get(tabindex), "default");
            tabindex = -1;
        }
    }

    public void select(int index) {
        if (buttons.size() == 0) {
            return;
        }
        loseOver();
        if (index < 0) {
            index = 0;
        }
        if (index >= buttons.size()) {
            index = buttons.size() - 1;
        }
        game.debug("We should try to highlight tabindex " + index);
        setStyle(buttons.get(index), "over");
        tabindex = index;
    }

    public void click() {
        if (!(tabindex < 0)) {
            buttons.get(tabindex).toggle();
        }
    }

    public void prev() {
        select((buttons.size() + tabindex - 1) % buttons.size());
    }

    public void next() {
        select((buttons.size() + tabindex + 1) % buttons.size());
    }

    public void setNavigation(NAVIGATION navigation) {
        this.navigation = navigation;
    }

    public NAVIGATION getNavigation() {
        return navigation;
    }

    public void setStyle(Button button, String stylename) {
        setStyle(button, game.getAssets().get("uiskin.json", Skin.class), stylename);
    }

    public void setStyle(Button button, Skin skin, String stylename) {
        if (button instanceof TextButton) {
            button.setStyle(skin.get(stylename, TextButton.TextButtonStyle.class));
        } else {
            button.setStyle(skin.get(stylename, Button.ButtonStyle.class));
        }

    }

    public void render (float delta) {
        //Gdx.gl.glEnable(GL20.GL_BLEND);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //Gdx.gl.glClearColor(0.8f, 0.2f, 0.2f, 0);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act(delta);
        stage.draw();
        //Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize (int width, int height) {
        game.debug("resizing");
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    @Override
    public void show() {
        super.show();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new MenuInputProcessor(this));
        game.getScreenInput().addProcessor(this, multiplexer);
    }
    
}
