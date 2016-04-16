package de.caffeineaddicted.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.caffeineaddicted.ld35.logic.ShapeRef;

public class CoffeeGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		ShapeRef s = new ShapeRef();
		s.PlaceShape(3,0);
		s.PlaceShape(3,1);
		s.PlaceShape(3,2);
		s.PlaceShape(3,3);

		int shapeID = s.GetShapeID();
        Gdx.app.log("",""+shapeID);

		ShapeRef ss = new ShapeRef();
		ss.SetShape(shapeID);
        Gdx.app.log("",ss.GetShape(0)+" "+ss.GetShape(1)+" "+ss.GetShape(2)+" "+ss.GetShape(3));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
