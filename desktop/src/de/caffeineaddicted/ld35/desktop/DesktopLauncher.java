package de.caffeineaddicted.ld35.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.caffeineaddicted.ld35.CoffeeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;

		// fullscreen
		config.fullscreen = false;
		// vSync
		config.vSyncEnabled = true;
		new LwjglApplication(new CoffeeGame(), config);
	}
}
