package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

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

    public Rectangle getBounds(){    return new Rectangle(getX()+100, getY()+100, getWidth()+50, getHeight()+50);}

    public void setRotation(float degree){
        getSprite().setRotation(degree);
    }

    public void goUp(){
        updateImage("SpaceshipBoost.png");
        setTranslateY(3);
    }

    public void goDown(){
        updateImage("SpaceshipBoost.png");
        setTranslateY(-3);
    }

    public void goLeft(){
        updateImage("SpaceshipBoost.png");
        setTranslateX(-3);
    }

    public void goRight(){
        updateImage("SpaceshipBoost.png");
        setTranslateX(3);
    }

    public void stop(){
        setSpeedX(0);
        setSpeedY(0);
    }
}
