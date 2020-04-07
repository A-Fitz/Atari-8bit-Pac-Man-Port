package edu.team08.pacman.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import edu.team08.pacman.Util;
import edu.team08.pacman.components.*;
import edu.team08.pacman.constants.CategoryBitsConstants;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.GameConstants;
import edu.team08.pacman.constants.MovementConstants;
import edu.team08.pacman.managers.GameManager;
import edu.team08.pacman.states.EntityState;

import static edu.team08.pacman.constants.DisplayConstants.ASSET_SIZE;

public class WorldBuilder
{
    private final World world;
    private final Engine engine;
    private TextureAtlas textureAtlas;
    private MapLayers mapLayers;

    public WorldBuilder(TiledMap tiledMap, Engine engine, World world)
    {
        this.engine = engine;
        this.world = world;
        this.textureAtlas = GameManager.getInstance().getTextureAtlas();
        this.mapLayers = tiledMap.getLayers();
    }

    /**
     * addWalls creates all the walls that are contained in the map and add then to the wall layer.
     */
    public void addWalls()
    {
        MapLayer wall = this.mapLayers.get("wall");
        for (MapObject mapObject : wall.getObjects())
        {
            Rectangle rectangleWall = ((RectangleMapObject) mapObject).getRectangle();
            Util.correctRectangle(rectangleWall);
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
     */
    public void addPills()
    {
        MapLayer pill = this.mapLayers.get("pill");
        for (MapObject mapObject : pill.getObjects())
        {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            Util.correctRectangle(rectangle);

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
        Entity pillEntity = new Entity();

        // create components
        PillComponent pillComponent = new PillComponent();
        TransformComponent transformComponent = new TransformComponent();
        TextureComponent textureComponent = new TextureComponent();
        BodyComponent bodyComponent = new BodyComponent();
        // create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(rectangle.x, rectangle.y);
        Body pillBody = world.createBody(bodyDef);
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
        fixtureDef.filter.categoryBits = CategoryBitsConstants.PILL_BITS;
        pillBody.createFixture(fixtureDef);
        bodyComponent.setBody(pillBody);
        circleShape.dispose();

        // set starting component values
        transformComponent.setPosition(rectangle.x, rectangle.y);
        TextureRegion textureRegion;
        if (big)
        {
            pillComponent.setBig(true);
            textureRegion = new TextureRegion(textureAtlas.findRegion("pill"), 1 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE);
        } else
        {
            pillComponent.setBig(false);
            textureRegion = new TextureRegion(textureAtlas.findRegion("pill"), 0 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE);
        }
        textureComponent.setRegion(textureRegion);

        // add components to entity
        pillEntity.add(textureComponent);
        pillEntity.add(transformComponent);
        pillEntity.add(pillComponent);
        pillEntity.add(bodyComponent);
        if (big)
        {
            StateComponent stateComponent = new StateComponent();
            stateComponent.setState(EntityState.BLINKING);
            AnimationComponent pillAnimationComponent = new AnimationComponent();
            createBigPillAnimationKeyFrames(pillAnimationComponent);
            pillEntity.add(pillAnimationComponent);
            pillEntity.add(stateComponent);
        }

        // finish entity setup
        pillBody.setUserData(pillEntity);
        bodyComponent.getBody().setUserData(pillEntity);
        engine.addEntity(pillEntity);
        GameManager.getInstance().increaseTotalPills();
    }

    private void createBigPillAnimationKeyFrames(AnimationComponent animationComponent)
    {
        Array<TextureRegion> keyFrames = new Array<>();
        Animation<TextureRegion> animation;

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pill"), 1 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pill"), 2 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(DisplayConstants.BIGPILL_ANIMATION_TIME, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityState.BLINKING, animation);
    }

    /**
     * addPlayer creates a Player and adds it to the world
     */
    public void addPlayer()
    {
        MapLayer playerLayer = this.mapLayers.get("player"); // player layer
        for (MapObject mapObject : playerLayer.getObjects())
        {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            Util.correctRectangle(rectangle);
            createPlayer(rectangle);
        }
    }

    private void createPlayer(Rectangle rectangle)
    {
        Entity playerEntity = new Entity();
        // add components
        BodyComponent bodyComponent = new BodyComponent();
        TransformComponent transformComponent = new TransformComponent();
        PlayerComponent playerComponent = new PlayerComponent();
        StateComponent stateComponent = new StateComponent();
        AnimationComponent animationComponent = new AnimationComponent();
        TextureComponent textureComponent = new TextureComponent();

        // create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(rectangle.x, rectangle.y);
        Body playerBody = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(rectangle.width * DisplayConstants.MOVING_ENTITY_BODY_SCALE); // Player needs to be able to move, so the scale needs to be slightly smaller than the walls around it
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = CategoryBitsConstants.PLAYER_BITS;
        playerBody.createFixture(fixtureDef);
        circleShape.dispose();

        // set starting component values
        textureComponent.setRegion(new TextureRegion(textureAtlas.findRegion("pacman"), 2 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        bodyComponent.setBody(playerBody);
        bodyComponent.setSpeed(getPlayerSpeedForLevel(GameManager.getInstance().getLevel()));
        transformComponent.setPosition(rectangle.x, rectangle.y);
        stateComponent.setState(EntityState.MOVING_LEFT);
        bodyComponent.getBody().setLinearVelocity(MathUtils.lerp(bodyComponent.getBody().getLinearVelocity().x, -bodyComponent.getSpeed(), 1), 0);

        // add components
        playerEntity.add(bodyComponent);
        playerEntity.add(transformComponent);
        playerEntity.add(playerComponent);
        playerEntity.add(stateComponent);
        playerEntity.add(textureComponent);

        // create and add animations
        createPacManAnimationKeyFrames(animationComponent);
        playerEntity.add(animationComponent);

        // finish entity creation
        bodyComponent.getBody().setUserData(playerEntity);
        engine.addEntity(playerEntity);
    }

    private float getPlayerSpeedForLevel(int level)
    {
        if (level <= GameConstants.MAX_GAME_LEVELS)
        {
            return MovementConstants.LEVEL_TO_PACMAN_SPEED_MAP.get(level);
        } else
        {
            return MovementConstants.LEVEL_TO_PACMAN_SPEED_MAP.get(GameConstants.MAX_GAME_LEVELS);
        }
    }

    private void createPacManAnimationKeyFrames(AnimationComponent animationComponent)
    {
        Array<TextureRegion> keyFrames = new Array<>();
        Animation<TextureRegion> animation;
        float frameDuration = DisplayConstants.PACMAN_ANIMATION_TIME;

        // start
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityState.FROZEN, animation);
        keyFrames.clear();

        // idle
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 1 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityState.IDLE_RIGHT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 2 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityState.IDLE_LEFT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityState.IDLE_UP, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 4 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(1f, keyFrames);
        animationComponent.addAnimation(EntityState.IDLE_DOWN, animation);
        keyFrames.clear();

        // moving
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, ASSET_SIZE, ASSET_SIZE));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 16, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityState.MOVING_RIGHT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, ASSET_SIZE, ASSET_SIZE));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 2 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityState.MOVING_LEFT, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, ASSET_SIZE, ASSET_SIZE));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityState.MOVING_UP, animation);
        keyFrames.clear();

        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, ASSET_SIZE, ASSET_SIZE));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 4 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
        animationComponent.addAnimation(EntityState.MOVING_DOWN, animation);
        keyFrames.clear();

        // dying
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 0, 0, ASSET_SIZE, ASSET_SIZE));
        keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), 3 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        for (int i = 5; i <= 17; i++)
        {
            keyFrames.add(new TextureRegion(textureAtlas.findRegion("pacman"), i * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE));
        }
        animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.NORMAL);
        animationComponent.addAnimation(EntityState.DYING, animation);
    }

    /**
     * createGhosts creates all the ghosts in PAC-MAN
     */
    public void createGhosts(){
        MapLayer ghostLayer = mapLayers.get("Ghosts");
        for(MapObject mapObject: ghostLayer.getObjects()){
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            Util.correctRectangle(rectangle);

            //GameManager.getInstance().ghostSpwanPos.set(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);

            for (int i = 0; i < 4; i++){
                createGhost(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2, i);
            }
        }
   }

    /**
     * createGhost creates a given ghost in PAC-MAN
     */
   private void createGhost(float x, float y, int index) {
       createGhostBody(x, y);
       addGhostComponents();

   }

   private void addGhostComponents(){
       Entity ghostEntity = new Entity();

       BodyComponent bodyComponent = new BodyComponent();
       TransformComponent transformComponent = new TransformComponent();
       PlayerComponent ghostComponent = new PlayerComponent();
       StateComponent stateComponent = new StateComponent();
       AnimationComponent animationComponent = new AnimationComponent();
       TextureComponent textureComponent = new TextureComponent();

       ghostEntity.add(bodyComponent);
       ghostEntity.add(transformComponent);
       ghostEntity.add(ghostComponent);
       ghostEntity.add(stateComponent);
       ghostEntity.add(textureComponent);
       createGhostAnimationKeyFrames(animationComponent, ghostEntity);
   }

   private void createGhostBody(float x, float y){
       BodyDef bodyDef = new BodyDef();
       bodyDef.type = BodyDef.BodyType.DynamicBody;
       bodyDef.position.set(x, y);
       Body body = world.createBody(bodyDef);
       CircleShape circleShape = new CircleShape();
       circleShape.setRadius(0.4f);
       FixtureDef fixtureDef = new FixtureDef();
       fixtureDef.shape = circleShape;
       fixtureDef.filter.maskBits = CategoryBitsConstants.GHOST_BITS;
       fixtureDef.isSensor = true;
       body.createFixture(fixtureDef);
       circleShape.dispose();
   }

   private void createGhostAnimationKeyFrames(AnimationComponent animationComponent, Entity ghostEntity){
       Array<TextureRegion> keyFrames = new Array<>();
       Animation<TextureRegion> animation;
       float frameDuration = DisplayConstants.PACMAN_ANIMATION_TIME;

       // move right
       for (int i = 0; i < 2; i++) {
           keyFrames.add(new TextureRegion(textureAtlas.findRegion("ghost"), 0, 0, ASSET_SIZE, ASSET_SIZE));
       }
       animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
       animationComponent.addAnimation(EntityState.MOVING_RIGHT, animation);
       keyFrames.clear();

       // move left
       for (int i = 2; i < 4; i++) {
           keyFrames.add(new TextureRegion(textureAtlas.findRegion("ghost"), 0, 0, ASSET_SIZE, ASSET_SIZE));
       }
       animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
       animationComponent.addAnimation(EntityState.MOVING_LEFT, animation);
       keyFrames.clear();

       // move up
       for (int i = 4; i < 6; i++) {
           keyFrames.add(new TextureRegion(textureAtlas.findRegion("ghost"), 0, 0, ASSET_SIZE, ASSET_SIZE));
       }
       animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
       animationComponent.addAnimation(EntityState.MOVING_UP, animation);
       keyFrames.clear();

       // move down
       for (int i = 6; i < 8; i++) {
           keyFrames.add(new TextureRegion(textureAtlas.findRegion("ghost"), 0, 0, ASSET_SIZE, ASSET_SIZE));
       }
       animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
       animationComponent.addAnimation(EntityState.MOVING_DOWN, animation);
       keyFrames.clear();

       ghostEntity.add(animationComponent);
    }
}