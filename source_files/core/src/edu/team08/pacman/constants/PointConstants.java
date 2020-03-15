package edu.team08.pacman.constants;

import edu.team08.pacman.BonusNuggetType;

import java.util.HashMap;

public class PointConstants
{
    public static final int PILL_POINTS = 1000;
    public static final int BIG_PILL_POINTS = 50;

    public static final int POINTS_FOR_EXTRA_LIFE = 10000;
    public static final HashMap<Integer, Integer> BLUE_GHOST_TO_POINTS_MAP;
    public static final HashMap<Integer, BonusNuggetType> LEVEL_TO_BONUS_MAP;
    public static final HashMap<Integer, Integer> LEVEL_TO_BONUS_TEXTURE_MAP;
    public static final HashMap<BonusNuggetType, Integer> BONUS_TO_POINTS_MAP;
    private static final int FIRST_BLUE_GHOST_POINTS = 200;
    private static final int SECOND_BLUE_GHOST_POINTS = 400;
    private static final int THIRD_BLUE_GHOST_POINTS = 800;
    private static final int FOURTH_BLUE_GHOST_POINTS = 1600;
    private static final int CHERRY = 100;
    private static final int STRAWBERRY = 300;
    private static final int ORANGE = 500;
    private static final int APPLE = 700;
    private static final int LIME = 1000;
    private static final int GALAXIAN_BOSS = 2000;
    private static final int BELL = 3000;
    private static final int KEY = 5000;

    static
    {
        BLUE_GHOST_TO_POINTS_MAP = new HashMap<>();
        BLUE_GHOST_TO_POINTS_MAP.put(1, FIRST_BLUE_GHOST_POINTS);
        BLUE_GHOST_TO_POINTS_MAP.put(2, SECOND_BLUE_GHOST_POINTS);
        BLUE_GHOST_TO_POINTS_MAP.put(3, THIRD_BLUE_GHOST_POINTS);
        BLUE_GHOST_TO_POINTS_MAP.put(4, FOURTH_BLUE_GHOST_POINTS);
    }

    static
    {
        LEVEL_TO_BONUS_MAP = new HashMap<>();
        LEVEL_TO_BONUS_MAP.put(1, BonusNuggetType.CHERRY);
        LEVEL_TO_BONUS_MAP.put(2, BonusNuggetType.STRAWBERRY);
        LEVEL_TO_BONUS_MAP.put(3, BonusNuggetType.ORANGE);
        LEVEL_TO_BONUS_MAP.put(4, BonusNuggetType.ORANGE);
        LEVEL_TO_BONUS_MAP.put(5, BonusNuggetType.APPLE);
        LEVEL_TO_BONUS_MAP.put(6, BonusNuggetType.APPLE);
        LEVEL_TO_BONUS_MAP.put(7, BonusNuggetType.LIME);
        LEVEL_TO_BONUS_MAP.put(8, BonusNuggetType.LIME);
        LEVEL_TO_BONUS_MAP.put(9, BonusNuggetType.GALAXIAN_BOSS);
        LEVEL_TO_BONUS_MAP.put(10, BonusNuggetType.GALAXIAN_BOSS);
        LEVEL_TO_BONUS_MAP.put(11, BonusNuggetType.BELL);
        LEVEL_TO_BONUS_MAP.put(12, BonusNuggetType.BELL);
        LEVEL_TO_BONUS_MAP.put(13, BonusNuggetType.KEY);
        LEVEL_TO_BONUS_MAP.put(14, BonusNuggetType.KEY);
        LEVEL_TO_BONUS_MAP.put(15, BonusNuggetType.KEY);
        LEVEL_TO_BONUS_MAP.put(16, BonusNuggetType.KEY);
        LEVEL_TO_BONUS_MAP.put(17, BonusNuggetType.KEY);
        LEVEL_TO_BONUS_MAP.put(18, BonusNuggetType.KEY);
        LEVEL_TO_BONUS_MAP.put(19, BonusNuggetType.KEY);
    }

    static
    {
        LEVEL_TO_BONUS_TEXTURE_MAP = new HashMap<>();
        LEVEL_TO_BONUS_TEXTURE_MAP.put(1, 0);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(2, 1);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(3, 2);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(4, 2);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(5, 3);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(6, 3);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(7, 4);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(8, 4);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(9, 5);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(10, 5);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(11, 6);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(12, 6);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(13, 7);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(14, 7);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(15, 7);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(16, 7);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(17, 7);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(18, 7);
        LEVEL_TO_BONUS_TEXTURE_MAP.put(19, 7);
    }

    static
    {
        BONUS_TO_POINTS_MAP = new HashMap<>();
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.CHERRY, CHERRY);
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.STRAWBERRY, STRAWBERRY);
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.ORANGE, ORANGE);
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.APPLE, APPLE);
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.LIME, LIME);
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.GALAXIAN_BOSS, GALAXIAN_BOSS);
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.BELL, BELL);
        BONUS_TO_POINTS_MAP.put(BonusNuggetType.KEY, KEY);
    }
}
