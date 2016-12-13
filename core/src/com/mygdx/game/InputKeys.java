package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Fidde on 2016-12-11.
 */
public class InputKeys {

    /**
    private static MyGdxGame main = new MyGdxGame().getInstance();

    public void checkInput() {

        /**
         * If-statement för att röra skeppet i olika riktningar.
         * / Fredrik
         */
    /**
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)){
            main.getSpaceship().goUp();
            main.getSpaceship().setRotation(0);
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
                main.getSpaceship().goLeft();
                main.getSpaceship().setRotation(45);
            }
            else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
                main.getSpaceship().goRight();
                main.getSpaceship().setRotation(-45);
            }
        }
        else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)){
            main.getSpaceship().goDown();
            main.getSpaceship().setRotation(180);
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
                main.getSpaceship().goLeft();
                main.getSpaceship().setRotation(135);
            }
            else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
                main.getSpaceship().goRight();
                main.getSpaceship().setRotation(-135);
            }
        }
        else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)){
            main.getSpaceship().goLeft();
            main.getSpaceship().setRotation(90);
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
                main.getSpaceship().goUp();
                main.getSpaceship().setRotation(45);
            }
            else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
                main.getSpaceship().goDown();
                main.getSpaceship().setRotation(-45);
            }
        }
        else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)){
            main.getSpaceship().goRight();
            main.getSpaceship().setRotation(-90);
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
                main.getSpaceship().goUp();
                main.getSpaceship().setRotation(-45);
            }
            else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
                main.getSpaceship().goDown();
                main.getSpaceship().setRotation(45);
            }
        }

        //Nikolaj cannon
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.O)){
            main.getCannon().rotateLeft();
        }
        else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)){
            main.getCannon().rotateRight();
        }

        /**
         * För att skjuta skott med Spacebar.
         * / Fredrik
         */
    /**
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            main.createBulletCannon();
            main.getPew().play();
        }
    }
     **/
}
