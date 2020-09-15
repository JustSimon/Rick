package com.dre3m.master;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.dre3m.screens.Splash;

public class MainRick extends Game {
	
	ActionResolver actionResolver;
	
	public MainRick(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		
		this.setScreen(new Splash(this, actionResolver));
	}
}
