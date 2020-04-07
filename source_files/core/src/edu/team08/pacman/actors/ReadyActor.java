package edu.team08.pacman.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.managers.GameManager;

public class ReadyActor extends Actor
{
    private Label scoreLabel;
    private FreeTypeFontGenerator freeTypeFontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter;
    private BitmapFont bitmapFont;
    public ReadyActor(float x, float y)
    {
        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FilePathConstants.FONT_PATH));
        freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        freeTypeFontParameter.size = 30;
        freeTypeFontParameter.borderWidth = 5;
        freeTypeFontParameter.borderColor = Color.CLEAR;
        freeTypeFontParameter.color = Color.WHITE;

        bitmapFont = freeTypeFontGenerator.generateFont(freeTypeFontParameter);

        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.valueOf(DisplayConstants.READY_ACTOR_TEXT_COLOR));
        scoreLabel = new Label("READY!", labelStyle);
        scoreLabel.setPosition(x, y);
        setBounds(scoreLabel.getX(), scoreLabel.getY(), scoreLabel.getWidth(), scoreLabel.getHeight());
    }

    public float getX()
    {
        return scoreLabel.getX();
    }

    public float getY()
    {
        return scoreLabel.getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        scoreLabel.draw(batch, parentAlpha);
    }
}