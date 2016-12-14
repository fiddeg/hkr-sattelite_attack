package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fidde on 2016-12-09.
 */
public class HighScoreIO implements Serializable{
    private static final long serialVersionUID = 1;
    private final int MAX_SCORES = 10;
    private int[] highScores;

    public HighScoreIO(){
        highScores = new int[MAX_SCORES];
        init();
    }

    public void init(){
        for (int i = 0; i < MAX_SCORES; i++){
            highScores[i] = 0;
        }
    }

    public int[] getHighScores() {
        return highScores;
    }

    public void setHighScores(int[] highScores) {
        this.highScores = highScores;
    }

    public boolean isHighScore(int score){
        return score > highScores[0];
    }

    public void addHighScore(int newScore){
        if (isHighScore(newScore)){
            highScores[0] = newScore;
            sortHighScore();
        }
    }

    public void sortHighScore(){
        Arrays.sort(highScores);
    }


}

