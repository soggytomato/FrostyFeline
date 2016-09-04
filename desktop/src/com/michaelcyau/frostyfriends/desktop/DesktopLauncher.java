package com.michaelcyau.frostyfriends.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.michaelcyau.frostyfriends.FFGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Frosty Friends";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new FFGame(), config);
	}
}
