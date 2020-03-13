package edu.team08.pacman;

import com.badlogic.gdx.math.Rectangle;
import edu.team08.pacman.constants.DisplayConstants;

public class Util
{
    /**
     * Converts a Rectangle object's position and scale from the TiledMap values to our game values.
     *
     * @param rectangle A given rectangle, some TiledMap object.
     */
    public static void correctRectangle(Rectangle rectangle)
    {
        rectangle.x = (rectangle.x / DisplayConstants.ASSET_SIZE) + (rectangle.width / DisplayConstants.ASSET_SIZE) / 2;
        rectangle.y = (rectangle.y / DisplayConstants.ASSET_SIZE) + (rectangle.height / DisplayConstants.ASSET_SIZE) / 2;
        rectangle.width = rectangle.width / (2 * DisplayConstants.ASSET_SIZE);
        rectangle.height = rectangle.height / (2 * DisplayConstants.ASSET_SIZE);
    }
}
