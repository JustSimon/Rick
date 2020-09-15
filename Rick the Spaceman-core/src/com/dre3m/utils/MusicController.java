package com.dre3m.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicController {
	
	static Music music;
	
	public static void startMusic(float f) {
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/cactusLand.ogg"));
		music.play();
		music.setVolume(f);
		music.setLooping(true);
	}
	
	public static void pauseMusic() {
		music.pause();
	}
	
	public static void playMusic() {
		music.play();
	}
	
	public void setVolume(float f) {
		music.setVolume(f);
	}
	
	public boolean getPlaying() {
		return music.isPlaying();
	}
	
	public void stopMusic() {
		if (music.isPlaying()) {
			music.stop();
		}
		music.dispose();
	}
	
	public boolean isLooping() {
		return music.isLooping();
	}
	
	public static boolean isPlaying() {
		return music.isPlaying();
	}
}
