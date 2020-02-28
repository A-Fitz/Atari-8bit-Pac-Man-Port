package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
    private final Vector3 position = new Vector3();

    private final Vector2 scale = new Vector2(1.0f, 1.0f);
    private float rotation = 0.0f;

    public TransformComponent() {
    }

    public TransformComponent(float x, float y, float z) {
        set(x, y, z);
    }

    public void set(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setRotation(float rotation) { this.rotation = rotation; }

    public float getRotation() { return rotation; }

    public Vector2 getScale() { return scale; }

    public Vector3 getPosition() { return position; }
}
