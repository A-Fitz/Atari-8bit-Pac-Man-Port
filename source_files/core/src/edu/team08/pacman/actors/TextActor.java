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

public class TextActor extends Actor
{
    private Label scoreLabel;
    private FreeTypeFontGenerator freeTypeFontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter;
    private BitmapFont bitmapFont;

    public TextActor(float x, float y, String text, Color color)
    {
        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FilePathConstants.FONT_PATH));
        freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        freeTypeFontParameter.size = DisplayConstants.TEXT_ACTOR_FONT_SIZE;
        freeTypeFontParameter.borderWidth = 5;
        freeTypeFontParameter.borderColor = Color.CLEAR;
        freeTypeFontParameter.color = Color.WHITE;

        bitmapFont = freeTypeFontGenerator.generateFont(freeTypeFontParameter);

        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, color);
        scoreLabel = new Label(text, labelStyle);
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