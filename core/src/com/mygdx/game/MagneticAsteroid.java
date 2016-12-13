package com.mygdx.game;


/**
 * Created by Tari on 2016-11-19.
 */
public class MagneticAsteroid extends Asteroid {

    private float rotationNumber;

    public MagneticAsteroid(String textureFileName, float x, float y, int sizeX, int sizeY) {
        super(textureFileName, x, y, sizeX, sizeY);
        rotationNumber = randomRotation();
        setRotation(rotationNumber);
        getSprite().setOriginCenter();
    }

}