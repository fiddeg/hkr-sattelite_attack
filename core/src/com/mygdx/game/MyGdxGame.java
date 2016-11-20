package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Spaceship spaceship;
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private ArrayList<Bullet> tempDispose = new ArrayList<Bullet>();
	private Shield shield;
	private Cannon cannon;
	private Asteroid asteroid;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("spaceBack.jpg");
		createSpaceShip();
		createShield();
		//Nikolaj cannon
		createCannon();
		asteroid = new Asteroid("asteroid.png", 100, 200, 60, 60);

	}

	private void createSpaceShip(){
		spaceship = new Spaceship("Spaceship.png", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 40, 40);
	}

	private void createShield(){
		shield = new Shield("shield.png", spaceship.getX()-10, spaceship.getY()-10, 60, 60);
	}

    //Nikolaj cannon - lägg in bild för cannon ist för shield
	private void createCannon(){
		cannon = new Cannon("cannon.png", spaceship.getX()-15, spaceship.getY()-15, 10, 22);
	}

	public void checkInput() {

        /**
         * If-statement för att röra skeppet i olika riktningar.
         * / Fredrik
         */
		if (Gdx.input.isKeyPressed(Input.Keys.W)){
			spaceship.goUp();
			spaceship.setRotation(0);
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				spaceship.goLeft();
				spaceship.setRotation(45);
			}
			else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				spaceship.goRight();
				spaceship.setRotation(-45);
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.S)){
			spaceship.goDown();
			spaceship.setRotation(180);
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				spaceship.goLeft();
				spaceship.setRotation(135);
			}
			else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				spaceship.goRight();
				spaceship.setRotation(-135);
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.A)){
			spaceship.goLeft();
			spaceship.setRotation(90);
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				spaceship.goUp();
				spaceship.setRotation(45);
			}
			else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				spaceship.goDown();
				spaceship.setRotation(-45);
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.D)){
			spaceship.goRight();
			spaceship.setRotation(-90);
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				spaceship.goUp();
				spaceship.setRotation(-45);
			}
			else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				spaceship.goDown();
				spaceship.setRotation(45);
			}
		}

		//Nikolaj cannon
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)){
            cannon.rotateLeft();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            cannon.rotateRight();
        }

        /**
         * För att skjuta skott med Spacebar.
         * / Fredrik
         */
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            createBulletCannon();
        }

	}

	public void createBulletCannon(){
        if (cannon.getRotation() == 0){ //Done
            bulletList.add(new Bullet(cannon.getX()+2 , cannon.getY()+23, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        } else if (cannon.getRotation() == 90){ //Done
            bulletList.add(new Bullet(cannon.getX()-19, cannon.getY()-1, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        } else if (cannon.getRotation() == -90){ //Done
            bulletList.add(new Bullet(cannon.getX()+25, cannon.getY()-1, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        } else if (cannon.getRotation() == 45){ //Done
            bulletList.add(new Bullet(cannon.getX()-13, cannon.getY()+15, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        } else if (cannon.getRotation() == -45){ //Done
            bulletList.add(new Bullet(cannon.getX()+18, cannon.getY()+14, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        } else if (cannon.getRotation() == 135){ //Done
            bulletList.add(new Bullet(cannon.getX()-13, cannon.getY()-19, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        } else if (cannon.getRotation() == -135){
            bulletList.add(new Bullet(cannon.getX()+20, cannon.getY()-18, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        } else if (cannon.getRotation() == 180){ //Done
            bulletList.add(new Bullet(cannon.getX()+4, cannon.getY()-25, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
        }
	}


	@Override
	public void render() {

		checkInput();
		spaceship.updatePositionFromSpeed();
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());
		asteroid.updatePositionFromSpeed();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		spaceship.draw(batch);
		shield.draw(batch);
		cannon.draw(batch);
		asteroid.draw(batch);

		if (spaceship.getSpeedY() == 0 && spaceship.getSpeedX() == 0){
			spaceship.updateImage("Spaceship.png");
		}
		else {
			spaceship.updateImage("SpaceshipBoost.png");
		}

		for (Bullet bullet : bulletList) {
			bullet.draw(batch);
		}



		batch.end();

		for (Bullet bullet : bulletList){
			bullet.update(Gdx.graphics.getDeltaTime());
			if (bullet.isTimeout()){
				tempDispose.add(bullet);
			}
		}

		if (tempDispose.size() != 0){
			bulletList.remove(tempDispose.get(0));
			tempDispose.remove(0);
		}


		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
			shield.isHit();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}


