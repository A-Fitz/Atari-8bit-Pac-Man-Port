package edu.team08.pacman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.team08.pacman.PacManGame;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.game.PlayScreen;
import edu.team08.pacman.managers.InputManager;

public class TitleScreen implements Screen
{
    private SpriteBatch batch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private Texture screenTexture;

    private PacManGame mainGame;

    public TitleScreen(PacManGame mainGame)
    {
        super();
        this.mainGame = mainGame;
    }

    @Override
    public void show()
    {
        camera = new OrthographicCamera();
        camera.translate(DisplayConstants.VIEWPORT_WIDTH / 2, (DisplayConstants.VIEWPORT_HEIGHT / 2) - 3); // -3 is to center the game between the stage
        camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        viewport = new FitViewport(DisplayConstants.VIEWPORT_WIDTH * 20, DisplayConstants.VIEWPORT_HEIGHT * 20, camera);

        screenTexture = new Texture(Gdx.files.internal(FilePathConstants.TITLE_SCREEN_IMAGE_PATH));
    }

    @Override
    public void render(float delta)
    {
        // clears screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (InputManager.getInstance().isKeyPressed(Input.Keys.SPACE))
        {
            mainGame.setScreen(new PlayScreen(mainGame));
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(screenTexture, -190, -190, 400, 400);
        batch.end();
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

    }
}
