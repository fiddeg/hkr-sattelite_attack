package com.mygdx.game;

/**
 * Created by User on 13/12/2016.
 */
public class GiantMagnetic extends Asteroid {

    private float rotationNumber;

    public GiantMagnetic(String textureFileName, float x, float y, int sizeX, int sizeY) {
        super(textureFileName, x, y, sizeX, sizeY);
        rotationNumber = randomRotation();
        setRotation(rotationNumber);
        getSprite().setOriginCenter();
    }
}

