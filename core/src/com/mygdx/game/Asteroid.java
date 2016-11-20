package com.mygdx.game;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Tari on 2016-11-19.
 */
public class Asteroid extends Figure{

    int timeCounter = 0;

    public Asteroid(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);
    }

    public void updatePositionFromSpeed(){
        super.updatePositionFromSpeed();
        timeCounter++;

        if(timeCounter >=30) {
            rngDirection();
            timeCounter = 0;
        }

    }
        //A random number generator to handle the movement of the asteroid//
    private void rngDirection() {
        Random direction = new Random();
        float rngX = direction.nextFloat() -0.5f;
        float rngY = direction.nextFloat() -0.5f;

        rngX = Float.parseFloat(String.format(java.util.Locale.US, "%.1f", rngX));
        rngY = Float.parseFloat(String.format(java.util.Locale.US, "%.1f", rngY));


        while(rngX == 0) {
            rngX = direction.nextFloat() -0.5f;
        }

        while(rngY == 0) {
            rngY = direction.nextFloat() -0.5f;
        }

        setSpeedX(rngX);
        setSpeedY(rngY);
    }

}