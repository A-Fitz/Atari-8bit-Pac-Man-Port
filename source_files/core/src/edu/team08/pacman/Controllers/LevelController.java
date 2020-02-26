package edu.team08.pacman.Controllers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class LevelController implements Disposable {

    public static final LevelController instance = new LevelController();
    public AssetManager assetManager;

    public int totalPills = 0;

    public int highScore = 0;
    public int score = 0;

    public int playerLives = 4;

    public boolean bigPillEaten = false;
    public boolean playerIsAlive = true;
    private boolean gameOver = false;

    LevelController() {
        assetManager = new AssetManager();
        assetManager.load("sprites/actors.atlas", TextureAtlas.class);

        assetManager.finishLoading();
    }

    public void decreasePlayerLives() {
        playerLives--;
    }

    public void resetPlayerLives() {
        playerLives = 3;
    }

    public void makeGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame(boolean restart) {
        if (restart) {
            score = 0;
            resetPlayerLives();
        }
        totalPills = 0;
        playerIsAlive = true;
        bigPillEaten = false;
        gameOver = false;
    }

    public void addScore(int score) {
        this.score += score;
        if (this.score > highScore) {
            highScore = this.score;
        }
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
