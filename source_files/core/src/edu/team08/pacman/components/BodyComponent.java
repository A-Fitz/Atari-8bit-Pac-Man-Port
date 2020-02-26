package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyComponent implements Component {
    public Body body;
    public float speed = 0.0f;
    public BodyComponent() {
    }

    public BodyComponent(Body body) {
        this.body = body;
    }
}
