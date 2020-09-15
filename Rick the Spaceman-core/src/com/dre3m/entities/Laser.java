package com.dre3m.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Laser {

	float hh = Gdx.graphics.getHeight() / 320f;
	float x, y;
	
	Rectangle box;
	
	Texture laserT;
	Sprite laser;
	
	public Laser(float h) {
		
		x = 80 * hh;
		
		laserT = new Texture(Gdx.files.internal("effects/laser_red.png"));
		
		laser = new Sprite(laserT);
		laser.setSize(laser.getWidth() * hh * 2, laser.getHeight() * hh * 2);
		h = h + 10 * hh;
		y = h;
		laser.setPosition(x, h);
		box = new Rectangle(x, h, laser.getWidth(), laser.getHeight());
	}
	
	public void draw(SpriteBatch batch) {
		laser.draw(batch);
	}
	
	public void movement() {
		x = x + 7 * hh;
	}
	
	public void debug(ShapeRenderer sr) {
		sr.rect(x, y, laser.getWidth(), laser.getHeight());
	}
	
	public void update() {
		movement();
		laser.setX(x);
		box.setX(x);
	}
	
	public float getX() {
		return x;
	}
	
	public Rectangle getBox() {
		return box;
	}
	
	public void dispose() {
		laserT.dispose();
	}
}
