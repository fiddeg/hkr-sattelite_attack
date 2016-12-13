package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Created by Nikolaj on 2016-12-05.
 */
public class Explosion{


    private Texture explosionImg;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;
    private SpriteBatch batch;
    private boolean hasStarted = false;
    private float x, y;

    public Explosion(float x, float y, float width, float height){
       this.x = x-width/2;
        this.y = y-height/2;
        int FRAME_COLS = 8;
        int FRAME_ROWS = 6;
        hasStarted = true;
        batch = new SpriteBatch();
        explosionImg = new Texture("explosion1.png");
        TextureRegion[][] tmpFrames = new TextureRegion(explosionImg).split(
                explosionImg.getWidth()/FRAME_COLS,
                explosionImg.getHeight()/FRAME_ROWS);
        animationFrames = new TextureRegion[FRAME_COLS*FRAME_ROWS];
        int index = 0;
        for(int i = 0; i < FRAME_ROWS; i++){
            for(int j = 0; j < FRAME_COLS; j++){
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation(0.01f, animationFrames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }
    public Explosion(float x, float y, float width, float height, boolean giant){
        this.x = x-width/2;
        this.y = y-height/2;
        int FRAME_COLS = 2;
        int FRAME_ROWS = 2;
        this.x = x;
        this.y = y;
        hasStarted = true;
        batch = new SpriteBatch();
        explosionImg = new Texture("GiantExplosion.png");
        TextureRegion[][] tmpFrames = new TextureRegion(explosionImg).split(
                explosionImg.getWidth()/FRAME_COLS,
                explosionImg.getHeight()/FRAME_ROWS);
        animationFrames = new TextureRegion[FRAME_COLS*FRAME_ROWS];
        int index = 0;
        for(int i = 0; i < FRAME_ROWS; i++){
            for(int j = 0; j < FRAME_COLS; j++){
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation(0.01f, animationFrames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime += elapsedTime;
    }

    public void setElapsedTimeToZero() {
        this.elapsedTime = 0;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}



