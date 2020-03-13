package edu.team08.pacman.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class GameManager implements Disposable
{

    public static final GameManager instance = new GameManager();
    public AssetManager assetManager;

    public int totalPills = 0;
    private int score = 0;
    private int level = 1;

    GameManager()
    {
        assetManager = new AssetManager();
        assetManager.load("sprites/actors.atlas", TextureAtlas.class);

        assetManager.finishLoading();
    }

    public int getScore()
    {
        return score;
    }

    public void AddScore(int scoreToAdd)
    {
        score = +scoreToAdd;
    }

    public void ResetScore()
    {
        score = 0;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void increaseLevel()
    {
        this.level++;
    }

    @Override
    public void dispose()
    {
        assetManager.dispose();
    }
}
