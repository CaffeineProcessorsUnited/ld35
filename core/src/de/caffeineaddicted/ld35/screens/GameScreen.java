package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.input.GameInputProcessor;
import de.caffeineaddicted.ld35.logic.ShapeRef;

import java.util.Random;

/**
 * Created by malte on 4/16/16.
 */
public class GameScreen implements Screen {
    
    private Model player_model, incoming_model;
    private ModelInstance player_instance, incoming_instance;
    private ModelBatch modelBatch;
    private  float dist = 80;
    private CoffeeGame g;
    private PerspectiveCamera camera;
    private ShapeRef playerShape;
    private ShapeRef incomingShape;

    public GameScreen(CoffeeGame g) {
        this.g = g;
        create();
    }

    private boolean matchShapes(){
        return playerShape.equals(incomingShape);
    }

    private void generateNewIncomimgShape(){
        int i = new Random().nextInt((int)Math.pow(ShapeRef.numShapes,ShapeRef.numSlots));
        incomingShape.SetShape(i);
    }

    public void create(){
        g.debug("Creating GameScreen");
        // give it to the multiplexer
        //Gdx.input.setInputProcessor();

        playerShape = new ShapeRef();
        incomingShape = new ShapeRef();
        generateNewIncomimgShape();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GameInputProcessor(playerShape));
        Gdx.input.setInputProcessor(multiplexer);
        modelBatch = new ModelBatch();

        ModelBuilder builder = new ModelBuilder();
        player_model = builder.createBox(1.0f,0.3f,0.01f,new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        player_instance = new ModelInstance(player_model);
        player_instance.transform = new Matrix4(new Vector3(0f,0f,1.2f),new Quaternion(),new Vector3(1,1,1));

        incoming_model = builder.createBox(20f,20f,0.01f, new Material(ColorAttribute.createDiffuse(Color.BLUE)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        incoming_instance = new ModelInstance(incoming_model);
        incoming_instance.transform = new Matrix4(new Vector3(0f,0f,dist),new Quaternion(),new Vector3(1,1,1));
    }

    public void render (float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        //camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        g.getBatch().setProjectionMatrix(camera.combined);
        modelBatch.begin(camera);
        modelBatch.render(player_instance);
        modelBatch.render(incoming_instance);
        modelBatch.end();

        if (dist > 2) {
            dist -= 0.1;
            incoming_instance.transform = new Matrix4(new Vector3(0f, 0f, dist), new Quaternion(), new Vector3(1, 1, 1));
        }

        if(matchShapes()){
            generateNewIncomimgShape();
        }
    }

    @Override
    public void resize (int width, int height) {
        g.debug("Im resizing...");
        float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, 2f*aspectRatio , 2f);
        camera.position.set(0f, 0.5f, 0f);
        camera.lookAt(0,0.5f,1);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        player_model.dispose();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }
}
