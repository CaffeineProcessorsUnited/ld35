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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.input.GameInputProcessor;
import de.caffeineaddicted.ld35.logic.ShapeRef;

import java.util.Random;

/**
 * Created by malte on 4/16/16.
 */
public class GameScreen implements Screen {
    static private class INDICES {
        public static int MIN_PLAYER_INDEX = 0;
        public static int MAX_PLAYER_INDEX = 4;
        public static int MIN_INCOMING_INDEX = 5;
        public static int MAX_INCOMING_INDEX = 11;
        public static int MIN_TUNNEL_INDEX = 12;
        public static int MAX_TUNNEL_INDEX = 18;
        public static int MAX_INDEX = 19;

    }

    private static float baseDist = 30.f;
    private static float baseSpeed = 2f;
    private static float speedMultiplier = 0.5f;
    private int iteration = 0;
    private static Color[] colors;

    private int numPoints;
    private static int numPointTrigger = 7;

    private boolean doDraw;

    private Model models[];
    private ModelInstance instances[];
    private ModelBuilder builder;
    static private long va = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
    private int leadingIncoming = 0;

/*
    private Model player_model[], incoming_model[], carpent_model;
    private Model tunnel_model[];
    private ModelInstance player_instance[], incoming_instance[], carpet_instance;
    private ModelInstance tunnel_instance[];*/
    private ModelBatch modelBatch;
    private float dist;
    private float speed;
    public CoffeeGame game;
    private PerspectiveCamera camera;
    public ShapeRef playerShape;
    private ShapeRef incomingShape;

    private Stage stage;

    public GameScreen(CoffeeGame game) {
        this.game = game;
        colors = new Color[5];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.WHITE;
        colors[4] = Color.FIREBRICK;
        create();
    }

    private boolean matchShapes() {
        return playerShape.equals(incomingShape);
    }

    private void generateNewIncomimgShape() {
        int i = new Random().nextInt((int) Math.pow(ShapeRef.numShapes, ShapeRef.numSlots));
        incomingShape.SetShape(i);
        dist = baseDist;
        numPoints++;
        if (numPoints % numPointTrigger == 0) {
            iteration++;
        }
        speed = Math.min(baseSpeed + speedMultiplier * iteration, 20.f);
        Gdx.app.log("GenerateNewIncomingShape", numPoints + " " + iteration + " " + speed);
        for (int j = 0; j < 4; j++) {
            instances[INDICES.MIN_INCOMING_INDEX+j].materials.first().set(ColorAttribute.createDiffuse(colors[incomingShape.GetShape(j)]));
        }
    }

    private void setModelTransform(int model) {
        float x = 0;
        float y = 0;
        float z = dist;
        if (model == 0) {
            x = -0.3f;
        } else if (model == 1) {

        } else if (model == 2) {
            x = 0.3f;
        } else if (model == 3) {
            y = 0.3f;
        } else if (model == 4) {
            z += 0.02;
        } else if (model == 5) {
            z += 0.04;
            y += 2.35;
        }

        instances[INDICES.MIN_INCOMING_INDEX+model].transform = new Matrix4(new Vector3(x, y, z), new Quaternion(), new Vector3(1, 1, 1));
    }

    private void makeModel(int idx, Vector3 size, Vector3 offset, Material material){
        makeModel(idx, size, offset, material, null);
    }

    private void makeModel(int idx, Vector3 size, Vector3 offset, Material material, Quaternion rotation){
        if(builder == null)
            return;
        if(rotation == null)
            rotation = new Quaternion();
        models[idx] = builder.createBox(size.x, size.y, size.z,material,va);
        instances[idx] = new ModelInstance(models[idx]);
        instances[idx].transform = new Matrix4(offset, rotation, new Vector3(1,1,1));
    }

    public void create() {
        doDraw = true;
        game.debug("Creating GameScreen");

        playerShape = new ShapeRef();
        incomingShape = new ShapeRef();

        modelBatch = new ModelBatch();
        builder = new ModelBuilder();

        dist = baseDist;
        speed = baseSpeed;

        models = new Model[INDICES.MAX_INDEX];
        instances = new ModelInstance[INDICES.MAX_INDEX];

        CreatePlayerModels();
        CreateIncomingModels();
            generateNewIncomimgShape();
        CreateTunnelModels();
    }

    private void CreatePlayerModels() {
        makeModel(INDICES.MIN_PLAYER_INDEX+0,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(-0.3f, 0f, 1.2f),
                new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.0f, 1.0f)));
        makeModel(INDICES.MIN_PLAYER_INDEX+1,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(0f, 0f, 1.2f),
                new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.2f, 1.0f)));
        makeModel(INDICES.MIN_PLAYER_INDEX+2,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(0.3f, 0f, 1.2f),
                new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.4f, 1.0f)));
        makeModel(INDICES.MIN_PLAYER_INDEX+3,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(0f, 0.3f, 1.2f),
                new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.6f, 1.0f)));
        makeModel(INDICES.MIN_PLAYER_INDEX+4,
                new Vector3(1f, 1f, 0.01f),
                new Vector3(0f, 0f, 1.22f),
                new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.8f, 1.0f)));
    }

    private void CreateIncomingModels() {
        makeModel(INDICES.MIN_INCOMING_INDEX + 0,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(0.0f, 0.0f, 1.0f, 1.0f)));
        makeModel(INDICES.MIN_INCOMING_INDEX + 1,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(0.2f, 0.0f, 1.0f, 1.0f)));
        makeModel(INDICES.MIN_INCOMING_INDEX + 2,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(0.4f, 0.0f, 1.0f, 1.0f)));
        makeModel(INDICES.MIN_INCOMING_INDEX + 3,
                new Vector3(0.3f, 0.3f, 0.01f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(0.6f, 0.0f, 1.0f, 1.0f)));
        makeModel(INDICES.MIN_INCOMING_INDEX + 4,
                new Vector3(1.0f, 1.0f, 0.01f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(Color.PURPLE)));
        makeModel(INDICES.MIN_INCOMING_INDEX + 5,
                new Vector3(20f, 5f, 0.01f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(new Color(0x8b522aff))));
        makeModel(INDICES.MIN_INCOMING_INDEX + 6,
                new Vector3(20f, 5f, 0.01f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(new Color(0x8b522aff))));
        for (int i = 0; i <= INDICES.MAX_INCOMING_INDEX - INDICES.MIN_INCOMING_INDEX; ++i)
            setModelTransform(i);

        stage = new Stage();
        stage.addActor(new Label("Marias HUD", game.getAssets().get("uiskin.json", Skin.class)));
    }

    private void CreateTunnelModels() {
        makeModel(INDICES.MIN_TUNNEL_INDEX+0,
                new Vector3(1.2f, 0.01f, baseDist * 2),
                new Vector3(0f, -0.16f, 0f), // Carpet
                new Material(ColorAttribute.createDiffuse(Color.PURPLE)));
        makeModel(INDICES.MIN_TUNNEL_INDEX+1,
                new Vector3(0.01f, 4f, baseDist * 5),
                new Vector3(-2f, -0f, 0f), // Tunnel walls
                new Material(ColorAttribute.createDiffuse(Color.BROWN)));
        makeModel(INDICES.MIN_TUNNEL_INDEX+2,
                new Vector3(0.01f, 4f, baseDist * 5),
                new Vector3(2f, -0f, 0f), // Tunnel walls
                new Material(ColorAttribute.createDiffuse(Color.BROWN)));
        makeModel(INDICES.MIN_TUNNEL_INDEX+3,
                new Vector3(4f, 0.01f, baseDist * 5),
                new Vector3(0f, -0.18f, 0f), // Tunnel walls
                new Material(ColorAttribute.createDiffuse(Color.GRAY)));
        makeModel(INDICES.MIN_TUNNEL_INDEX+4,
                new Vector3(4f, 0.01f, baseDist * 5),
                new Vector3(0f, 2f, 0f), // Tunnel walls
                new Material(ColorAttribute.createDiffuse(Color.BROWN)));
        makeModel(INDICES.MIN_TUNNEL_INDEX+5,
                new Vector3(0.2f, 0.05f, baseDist * 5),
                new Vector3(-1.98f, 1.98f, 0f), // Tunnel walls
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                new Quaternion().setFromAxis(0,0,1,-45));
        makeModel(INDICES.MIN_TUNNEL_INDEX+6,
                new Vector3(0.2f, 0.05f, baseDist * 5),
                new Vector3(1.98f, 1.98f, 0f), // Tunnel corners
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                new Quaternion().setFromAxis(0,0,1,-45));
    }

    public void render(float delta) {
        if(!doDraw)
            return;
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

        if (playerShape.isDirty()) {
            for (int i = 0; i < 4; i++) {
                instances[INDICES.MIN_PLAYER_INDEX+i].materials.first().set(ColorAttribute.createDiffuse(colors[playerShape.GetShape(i)]));
            }
            playerShape.setDirty(false);
        }

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.getBatch().setProjectionMatrix(camera.combined);
        modelBatch.begin(camera);
        for(int i = 0; i < instances.length; ++i)
            modelBatch.render(instances[i]);
        modelBatch.end();

        if (dist >= 1.2) {
            dist -= (speed * delta);
            for (int i = 0; i < 6; ++i)
                setModelTransform(i);
        }

        if (dist < 1.2) {
            if (matchShapes()) {
                generateNewIncomimgShape();
            }
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.debug("Im resizing...");
        float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
        camera.position.set(0f, 1.f, 0f);
        camera.lookAt(0, 0.8f, 1);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        for(int i = 0; i < instances.length; ++i)
            models[i].dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GameInputProcessor(this));
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        doDraw = false;
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        doDraw = true;
    }
}
