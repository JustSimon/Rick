package com.dre3m.screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.dre3m.entities.BackgroundTile;
import com.dre3m.entities.Button;
import com.dre3m.master.ActionResolver;

public class Menu implements Screen {
	
	//variables\\Inherited values--
	Game game;
	ActionResolver actionResolver;
	
	//variables\\Own values--
	float hh; int scrw, scrh;
	
	SpriteBatch batch; BitmapFont font;
	
	ArrayList<BackgroundTile> BackTiles;
	Iterator<BackgroundTile> BackIterator;
	
	Button play, shop, leader, sound;
	
	Texture big_banner_texture;
	Sprite big_banner;
	
	boolean playbool, shopbool;
	
	//method\\Constructor--
	public Menu(Game game, ActionResolver actionResolver) {
		this.game = game;
		this.actionResolver = actionResolver;
	}
	
	//method\\Show--
	@Override
	public void show() {
		hh = Gdx.graphics.getHeight() / 320f;
		
		scrh = Gdx.graphics.getHeight();
		scrw = Gdx.graphics.getWidth();
		
		BackTiles = new ArrayList<BackgroundTile>();
		
		batch = new SpriteBatch();
		
		//font\\
		font = new BitmapFont(Gdx.files.internal("ui/font/visitor.fnt"), Gdx.files.internal("ui/font/visitor_0.png"), false);
		
		//textures\\Textures for random items--
		big_banner_texture = new Texture(Gdx.files.internal("ui/RickBanner.png"));
		
		//sprites\\Setup the random items--
			//item 1\\Big banner
			big_banner = new Sprite(big_banner_texture);
			big_banner.setSize((float)(big_banner.getWidth() * hh * 0.75), (float)(big_banner.getHeight() * hh * 0.75));
			big_banner.setPosition(scrw / 2 - big_banner.getWidth() / 2, (scrh / 3) * 2 - big_banner.getHeight() / 2);
			
		//buttons\\Button setup--
		play = new Button(scrw / 2, scrh / 3, Gdx.files.internal("ui/playButton.png"), Gdx.files.internal("ui/playButtonDown.png"));
		shop = new Button(scrw / 2, scrh / 5 + 8, Gdx.files.internal("ui/shopButton.png"), Gdx.files.internal("ui/shopButtonDown.png"));
		leader = new Button(scrw - 20 * hh, scrh - 20 * hh, Gdx.files.internal("ui/leaderButton.png"), Gdx.files.internal("ui/leaderButtonPressed.png"));
		sound = new Button(scrw - 50 * hh, scrh - 20 * hh, Gdx.files.internal("ui/soundButtons/muteButtonOn.png"), Gdx.files.internal("ui/soundButtons/muteButtonOnDown.png"));
		
		//values\\Default values--

		
		//backtiles\\Background setup
		for (int i = 0; i < scrw + 128 * hh; i = (int)(i + 127 * hh)) {
			for (int g = 0; g < scrh + 128 * hh; g = (int)(g + 127 * hh)) {
				BackTiles.add(new BackgroundTile(i, g));
			}
		}
		
	}
	
	//method\\Render-- repeater
	public void render(float delta) {update();draw();}

		public void update() {
			
			//input\\Handler
			if (Gdx.input.isKeyPressed(Keys.BACK)) Gdx.app.exit();
			
			if (Gdx.input.isTouched()) {
				Rectangle touch = new Rectangle (Gdx.input.getX(), Gdx.input.getY(), 0, 0);
				if (touch.overlaps(play.getBox())) {
					setTouch(true, false, false, false);
				} else if (touch.overlaps(shop.getBox())) {
					setTouch(false, true, false, false);
				} else if (touch.overlaps(leader.getBox())) {
					setTouch(false, false, true, false);
				} else if (touch.overlaps(sound.getBox())) {
					setTouch(false, false, false, true);
				} else {
					setTouch(false, false, false, false);
				}
			} else {
				if (playbool) {
					game.setScreen(new Play(game, this, actionResolver));
				}
				setTouch(false, false, false, false);
			}
			
			//arrays\\
				//prepare\\
				BackIterator = BackTiles.iterator();
				
				//background\\
				while (BackIterator.hasNext()) {
					BackgroundTile tile = BackIterator.next();
					tile.movement();
					tile.update();
				}
				
				//reset iterators\\
				BackIterator = BackTiles.iterator();
		}	
		
		public void draw() {
			Gdx.gl20.glClearColor( 0, 0, 0, 1 );
			Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
			
			batch.begin(); //begin draws\\
			
			while (BackIterator.hasNext()) {
				BackgroundTile tile = BackIterator.next();
				tile.draw(batch);
			}
			
			//text\\
			font.setColor(Color.WHITE);
			font.draw(batch, "HIGHSCORE : ", 16 * hh, scrh - 8 * hh);
			
			//buttons\\
			play.draw(batch);
			//shop.draw(batch);
			leader.draw(batch);
			sound.draw(batch);
			
			//banner\\
			big_banner.draw(batch);
			
			batch.end(); //end draws\\
		}

	//end repeater\\--
	
	//method\\Dispose--
	public void dispose() {
		
	}

	//method\\Resize--
	public void resize(int width, int height) {}
	
	//method\\Pause--
	public void pause() {}
	
	//method\\Resume--
	public void resume() {}
	
	//method\\Hide--
	@Override
	public void hide() {}
	
	//extra\\
	private void setTouch(boolean playb, boolean shopb, boolean leaderb, boolean soundb) {
		play.touch(playb);
		shop.touch(shopb);
		leader.touch(leaderb);
		sound.touch(soundb);
		playbool = playb;
		shopbool = shopb;
	}
}