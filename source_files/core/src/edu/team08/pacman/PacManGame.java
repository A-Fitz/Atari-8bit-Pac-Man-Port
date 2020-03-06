/***********************************************************************************************************************
 * Class: PacManGame
 *
 * Purpose: Creates main screen for the game and manages players score.
 **********************************************************************************************************************/
package edu.team08.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import edu.team08.pacman.screens.PlayScreen;

public class PacManGame extends Game {
    private SpriteBatch batch;
    private AssetManager assetManager;
    private int score;
    private PlayScreen playScreen;

    public PacManGame(){
        this.assetManager = new AssetManager();
        this.batch = new SpriteBatch();
        this.playScreen = new PlayScreen(this.batch);

        this.assetManager.load("sprites/actors.atlas", TextureAtlas.class);
        this.assetManager.finishLoading();
    }

    @Override
    public void create() {}

    @Override
    public void render() { this.setScreen(playScreen); }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public AssetManager getAssetManager() {return this.assetManager;}

    public int getScore() { return this.score; }

    public void AddScore(int scoreToAdd) { this.score =+ scoreToAdd; }

    public void ResetScore() { this.score = 0; }
}
