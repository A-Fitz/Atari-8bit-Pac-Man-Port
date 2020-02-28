package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyComponent implements Component {
    private Body body;
    public BodyComponent() {
    }

    public BodyComponent(Body body) {
        this.body = body;
    }

    public Body getBody() { return body; }

    public void setBody(Body body) {
        this.body = body;
    }
}
