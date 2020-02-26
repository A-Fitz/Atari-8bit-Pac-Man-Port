package edu.team08.pacman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.team08.pacman.Controllers.InputController;
import edu.team08.pacman.Controllers.LevelController;

public class PacManGame extends Game {
	SpriteBatch batch;
	Texture img;
	InputController inputController;
	LevelController levelController;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(batch));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
