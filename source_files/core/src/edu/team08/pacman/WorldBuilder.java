package edu.team08.pacman;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
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
import edu.team08.pacman.components.*;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.managers.GameManager;

public class WorldBuilder {
    private final TiledMap tiledMap;
    private final World world;
    private final PooledEngine engine;
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

    public WorldBuilder(TiledMap tiledMap, PooledEngine engine, World world, SpriteBatch batch) {
        this.tiledMap = tiledMap;
        this.engine = engine;
        this.world = world;
        this.batch = batch;
        textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
    }

    public void build() {
        buildMap();
    }

    private void buildMap() {

        MapLayers mapLayers = tiledMap.getLayers();

        MapLayer wall = mapLayers.get("wall");
        for (MapObject mapObject : wall.getObjects()) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bodyDef);

        }

        MapLayer pill = mapLayers.get("pill");
        for (MapObject mapObject : pill.getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            centerRectangle(rectangle);

            if (mapObject.getProperties().containsKey("big")) {
                createPill(rectangle, true);
            } else {
                createPill(rectangle, false);
            }
        }

        // player
        MapLayer playerLayer = mapLayers.get("player"); // player layer
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
            circleShape.setRadius(0.2f);
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
        Entity entity = engine.createEntity();
        //add components
        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);
        TextureComponent textComp = engine.createComponent(TextureComponent.class);
        TextureAtlas textureAtlas = GameManager.instance.assetManager.get(FilePathConstants.SPRITES_PATH, TextureAtlas.class);
        textComp.region = new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, 16, 16);


        // set the components data
        body.body = createOval(rectangle.x, rectangle.y, true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(rectangle.x, rectangle.y, 1);
        type.type = TypeComponent.PLAYER;
        stateCom.set(EntityStates.IDLE_RIGHT);
        body.body.setUserData(entity);

        // add components to entity
        entity.add(body);
        entity.add(position);
        entity.add(player);
        entity.add(type);
        entity.add(stateCom);
        entity.add(textComp);

        //add entity to engine
        engine.addEntity(entity);

    }

    private Body createOval(float x, float y, boolean dynamic) {
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

