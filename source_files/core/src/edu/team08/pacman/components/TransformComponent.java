package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component
{
    private final Vector2 position = new Vector2();

    private final Vector2 scale = new Vector2(1.0f, 1.0f);
    private float rotation = 0.0f;

    public void set(float x, float y)
    {
        this.position.x = x;
        this.position.y = y;
    }

    public void set(Vector2 pos)
    {
        this.position.x = pos.x;
        this.position.y = pos.y;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public Vector2 getScale()
    {
        return scale;
    }

    public Vector2 getPosition()
    {
        return position;
    }
}
