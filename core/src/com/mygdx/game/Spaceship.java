package com.mygdx.game;

/**
 * Created by Fidde on 2016-11-08.
 */
public class Spaceship extends Figure{
    public Spaceship(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);

        getSprite().setOriginCenter();
    }

    public void updatePositionFromSpeed(){
        super.updatePositionFromSpeed();
        stopAtEdge();
    }

    public void setRotation(float degree){
        getSprite().setRotation(degree);
    }

    public void goUp(){
        updateImage("SpaceshipBoost.png");
        setTranslateY(3);
        //getSprite().setRotation(0);
    }

    public void goDown(){
        updateImage("SpaceshipBoost.png");
        setTranslateY(-3);
        //getSprite().setRotation(180);
    }

    public void goLeft(){
        updateImage("SpaceshipBoost.png");
        setTranslateX(-3);
        //getSprite().setRotation(90);
    }

    public void goRight(){
        updateImage("SpaceshipBoost.png");
        setTranslateX(3);
        //getSprite().setRotation(-90);
    }

    public void stop(){
        setSpeedX(0);
        setSpeedY(0);
    }
}
