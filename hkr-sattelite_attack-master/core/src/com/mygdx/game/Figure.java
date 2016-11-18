package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Fidde on 2016-11-08.
 */
public class Figure {
    private int speedX = 0;
    private int speedY = 0;
    private Sprite sprite;

    public Figure(String textureFileName, float x, float y, int sizeX, int sizeY){
        sprite = new Sprite(new Texture(textureFileName));
        sprite.setSize(sizeX, sizeY);
        sprite.setX(x);
        sprite.setY(y);
    }

    public void updateImage(String filename){
        Texture texture = new Texture(filename);
        getSprite().setTexture(texture);
    }

    public float getWidth(){
        return getSprite().getWidth();
    }

    public float getHeight(){
        return getSprite().getHeight();
    }

    public void setTranslateX(float x){
        sprite.translateX(x);
    }

    public void setTranslateY(float y){
        sprite.translateY(y);
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

    public void setSpeedX(int xSpeed) {
        this.speedX = xSpeed;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int ySpeed) {
        this.speedY = ySpeed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void updatePositionFromSpeed(){
        if (getSpeedX()==0 && getSpeedY()==0)
            return;
        setX(getX()+getSpeedX());
        setY(getY()+getSpeedY());

    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void bounceAtEdge(){
        if ((getX()> Gdx.graphics.getWidth()-sprite.getWidth()) || (getX()<0))
            setSpeedX(-getSpeedX());
        if ((getY()>Gdx.graphics.getHeight()-sprite.getHeight()) || (getY()<0))
            setSpeedY(-getSpeedY());
    }

    public void stopAtEdge(){
        if (getX()>Gdx.graphics.getWidth()-sprite.getWidth())
            setX(Gdx.graphics.getWidth()-sprite.getWidth());
        if (getX()<0)
            setX(0);
        if (getY()>Gdx.graphics.getHeight()-sprite.getHeight())
            setY(Gdx.graphics.getHeight()-sprite.getHeight());
        if (getY()<0)
            setY(0);
    }

}
