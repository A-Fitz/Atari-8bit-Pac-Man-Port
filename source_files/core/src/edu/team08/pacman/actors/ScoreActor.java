package edu.team08.pacman.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import edu.team08.pacman.managers.GameManager;

public class ScoreActor extends Actor
{
    private StringBuilder stringBuilder;
    private Label scoreLabel;

    public ScoreActor (float x, float y) {
        this.stringBuilder = new StringBuilder();
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        scoreLabel = new Label("0", labelStyle);
        scoreLabel.setPosition(x, y);
        setBounds(scoreLabel.getX(), scoreLabel.getY(), scoreLabel.getWidth(), scoreLabel.getHeight());
    }

    public void update()
    {
        stringBuilder.setLength(0);
        stringBuilder.append("Score: ");
        stringBuilder.append(GameManager.getInstance().getScore());
        scoreLabel.setText(stringBuilder);
        setBounds(scoreLabel.getX(), scoreLabel.getY(), scoreLabel.getWidth(), scoreLabel.getHeight());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        scoreLabel.draw(batch, parentAlpha);
    }
}
