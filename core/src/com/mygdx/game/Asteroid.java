package com.mygdx.game;


import java.util.Random;

/**
 * Created by Tari on 2016-11-19.
 */
public class Asteroid extends Figure{

    private boolean isHit = false;
    private float rotationNumber;

    public Asteroid(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);
        rotationNumber = randomRotation();
        setRotation(rotationNumber);
        getSprite().setOriginCenter();
    }

    public void updatePositionFromSpeed(){
        super.updatePositionFromSpeed();
        wrapAround();
        setRotation(getRotation() + rotationNumber);

    }

    /*wraparound gives the asteroid the illusion of wrapping around the screen,
    where it reverts the x and y-values*/
    private void wrapAround() {
        if (getX() < -100) {
            setX(1301);
        }
        if (getX() > 1301) {
            setX(-100);
        }
        if (getY() < -100) {
            setY(601);
        }
        if (getY() > 601) {
            setY(-100);
        }
    }

    public boolean isHit(){
        return isHit;
    }

    public void hit(){
        isHit = true;
    }

    public float randomRotation(){
        float range = (1.5f - 0.5f) + 0.5f;
        int negative = (int)(Math.random()* (3 - 1))+1;
        float rotation = (float)(Math.random() * range) + 0.5f;
        if (negative == 1){
            rotation = -rotation;
        }
        return rotation;
    }

}