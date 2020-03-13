package edu.team08.pacman.game;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.team08.pacman.constants.*;
import edu.team08.pacman.managers.GameManager;
import edu.team08.pacman.managers.InputManager;
import edu.team08.pacman.systems.*;

import static edu.team08.pacman.constants.DisplayConstants.ASSET_SIZE;

public class PlayScreen implements Screen
{
    private SpriteBatch batch;
    private Stage stage;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private Engine engine;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

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

        // setup stage
        FitViewport stageViewport = new FitViewport(DisplayConstants.VIEWPORT_WIDTH * 20, DisplayConstants.VIEWPORT_HEIGHT * 20);
        stage = new Stage(stageViewport, batch);

        // create level
        newLevel();

        // set the input controller
        Gdx.input.setInputProcessor(InputManager.getInstance());
    }

    private void newLevel()
    {
        GameManager.getInstance().newLevel();

        // setup SpriteBatch and camera
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // box2d
        World world = new World(new Vector2(0, 0), true);

        // create new systems and add to engine
        engine = new Engine();
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new RenderSystem(batch, camera));
        engine.addSystem(new PlayerControlSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new PillSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new BonusNuggetSystem());

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

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        engine.update(delta);
        stage.draw();

        if(GameManager.getInstance().isLevelEnded())
        {
            newLevel();
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
