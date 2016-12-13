package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Fidde on 2016-11-08.
 */
public class Spaceship extends Figure{
    private float powerUpSpeed;
    private boolean shieldDown;

    public Spaceship(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);
        shieldDown = false;
        powerUpSpeed = 0;
        getSprite().setOriginCenter();
    }

    public void updatePositionFromSpeed(float delta){
        super.updatePositionFromSpeed();
        if (powerUpSpeed > 0){
            powerUpSpeed -= delta;
        }
        stopAtEdge();
    }

    public boolean isShieldDown() {
        return shieldDown;
    }



    public void setShieldDown(boolean shieldDown) {
        this.shieldDown = shieldDown;
    }

    public void powerUpSpeed(){
        powerUpSpeed = 5;
    }

    public Rectangle getBounds(){
        return new Rectangle(getX()-400, getY()-400, 800, 800);
    }

    public void setRotation(float degree){
        getSprite().setRotation(degree);
    }

    public void goUp(){
        updateImage("SpaceshipBoost.png");
        if (powerUpSpeed > 0){
            setTranslateY(5);
        } else {
            setTranslateY(3);
        }

    }

    public void goDown(){
        updateImage("SpaceshipBoost.png");
        if (powerUpSpeed > 0){
            setTranslateY(-5);
        } else {
            setTranslateY(-3);
        }
    }

    public void goLeft(){
        updateImage("SpaceshipBoost.png");
        if (powerUpSpeed > 0){
            setTranslateX(-5);
        } else {
            setTranslateX(-3);
        }
    }

    public void goRight(){
        updateImage("SpaceshipBoost.png");
        if (powerUpSpeed > 0){
            setTranslateX(5);
        } else {
            setTranslateX(3);
        }
    }

    public void stop(){
        setSpeedX(0);
        setSpeedY(0);
    }
}
