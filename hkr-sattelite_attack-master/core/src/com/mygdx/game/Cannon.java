package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Nikolaj on 2016-11-18.
 */
public class Cannon extends Figure{

        public Cannon (String textureFileName, float x, float y, int sizeX, int sizeY){
            super(textureFileName, x, y, sizeX, sizeY);

            getSprite().setOriginCenter();
        }

    public void updatePositionFromSpeed(){
        super.updatePositionFromSpeed();
        stopAtEdge();
    }

    public void updatePositionFromSpaceship(float x, float y, float deltaTime){

            setX(x-10);
            setY(y-10);
        }


    public void setRotation(float degree){
            getSprite().setRotation(degree);
        }


        public void go360(){
            //lägg in bild med rotation för cannon istället för bild på spaceship
            //updateImage("SpaceshipBoost.png");
            setTranslateY(3);
            //getSprite().setRotation(0);
        }

        public void go180(){
            //lägg in bild med rotation för cannon istället för bild på spaceship
            //updateImage("SpaceshipBoost.png");
            setTranslateY(-3);
            //getSprite().setRotation(180);
        }

        public void go270(){
            //lägg in bild med rotation för cannon istället för bild på spaceship
            //updateImage("SpaceshipBoost.png");
            setTranslateX(-3);
            //getSprite().setRotation(90);
        }

        public void go90(){
            //lägg in bild med rotation för cannon istället för bild på spaceship
            //updateImage("SpaceshipBoost.png");
            setTranslateX(3);
            //getSprite().setRotation(-90);
        }

        public void stop(){
            setSpeedX(0);
            setSpeedY(0);
        }
    }



