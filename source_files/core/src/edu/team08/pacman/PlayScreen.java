package edu.team08.pacman;

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
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen implements Screen {
    private SpriteBatch batch;

    private FitViewport viewport;
    private OrthographicCamera camera;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private Sprite pacmanSprite;

    public PlayScreen(SpriteBatch batch)
    {
        this.batch = batch;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(DisplayConstants.TILEDMAP_WIDTH, DisplayConstants.TILEDMAP_HEIGHT, camera);
        camera.translate(DisplayConstants.TILEDMAP_WIDTH / 2, DisplayConstants.TILEDMAP_HEIGHT / 2);
        camera.update();

        tiledMap = new TmxMapLoader().load(FilePathConstants.TILEDMAP_PATH);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f, batch);

        TextureAtlas textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
        pacmanSprite = new Sprite(new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, 16, 16));
        pacmanSprite.setBounds(8f, 21.5f, 1, 1);
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
        pacmanSprite.setPosition(9, 5);
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
