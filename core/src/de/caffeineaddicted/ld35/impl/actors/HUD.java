package de.caffeineaddicted.ld35.impl.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.screens.GameScreen;

/**
 * Created by maria on 16.04.16.
 */
public class HUD extends Group {

    private KeyDisplay keyDisplay;
    private ObstacleDisplay obstacleDisplay;
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

    public void create() {
        keyDisplay = new KeyDisplay(g, this);
        keyDisplay.setScale(0.3f, 0.3f);
        keyDisplay.setPosition(10, 10);
        keyDisplay.setWidth(getWidth() - 20);

        obstacleDisplay = new ObstacleDisplay(g, this);
        obstacleDisplay.setScale(0.05f, 0.05f);
        obstacleDisplay.setPosition(getWidth() / 2 - obstacleDisplay.getWidth() / 2, getHeight() - obstacleDisplay.getHeight() - 10);

        labelSpeed = new Label("Speed: " + gameScreen.getSpeed(), g.getAssets().get("uiskin.json", Skin.class));
        labelSpeed.setPosition(20, getHeight() - labelSpeed.getHeight() - 20);

        labelPoints = new Label("Points: " + gameScreen.getScore(), g.getAssets().get("uiskin.json", Skin.class));
        labelPoints.setPosition(getWidth() - labelPoints.getWidth() - 20, getHeight() - labelSpeed.getHeight() - 20);

        addActor(keyDisplay);
        addActor(labelSpeed);
        addActor(labelPoints);
        addActor(obstacleDisplay);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = getChildren().size - 1; i >= 0; i--) {
            if (getChildren().get(i) == labelPoints) {
                getChildren().get(i).remove();
            } else if (getChildren().get(i) == labelSpeed) {
                getChildren().get(i).remove();
            }
        }
        obstacleDisplay.setVisible(!gameScreen.isHardcore());
        labelSpeed = new Label("Speed: " + gameScreen.getSpeed(), g.getAssets().get("uiskin.json", Skin.class));
        labelSpeed.setPosition(20, getHeight() - labelSpeed.getHeight() - 20);
        addActor(labelSpeed);
        labelPoints = new Label("Points: " + gameScreen.getScore(), g.getAssets().get("uiskin.json", Skin.class));
        labelPoints.setPosition(getWidth() - labelPoints.getWidth() - 20, getHeight() - labelPoints.getHeight() - 20);
        addActor(labelPoints);
        super.draw(batch, parentAlpha);
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

}
