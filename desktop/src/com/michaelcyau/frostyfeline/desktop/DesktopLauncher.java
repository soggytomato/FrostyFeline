package com.michaelcyau.frostyfeline.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Frosty Friends";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new com.michaelcyau.frostyfeline.FFGame(), config);
	}
}
