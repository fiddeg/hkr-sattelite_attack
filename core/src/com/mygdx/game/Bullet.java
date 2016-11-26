package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Fidde on 2016-11-08.
 */
public class Bullet {
    private Rectangle bullet;
    private int speedX, speedY;
    private Sprite sprite;
    private float a, time, angle, deltaTimer;
    private Texture picture;

    public Bullet(String texture,float positionX, float positionY, float angle, float direction){
        sprite = new Sprite(new Texture(texture));
        sprite.setSize(4, 14);
        sprite.setX(positionX);
        sprite.setY(positionY);
        getSprite().setRotation(angle);
        this.angle = direction;
        speedX = 350;
        speedY = 350;
        time = 3;
    }

    public float getRotation(){
        return sprite.getRotation();
    }

    public float getX() {
        return sprite.getX();
    }

    public void setX(float x) {
        sprite.setX(x);
    }

    public float getY() {
        return sprite.getY();
    }

    public void setY(float y) {
        sprite.setY(y);
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int Speed) {
        this.speedX = Speed;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int Speed) {
        this.speedY = Speed;
    }

    public Sprite getSprite() {
        return sprite;
    }


    public void updatePositionFromSpeed(){
        if (getRotation() >= 0)
            setX(getX()+getSpeedX());
        setY(getY()+getSpeedY());
    }

    public void update(float delta){
        //bullet.x += speedX * (float)Math.sin(a) * delta;
        //bullet.y += speedY * (float)Math.cos(a) * delta;
        sprite.setX(getX() + speedX * (float)Math.sin(angle) * delta);
        sprite.setY(getY() + speedY * (float)Math.cos(angle) * delta);
        time -= delta;
    }


    public void draw(SpriteBatch batch){
        //batch.draw(picture, bullet.x, bullet.y, 4, 14);
        sprite.draw(batch);
    }

    public void hit(){
        time = 0;
    }

    public boolean isTimeout(){
        if (time < 0){
            return true;
        }
        return false;

    }

    public Rectangle getCollisionRectangle(){
        return sprite.getBoundingRectangle();

    }
}
