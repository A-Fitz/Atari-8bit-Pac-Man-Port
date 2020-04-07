package edu.team08.pacman.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.PillComponent;
import edu.team08.pacman.components.PlayerComponent;
import edu.team08.pacman.components.StateComponent;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.managers.GameManager;
import edu.team08.pacman.managers.InputManager;
import edu.team08.pacman.states.EntityState;
import edu.team08.pacman.states.GameState;
import edu.team08.pacman.systems.*;

import static edu.team08.pacman.constants.DisplayConstants.ASSET_SIZE;

public class PlayScreen implements Screen
{
    private SpriteBatch batch;
    private Stage stage;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private FitViewport stageViewport;
    private Engine engine;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private Level level;

    public PlayScreen(SpriteBatch batch)
    {
        this.batch = batch;
    }

    @Override
    public void show()
    {
        camera = new OrthographicCamera();
        viewport = new FitViewport(DisplayConstants.VIEWPORT_WIDTH, DisplayConstants.VIEWPORT_HEIGHT, camera);
        camera.translate(DisplayConstants.VIEWPORT_WIDTH / 2, (DisplayConstants.VIEWPORT_HEIGHT / 2) - 3); // -3 is to center the game between the stage
        camera.update();
        box2DDebugRenderer = new Box2DDebugRenderer();

        stageViewport = new FitViewport(DisplayConstants.VIEWPORT_WIDTH * 20, DisplayConstants.VIEWPORT_HEIGHT * 20);

        // create level
        newLevel();

        // set input manager
        Gdx.input.setInputProcessor(InputManager.getInstance());
    }

    private void newLevel()
    {
        if(GameManager.getInstance().getGameState() != GameState.STARTING)
            GameManager.getInstance().setGameState(GameState.IN_TRANSITION);

        GameManager.getInstance().newLevel();

        // setup SpriteBatch and camera
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // box2d
        world = new World(new Vector2(0, 0), true);
        stage = new Stage(stageViewport, batch);

        // create new systems and add to engine
        engine = new Engine();
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new RenderSystem(batch, camera));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new PillSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new BonusNuggetSystem());
        engine.addSystem(new AnimationSystem());

        // build map and world
        tiledMap = new TmxMapLoader().load(FilePathConstants.TILEDMAP_PATH);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1.0f / ASSET_SIZE, batch);

        // build level
        this.level = new Level(engine, tiledMap, world, stage);
        level.buildLevel();
    }

    @Override
    public void render(float delta)
    {
        // set background color, stop glitching on resize
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        level.update();
        box2DDebugRenderer.render(world, camera.combined);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        engine.update(delta);
        stage.draw();

        checkEndOfLevelConditions();

        if (GameManager.getInstance().getGameState() == GameState.ENDED)
        {
            // TODO end game screen
            System.exit(0);
        } else if (GameManager.getInstance().isLevelEnded() && GameManager.getInstance().getGameState() == GameState.IN_PROGRESS)
        {
            GameManager.getInstance().setGameState(GameState.IN_TRANSITION); // used to do this if statement only once
            endLevel();
        }
    }

    public void endLevel()
    {
        // failure animation
        if(GameManager.getInstance().isPacManKilled())
        {
            // TODO remove ghost entities

            Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
            player.getComponent(StateComponent.class).setState(EntityState.DYING);
            player.getComponent(PlayerComponent.class).setAlive(false); // TODO do this in contact listener for ghost/player contact

            GameManager.getInstance().getAssetManager().get(FilePathConstants.MUSIC_DEATH_PATH, Music.class).play();
            com.badlogic.gdx.utils.Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    newLevel();
                }
            }, DisplayConstants.END_LEVEL_DISPLAY_TIME);
        } else // success animation
        {
            // TODO remove ghost entities

            // remove pill entities
            ImmutableArray<Entity> pillEntities = engine.getEntitiesFor(Family.all(PillComponent.class).get());
            for(Entity pillEntity : pillEntities)
            {
                Body body = pillEntity.getComponent(BodyComponent.class).getBody();
                body.getWorld().destroyBody(body);
                engine.removeEntity(pillEntity);
            }

            Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
            player.getComponent(StateComponent.class).setState(EntityState.FROZEN);

            // TODO flash the tiledmap somehow

            // wait time
            com.badlogic.gdx.utils.Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    newLevel();
                }
            }, DisplayConstants.END_LEVEL_DISPLAY_TIME);
        }
    }

    private void checkEndOfLevelConditions()
    {
        if (GameManager.getInstance().getTotalPills() <= 0)
        {
            GameManager.getInstance().endLevel();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        batch.dispose();
        stage.dispose();
    }

}
