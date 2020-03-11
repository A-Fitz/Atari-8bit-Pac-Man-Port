package edu.team08.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.team08.pacman.screens.PlayScreen;

public class PacManGame extends Game
{
    private SpriteBatch batch;

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(batch));
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }
}
