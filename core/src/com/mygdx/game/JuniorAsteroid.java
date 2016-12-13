package com.mygdx.game;

/**
 * Created by Tari on 2016-11-19.
 */
public class JuniorAsteroid extends Asteroid {


    private float rotationNumber;


    public JuniorAsteroid(String textureFileName, float x, float y, int sizeX, int sizeY) {
        super(textureFileName, x, y, sizeX, sizeY);
        rotationNumber = randomRotation();
        setRotation(rotationNumber);
        getSprite().setOriginCenter();
    }
}