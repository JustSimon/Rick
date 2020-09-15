package com.dre3m.spaceman.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dre3m.master.ActionResolver;
import com.dre3m.master.MainRick;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, ActionResolver {
	
	private GameHelper gameHelper;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		config.useWakelock = true;
		config.useCompass = false;
		config.useAccelerometer = false;
		initialize(new MainRick(this), config);
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}
	
	@Override 
	public void onPause() {
		super.onPause();
	} 
	
	@Override 
	public void onResume() { 
		super.onResume();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		gameHelper.onStop();
	}
	
	 
	@Override protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score, String leaderboardId) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), leaderboardId, score);
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS(String leaderId) {
		if (gameHelper.isSignedIn()) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), leaderId), 0);
		} else if (!gameHelper.isConnecting()) {
			loginGPGS();
		} else if (gameHelper.isConnecting()) {
			System.out.println("Logging in... wait longer for leaderboard...");
		} else {
			System.out.println("Unknown leaderboard problem.");
		}
	}

	@Override
	public void getAchievementsGPGS() {
		if (gameHelper.isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 100);
		} else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}

	@Override
	public void onSignInFailed() {
		System.out.println("Sign in failed.");
	}

	@Override
	public void onSignInSucceeded() {
		System.out.println("Sign in succeeded.");
	}

}
