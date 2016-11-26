package com.mygdx.game;


import java.util.Random;

/**
 * Created by Tari on 2016-11-19.
 */
public class MagneticAsteroid extends Figure {

    private int timeCounter = 0;
    private boolean isHit = false;

    //timecounter makes it update the position every 60 frames instead of each frame//
    public MagneticAsteroid(String textureFileName, float x, float y, int sizeX, int sizeY, float speedX, float speedY) {
        super(textureFileName, x, y, sizeX, sizeY);

        setSpeedX(speedX);
        setSpeedY(speedY);
    }
    public void updatePositionFromSpeed(){
        super.updatePositionFromSpeed();
        bounceAtEdge();
        timeCounter++;

        if(timeCounter >=60) {
            rngDirection();
            timeCounter = 0;
        }

    }

    //A random number generator to handle the movement of the asteroid in 8 directions//
    public void rngDirection() {

        Random direction = new Random();
        float rngX = (direction.nextFloat() - 0.5f) * 2;
        float rngY = (direction.nextFloat() - 0.5f) * 2;

        rngX = Float.parseFloat(String.format(java.util.Locale.US, "%.1f", rngX));
        rngY = Float.parseFloat(String.format(java.util.Locale.US, "%.1f", rngY));


        while (rngX == 0) {
            rngX = (direction.nextFloat() - 0.5f) * 2;
        }

        while (rngY == 0) {
            rngY = (direction.nextFloat() - 0.5f) * 2;
        }

        setSpeedX(rngX);
        setSpeedY(rngY);
    }

    public void rngDirectionAttack(float spaceShipX, float spaceShipY, float asteroidX, float asteroidY) {
        float rngX2 = asteroidX;
        float rngY2 = asteroidY;
        float magneticAsteroidX = rngX2;
        float magneticAsteroidY = rngY2;
        float dx = spaceShipX - rngX2;
        float dy = spaceShipY - rngY2;
        float angle = (float) Math.atan2( dy, dx );
        float speedX = (float) (5 * Math.cos( angle ));
        float speedY = (float) (5 * Math.sin( angle ));
        magneticAsteroidX += speedX;
        magneticAsteroidY += speedY;

        setSpeedX(magneticAsteroidX);
        setSpeedY(magneticAsteroidY);

    }


    public boolean isHit(){
        return isHit;
    }

    public void hit(){
        isHit = true;
    }

}