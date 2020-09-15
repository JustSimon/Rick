package com.dre3m.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AsteroidDecay {
	float x1, x2, x3, x4;
	float y1, y2, y3, y4;
	double a;
	float f;
	
	float rot1, rot2, rot3, rot4;
	float speed1, speed2, speed3, speed4;
	
	Texture decayT;
	Sprite decay1, decay2, decay3, decay4;
	
	public AsteroidDecay(float x, float y) {
		x1 = x; y1 = y;
		x2 = x; y2 = y;
		x3 = x; y3 = y;
		x4 = x; y4 = y;
		
		a = 1;
		
		rot1 = (float) (Math.random() * 6) - 3;
		rot2 = (float) (Math.random() * 6) - 3;
		rot3 = (float) (Math.random() * 6) - 3;
		rot4 = (float) (Math.random() * 6) - 3;
		
		speed1 = (float) (Math.random() * 4) - 8;
		speed2 = (float) (Math.random() * 4) - 6;
		speed3 = (float) (Math.random() * 4);
		speed4 = (float) (Math.random() * 4);
		
		decayT = new Texture(Gdx.files.internal("asteroid/asteroidDecay.png"));
		
		decay1 = new Sprite(decayT);
		decay2 = new Sprite(decayT);
		decay3 = new Sprite(decayT);
		decay4 = new Sprite(decayT);
		
		decay1.setSize(decay1.getWidth() * 4, decay1.getHeight() * 4);
		decay2.setSize(decay1.getWidth(), decay1.getHeight());
		decay3.setSize(decay1.getWidth(), decay1.getHeight());
		decay4.setSize(decay1.getWidth(), decay1.getHeight());
	}
	
	public void movement() {
		if (a >= 0) {
			x1 = x1 + speed1; y1 = y1 + speed2;
			x2 = x2 + speed2; y2 = y2 + speed4;
			x3 = x4 + speed3; y3 = y3 + speed1;
			x4 = x4 + speed4; y4 = y4 + speed3;
		
			a = a - 0.02;
		
			f = (float) a;
		}
	}
	
	public void update() {
		movement();
		decay1.setPosition(x1, y1);
		decay2.setPosition(x2, y2);
		decay3.setPosition(x3, y3);
		decay4.setPosition(x4, y4);
		
		decay1.rotate(rot1);
		decay2.rotate(rot2);
		decay3.rotate(rot3);
		decay4.rotate(rot4);
		
		decay1.setAlpha(f);
		decay2.setAlpha(f);
		decay3.setAlpha(f);
		decay4.setAlpha(f);
	}
	
	public void draw(SpriteBatch batch) {
		decay1.draw(batch);
		decay2.draw(batch);
		decay3.draw(batch);
		decay4.draw(batch);
	}
	
	public double getA() {
		return a;
	}
}
