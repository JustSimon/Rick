package com.dre3m.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundTile {
	
	float x, y;
	
	float hh = Gdx.graphics.getHeight() / 320f;
	
	Texture texture;
	Sprite back;
	
	public BackgroundTile(float x, float y) {
		this.x = x;
		this.y = y;
		
		texture = new Texture(Gdx.files.internal("effects/backTile.png"));
		back = new Sprite(texture);
		
		back.setSize(back.getWidth() * hh, back.getHeight() * hh);
		back.setPosition(x, y);
	}
	
	public void draw(SpriteBatch batch) {
		back.draw(batch);
	}
	
	public void update() {
		back.setX(x);
	}
	
	public void movement() {
		x = x - hh;
		if (x < -128 * hh) {
			x = Gdx.graphics.getWidth() + 1;
		}
	}
	
}
