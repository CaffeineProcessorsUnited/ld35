package de.caffeineaddicted.ld35.impl.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import de.caffeineaddicted.ld35.logic.ShapeRef;
import de.caffeineaddicted.ld35.impl.messages.PauseGameMessage;
import de.caffeineaddicted.ld35.impl.screens.GameScreen;

public class GameInputProcessor implements InputProcessor {

    private ShapeRef myshape;
    private GameScreen screen;

    public GameInputProcessor(GameScreen screen){
        this.screen = screen;
        myshape = screen.playerShape;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode)
        {
            case Input.Keys.A:
                PlaceShape(1);
                break;
            case Input.Keys.W:
                PlaceShape(2);
                break;
            case Input.Keys.D:
                PlaceShape(3);
                break;
            case Input.Keys.S:
                PlaceShape(4);
                break;
            case Input.Keys.SPACE:
                PlaceShape(0);
                break;
            case Input.Keys.ESCAPE:
            case Input.Keys.P:
                screen.game.message(new PauseGameMessage());
                break;
        }
        //System.out.println(myshape.GetShapeID());
        return true;
    }

    private void PlaceShape(int shape){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            myshape.PlaceShape(shape,2);
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            myshape.PlaceShape(shape,3);
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            myshape.PlaceShape(shape,0);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            myshape.PlaceShape(shape,1);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
