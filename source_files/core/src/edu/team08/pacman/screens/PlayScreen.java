package edu.team08.pacman.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.team08.pacman.WorldBuilder;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.managers.InputManager;
import edu.team08.pacman.systems.*;

public class PlayScreen implements Screen {
    private SpriteBatch batch;

    private FitViewport stageViewport;
    private Stage stage;
    private FitViewport viewport;

    private OrthographicCamera camera;

    private PooledEngine engine;
    private World world;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public PlayScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(DisplayConstants.TILEDMAP_WIDTH, DisplayConstants.TILEDMAP_HEIGHT, camera);
        camera.translate(DisplayConstants.TILEDMAP_WIDTH / 2, DisplayConstants.TILEDMAP_HEIGHT / 2);
        camera.update();


        float unitScale = 1.0f / DisplayConstants.ASSET_SIZE;

        batch.setProjectionMatrix(camera.combined);

        // box2d
        world = new World(new Vector2(0, 0), true);

        engine = new PooledEngine();
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new RenderSystem(batch, camera));
        engine.addSystem(new PlayerControlSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new PillSystem());
        stageViewport = new FitViewport(DisplayConstants.TILEDMAP_WIDTH * 20, DisplayConstants.TILEDMAP_HEIGHT * 20);
        stage = new Stage(stageViewport, batch);
        // set the input controller
        Gdx.input.setInputProcessor(InputManager.getInstance());

        tiledMap = new TmxMapLoader().load(FilePathConstants.TILEDMAP_PATH);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale, batch);

        new WorldBuilder(tiledMap, engine, world, batch).build();
    }

    @Override
    public void render(float delta) {
        // set background color, stop glitching on resize
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        engine.update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }
}
