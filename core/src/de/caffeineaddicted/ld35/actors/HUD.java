package de.caffeineaddicted.ld35.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.screens.GameScreen;

/**
 * Created by maria on 16.04.16.
 */
public class HUD extends Group {

    private KeyDisplay keyDisplay;
    private Label labelSpeed;
    private Label labelPoints;

    private GameScreen gameScreen;
    private CoffeeGame g;

    public HUD(GameScreen g) {
        gameScreen = g;
        this.g = g.game;
        setBounds(0, 0, gameScreen.getStage().getWidth(), gameScreen.getStage().getHeight());
        create();
    }

    public void create () {
        keyDisplay = new KeyDisplay(g, this);
        keyDisplay.setScale(0.3f, 0.3f);
        keyDisplay.setPosition(10, 10);
        keyDisplay.setWidth(getWidth() - 20);
        labelSpeed = new Label("Speed: ",g.getAssets().get("uiskin.json", Skin.class));
        labelSpeed.setPosition(20, getHeight() - labelSpeed.getHeight() - 20);
        labelPoints = new Label("Points: ",g.getAssets().get("uiskin.json", Skin.class));
        labelPoints.setPosition(getWidth() - labelPoints.getWidth() - 20, getHeight() - labelSpeed.getHeight() - 20);

        addActor(keyDisplay);
        addActor(labelSpeed);
        addActor(labelPoints);
    }

    @Override
    public void draw (Batch batch, float parentAlpha ) {
        labelSpeed.setText("Speed: " + gameScreen.getSpeed());
        labelPoints.setText("Score: " + gameScreen.getScore());
        super.draw(batch, parentAlpha);
    }

}
