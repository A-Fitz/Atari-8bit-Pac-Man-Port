package edu.team08.pacman.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class GameManager implements Disposable {

    public static final GameManager instance = new GameManager();
    public AssetManager assetManager;

    public int totalPills = 0;
    private int score = 0;

    GameManager() {
        assetManager = new AssetManager();
        assetManager.load("sprites/actors.atlas", TextureAtlas.class);

        assetManager.finishLoading();
    }

    public int getScore() { return score; }

    public void AddScore(int scoreToAdd) { score =+ scoreToAdd; }

    public void ResetScore() { score = 0; }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
