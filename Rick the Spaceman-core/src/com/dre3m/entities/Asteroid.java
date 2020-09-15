package com.dre3m.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Asteroid {
	
	float x, y, boX, boY;
	float speedHor, speedVer;
	float rot;
	long modifier;
	
	Circle box;
	
	Texture asteroidT;
	Sprite asteroid;
	
	float hh = Gdx.graphics.getHeight() / 320f;
	
	public Asteroid(long modifier) {
		y = (float) (Math.random() * (Gdx.graphics.getHeight() - 30) + 15);
		x = Gdx.graphics.getWidth() + 50 * hh;
		
		this.modifier = modifier * 20;
		
		speedHor = (float) (Math.random() * 4) + 8;
		speedVer = (float) (Math.random() * 4) - 2;
		rot = (float) (Math.random() * 6) - 3;
		
		double textureSel = Math.random();
		String asteroidLink = null;
		if (textureSel > 0.5) {
			asteroidLink = "asteroid/asteroid.png";
		} else {
			asteroidLink = "asteroid/asteroid2.png";
		}
		
		asteroidT = new Texture(Gdx.files.internal(asteroidLink));
		asteroid = new Sprite(asteroidT);
		asteroid.setSize(asteroid.getWidth() * hh, asteroid.getHeight() * hh);
		asteroid.setOrigin(asteroid.getWidth() / 2, asteroid.getHeight() / 2);
		asteroid.setPosition(x, y);
		
		boX = x + asteroid.getWidth() / 2;
		boY = y + asteroid.getHeight() / 2;
		
		box = new Circle(boX, boY, asteroid.getWidth() / 2 - 7 * hh);
	}
	
	public void movement() {
		x = x - speedHor;
		y = y - speedVer;
		boX = boX - speedHor;
		boY = boY - speedVer;
		asteroid.setRotation(asteroid.getRotation() + rot);
	}
	
	public void update() {
		movement();
		asteroid.setPosition(x, y);
		box.setPosition(boX, boY);
	}
	
	public void draw(SpriteBatch batch) {
		asteroid.draw(batch);
	}
	
	public void debug(ShapeRenderer sr) {
		sr.circle(boX, boY, asteroid.getWidth() / 2 - 5 * hh);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Circle getBox() {
		return box;
	}
}
