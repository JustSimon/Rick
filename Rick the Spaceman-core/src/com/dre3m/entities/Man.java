package com.dre3m.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Man {
float x, y, boxX, boxY, boxWidth, boxHeight, v, flameX, flameY;
	
	float hh = Gdx.graphics.getHeight() / 320f;
	
	boolean flameSwitch;
	
	int counter;
	
	Texture texture1, texture2, texture3, texture4, flame1, flame2;
	Sprite rick, flame;
	
	Rectangle box;
	
	ShapeRenderer sr;
	
	public Man(String type) {
		if (type == "normal") {
			texture1 = new Texture(Gdx.files.internal("rick/rick.png"));
			texture2 = new Texture(Gdx.files.internal("rick/jumpingRick.png"));
			texture3 = new Texture(Gdx.files.internal("rick/shootingRick.png"));
			texture4 = new Texture(Gdx.files.internal("rick/jumpingShootingRick.png"));
		}
		
		flame1 = new Texture(Gdx.files.internal("effects/Flame_1.png"));
		flame2 = new Texture(Gdx.files.internal("effects/Flame_2.png"));
		
		rick = new Sprite(texture1);
		rick.setSize(rick.getWidth() * hh, rick.getHeight() * hh);
		
		flame = new Sprite(flame1);
		flame.setSize(flame.getWidth() * hh, flame.getHeight() * hh);
		
		
		x = 70 * hh;
		y = Gdx.graphics.getHeight() / 2 - rick.getHeight() / 2;
		v = 0;
		
		flameSwitch = false;
		
		boxX = x + 22 * hh;
		boxY = y + 13 * hh;
		flameX = x + rick.getWidth() / 2 - flame.getWidth() / 2 + hh;
		flameY = y + rick.getHeight() / 2 - flame.getHeight() / 2 - 20 * hh;
		boxWidth = rick.getWidth() - 42 * hh;
		boxHeight = rick.getHeight() - 30 * hh;
		
		rick.setPosition(x, y);
		flame.setPosition(flameX, flameY);
		
		box = new Rectangle(boxX, boxY, boxWidth, boxHeight);
		sr = new ShapeRenderer();
	}
	
	public float getY() {
		return rick.getY();
	}
	
	public float getWidth() {
		return rick.getWidth();
	}
	
	public float getHeight() {
		return rick.getHeight();
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public Rectangle getBox() {
		return box;
	}
	
	public void setTexture(int i) {
		if (i == 1) {
			rick.setTexture(texture1);
		} else if (i == 2) {
			rick.setTexture(texture2);
		} else if (i == 3) {
			rick.setTexture(texture3);
		} else if (i == 4) {
			rick.setTexture(texture4);
		}
	}
	
	public void draw(SpriteBatch batch, boolean jumping, boolean dead) {
		counter++;
		if (counter == 10) {
			counter = 0;
			if (flameSwitch) {
				flame.setTexture(flame2);
				flameSwitch = false;
			} else {
				flame.setTexture(flame1);
				flameSwitch = true;
			}
		}
		if (jumping) {
			flame.draw(batch);
		}
		rick.draw(batch);
	}
	
	public void update() {
		rick.setY(y);
		flame.setY(flameY);
		box.setY(boxY);
	}
	
	public void shape() {
		sr.begin(ShapeType.Line);
		sr.rect(boxX, boxY, boxWidth, boxHeight);
		sr.end();
	}
	
	public void movement(boolean touch) {
		if (touch) {
			if (v <= 5 * hh) {
				v = v + 1 * hh;
			}
		} else {
			if (v >= -5 * hh) {
				v = (float) (v - 0.5 * hh);
			}
		}
		y = y + v;
		boxY = y + 13 * hh;
		flameY = y + rick.getHeight() / 2 - flame.getHeight() / 2 - 20 * hh;
	}
	
}
