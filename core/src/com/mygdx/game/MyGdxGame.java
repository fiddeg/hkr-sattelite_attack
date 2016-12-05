package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

	private enum GameState{
		TITLE_SCREEN,
		LEVEL_1,
		LEVEL_2,
		LEVEL_3,
		LEVEL_COMPLETE_1,
		GAME_OVER
	}

	private GameState gameState = GameState.TITLE_SCREEN;
	private SpriteBatch batch;
	private Texture img;
	private Spaceship spaceship;
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private ArrayList<Bullet> tempDispose = new ArrayList<Bullet>();
	private Shield shield;
	private Cannon cannon;
	private Asteroid asteroid;
	private MagneticAsteroid magneticAsteroid;
    private Explosion explosionImage;
    private ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();
	private ArrayList<Asteroid> disposeAsteroidList = new ArrayList<Asteroid>();
    private ArrayList<MagneticAsteroid> magneticAsteroidList = new ArrayList<MagneticAsteroid>();
    private ArrayList<MagneticAsteroid> disposeMagneticAsteroidList = new ArrayList<MagneticAsteroid>();
	private ArrayList<Satellite> satelliteList = new ArrayList<Satellite>();
	private Sound pew;
	private Sound explosion;
	private Sound satellitePew;
	private Music backMusic;
    private int spawnAsteroidTimer;
    private int timer;
	private BitmapFont font;
	private Music titleMusic;
	private Sprite menuSprite;
	private Texture menuTexture;
	private int menuPos = 0;


	private int countAsteroid = 0;
    private int countMagneticAsteroid = 0;
	private int score = 0;
    float elapsedTime;


	@Override
	public void create () {

		batch = new SpriteBatch();
		img = new Texture("spaceBack.jpg");
		pew = Gdx.audio.newSound(Gdx.files.internal("sounds/pew.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explode.wav"));
		satellitePew = Gdx.audio.newSound(Gdx.files.internal("sounds/satellitepew.wav"));
		backMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backmusic.mp3"));
		titleMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/titlemusic.mp3"));
		font = new BitmapFont();
		createObjects();
		spawnAsteroid();
        spawnMagneticAsteroid();

        explosionImage = new Explosion("shield.png", 0, 0, 100, 100);


	}

	/**
	 * La alla create-metoder till en gemensam metod istället.
	 * /Fredrik
	 */
	private void createObjects(){
		spaceship = new Spaceship("Spaceship.png", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 40, 40);

		shield = new Shield("shield.png", spaceship.getX()-10, spaceship.getY()-10, 60, 60);

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
			pew.play();
        }

		if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)){
			if (satelliteList.size() == 0){
				randomSatellite();
			}

		}



	}

	public void createBulletCannon(){
		if (cannon.getRotation() == 0){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png",cannon.getX()+2 , cannon.getY()+23, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		} else if (cannon.getRotation() == 90){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()-19, cannon.getY()-1, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		} else if (cannon.getRotation() == -90){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+25, cannon.getY()-1, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		} else if (cannon.getRotation() == 45){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()-13, cannon.getY()+15, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		} else if (cannon.getRotation() == -45){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+18, cannon.getY()+14, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		} else if (cannon.getRotation() == 135){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()-13, cannon.getY()-19, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		} else if (cannon.getRotation() == -135){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+20, cannon.getY()-18, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		} else if (cannon.getRotation() == 180){ //Done
			bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+4, cannon.getY()-25, cannon.getRotation(), (cannon.getRotation() * (float)Math.PI / -180)));
		}
	}


	@Override
	public void render() {

		if (gameState == GameState.TITLE_SCREEN) {
			titleScreen();
		}
		else if (gameState == GameState.LEVEL_1) {
			level1();
		}
		else if (gameState == GameState.LEVEL_2){
			level2();
		}
		else if (gameState == GameState.LEVEL_COMPLETE_1) {
			level1Complete();
		}
	}

	@Override
	public void dispose(){
		batch.dispose();
		img.dispose();
		pew.dispose();
		explosion.dispose();
		satellitePew.dispose();
		backMusic.dispose();
		titleMusic.dispose();
	}

	public void magneticAsteroid(){
		for (MagneticAsteroid mAsteroid : magneticAsteroidList){
			if (spaceship.getBounds().contains(mAsteroid.getX(),
					mAsteroid.getY())) {
				float rngX2 = mAsteroid.getX();
				float rngY2 = mAsteroid.getY();
				float magneticAsteroidX = rngX2;
				float magneticAsteroidY = rngY2;
				float dx = spaceship.getX() - rngX2;
				float dy = spaceship.getY() - rngY2;
				float angle = (float) Math.atan2(dy, dx);
				float speedX = (float) (0.8f * Math.cos(angle));
				float speedY = (float) (0.8f * Math.sin(angle));
				magneticAsteroidX += speedX;
				magneticAsteroidY += speedY;
				mAsteroid.setX(magneticAsteroidX);
				mAsteroid.setY(magneticAsteroidY);
				mAsteroid.setSpeedX(speedX);
				mAsteroid.setSpeedY(speedY);
			}
		}
	}

	public int randomX(){

        Random spawn = new Random();
        int rngX = (spawn.nextInt(Gdx.graphics.getWidth()-60));
        //int rngY = (spawn.nextInt(Gdx.graphics.getHeight()));

        while((float) rngX > spaceship.getX()-100 && (float) rngX < spaceship.getX()+100) {
            rngX = (spawn.nextInt(Gdx.graphics.getWidth()-60));
        }

        return rngX;
    }

    public int randomY(){

        Random spawn = new Random();
        //int rngX = (spawn.nextInt(Gdx.graphics.getWidth()));
        int rngY = (spawn.nextInt(Gdx.graphics.getHeight()-60));

        while((float) rngY > spaceship.getY()-100 && (float) rngY < spaceship.getY()+100) {
            rngY = (spawn.nextInt(Gdx.graphics.getHeight()-60));
        }
        return rngY;
    }


    public void spawnMagneticAsteroid() {

        while (countMagneticAsteroid < 4) {
            int rngX = randomX();
            int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;

            magneticAsteroid = new MagneticAsteroid("meteorBrown_big4.png", rngX, rngY, size, size);

            magneticAsteroidList.add(magneticAsteroid);

            countMagneticAsteroid++;

        }
    }
	public void spawnAsteroid() {

        while (countAsteroid <= 10){
			int rngX = randomX();
			int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;

			asteroid = new Asteroid("asteroid.png", rngX, rngY, size, size);

            asteroidList.add(asteroid);

            countAsteroid++;

		}

	}
	public void spawnNewAsteroid(){

		if (countAsteroid <= 10){
			int rngX = randomX();
			int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;

			asteroid = new Asteroid("asteroid.png", rngX, rngY, size, size);

			asteroidList.add(asteroid);
			countAsteroid++;
		}
		timer = 0;



	}

    public void spawnNewMagneticAsteroid() {
		if (countMagneticAsteroid < 4){
			int rngX = randomX();
			int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;

			magneticAsteroid = new MagneticAsteroid("meteorBrown_big4.png", rngX, rngY, size, size);

			magneticAsteroidList.add(magneticAsteroid);
			countMagneticAsteroid++;
		}
    }

	public void randomSatellite(){
		Random spawn = new Random();
		int rngX;
		int rngY;
		int xMax = Gdx.graphics.getWidth()+80;
		int xMin = -50;
		int yMax = Gdx.graphics.getHeight()+80;
		int yMin = -50;
		int random = (int)(Math.random() * 4 + 1);

		switch (random){
			case 1:
				rngX = (spawn.nextInt(xMax + 1 + xMin) - xMin);
				rngY = (spawn.nextInt(yMax + 1 + yMin) - yMin);
				while (rngY < Gdx.graphics.getHeight()+40 && rngY > 0){
					rngY = (spawn.nextInt(yMax + 1 + yMin) - yMin);
				}
				satelliteList.add(new Satellite("Satellite.png", rngX, rngY, 55, 55));
				break;
			case 2:
				rngX = (spawn.nextInt(xMax + 1 + xMin) - xMin);
				rngY = (spawn.nextInt(yMax + 1 + yMin) - yMin);
				while (rngX < Gdx.graphics.getWidth()+40 && rngX > 0){
					rngX = (spawn.nextInt(xMax + 1 + xMin) - xMin);
				}
				satelliteList.add(new Satellite("Satellite.png", rngX, rngY, 55, 55));
				break;
			case 3:
				rngX = (spawn.nextInt(xMax + 1 + xMin) - xMin);
				rngY = (int)(Math.random() * ((yMax -50) + 1)) - 50;
				while (rngY < Gdx.graphics.getHeight()+40 && rngY > 0){
					rngY = (int)(Math.random() * ((yMax -50) + 1)) - 50;
				}
				satelliteList.add(new Satellite("Satellite.png", rngX, rngY, 55, 55));
				break;
			case 4:
				rngX = (int)(Math.random() * ((xMax -50) + 1)) - 50;
				rngY = (spawn.nextInt(yMax + 1 + yMin) - yMin);
				while (rngX < Gdx.graphics.getWidth()+40 && rngX > 0){
					rngX = (int)(Math.random() * ((xMax -50) + 1)) - 50;
				}
				satelliteList.add(new Satellite("Satellite.png", rngX, rngY, 55, 55));
				break;

		}
	}

	public void satelliteShoot(){
		for (Satellite satellite : satelliteList){
			if (satellite.getY() <= spaceship.getY()){
				float opposite = satellite.getX()-spaceship.getX();
				float adjacent = satellite.getY()-spaceship.getY();
				if(adjacent == 0){
					adjacent = 0.5f;
				}
				if (opposite == 0){
					opposite = 0.5f;
				}

				float opposite2 = spaceship.getX()- satellite.getX();
				float adjacent2 = spaceship.getY()- satellite.getY();
				if(adjacent2 == 0){
					adjacent = 0.5f;
				}
				if (opposite2 == 0){
					opposite = 0.5f;
				}
				float angle = (float)Math.atan(opposite/adjacent);
				float radians = (float)Math.atan2(opposite2,adjacent2);
				float angle2 = radians * (-180/(float)Math.PI);
				bulletList.add(new SatelliteBullet("laserBlue02.png", satellite.getX()+20, satellite.getY()+20, angle2, angle));
				satellitePew.play();

			} else {
				float opposite = satellite.getX()-spaceship.getX();
				float adjacent = satellite.getY()-spaceship.getY();
				if(adjacent == 0){
					adjacent = 0.5f;
				}
				if (opposite == 0){
					opposite = 0.5f;
				}

				float opposite2 = spaceship.getX()- satellite.getX();
				float adjacent2 = spaceship.getY()- satellite.getY();
				if(adjacent2 == 0){
					adjacent = 0.5f;
				}
				if (opposite2 == 0){
					opposite = 0.5f;
				}
				float angle = (float)Math.atan2(opposite2,adjacent2);
				float radians = (float)Math.atan2(opposite,adjacent);
				float angle2 = radians * (-180/(float)Math.PI);
				bulletList.add(new SatelliteBullet("laserBlue02.png", satellite.getX()+20, satellite.getY()+20, angle2, angle));
				satellitePew.play();
			}
		}

	}

	public void level2(){
		backMusic.play();
        elapsedTime = Gdx.graphics.getDeltaTime();
		checkInput();
		spaceship.updatePositionFromSpeed();
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());



		spawnNewAsteroid();
		spawnNewMagneticAsteroid();



		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, Integer.toString(score), 20, 20);
		shield.draw(batch);
		spaceship.draw(batch);
		cannon.draw(batch);
		magneticAsteroid();
		for (Asteroid asteroid : asteroidList){
			asteroid.draw(batch);
		}
		for (MagneticAsteroid magneticAsteroid : magneticAsteroidList){
			magneticAsteroid.draw(batch);
		}
		for (Bullet bullet : bulletList) {
			bullet.draw(batch);
		}
		for (Satellite satellite : satelliteList){
			satellite.draw(batch);
		}
        for (Asteroid asteroid : asteroidList) {
            asteroid.updatePositionFromSpeed();
            for (Bullet bullet : bulletList){
                if (asteroid.collidesWith(bullet.getCollisionRectangle())) {
                    if (bullet instanceof SpaceshipBullet){
                        asteroid.hit();
                        bullet.hit();
                        explosionImage.addExplosion(asteroid.getX(), asteroid.getY());
                        countAsteroid--;
                        score++;
                        explosion.play();
                        break;
                    }

                }
                if (bullet instanceof SatelliteBullet){
                    if (shield.collidesWith(bullet.getCollisionRectangle())){
                        shield.isHit();
                        bullet.hit();
                        break;
                    }
                }

            }
            if (asteroid.isHit()){
                asteroidList.remove(asteroid);
                break;
            }

        }
        for (MagneticAsteroid magneticAsteroid : magneticAsteroidList) {
            magneticAsteroid.updatePositionFromSpeed();

            for (Bullet bullet : bulletList) {
                if (magneticAsteroid.collidesWith(bullet.getCollisionRectangle())) {
                    if (bullet instanceof SpaceshipBullet){
                        magneticAsteroid.hit();
                        bullet.hit();
                        explosionImage.addExplosion(magneticAsteroid.getX(), magneticAsteroid.getY());
                        score++;
                        countMagneticAsteroid--;
                        explosion.play();
                        break;
                    }
                }
            }

            if (magneticAsteroid.isHit()) {
                //disposeAsteroidList.add(asteroid);
                magneticAsteroidList.remove(magneticAsteroid);
                break;
            }
        }
		//asteroid.draw(batch);
		for (Asteroid asteroid : asteroidList) {
			if (shield.collidesWith(asteroid.getCollisionRectangle())) {
				shield.isHit();
				asteroid.hit();
                explosionImage.addExplosion(asteroid.getX(), asteroid.getY());
				countAsteroid--;
				explosion.play();
                score++;
				break;
			}
		}

		for (Satellite satellite : satelliteList){
			for (Bullet bullet : bulletList) {
				if (bullet instanceof SpaceshipBullet) {
					if (satellite.collidesWith(bullet.getCollisionRectangle())) {
						satellite.hit();
						bullet.hit();
                        explosionImage.addExplosion(satellite.getX(), satellite.getY());
                        score++;
						break;
					}
				}
			}
		}

		for (MagneticAsteroid magneticAsteroid : magneticAsteroidList) {
			if (shield.collidesWith(magneticAsteroid.getCollisionRectangle())) {
				shield.isHit();
				magneticAsteroid.hit();
                explosionImage.addExplosion(magneticAsteroid.getX(), magneticAsteroid.getY());
				countMagneticAsteroid--;
				explosion.play();
                score++;
				break;
			}
		}



		if (spaceship.getSpeedY() == 0 && spaceship.getSpeedX() == 0){
			spaceship.updateImage("Spaceship.png");
		}
		else {
			spaceship.updateImage("SpaceshipBoost.png");
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

		for (Satellite satellite : satelliteList){
			satellite.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
			if (satellite.shoot()){
				satelliteShoot();
			}
			if (satellite.isTimeout() || satellite.isHit()){
				satelliteList.remove(satellite);
				break;
			}
		}


		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
			shield.isHit();
		}
	}

	public void level1(){
		backMusic.play();
        elapsedTime = Gdx.graphics.getDeltaTime();
        timer = timer +1;
		checkInput();

		spaceship.updatePositionFromSpeed();
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());


		spawnNewMagneticAsteroid();
        if(timer > spawnAsteroidTimer) {
            spawnNewAsteroid();
        }

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, Integer.toString(score), 20, 20);
		shield.draw(batch);
		spaceship.draw(batch);
		cannon.draw(batch);
		for (Asteroid asteroid : asteroidList){
			asteroid.draw(batch);
		}
		for (Bullet bullet : bulletList) {
			bullet.draw(batch);
		}
		for (Satellite satellite : satelliteList){
			satellite.draw(batch);
		}


        for (Asteroid asteroid : asteroidList) {
            asteroid.updatePositionFromSpeed();
            for (Bullet bullet : bulletList){
                if (asteroid.collidesWith(bullet.getCollisionRectangle())) {
                    if (bullet instanceof SpaceshipBullet){
                        asteroid.hit();
                        explosionImage.addExplosion(asteroid.getX(), asteroid.getY());
                        bullet.hit();
                        countAsteroid--;
                        explosion.play();
                        score++;
                        Random spawn = new Random();
                        spawnAsteroidTimer = (spawn.nextInt(200));
                        break;
                    }

                }
                if (bullet instanceof SatelliteBullet){
                    if (shield.collidesWith(bullet.getCollisionRectangle())){
                        shield.isHit();
                        bullet.hit();
                        break;
                    }
                }

            }
            if (asteroid.isHit()){
                asteroidList.remove(asteroid);
                break;
            }

        }


        for (Asteroid asteroid : asteroidList) {
            if (shield.collidesWith(asteroid.getCollisionRectangle())) {
                shield.isHit();
                asteroid.hit();
                explosionImage.addExplosion(asteroid.getX(), asteroid.getY());
                // batch.draw(animation.getKeyFrame(elapsedTime), asteroid.getX(),asteroid.getY());
                countAsteroid--;
                explosion.play();
                score++;
                Random spawn = new Random();
                spawnAsteroidTimer = (spawn.nextInt(200));

                break;
            }
        }


        for (Satellite satellite : satelliteList){
			for (Bullet bullet : bulletList) {
				if (bullet instanceof SpaceshipBullet) {
					if (satellite.collidesWith(bullet.getCollisionRectangle())) {
						satellite.hit();
						bullet.hit();
                        explosionImage.addExplosion(satellite.getX(), satellite.getY());
						explosion.play();
						break;
					}
				}
			}
		}

		if (spaceship.getSpeedY() == 0 && spaceship.getSpeedX() == 0){
			spaceship.updateImage("Spaceship.png");
		}
		else {
			spaceship.updateImage("SpaceshipBoost.png");
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

		for (Satellite satellite : satelliteList){
			satellite.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
			if (satellite.shoot()){
				satelliteShoot();
			}
			if (satellite.isTimeout() || satellite.isHit()){
				satelliteList.remove(satellite);
				break;
			}
		}

		if (score == 10){
			gameState = gameState.LEVEL_COMPLETE_1;
			//score = 0;
		}
	}

	public void level1Complete(){

		spaceship.setX(Gdx.graphics.getWidth()/2);
		spaceship.setY(Gdx.graphics.getHeight()/2);

		img = new Texture("levelonecompleted.png");

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			gameState = GameState.LEVEL_2;
			img = new Texture("spaceBack.jpg");
		}
		batch.end();
	}

	private void titleScreen() {
		titleMusic.play();

		img = new Texture("menubg.png");
		menuTexture = new Texture(Gdx.files.internal("menuarrow.png"));
		menuSprite = new Sprite(menuTexture);

		checkInputMenu();
		updateArrowMenuPos();

		batch.begin();
		batch.draw(img, 0, 0);
		menuSprite.draw(batch);


		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			titleMusic.stop();
			if (menuPos == 0) {
				gameState = GameState.LEVEL_1;
				img = new Texture("spaceBack.jpg");
			}
		}
		batch.end();
	}

	public void checkInputMenu() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			menuPos--;
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			menuPos++;
		}

	}

	public void updateArrowMenuPos() {
		if(menuPos > 3){
			menuPos = 0;
		}else if(menuPos < 0){
			menuPos = 3;
		}

		if(menuPos == 0) {
			menuSprite.setPosition(60,350);
		}else if(menuPos == 1) {
			menuSprite.setPosition(60,282);
		}else if(menuPos == 2) {
			menuSprite.setPosition(60,216);
		}else if(menuPos == 3) {
			menuSprite.setPosition(60,148);
		}

	}
}


