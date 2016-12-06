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

    Texture explosionImg;
    TextureRegion[] animationFrames;
    Animation animation;
    public float elapsedTime;
    SpriteBatch batch;
    private boolean hasStarted = false;

    public Explosion(){
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

    public void addExplosion(float deltaTime, float x, float y){
        elapsedTime += deltaTime;
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsedTime), x-20 , y-20);
        batch.end();

    }
}

