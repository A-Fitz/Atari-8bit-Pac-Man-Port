package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import edu.team08.pacman.EntityStates;

public class AnimationComponent implements Component {
    public ArrayMap<EntityStates, Animation<TextureRegion>> animations = new ArrayMap();
}
