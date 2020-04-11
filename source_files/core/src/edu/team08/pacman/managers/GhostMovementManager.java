/***********************************************************************************************************************
 * Class:   GhostMovementManager
 *
 * Purpose: Chooses a direction for the ghost to move and sets a speed in the chosen direction.
 *
 * Author: Nate Braukhoff
 **********************************************************************************************************************/
package edu.team08.pacman.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.Random;


public class GhostMovementManager {
    private int previousDirection;

    public GhostMovementManager(){
        this.previousDirection = 3;
    }
    /**
     * Set a direction for the ghost to move
     * @param body
     */
    public void setDirections(Body body, int speed){
        switch(this.pickDirection()){
            case 1:
                this.moveWithYAxis(body, speed);
                break;
            case 2:
                this.moveWithYAxis(body, -speed);
                break;
            case 3:
                this.moveWithXAxis(body, speed);
                break;
            case 4:
                this.moveWithXAxis(body, -speed);
                break;
        }
    }

    /**
     * moves ghost along the y-axis. When speed > 0 the ghost moves up and when speed < 0 the ghost moves down
     * @param body
     * @param speed
     */
    private void moveWithYAxis(Body body, int speed){
        body.setLinearVelocity(0, MathUtils.lerp(body.getLinearVelocity().x, speed, 1));
        
    }

    /**
     * moves ghost along the x-axis. When speed > 0 the ghost moves to the right and when speed < 0 the ghost moves to
     * the left.
     * @param body ghost's body
     * @param speed speed of the ghost
     */
    private void moveWithXAxis(Body body, int speed){
        body.setLinearVelocity(MathUtils.lerp(body.getLinearVelocity().x, speed, 1), 0);
    }

    /**
     * Generate a random number between 1 and 4
     * @return returns the random number
     * resource: https://dzone.com/articles/random-number-generation-in-java
     */
    private int pickDirection(){
        Random random = new Random();
        int max = 4;
        int min = 1;
        int direction = random.nextInt((max - min) + 1) + min;

        while (direction == this.previousDirection)
            direction = random.nextInt((max - min) + 1) + min;

        this.previousDirection = direction;
        return direction;

    }



}
