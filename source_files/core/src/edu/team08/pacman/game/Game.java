/**
 * Class: Game
 *
 * Purpose: Manages the game's data
 */
package edu.team08.pacman.game;

import edu.team08.pacman.constants.GameConstants;
import edu.team08.pacman.states.GameState;
import edu.team08.pacman.states.PlayerState;

public class Game
{
    private int totalPills;
    private int score;
    private int level;
    private int livesLeft;
    private GameState gameState;
    private PlayerState playerState;
    private boolean extraLifeEarned;
    private boolean ghostsFlashing;

    /**
     * Game() sets initial values
     */
    public Game()
    {
        this.totalPills = 0;
        this.score = 0;
        this.level = 1;
        this.livesLeft = GameConstants.STARTING_LIVES;
        this.gameState = GameState.IN_TRANSITION;
        this.playerState = PlayerState.ALIVE;
        this.extraLifeEarned = false;
        this.ghostsFlashing = false;
    }

    public boolean isExtraLifeEarned()
    {
        return extraLifeEarned;
    }

    public void setExtraLifeEarned(boolean extraLifeEarned)
    {
        this.extraLifeEarned = extraLifeEarned;
    }

    public int getTotalPills()
    {
        return totalPills;
    }

    public void setTotalPills(int totalPills)
    {
        this.totalPills = totalPills;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLivesLeft()
    {
        return livesLeft;
    }

    public void setLivesLeft(int livesLeft)
    {
        this.livesLeft = livesLeft;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public PlayerState getPlayerState()
    {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState)
    {
        this.playerState = playerState;
    }

    public boolean areGhostsFlashing()
    {
        return ghostsFlashing;
    }

    public void setGhostsFlashing(boolean ghostsFlashing)
    {
        this.ghostsFlashing = ghostsFlashing;
    }
}
