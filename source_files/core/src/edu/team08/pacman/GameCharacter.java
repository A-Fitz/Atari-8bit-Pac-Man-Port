package edu.team08.pacman;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameCharacter {
    private Sprite sprite;
    private boolean isControlledByPlayer;
    private String name;
    private int xPosition;
    private int yPosition;
    //var for image

    public GameCharacter(String name, int x, int y) {
        this.name = name;
        this.isControlledByPlayer = false;
        this.sprite = new Sprite();
        this.xPosition = x;
        this.yPosition = y;
        //create image
    }

    public int[] getPosition(){
        return new int[]{this.xPosition, this.yPosition};
    }

    public String getName(){
        return this.name;
    }

    public void moveUp(){
        this.yPosition++;
    }

    public void moveDown(){
        this.yPosition--;
    }

    public void moveRight(){
        this.xPosition++;
    }

    public void moveLeft(){
        this.xPosition--;
    }

}
