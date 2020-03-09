package edu.team08.pacman.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import edu.team08.pacman.components.TextureComponent;
import edu.team08.pacman.components.TransformComponent;
import edu.team08.pacman.constants.DisplayConstants;

public class RenderSystem extends EntitySystem
{
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<TransformComponent> transformComponentComponentMapper = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<TextureComponent> textureComponentComponentMapper = ComponentMapper.getFor(TextureComponent.class);

    public RenderSystem(SpriteBatch batch, OrthographicCamera camera)
    {
        this.batch = batch;

        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, TextureComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine)
    {

    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        TransformComponent position;
        TextureComponent visual;

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i)
        {
            Entity e = entities.get(i);

            position = transformComponentComponentMapper.get(e);
            visual = textureComponentComponentMapper.get(e);

            float width = visual.region.getRegionWidth() / DisplayConstants.ASSET_SIZE;
            float height = visual.region.getRegionHeight() / DisplayConstants.ASSET_SIZE;
            float originX = width / 2;
            float originY = height / 2;
            batch.draw(visual.region,
                    position.getPosition().x - originX, position.getPosition().y - originY,
                    originX, originY,
                    width, height,
                    position.getScale().x, position.getScale().y,
                    position.getRotation() * MathUtils.radiansToDegrees);
        }

        batch.end();
    }
}