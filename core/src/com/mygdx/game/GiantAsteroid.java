package com.mygdx.game;

/**
 * Created by Nikolaj on 2016-12-10.
 */
public class GiantAsteroid extends Asteroid {

    private float rotationNumber;

    public GiantAsteroid(String textureFileName, float x, float y, int sizeX, int sizeY) {
        super(textureFileName, x, y, sizeX, sizeY);
        rotationNumber = randomRotation();
        setRotation(rotationNumber);
        getSprite().setOriginCenter();
    }
}