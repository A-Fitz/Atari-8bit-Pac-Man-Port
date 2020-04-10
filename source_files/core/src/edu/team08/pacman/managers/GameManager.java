package edu.team08.pacman.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.game.Game;
import edu.team08.pacman.states.GameState;
import edu.team08.pacman.states.PlayerState;

/**
 * Maintains some resources and acts as an accessible interface for the Game class.
 */
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
        this.assetManager.load(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
        this.assetManager.load(FilePathConstants.MUSIC_BEGINNING_PATH, Music.class);
        this.assetManager.load(FilePathConstants.MUSIC_DEATH_PATH, Music.class);
        this.assetManager.load(FilePathConstants.SOUND_EAT_FRUIT_PATH, Sound.class);
        this.assetManager.load(FilePathConstants.SOUND_EAT_GHOST_PATH, Sound.class);
        this.assetManager.load(FilePathConstants.SOUND_EAT_PILL_PATH, Sound.class);
        this.assetManager.load(FilePathConstants.SOUND_EXTRA_LIFE_PATH, Sound.class);
        this.assetManager.load(FilePathConstants.SOUND_SIREN_PATH, Sound.class);
        this.assetManager.finishLoading();
        this.textureAtlas = assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
    }

    public static GameManager getInstance()
    {
        return instance;
    }

    public void newGame()
    {
        this.game = new Game();
    }

    public boolean isExtraLifeEarned()
    {
        return this.game.isExtraLifeEarned();
    }

    public void setExtraLifeEarned()
    {
        this.game.setExtraLifeEarned(true);
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

    public AssetManager getAssetManager()
    {
        return this.assetManager;
    }

    public TextureAtlas getTextureAtlas()
    {
        return this.textureAtlas;
    }

    public int getLivesLeft()
    {
        return this.game.getLivesLeft();
    }

    public void setLivesLeft(int livesLeft)
    {
        this.game.setLivesLeft(livesLeft);
    }

    public void addLife()
    {
        this.game.setLivesLeft(this.game.getLivesLeft() + 1);
    }

    /**
     * Happens when Pac-Man is killed.
     */
    public void killPacMan()
    {
        this.game.setLivesLeft(this.game.getLivesLeft() - 1);
        this.game.setPlayerState(PlayerState.DEAD);
    }

    @Override
    public void dispose()
    {
        this.assetManager.dispose();
    }

    public void setGameState(GameState gameState)
    {
        this.game.setGameState(gameState);
    }

    public void setPlayerState(PlayerState playerState)
    {
        this.game.setPlayerState(playerState);
    }

    public GameState getGameState()
    {
        return this.game.getGameState();
    }

    public PlayerState getPlayerState()
    {
        return this.game.getPlayerState();
    }

    public boolean areGhostsFlashing()
    {
        return this.game.areGhostsFlashing();
    }

    public void setGhostsFlashing(boolean ghostsFlashing)
    {
        this.game.setGhostsFlashing(ghostsFlashing);
    }
}
