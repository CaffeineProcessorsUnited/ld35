package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    protected Stage stage;
    private Label title;
    private int tabindex = -1;
    private ArrayList<Button> buttons;
    private NAVIGATION navigation = NAVIGATION.Both;

    private boolean dimmBackground;

    public MenuScreen(CoffeeGame g) {
        super(g);
    }

    public void create() {
        game.debug("Creating MenuScreen");
        stage = new Stage(game.createViewport());
        dimmBackground = true;

        title = new Label("", game.getDefaultSkin(), "title");
        title.setPosition(stage.getWidth() / 2 - title.getWidth() / 2, stage.getHeight() - title.getHeight() - 10);
        stage.addActor(title);

        buttons = new ArrayList<Button>();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new MenuInputProcessor(this));
        game.getScreenInput().addProcessor(this, multiplexer);
    }

    public void setTitle(String titleStr) {
        for (Actor actor : stage.getActors()) {
            if (actor == title) {
                actor.remove();
            }
        }
        title = new Label(titleStr, game.getDefaultSkin(), "title");
        title.setPosition(stage.getWidth() / 2 - title.getWidth() / 2, stage.getHeight() - title.getHeight() - 10);
        stage.addActor(title);
    }

    public void setDimmBackground(boolean dimmBackground) {
        this.dimmBackground = dimmBackground;
    }

    public boolean getDimmBackground() {
        return dimmBackground;
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
                public void changed(ChangeEvent event, Actor actor) {
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

    public NAVIGATION getNavigation() {
        return navigation;
    }

    public void setNavigation(NAVIGATION navigation) {
        this.navigation = navigation;
    }

    public void setStyle(Button button, String stylename) {
        setStyle(button, game.getDefaultSkin(), stylename);
    }

    public void setStyle(Button button, Skin skin, String stylename) {
        if (button instanceof TextButton) {
            button.setStyle(skin.get(stylename, TextButton.TextButtonStyle.class));
        } else {
            button.setStyle(skin.get(stylename, Button.ButtonStyle.class));
        }

    }

    public void render(float delta) {
        if (dimmBackground) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            game.getShape().begin(ShapeRenderer.ShapeType.Filled);
            game.getShape().setColor(0f, 0f, 0f, 0.6f);
            game.getShape().rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.getShape().end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.debug("resizing");
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        super.hide();
        tabindex = -1;
        create();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public enum NAVIGATION {
        Both, Horizontal, Vertical
    }

}
