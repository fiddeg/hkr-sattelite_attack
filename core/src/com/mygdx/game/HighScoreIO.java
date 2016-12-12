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
    private SpriteBatch batch;
    private BitmapFont font;
    private ArrayList<Integer> highScoresList;
    private int[] highScores;
    private String[] names;

    private int tempScore;

    public HighScoreIO(){
        highScores = new int[MAX_SCORES];
        init();
        //names = new String[MAX_SCORES];
    }

    public void init(){
        for (int i = 0; i < MAX_SCORES; i++){
            highScores[i] = 0;
        }
    }

    public ArrayList<Integer> getHighScoresList() {
        return highScoresList;
    }

    public int[] getHighScores() {
        return highScores;
    }

    public void setHighScores(int[] highScores) {
        this.highScores = highScores;
    }

    public String[] getNames() {
        return names;
    }

    public int getTempScore() {
        return tempScore;
    }

    public void setTempScore(int tempScore) {
        this.tempScore = tempScore;
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

        /**
        for (int i = 0; i < MAX_SCORES-1; i++){
            int index = i;
            int j;
            for (j = i+1; j < MAX_SCORES; j++){
                if (highScores[j] < highScores[i]){
                    index = j;
                }
                int smallerNumber = highScores[index];
                highScores[index] = highScores[i];
                highScores[i] = smallerNumber;
            }
        }
         **/
    }


}

