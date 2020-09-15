package com.dre3m.master;

public interface ActionResolver {
	
	  public boolean getSignedInGPGS();
	
	  public void loginGPGS();
	  
	  public void submitScoreGPGS(int score, String leaderboardId);
	  
	  public void unlockAchievementGPGS(String achievementId);
	  
	  public void getLeaderboardGPGS(String leaderId);
	  
	  public void getAchievementsGPGS();
}
