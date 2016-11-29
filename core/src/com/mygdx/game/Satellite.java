package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Fidde on 2016-11-25.
 */
public class Satellite extends Figure{
    private boolean isHit = false;
    Bullet bullet;
    private float time;
    private int rangeToShoot;
    private int timeToShoot;
    private boolean haveShot = false;

    public Satellite(String textureFileName, float x, float y, int sizeX, int sizeY){
        super(textureFileName, x, y, sizeX, sizeY);

        rangeToShoot = (7 - 5) + 1;
        timeToShoot = (int)(Math.random() * rangeToShoot) +4;

        getSprite().setOriginCenter();
        RandomDirection();
        time = 10;
    }

    public void updatePositionFromSpeed(float deltaTime) {
        super.updatePositionFromSpeed();
        time -= deltaTime;
    }

    public boolean isTimeout(){
        if (time < 0){
            return true;
        }
        return false;
    }

    public void RandomDirection(){

        if (getX() <= Gdx.graphics.getWidth()/2 && getY() <= Gdx.graphics.getHeight()/2){
            float range = (2 - 0.5f) + 1;
            float xDirection = (float)(Math.random() * range) + 0.5f;
            float yDirection = (float)(Math.random() * range) + 0.5f;

            if (xDirection == yDirection){
                yDirection = (float)(Math.random() * range) + 0.5f;
                setSpeedX(xDirection);
                setSpeedY(yDirection);

            } else {
                setSpeedX(xDirection);
                setSpeedY(yDirection);
            }

        } else if (getX() > Gdx.graphics.getWidth()/2 && getY() > Gdx.graphics.getHeight()/2){
            float range = (-0.5f + 3) - 1;
            float xDirection = (float)(Math.random() * range) -3;
            float yDirection = (float)(Math.random() * range) -3;

            if (xDirection == yDirection){
                yDirection = (float)(Math.random() * range) -3;
                setSpeedX(xDirection);
                setSpeedY(yDirection);

            } else {
                setSpeedX(xDirection);
                setSpeedY(yDirection);
            }


        } else if (getX() <= Gdx.graphics.getWidth()/2 && getY() > Gdx.graphics.getHeight()/2){
            float rangeX = (2 - 0.5f) + 1;
            float rangeY = (-0.5f + 3) - 1;
            float xDirection = (float)(Math.random() * rangeX) + 0.5f;
            float yDirection = (float)(Math.random() * rangeY) - 3;

            if (xDirection == yDirection){
                yDirection = (float)(Math.random() * rangeY) -3;
                setSpeedX(xDirection);
                setSpeedY(yDirection);

            } else {
                setSpeedX(xDirection);
                setSpeedY(yDirection);
            }


        } else if (getX() > Gdx.graphics.getWidth()/2 && getY() <= Gdx.graphics.getHeight()/2){
            float rangeY = (2 - 0.5f) + 1;
            float rangeX = (-0.5f + 3) - 1;
            float xDirection = (float)(Math.random() * rangeX) - 3;
            float yDirection = (float)(Math.random() * rangeY) + 0.5f;

            if (xDirection == yDirection){
                yDirection = (float)(Math.random() * rangeY) + 0.5f;
                setSpeedX(xDirection);
                setSpeedY(yDirection);

            } else {
                setSpeedX(xDirection);
                setSpeedY(yDirection);
            }
        }
    }

    public boolean shoot(){

        if (timeToShoot == (int)time && !haveShot){
            haveShot = true;
            return true;
        }
        return false;
    }

    public boolean isHit(){
        return isHit;
    }

    public void hit(){
        isHit = true;
    }

}
