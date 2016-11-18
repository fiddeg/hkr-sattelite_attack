package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Fidde on 2016-11-10.
 */
public class Shield extends Figure{
    private float timerForDisable;

    public Shield(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);
        getSprite().setOriginCenter();
        timerForDisable = 0;
    }

    public void updatePositionFromSpeed(){
        super.updatePositionFromSpeed();
    }

    public void updatePositionFromSpaceship(float x, float y, float deltaTime){
        if (timerForDisable > 0){
            setX(Gdx.graphics.getWidth()+100);
            setY(Gdx.graphics.getHeight()+100);
            timerForDisable -= deltaTime;
        } else {
            setX(x-10);
            setY(y-10);
        }
    }

    public void isHit(){
        timerForDisable = 4;
    }

}
