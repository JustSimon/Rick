package com.dre3m.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	
	Texture texture_up, texture_down;
	Sprite button;
	
	Rectangle box;
	
	float x, y;
	
	float hh = Gdx.graphics.getHeight() / 320f;
	
	public Button(float x , float y, FileHandle f1, FileHandle f2) {
		
		this.x = x;
		this.y = y;
		
		texture_up = new Texture(f1);
		texture_down = new Texture(f2);
		button = new Sprite(texture_up);
		
		button.setSize(button.getWidth() * hh, button.getHeight() * hh);
		button.setPosition(x - button.getWidth() / 2, y - button.getHeight() / 2);
		
		box = new Rectangle(x - 13 * hh, Gdx.graphics.getHeight() - y, button.getWidth(), button.getHeight());
	}
	
	public void draw(SpriteBatch batch) {
		button.draw(batch);
	}
	
	public void touch(boolean b) {
		if (b) {
			if (button.getTexture() == texture_up) {
				button.setTexture(texture_down);
			}
		} else {
			if (button.getTexture() == texture_down) {
				button.setTexture(texture_up);
			}
		}
	}
	
	public Rectangle getBox() {
		return box;
	}
}
