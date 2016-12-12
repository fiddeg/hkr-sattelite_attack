package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Fidde on 2016-12-11.
 */
public class PowerUp{
    private int typePowerUp;
    private Sprite sprite;
    private boolean remove;

    public PowerUp(float x, float y){
        remove = false;
        typePowerUp = (int)(Math.random() * 2)+1;
        if (typePowerUp == 1){
            sprite = new Sprite(new Texture("powerup_bolt.png"));
        }
        else if (typePowerUp == 2){
            sprite = new Sprite(new Texture("powerup_star.png"));
        }
        sprite.setY(y-10);
        sprite.setX(x-10);
        sprite.setSize(20,20);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public int getTypePowerUp(){
        return typePowerUp;
    }

    public static boolean shouldSpawn(){
        int number = (int)(Math.random() * 10);
        System.out.println(number);
        if (number == 1){
            return true;
        }
        return false;
    }

    public void hit(){
        remove = true;
    }

    public boolean isHit(){
        return remove;
    }

    public Rectangle getCollisionRectangle(){
        return new Rectangle(
                getSprite().getX(),
                getSprite().getY(),
                getSprite().getWidth(),
                getSprite().getHeight());
    }

    public boolean collidesWith(Rectangle otherRect){
        return getCollisionRectangle().overlaps(otherRect);
    }
}
