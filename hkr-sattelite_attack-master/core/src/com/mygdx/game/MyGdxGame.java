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
	private float timer, deltaTimer;
	private Shield shield;
//Nikolaj cannon
int rotationCounter = 0;
	private Cannon cannon;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("spaceBack.jpg");
		createSpaceShip();
		createShield();
		//Nikolaj cannon
		createCannon();
	}

	private void createSpaceShip(){
		spaceship = new Spaceship("Spaceship.png", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 40, 40);
	}

	private void createShield(){
		shield = new Shield("shield.png", spaceship.getX()-10, spaceship.getY()-10, 60, 60);
	}

//Nikolaj cannon - lägg in bild för cannon ist för shield
	private void createCannon(){
		cannon = new Cannon("cannon.png", spaceship.getX()-20, spaceship.getY()-20, 20, 40);
	}

	public void checkInput() {
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
		else if (Gdx.input.isKeyPressed(Input.Keys.O)) {
			rotationCounter = rotationCounter + 45;
			if(rotationCounter < 0){
				rotationCounter = 315;
			}
			cannon.setRotation(rotationCounter);
		}
		//Nikolaj cannon
		else if (Gdx.input.isKeyPressed(Input.Keys.P)) {
			rotationCounter = rotationCounter - 45;
			if(rotationCounter > 360){
				rotationCounter = 45;
			}
			cannon.setRotation(rotationCounter);
		}

	}

	public void createBullet(){
		if (spaceship.getRotation() == 0){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()/2)-2, spaceship.getY()+(spaceship.getHeight())-2, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (spaceship.getRotation() == 90){
			bulletList.add(new Bullet(spaceship.getX()-5, spaceship.getY()+(spaceship.getHeight()/2)-7, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (spaceship.getRotation() == -90){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()+4), spaceship.getY()+(spaceship.getHeight()/2)-7, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (spaceship.getRotation() == 45){
			bulletList.add(new Bullet(spaceship.getX()-3, spaceship.getY()+34, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (spaceship.getRotation() == -45){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()-2), spaceship.getY()+(spaceship.getHeight()-6), spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (spaceship.getRotation() == 135){
			bulletList.add(new Bullet(spaceship.getX()+1, spaceship.getY()-5, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (spaceship.getRotation() == -135){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()-4), spaceship.getY()-6, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (spaceship.getRotation() == 180){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()/2)-2, spaceship.getY()-14, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		/*

	public void createBullet(){
		if (cannon.getRotation() == 0){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()/2)-2, spaceship.getY()+(spaceship.getHeight())-2, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		}
		else if (cannon.getRotation() == 90){
			bulletList.add(new Bullet(spaceship.getX()-5, spaceship.getY()+(spaceship.getHeight()/2)-7, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		}
		else if (cannon.getRotation() == -90){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()+4), spaceship.getY()+(spaceship.getHeight()/2)-7, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (cannon.getRotation() == 45){
			bulletList.add(new Bullet(spaceship.getX()-3, spaceship.getY()+34, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (cannon.getRotation() == -45){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()-2), spaceship.getY()+(spaceship.getHeight()-6), spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (cannon.getRotation() == 135){
			bulletList.add(new Bullet(spaceship.getX()+1, spaceship.getY()-5, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (cannon.getRotation() == -135){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()-4), spaceship.getY()-6, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
		}
		else if (cannon.getRotation() == 180){
			bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()/2)-2, spaceship.getY()-14, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		}
		*/

		//bulletList.add(new Bullet(spaceship.getX()+(spaceship.getWidth()/2)-2, spaceship.getY()+(spaceship.getHeight()/2)-2, spaceship.getRotation(), (spaceship.getRotation() * (float)Math.PI / -180)));
	}


	@Override
	public void render () {

		checkInput();
		spaceship.updatePositionFromSpeed();
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		spaceship.draw(batch);
		shield.draw(batch);
		cannon.draw(batch);

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

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			createBullet();
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


