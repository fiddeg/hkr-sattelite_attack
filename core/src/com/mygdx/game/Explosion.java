package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Created by Nikolaj on 2016-12-05.
 */
public class Explosion extends Figure{
    Texture explotionImg;
    TextureRegion[] animationFrames;
    Animation animation;
    float elapsedTime;
    SpriteBatch batch;
    // elapsedTime = Gdx.graphics.getDeltaTime();
    public Explosion(String textureFileName, float x, float y, int sizeX, int sizeY){
        //First call the parent class constructor (MovingFigure)
        super(textureFileName, x, y, sizeX, sizeY);
        batch = new SpriteBatch();
        explotionImg = new Texture("explosion.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(explotionImg, sizeX, sizeY);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                animationFrames[index] = tmpFrames[j][i];
                index++;
            }
        }
        animation = new Animation(1f/100f, animationFrames);
    }
    public void addExplosion(float x, float y){
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsedTime), x , y);
        batch.end();
    }
}

