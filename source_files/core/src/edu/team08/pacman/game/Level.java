package edu.team08.pacman.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.team08.pacman.GameContactListener;
import edu.team08.pacman.Util;
import edu.team08.pacman.actors.BonusNuggetsActor;
import edu.team08.pacman.actors.ScoreActor;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.BonusNuggetComponent;
import edu.team08.pacman.components.TextureComponent;
import edu.team08.pacman.components.TransformComponent;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.GameConstants;
import edu.team08.pacman.constants.PointConstants;
import edu.team08.pacman.managers.GameManager;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static edu.team08.pacman.constants.DisplayConstants.ASSET_SIZE;

public class Level
{
    private Engine engine;
    private TiledMap tiledMap;
    private World world;
    private Stage stage;
    private List<Rectangle> bonusNuggetRectangles;
    private WorldBuilder worldBuilder;

    private ScoreActor scoreActor;
    private TimerTask addBonusNuggetTask = new TimerTask()
    {
        @Override
        public void run()
        {
            addBonusNugget();
        }
    };

    public Level(Engine engine, TiledMap tiledMap, World world, Stage stage)
    {
        this.engine = engine;
        this.tiledMap = tiledMap;
        this.world = world;
        this.stage = stage;
        this.bonusNuggetRectangles = new ArrayList<>();
        this.worldBuilder = new WorldBuilder(this.tiledMap, this.engine, this.world);

        this.world.setContactListener(new GameContactListener());
    }

    public void buildLevel()
    {
        this.worldBuilder.addWalls();
        this.worldBuilder.addPills();
        this.worldBuilder.addPlayer();
        getBonusNuggetRectangles();
        addBonusNuggetActors();
        addScoreActor();

        long time = TimeUnit.MILLISECONDS.convert(DisplayConstants.BONUS_NUGGET_SPAWN_TIME, TimeUnit.SECONDS);
        new Timer().scheduleAtFixedRate(addBonusNuggetTask, time, time);
    }

    private void addScoreActor()
    {
        this.scoreActor = new ScoreActor(DisplayConstants.VIEWPORT_WIDTH * 3, DisplayConstants.VIEWPORT_HEIGHT * 17.5f);
        this.stage.addActor(scoreActor);
    }

    private void getBonusNuggetRectangles()
    {
        MapLayers layers = tiledMap.getLayers();
        MapLayer bonus_nugget = layers.get("bonus_nugget");
        for (MapObject mapObject : bonus_nugget.getObjects())
        {
            Rectangle rectangleBonusNugget = ((RectangleMapObject) mapObject).getRectangle();
            Util.correctRectangle(rectangleBonusNugget);
            bonusNuggetRectangles.add(rectangleBonusNugget);
        }
    }

    private void addBonusNuggetActors()
    {
        stage.addActor(new BonusNuggetsActor(getNewBonusNuggetTextureRegion(),
                DisplayConstants.LEVEL_MARKER_BONUS_NUGGET_XPOS - ((GameManager.getInstance().getLevel() - 1) * ASSET_SIZE) - ((ASSET_SIZE / 2) * GameManager.getInstance().getLevel()),
                DisplayConstants.LEVEL_MARKER_BONUS_NUGGET_YPOS));
    }

    private TextureRegion getNewBonusNuggetTextureRegion()
    {
        int texturePosition;

        if (GameManager.getInstance().getLevel() <= GameConstants.MAX_GAME_LEVELS)
        {
            texturePosition = PointConstants.LEVEL_TO_BONUS_TEXTURE_MAP.get(GameManager.getInstance().getLevel());
        } else
        {
            texturePosition = PointConstants.LEVEL_TO_BONUS_TEXTURE_MAP.get(GameConstants.MAX_GAME_LEVELS);
        }

        return new TextureRegion(GameManager.getInstance().getTextureAtlas().findRegion("items"), texturePosition * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE);
    }

    public void update()
    {
        if (GameManager.getInstance().getTotalPills() == 0)
        {
            GameManager.getInstance().endLevel();
        }
        this.scoreActor.update();
    }

    public void addBonusNugget()
    {
        Rectangle bonusNuggetRectangle = getRandomBonusNuggetLocation();

        Entity bonusNuggetEntity = new Entity();

        BodyComponent bodyComponent = new BodyComponent();
        TextureComponent textureComponent = new TextureComponent();
        TransformComponent transformComponent = new TransformComponent();
        BonusNuggetComponent bonusNuggetComponent = new BonusNuggetComponent();

        // create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(bonusNuggetRectangle.x, bonusNuggetRectangle.y);
        Body bonusNuggetBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bonusNuggetRectangle.width, bonusNuggetRectangle.height);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bonusNuggetBody.createFixture(fixtureDef);
        shape.dispose();

        bodyComponent.setBody(bonusNuggetBody);
        transformComponent.setPosition(bonusNuggetRectangle.x, bonusNuggetRectangle.y);

        textureComponent.setRegion(getNewBonusNuggetTextureRegion());

        bonusNuggetEntity.add(bodyComponent);
        bonusNuggetEntity.add(textureComponent);
        bonusNuggetEntity.add(transformComponent);
        bonusNuggetEntity.add(bonusNuggetComponent);
        bonusNuggetBody.setUserData(bonusNuggetEntity);
        engine.addEntity(bonusNuggetEntity);
    }

    private Rectangle getRandomBonusNuggetLocation()
    {
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        return bonusNuggetRectangles.get(rand.nextInt(bonusNuggetRectangles.size()));
    }
}
