package edu.team08.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import edu.team08.pacman.game.TitleScreen;
import edu.team08.pacman.managers.InputManager;

public class PacManGame extends Game
{
    @Override
    public void create()
    {
        Gdx.input.setInputProcessor(InputManager.getInstance());
        setScreen(new TitleScreen(this));
    }
}
