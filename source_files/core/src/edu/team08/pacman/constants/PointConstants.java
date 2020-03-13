package edu.team08.pacman.constants;

import java.util.HashMap;

public class PointConstants
{
    public static final int PILL_POINTS = 10;
    public static final int BIG_PILL_POINTS = 50;

    public static final int POINTS_FOR_EXTRA_LIFE = 10000;

    private static final int FIRST_BLUE_GHOST_POINTS = 200;
    private static final int SECOND_BLUE_GHOST_POINTS = 400;
    private static final int THIRD_BLUE_GHOST_POINTS = 800;
    private static final int FOURTH_BLUE_GHOST_POINTS = 1600;
    public static final HashMap<Integer, Integer> BLUE_GHOST_TO_POINTS_MAP;
    static {
        BLUE_GHOST_TO_POINTS_MAP = new HashMap<>();
        BLUE_GHOST_TO_POINTS_MAP.put(1, FIRST_BLUE_GHOST_POINTS);
        BLUE_GHOST_TO_POINTS_MAP.put(2, SECOND_BLUE_GHOST_POINTS);
        BLUE_GHOST_TO_POINTS_MAP.put(3, THIRD_BLUE_GHOST_POINTS);
        BLUE_GHOST_TO_POINTS_MAP.put(4, FOURTH_BLUE_GHOST_POINTS);
    }

    private static final int CHERRY = 100;
    private static final int STRAWBERRY = 300;
    private static final int ORANGE = 500;
    private static final int APPLE = 700;
    private static final int LIME = 1000;
    private static final int GALAXIAN_BOSS = 2000;
    private static final int BELL = 3000;
    private static final int KEY = 5000;

    public static final HashMap<Integer, BonusNuggets> LEVEL_TO_BONUS_MAP;
    static {
        LEVEL_TO_BONUS_MAP = new HashMap<>();
        LEVEL_TO_BONUS_MAP.put(1, BonusNuggets.CHERRY);
        LEVEL_TO_BONUS_MAP.put(2, BonusNuggets.STRAWBERRY);
        LEVEL_TO_BONUS_MAP.put(3, BonusNuggets.ORANGE);
        LEVEL_TO_BONUS_MAP.put(4, BonusNuggets.ORANGE);
        LEVEL_TO_BONUS_MAP.put(5, BonusNuggets.APPLE);
        LEVEL_TO_BONUS_MAP.put(6, BonusNuggets.APPLE);
        LEVEL_TO_BONUS_MAP.put(7, BonusNuggets.LIME);
        LEVEL_TO_BONUS_MAP.put(8, BonusNuggets.LIME);
        LEVEL_TO_BONUS_MAP.put(9, BonusNuggets.GALAXIAN_BOSS);
        LEVEL_TO_BONUS_MAP.put(10, BonusNuggets.GALAXIAN_BOSS);
        LEVEL_TO_BONUS_MAP.put(11, BonusNuggets.BELL);
        LEVEL_TO_BONUS_MAP.put(12, BonusNuggets.BELL);
        LEVEL_TO_BONUS_MAP.put(13, BonusNuggets.KEY);
        LEVEL_TO_BONUS_MAP.put(14, BonusNuggets.KEY);
        LEVEL_TO_BONUS_MAP.put(15, BonusNuggets.KEY);
        LEVEL_TO_BONUS_MAP.put(16, BonusNuggets.KEY);
        LEVEL_TO_BONUS_MAP.put(17, BonusNuggets.KEY);
        LEVEL_TO_BONUS_MAP.put(18, BonusNuggets.KEY);
        LEVEL_TO_BONUS_MAP.put(19, BonusNuggets.KEY);
    }

    public static final HashMap<Integer, Integer> LEVEL_TO_BONUS_TEXTURE_MAP;
    static {
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

    public static final HashMap<BonusNuggets, Integer> BONUS_TO_POINTS_MAP;
    static {
        BONUS_TO_POINTS_MAP = new HashMap<>();
        BONUS_TO_POINTS_MAP.put(BonusNuggets.CHERRY, CHERRY);
        BONUS_TO_POINTS_MAP.put(BonusNuggets.STRAWBERRY, STRAWBERRY);
        BONUS_TO_POINTS_MAP.put(BonusNuggets.ORANGE, ORANGE);
        BONUS_TO_POINTS_MAP.put(BonusNuggets.APPLE, APPLE);
        BONUS_TO_POINTS_MAP.put(BonusNuggets.LIME, LIME);
        BONUS_TO_POINTS_MAP.put(BonusNuggets.GALAXIAN_BOSS, GALAXIAN_BOSS);
        BONUS_TO_POINTS_MAP.put(BonusNuggets.BELL, BELL);
        BONUS_TO_POINTS_MAP.put(BonusNuggets.KEY, KEY);
    }
}
