package com.michaelcyau.frostyfriends;

import com.badlogic.gdx.Game;
import com.michaelcyau.helpers.AssetLoader;
import com.michaelcyau.screens.GameScreen;

public class FFGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new GameScreen());
		AssetLoader.bgMusic.play();
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
