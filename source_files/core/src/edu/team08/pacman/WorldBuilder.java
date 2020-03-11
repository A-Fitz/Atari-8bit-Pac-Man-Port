package edu.team08.pacman;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import edu.team08.pacman.components.*;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.constants.MovementConstants;
import edu.team08.pacman.managers.GameManager;

public class WorldBuilder
{
    private final TiledMap tiledMap;
    private final World world;
    private final PooledEngine engine;
    private TextureAtlas textureAtlas;

    public WorldBuilder(TiledMap tiledMap, PooledEngine engine, World world, SpriteBatch batch)
    {
        this.tiledMap = tiledMap;
        this.engine = engine;
        this.world = world;
        textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
    }

    public void buildMap()
    {
        MapLayers mapLayers = tiledMap.getLayers();
        addPlayer(mapLayers);
        addWalls(mapLayers);
        addPills(mapLayers);
    }

    /**
     * addWalls creates all the walls that are contained in the map and add then to the wall layer.
     *
     * @param layers MapLayer
     */
    private void addWalls(MapLayers layers)
    {
        MapLayer wall = layers.get("wall");
        for (MapObject mapObject : wall.getObjects())
        {
            Rectangle rectangleWall = ((RectangleMapObject) mapObject).getRectangle();
            correctRectangle(rectangleWall);
            createWall(rectangleWall);
        }
    }

    private void createWall(Rectangle rectangle)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(rectangle.x, rectangle.y);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.width, rectangle.height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    /**
     * addPills creates each pill and adds them to the pill layer.
     *
     * @param layers MapLayers
     */
    private void addPills(MapLayers layers)
    {
        MapLayer pill = layers.get("pill");
        for (MapObject mapObject : pill.getObjects())
        {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            correctRectangle(rectangle);

            if (mapObject.getProperties().containsKey("big"))
            {
                createPill(rectangle, true);
            } else
            {
                createPill(rectangle, false);
            }
        }
    }

    private void createPill(Rectangle rectangle, boolean big)
    {
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TextureComponent textComp = engine.createComponent(TextureComponent.class);
        TextureAtlas textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
        type.type = TypeComponent.PILL;

        position.set(rectangle.x, rectangle.y, 5);

        TextureRegion textureRegion;
        if (big)
        {
            textureRegion = new TextureRegion(textureAtlas.findRegion("pill"), 16, 0, 16, 16);
        } else
        {
            textureRegion = new TextureRegion(textureAtlas.findRegion("pill"), 0, 0, 16, 16);
        }

        textComp.region = textureRegion;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(rectangle.x, rectangle.y);
        Body body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();

        if (big)
        {
            circleShape.setRadius(rectangle.width);
        } else
        {
            circleShape.setRadius(rectangle.width / 2);
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);

        circleShape.dispose();

        Entity entity = engine.createEntity();

        entity.add(textComp);
        entity.add(position);
        entity.add(type);
        engine.addEntity(entity);
        body.setUserData(entity);


        GameManager.instance.totalPills++;
    }

    /**
     * addPlayer creates a Player and adds it to the world
     *
     * @param layers MapLayers
     */
    private void addPlayer(MapLayers layers)
    {
        MapLayer playerLayer = layers.get("player"); // player layer
        for (MapObject mapObject : playerLayer.getObjects())
        {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            correctRectangle(rectangle);
            createPlayer(rectangle);
        }
    }

    /**
     * Converts a Rectangle object's position and scale from the TiledMap values to our game values.
     *
     * @param rectangle A given rectangle, some TiledMap object.
     */
    private void correctRectangle(Rectangle rectangle)
    {
        rectangle.x = (rectangle.x / DisplayConstants.ASSET_SIZE) + (rectangle.width / DisplayConstants.ASSET_SIZE) / 2;
        rectangle.y = (rectangle.y / DisplayConstants.ASSET_SIZE) + (rectangle.height / DisplayConstants.ASSET_SIZE) / 2;
        rectangle.width = rectangle.width / (2 * DisplayConstants.ASSET_SIZE);
        rectangle.height = rectangle.height / (2 * DisplayConstants.ASSET_SIZE);
    }

    private void createPlayer(Rectangle rectangle)
    {
        Entity playerEntity = new Entity();
        // add components
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        TextureAtlas textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);

        // create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(rectangle.x, rectangle.y);
        Body playerBody = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(rectangle.width * DisplayConstants.MOVING_ENTITY_BODY_SCALE); // Player needs to be able to move, so the scale needs to be slightly smaller than the walls around it
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        playerBody.createFixture(fixtureDef);
        circleShape.dispose();

        // set starting component values
        textureComponent.region = new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, 16, 16);
        bodyComponent.setBody(playerBody);
        bodyComponent.setSpeed(MovementConstants.PACMAN_SPEED);
        transformComponent.getPosition().set(rectangle.x, rectangle.y, 1);
        stateComponent.setState(EntityStates.START);
        type.type = TypeComponent.PLAYER;

        // add components
        playerEntity.add(bodyComponent);
        playerEntity.add(transformComponent);
        playerEntity.add(playerComponent);
        playerEntity.add(type);
        playerEntity.add(stateComponent);
        playerEntity.add(textureComponent);

        // create and add animations
        createKeyFrames(animationComponent);
        playerEntity.add(animationComponent);

        // finish entity creation
        engine.addEntity(playerEntity);
        bodyComponent.getBody().setUserData(playerEntity);
    }

    private void createKeyFrames(AnimationComponent animationComponent)
    {
        Array<TextureRegion> keyFrames = new Array<>();
        Animation<TextureRegion> animation;
        int ASSET_SIZE = (int)DisplayConstants.ASSET_SIZE;

        // start (same as idle right)
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 1 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.START, animation);
        keyFrames.clear();

        // idle
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 1 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_RIGHT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 2 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_LEFT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_UP, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 4 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_DOWN, animation);
        keyFrames.clear();

        // moving
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, 16, 16));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_RIGHT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 2 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_LEFT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_UP, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 4 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_DOWN, animation);
        keyFrames.clear();

        // dying
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        for (int i = 5; i <= 17; i++)
        {
            keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), i * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        }
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.DYING, animation);
        keyFrames.clear();
    }
}