package edu.team08.pacman.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.game.Game;
import edu.team08.pacman.states.GameState;

public class GameManager implements Disposable
{

    private static final GameManager instance = new GameManager();

    private Game game;
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public GameManager()
    {
        this.game = new Game();
        this.assetManager = new AssetManager();
        this.assetManager.load("sprites/actors.atlas", TextureAtlas.class);
        this.assetManager.finishLoading();
        this.textureAtlas = assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
    }

    public static GameManager getInstance()
    {
        return instance;
    }

    public int getTotalPills()
    {
        return game.getTotalPills();
    }

    public void increaseTotalPills()
    {
        this.game.setTotalPills(this.game.getTotalPills() + 1);
    }

    public void decreaseTotalPills()
    {
        this.game.setTotalPills(this.game.getTotalPills() - 1);
    }

    public int getScore()
    {
        return this.game.getScore();
    }

    public void addScore(int scoreToAdd)
    {
        this.game.setScore(this.game.getScore() + scoreToAdd);
    }

    public void resetScore()
    {
        this.game.setScore(0);
    }

    public int getLevel()
    {
        return this.game.getLevel();
    }

    public void setLevel(int level)
    {
        this.game.setLevel(level);
    }

    public void newLevel()
    {
        this.game.setLevelEnded(false);
        this.game.setTotalPills(0);
        this.game.setLevel(this.game.getLevel() + 1);
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
        return this.game.isLevelEnded();
    }

    /**
     * Use this method when a condition is reached where the level should end.
     */
    public void endLevel()
    {
        this.game.setLevelEnded(true);
    }

    public int getLivesLeft()
    {
        return this.game.getLivesLeft();
    }

    public void setLivesLeft(int livesLeft)
    {
        this.game.setLivesLeft(livesLeft);
    }

    /**
     * Happens when Pac-Man is killed. Please add some wait timer before calling this so we can see the death animation.
     */
    public void decreaseLivesLeft()
    {
        int potentialLivesLeft = this.game.getLivesLeft() - 1;

        if (potentialLivesLeft <= 0)
        {
            this.game.setLivesLeft(0);
            this.game.setGameState(GameState.ENDING);
        } else
        {
            this.game.setLivesLeft(potentialLivesLeft);
        }

        this.game.setLevelEnded(true);
    }

    public boolean isGameEnding()
    {
        return this.game.getGameState() == GameState.ENDING;
    }

    public GameState getGameState()
    {
        return this.game.getGameState();
    }

    public void setGameState(GameState gameState)
    {
        this.game.setGameState(gameState);
    }

    @Override
    public void dispose()
    {
        this.assetManager.dispose();
    }
}
