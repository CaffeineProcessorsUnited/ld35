package de.caffeineaddicted.ld35.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance;
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

    private static float baseDist = 30.f;
    private static float baseSpeed = 4f;
    private static float speedMultiplier = 0.5f;
    private int iteration = 0;
    private static Color[] colors;

    private int numPoints;
    private static int numPointTrigger = 7;


    private Model player_model[], incoming_model[];
    private ModelInstance player_instance[], incoming_instance[];
    private ModelBatch modelBatch;
    private float dist;
    private float speed;
    private CoffeeGame g;
    private PerspectiveCamera camera;
    private ShapeRef playerShape;
    private ShapeRef incomingShape;

    public GameScreen(CoffeeGame g) {
        this.g = g;
        colors =new Color[5];
        colors[0]= Color.RED;
        colors[1]= Color.GREEN;
        colors[2]= Color.BLUE;
        colors[3]= Color.WHITE;
        colors[4]= Color.ORANGE;
        create();
    }

    private boolean matchShapes(){
        return playerShape.equals(incomingShape);
    }

    private void generateNewIncomimgShape(){
        int i = new Random().nextInt((int)Math.pow(ShapeRef.numShapes,ShapeRef.numSlots));
        incomingShape.SetShape(i);
        dist = baseDist;
        numPoints++;
        if(numPoints % numPointTrigger == 0) {
            iteration++;
        }
        speed = Math.min(baseSpeed + speedMultiplier * iteration,20.f);
        Gdx.app.log("GenerateNewIncomingShape",numPoints+" "+iteration+" "+speed);
        for(int j=0;j<4;j++){
            incoming_instance[j].materials.first().set(ColorAttribute.createDiffuse(colors[incomingShape.GetShape(j)]));
        }
    }

    private void setModelTransform(int model){
        float x = 0;
        float y = 0;
        float z = dist;
        if(model == 0){
            x = -0.3f;
        } else if(model == 1){

        } else if(model == 2){
            x = 0.3f;
        } else if(model == 3){
            y = 0.3f;
        } else {
            z += 0.05;
        }

        incoming_instance[model].transform = new Matrix4(new Vector3(x,y,z),new Quaternion(),new Vector3(1,1,1));
    }

    public void create(){
        g.debug("Creating GameScreen");
        // give it to the multiplexer
        //Gdx.input.setInputProcessor();

        playerShape = new ShapeRef();
        incomingShape = new ShapeRef();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GameInputProcessor(playerShape));
        Gdx.input.setInputProcessor(multiplexer);
        modelBatch = new ModelBatch();

        dist = baseDist;
        speed = baseSpeed;

        ModelBuilder builder = new ModelBuilder();
        player_model = new Model[5];
        player_model[0] = builder.createBox(0.3f,0.3f,0.01f,new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.0f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        player_model[1] = builder.createBox(0.3f,0.3f,0.01f,new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.2f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        player_model[2] = builder.createBox(0.3f,0.3f,0.01f,new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.4f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        player_model[3] = builder.createBox(0.3f,0.3f,0.01f,new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.6f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        player_model[4] = builder.createBox(1.0f,1f,0.01f,new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.8f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        player_instance = new ModelInstance[5];//new ModelInstance(player_model);
        for(int i = 0; i <5 ; ++i)
            player_instance[i] = new ModelInstance(player_model[i]);
        player_instance[0].transform = new Matrix4(new Vector3(-0.3f,0f,1.2f),new Quaternion(),new Vector3(1,1,1));
        player_instance[1].transform = new Matrix4(new Vector3(0f,0f,1.2f),new Quaternion(),new Vector3(1,1,1));
        player_instance[2].transform = new Matrix4(new Vector3(0.3f,0f,1.2f),new Quaternion(),new Vector3(1,1,1));
        player_instance[3].transform = new Matrix4(new Vector3(0f,0.3f,1.2f),new Quaternion(),new Vector3(1,1,1));
        player_instance[4].transform = new Matrix4(new Vector3(0f,0f,1.25f),new Quaternion(),new Vector3(1,1,1));

        incoming_model = new Model[6];
        incoming_model[0] = builder.createBox(0.3f,0.3f,0.01f, new Material(ColorAttribute.createDiffuse(0.0f, 0.0f, 1.0f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        incoming_model[1] = builder.createBox(0.3f,0.3f,0.01f, new Material(ColorAttribute.createDiffuse(0.2f, 0.0f, 1.0f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        incoming_model[2] = builder.createBox(0.3f,0.3f,0.01f, new Material(ColorAttribute.createDiffuse(0.4f, 0.0f, 1.0f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        incoming_model[3] = builder.createBox(0.3f,0.3f,0.01f, new Material(ColorAttribute.createDiffuse(0.6f, 0.0f, 1.0f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        incoming_model[4] = builder.createBox(5f,5f,0.01f, new Material(ColorAttribute.createDiffuse(0.8f, 0.0f, 1.0f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        incoming_model[5] = builder.createBox(5f,5f,0.01f, new Material(ColorAttribute.createDiffuse(1.0f, 0.0f, 1.0f, 1.0f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        incoming_instance = new ModelInstance[6];
        for(int i = 0; i< 6; ++i) {
            incoming_instance[i] = new ModelInstance(incoming_model[i]);
            setModelTransform(i);
        }
        generateNewIncomimgShape();
    }

    public void render (float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        //camera.update();

        if(playerShape.isDirty()){
            for (int i=0;i<4;i++){
                player_instance[i].materials.first().set(ColorAttribute.createDiffuse(colors[playerShape.GetShape(i)]));
            }
            playerShape.setDirty(false);
        }

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        g.getBatch().setProjectionMatrix(camera.combined);
        modelBatch.begin(camera);
        for(int i = 0; i < 5; ++i) {
            modelBatch.render(player_instance[i]);
        }
        for(int i = 0; i < 6; ++i) {
            modelBatch.render(incoming_instance[i]);
        }
        modelBatch.end();

        if (dist > 0) {
            dist -= (speed*delta);
            for(int i = 0; i <6; ++i)
                setModelTransform(i);
        }

        if(dist < 1) {
            //if (matchShapes()) {
                generateNewIncomimgShape();
            //}
        }
    }

    @Override
    public void resize (int width, int height) {
        g.debug("Im resizing...");
        float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, 2f*aspectRatio , 2f);
        camera.position.set(0f, 1.f, 0f);
        camera.lookAt(0,0.8f,1);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        for(int i = 0; i <5; ++i)
            player_model[i].dispose();
        for(int i = 0; i <6; ++i)
            incoming_model[i].dispose();
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
