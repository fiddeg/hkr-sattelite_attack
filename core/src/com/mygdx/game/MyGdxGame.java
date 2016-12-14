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

	private GameState gameState = GameState.LEVEL_4;
	private SpriteBatch batch;
	private Save save = new Save();
	private Texture gameOverImage;
	private Texture img;
	private Texture titleImg;
	private Texture levelOneImg;
	private Texture levelTwoImg;
	private Texture levelThreeImg;
	private Texture levelFourImg;
	private Texture helpScreenImg;
	private Texture highScoreImg;
	private Spaceship spaceship;
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private ArrayList<Bullet> tempDispose = new ArrayList<Bullet>();
	private Shield shield;
	private Cannon cannon;
	private Asteroid asteroid;
	private GiantAsteroid giantAsteroid;
	private GiantMagnetic giantMagnetic;
	private JuniorAsteroid juniorAsteroid;
	private JuniorMagnetic juniorMagnetic;
	private static PowerUp powerUp;
	private MagneticAsteroid magneticAsteroid;
    private ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();
	private ArrayList<Satellite> satelliteList = new ArrayList<Satellite>();
	private Sound pew;
	private Sound explosion;
	private Sound satellitePew;
	private Music backMusic;
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
	private int countGiantMagnetic= 0;
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
		img = new Texture("spaceBack.jpg");
		gameOverImage = new Texture("GameOver.png");
		pew = Gdx.audio.newSound(Gdx.files.internal("sounds/pew.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explode.wav"));
		satellitePew = Gdx.audio.newSound(Gdx.files.internal("sounds/satellitepew.wav"));
		titleImg = new Texture("menubg.png");
		levelOneImg = new Texture("level1completed.png");
		levelTwoImg = new Texture("level2completed.png");
		levelThreeImg = new Texture("Level3completed.png");
		levelFourImg = new Texture("Level4completed.png");
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
			createLevels(1);
		}
		else if (gameState == GameState.LEVEL_2){
			createLevels(2);
		}
		else if (gameState == GameState.LEVEL_3){
			createLevels(3);
		}
		else if (gameState == GameState.LEVEL_4){
			createLevels(4);
		}
		else if (gameState == GameState.LEVEL_COMPLETE_1) {
			levelComplete(1);
		}
		else if (gameState == GameState.LEVEL_COMPLETE_2) {
			levelComplete(2);
		}
		else if (gameState == GameState.LEVEL_COMPLETE_3) {
			levelComplete(3);
		}
		else if (gameState == GameState.LEVEL_COMPLETE_4) {
			levelComplete(4);
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


	public void makeMagneticAsteroidFollow(){
		for (Asteroid mAsteroid : asteroidList){
			if (mAsteroid instanceof MagneticAsteroid || mAsteroid instanceof JuniorMagnetic){
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
	}

	public int createRandomXStartPosition(){

		Random spawn = new Random();
		int rngX = (spawn.nextInt(Gdx.graphics.getWidth()+200)-100);

		while ( rngX > 0 && rngX < 1200) {
			rngX = (spawn.nextInt(Gdx.graphics.getWidth()+200)-100);
		}
		return rngX;
	}

	public int createRandomYStartPosition(){

		Random spawn = new Random();
		int rngY = (spawn.nextInt(Gdx.graphics.getHeight()+100)-100);
		return rngY;
	}

	public void spawnGiantAsteroid(){

		while (countGiantAsteroid <= 3){
			int rngX = createRandomXStartPosition();
			int rngY = createRandomYStartPosition();

			giantAsteroid = new GiantAsteroid("asteroid-icon.png", rngX, rngY, 190, 190); // from 210 to 190
			giantAsteroid.setSpeedX(randomSpeed());
			giantAsteroid.setSpeedY(randomSpeed());
			asteroidList.add(giantAsteroid);
			countGiantAsteroid++;
		}
	}

	public void spawnGiantMagnetic(){
		while (countGiantMagnetic <= 3){
			int rngX = createRandomXStartPosition();
			int rngY = createRandomYStartPosition();

			giantMagnetic = new GiantMagnetic("magnetic-icon.png", rngX, rngY, 190, 190); // from 210 to 190
			giantMagnetic.setSpeedX(randomSpeed());
			giantMagnetic.setSpeedY(randomSpeed());
			asteroidList.add(giantMagnetic);
			countGiantMagnetic++;
		}
	}
	public void spawnMagneticAsteroid() {

		while (countMagneticAsteroid < 4) {
			int rngX = createRandomXStartPosition();
			int rngY = createRandomYStartPosition();

			int size = (int)(Math.random()* (70 - 35))+35;
			int texture = (int)(Math.random()* 2);

			magneticAsteroid = new MagneticAsteroid(magneticAsteroidPicture[texture], rngX, rngY, size, size);
			magneticAsteroid.setSpeedX(randomSpeed());
			magneticAsteroid.setSpeedY(randomSpeed());
			asteroidList.add(magneticAsteroid);
			countMagneticAsteroid++;
		}
	}

	public void spawnAsteroid() {

		while (countAsteroid <= 10){
			int rngX = createRandomXStartPosition();
			int rngY = createRandomYStartPosition();

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
			juniorAsteroid.setSpeedX(randomSpeed());
			juniorAsteroid.setSpeedY(randomSpeed());
			asteroidList.add(juniorAsteroid);
		}

	}
	public void spawnJuniorMagnetic(float x, float y){
		for (int i = 0; i < 3; i++){
			int size = (int)(Math.random()* (70-35))+35;
			int texture = (int)(Math.random()* 2);

			juniorMagnetic = new JuniorMagnetic(magneticAsteroidPicture[texture], x, y, size, size);
			juniorMagnetic.setSpeedX(randomSpeed());
			juniorMagnetic.setSpeedY(randomSpeed());
			asteroidList.add(juniorMagnetic);
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


	public void CreateRandomSatellite(){
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

	public void levelComplete(int level){
		titleMusic.play();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		if (level == 1){
			batch.draw(levelOneImg, 0, 0);
		} else if (level == 2){
			batch.draw(levelTwoImg, 0, 0);
		} else if (level == 3){
			batch.draw(levelThreeImg, 0, 0);
		} else if (level == 4){
			batch.draw(levelFourImg, 0, 0);
		}

		batch.end();

		asteroidList.clear();
		bulletList.clear();
		explosionList.clear();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			titleMusic.stop();
			backMusic.play();
			if (level == 1){
				gameState = GameState.LEVEL_2;
			} else if (level == 2){
				gameState = GameState.LEVEL_3;
			} else if (level == 3){
				gameState = GameState.LEVEL_4;
			} else if (level == 4){
				gameState = GameState.TITLE_SCREEN;
			}

			img = new Texture("spaceBack.jpg");
			countAsteroid = 0;
			createNew();
		}

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

	public void renderGameStateGameOver(){

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		asteroidList.clear();
		bulletList.clear();
		explosionList.clear();
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

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			gameState = GameState.TITLE_SCREEN;
			createNew();
		}
		batch.end();
	}

	public void createLevels(int level){
		checkInput();

		spaceship.updatePositionFromSpeed(Gdx.graphics.getDeltaTime());
		shield.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY(), Gdx.graphics.getDeltaTime());
		cannon.updatePositionFromSpaceship(spaceship.getX(), spaceship.getY());
		spawnAsteroid();

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
						if (asteroid instanceof GiantMagnetic){
							explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight(), true));
							spawnJuniorMagnetic(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY()+(asteroid.getHeight()/2));
						} else if (asteroid instanceof GiantAsteroid){
							explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight(), true));
							spawnJuniorAsteroids(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY()+(asteroid.getHeight()/2));
						} else {
							explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight(), false));
						}
						bullet.hit();
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
				if (bullet instanceof SatelliteBullet){
					if (shield.collidesWith(bullet.getCollisionRectangle())){
						shield.getHit();
						bullet.hit();
						break;
					}
				}

			}

			if (asteroid.collidesWith(shield.getBounds())){
				shield.getHit();
				asteroid.hit();

				if (asteroid instanceof GiantMagnetic){
					explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight(), true));
					spawnJuniorMagnetic(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY()+(asteroid.getHeight()/2));
				} else if (asteroid instanceof GiantAsteroid){
					explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight(), true));
					spawnJuniorAsteroids(asteroid.getX()+(asteroid.getWidth()/2), asteroid.getY()+(asteroid.getHeight()/2));
				} else {
					explosionList.add(new Explosion(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight(), false));
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


		if (level >= 2) {
			spawnMagneticAsteroid();
			makeMagneticAsteroidFollow();
		}


		if (level >= 3){

			randomSatellite = (int)(Math.random() * 8 + 1);
			if (randomSatellite == 4){
				if (satelliteList.size() == 0){
						CreateRandomSatellite();
				}
			}

			for (Satellite satellite : satelliteList) {

				for (Bullet bullet : bulletList) {
					if (bullet instanceof SpaceshipBullet) {
						if (satellite.collidesWith(bullet.getCollisionRectangle())) {
							satellite.hit();
							bullet.hit();
							explosionList.add(new Explosion(satellite.getX(), satellite.getY(), satellite.getWidth(), satellite.getHeight(),false));
							score++;
							enemysLeft--;
							break;
						}
					}
				}

				if (satellite.collidesWith(spaceship.getCollisionRectangle())) {
					if (spaceship.isShieldDown()){
						gameOver = true;
					}
				}

				if (satellite.collidesWith(shield.getCollisionRectangle())) {
					satellite.hit();
					shield.getHit();
					explosionList.add(new Explosion(satellite.getX(), satellite.getY(), satellite.getWidth(), satellite.getHeight(), false));
					score++;
					enemysLeft--;
					break;
				}
			}
		}

		if (level >= 4){
			spawnGiantAsteroid();
			spawnGiantMagnetic();
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
			if (powerUp.getsHit()){
				powerUpList.remove(powerUp);
				break;
			}

		}

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
			if (explosionImage.isHasStarted()){
				explosionImage.setElapsedTime(Gdx.graphics.getDeltaTime());
				if (explosionImage.isGiant()){
					batch.draw(explosionImage.getAnimation().getKeyFrame(explosionImage.getElapsedTime()), explosionImage.getX(), explosionImage.getY(), 200, 200);
				} else {
					batch.draw(explosionImage.getAnimation().getKeyFrame(explosionImage.getElapsedTime()), explosionImage.getX(), explosionImage.getY());
				}
				if (explosionImage.getAnimation().isAnimationFinished(explosionImage.getElapsedTime())){
					explosionList.remove(explosionImage);
					explosionImage.setElapsedTimeToZero();
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

		if (level >=3){
			for (Satellite satellite : satelliteList){
				satellite.draw(batch);
			}
		}

		batch.end();

		if (spaceship.getSpeedY() == 0 && spaceship.getSpeedX() == 0){
			spaceship.updateImage("Spaceship.png");
		}
		else {
			spaceship.updateImage("SpaceshipBoost.png");
		}

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

		if (level >= 3){
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
			asteroidList.clear();
			gameOver = false;
			backMusic.stop();
			titleMusic.play();
		}

		if (enemysLeft <= 0){
			if (level == 1){
				gameState = gameState.LEVEL_COMPLETE_1;
			} else if (level == 2){
				gameState = gameState.LEVEL_COMPLETE_2;
			} else if (level == 3){
				gameState = gameState.LEVEL_COMPLETE_3;
			} else if (level == 4){
				gameState = gameState.LEVEL_COMPLETE_4;
			}
			asteroidList.clear();
			backMusic.stop();
			enemysLeft = 15;
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
}


