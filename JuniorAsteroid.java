package com.mygdx.game;


import java.util.Random;

/**
 * Created by Tari on 2016-11-19.
 */
public class JuniorAsteroid extends Figure {

    private int timeCounter = 0;
    private boolean isHit = false;
    private float rotationNumber;

    //timecounter makes it update the position every 60 frames instead of each frame//
    public JuniorAsteroid(String textureFileName, float x, float y, int sizeX, int sizeY) {
        super(textureFileName, x, y, sizeX, sizeY);
       // rotationNumber = randomRotation();
        setRotation(rotationNumber);
        getSprite().setOriginCenter();
    }

    public void updatePositionFromSpeed() {
        super.updatePositionFromSpeed();
        wrapAround();
        setRotation(getRotation() + rotationNumber);
        timeCounter++;

        if (timeCounter >= 10) {
            rngDirection();
            timeCounter = 0;
        }
    }

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

    //A random number generator to handle the movement of the asteroid in 8 directions//
    private void rngDirection() {
        Random direction = new Random();
        float rngX = (direction.nextFloat() - 0.5f) * 5;
        float rngY = (direction.nextFloat() - 0.5f) * 5;

        rngX = Float.parseFloat(String.format(java.util.Locale.US, "%.1f", rngX));
        rngY = Float.parseFloat(String.format(java.util.Locale.US, "%.1f", rngY));


        while (rngX == 0) {
            rngX = (direction.nextFloat() - 0.5f) * 5;
        }

        while (rngY == 0) {
            rngY = (direction.nextFloat() - 0.5f) * 5;
        }

        setSpeedX(rngX);
        setSpeedY(rngY);

    }

    public boolean isHit() {
        return isHit;
    }

    public void hit() {
        isHit = true;
    }
}

       /**  public float randomRotation(){
         float range = (1.5f - 0.5f) + 0.5f;
         int negative = (int)(Math.random()* (3 - 1))+1;
         float rotation = (float)(Math.random() * range) + 0.5f;
         if (negative == 1){
         rotation = -rotation;
         }
         return rotation;

         }
        **/
