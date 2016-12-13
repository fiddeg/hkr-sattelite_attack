package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Created by Nikolaj on 2016-12-05.
 */
public class Explosion{
    private static final int FRAME_COLS = 8;
    private static final int FRAME_ROWS = 6;

    private Texture explosionImg;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;
    private SpriteBatch batch;
    private boolean hasStarted = false;
    private float x, y;

    public Explosion(float x, float y){
        this.x = x-20;
        this.y = y-20;
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



