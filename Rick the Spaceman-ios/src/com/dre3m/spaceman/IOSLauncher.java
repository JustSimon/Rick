package com.dre3m.spaceman;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.dre3m.master.ActionResolver;
import com.dre3m.master.MainRick;

public class IOSLauncher extends IOSApplication.Delegate implements ActionResolver {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new MainRick(this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

	@Override
	public boolean getSignedInGPGS() {return false;}

	@Override
	public void loginGPGS() {}

	@Override
	public void submitScoreGPGS(int score, String leaderboardId) {}

	@Override
	public void unlockAchievementGPGS(String achievementId) {}

	@Override
	public void getLeaderboardGPGS(String leaderId) {}

	@Override
	public void getAchievementsGPGS() {}
}