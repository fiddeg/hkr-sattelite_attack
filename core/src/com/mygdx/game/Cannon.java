package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Nikolaj on 2016-11-18.
 */
public class Cannon extends Figure{

    public Cannon (String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);

        getSprite().setOrigin(5, 6);
    }

    public void updatePositionFromSpaceship(float x, float y){
        setX(x+15);
        setY(y+13);
    }

    public void setRotation(float degree){
        getSprite().setRotation(degree);
    }

    public void rotateRight(){
        if (getRotation() > -135){
            setRotation(getRotation()-45);
        } else {
            setRotation(180);
        }
    }

    public void rotateLeft(){
        if (getRotation() < 180){
            setRotation(getRotation()+45);
        } else {
            setRotation(-135);
        }
    }

}



