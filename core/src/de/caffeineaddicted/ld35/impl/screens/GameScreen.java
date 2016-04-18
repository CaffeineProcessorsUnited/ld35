package de.caffeineaddicted.ld35.impl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.impl.messages.PauseGameMessage;
import de.caffeineaddicted.ld35.impl.messages.ResumeGameMessage;
import de.caffeineaddicted.ld35.impl.messages.ShowPauseScreenMessage;
import de.caffeineaddicted.ld35.screens.CoffeeScreen;
import de.caffeineaddicted.ld35.impl.actors.HUD;
import de.caffeineaddicted.ld35.impl.input.GameInputProcessor;
import de.caffeineaddicted.ld35.logic.ShapeRef;
import de.caffeineaddicted.ld35.impl.messages.GameOverMessage;

import java.util.Random;

import static com.badlogic.gdx.graphics.GL20.GL_NONE;

/**
 * Created by malte on 4/16/16.
 */
public class GameScreen extends CoffeeScreen {

    static private class INDICES {
        public static int MIN_PLAYER_INDEX = 0;
        public static int MAX_PLAYER_INDEX = 4;
        public static int MIN_INCOMING_INDEX = 5;
        public static int MAX_INCOMING_INDEX = 14;
        public static int MIN_TUNNEL_INDEX = 15;
        public static int MAX_TUNNEL_INDEX = 21;
        public static int MAX_INDEX = 22;

        public static int BLUE_TILES_SIDE = 0;
        public static int GREY_TILES = 1;
        public static int BRICK_TILES = 2;
        public static int SPRITE_UNICORN = 3;
        public static int SPRITE_UNICORN_INV = 4;
        public static int SPRITE_SQUARE = 5;
        public static int SPRITE_SQUARE_INV = 6;
        public static int SPRITE_CIRCLE = 7;
        public static int SPRITE_CIRCLE_INV = 8;
        public static int SPRITE_STAR = 9;
        public static int SPRITE_STAR_INV = 10;
        public static int SPRITE_TRIANGLE = 11;
        public static int SPRITE_TRIANGLE_INV = 12;
        public static int SPRITE_RAINBOW = 13;

        public static int NUM_TEXTURES = 14;

        public static int SLOT_LEFT = 0;
        public static int SLOT_UP = 1;
        public static int SLOT_RIGHT = 2;
        public static int SLOT_DOWN = 3;
    }

    private boolean cheatMode;
    private boolean gameOver;
    private boolean hardcore;

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
    static private long va = VertexAttributes.Usage.Position |
                             VertexAttributes.Usage.Normal |
                             VertexAttributes.Usage.TextureCoordinates;
    private int leadingIncoming = 0;

/*
    private Model player_model[], incoming_model[], carpent_model;
    private Model tunnel_model[];
    private ModelInstance player_instance[], incoming_instance[], carpet_instance;
    private ModelInstance tunnel_instance[];*/
    private ModelBatch modelBatch;
    private float dist;
    private float speed;
    
    private PerspectiveCamera camera;
    public ShapeRef playerShape;
    private ShapeRef incomingShape;

    private Texture textures[];
    public Texture GetTextureByIncomingShape(int slot, boolean inv){
        if(slot < 0 || slot >= 4)
            return null;
        int shapeid = incomingShape.GetShape(slot);
        return textures[INDICES.SPRITE_UNICORN + 2 * shapeid + (inv ? 1 : 0)];
    }
    public Texture GetTextureByShape(int shapeid, boolean inv){
        return textures[INDICES.SPRITE_UNICORN + 2 * shapeid + (inv ? 1 : 0)];
    }

    private Stage stage;

    private void loadTexture(int idx, final String name, boolean mipmap){
        textures[idx] = game.getAssets().get(name+".png", Texture.class);
        if(mipmap)
            textures[idx].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        else
            textures[idx].setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

    }

    private void loadTexture(int idx, final String name){
        loadTexture(idx, name, true);
    }

    public GameScreen(CoffeeGame game) {
        super(game);
        colors = new Color[5];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.WHITE;
        colors[4] = Color.FIREBRICK;

        textures = new Texture[INDICES.NUM_TEXTURES];

    }

    private boolean matchShapes() {
        return playerShape.equals(incomingShape) || cheatMode;
    }

    private void generateNewIncomimgShape() {
        int i = new Random().nextInt((int) Math.pow(ShapeRef.numShapes, ShapeRef.numSlots));
        incomingShape.SetShape(i);
        dist = baseDist;
        if(doDraw)
            numPoints++;
        if (numPoints % numPointTrigger == 0) {
            iteration++;
        }
        float s = Math.min(baseSpeed + speedMultiplier * iteration, 20.f);
        if(!cheatMode)
            speed = s;
        else {
            speed = Math.max(speed,s);
        }
        for (int j = 0; j < 4; j++) {
            instances[INDICES.MIN_INCOMING_INDEX+j].materials.first().set(makeAttributeFromShape(incomingShape.GetShape(j),true));
        }
        setTunnelTransform();
    }

    private void setIncomingTransform(int model) {
        float x = 0;
        float y = 0;
        float z = dist;
        Vector3 scaling = new Vector3(1,1,1);
        if (model == 0) {
            x = -0.3f;
            scaling.x = -1;
        } else if (model == 1) {
            scaling.x = -1;
        } else if (model == 2) {
            x = 0.3f;
            scaling.x = -1;
        } else if (model == 3) {
            y = 0.3f;
            scaling.x = -1;
        } else if (model == 4) {
            z += 0.02;
        } else if (model == 5) {
            x += 1.225;
            y += 0.9;
        } else if (model == 6) {
            y += 1.45;
        } else if (model == 7) {
            x += -1.225;
            y += 0.9;
        } else if (model == 8) {
            x += -0.3;
            y += 0.3;
        } else if (model == 9) {
            x += 0.3;
            y += 0.3;
        }

        if(instances[INDICES.MIN_INCOMING_INDEX+model] != null)
            instances[INDICES.MIN_INCOMING_INDEX+model].transform = new Matrix4(new Vector3(x, y, z), new Quaternion(), scaling);
    }

    private void setTunnelTransform(){
        for (int i = INDICES.MIN_TUNNEL_INDEX;i<=INDICES.MAX_TUNNEL_INDEX;i++){
            Vector3 positon = instances[i].transform.getTranslation(new Vector3());
            positon.z = dist;
            instances[i].transform.setTranslation(positon);
        }
    }

    private void calcPlane(final Vector3 pos, final Vector3 size, Vector3 aa, Vector3 bb, Vector3 cc, Vector3 dd, Vector3 nn){
        Vector3 a,b,c,d,n;
        a = new Vector3(0,0,0);
        if(size.x < 0.001){
            b = new Vector3
                    (0f,size.y,0f);
            c = new Vector3(0f,size.y, size.z);
            d = new Vector3(0f,0f,size.z);
        } else if(size.y < 0.001){
            b = new Vector3(size.x,0f,0f);
            c = new Vector3(size.x,0 , size.z);
            d = new Vector3(0f,0f,size.z);
        } else if(size.z < 0.001){
            b = new Vector3(size.x,0f,0f);
            c = new Vector3(size.x,size.y, 0);
            d = new Vector3(0f,size.y,0f);
        } else {
            b = new Vector3(0,1,0);
            d = new Vector3(0,1,1);
            c = new Vector3(0,0,1);
        }

        Vector3 s = size.scl(0.5f);
        a.sub(s);
        b.sub(s);
        c.sub(s);
        d.sub(s);

        Vector3 ab = b.cpy().sub(a);
        Vector3 ad = d.cpy().sub(a);
        n = ab.crs(ad);
        n = n.nor();
        aa.set(a);
        bb.set(b);
        cc.set(c);
        dd.set(d);
        nn.set(n);
    }

    private void makeModel(int idx, Vector3 size, Vector3 offset, Material material){
        makeModel(idx, size, offset, material, null);
    }
    private void makeModel(int idx, Vector3 size, Vector3 offset, Material material, Quaternion rotation){
        makeModel(idx, size, offset, material, rotation,new Vector3(1,1,1));
    }
    private void makeModel(int idx, Vector3 size, Vector3 offset, Material material, Quaternion rotation, Vector3 scaling){
        if(builder == null)
            return;
        if(rotation == null)
            rotation = new Quaternion();
        Vector3 a = new Vector3();
        Vector3 b = new Vector3();
        Vector3 c = new Vector3();
        Vector3 d = new Vector3();
        Vector3 n = new Vector3();
        calcPlane(offset,size,a,b,c,d,n);

        material.set(IntAttribute.createCullFace(GL_NONE));
        material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

        models[idx] = builder.createRect(a.x,a.y,a.z,
                                         b.x,b.y,b.z,
                                         c.x,c.y,c.z,
                                         d.x,d.y,d.z,
                                         n.x,n.y,n.z,
                                         material, va);
        instances[idx] = new ModelInstance(models[idx]);
        instances[idx].transform = new Matrix4(offset, rotation, scaling);
    }

    private TextureAttribute makeAttributeFromShape(int idx, boolean inv){
        return TextureAttribute.createDiffuse(GetTextureByShape(idx,inv));
    }

    private Material makeMaterialFromTexture(int idx){
        return new Material(TextureAttribute.createDiffuse(textures[idx]));
    }

    public void create() {
        doDraw = false;
        game.debug("Creating GameScreen");

        loadTexture(INDICES.BLUE_TILES_SIDE,"BluetilesTexture");
        loadTexture(INDICES.GREY_TILES,"GreyTriagTexture");
        loadTexture(INDICES.BRICK_TILES,"BrickTexture");
        loadTexture(INDICES.SPRITE_UNICORN,"unicorn");
        loadTexture(INDICES.SPRITE_UNICORN_INV,"unicorninverted");
        loadTexture(INDICES.SPRITE_SQUARE,"square");
        loadTexture(INDICES.SPRITE_SQUARE_INV,"squareinverted");
        loadTexture(INDICES.SPRITE_CIRCLE,"circle");
        loadTexture(INDICES.SPRITE_CIRCLE_INV,"circleinverted");
        loadTexture(INDICES.SPRITE_STAR,"star");
        loadTexture(INDICES.SPRITE_STAR_INV,"starinverted");
        loadTexture(INDICES.SPRITE_TRIANGLE,"triangle");
        loadTexture(INDICES.SPRITE_TRIANGLE_INV,"triangleinverted");
        loadTexture(INDICES.SPRITE_RAINBOW,"rainbow",false);

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
        CreateTunnelModels();
        generateNewIncomimgShape();
        doDraw = true;

        game.getScreenInput().addProcessor(this, new GameInputProcessor(this));

        reset();

        cheatMode = false;
    }

    private void CreatePlayerModels() {
        makeModel(INDICES.MIN_PLAYER_INDEX+0,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(-0.3f, 0f, 1.2f),
                makeMaterialFromTexture(INDICES.SPRITE_STAR),
                new Quaternion(),
                new Vector3(-1,1,1));
        makeModel(INDICES.MIN_PLAYER_INDEX+1,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(0f, 0f, 1.2f),
                makeMaterialFromTexture(INDICES.SPRITE_STAR),
                new Quaternion(),
                new Vector3(-1,1,1));
        makeModel(INDICES.MIN_PLAYER_INDEX+2,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(0.3f, 0f, 1.2f),
                makeMaterialFromTexture(INDICES.SPRITE_STAR),
                new Quaternion(),
                new Vector3(-1,1,1));
        makeModel(INDICES.MIN_PLAYER_INDEX+3,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(0f, 0.3f, 1.2f),
                makeMaterialFromTexture(INDICES.SPRITE_STAR),
                new Quaternion(),
                new Vector3(-1,1,1));
        /*makeModel(INDICES.MIN_PLAYER_INDEX+4,
                new Vector3(1f, 1f, 0f),
                new Vector3(0f, 0f, 1.22f),
                new Material(ColorAttribute.createDiffuse(0.0f, 1.0f, 0.8f, 1.0f)));*/
    }

    private void CreateIncomingModels() {
        makeModel(INDICES.MIN_INCOMING_INDEX + 0,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                makeMaterialFromTexture(INDICES.SPRITE_STAR_INV));
        makeModel(INDICES.MIN_INCOMING_INDEX + 1,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                makeMaterialFromTexture(INDICES.SPRITE_STAR_INV));
        makeModel(INDICES.MIN_INCOMING_INDEX + 2,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                makeMaterialFromTexture(INDICES.SPRITE_STAR_INV));
        makeModel(INDICES.MIN_INCOMING_INDEX + 3,
                new Vector3(0.3f, 0.3f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                makeMaterialFromTexture(INDICES.SPRITE_STAR_INV));
        /*makeModel(INDICES.MIN_INCOMING_INDEX + 4,
                new Vector3(20f, 5f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(TextureAttribute.createDiffuse(textures[INDICES.BLUE_TILES_SIDE])));*/
        //Wall on Back
        makeModel(INDICES.MIN_INCOMING_INDEX + 5, //Left
                new Vector3(1.55f, 2.2f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(new Color(0x8b522aff))));
        makeModel(INDICES.MIN_INCOMING_INDEX + 6, //UpperMid
                new Vector3(0.9f, 2f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(new Color(0x8b522aff))));
        makeModel(INDICES.MIN_INCOMING_INDEX + 7, // Right
                new Vector3(1.55f, 2.2f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(new Color(0x8b522aff))));
        makeModel(INDICES.MIN_INCOMING_INDEX + 8, //Lower Left Mid
                new Vector3(0.31f, 0.3f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(new Color(0x8b522aff))));
        makeModel(INDICES.MIN_INCOMING_INDEX + 9, //Lower Right Mid
                new Vector3(0.31f, 0.3f, 0f),
                new Vector3(0f, 0f, 0f), // Will be overridden in setModelTransform
                new Material(ColorAttribute.createDiffuse(new Color(0x8b522aff))));
        for (int i = 0; i <= INDICES.MAX_INCOMING_INDEX - INDICES.MIN_INCOMING_INDEX; ++i)
            setIncomingTransform(i);

        stage = new Stage();
        stage.addActor(new HUD(this));
    }

    private void CreateTunnelModels() {
        makeModel(INDICES.MIN_TUNNEL_INDEX+0,
                new Vector3(1.2f, 0f, baseDist * 2),
                new Vector3(0f, -0.16f, 0f), // Carpet
                makeMaterialFromTexture(INDICES.SPRITE_RAINBOW));
        makeModel(INDICES.MIN_TUNNEL_INDEX+1,
                new Vector3(0f, 4f, baseDist * 5),
                new Vector3(-2f, -0f, 0f), // Tunnel walls
                makeMaterialFromTexture(INDICES.BLUE_TILES_SIDE));
        makeModel(INDICES.MIN_TUNNEL_INDEX+2,
                new Vector3(0f, 4f, baseDist * 5),
                new Vector3(2f, -0f, 0f), // Tunnel walls
                makeMaterialFromTexture(INDICES.BLUE_TILES_SIDE));
        makeModel(INDICES.MIN_TUNNEL_INDEX+3,
                new Vector3(4f, 0f, baseDist * 5),
                new Vector3(0f, -0.18f, 0f), // Tunnel walls
                makeMaterialFromTexture(INDICES.GREY_TILES));
        makeModel(INDICES.MIN_TUNNEL_INDEX+4,
                new Vector3(4f, 0f, baseDist * 5),
                new Vector3(0f, 2f, 0f), // Tunnel Top
                makeMaterialFromTexture(INDICES.BLUE_TILES_SIDE));
        makeModel(INDICES.MIN_TUNNEL_INDEX+5,
                new Vector3(0.2f, 0f, baseDist * 5),
                new Vector3(-1.98f, 1.98f, 0f), // Tunnel walls
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                new Quaternion().setFromAxis(0,0,1,45));
        makeModel(INDICES.MIN_TUNNEL_INDEX+6,
                new Vector3(0.2f, 0f, baseDist * 5),
                new Vector3(1.98f, 1.98f, 0f), // Tunnel corners
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                new Quaternion().setFromAxis(0,0,1,-45));
        setTunnelTransform();
    }

    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        updatePlayerModel();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        modelBatch.begin(camera);
        for(int i = 0; i < instances.length; ++i)
            if(instances[i] != null) {
                game.debug(""+i);
                modelBatch.render(instances[i]);
            }
        modelBatch.end();

        stage.draw();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        if(!doDraw)
            return;


        if (dist >= 1.2) {
            dist -= (speed * delta);
            for (int i = 0; i <= INDICES.MAX_INCOMING_INDEX - INDICES.MIN_INCOMING_INDEX; ++i)
                setIncomingTransform(i);
                setTunnelTransform();
        }

        if (dist < 1.2) {
            if (matchShapes()) {
                generateNewIncomimgShape();
            } else {
                gameOver = true;
                game.message(new GameOverMessage(numPoints));
            }
        }

        stage.act(delta);

        models = new Model[INDICES.MAX_INDEX];
    }

    private void updatePlayerModel() {
        if (playerShape.isDirty()) {
            for (int i = 0; i < 4; i++) {
                instances[INDICES.MIN_PLAYER_INDEX+i].materials.first().set(makeAttributeFromShape(playerShape.GetShape(i),false));
            }
            playerShape.setDirty(false);
        }
    }

    public void toggleCheats(){
        cheatMode = !cheatMode;
    }
    public void incSpeed(){
        if(cheatMode)
            speed += 1;
        else
            dist = 2;
    }

    private void reset(){
        gameOver = false;
        hardcore = false;

        numPoints = 0;
        dist = baseDist;
        speed = baseSpeed;
        iteration = 0;

        generateNewIncomimgShape();
        playerShape.Reset();
        updatePlayerModel();
        numPoints = 0;
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

    public Stage getStage() {
        return stage;
    }


    public int getScore() {
        return numPoints;
    }

    public int getSpeed() {
        return (int) (speed * 10);
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
        super.show();
        reset();
    }

    public void setDoDraw(boolean doDraw) {
        this.doDraw = doDraw;
    }

    public boolean isRunning() {
        return doDraw;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isHardcore(){ return hardcore; }

    public void setHardcore(boolean hardcore) {
        this.hardcore=hardcore;
        if(hardcore){
            iteration = Math.max(iteration,21);
        }
    }

    @Override
    public void pause() {
        super.pause();
        game.message(new PauseGameMessage());
    }

}
