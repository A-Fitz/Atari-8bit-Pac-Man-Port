package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import edu.team08.pacman.constants.DisplayConstants;

public class BonusNuggetComponent implements Component
{
    private float timeLeft;

    public BonusNuggetComponent()
    {
        timeLeft = DisplayConstants.BONUS_NUGGET_DURATION;
    }

    public float getTimeLeft()
    {
        return this.timeLeft;
    }

    public void decreaseTimeLeft(float decreaseTime)
    {
        this.timeLeft -= decreaseTime;
    }
}
