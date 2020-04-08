package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import edu.team08.pacman.game.BonusNuggetType;
import edu.team08.pacman.constants.DisplayConstants;

public class BonusNuggetComponent implements Component
{
    private float timeLeft;
    private boolean isEaten;
    private BonusNuggetType bonusNugget;

    public BonusNuggetComponent(BonusNuggetType bonusNugget)
    {
        this.isEaten = false;
        this.bonusNugget = bonusNugget;
        this.timeLeft = DisplayConstants.BONUS_NUGGET_DURATION;
    }

    public float getTimeLeft()
    {
        return this.timeLeft;
    }

    public void decreaseTimeLeft(float decreaseTime)
    {
        this.timeLeft -= decreaseTime;
    }

    public boolean isEaten()
    {
        return isEaten;
    }

    public void setEaten(boolean isEaten)
    {
        this.isEaten = isEaten;
    }

    public BonusNuggetType getBonusNugget()
    {
        return this.bonusNugget;
    }
}
