package edu.team08.pacman.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import edu.team08.pacman.constants.FilePathConstants;

public class GameManager implements Disposable
{

    private static final GameManager instance = new GameManager();

    private int totalPills;
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;
    private int score;
    private int level;
    private boolean levelEnded;

    GameManager()
    {
        this.assetManager = new AssetManager();
        this.assetManager.load("sprites/actors.atlas", TextureAtlas.class);
        this.assetManager.finishLoading();
        this.textureAtlas = assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);

        this.totalPills = 0;
        this.score = 0;
        this.level = 0;
        this.levelEnded = false;
    }

    public static GameManager getInstance()
    {
        return instance;
    }

    public int getTotalPills()
    {
        return totalPills;
    }

    public void increaseTotalPills()
    {
        this.totalPills++;
    }

    public void decreaseTotalPills()
    {
        this.totalPills--;
    }

    public int getScore()
    {
        return this.score;
    }

    public void addScore(int scoreToAdd)
    {
        this.score += scoreToAdd;
    }

    public void resetScore()
    {
        this.score = 0;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void newLevel()
    {
        this.totalPills = 0;
        this.level++;
    }

    public AssetManager getAssetManager()
    {
        return this.assetManager;
    }

    public TextureAtlas getTextureAtlas()
    {
        return this.textureAtlas;
    }

    public boolean isLevelEnded()
    {
        return this.levelEnded;
    }

    /**
     * Use this method when a condition is reached where the level should end.
     */
    public void endLevel()
    {
        this.levelEnded = true;
    }

    @Override
    public void dispose()
    {
        this.assetManager.dispose();
    }
}
