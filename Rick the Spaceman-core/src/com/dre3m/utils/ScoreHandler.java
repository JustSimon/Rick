package com.dre3m.utils;

import com.dre3m.master.ActionResolver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreHandler {
	
	static float scrw = Gdx.graphics.getWidth();
	static float scrh = Gdx.graphics.getHeight();
	
	String scoreStr;
	BitmapFont font;
	
	float x, y;
	
	ActionResolver actionResolver;
	
	int loop;
	
	static Preferences prefs;
	
	public ScoreHandler(float x, float y, float hh, ActionResolver actionR) {
		font = new BitmapFont(Gdx.files.internal("ui/font/visitor.fnt"), Gdx.files.internal("ui/font/visitor_0.png"), false);
		
		this.x = x;
		this.y = y;
		
		this.actionResolver = actionR;
		loop = 0;
		scoreStr = "SCORE : 0";
	}
	
	public void draw(SpriteBatch batch) {
		font.setColor(Color.WHITE);
		font.draw(batch, scoreStr, x, y);
	}
	
	public void updateScore(int score) {
		scoreStr = "SCORE : " + score;
		loop++;
		if (loop > 60) {
			loop = 0;
			if (actionResolver.getSignedInGPGS()) {
				if (score > 10) {
					actionResolver.unlockAchievementGPGS("CgkI4cftyaoNEAIQAw");
				} else if (score > 100) {
					actionResolver.unlockAchievementGPGS("CgkI4cftyaoNEAIQAg");
				} else if (score > 1000) {
					actionResolver.unlockAchievementGPGS("CgkI4cftyaoNEAIQBA");
				}
			}
		}
	}
	
	public void onDeath(int score) {
		prefs = Gdx.app.getPreferences("4wqq123dwa2");
		if (prefs.getInteger("suspiciouspotatos") < score) {
			prefs.putInteger("suspiciouspotatos", score);
		}
		int cumul = prefs.getInteger("normalpotatos") + score;
		prefs.putInteger("normalpotatos", cumul);
		prefs.flush();
		if (actionResolver.getSignedInGPGS()) {
			if (cumul > 100 ) {
				actionResolver.unlockAchievementGPGS("CgkI4cftyaoNEAIQBg");
			} else if (cumul > 1000) {
				actionResolver.unlockAchievementGPGS("CgkI4cftyaoNEAIQBw");
			}
		}
	}
	
	public static int requestScore() {
		prefs = Gdx.app.getPreferences("4wqq123dwa2");
		return prefs.getInteger("suspiciouspotatos");
	}
}