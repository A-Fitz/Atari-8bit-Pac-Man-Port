package edu.team08.pacman.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import edu.team08.pacman.constants.DisplayConstants;

public class BonusNuggetsActor extends Actor
{
    private TextureRegion region;

    public BonusNuggetsActor (TextureRegion region, float x, float y) {
        this.region = region;
        setBounds(x, y, DisplayConstants.ASSET_SIZE, DisplayConstants.ASSET_SIZE);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
