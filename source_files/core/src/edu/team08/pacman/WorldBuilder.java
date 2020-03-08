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
import edu.team08.pacman.managers.GameManager;

public class WorldBuilder {
    private final TiledMap tiledMap;
    private final World world;
    private final PooledEngine engine;
    private TextureAtlas textureAtlas;

    public WorldBuilder(TiledMap tiledMap, PooledEngine engine, World world, SpriteBatch batch) {
        this.tiledMap = tiledMap;
        this.engine = engine;
        this.world = world;
        textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
    }

    public void build() {
        buildMap();
    }

    private void buildMap() {
        MapLayers mapLayers = tiledMap.getLayers();
        addWalls(mapLayers);
        addPills(mapLayers);
        addPlayer(mapLayers);
    }

    /**
     * addWalls creates all the walls that are contained in the map and add then to the wall layer.
     * @param layers MapLayer
     */
    private void addWalls(MapLayers layers){
        MapLayer wall = layers.get("wall");
        for (MapObject mapObject : wall.getObjects()) {
            Rectangle rectangleWall = ((RectangleMapObject) mapObject).getRectangle();
            centerRectangle(rectangleWall);
            createWall(rectangleWall);
        }
    }

    /**
     * addPills creates each pill and adds them to the pill layer.
     * @param layers MapLayers
     */
    private void addPills(MapLayers layers){
            MapLayer pill = layers.get("pill");
        for (MapObject mapObject : pill.getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            centerRectangle(rectangle);

            if (mapObject.getProperties().containsKey("big")) {
                createPill(rectangle, true);
            } else {
                createPill(rectangle, false);
            }
        }
    }

    /**
     * addPlayer creates a Player and adds it to the world
     * @param layers MapLayers
     */
    private void addPlayer(MapLayers layers)
    {
        MapLayer playerLayer = layers.get("player"); // player layer
        for (MapObject mapObject : playerLayer.getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            centerRectangle(rectangle);
            createPlayer(rectangle);
        }
    }

    private void centerRectangle(Rectangle rectangle) {
        rectangle.x = (rectangle.x / DisplayConstants.ASSET_SIZE) + (rectangle.width / DisplayConstants.ASSET_SIZE) / 2;
        rectangle.y = (rectangle.y / DisplayConstants.ASSET_SIZE) + (rectangle.height / DisplayConstants.ASSET_SIZE) / 2;
    }
    
    private void createWall(Rectangle rectangle)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(rectangle.x, rectangle.y);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.x, rectangle.y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void createPill(Rectangle rectangle, boolean big) {
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TextureComponent textComp = engine.createComponent(TextureComponent.class);
        TextureAtlas textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
        type.type = TypeComponent.PILL;

        position.set(rectangle.x, rectangle.y, 5);

        TextureRegion textureRegion;
        if (big)
            textureRegion = new TextureRegion(textureAtlas.findRegion("pill"), 16, 0, 16, 16);
        else
            textureRegion = new TextureRegion(textureAtlas.findRegion("pill"), 0, 0, 16, 16);

        textComp.region = textureRegion;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(rectangle.x, rectangle.y);
        Body body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();

        if (big)
            circleShape.setRadius(1f);
        else
            circleShape.setRadius(0.1f);

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

    private void createPlayer(Rectangle rectangle) {
        //create an empty entity
        Entity entity = new Entity();
        //add components
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        TextureAtlas textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);

        textureComponent.region = new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, 16, 16);

        bodyComponent.setBody(createOval(rectangle.x, rectangle.y));
        transformComponent.getPosition().set(rectangle.x, rectangle.y, 1);

        type.type = TypeComponent.PLAYER;

        // add components
        entity.add(bodyComponent);
        entity.add(transformComponent);
        entity.add(playerComponent);
        entity.add(type);
        entity.add(stateComponent);
        entity.add(textureComponent);

        // create and add animations
        createKeyFrames(animationComponent);
        entity.add(animationComponent);

        engine.addEntity(entity);
        bodyComponent.getBody().setUserData(entity);
    }

    private void createKeyFrames(AnimationComponent animationComponent)
    {
        Array<TextureRegion> keyFrames = new Array<>();
        Animation<TextureRegion> animation;
        // idle
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 1 * 16, 0, 16, 16));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_RIGHT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 2 * 16, 0, 16, 16));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_LEFT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * 16, 0, 16, 16));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_UP, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 4 * 16, 0, 16, 16));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityStates.IDLE_DOWN, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, 16, 16));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_RIGHT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 2 * 16, 0, 16, 16));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_LEFT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * 16, 0, 16, 16));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_UP, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 4 * 16, 0, 16, 16));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.MOVING_DOWN, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, 16, 16));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * 16, 0, 16, 16));
        for(int i = 5; i <= 17; i++)
            keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), i * 16, 0, 16, 16));
        animation = new Animation<>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityStates.DYING, animation);
        keyFrames.clear();
    }

    private Body createOval(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.linearDamping = 16f;

        Body body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.45f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
        circleShape.dispose();

        return body;
    }
}