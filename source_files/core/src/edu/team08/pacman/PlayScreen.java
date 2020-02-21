package edu.team08.pacman;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen implements Screen {
    private final float WIDTH = 19.0f;
    private final float HEIGHT = 19.0f;

    private final PacManGame game;
    private SpriteBatch batch;

    private FitViewport viewport;
    private OrthographicCamera camera;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private Sprite pacmanSprite;

    public PlayScreen(PacManGame game)
    {
        this.game = game;
        this.batch = game.batch;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        camera.translate(WIDTH / 2, HEIGHT / 2);
        camera.update();

        tiledMap = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f, batch);

        TextureAtlas textureAtlas = GameManager.instance.assetManager.get("sprites/actors.atlas", TextureAtlas.class);
        pacmanSprite = new Sprite(new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, 16, 16));
        pacmanSprite.setBounds(8f, 21.5f, 16 / GameManager.PPM, 16 / GameManager.PPM);
    }

    @Override
    public void render(float delta) {
        // set background color, stop glitching on resize
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        pacmanSprite.setPosition(9, 9);
        pacmanSprite.draw(batch);
        batch.end();
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
