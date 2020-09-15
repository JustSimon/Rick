package com.dre3m.screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.dre3m.entities.Asteroid;
import com.dre3m.entities.AsteroidDecay;
import com.dre3m.entities.BackgroundTile;
import com.dre3m.entities.Laser;
import com.dre3m.entities.Man;
import com.dre3m.master.ActionResolver;
import com.dre3m.utils.ScoreHandler;

public class Play implements Screen {

	Game game;
	Menu menu;
	ActionResolver actionResolver;
	Preferences prefs;
	
	SpriteBatch batch;
	Sound explosion;
	Sound rockets;
	long rocketsId;
	Sound laserShot;
	
	BitmapFont font;
	
	float scrw, scrh;
	float hh;
	int counter;
	int score;
	int divider;
	long reloadTime;
	long startTime;
	long curTime;
	boolean jumping;
	boolean shooting;
	boolean dead;
	boolean first;
	
	ArrayList<BackgroundTile> BackTiles;
	Iterator<BackgroundTile> BackIterator;
	
	ArrayList<Laser> Lasers;
	Iterator<Laser> LaserIterator;
	
	ArrayList<Asteroid> Asteroids;
	Iterator<Asteroid> AsteroidIterator;
	
	ArrayList<AsteroidDecay> Decay;
	Iterator<AsteroidDecay> DecayIterator;
	
	Man rick;
	ScoreHandler scores;
	Button muteOff, muteOn;
	
	Texture press;
	Sprite pressToStart;
	
	Rectangle bot, top;
	
	ShapeRenderer sr;
	
	public Play(Game game, Menu menu, ActionResolver ar) {
		this.game = game;
		this.menu = menu;
		actionResolver = ar;
	}
	
	@Override
	public void render(float delta) {
		
		spawns();
		
		inputCycle();
	
		movements();
		
		rickTextures();
		
		updates();
		
		collisions();
		
		batch.begin();
		draws();
		batch.end();
		
		//TODO temp
		
//		sr.begin(ShapeType.Line);
//		
//		AsteroidIterator = Asteroids.iterator();
//		
//		while (AsteroidIterator.hasNext()) {
//			Asteroid aster = AsteroidIterator.next();
//			aster.debug(sr);
//		}
//		
//		LaserIterator = Lasers.iterator();
//		
//		while (LaserIterator.hasNext()) {
//			Laser laser = LaserIterator.next();
//			laser.debug(sr);
//		}
//		sr.end();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {
		
		hh = Gdx.graphics.getHeight() / 320f;
		
		scrw = Gdx.graphics.getWidth();
		scrh = Gdx.graphics.getHeight();
		
		dead = true; //Because Rick starts dead
		first = true; //Because it's the first game
		
		counter = 0;
		score = 0;
		divider = 10;
		
	//Initialize new whatevers
		batch = new SpriteBatch();
		
		rick = new Man("normal");
		scores = new ScoreHandler(16 * hh, scrh - 8 * hh, hh, actionResolver);
		
		BackTiles = new ArrayList<BackgroundTile>();
		Lasers = new ArrayList<Laser>();
		Asteroids = new ArrayList<Asteroid>();
		Decay = new ArrayList<AsteroidDecay>();
		
		bot = new Rectangle(0, 0 - scrh / 2, scrw, 0);
		top = new Rectangle(0, scrh + scrh / 2, scrw, 0);
	
	//Do the sounds
		explosion = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.ogg"));
		rockets = Gdx.audio.newSound(Gdx.files.internal("sound/rocket.ogg"));
		rocketsId = rockets.loop(0f);
		laserShot = Gdx.audio.newSound(Gdx.files.internal("sound/laser.ogg"));
		
	//Do the textures
		press = new Texture(Gdx.files.internal("ui/PressToStart.png"));
		
	//Do the sprites
		//Initialize the sprites
		pressToStart = new Sprite(press);
		
		//Set the sprite size
		pressToStart.setSize((float)(pressToStart.getWidth() * hh * 2.5), (float)(pressToStart.getHeight() * hh * 2.5));
		
		//Set the sprite position
		pressToStart.setPosition(scrw / 2 - pressToStart.getWidth() / 2, scrh / 2 - pressToStart.getHeight() / 2);
		
		
	//Do the background
		for (int i = 0; i < scrw + 128 * hh; i = (int) (i + 127 * hh)) {
			for (int g = 0; g < scrh + 128 * hh; g = (int) (g + 127 * hh)) {
				BackTiles.add(new BackgroundTile(i, g));
			}
		}
		
		reloadTime = System.currentTimeMillis();
		
		// TODO temp
		sr = new ShapeRenderer();
		
		font = new BitmapFont(Gdx.files.internal("ui/font/visitor.fnt"), Gdx.files.internal("ui/font/visitor_0.png"), false);
		
	}
	
	private void inputCycle() {
		// TODO input
		
		//Back button
		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			game.setScreen(menu);
		}
		
		//Starting controls
		if (dead) {
			if (Gdx.input.isTouched()) {
				dead = false;
				score = 0;
				scores.updateScore(score);
				startTime = System.currentTimeMillis();
			}
		}
		
		//Movement controls
		if (!dead) {
			//Jumping controls
			if (Gdx.input.isTouched(0) && Gdx.input.getX(0) < scrw / 2 || Gdx.input.isTouched(1) && Gdx.input.getX(1) < scrw / 2) {
				jumping = true;
			} else {
				jumping = false;
			}
			//Shooting controls
			if (Gdx.input.isTouched(0) && Gdx.input.getX(0) > scrw / 2 || Gdx.input.isTouched(1) && Gdx.input.getX(1) > scrw / 2) {
				shooting = true;
				if (System.currentTimeMillis() - reloadTime > 500) {
					Lasers.add(new Laser(rick.getY() + 10 * hh));
					laserShot.play(0.2f);
					reloadTime = System.currentTimeMillis();
				}
			} else {
				shooting = false;
			}
		} else {
			jumping = false;
			shooting = false;
		}
	}
	
	private void movements() {
		// TODO movements
		
		updateIterators();
		
		if (!dead) {
			//Rick's movement
				rick.movement(jumping);		
			//Laser movement
				while (LaserIterator.hasNext()) {
					Laser laser = LaserIterator.next();
					laser.movement();
				}
			//Asteroid movement
				while (AsteroidIterator.hasNext()) {
					Asteroid aster = AsteroidIterator.next();
					aster.movement();
				}
		    //Decay movement
				while (DecayIterator.hasNext()) {
					AsteroidDecay dec = DecayIterator.next();
					dec.movement();
				}
				
			} //End of Playfield objects
			
			//Background movements
				while (BackIterator.hasNext()) {
					BackgroundTile tile = BackIterator.next();
					tile.movement();
				}
	}
	
	private void updates() {
		// TODO updates
		
		updateIterators();
		
		//Update Rick
		rick.update();
		//Update Background
		while (BackIterator.hasNext()) {
			BackgroundTile tile = BackIterator.next();
			tile.update();
		}
		//Update Asteroids
		while (AsteroidIterator.hasNext()) {
			Asteroid aster = AsteroidIterator.next();
			aster.update();
			if (aster.getX() < - 100 * hh) {
				Asteroids.remove(aster);
				AsteroidIterator = Asteroids.iterator();
			}
		}
		//Update Lasers
		while (LaserIterator.hasNext()) {
			Laser laser = LaserIterator.next();
			laser.update();
			if (laser.getX() > scrw + 50 * hh) {
				Lasers.remove(laser);
				LaserIterator = Lasers.iterator();
			}
		}
		//Update Decay
		while (DecayIterator.hasNext()) {
			AsteroidDecay dec = DecayIterator.next();
			dec.update();
			if (dec.getA() <= 0) {
				Decay.remove(dec);
				DecayIterator = Decay.iterator();
			}
		}
		
	}
	
	private void collisions() {
		// TODO collisions
		updateIterators();
		
		//Rick with objects
		if (rick.getBox().overlaps(bot) || rick.getBox().overlaps(top)) {
			die();
		}
		
		//Lasers with Asteroids
		while (LaserIterator.hasNext()) {
			Laser lase = LaserIterator.next();
			boolean intersected = false;
			AsteroidIterator = Asteroids.iterator();
			while (AsteroidIterator.hasNext()) {
				Asteroid aster = AsteroidIterator.next();
				if (Intersector.overlaps(aster.getBox(), lase.getBox())) {
					explosion.play();
					score++;
					scores.updateScore(score);
					Decay.add(new AsteroidDecay(aster.getX(), aster.getY()));
					intersected = true;
					Asteroids.remove(aster);
					break;
				}
			}
			if (intersected) {
				Lasers.remove(lase);
				LaserIterator = Lasers.iterator();
				break;
			}
		}
		
		//Update the Asteroid iterator
		AsteroidIterator = Asteroids.iterator();
		
		//Rick with Asteroids
		while (AsteroidIterator.hasNext()) {
			Asteroid aster = AsteroidIterator.next();
			if (Intersector.overlaps(aster.getBox(), rick.getBox())) {
				Asteroids.remove(aster);
				die();
				AsteroidIterator = Asteroids.iterator();
			}
		}
	}
	
	private void draws() {
		// TODO draws
		
		updateIterators();
		
		while (BackIterator.hasNext()) {
			BackgroundTile tile = BackIterator.next();
			tile.draw(batch);
		}
		
		while (LaserIterator.hasNext()) {
			Laser laser = LaserIterator.next();
			laser.draw(batch);
		}
		
		while (AsteroidIterator.hasNext()) {
			Asteroid aster = AsteroidIterator.next();
			aster.draw(batch);
		}
		
		while (DecayIterator.hasNext()) {
			AsteroidDecay dec = DecayIterator.next();
			dec.draw(batch);
		}
		
		rick.draw(batch, jumping, dead);
		
		if (dead) {
			font.draw(batch, "HIGHSCORE : " + ScoreHandler.requestScore(), 16 * hh, scrh - 32 * hh);
		}
		
		drawUI();
	}
	
	private void drawUI() {
		if (dead) {
			pressToStart.draw(batch);
		}
		
		scores.draw(batch);
	}
	
	private void spawns() {
		// TODO spawns
		if (!dead) {
			counter++;
			
			if (score % 10 == 0) {
				if (score == 10 && divider == 20) {
					divider = 15;
				} else if (score == 20 && divider == 15) {
					divider = 10;
				} else if (score == 30 && divider == 10) {
					divider = 5;
				}	
			}
			
			if (counter % divider == 0) {
				curTime = System.currentTimeMillis();
				
				long modifier = (curTime - startTime) / 100000;
				if (Math.random() > 0.5) {
					Asteroids.add(new Asteroid(modifier));
				}
			}
			
			if (counter == 100) {
				counter = 0;
			}
		}
	}
	
	private void rickTextures() {
		
		if (jumping && shooting) {
			rick.setTexture(4);
		} else if (shooting && !jumping) {
			rick.setTexture(3);
		} else if (jumping && !shooting) {
			rick.setTexture(2);
		} else if (!jumping && !shooting) {
			rick.setTexture(1);
		}
		
		if (jumping) {
			rockets.setVolume(rocketsId, 0.4f);
		} else {
			rockets.setVolume(rocketsId, 0f);;
		}
	
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		explosion.dispose();
		rockets.dispose();
		laserShot.dispose();
	}
	
	private void updateIterators() {
		BackIterator = BackTiles.iterator();
		LaserIterator = Lasers.iterator();
		AsteroidIterator = Asteroids.iterator();
		DecayIterator = Decay.iterator();
	}
	
	public void die() {
		dead = true;
		divider = 10;
		Lasers.clear();
		Asteroids.clear();
		Decay.clear();
		rick.setY(scrh / 2 - rick.getHeight() / 2);
		scores.onDeath(score);
	}
	
}
