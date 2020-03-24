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
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.BonusNuggetComponent;
import edu.team08.pacman.components.TextureComponent;
import edu.team08.pacman.components.TransformComponent;
import edu.team08.pacman.constants.CategoryBitsConstants;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.GameConstants;
import edu.team08.pacman.constants.PointConstants;
import edu.team08.pacman.managers.GameManager;
import edu.team08.pacman.actors.GameInfoActor;
import edu.team08.pacman.actors.ScoreActor;

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
        addLivesActors();

        long time = TimeUnit.MILLISECONDS.convert(DisplayConstants.BONUS_NUGGET_SPAWN_TIME, TimeUnit.SECONDS);
        new Timer().scheduleAtFixedRate(addBonusNuggetTask, time, time);
    }

    private void addLivesActors()
    {
        for (int i = 1; i <= GameManager.getInstance().getLivesLeft(); i++)
        {
            addLivesActor(i);
        }
    }

    private void addLivesActor(int lifeNumber)
    {
        TextureRegion textureRegion = new TextureRegion(GameManager.getInstance().getTextureAtlas().findRegion("pacman"), 2 * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE);
        GameInfoActor livesActor = new GameInfoActor(textureRegion,
                DisplayConstants.GAME_INFO_ACTOR_LEFT_X_POS + ((lifeNumber-1) * ASSET_SIZE) + ((ASSET_SIZE / 2) * (lifeNumber-1)),
                DisplayConstants.GAME_INFO_ACTOR_LOWER_Y_POS);
        stage.addActor(livesActor);
    }

    private void addScoreActor()
    {
        this.scoreActor = new ScoreActor(DisplayConstants.VIEWPORT_WIDTH * 2, DisplayConstants.VIEWPORT_HEIGHT * 17.5f);
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
        List<TextureRegion> textureRegionList = getNewBonusNuggetTextureRegionList();
        for (int i = textureRegionList.size() - 1; i >= 0; i--)
        {
            TextureRegion textureRegion = textureRegionList.get(i);
            stage.addActor(new GameInfoActor(textureRegion,
                    DisplayConstants.GAME_INFO_ACTOR_RIGHT_X_POS - (i * ASSET_SIZE) - ((ASSET_SIZE / 2) * i),
                    DisplayConstants.GAME_INFO_ACTOR_LOWER_Y_POS));
        }
    }

    private List<TextureRegion> getNewBonusNuggetTextureRegionList()
    {
        List<TextureRegion> textureRegionList = new ArrayList<>();
        int level = GameManager.getInstance().getLevel();
        if (level > GameConstants.MAX_GAME_LEVELS)
        {
            level = GameConstants.MAX_GAME_LEVELS;
        }

        for (int i = 0; i < DisplayConstants.MAX_BONUS_NUGGET_LEVEL_MARKERS_IN_STAGE; i++)
        {
            if (level - i < 1)
            {
                break;
            }
            textureRegionList.add(getNewBonusNuggetTextureRegion(level - i));
        }

        return textureRegionList;
    }

    private TextureRegion getNewBonusNuggetTextureRegion(int level)
    {
        int texturePosition;

        if (level <= GameConstants.MAX_GAME_LEVELS)
        {
            texturePosition = PointConstants.LEVEL_TO_BONUS_TEXTURE_MAP.get(level);
        } else
        {
            texturePosition = PointConstants.LEVEL_TO_BONUS_TEXTURE_MAP.get(GameConstants.MAX_GAME_LEVELS);
        }

        return new TextureRegion(GameManager.getInstance().getTextureAtlas().findRegion("items"), texturePosition * ASSET_SIZE, 0, ASSET_SIZE, ASSET_SIZE);
    }

    public void update()
    {
        if(GameManager.getInstance().getScore() >= PointConstants.POINTS_FOR_EXTRA_LIFE && !GameManager.getInstance().isExtraLifeEarned())
        {
            GameManager.getInstance().addLife();
            addLivesActor(GameManager.getInstance().getLivesLeft());
            GameManager.getInstance().extraLifeEarned();
        } else if (GameManager.getInstance().getTotalPills() <= 0)
        {
            GameManager.getInstance().endLevel();
        }
        this.scoreActor.update();
    }

    private void addBonusNugget()
    {
        Rectangle bonusNuggetRectangle = getRandomBonusNuggetLocation();

        Entity bonusNuggetEntity = new Entity();

        BodyComponent bodyComponent = new BodyComponent();
        TextureComponent textureComponent = new TextureComponent();
        TransformComponent transformComponent = new TransformComponent();
        BonusNuggetComponent bonusNuggetComponent = new BonusNuggetComponent(PointConstants.LEVEL_TO_BONUS_MAP.get(GameManager.getInstance().getLevel()));

        // create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(bonusNuggetRectangle.x, bonusNuggetRectangle.y);
        Body bonusNuggetBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bonusNuggetRectangle.width, bonusNuggetRectangle.height);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = CategoryBitsConstants.BONUS_NUGGET_BITS;
        bonusNuggetBody.createFixture(fixtureDef);
        bodyComponent.setBody(bonusNuggetBody);
        shape.dispose();

        transformComponent.setPosition(bonusNuggetRectangle.x, bonusNuggetRectangle.y);

        textureComponent.setRegion(getNewBonusNuggetTextureRegion(GameManager.getInstance().getLevel()));

        bonusNuggetEntity.add(bodyComponent);
        bonusNuggetEntity.add(textureComponent);
        bonusNuggetEntity.add(transformComponent);
        bonusNuggetEntity.add(bonusNuggetComponent);

        bonusNuggetBody.setUserData(bonusNuggetEntity);
        bodyComponent.getBody().setUserData(bonusNuggetEntity);
        engine.addEntity(bonusNuggetEntity);
    }

    private Rectangle getRandomBonusNuggetLocation()
    {
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        return bonusNuggetRectangles.get(rand.nextInt(bonusNuggetRectangles.size()));
    }
}
