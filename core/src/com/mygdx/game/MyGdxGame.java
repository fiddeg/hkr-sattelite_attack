package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

	private enum GameState{
		TITLE_SCREEN,
		HELP_SCREEN,
		HIGH_SCORE_SCORE,
		LEVEL_1,
		LEVEL_2,
		LEVEL_3,
		LEVEL_4,
		LEVEL_COMPLETE_1,
		LEVEL_COMPLETE_2,
		LEVEL_COMPLETE_3,
		LEVEL_COMPLETE_4,
		GAME_OVER
	}

	private GameState gameState = GameState.LEVEL_3;
	private SpriteBatch batch;
	private HighScoreIO highScoreIO = new HighScoreIO();
	private Save save = new Save();
	private Texture gameOverImage;
	private Texture img;
	private Texture titleImg;
	private Texture levelOneImg;
	private Texture levelTwoImg;
	private Texture levelThreeImg;
	private Texture helpScreenImg;
	private Texture highScoreImg;
	private Spaceship spaceship;
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private ArrayList<Bullet> tempDispose = new ArrayList<Bullet>();
	private Shield shield;
	private Cannon cannon;
	private Asteroid asteroid;
	private GiantAsteroid giantAsteroid;
	private JuniorAsteroid juniorAsteroid;
	private static PowerUp powerUp;
	private InputKeys inputKeys = new InputKeys();
	private MagneticAsteroid magneticAsteroid;
    private ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();
    private ArrayList<MagneticAsteroid> magneticAsteroidList = new ArrayList<MagneticAsteroid>();
	private ArrayList<GiantAsteroid> giantAsteroidList = new ArrayList<GiantAsteroid>();
	private ArrayList<JuniorAsteroid> juniorAsteriodList = new ArrayList<JuniorAsteroid>();
	private ArrayList<Satellite> satelliteList = new ArrayList<Satellite>();
	private Sound pew;
	private Sound explosion;
	private Sound satellitePew;
	private Music backMusic;
    private int timer;
	private BitmapFont font;
	private BitmapFont fontHighScore;
	private Music titleMusic;
	private Sprite menuSprite;
	private Texture menuTexture;
	private int menuPos = 0;
	private String[] asteroidPicture= new String[]{"brownast.png","brownspotast.png"};
	private String[] magneticAsteroidPicture= new String[]{"coboltast.png","bluestripeast.png"};
	private ArrayList<Explosion> explosionList = new ArrayList<Explosion>();
	private String highScoreText = "";
	private int[] highScoresList;
	private ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();

	private int specialShots = 0;
	private int countAsteroid = 0;
    private int countMagneticAsteroid = 0;
	private int countGiantAsteroid= 0;
	private int score = 0;
	private int enemysLeft = 15;
	private int highScore = 0;
	private int randomSatellite;
	private boolean gameOver = false;
	private boolean highScoreListAdded = false;



	@Override
	public void create () {
		batch = new SpriteBatch();
		backMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backmusic.mp3"));
		titleMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/titlemusic.mp3"));
		createNew();
	}

	public void createNew(){
		//batch = new SpriteBatch();
		img = new Texture("spaceBack.jpg");
		gameOverImage = new Texture("GameOver.png");
		pew = Gdx.audio.newSound(Gdx.files.internal("sounds/pew.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explode.wav"));
		satellitePew = Gdx.audio.newSound(Gdx.files.internal("sounds/satellitepew.wav"));
		titleImg = new Texture("menubg.png");
		levelOneImg = new Texture("level1completed.png");
		levelTwoImg = new Texture("level2completed.png");
		levelThreeImg = new Texture("Level3completed.png");
		helpScreenImg = new Texture("HelpScreen.png");
		highScoreImg = new Texture("highscore.png");
		menuTexture = new Texture(Gdx.files.internal("menuarrow.png"));
		menuSprite = new Sprite(menuTexture);
		font = new BitmapFont();


		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/space age.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 41;
		fontHighScore = generator.generateFont(parameter);
		generator.dispose();

		Save.load();
		highScoresList = Save.io.getHighScores();
		createObjects();
		spawnAsteroid();
		spawnMagneticAsteroid();
		spawnGiantAsteroid();
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (specialShots > 0){
				createSpecialBullet();
				specialShots--;
			}
			else {
				createBulletCannon();
			}
			pew.play();
        }
	}

	public Spaceship getSpaceship(){
		return spaceship;
	}

	public Cannon getCannon(){
		return cannon;
	}

	public Sound getPew(){
		return pew;
	}

	public MyGdxGame getInstance(){
		return MyGdxGame.this;
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

	public void createSpecialBullet(){
		bulletList.add(new SpaceshipBullet("bullet.png",cannon.getX()+2 , cannon.getY()+23, 0, (0 * (float)Math.PI / -180)));
		bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()-19, cannon.getY()-1, 90, (90 * (float)Math.PI / -180)));
		bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+25, cannon.getY()-1, -90, (-90 * (float)Math.PI / -180)));
		bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()-13, cannon.getY()+15, 45, (45 * (float)Math.PI / -180)));
		bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+18, cannon.getY()+14, -45, (-45 * (float)Math.PI / -180)));
		bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()-13, cannon.getY()-19, 135, (135 * (float)Math.PI / -180)));
		bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+20, cannon.getY()-18, -135, (-135 * (float)Math.PI / -180)));
		bulletList.add(new SpaceshipBullet("bullet.png", cannon.getX()+4, cannon.getY()-25, 180, (180 * (float)Math.PI / -180)));
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
		else if (gameState == GameState.LEVEL_3){
			level3();
		}
		else if (gameState == GameState.LEVEL_4){
			level4();
		}
		else if (gameState == GameState.LEVEL_COMPLETE_1) {
			level1Complete();
		}
		else if (gameState == GameState.LEVEL_COMPLETE_2) {
			level2Complete();
		}
		else if (gameState == GameState.LEVEL_COMPLETE_3) {
			level3Complete();
		}
		else if (gameState == GameState.LEVEL_COMPLETE_4) {
			titleScreen();
		}
		else if (gameState == GameState.GAME_OVER) {
			renderGameStateGameOver();
		}
		else if (gameState == GameState.HELP_SCREEN){
			renderHelpScreen();
		}
		else if (gameState == GameState.HIGH_SCORE_SCORE){
			renderHighScore();
		}
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
		int rngX = (spawn.nextInt(Gdx.graphics.getWidth()+200)-100);

		while ( rngX > 0 && rngX < 1200) {
			rngX = (spawn.nextInt(Gdx.graphics.getWidth()+200)-100);
		}
		return rngX;
	}

	public int randomY(){

		Random spawn = new Random();
		int rngY = (spawn.nextInt(Gdx.graphics.getHeight()+100)-100);
		return rngY;
	}

	public void spawnGiantAsteroid(){

		while (countGiantAsteroid <= 3){
			int rngX = randomX();
			int rngY = randomY();

			giantAsteroid = new GiantAsteroid("asteroid-icon.png", rngX, rngY, 210, 210);

			giantAsteroidList.add(giantAsteroid);
			countGiantAsteroid++;
		}
	}

	public void spawnMagneticAsteroid() {

		while (countMagneticAsteroid < 4) {
			int rngX = randomX();
			int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;
			int texture = (int)(Math.random()* 2);

			magneticAsteroid = new MagneticAsteroid(magneticAsteroidPicture[texture], rngX, rngY, size, size);
			magneticAsteroid.setSpeedX(randomSpeed());
			magneticAsteroid.setSpeedY(randomSpeed());
			magneticAsteroidList.add(magneticAsteroid);
			countMagneticAsteroid++;
		}
	}

	public void spawnAsteroid() {

		while (countAsteroid <= 10){
			int rngX = randomX();
			int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;
			int texture = (int)(Math.random()* 2);

			asteroid = new Asteroid(asteroidPicture[texture], rngX, rngY, size, size);
			asteroid.setSpeedX(randomSpeed());
			asteroid.setSpeedY(randomSpeed());
			asteroidList.add(asteroid);
			countAsteroid++;
		}
	}
	public void spawnJuniorAsteroids(float x, float y) {

		for (int i = 0; i < 3; i++){

			int size = (int)(Math.random()* (70 - 35))+35;
			int texture = (int)(Math.random()* 2);

			juniorAsteroid = new JuniorAsteroid(asteroidPicture[texture], x, y, size, size);
			juniorAsteriodList.add(juniorAsteroid);

		}

	}
	public void spawnNewAsteroid(){

		if (countAsteroid <= 10){
			int rngX = randomX();
			int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;
			int texture = (int)(Math.random()* 2);

			asteroid = new Asteroid(asteroidPicture[texture], rngX, rngY, size, size);
			asteroid.setSpeedX(randomSpeed());
			asteroid.setSpeedY(randomSpeed());
			asteroidList.add(asteroid);
			countAsteroid++;
		}
	}


	public void spawnNewMagneticAsteroid() {
		if (countMagneticAsteroid < 4){
			int rngX = randomX();
			int rngY = randomY();

			int size = (int)(Math.random()* (70 - 35))+35;
			int texture = (int)(Math.random()* 2);

			magneticAsteroid = new MagneticAsteroid(magneticAsteroidPicture[texture], rngX, rngY, size, size);
			magneticAsteroid.setSpeedX(randomSpeed());
			magneticAsteroid.setSpeedY(randomSpeed());
			magneticAsteroidList.add(magneticAsteroid);
			countMagneticAsteroid++;
		}
	}

	public float randomSpeed() {
		Random random = new Random();
		float randomSpeed = random.nextFloat()*4 -2;

		while (randomSpeed >= -1 && randomSpeed <= 1) {
			randomSpeed = random.nextFloat()*4 -2;
		}
		return randomSpeed;
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
		checkInput();
		spaceship.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());

		spawnNewAsteroid();
		spawnNewMagneticAsteroid();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, "Score: " + Integer.toString(score), 20, 25);
		font.draw(batch, "Enemy left: " + Integer.toString(enemysLeft), 20, 45);
		font.draw(batch, "High Score: " + Integer.toString(highScore), 20, 65);
		shield.draw(batch);
		spaceship.draw(batch);
		cannon.draw(batch);
		magneticAsteroid();

		for(Explosion explosionImage : explosionList){
			if (explosionImage.hasStarted){
				explosionImage.elapsedTime += Gdx.graphics.getDeltaTime();
				batch.draw(explosionImage.animation.getKeyFrame(explosionImage.elapsedTime), explosionImage.x, explosionImage.y);
				if (explosionImage.animation.isAnimationFinished(explosionImage.elapsedTime)){
					explosionList.remove(explosionImage);
					explosionImage.elapsedTime = 0;
					break;
				}
			}
		}

		for (Asteroid asteroid : asteroidList){
			asteroid.draw(batch);
		}
		for (MagneticAsteroid magneticAsteroid : magneticAsteroidList){
			magneticAsteroid.draw(batch);
		}
		for (Bullet bullet : bulletList) {
			bullet.draw(batch);
		}

		for (PowerUp powerUp : powerUpList){
			if (powerUp.collidesWith(spaceship.getCollisionRectangle()) || powerUp.collidesWith(shield.getCollisionRectangle())){
				if (powerUp.getTypePowerUp() == 1){
					spaceship.powerUpSpeed();
				}
				else if (powerUp.getTypePowerUp() == 2){
					specialShots = 5;
				}
				powerUp.hit();
			}
			if (powerUp.isHit()){
				powerUpList.remove(powerUp);
				break;
			}

		}

        for (Asteroid asteroid : asteroidList) {
            asteroid.updatePositionFromSpeed();
			if (asteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				asteroid.hit();
			}
            for (Bullet bullet : bulletList){
                if (asteroid.collidesWith(bullet.getCollisionRectangle())) {
                    if (bullet instanceof SpaceshipBullet){
                        asteroid.hit();
                        bullet.hit();
						explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
						if (powerUp.shouldSpawn()){
							powerUpList.add(new PowerUp(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY() + (asteroid.getHeight()/2)));
						}
                        countAsteroid--;
                        score++;
						enemysLeft--;
                        explosion.play();
                        break;
                    }

                }
            }

			if (shield.collidesWith(asteroid.getCollisionRectangle())) {
				shield.isHit();
				asteroid.hit();
				explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
				if (powerUp.shouldSpawn()){
					powerUpList.add(new PowerUp(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY() + (asteroid.getHeight()/2)));
				}
				countAsteroid--;
				explosion.play();
				score++;
				enemysLeft--;
				break;
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
						explosionList.add(new Explosion(magneticAsteroid.getX(), magneticAsteroid.getY(), true));
						if (powerUp.shouldSpawn()){
							powerUpList.add(new PowerUp(magneticAsteroid.getX()+(magneticAsteroid.getWidth()/2), magneticAsteroid.getY() + (magneticAsteroid.getHeight()/2)));
						}
                        score++;
						enemysLeft--;
                        countMagneticAsteroid--;
                        explosion.play();
                        break;
                    }
                }
            }

			if (magneticAsteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				asteroid.hit();
			}

			if (shield.collidesWith(magneticAsteroid.getCollisionRectangle())) {
				shield.isHit();
				magneticAsteroid.hit();
				explosionList.add(new Explosion(magneticAsteroid.getX(), magneticAsteroid.getY(), true));
				if (powerUp.shouldSpawn()){
					powerUpList.add(new PowerUp(magneticAsteroid.getX()+(magneticAsteroid.getWidth()/2), magneticAsteroid.getY() + (magneticAsteroid.getHeight()/2)));
				}
				countMagneticAsteroid--;
				explosion.play();
				score++;
				enemysLeft--;
				break;
			}

            if (magneticAsteroid.isHit()) {
                magneticAsteroidList.remove(magneticAsteroid);
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

		if (gameOver){
			Save.io.addHighScore(score);
			Save.save();
			gameState = gameState.GAME_OVER;
			backMusic.stop();
			highScoreListAdded = false;
			if (score > highScore){
				highScore = score;
			}
			score = 0;
			enemysLeft = 15;
			gameOver = false;
		}

		if (enemysLeft <= 0){
			gameState = gameState.LEVEL_COMPLETE_2;
			backMusic.stop();
			enemysLeft = 15;
		}
	}

	public void level1(){

		checkInput();

		spaceship.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());



		spawnNewMagneticAsteroid();
		spawnNewAsteroid();


		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, "Score: " + Integer.toString(score), 20, 25);
		font.draw(batch, "Enemy left: " + Integer.toString(enemysLeft), 20, 45);
		font.draw(batch, "High Score: " + Integer.toString(highScore), 20, 65);
		shield.draw(batch);
		spaceship.draw(batch);
		cannon.draw(batch);

		for(Explosion explosionImage : explosionList){
			if (explosionImage.hasStarted){
				explosionImage.elapsedTime += Gdx.graphics.getDeltaTime();
				batch.draw(explosionImage.animation.getKeyFrame(explosionImage.elapsedTime), explosionImage.x, explosionImage.y);
				if (explosionImage.animation.isAnimationFinished(explosionImage.elapsedTime)){
					explosionList.remove(explosionImage);
					explosionImage.elapsedTime = 0;
					break;
				}
			}
		}


		for (Asteroid asteroid : asteroidList){
			asteroid.draw(batch);
		}
		for (PowerUp powerUp : powerUpList){
			powerUp.draw(batch);
		}
		for (Bullet bullet : bulletList) {
			bullet.draw(batch);
		}

		for (PowerUp powerUp : powerUpList){
			if (powerUp.collidesWith(spaceship.getCollisionRectangle()) || powerUp.collidesWith(shield.getCollisionRectangle())){
				if (powerUp.getTypePowerUp() == 1){
					spaceship.powerUpSpeed();
				}
				else if (powerUp.getTypePowerUp() == 2){
					specialShots = 5;
				}
				powerUp.hit();
			}
			if (powerUp.isHit()){
				powerUpList.remove(powerUp);
				break;
			}
		}

        for (Asteroid asteroid : asteroidList) {
            asteroid.updatePositionFromSpeed();
			if (asteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				asteroid.hit();
			}

            for (Bullet bullet : bulletList){
                if (asteroid.collidesWith(bullet.getCollisionRectangle())) {
                    if (bullet instanceof SpaceshipBullet){
                        asteroid.hit();
						explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
                        bullet.hit();
						if (powerUp.shouldSpawn()){
							powerUpList.add(new PowerUp(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY() + (asteroid.getHeight()/2)));
						}
                        countAsteroid--;
                        explosion.play();
                        score++;
						enemysLeft--;
                        Random spawn = new Random();
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

			if (shield.collidesWith(asteroid.getCollisionRectangle())) {
				shield.isHit();
				asteroid.hit();
				explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
				countAsteroid--;
				explosion.play();
				score++;
				enemysLeft--;
				break;
			}

            if (asteroid.isHit()){
                asteroidList.remove(asteroid);
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

		if (gameOver){
			Save.io.addHighScore(score);
			Save.save();
			gameState = gameState.GAME_OVER;
			highScoreListAdded = false;
			if (score > highScore){
				highScore = score;
			}
			score = 0;
			enemysLeft = 15;
			gameOver = false;
			backMusic.stop();
			titleMusic.play();
		}

		if (enemysLeft <= 0){
			gameState = gameState.LEVEL_COMPLETE_1;
			backMusic.stop();
			enemysLeft = 15;
		}
	}

	public void level1Complete(){
		titleMusic.play();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(levelOneImg, 0, 0);

		asteroidList.clear();
		bulletList.clear();
		explosionList.clear();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			titleMusic.stop();
			backMusic.play();
			gameState = GameState.LEVEL_2;
			img = new Texture("spaceBack.jpg");
			countAsteroid = 0;
			createNew();
		}
		batch.end();
	}

	public void level2Complete(){
		titleMusic.play();

		asteroidList.clear();
		bulletList.clear();
		explosionList.clear();
		magneticAsteroidList.clear();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(levelTwoImg, 0, 0);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			titleMusic.stop();
			backMusic.play();
			countAsteroid = 0;
			countMagneticAsteroid = 0;
			gameState = GameState.LEVEL_3;
			createNew();
		}
		batch.end();
	}

	public void level3Complete(){
		titleMusic.play();

		asteroidList.clear();
		bulletList.clear();
		explosionList.clear();
		magneticAsteroidList.clear();
		satelliteList.clear();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(levelThreeImg, 0, 0);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			titleMusic.stop();
			backMusic.play();
			countAsteroid = 0;
			countMagneticAsteroid = 0;
			gameState = GameState.LEVEL_4;
			createNew();
		}
		batch.end();
	}

	private void titleScreen() {
		titleMusic.play();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		checkInputMenu();
		updateArrowMenuPos();

		batch.begin();
		batch.draw(titleImg, 0, 0);
		menuSprite.draw(batch);


		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			//titleMusic.stop();
			if (menuPos == 0) {
				titleMusic.stop();
				backMusic.play();
				gameState = GameState.LEVEL_1;
			}
			else if (menuPos == 1) {
				gameState = GameState.HIGH_SCORE_SCORE;
			}
			else if (menuPos == 2) {
				gameState = GameState.HELP_SCREEN;
			}
			else if (menuPos == 3) {
				Gdx.app.exit();
			}
			createNew();
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

	public void level3(){
		checkInput();
		spaceship.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());

		randomSatellite = (int)(Math.random() * 8 + 1);
		if (randomSatellite == 4){
			if (satelliteList.size() == 0){
				randomSatellite();
			}
		}


		spawnNewAsteroid();
		spawnNewMagneticAsteroid();


		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, "Score: " + Integer.toString(score), 20, 25);
		font.draw(batch, "Enemy left: " + Integer.toString(enemysLeft), 20, 45);
		font.draw(batch, "High Score: " + Integer.toString(highScore), 20, 65);
		shield.draw(batch);
		spaceship.draw(batch);
		cannon.draw(batch);
		magneticAsteroid();
		for(Explosion explosionImage : explosionList){
			if (explosionImage.hasStarted){
				explosionImage.elapsedTime += Gdx.graphics.getDeltaTime();
				batch.draw(explosionImage.animation.getKeyFrame(explosionImage.elapsedTime), explosionImage.x, explosionImage.y);
				if (explosionImage.animation.isAnimationFinished(explosionImage.elapsedTime)){
					explosionList.remove(explosionImage);
					explosionImage.elapsedTime = 0;
					break;
				}
			}
		}

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

		for (PowerUp powerUp : powerUpList){
			if (powerUp.collidesWith(spaceship.getCollisionRectangle()) || powerUp.collidesWith(shield.getCollisionRectangle())){
				if (powerUp.getTypePowerUp() == 1){
					spaceship.powerUpSpeed();
				}
				else if (powerUp.getTypePowerUp() == 2){
					specialShots = 5;
				}
				powerUp.hit();
			}
			if (powerUp.isHit()){
				powerUpList.remove(powerUp);
				break;
			}

		}

		for (Asteroid asteroid : asteroidList) {
			asteroid.updatePositionFromSpeed();
			if (asteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				asteroid.hit();
			}

			for (Bullet bullet : bulletList){
				if (asteroid.collidesWith(bullet.getCollisionRectangle())) {
					if (bullet instanceof SpaceshipBullet){
						asteroid.hit();
						bullet.hit();
						explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
						if (powerUp.shouldSpawn()){
							powerUpList.add(new PowerUp(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY() + (asteroid.getHeight()/2)));
						}
						countAsteroid--;
						score++;
						enemysLeft--;
						explosion.play();
						break;
					}

				}
				if (bullet instanceof SatelliteBullet){
					if (spaceship.collidesWith(bullet.getCollisionRectangle())){
						gameOver = true;
						break;
					}
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
			if (magneticAsteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				magneticAsteroid.hit();
			}

			for (Bullet bullet : bulletList) {
				if (magneticAsteroid.collidesWith(bullet.getCollisionRectangle())) {
					if (bullet instanceof SpaceshipBullet){
						magneticAsteroid.hit();
						bullet.hit();
						explosionList.add(new Explosion(magneticAsteroid.getX(), magneticAsteroid.getY(), true));
						if (powerUp.shouldSpawn()){
							powerUpList.add(new PowerUp(magneticAsteroid.getX()+(magneticAsteroid.getWidth()/2), magneticAsteroid.getY() + (magneticAsteroid.getHeight()/2)));
						}
						score++;
						enemysLeft--;
						countMagneticAsteroid--;
						explosion.play();
						break;
					}
				}
			}

			if (shield.collidesWith(magneticAsteroid.getCollisionRectangle())) {
				shield.isHit();
				magneticAsteroid.hit();
				explosionList.add(new Explosion(magneticAsteroid.getX(), magneticAsteroid.getY(), true));
				countMagneticAsteroid--;
				if (powerUp.shouldSpawn()){
					powerUpList.add(new PowerUp(magneticAsteroid.getX()+(magneticAsteroid.getWidth()/2), magneticAsteroid.getY() + (magneticAsteroid.getHeight()/2)));
				}
				explosion.play();
				score++;
				enemysLeft--;
				break;
			}

			if (magneticAsteroid.isHit()) {
				magneticAsteroidList.remove(magneticAsteroid);
				break;
			}
		}
		//asteroid.draw(batch);
		for (Asteroid asteroid : asteroidList) {
			if (shield.collidesWith(asteroid.getCollisionRectangle())) {
				shield.isHit();
				asteroid.hit();
				explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
				if (powerUp.shouldSpawn()){
					powerUpList.add(new PowerUp(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY() + (asteroid.getHeight()/2)));
				}
				countAsteroid--;
				explosion.play();
				score++;
				enemysLeft--;
				break;
			}
		}

		for (Satellite satellite : satelliteList){

			for (Bullet bullet : bulletList) {
				if (bullet instanceof SpaceshipBullet) {
					if (satellite.collidesWith(bullet.getCollisionRectangle())) {
						satellite.hit();
						bullet.hit();
						explosionList.add(new Explosion(satellite.getX(), satellite.getY(), true));
						score++;
						enemysLeft--;
						break;
					}
				}
			}

			if (satellite.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
			}

			if (satellite.collidesWith(shield.getCollisionRectangle())){
				satellite.hit();
				shield.isHit();
				explosionList.add(new Explosion(satellite.getX(), satellite.getY(), true));
				score++;
				enemysLeft--;
				break;
			}
		}
		/**
		for (MagneticAsteroid magneticAsteroid : magneticAsteroidList) {
			if (shield.collidesWith(magneticAsteroid.getCollisionRectangle())) {
				shield.isHit();
				magneticAsteroid.hit();
				explosionList.add(new Explosion(magneticAsteroid.getX(), magneticAsteroid.getY(), true));
				countMagneticAsteroid--;
				if (powerUp.shouldSpawn()){
					powerUpList.add(new PowerUp(magneticAsteroid.getX()+(magneticAsteroid.getWidth()/2), magneticAsteroid.getY() + (magneticAsteroid.getHeight()/2)));
				}
				explosion.play();
				score++;
				enemysLeft--;
				break;
			}
		}
		**/


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

		if (gameOver){
			Save.io.addHighScore(score);
			Save.save();
			gameState = gameState.GAME_OVER;
			highScoreListAdded = false;
			save.save();
			if (score > highScore){
				highScore = score;
			}
			score = 0;
			enemysLeft = 15;
			countAsteroid = 0;
			gameOver = false;
			backMusic.stop();
			titleMusic.play();
		}

		if (enemysLeft <= 0){
			gameState = gameState.LEVEL_COMPLETE_3;
			backMusic.stop();
			enemysLeft = 15;
		}
	}

	public void level4(){

		checkInput();

		spaceship.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());

		spawnNewMagneticAsteroid();
		spawnNewAsteroid();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, "Score: " + Integer.toString(score), 20, 25);
		font.draw(batch, "Enemy left: " + Integer.toString(enemysLeft), 20, 45);
		font.draw(batch, "High Score: " + Integer.toString(highScore), 20, 65);
		shield.draw(batch);
		spaceship.draw(batch);
		cannon.draw(batch);

		for(Explosion explosionImage : explosionList){
			if (explosionImage.hasStarted){
				explosionImage.elapsedTime += Gdx.graphics.getDeltaTime();
				batch.draw(explosionImage.animation.getKeyFrame(explosionImage.elapsedTime), explosionImage.x, explosionImage.y);
				if (explosionImage.animation.isAnimationFinished(explosionImage.elapsedTime)){
					explosionList.remove(explosionImage);
					explosionImage.elapsedTime = 0;
					break;
				}
			}
		}


		for (Asteroid asteroid : asteroidList){
			asteroid.draw(batch);
		}
		for (Bullet bullet : bulletList) {
			bullet.draw(batch);
		}
		for (GiantAsteroid giantAsteroid : giantAsteroidList){
			giantAsteroid.draw(batch);
		}
		for (JuniorAsteroid juniorAsteroid : juniorAsteriodList){
			juniorAsteroid.draw(batch);
		}

		for (PowerUp powerUp : powerUpList){
			if (powerUp.collidesWith(spaceship.getCollisionRectangle()) || powerUp.collidesWith(shield.getCollisionRectangle())){
				if (powerUp.getTypePowerUp() == 1){
					spaceship.powerUpSpeed();
				}
				else if (powerUp.getTypePowerUp() == 2){
					specialShots = 5;
				}
				powerUp.hit();
			}
			if (powerUp.isHit()){
				powerUpList.remove(powerUp);
				break;
			}

		}

		for (JuniorAsteroid juniorAsteroid : juniorAsteriodList) {
			juniorAsteroid.updatePositionFromSpeed();
			if (juniorAsteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				juniorAsteroid.hit();
			}

			for (Bullet bullet : bulletList){
				if (juniorAsteroid.collidesWith(bullet.getCollisionRectangle())) {
					if (bullet instanceof SpaceshipBullet){
						juniorAsteroid.hit();
						explosionList.add(new Explosion(juniorAsteroid.getX(), juniorAsteroid.getY(), true));
						if (powerUp.shouldSpawn()){
							powerUpList.add(new PowerUp(juniorAsteroid.getX()+(juniorAsteroid.getWidth()/2), juniorAsteroid.getY() + (juniorAsteroid.getHeight()/2)));
						}
						bullet.hit();
						explosion.play();
						score++;
						enemysLeft--;
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
			if (juniorAsteroid.isHit()){
				juniorAsteriodList.remove(juniorAsteroid);
				break;
			}

		}


		for (JuniorAsteroid juniorAsteroid : juniorAsteriodList) {
			if (shield.collidesWith(juniorAsteroid.getCollisionRectangle())) {
				shield.isHit();
				juniorAsteroid.hit();
				explosionList.add(new Explosion(juniorAsteroid.getX(), juniorAsteroid.getY(), true));
				if (powerUp.shouldSpawn()){
					powerUpList.add(new PowerUp(juniorAsteroid.getX()+(juniorAsteroid.getWidth()/2), juniorAsteroid.getY() + (juniorAsteroid.getHeight()/2)));
				}
				explosion.play();
				score++;
				enemysLeft--;
				break;
			}
		}
		for (Asteroid asteroid : asteroidList) {
			asteroid.updatePositionFromSpeed();
			if (asteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				asteroid.hit();
			}

			for (Bullet bullet : bulletList){
				if (asteroid.collidesWith(bullet.getCollisionRectangle())) {
					if (bullet instanceof SpaceshipBullet){
						asteroid.hit();
						explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
						if (powerUp.shouldSpawn()){
							powerUpList.add(new PowerUp(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY() + (asteroid.getHeight()/2)));
						}
						bullet.hit();
						countAsteroid--;
						explosion.play();
						score++;
						enemysLeft--;
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

		for (GiantAsteroid giantAsteroid : giantAsteroidList) {
			giantAsteroid.updatePositionFromSpeed();
			if (giantAsteroid.collidesWith(spaceship.getCollisionRectangle())){
				gameOver = true;
				giantAsteroid.hit();
			}


			for (Bullet bullet : bulletList){
				if (giantAsteroid.collidesWith(bullet.getCollisionRectangle())) {

					if (bullet instanceof SpaceshipBullet){

						giantAsteroid.hit();
						explosionList.add(new Explosion(giantAsteroid.getX(), giantAsteroid.getY(), true));

						bullet.hit();
						countGiantAsteroid--;
						explosion.play();
						spawnJuniorAsteroids(giantAsteroid.getX(), giantAsteroid.getY());
						countGiantAsteroid--;

						score++;
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
			if (giantAsteroid.isHit()){
				giantAsteroidList.remove(giantAsteroid);
				break;
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
				explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), true));
				if (powerUp.shouldSpawn()){
					powerUpList.add(new PowerUp(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY() + (asteroid.getHeight()/2)));
				}
				countAsteroid--;
				explosion.play();
				score++;
				enemysLeft--;

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

		if (gameOver){
			Save.io.addHighScore(score);
			Save.save();
			gameState = gameState.GAME_OVER;
			highScoreListAdded = false;
			save.save();
			if (score > highScore){
				highScore = score;
			}
			score = 0;
			enemysLeft = 15;
			countAsteroid = 0;
			gameOver = false;
			backMusic.stop();
			titleMusic.play();
		}

		if (enemysLeft <= 0){
			gameState = gameState.LEVEL_COMPLETE_4;
			backMusic.stop();
			enemysLeft = 15;
		}
	}

	public void renderGameStateGameOver(){

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		asteroidList.clear();
		bulletList.clear();
		explosionList.clear();
		magneticAsteroidList.clear();
		satelliteList.clear();

		batch.begin();
		batch.draw(gameOverImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			titleMusic.stop();
			countAsteroid = 0;
			countMagneticAsteroid = 0;
			gameState = GameState.TITLE_SCREEN;
		}
		batch.end();
	}

	public void renderHelpScreen(){
		titleMusic.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(helpScreenImg, 0, 0);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			gameState = GameState.TITLE_SCREEN;
			createNew();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			titleMusic.stop();
			gameState = GameState.LEVEL_1;
			backMusic.play();
			createNew();
		}
		batch.end();
	}

	public void addHighScore(){
		Save.load();
		highScoresList = Save.io.getHighScores();
		highScoreText = "";
		for (int i = highScoresList.length-1; i >= 0; i--){
			highScoreText += Integer.toString(highScoresList[i]) + "\n";
		}
		highScoreListAdded = true;
	}

	public void renderHighScore(){
		titleMusic.play();
		if (!highScoreListAdded){
			addHighScore();
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(highScoreImg, 0, 0);

		fontHighScore.draw(batch, highScoreText, 180,438);
		//font.draw(batch, "High Score: " + Integer.toString(highScore), 20, 65);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			gameState = GameState.TITLE_SCREEN;
			createNew();
		}
		batch.end();
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
}


